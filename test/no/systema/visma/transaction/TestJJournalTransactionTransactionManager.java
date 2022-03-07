package no.systema.visma.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.jakewharton.fliptables.FlipTableConverters;

import no.systema.jservices.common.dao.VistranshDao;
import no.systema.jservices.common.dao.services.VistranshDaoService;
import no.systema.visma.dto.PrettyPrintVistranslError;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJJournalTransactionTransactionManager {

	private static Logger logger = LoggerFactory.getLogger(CustomerTransactionManager.class);	
	
	@Autowired
	JournalTransactionTransactionManager transactionManager;
	
	@Autowired
	VistranshDaoService vistranshDaoService;

	
	@Test
	public void testSyncJournalTransactionFromDB() {
		
		List<PrettyPrintVistranslError> errorList = transactionManager.syncronizeJournalTransaction();
		logger.info(FlipTableConverters.fromIterable(errorList, PrettyPrintVistranslError.class));
		
	}	
	
	@Test
	public void testSyncJournalTransactionValid() {

//		vistranshDaoService.deleteAll(null);
		
		
		setupValid();
		
		List<PrettyPrintVistranslError> errorList = transactionManager.syncronizeJournalTransaction();
		logger.info(FlipTableConverters.fromIterable(errorList, PrettyPrintVistranslError.class));

		assertResultValid();
		
		assertEquals("Should not contains errors", 0, errorList.size());
		
	}
	
	private void setupValid() {
		getValidVistranshDaos().forEach((vk) -> {
			vistranshDaoService.create(vk);
		});
	}
	
	private void assertResultValid() {
		getValidVistranshDaos().forEach((vk) ->{
			assertNull(vistranshDaoService.find(vk));
		});
	}	
	
	private List<VistranshDao> getValidVistranshDaos() {
		List<VistranshDao> list = new ArrayList<VistranshDao>();
		VistranshDao dao = new VistranshDao();		
		
		dao.setFirma("SY");
		dao.setBilnr(111);
		dao.setPosnr(1);
		dao.setBilaar(2018);
		dao.setBilmnd(9);
		dao.setBildag(5);
		dao.setPeraar(2018);
		dao.setPernr(9);
		dao.setBiltxt("biltxt...");
		dao.setKontov(3010);
		dao.setKsted(0); //avd
		dao.setNbelpo(new BigDecimal(99.055));
		dao.setFakkre("F");

		
		VistranshDao dao2 = new VistranshDao();		
		
		dao2.setFirma("SY");
		dao2.setBilnr(111);
		dao2.setPosnr(2);
		dao2.setBilaar(2018);
		dao2.setBilmnd(9);
		dao2.setBildag(5);
		dao.setPeraar(2018);
		dao.setPernr(9);		
		dao2.setBiltxt("biltxt...");
		dao2.setKontov(2010);
		dao2.setKsted(0); //avd
		dao2.setNbelpo(new BigDecimal(23.055));
		dao2.setFakkre("K");
		
		list.add(dao);		
		list.add(dao2);
		
		return list;
		
	}

	
	
	
}
