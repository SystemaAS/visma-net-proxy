package no.systema.visma.integration;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.jservices.common.dao.VistranskDao;

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
	

//	@Test
//	public void testGetCustomerInvoice() {
//		CustomerInvoiceDto dto = customerInvoice.getByinvoiceNumber("987");
//		
//		logger.debug("dto="+dto);
//		
//		assertNotNull(dto);
//
//	}	

	@Test
	public void testAttachInvoice() throws IOException{
		
		Resource file = getPdfFile();
		
		logger.info("file exist="+file.exists());
		logger.info("file contentlenght="+file.contentLength());
		
		assertNotNull(file);
//		Object obj = customerInvoice.attachInvoiceFile("20", file);
//		logger.debug("obj="+obj);
		
	}	
	
	
	@Test
	public void testAttachCreditNote() throws IOException{
		
		Resource file = getTestFile();
		
		assertNotNull(file);
		customerInvoice.attachCreditNoteFile("987", file);
		
	}		
	
	
	
//	@Test
//	public void testCustomerInvoiceSyncCreate() {
//		List<VistranskHeadDto> list = VistranskTransformer.transform( getCreateList() );
//
//		customerInvoice.syncronize(list.get(0));
//		
//		CustomerInvoiceDto dto = customerInvoice.getByinvoiceNumber(String.valueOf(list.get(0).getBilnr()));
//		assertEquals("Should be same", desc, dto.getInvoiceLines().get(0).getDescription());
//		logger.debug("dto="+dto);		
//
//		assertTrue("Should be 2 lines as defined in getCreateList.", dto.getInvoiceLines().size() == 2);
//		
//		
//		
//	}

	private List<VistranskDao> getCreateList() {
		List<VistranskDao> list = new ArrayList<VistranskDao>();
		
		list.add(getVistranskDao(10, 200, 1, desc));
		list.add(getVistranskDao(10, 200, 2, "Nice %&# åäö"));
		
		return list;
		
	}	
	
	
//	@Test(expected=RuntimeException.class) //Updates not allowed
//	public void testCustomerInvoiceSyncUpdate() {
//		List<VistranskHeadDto> list = VistranskTransformer.transform( getUpdateList() );
//
//		customerInvoice.syncronize(list.get(0));
//		
//		CustomerInvoiceDto dto = customerInvoice.getByinvoiceNumber(String.valueOf(list.get(0).getBilnr()));
//		assertEquals("Should be same", desc, dto.getInvoiceLines().get(0).getDescription());
//		logger.debug("dto="+dto);		
//		
//	}

	private List<VistranskDao> getUpdateList() {
		List<VistranskDao> list = new ArrayList<VistranskDao>();
		
		list.add(getVistranskDao(10, 100, 1, desc));
		
		return list;
		
	}

	private VistranskDao getVistranskDao(int resnr, int bilnr, int posnr, String biltxt) {
		VistranskDao dao = new VistranskDao();
		dao.setFirma("SY");
		dao.setResnr(resnr);
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
		dao.setKontov(3000);		
		dao.setKsted(3);  // avd
		dao.setBetbet("14");
		dao.setBbelop(new BigDecimal(15.0));
		dao.setPeraar(2018);
		dao.setPernr(8);
		dao.setValkox("NOK");
		dao.setValku1(new BigDecimal(100.0));
		
		return dao;
	}	

	
    public static Resource getTestFile() throws IOException {
        Path testFile = Files.createTempFile("test-file", ".txt");
        StringBuilder txt = new StringBuilder("Hello World !!, This is a test file, generated:").append(LocalDateTime.now().toString());
        Files.write(testFile, txt.toString().getBytes());

        return new FileSystemResource(testFile.toFile());
    }	
	
    public static Resource getPdfFile() throws IOException {
    	 
    	File file =new File("/Users/fredrikmoller/git/visma-net-proxy/test/CloudNativeLandscape_v0.9.4.pdf");

    	logger.info("file.exists())= "+file.exists());
    	
    	return new FileSystemResource(file);	

    }	
    
    
	
}
