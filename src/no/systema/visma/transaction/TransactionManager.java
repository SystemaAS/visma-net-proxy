package no.systema.visma.transaction;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.visma.integration.Customer;

@Service("tsManager")
public class TransactionManager {
	private static Logger logger = Logger.getLogger(TransactionManager.class);
	
	
	@Autowired
	private Customer customer;
	
	@Autowired
	private ViskundeDaoService viskundeDaoService;
	
	
	/**
	 * Syncronice VISKUNDE with Customer in Visma.net <br>
	 * 
	 * Start with DML on VISKUNDE and then call to Visma.net
	 * If Visma.net return error DML in VISKUNDE is rolled back.
	 * 
	 * Error status can be found in error-log
	 * 
	 * 
	 * 
	 */
	public void syncronizeCustomers() {
		//TODO
		
		logger.info("syncronizeCustomers");
		
		List<ViskundeDao> viskundeList = viskundeDaoService.findAll(null);
		
		
		
		
	}
	
	public void syncronizeCustomer(String kundnr) {
		//TODO
		
		logger.info("syncronizeCustomer, kundnr="+kundnr);
		
		ViskundeDao dao = viskundeDaoService.find(kundnr);
		
		logger.info("Viskunde about to be syncronized. dao="+ReflectionToStringBuilder.toString(dao));
		
		//TODO
		//1. Delete from VISKKUNDE
		//2. Update/Create in Visma
		//3. Add transaction, maybe on syncronizeCustomer
		try {
			customer.customerPutBycustomerCd(dao); //Nja customerPost oxå
		} catch (RestClientException e) {
			logger.error("Could not do PUT on dao="+dao.toString(), e);
			throw new RuntimeException(e.getMessage());
		}

		viskundeDaoService.delete(dao); 
		
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
