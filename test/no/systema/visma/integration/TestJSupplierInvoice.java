package no.systema.visma.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import no.systema.jservices.common.dao.VistranslDao;
import no.systema.visma.dto.VistranskHeadDto;
import no.systema.visma.dto.VistranskTransformer;
import no.systema.visma.dto.VistranslHeadDto;
import no.systema.visma.dto.VistranslTransformer;
import no.systema.visma.v1client.model.CustomerInvoiceDto;
import no.systema.visma.v1client.model.SupplierInvoiceDto;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJSupplierInvoice {

	private static Logger logger = Logger.getLogger(TestJSupplierInvoice.class);	

	String desc;
	BigDecimal cost;
	LocalDateTime now;
	
	@Autowired 
	SupplierInvoice supplierInvoice;
	
	@Before
	public void setUp() throws Exception {
		now = LocalDateTime.now();
		cost = new BigDecimal(15.0);
		desc = "Go bananas"+now;
	}

	@Test
	public void testGetSupplierInvoice() {
		SupplierInvoiceDto dto = supplierInvoice.getByinvoiceNumber("200001");
		
		logger.debug("dto="+dto);

	}	
	
	@Test
	public void testSupplierInvoiceSyncCreate() {
		List<VistranslHeadDto> list = VistranslTransformer.transform( getCreateList() );

		supplierInvoice.syncronize(list.get(0));
		
		SupplierInvoiceDto dto = supplierInvoice.getByinvoiceNumber(String.valueOf(list.get(0).getBilnr()));
		assertEquals("Should be same", cost, dto.getInvoiceLines().get(0).getCostInCurrency());
		logger.debug("dto="+dto);		

		assertTrue("Should be 2 lines as defined in getCreateList.", dto.getInvoiceLines().size() == 2);
		
//		supplierInvoice.supplierInvoiceDeleteByinvoiceNumber(dto);
//
//		dto = supplierInvoice.getByinvoiceNumber(String.valueOf(list.get(0).getBilnr()));
//		assertNull("Should not exist in Visma.net", dto);
		
		
	}

	
	
	@Test(expected=RuntimeException.class) //Updates not allowed
	public void testSupplierInvoiceSyncUpdate() {
		List<VistranslHeadDto> list = VistranslTransformer.transform( getUpdateList() );

		supplierInvoice.syncronize(list.get(0));
		
		SupplierInvoiceDto dto = supplierInvoice.getByinvoiceNumber(String.valueOf(list.get(0).getBilnr()));
		assertEquals("Should be same", desc, dto.getInvoiceLines().get(0).getTransactionDescription());
		logger.debug("dto="+dto);		
		
	}
	

	private List<VistranslDao> getCreateList() {
		List<VistranslDao> list = new ArrayList<VistranslDao>();
		
		list.add(getVistranslDao(50000, 200, 1, desc));
		list.add(getVistranslDao(50000, 200, 2, "Nice %&# åäö"));
		
		return list;
		
	}	
	
	
	
	private List<VistranslDao> getUpdateList() {
		List<VistranslDao> list = new ArrayList<VistranslDao>();
		
		list.add(getVistranslDao(10, 100, 1, desc));
		
		return list;
		
	}

	private VistranslDao getVistranslDao(int recnr, int bilnr, int posnr, String biltxt) {
		VistranslDao dao = new VistranslDao();
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
		dao.setKbarer(3);  //Kostnadsbarer avd
		dao.setProsnr(55); //Kostnadsbarer projekt
		dao.setBetbet("14");
		dao.setBbelop(cost);
		dao.setPeraar(2018);
		dao.setPernr(8);
		
		return dao;
	}	
	
}
