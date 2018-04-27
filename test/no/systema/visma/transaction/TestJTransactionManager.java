package no.systema.visma.transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJTransactionManager {

	@Autowired
	@Qualifier("tsManager")
	TransactionManager transactionManager;
	

	@Test
	public void testSyncCustomer() {
		
		transactionManager.syncronizeCustomers();

		
	}

}
