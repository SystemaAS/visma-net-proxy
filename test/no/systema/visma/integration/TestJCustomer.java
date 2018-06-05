package no.systema.visma.integration;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.visma.v1client.model.CustomerDto;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJCustomer {

	static Logger logger = Logger.getLogger(TestJCustomer.class);	
	
	LocalDateTime now;
	
	@Autowired 
	Customer customer;

	@Before
	public void setUp() throws Exception {
		now = LocalDateTime.now();
	}	
	
	
	@Test
	public void testGetCustomer() {
		
		logger.debug("dto="+customer.getGetBycustomerCd("10"));

	}	

	@Test(expected=HttpClientErrorException.class)  //Postcode value
	public void testCustomerSyncSmall() {
		String name = "Kalles chokladfabrik XXX";
		ViskundeDao dao =getInvalidDao(10, name);
		
		customer.syncronize(dao);

	}
	
	@Test
	public void testCustomerSync() {
		int kundnr = 100; 
		String name = "Kalles chokladfabrik"+now;
		ViskundeDao dao =getValidDao(kundnr, "123999" ,name);

		customer.syncronize(dao);
		
		CustomerDto dto = customer.getGetBycustomerCd("100");
		
		assertNotNull(dto);
		assertEquals("", name, dto.getName());
		
	}

	private ViskundeDao getInvalidDao(int kundnr, String name) {
		ViskundeDao dao = new ViskundeDao();
		dao.setKundnr(kundnr);
		dao.setKnavn(name);
		dao.setAktkod("A"); //I
		return dao;
	}		
	
	private ViskundeDao getValidDao(int kundnr, String syrg, String name) {
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
	
}
