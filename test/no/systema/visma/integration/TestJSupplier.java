package no.systema.visma.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import no.systema.jservices.common.dao.VisleveDao;
import no.systema.visma.v1client.api.SupplierApi;
import no.systema.visma.v1client.model.SupplierDto;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJSupplier {

	static Logger logger = Logger.getLogger(TestJSupplier.class);	
	
	LocalDateTime now;
	
	@Autowired 
	Supplier supplier;
	
	@Before
	public void setUp() throws Exception {
		now = LocalDateTime.now();
	}	
	
	
	@Test
	public void testGetSupplier() {
		
		logger.debug("dto="+supplier.getGetBysupplierCd("10"));

	}	
	
	
	@Test
	public void testGetAllSupplier() {
		
		logger.debug("all="+supplier.supplierGetAll());

	}	
	
	

	@Test(expected=HttpClientErrorException.class)  //Postcode value
	public void testSupplierSyncSmall() {
		String name = "Göteborg kexfabrik XXX";
		VisleveDao dao =getInvalidDao(10, name);
		
		supplier.syncronize(dao);

	}
	
	@Test
	public void testSupplierSync() {
		int levnr = 10; 
		String name = "Göteborg kexfabrik; "+now;
		VisleveDao dao =getValidDao(levnr, name);

		supplier.syncronize(dao);
		
		SupplierDto dto = supplier.getGetBysupplierCd(String.valueOf(levnr));
		
		assertNotNull(dto);
		assertEquals("", name, dto.getName());
		
	}

	private VisleveDao getInvalidDao(int levnr, String name) {
		VisleveDao dao = new VisleveDao();
		dao.setLevnr(levnr);
		dao.setLnavn(name);
		dao.setAktkod("A"); //I
		
		return dao;
	}		
	
	private VisleveDao getValidDao(int levnr,  String name) {
		VisleveDao dao = new VisleveDao();
		dao.setLevnr(levnr);
		dao.setLnavn(name);
		dao.setAdr1("adr1");
		dao.setAdr2("adr2");
		dao.setAdr3("adr3");
		dao.setLand("NO");
		dao.setPostnr(6001);
		dao.setAktkod("A"); //I
		
		return dao;
	}	
	
}
