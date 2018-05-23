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

import no.systema.jservices.common.dao.ViskulogDao;
import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.services.FirmDaoService;
import no.systema.jservices.common.dao.services.ViskulogDaoService;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.jservices.common.dao.services.VissyskunDaoService;
import no.systema.visma.PrettyPrintViskundeError;
import no.systema.visma.integration.Customer;
import no.systema.visma.integration.Helper;

@Service
public class CustomerTransactionManager {
	/**
	 * Separate log: ${catalina.home}/logs/log4j_visma-net-proxy-transaction.log
	 */
	private static Logger logger = Logger.getLogger(CustomerTransactionManager.class);
	
	@Autowired
	Customer customer;
	
	@Autowired
	ViskundeDaoService viskundeDaoService;

	@Autowired
	FirmDaoService firmDaoService;
	
	@Autowired
	ViskulogDaoService viskulogDaoService;

	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd"); 		
	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
	
	/**
	 * Syncronize all VISKUNDE with Customer in Visma.net <br>
	 * 
	 * @return List<PrettyPrintViskundeError> could be empty
	 */
	public List<PrettyPrintViskundeError> syncronizeCustomers() {
		logger.info("Syncronizing all records in VISKUNDE -> Customer.");
		List<ViskundeDao> viskundeList = viskundeDaoService.findAll(null);
		
		List<PrettyPrintViskundeError> errorList = new ArrayList<PrettyPrintViskundeError>();
		
		viskundeList.forEach((dao) -> {
			try {

				syncronizeCustomer(dao);

				ViskulogDao viskulogDao = getViskulogDao(dao, null);
				viskulogDaoService.create(viskulogDao);
				logger.info("VISKULOG created, dao="+viskulogDao);	
			} 
			catch (HttpClientErrorException e) {
				logger.error(e);
				errorList.add(new PrettyPrintViskundeError(dao.getKundnr(), LocalDateTime.now(), e.getStatusText()));
				setError(dao, e.getStatusText());
				viskundeDaoService.updateOnError(dao);
				ViskulogDao viskulogDao = getViskulogDao(dao, e.getStatusText());
				viskulogDaoService.create(viskulogDao);
				logger.info("VISKULOG created, dao="+viskulogDao);			
				//continues with next dao in list
			}		
			catch (Exception e) {
				logger.error(e);
				errorList.add(new PrettyPrintViskundeError(dao.getKundnr(), LocalDateTime.now(), e.getMessage()));
				setError(dao, e.getMessage());
				viskundeDaoService.updateOnError(dao);		
				ViskulogDao viskulogDao = getViskulogDao(dao, e.getMessage());
				viskulogDaoService.create(viskulogDao);
				logger.info("VISKULOG created, dao="+viskulogDao);	
				//continues with next dao in list
			}


		});

		logger.info("Syncronized ("+viskundeList.size()+") in VISKUNDE -> Customer.");
		logger.info("Error list size="+errorList.size());
		
		return errorList;
		
	}
	
	private void setError(ViskundeDao dao, String errorText) {
		logger.info("Inside setError, errorText="+errorText);
		
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
	public void syncronizeCustomer(ViskundeDao viskundeDao) throws RestClientException,  IndexOutOfBoundsException { 
		logger.info("Kundnr:"+viskundeDao.getKundnr()+" about to be syncronized.");
		try {
			
			customer.syncronize(viskundeDao);
			logger.info("Kundnr:"+viskundeDao.getKundnr()+" syncronized.");

			viskundeDaoService.delete(viskundeDao);
			logger.info("VISKUNDE deleted, dao="+viskundeDao);
			
			
		} 
		catch (HttpClientErrorException e) {
			logger.error(Helper.logPrefix(viskundeDao.getKundnr()));
			logger.error("Could not syncronize viskunde, due to Visma.net error="+e.getStatusText(), e);  //Status text holds Response body from Visma.net
			throw e;
		} 
		catch (RestClientException | IndexOutOfBoundsException e) {
			logger.error(Helper.logPrefix(viskundeDao.getKundnr()));
			logger.error("Could not syncronize viskunde="+viskundeDao, e);
			throw e;
		}
		catch (Exception e) {
			logger.error(Helper.logPrefix(viskundeDao.getKundnr()));
			logger.error("Could not syncronize viskunde="+viskundeDao, e);
			throw e;
			
		}
		
		logger.info(Helper.logPrefix(viskundeDao.getKundnr()));
		logger.info("Kundnr:"+viskundeDao.getKundnr()+" syncronized.");
		
	}

	
	private ViskulogDao getViskulogDao(ViskundeDao viskundeDao, String errorText) {
		String syerror;
		ViskulogDao dao = new ViskulogDao();
		dao.setFirma(viskundeDao.getFirma());
		dao.setKnavn(viskundeDao.getKnavn());
		dao.setKundnr(viskundeDao.getKundnr());

		if (errorText != null) {
			if (errorText.length() < 200) {
				syerror = errorText;
			}
			int beginIndex = errorText.length() - 199;  //syerro is set to 200
			syerror = errorText.substring(beginIndex);		
			dao.setSyerro(syerror);
			dao.setStatus("ER");
		}

		LocalDateTime now = LocalDateTime.now();
		String nowDate = now.format(dateFormatter);
		String nowTime = now.format(timeFormatter);
		int syncDa = Integer.valueOf(nowDate);
		int synctm = Integer.valueOf(nowTime);	
		dao.setSyncda(syncDa);
		dao.setSynctm(synctm);
		
		return dao;
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
