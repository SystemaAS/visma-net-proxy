package no.systema.visma.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.*;
import org.junit.Assert;
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
import no.systema.visma.v1client.model.JournalTransactionDto;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJJournalTransaction {
	static Logger logger = LoggerFactory.getLogger(TestJJournalTransaction.class);	
	
	@Autowired 
	JournalTransaction journalTransaction;
	

//	@Test
//	public void testGetAllJournalTransactions() {
//		List<JournalTransactionDto> list = journalTransaction.journalTransactionGetAllJournalTransactions();
//
//		list.forEach(dto -> {
//			logger.info("dto.getBatchNumber()=" + dto.getBatchNumber());
//			logger.info("executing getSpecific, dto="+journalTransaction.getJournalTransactionByBatchnr(dto.getBatchNumber()));
//		});
//
//	}

//	@Test
//	public void testGetJournalTransactionsToRelease() {
//		JournalTransactionDto dtoToRelease = journalTransaction.getJournalTransactiontoRelease("303"); 
//		logger.info("dto="+ dtoToRelease);
////		list.forEach(dto -> {
////			logger.info("batchNumber="+ dto.getBatchNumber()+", description="+ dto.getDescription()+", status="+dto.getStatus());
//////			logger.info("dto="+ dto);
////			
////		});
////	
//	
//	}	
	

	@Test
	public void testTransformerForTwoRowsGrouped() throws HttpClientErrorException, RestClientException, IOException {
		List<VistranshHeadDto> list = VistranshTransformer.transform( getCreateList() );

		list.forEach(dto -> {
			Assert.assertEquals("JournalTransaction expect one credit and one debit.",2,dto.getLines().size());
			Assert.assertNotSame(dto.getLines().get(0).getFakkre(), dto.getLines().get(1).getFakkre()); 
		});
		
	}	
	

	@Test
	public void testSummary() {
		List<VistranshHeadDto> headDtolist = VistranshTransformer.transform( getCreateList() );

		
		headDtolist.forEach((headDto) -> {
	
			DoubleSummaryStatistics F = headDto.getLines().stream()
				.filter(dto -> dto.getFakkre() == "F")
				.collect( Collectors.summarizingDouble( dto -> Double.valueOf(dto.getNbelpo().doubleValue())) );
			
			DoubleSummaryStatistics K = headDto.getLines().stream()
					.filter(dto -> dto.getFakkre() == "K")
					.collect( Collectors.summarizingDouble( dto -> Double.valueOf(dto.getNbelpo().doubleValue())) );	
			
	
			logger.info("sumF="+F);
			logger.info("sumK="+K);			
			
			
			logger.info("On bilnr:"+headDto.getBilnr()+ "F sum:"+F.getSum()+" K sum "+ K.getSum());
			logger.info("compare= "+Double.compare(F.getSum(), K.getSum()));			
			
			
			Assert.assertEquals("Must balance to 0" ,F.getSum(), K.getSum(), 0); 
		
		});
		
		
	}
	
	
	@Test
	public void testSummaryInvalid() {
		List<VistranshHeadDto> headDtolist = VistranshTransformer.transform( getCreateInvalidList() );

		
		headDtolist.forEach((headDto) -> {
	
			DoubleSummaryStatistics F = headDto.getLines().stream()
				.filter(dto -> dto.getFakkre() == "F")
				.collect( Collectors.summarizingDouble( dto -> Double.valueOf(dto.getNbelpo().doubleValue())) );
			
			DoubleSummaryStatistics K = headDto.getLines().stream()
					.filter(dto -> dto.getFakkre() == "K")
					.collect( Collectors.summarizingDouble( dto -> Double.valueOf(dto.getNbelpo().doubleValue())) );	
			
	
			Assert.assertNotEquals("On bilnr:"+headDto.getBilnr(), F.getSum(), K.getSum(), 0); 
		
		});
		
		
	}
	
	
	
	
	
	
//	@Test(expected=RuntimeException.class )
//	public void testTransformerForTwoRowsGroupedInvalidLess() throws HttpClientErrorException, RestClientException, IOException {
//		List<VistranshHeadDto> list = VistranshTransformer.transform( getCreateInvalidListLess() );
//
//	}	
//
//	@Test(expected=RuntimeException.class )
//	public void testTransformerForTwoRowsGroupedInvalidMore() throws HttpClientErrorException, RestClientException, IOException {
//		List<VistranshHeadDto> list = VistranshTransformer.transform( getCreateInvalidListMore() );
//		
//	}	
//	
//	
//	@Test(expected=RuntimeException.class )
//	public void testTransformerForTwoRowsGroupedInvalidAmount() throws HttpClientErrorException, RestClientException, IOException {
//		List<VistranshHeadDto> list = VistranshTransformer.transform( getCreateInvalidAmountList() );
//
//		list.forEach(dto -> {
//			Assert.assertEquals("JournalTransaction expect one credit and one debit.",2,dto.getLines().size());
//			Assert.assertSame(dto.getLines().get(0).getNbelpo(), dto.getLines().get(1).getNbelpo()); 
//		});
//		
//	}	
	
	@Test
	public void testJournalTransaction() throws HttpClientErrorException, RestClientException, IOException {
		List<VistranshHeadDto> list = VistranshTransformer.transform( getCreateList() );

		journalTransaction.syncronize(list.get(0));
		
	}	
	
	private List<VistranshDao> getCreateList() {
		List<VistranshDao> list = new ArrayList<VistranshDao>();
		
		list.add(getVistranshDao(303, 1, "description", "F", new BigDecimal(15.0)));
		list.add(getVistranshDao(303, 2, "Nice %&# åäö", "K", new BigDecimal(15.0)));
		
		return list;
		
	}	

	private List<VistranshDao> getCreateInvalidList() {
		List<VistranshDao> list = new ArrayList<VistranshDao>();
		
		list.add(getVistranshDao(303, 1, "description", "F", new BigDecimal(15.0)));
		list.add(getVistranshDao(303, 2, "Nice %&# åäö", "K", new BigDecimal(19.0)));
		
		list.add(getVistranshDao(404, 1, "Nice %&# åäö", "K", new BigDecimal(25.0)));
		
		return list;
		
	}	

	private List<VistranshDao> getCreateInvalidList2() {
		List<VistranshDao> list = new ArrayList<VistranshDao>();
		
		list.add(getVistranshDao(303, 1, "description", "F", new BigDecimal(15.0)));
		list.add(getVistranshDao(303, 2, "Nice %&# åäö", "K", new BigDecimal(15.0)));
		
		list.add(getVistranshDao(404, 1, "Nice %&# åäö", "K", new BigDecimal(25.0)));
		list.add(getVistranshDao(404, 2, "Nice %&# åäö", "K", new BigDecimal(25.0)));
		list.add(getVistranshDao(404, 3, "Nice %&# åäö", "F", new BigDecimal(35.0)));

		
		return list;
		
	}	
	
	private List<VistranshDao> getCreateInvalidList3() {
		List<VistranshDao> list = new ArrayList<VistranshDao>();
		
		list.add(getVistranshDao(303, 1, "description", "F", new BigDecimal(15.0)));
		list.add(getVistranshDao(303, 2, "Nice %&# åäö", "K", new BigDecimal(15.25)));
		
		return list;
		
	}		
	
	private VistranshDao getVistranshDao(int bilnr, int posnr, String biltxt, String fakkre, BigDecimal nbelpo) {
		VistranshDao dao = new VistranshDao();
		dao.setFirma("SY");
		dao.setBilnr(bilnr);
		dao.setPosnr(posnr);
		dao.setBiltxt(biltxt);
		dao.setAktkod("A");
		dao.setBilaar(2018);
		dao.setBilmnd(9);
		dao.setBildag(3);
		dao.setMomsk("0");
		dao.setKontov(3000);		
		dao.setKsted(3); //avd 
		dao.setNbelpo(nbelpo);
		dao.setPeraar(2018);
		dao.setPernr(9);
		dao.setFakkre(fakkre);
		dao.setPath("/Users/fredrikmoller/git/visma-net-proxy/test/mr_bean.pdf");
		dao.setValkox("SEK");
		dao.setValku1(new BigDecimal(0.925));
		
		return dao;
	}	
	
	
}
