package no.systema.visma.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.VistranshDao;
import no.systema.visma.dto.VistranshHeadDto;
import no.systema.visma.dto.VistranshTransformer;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJJournalTransaction {
	static Logger logger = Logger.getLogger(TestJJournalTransaction.class);	
	
	@Autowired 
	JournalTransaction journalTransaction;
	

	@Test
	public void testGetJournalTransaction() {
		logger.info("dto="+journalTransaction.getJournalTransactionByBatchnr("000003"));
	}	
	

	
	@Test
	public void testJournalTransaction() throws HttpClientErrorException, RestClientException, IOException {
		List<VistranshHeadDto> list = VistranshTransformer.transform( getCreateList() );

		journalTransaction.syncronize(list.get(0));
		
	}	
	
	private List<VistranshDao> getCreateList() {
		List<VistranshDao> list = new ArrayList<VistranshDao>();
		
		list.add(getVistranshDao(303, 1, "description", "F"));
		list.add(getVistranshDao(303, 2, "Nice %&# åäö", "K"));
		
		return list;
		
	}	

	private VistranshDao getVistranshDao(int bilnr, int posnr, String biltxt, String fakkre) {
		VistranshDao dao = new VistranshDao();
		dao.setFirma("SY");
		dao.setBilnr(bilnr);
		dao.setPosnr(posnr);
		dao.setBiltxt(biltxt);
		dao.setAktkod("A");
		dao.setKrdaar(2018);
		dao.setKrdmnd(9);
		dao.setKrddag(3);
		dao.setMomsk("0");
		dao.setKontov(3000);		
		dao.setKsted(3); //avd 
		dao.setNbelpo(new BigDecimal(15.0));
		dao.setPeraar(2018);
		dao.setPernr(9);
		dao.setFakkre(fakkre);
		dao.setPath("/Users/fredrikmoller/git/visma-net-proxy/test/headf.pdf");
		dao.setValkox("SEK");
		dao.setValku1(new BigDecimal(0.925));
		
		return dao;
	}	
	
	
}
