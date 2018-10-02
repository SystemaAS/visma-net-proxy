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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.VistranslDao;
import no.systema.visma.dto.VistranslHeadDto;
import no.systema.visma.dto.VistranslTransformer;
import no.systema.visma.v1client.model.SupplierInvoiceDto;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJSupplierInvoice {

	private static Logger logger = Logger.getLogger(TestJSupplierInvoice.class);	

	String desc;
	LocalDateTime now;
	
	@Autowired 
	SupplierInvoice supplierInvoice;
	
	@Before
	public void setUp() throws Exception {
		now = LocalDateTime.now();
		desc = "Go bananas"+now;
	}

	@Test
	public void testGetSupplierInvoice() {
		SupplierInvoiceDto dto = supplierInvoice.getByinvoiceNumber("20");
		
		logger.debug("dto="+dto);

	}	

//	@Test
//	public void testAttachCreditNote() throws IOException{
//		
////		Resource file = getTestFile();
//		Resource file = getPdfFile();
//		
//		assertNotNull(file);
//		supplierInvoice.attachInvoiceFile("31", file);
//		
//	}		
	
	@Test
	public void testReleaseInvoice() throws IOException{
		
		supplierInvoice.releaseInvoice("22");
		
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
	
//	@Test
//	public void testSupplierInvoiceSyncCreate() {
//		List<VistranslHeadDto> list = VistranslTransformer.transform( getCreateList() );
//
//		supplierInvoice.syncronize(list.get(0));
//		
//		SupplierInvoiceDto dto = supplierInvoice.getByinvoiceNumber(String.valueOf(list.get(0).getBilnr()));
//		assertEquals("Should be same", cost, dto.getInvoiceLines().get(0).getCostInCurrency());
//		logger.debug("dto="+dto);		
//
//		assertTrue("Should be 2 lines as defined in getCreateList.", dto.getInvoiceLines().size() == 2);
//		
//		supplierInvoice.supplierInvoiceDeleteByinvoiceNumber(dto);
//
//		dto = supplierInvoice.getByinvoiceNumber(String.valueOf(list.get(0).getBilnr()));
//		assertNull("Should not exist in Visma.net", dto);
//		
//		
//	}

	
	
	@Test
	public void testSupplierInvoiceInsert() throws HttpClientErrorException, RestClientException, IOException {
		List<VistranslHeadDto> list = VistranslTransformer.transform( getCreateList() );

		supplierInvoice.syncronize(list.get(0));
		
	}
	

	private List<VistranslDao> getCreateList() {
		List<VistranslDao> list = new ArrayList<VistranslDao>();
		
		list.add(getVistranslDao(101, 303, 1, desc));
		list.add(getVistranslDao(101, 303, 2, "Nice %&# åäö"));
		
		return list;
		
	}	
	
	private List<VistranslDao> getUpdateList() {
		List<VistranslDao> list = new ArrayList<VistranslDao>();
		
		list.add(getVistranslDao(10, 100, 1, desc));
		
		return list;
		
	}

	private VistranslDao getVistranslDao(int resnr, int bilnr, int posnr, String biltxt) {
		VistranslDao dao = new VistranslDao();
		dao.setFirma("SY");
		dao.setResnr(resnr);
		dao.setBilnr(bilnr);
		dao.setPosnr(posnr);
		dao.setBiltxt(biltxt);
		dao.setAktkod("A");
		dao.setKrdaar(2018);
		dao.setKrdmnd(10);
		dao.setKrddag(02);
		dao.setFfdaar(2018);
		dao.setFfdmnd(10);
		dao.setFfddag(02);	
		dao.setMomsk("0");
		dao.setKontov(3000);		
		dao.setKsted(3); //avd 
		dao.setBetbet("14");
		dao.setNbelpo(new BigDecimal(15.0));
		dao.setPeraar(2018);
		dao.setPernr(8);
		dao.setLkid("123456789");
		dao.setKrnr("987654321");
		dao.setFakkre("K");
		dao.setPath("/Users/fredrikmoller/git/visma-net-proxy/test/mr_bean.pdf");
		dao.setValkox("SEK");
		dao.setValku1(new BigDecimal(0.925));
		
		return dao;
	}	
	
}
