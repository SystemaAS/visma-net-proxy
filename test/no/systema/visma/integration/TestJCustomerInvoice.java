package no.systema.visma.integration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.jservices.common.dao.VistranskDao;
import no.systema.visma.dto.VistranskHeadDto;
import no.systema.visma.dto.VistranskTransformer;
import no.systema.visma.v1client.RFC3339DateFormat;
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
	
	@Test
	public void testCustomerInvoiceSync() {
		
		List<VistranskHeadDto> list = VistranskTransformer.transform( getList() );
		

		customerInvoice.syncronize(list.get(0));
		
	}
	
	
	private List<VistranskDao> getList() {
		List<VistranskDao> list = new ArrayList<VistranskDao>();
		
		list.add(getVistranskDao(10, 100, 1, "T-shirt"));
//		list.add(getVistranskDao(1, 100, 2, "Hat"));
		
		return list;
		
	}
	
	
	private VistranskDao getVistranskDao(int recnr, int bilnr, int posnr, String biltxt) {
		VistranskDao dao = new VistranskDao();
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
//		   vatCategoryId: 32
//		    description: Utgående mva, middels sats - rå fisk
		dao.setMomsk("32");
		dao.setKonto(3000);		
		dao.setKbarer(1000);
		dao.setBetbet("14");
		dao.setPeraar(2018);
		dao.setPernr(5);
		
		return dao;
	}	
	
	@Test
	public void testDate() {
		//https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
		
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd"); 		
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		
		logger.info("now, NO timezone"+LocalDateTime.now());

	  LocalDateTime date = LocalDateTime.now();
	  String text = date.format(dateTimeFormatter);
	  LocalDateTime parsedDate = LocalDateTime.parse(text, dateTimeFormatter);
		
		logger.info("now, timezone"+LocalDateTime.now(ZoneId.systemDefault()));

		logger.info("parsedDate "+parsedDate );
		
		
		VistranskDao dao = getVistranskDao(0, 0,  0, null); 		
		LocalDateTime value = LocalDateTime.of(dao.getKrdaar(), dao.getKrdmnd(), dao.getKrddag(), 0, 0, 0, 0);	
		
		logger.info("value="+value);
		
		
		logger.info("value, format="+value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		RFC3339DateFormat format = new RFC3339DateFormat();
		
		StringBuffer sb = new StringBuffer();
		logger.info("format="+format.format(value, sb, null));
		
		
		
	}
	
	
	
}
