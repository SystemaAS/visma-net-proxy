package no.systema.visma.integration;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.visma.transaction.TransactionManager;
import no.systema.visma.v1client.model.CustomerInvoiceDto;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJCustomerInvoice {

	private static Logger logger = Logger.getLogger(TestJCustomerInvoice.class);	
	
	@Autowired 
	CustomerInvoice customerInvoice;

	@Test
	public void testGetCustomerInvoice() {

		
		CustomerInvoiceDto dto = customerInvoice.getByinvoiceNumber("000001");
		
		logger.debug("dto="+dto);

	}	
	
	

//	@Test(expected=HttpClientErrorException.class)  //Postcode value
//	public void testCustomerSyncSmall() {
//		String name = "Kalles chokladfabrik (small)";
//		ViskundeDao dao =getSmallDao(10, name);
//		
//		customerInvoice.syncronize(dao);
//
//	}
//	
//	@Test
//	public void testCustomerSyncMedium() {
//		String name = "Kalles chokladfabrik (medium)";
//		ViskundeDao dao =getMediumDao(10, "123999" ,name);
//
//		customerInvoice.syncronize(dao);
//		
//	}
//
//	@Test
//	public void testCustomerUpdate() {
//		String name = "Kalles chokladfabrik (medium)";
//		ViskundeDao dao =getMediumDao(1,"123456" ,name);
//		dao.setKnavn("Kalles chokladfabrik (UPDATE 11:41)");
//		
//		customerInvoice.syncronize(dao);
//		
//	}	
//
//	private ViskundeDao getSmallDao(int kundnr, String name) {
//		ViskundeDao dao = new ViskundeDao();
//		dao.setKundnr(kundnr);
//		dao.setKnavn(name);
//		dao.setAktkod("A"); //I
//		return dao;
//	}		
//	
//	private ViskundeDao getMediumDao(int kundnr, String syrg, String name) {
//		ViskundeDao dao = new ViskundeDao();
//		dao.setKundnr(kundnr);
//		dao.setKnavn(name);
//		dao.setAdr1("adr1");
//		dao.setAdr2("adr2");
//		dao.setAdr3("adr3");
//		dao.setSyland("NO");
//		dao.setPostnr(6001);
//		dao.setSyrg(syrg);
//		dao.setAktkod("A"); //I
//
//		
//		return dao;
//	}	
	
}
