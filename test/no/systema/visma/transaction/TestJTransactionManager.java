package no.systema.visma.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.jakewharton.fliptables.FlipTableConverters;

import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.jservices.common.dao.services.VissyskunDaoService;
import no.systema.visma.PrettyPrintViskundeError;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJTransactionManager {

	private static Logger logger = Logger.getLogger(TransactionManager.class);	
	
	@Autowired
	@Qualifier("tsManager")
	TransactionManager transactionManager;
	
	@Autowired
	ViskundeDaoService viskundeDaoService;
	
	@Autowired
	VissyskunDaoService vissyskunDaoService;	
	

	@Test
	public void testSyncCustomer2Runs_VAlidAndInValid() {
		//Prereq:
		//1. numbers correspond to viskunde-setup
		setupValid();
		List<PrettyPrintViskundeError> errorList = transactionManager.syncronizeCustomers();
		System.out.println("Empty(prettyprint):");
		System.out.println(FlipTableConverters.fromIterable(errorList, PrettyPrintViskundeError.class));
		assertResultValid();

		setupInValid();
		errorList = transactionManager.syncronizeCustomers();
		assertEquals(1, errorList.size());
		System.out.println("NOT Empty(viskundedao):");
		List<ViskundeDao> daoErrorList = viskundeDaoService.findAll(null);
		System.out.println(FlipTableConverters.fromIterable(daoErrorList, ViskundeDao.class));
		
		assertResultInValid();		
		
		//cleanup
		viskundeDaoService.deleteAll(null);
		
	}
	
	
	@Test(expected=AssertionError.class)
	public void testSyncCustomer1Run_InValidNotExistInVisma() {  //Manully delete selected kunde in Visma.net before run
		
		setupValid();
		List<PrettyPrintViskundeError> errorList = transactionManager.syncronizeCustomers();
		System.out.println("Empty:");
		System.out.println(FlipTableConverters.fromIterable(errorList, PrettyPrintViskundeError.class));
		assertResultValid();

		setupInValid();
		errorList = transactionManager.syncronizeCustomers();
		assertEquals(1, errorList.size());
		System.out.println("NOT Empty:");
		List<ViskundeDao> daoErrorList = viskundeDaoService.findAll(null);
		System.out.println(FlipTableConverters.fromIterable(daoErrorList, ViskundeDao.class));
		assertResultInValid();		
		
		//cleanup
		viskundeDaoService.deleteAll(null);
		
	}	
	
	private void setupValid() {
		if (viskundeDaoService.findAll(null).isEmpty()) {
			getValidViskundeDaos().forEach((vk) ->{
				viskundeDaoService.create(vk);
			});
			
		}
	}
	
	private void setupInValid() {
		if (viskundeDaoService.findAll(null).isEmpty()) {
			getInValidViskundeDaos().forEach((vk) ->{
				viskundeDaoService.create(vk);
			});
			
		}

	}	
	
	private void setupDuplicates() {
		if (viskundeDaoService.findAll(null).isEmpty()) {
			getDuplicatesViskundeDaos().forEach((vk) ->{
				viskundeDaoService.create(vk);
			});
		}
	}	
	
	private void assertResultValid() {
		//1. check that viskunde is empty
		getValidViskundeDaos().forEach((vk) ->{
			assertNull(viskundeDaoService.find(vk));
		});
		//2. check that vissyskund exist
		getValidViskundeDaos().forEach((vk) ->{
			assertNotNull(vissyskunDaoService.find(vk));
		});
		//TODO 3. Assert rollback
	}	
	
	
	private void assertResultInValid() {
		assertTrue(!getInValidViskundeDaos().isEmpty());
		//check that vissyskund exist
		getInValidViskundeDaos().forEach((vk) ->{
			assertNotNull(vissyskunDaoService.find(vk));
		});
		//TODO 3. Assert rollback
	}	

	private void assertResultDuplicates() {
		assertTrue(!getDuplicatesViskundeDaos().isEmpty());
		//check that not exist in vissyskund
		getDuplicatesViskundeDaos().forEach((vk) ->{
			assertNull(vissyskunDaoService.find(vk));
		});
		//TODO 3. Assert rollback
	}		
	
	private List<ViskundeDao> getInValidViskundeDaos() {
		List<ViskundeDao> list = getValidViskundeDaos();
		//Invalidate one
		list.get(0).setBetbet("33");
		
		return list;
	}

	private List<ViskundeDao> getDuplicatesViskundeDaos() {
		List<ViskundeDao> list = getValidViskundeDaos();
		ViskundeDao oneDao = list.get(0);
		
		ViskundeDao dupDao = oneDao;
		
		list.add(dupDao);
		
		return list;
	}	
	
	
	
	private List<ViskundeDao> getValidViskundeDaos() {
		List<ViskundeDao> list = new ArrayList<ViskundeDao>();
		
		ViskundeDao dao = new ViskundeDao();
		dao.setFirma("SY"); //private String firma;
		dao.setKundnr(10); //private int kundnr; //key
		dao.setAktkod("A");  // private String aktkod;
		dao.setDkund("d"); //private String dkund;
		dao.setKnavn("knavn ,"+LocalTime.now()); //;private String knavn;
		dao.setAdr1("adr1"); //;private String adr1;
		dao.setAdr2("adr2"); //private String adr2;
		dao.setPostnr(3333);  //private int postnr;
		dao.setAdr3("adr3"); //private String adr3;
		dao.setKpers("kpers"); //private String kpers;
		dao.setTlf("tlf");//private String tlf;
		dao.setSonavn("sonavn"); //private String sonavn;
		dao.setValkod("vkd"); //;private String valkod;
		dao.setSpraak("s"); //private String spraak;
		dao.setBankg("bankg");//private String bankg;
		dao.setPostg("postg"); //private String postg;
		dao.setFmot(100); //private int fmot;
		dao.setBetbet("30"); //private String betbet;
		dao.setBetmat("b"); //private String betmat;
		dao.setSfakt("s"); //private String sfakt;
		dao.setKgrens(999);  //private int kgrens;
		dao.setTfaxnr("tfaxnr"); //private String tfaxnr;
		dao.setSyregn(444); //private int syregn;
		dao.setSykont(111); //private int sykont;
		dao.setSylikv("s"); //private String sylikv;
		dao.setSyopdt("sy"); //private String syopdt;
		dao.setSyminu(new BigDecimal(1.1)); //;private BigDecimal syminu = new BigDecimal(0);
		dao.setSyutlp(new BigDecimal(2.2)); //private BigDecimal syutlp = new BigDecimal(0);
		dao.setSyrg("123456"); //private String syrg;
		dao.setSypoge("sypoge"); //private String sypoge;
		dao.setSystat("sys"); //private String systat;
		dao.setSyland("sy"); //private String syland;
		dao.setSyselg("sys"); //private String syselg;
		dao.setSyiat1(888); //private int syiat1;
		dao.setSyiat2(88); //private int syiat2;
		dao.setSycoty("s"); //private String sycoty;
		dao.setSyfr01("s"); //private String syfr01;
		dao.setSyfr02("s"); //private String syfr02;
		dao.setSyfr03("sy"); //private String syfr03;
		dao.setSyfr04("sy"); //private String syfr04;
		dao.setSyfr05("syf"); //private String syfr05;
		dao.setSyfr06("syf"); //private String syfr06;
		dao.setSysalu(2); //private int sysalu;
		dao.setSyepos("syepos"); //private String syepos;
		dao.setAknrku(400); //;private int aknrku;
		dao.setVatkku("vatkku"); //private String vatkku;
		dao.setXxbre(new BigDecimal(5.5)); //private BigDecimal xxbre = new BigDecimal(0);
		dao.setXxlen(new BigDecimal(6.6)); //private BigDecimal xxlen = new BigDecimal(0);
		dao.setXxinm3(new BigDecimal(7.7)); //private BigDecimal xxinm3 = new BigDecimal(0);
		dao.setXxinlm(new BigDecimal(8.8)); //private BigDecimal xxinlm = new BigDecimal(0);
		dao.setRnraku("rnraku"); //private String rnraku;
		dao.setGolk("golk"); //private String golk;
		dao.setKundgr("ku"); //private String kundgr;
		dao.setPnpbku("pnpbku"); //private String pnpbku;
		dao.setAdr21("adr21"); //private String adr21;
		dao.setEori("eori"); //private String eori;
		dao.setSymvjn("J"); //private String symvjn;
		dao.setSymvsp("J"); //private String symvsp;

		ViskundeDao dao2 = new ViskundeDao();
		dao2.setFirma("SY"); //private String firma;
		dao2.setKundnr(20); //private int kundnr; //key
		dao2.setAktkod("A");  // private String aktkod;
		dao2.setDkund("d"); //private String dkund;
		dao2.setKnavn("knavn, "+LocalTime.now()); //;private String knavn;
		dao2.setAdr1("adr1"); //;private String adr1;
		dao2.setAdr2("adr2"); //private String adr2;
		dao2.setPostnr(3333);  //private int postnr;
		dao2.setAdr3("adr3"); //private String adr3;
		dao2.setKpers("kpers"); //private String kpers;
		dao2.setTlf("tlf");//private String tlf;
		dao2.setSonavn("sonavn"); //private String sonavn;
		dao2.setValkod("vkd"); //;private String valkod;
		dao2.setSpraak("s"); //private String spraak;
		dao2.setBankg("bankg");//private String bankg;
		dao2.setPostg("postg"); //private String postg;
		dao2.setFmot(100); //private int fmot;
		dao2.setBetbet("10"); //private String betbet;
		dao2.setBetmat("b"); //private String betmat;
		dao2.setSfakt("s"); //private String sfakt;
		dao2.setKgrens(999);  //private int kgrens;
		dao2.setTfaxnr("tfaxnr"); //private String tfaxnr;
		dao2.setSyregn(444); //private int syregn;
		dao2.setSykont(111); //private int sykont;
		dao2.setSylikv("s"); //private String sylikv;
		dao2.setSyopdt("sy"); //private String syopdt;
		dao2.setSyminu(new BigDecimal(1.1)); //;private BigDecimal syminu = new BigDecimal(0);
		dao2.setSyutlp(new BigDecimal(2.2)); //private BigDecimal syutlp = new BigDecimal(0);
		dao2.setSyrg("123456"); //private String syrg;
		dao2.setSypoge("sypoge"); //private String sypoge;
		dao2.setSystat("sys"); //private String systat;
		dao2.setSyland("sy"); //private String syland;
		dao2.setSyselg("sys"); //private String syselg;
		dao2.setSyiat1(888); //private int syiat1;
		dao2.setSyiat2(88); //private int syiat2;
		dao2.setSycoty("s"); //private String sycoty;
		dao2.setSyfr01("s"); //private String syfr01;
		dao2.setSyfr02("s"); //private String syfr02;
		dao2.setSyfr03("sy"); //private String syfr03;
		dao2.setSyfr04("sy"); //private String syfr04;
		dao2.setSyfr05("syf"); //private String syfr05;
		dao2.setSyfr06("syf"); //private String syfr06;
		dao2.setSysalu(2); //private int sysalu;
		dao2.setSyepos("syepos"); //private String syepos;
		dao2.setAknrku(400); //;private int aknrku;
		dao2.setVatkku("vatkku"); //private String vatkku;
		dao2.setXxbre(new BigDecimal(5.5)); //private BigDecimal xxbre = new BigDecimal(0);
		dao2.setXxlen(new BigDecimal(6.6)); //private BigDecimal xxlen = new BigDecimal(0);
		dao2.setXxinm3(new BigDecimal(7.7)); //private BigDecimal xxinm3 = new BigDecimal(0);
		dao2.setXxinlm(new BigDecimal(8.8)); //private BigDecimal xxinlm = new BigDecimal(0);
		dao2.setRnraku("rnraku"); //private String rnraku;
		dao2.setGolk("golk"); //private String golk;
		dao2.setKundgr("ku"); //private String kundgr;
		dao2.setPnpbku("pnpbku"); //private String pnpbku;
		dao2.setAdr21("adr21"); //private String adr21;
		dao2.setEori("eori"); //private String eori;
		dao2.setSymvjn("J"); //private String symvjn;
		dao2.setSymvsp("J"); //private String symvsp;
		
		list.add(dao);		
		list.add(dao2);
		
		return list;
		
	}

	
	
	
}
