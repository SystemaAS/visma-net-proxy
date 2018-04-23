package no.systema.visma.transaction;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.jservices.common.dao.services.ViskundeDaoServiceImpl;
import no.systema.visma.integration.Customer;
import no.systema.visma.v1client.ApiClient;
import no.systema.visma.v1client.api.CustomerApi;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TransactionManager.class, ApiClient.class, RestTemplate.class,CustomerApi.class, Customer.class, ViskundeDaoServiceImpl.class}, loader=AnnotationConfigContextLoader.class)
public class TestJTransactionManager {

	@Autowired
	@Qualifier("tsManager")
	TransactionManager transactionManager;
	
	
	@Before
	public void setUp() throws Exception {
		ApplicationContext  context = new ClassPathXmlApplicationContext("syjservicescommon-data-service-test.xml");
		ViskundeDaoService viskundeDaoService = (ViskundeDaoService) context.getBean("viskundeDaoService");
		
		transactionManager.setViskundeDaoService(viskundeDaoService); //To get DI to work
	
	}

	@Test
	public void testSyncCustomer() {
		
		transactionManager.syncronizeCustomers();

		
	}

}
