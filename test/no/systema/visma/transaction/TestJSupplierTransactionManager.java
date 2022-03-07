package no.systema.visma.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.jakewharton.fliptables.FlipTableConverters;

import no.systema.jservices.common.dao.VisleveDao;
import no.systema.jservices.common.dao.services.VisleveDaoService;
import no.systema.visma.dto.PrettyPrintVisleveError;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJSupplierTransactionManager {

	private static Logger logger = LoggerFactory.getLogger(CustomerTransactionManager.class);	
	
	@Autowired
	SupplierTransactionManager transactionManager;
	
	@Autowired
	VisleveDaoService visleveDaoService;

	
	@Test
	public void testSyncSupplierFromDB() {
		
		List<PrettyPrintVisleveError> errorList = transactionManager.syncronizeSuppliers();
		logger.info(FlipTableConverters.fromIterable(errorList, PrettyPrintVisleveError.class));
		
	}	
	
	

	@Test
	public void testSyncSupplierValid() {

		setupValid();
		
		List<PrettyPrintVisleveError> errorList = transactionManager.syncronizeSuppliers();
		logger.info("Empty(prettyprint):");
		logger.info(FlipTableConverters.fromIterable(errorList, PrettyPrintVisleveError.class));

		assertResultValid();
		
		//cleanup
		visleveDaoService.deleteAll(null);
		
	}
	
	@Test
	public void testSyncSupplier_InValid() {
		setupInValid();
		
		List<PrettyPrintVisleveError> errorList = transactionManager.syncronizeSuppliers();
		assertEquals(1, errorList.size());
		System.out.println("NOT Empty(vislevedao):");
		List<VisleveDao> daoErrorList = visleveDaoService.findAll(null);
		System.out.println(FlipTableConverters.fromIterable(daoErrorList, VisleveDao.class));
		
		assertResultInValid();		
		
		//cleanup
		visleveDaoService.deleteAll(null);
		
	}	
	
	
	@Test(expected=AssertionError.class)
	public void testSyncSupplier1Run_InValidNotExistInVisma() {  //Manully delete selected kunde in Visma.net before run
		
		setupValid();

		List<PrettyPrintVisleveError> errorList = transactionManager.syncronizeSuppliers();
		System.out.println("Empty:");
		System.out.println(FlipTableConverters.fromIterable(errorList, PrettyPrintVisleveError.class));

		assertResultValid();

		setupInValid();

		errorList = transactionManager.syncronizeSuppliers();
		assertEquals(1, errorList.size());
		System.out.println("NOT Empty:");
		List<VisleveDao> daoErrorList = visleveDaoService.findAll(null);
		System.out.println(FlipTableConverters.fromIterable(daoErrorList, VisleveDao.class));

		assertResultInValid();		
		
		//cleanup
		visleveDaoService.deleteAll(null);
		
	}	
	
	private void setupValid() {
		if (visleveDaoService.findAll(null).isEmpty()) {
			getValidVisleveDaos().forEach((vk) ->{
				visleveDaoService.create(vk);
			});
			
		}
	}
	
	private void setupInValid() {
		if (visleveDaoService.findAll(null).isEmpty()) {
			getInValidVisleveDaos().forEach((vk) ->{
				visleveDaoService.create(vk);
			});
			
		}

	}	
	
	private void assertResultValid() {
		//1. check that viskunde is empty
		getValidVisleveDaos().forEach((vk) ->{
			assertNull(visleveDaoService.find(vk));
		});
	}	
	
	
	private void assertResultInValid() {
		assertTrue(!getInValidVisleveDaos().isEmpty());
	}	

	private List<VisleveDao> getInValidVisleveDaos() {
		List<VisleveDao> list = getValidVisleveDaos();
		//Invalidate one
		list.get(0).setBetbet("33");
		
		return list;
	}

	private List<VisleveDao> getValidVisleveDaos() {
		List<VisleveDao> list = new ArrayList<VisleveDao>();
		
		VisleveDao dao = new VisleveDao();
		dao.setFirma("SY"); //private String firma;
		dao.setLevnr(10); //private int levnr; //key
		dao.setAktkod("A");  // private String aktkod;
		dao.setLnavn("lnavn ,"+LocalTime.now()); //;private String lnavn;
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
		dao.setBetbet("30"); //private String betbet;
		dao.setBetmat("b"); //private String betmat;
		dao.setTfaxnr("tfaxnr"); //private String tfaxnr;
		dao.setLand("NO"); //private String land;
		dao.setAdr21("adr21"); //private String adr21;

		VisleveDao dao2 = new VisleveDao();
		dao2.setFirma("SY"); //private String firma;
		dao2.setLevnr(20); //private int levnr; //key
		dao2.setAktkod("A");  // private String aktkod;
		dao2.setLnavn("lnavn, "+LocalTime.now()); //;private String lnavn;
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
		dao2.setBetbet("10"); //private String betbet;
		dao2.setBetmat("b"); //private String betmat;
		dao2.setTfaxnr("tfaxnr"); //private String tfaxnr;
		dao2.setLand("NO"); //private String syland;
		
		list.add(dao);		
		list.add(dao2);
		
		return list;
		
	}

	
	
	
}
