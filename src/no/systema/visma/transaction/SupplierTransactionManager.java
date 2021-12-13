package no.systema.visma.transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.VislelogDao;
import no.systema.jservices.common.dao.VisleveDao;
import no.systema.jservices.common.dao.services.FirmDaoService;
import no.systema.jservices.common.dao.services.VislelogDaoService;
import no.systema.jservices.common.dao.services.VisleveDaoService;
import no.systema.visma.dto.PrettyPrintVisleveError;
import no.systema.visma.integration.LogHelper;
import no.systema.visma.integration.Supplier;

@Service
public class SupplierTransactionManager {
	/**
	 * Separate log: ${catalina.home}/logs/log4j_visma-net-proxy-transaction.log
	 */
	private static Logger logger = LogManager.getLogger(SupplierTransactionManager.class);
	
	@Autowired
	Supplier supplier;
	
	@Autowired
	VisleveDaoService visleveDaoService;

	@Autowired
	FirmDaoService firmDaoService;
	
	@Autowired
	VislelogDaoService vislelogDaoService;

	/**
	 * Syncronize all VISLEVE with Supplier in Visma.net <br>
	 * 
	 * @return List<PrettyPrintVisleveError> could be empty
	 */
	public List<PrettyPrintVisleveError> syncronizeSuppliers() {
		logger.info("Syncronizing all records in VISLEVE -> Supplier.");
		List<VisleveDao> visleveList = visleveDaoService.findAll(null);
		
		List<PrettyPrintVisleveError> errorList = new ArrayList<PrettyPrintVisleveError>();
		
		visleveList.forEach((dao) -> {
			try {

				syncronizeSupplier(dao);
				
				deleteVisleve(dao);

			} 
			catch (HttpClientErrorException e) {
				logger.error(e);
				errorList.add(new PrettyPrintVisleveError(dao.getKundnr(), LocalDateTime.now(), e.getStatusText()));
				setError(dao, e.getStatusText());
				visleveDaoService.updateOnError(dao);
				createVislelog(dao,  e.getMessage());				
				//continues with next dao in list
			}		
			catch (Exception e) {
				logger.error(e);
				errorList.add(new PrettyPrintVisleveError(dao.getKundnr(), LocalDateTime.now(), e.getMessage()));
				setError(dao, e.getMessage());
				visleveDaoService.updateOnError(dao);		
				createVislelog(dao,  e.getMessage());	
				//continues with next dao in list
			}

		});

		logger.info("Syncronized ("+visleveList.size()+") in VISLEVE -> Supplier.");
		logger.info("Error list size="+errorList.size());
		
		return errorList;
		
	}
	
	private void createVislelog(VisleveDao dao) {
		VislelogDao vislelogDao = getVislelogDao(dao, null);
		vislelogDaoService.create(vislelogDao);
		logger.debug("VISLELOG created, dao="+vislelogDao);	
	}

	
	private void createVislelog(VisleveDao dao, String errorText) {
		VislelogDao vislelogDao = getVislelogDao(dao, errorText);
		vislelogDaoService.create(vislelogDao);
		logger.debug("VISLELOG created(with error), dao="+vislelogDao);	
	}	
	
	
	private void deleteVisleve(VisleveDao dao) {
		visleveDaoService.delete(dao);
		logger.info("VISLEVE deleted, dao="+dao);
		
	}

	private void setError(VisleveDao dao, String errorText) {
		int[] dato = LogHelper.getNowDato();		
		dao.setSyncda(dato[0]);
		dao.setSyerro(errorText);
		
	}

	private void syncronizeSupplier(VisleveDao visleveDao) throws RestClientException,  IndexOutOfBoundsException { 
		logger.info("Levnr:"+visleveDao.getLevnr()+" about to be syncronized.");
		try {
			
			supplier.syncronize(visleveDao);
			logger.info("Levnr:"+visleveDao.getLevnr()+" syncronized.");
			
		} 
		catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixSupplier(visleveDao.getLevnr()));
			logger.error("Could not syncronize visleve, due to Visma.net error="+e.getStatusText());  //Status text holds Response body from Visma.net
			throw e;
		} 
		catch (RestClientException | IndexOutOfBoundsException e) {
			logger.error(LogHelper.logPrefixSupplier(visleveDao.getLevnr()));
			logger.error("Could not syncronize visleve="+visleveDao, e);
			throw e;
		}
		catch (Exception e) {
			logger.error(LogHelper.logPrefixSupplier(visleveDao.getLevnr()));
			logger.error("Could not syncronize visleve="+visleveDao, e);
			throw e;
			
		}
		
		logger.info(LogHelper.logPrefixSupplier(visleveDao.getLevnr()));
		logger.info("Levnr:"+visleveDao.getLevnr()+" syncronized.");
		
	}

	
	private VislelogDao getVislelogDao(VisleveDao visleveDao, String errorText) {
		String syerror;
		VislelogDao dao = new VislelogDao();
		dao.setFirma(visleveDao.getFirma());
		dao.setLnavn(visleveDao.getLnavn());
		dao.setLevnr(visleveDao.getLevnr());

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
	 * For test and debugging purpose. Just sync one VISLEVE to Supplier
	 * 
	 * @param levnr
	 */
	public void syncronizeSupplier(int levnr) {
		VisleveDao qDao = new VisleveDao();
		qDao.setKundnr(levnr);
		VisleveDao resultDao = visleveDaoService.find(qDao);
		
		if(resultDao != null) {
			logger.debug("VISLEVE found on levnr="+levnr);
			syncronizeSupplier(qDao);
			
		} else {
			logger.debug("VISLEVE NOT found on levnr="+levnr);
		}
		
	}
	
}
