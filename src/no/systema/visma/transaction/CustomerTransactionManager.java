package no.systema.visma.transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.ViskulogDao;
import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.services.FirmDaoService;
import no.systema.jservices.common.dao.services.ViskulogDaoService;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.visma.dto.PrettyPrintViskundeError;
import no.systema.visma.integration.Customer;
import no.systema.visma.integration.LogHelper;

@Service
public class CustomerTransactionManager {
	/**
	 * Separate log: ${catalina.home}/logs/log4j_visma-net-proxy-transaction.log
	 */
	private static Logger logger = LoggerFactory.getLogger(CustomerTransactionManager.class);
	
	@Autowired
	Customer customer;
	
	@Autowired
	ViskundeDaoService viskundeDaoService;

	@Autowired
	FirmDaoService firmDaoService;
	
	@Autowired
	ViskulogDaoService viskulogDaoService;

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
				
				deleteViskunde(dao);
				

			} 
			catch (HttpClientErrorException e) {
				logger.error(e.toString());
				errorList.add(new PrettyPrintViskundeError(dao.getKundnr(), LocalDateTime.now(), e.getStatusText()));
				setError(dao, e.getStatusText());
				viskundeDaoService.updateOnError(dao);
				createViskulog(dao,  e.getStatusText());				
				//continues with next dao in list
			}		
			catch (Exception e) {
				logger.error(e.toString());
				errorList.add(new PrettyPrintViskundeError(dao.getKundnr(), LocalDateTime.now(), e.getMessage()));
				setError(dao, e.getMessage());
				viskundeDaoService.updateOnError(dao);		
				createViskulog(dao,  e.getMessage());	
				//continues with next dao in list
			}

		});

		logger.info("Syncronized ("+viskundeList.size()+") in VISKUNDE -> Customer.");
		logger.info("Error list size="+errorList.size());
		
		return errorList;
		
	}
	
	private void createViskulog(ViskundeDao dao) {
		ViskulogDao viskulogDao = getViskulogDao(dao, null);
		viskulogDaoService.create(viskulogDao);
		logger.debug("VISKULOG created, dao="+viskulogDao);	
	}

	
	private void createViskulog(ViskundeDao dao, String errorText) {
		ViskulogDao viskulogDao = getViskulogDao(dao, errorText);
		viskulogDaoService.create(viskulogDao);
		logger.debug("VISKULOG created(with error), dao="+viskulogDao);	
	}	
	
	
	private void deleteViskunde(ViskundeDao dao) {
		viskundeDaoService.delete(dao);
		logger.info("VISKUNDE deleted, dao="+dao);
		
	}

	private void setError(ViskundeDao dao, String errorText) {
		int[] dato = LogHelper.getNowDato();		
		dao.setSyncda(dato[0]);
		dao.setSyerro(errorText);
		
	}

	private void syncronizeCustomer(ViskundeDao viskundeDao) throws RestClientException,  IndexOutOfBoundsException { 
		logger.info("Kundnr:"+viskundeDao.getKundnr()+" about to be syncronized.");
		try {
			
			customer.syncronize(viskundeDao);
			logger.info("Kundnr:"+viskundeDao.getKundnr()+" syncronized.");
			
		} 
		catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixCustomer(viskundeDao.getKundnr()));
			logger.error("Could not syncronize viskunde, due to Visma.net error="+e.getStatusText());  //Status text holds Response body from Visma.net
			throw e;
		} 
		catch (RestClientException | IndexOutOfBoundsException e) {
			logger.error(LogHelper.logPrefixCustomer(viskundeDao.getKundnr()));
			logger.error("Could not syncronize viskunde="+viskundeDao, e);
			throw e;
		}
		catch (Exception e) {
			logger.error(LogHelper.logPrefixCustomer(viskundeDao.getKundnr()));
			logger.error("Could not syncronize viskunde="+viskundeDao, e);
			throw e;
			
		}
		
		logger.info(LogHelper.logPrefixCustomer(viskundeDao.getKundnr()));
		logger.info("Kundnr:"+viskundeDao.getKundnr()+" syncronized.");
		
	}

	
	private ViskulogDao getViskulogDao(ViskundeDao viskundeDao, String errorText) {
		String syerror;
		ViskulogDao dao = new ViskulogDao();
		dao.setFirma(viskundeDao.getFirma());
		dao.setKnavn(viskundeDao.getKnavn());
		dao.setKundnr(viskundeDao.getKundnr());

		if (errorText != null) {
			syerror = LogHelper.trimToError(errorText);
			dao.setSyerro(syerror);
			dao.setStatus("ER");
		} else {
			dao.setStatus("OK");			
		}

		int[] dato = LogHelper.getNowDato();		
		dao.setSyncda(dato[0]);
		dao.setSynctm(dato[1]);
		
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
