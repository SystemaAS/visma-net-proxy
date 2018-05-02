package no.systema.visma.transaction;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmDao;
import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.VissyskunDao;
import no.systema.jservices.common.dao.services.FirmDaoService;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.jservices.common.dao.services.VissyskunDaoService;
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
	
	
	/**
	 * Syncronize all VISKUNDE with Customer in Visma.net <br>
	 * 
	 * 
	 */
	public void syncronizeCustomers() {
		logger.info("Syncronizing all records in VISKUNDE -> Customer.");
		List<ViskundeDao> viskundeList = viskundeDaoService.findAll(null);
		
		List<ViskundeDao> errorList = new ArrayList<ViskundeDao>();
		
		viskundeList.forEach((dao) -> {
			try {
				syncronizeCustomer(dao);
			} catch (RestClientException  | IndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				logger.error(Customer.logPrefix(dao.getKundnr(), null));
				logger.error(e);
				//TODO bra loggning i tabell
				
				errorList.add(dao);
				
			}

		});

		logger.info("Syncronized ("+viskundeList.size()+") in VISKUNDE -> Customer.");
		
		logger.info("Error list size="+errorList.size());
		
	}
	
	/**
	 * Syncronize one VISKUNDE with Customer in Visma.net
	 * 
	 * @param viskundDao
	 */
	public void syncronizeCustomer(ViskundeDao viskundeDao) throws RestClientException,  IndexOutOfBoundsException {  //TODO Add transaction and smart logging!!
		logger.info("Kundnr:"+viskundeDao.getKundnr()+" about to be syncronized.");
		String number = null;
		try {
			
			VissyskunDao vissyskunDao = vissyskunDaoService.findBySyspedKundnr(viskundeDao.getKundnr());

			if (vissyskunDao != null) { //Update
				number = String.valueOf(vissyskunDao.getVisknr());
				customer.customerPutBycustomerCd(String.valueOf(vissyskunDao.getVisknr()), viskundeDao);
			} else { //New
				int numberNew = customer.customerPost(viskundeDao);
				VissyskunDao vissyskundao = createVissyskunDao(viskundeDao, numberNew);
				vissyskunDaoService.create(vissyskundao);
				number = String.valueOf(numberNew);
				logger.info(Customer.logPrefix(viskundeDao.getKundnr(), number));
				logger.info("Kundnr:"+viskundeDao.getKundnr()+" created in VISSYSKUN.");
			}
			
			viskundeDaoService.delete(viskundeDao);
			
		} catch (RestClientException | IndexOutOfBoundsException e) {
			logger.error(Customer.logPrefix(viskundeDao.getKundnr(), number));
			logger.error("Could not syncronize viskunde="+viskundeDao, e);
			throw e;
		} 

		logger.debug(Customer.logPrefix(viskundeDao.getKundnr(), number));
		logger.info("Record="+viskundeDao.toString()+" syncronized.");
		
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
