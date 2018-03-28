package no.systema.visma.integration;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("tsManager")
public class TransactionManager {
	private static Logger log = Logger.getLogger(TransactionManager.class.getSimpleName());
	
	
	public void syncronizeCustomer(String id) {
		//TODO
		
		log.info("syncronizeCustomer, id="+id);
		
	}
	
	public void syncronizeCustomers() {
		//TODO
		
		log.info("syncronizeCustomers");
		
	}	
	
}
