package no.systema.visma.integration;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.jservices.common.dao.VistranskDao;
import no.systema.visma.dto.VistranskHeadDto;
import no.systema.visma.dto.VistranskTransformer;
import no.systema.visma.v1client.model.CustomerInvoiceDto;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJCustomerInvoice {

	private static Logger logger = Logger.getLogger(TestJCustomerInvoice.class);	

	String desc;
	LocalDateTime now;
	
	@Autowired 
	CustomerInvoice customerInvoice;
	
	@Before
	public void setUp() throws Exception {
		now = LocalDateTime.now();
		desc = "T-shirt"+now;
	}

	@Test
	public void testGetCustomerInvoice() {
		CustomerInvoiceDto dto = customerInvoice.getByinvoiceNumber("10");
		
		logger.debug("dto="+dto);

	}	
	
	@Test
	public void testCustomerInvoiceSyncCreateAndDelete() {
		List<VistranskHeadDto> list = VistranskTransformer.transform( getCreateList() );

		customerInvoice.syncronize(list.get(0));
		
		CustomerInvoiceDto dto = customerInvoice.getByinvoiceNumber(String.valueOf(list.get(0).getBilnr()));
		assertEquals("Should be same", desc, dto.getInvoiceLines().get(0).getDescription());
		logger.debug("dto="+dto);		

		assertTrue("Should be 2 lines as defined in getCreateList.", dto.getInvoiceLines().size() == 2);
		
		customerInvoice.customerInvoiceDeleteByinvoiceNumber(dto);

		dto = customerInvoice.getByinvoiceNumber(String.valueOf(list.get(0).getBilnr()));
		assertNull("Should not exist in Visma.net", dto);
		
		
	}

	private List<VistranskDao> getCreateList() {
		List<VistranskDao> list = new ArrayList<VistranskDao>();
		
		list.add(getVistranskDao(10, 200, 1, desc));
		list.add(getVistranskDao(10, 200, 2, "Nice %&# åäö"));
		
		return list;
		
	}	
	
	
	@Test
	public void testCustomerInvoiceSyncUpdate() {
		List<VistranskHeadDto> list = VistranskTransformer.transform( getUpdateList() );

		customerInvoice.syncronize(list.get(0));
		
		CustomerInvoiceDto dto = customerInvoice.getByinvoiceNumber(String.valueOf(list.get(0).getBilnr()));
		assertEquals("Should be same", desc, dto.getInvoiceLines().get(0).getDescription());
		logger.debug("dto="+dto);		
		
	}
	
	
	private List<VistranskDao> getUpdateList() {
		List<VistranskDao> list = new ArrayList<VistranskDao>();
		
		list.add(getVistranskDao(10, 100, 1, desc));
		
		return list;
		
	}

	private VistranskDao getVistranskDao(int recnr, int bilnr, int posnr, String biltxt) {
		VistranskDao dao = new VistranskDao();
		dao.setFirma("SY");
		dao.setRecnr(recnr);
		dao.setBilnr(bilnr);
		dao.setPosnr(posnr);
		dao.setBiltxt(biltxt);
		dao.setAktkod("A");
		dao.setKrdaar(2018);
		dao.setKrdmnd(5);
		dao.setKrddag(25);
		dao.setFfdaar(2018);
		dao.setFfdmnd(5);
		dao.setFfddag(25);	
		dao.setMomsk("32");
		dao.setKonto(3000);		
		dao.setKbarer(1000);
		dao.setBetbet("14");
		dao.setBbelop(new BigDecimal(15.0));
		dao.setPeraar(2018);
		dao.setPernr(5);
		
		return dao;
	}	
	
}
