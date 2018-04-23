package no.systema.visma.transaction;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.visma.integration.Customer;

@Service("tsManager")
public class TransactionManager {
	/**
	 * Separate log: ${catalina.home}/logs/log4j_visma-net-proxy-transaction.log
	 */
	private static Logger logger = Logger.getLogger(TransactionManager.class);
	
	@Autowired
	private Customer customer;
	
	@Autowired
	private ViskundeDaoService viskundeDaoService;
	
	/**
	 * Syncronize all VISKUNDE with Customer in Visma.net <br>
	 * 
	 * 
	 */
	public void syncronizeCustomers() {
		logger.info("Syncronizing all records in VISKUNDE -> Customer.");
		List<ViskundeDao> viskundeList = viskundeDaoService.findAll(null);
		
		viskundeList.forEach((dao) -> {
			syncronizeCustomer(dao);

		});
		
		logger.info("Syncronizing all ("+viskundeList.size()+") in VISKUNDE -> Customer ready!");
		
	}
	
	/**
	 * Syncronize one VISKUNDE with Customer in Visma.net
	 * 
	 * @param dao
	 */
	public void syncronizeCustomer(ViskundeDao dao) {
		logger.info("Kundnr:"+dao.getKundnr()+" about to be syncronized.");
		customer.syncronizeCustomer(dao);
		logger.info("Record="+dao.toString()+" syncronized.");
			
		viskundeDaoService.delete(dao); 
		logger.info("Kundnr:"+dao.getKundnr()+" deleted from VISKUNDE.");
		
		//TODO bra loggning, FlipTable
		
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
	
	
	/**
	 * To be initiated via JUnit tests
	 * 
	 * @param viskundeDaoService
	 */
	public void setViskundeDaoService(ViskundeDaoService viskundeDaoService) {
		this.viskundeDaoService = viskundeDaoService;
	}	
	
	
	
}
