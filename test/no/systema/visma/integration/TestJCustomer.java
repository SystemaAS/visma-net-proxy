package no.systema.visma.integration;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import no.systema.jservices.common.dao.ViskundeDao;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJCustomer {

	private static Logger logger = Logger.getLogger(TestJCustomer.class);	
	
	@Autowired 
	Customer customer;

	@Test
	public void testGetCustomer() {
		
		logger.debug("dto="+customer.getGetBycustomerCd("10010"));

	}	
	

	@Test(expected=HttpClientErrorException.class)  //Postcode value
	public void testCustomerSyncSmall() {
		String name = "Kalles chokladfabrik (small)";
		ViskundeDao dao =getSmallDao(10, name);
		
		customer.syncronize(dao);

	}
	
	@Test
	public void testCustomerSyncMedium() {
		String name = "Kalles chokladfabrik (medium)";
		ViskundeDao dao =getMediumDao(10, "123999" ,name);

		customer.syncronize(dao);
		
	}

	@Test
	public void testCustomerUpdate() {
		String name = "Kalles chokladfabrik (medium)";
		ViskundeDao dao =getMediumDao(1,"123456" ,name);
		dao.setKnavn("Kalles chokladfabrik (UPDATE 11:41)");
		
		customer.syncronize(dao);
		
	}	


	@Test
	public void testGetCustomerClasses() {
		
		customer.customerGetCustomerClasses();
		
	}		
	
	
	private ViskundeDao getSmallDao(int kundnr, String name) {
		ViskundeDao dao = new ViskundeDao();
		dao.setKundnr(kundnr);
		dao.setKnavn(name);
		dao.setAktkod("A"); //I
		return dao;
	}		
	
	private ViskundeDao getMediumDao(int kundnr, String syrg, String name) {
		ViskundeDao dao = new ViskundeDao();
		dao.setKundnr(kundnr);
		dao.setKnavn(name);
		dao.setAdr1("adr1");
		dao.setAdr2("adr2");
		dao.setAdr3("adr3");
		dao.setSyland("NO");
		dao.setPostnr(6001);
		dao.setSyrg(syrg);
		dao.setAktkod("A"); //I

		
		return dao;
	}	
	
	
//	@Test
//	public void testLocationParsing() {
//		String number = "10016";
//		String location = "https://integration.visma.net/API/controller/api/v1/customer/10016";
//		
//		String diff = doTheThing(location);
//		
//		System.out.println("diff="+diff);
//		
//		Assert.assertEquals(number, diff);
//	}
//
//	
//	private static String doTheThing(String location) {
//		String callUrl = "https://integration.visma.net/API/controller/api/v1/customer";
//		
//		int lastSlash = StringUtils.indexOfDifference(location, callUrl);
//		String diff = StringUtils.substring(location, lastSlash +1 );
//		return diff;
//	}	
	
	
}
