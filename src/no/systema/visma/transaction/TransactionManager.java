package no.systema.visma.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmDao;
import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.VissyskunDao;
import no.systema.jservices.common.dao.services.FirmDaoService;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.jservices.common.dao.services.VissyskunDaoService;
import no.systema.visma.PrettyPrintViskundeError;
import no.systema.visma.integration.Customer;

@Service("tsManager")
public class TransactionManager {
	/**
	 * Separate log: ${catalina.home}/logs/log4j_visma-net-proxy-transaction.log
	 */
	private static Logger logger = Logger.getLogger(TransactionManager.class);
	
	@Autowired
	Customer customer;
	
	@Autowired
	ViskundeDaoService viskundeDaoService;

	@Autowired
	VissyskunDaoService vissyskunDaoService;	
	
	@Autowired
	FirmDaoService firmDaoService;

	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd"); 		
	
	/**
	 * Syncronize all VISKUNDE with Customer in Visma.net <br>
	 * 
	 * 
	 */
	public List<PrettyPrintViskundeError> syncronizeCustomers() {
		logger.info("Syncronizing all records in VISKUNDE -> Customer.");
		List<ViskundeDao> viskundeList = viskundeDaoService.findAll(null);
		
		List<PrettyPrintViskundeError> errorList = new ArrayList<PrettyPrintViskundeError>();
		
		viskundeList.forEach((dao) -> {
			try {
				syncronizeCustomer(dao);
			} 
			catch (HttpClientErrorException e) {
				logger.error(e);
				errorList.add(new PrettyPrintViskundeError(dao.getKundnr(), LocalDateTime.now(), e.getStatusText()));
				setError(dao, e.getStatusText());
				viskundeDaoService.updateOnError(dao);
				//continues with next dao in list
			}		
			catch (Exception e) {
				logger.error(e);
				errorList.add(new PrettyPrintViskundeError(dao.getKundnr(), LocalDateTime.now(), e.getMessage()));
				setError(dao, e.getMessage());
				viskundeDaoService.updateOnError(dao);				
				//continues with next dao in list
			}


		});

		logger.info("Syncronized ("+viskundeList.size()+") in VISKUNDE -> Customer.");
		logger.info("Error list size="+errorList.size());
		
		return errorList;
		
	}
	
	private void setError(ViskundeDao dao, String errorText) {
		LocalDateTime now = LocalDateTime.now();
		String nowDate = now.format(dateFormatter);
		int syncDa = Integer.valueOf(nowDate);
		dao.setSyncda(syncDa);
		dao.setSyerro(errorText);
		
	}

	/**
	 * Syncronize one VISKUNDE with Customer in Visma.net
	 * 
	 * @param viskundDao
	 */
	public void syncronizeCustomer(ViskundeDao viskundeDao) throws RestClientException,  IndexOutOfBoundsException {  //TODO Add transaction 
		logger.info("Kundnr:"+viskundeDao.getKundnr()+" about to be syncronized.");
		String number = null;
		try {
			
			VissyskunDao vissyskunDao = vissyskunDaoService.findBySyspedKundnr(viskundeDao.getKundnr());
			logger.info(Customer.logPrefix(viskundeDao.getKundnr(), number));
			logger.info("VISSYSKUN exist. Dao="+vissyskunDao);
			
			if (vissyskunDao != null) { //Update
				number = String.valueOf(vissyskunDao.getVisknr());
				customer.customerPutBycustomerCd(String.valueOf(vissyskunDao.getVisknr()), viskundeDao);
				logger.info(Customer.logPrefix(viskundeDao.getKundnr(), number));
				logger.info("Customer uppdated.");
			} else { //New
				int numberNew = customer.customerPost(viskundeDao);
				number = String.valueOf(numberNew); //for logging
				logger.info(Customer.logPrefix(viskundeDao.getKundnr(), number));
				logger.info("Customer created.");
				VissyskunDao vissyskundao = createVissyskunDao(viskundeDao, numberNew);
				vissyskunDaoService.create(vissyskundao);
				logger.info("VISSYSKUN created. Dao="+vissyskundao);
			}
			
			viskundeDaoService.delete(viskundeDao);
			logger.info("VISKUNDE deleted, dao="+viskundeDao);
			
		} 
		catch (HttpClientErrorException e) {
			logger.error(Customer.logPrefix(viskundeDao.getKundnr(), number));
			logger.error("Could not syncronize viskunde, due to Visma.net error="+e.getStatusText(), e);  //Status text holds Response body from Visma.net
			throw e;
		} 
		catch (RestClientException | IndexOutOfBoundsException e) {
			logger.error(Customer.logPrefix(viskundeDao.getKundnr(), number));
			logger.error("Could not syncronize viskunde="+viskundeDao, e);
			throw e;
		}
		catch (Exception e) {
			logger.error(Customer.logPrefix(viskundeDao.getKundnr(), number));
			logger.error("Could not syncronize viskunde="+viskundeDao, e);
			throw e;
			
		}
		
		logger.info(Customer.logPrefix(viskundeDao.getKundnr(), number));
		logger.info("Kundnr:"+viskundeDao.getKundnr()+" syncronized.");
		
	}


	private VissyskunDao createVissyskunDao(ViskundeDao viskundeDao, int number) {
		VissyskunDao dao;
		try {
			dao = new VissyskunDao();
			dao.setFirma(getFirma());
			dao.setKundnr(viskundeDao.getKundnr());
			dao.setVisknr(number);
		} catch (Exception e) {
			logger.error(Customer.logPrefix(viskundeDao.getKundnr(), String.valueOf(number)));
			logger.error("Could not create VissyskunDao", e);
			throw e;
		}
		
		return dao;
	}
	
	
	/**
	 * Get fifirm from FIRM
	 * 
	 * Note: support only one firma! If multifirma is needed, take as inparam in web-controller
	 * 
	 * @return fifirm
	 * @throws IndexOutOfBoundsException if more than one row in FIRM
	 */
	private String getFirma() throws IndexOutOfBoundsException {
		List<FirmDao> daoList = firmDaoService.findAll(null);
		String firma;
		try {
			firma = daoList.get(0).getFifirm();
		} catch (IndexOutOfBoundsException e) {
			logger.error("Could not select one fifirm from FIRMA!");
			throw e;
		}

		return firma;

	}

	/**
	 * For test and debugging purpose. Just sync one VISKUNDE to Customer
	 * 
	 * @param kundnr
	 */
	public void syncronizeCustomer(int kundnr) {
		ViskundeDao qDao = new ViskundeDao();
		qDao.setKundnr(kundnr);
		ViskundeDao resultDao = viskundeDaoService.find(qDao);
		
		if(resultDao != null) {
			logger.debug("VISKUNDE found on kundnr="+kundnr);
			syncronizeCustomer(qDao);
			
		} else {
			logger.debug("VISKUNDE NOT found on kundnr="+kundnr);
		}
		
	}
	
}
