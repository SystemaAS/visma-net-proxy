package no.systema.visma.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.jakewharton.fliptables.FlipTableConverters;

import no.systema.jservices.common.dao.VistranslDao;
import no.systema.jservices.common.dao.services.VistranslDaoService;
import no.systema.visma.dto.PrettyPrintVistranslError;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJSupplierInvoiceTransactionManager {

	private static Logger logger = LoggerFactory.getLogger(CustomerTransactionManager.class);	
	
	@Autowired
	SupplierInvoiceTransactionManager transactionManager;
	
	@Autowired
	VistranslDaoService vistranslDaoService;

	
	@Test
	public void testSyncSupplierFromDB() {
		
		List<PrettyPrintVistranslError> errorList = transactionManager.syncronizeSupplierInvoices();
		logger.info(FlipTableConverters.fromIterable(errorList, PrettyPrintVistranslError.class));
		
	}	
	
	@Test
	public void testSyncSupplierInvoiceValid() {

		vistranslDaoService.deleteAll(null);
		
		
		setupValid();
		
		List<PrettyPrintVistranslError> errorList = transactionManager.syncronizeSupplierInvoices();
		logger.info(FlipTableConverters.fromIterable(errorList, PrettyPrintVistranslError.class));

		assertResultValid();
		
		assertEquals("Should not contains errors", 0, errorList.size());
		
	}
	
	private void setupValid() {
		getValidVistranslDaos().forEach((vk) -> {
			vistranslDaoService.create(vk);
		});
	}
	
	private void assertResultValid() {
		getValidVistranslDaos().forEach((vk) ->{
			assertNull(vistranslDaoService.find(vk));
		});
	}	
	
	private List<VistranslDao> getValidVistranslDaos() {
		List<VistranslDao> list = new ArrayList<VistranslDao>();
		VistranslDao dao = new VistranslDao();		
		
		dao.setFirma("SY");
		dao.setAktkod("A");
		dao.setResnr(50000);
		dao.setBilnr(111);
		dao.setPosnr(1);
		dao.setBilaar(2018);
		dao.setBilmnd(5);
		dao.setBildag(29);
		dao.setPeraar(2018);
		dao.setPernr(5);
		dao.setKrdaar(2018);
		dao.setKrdmnd(5);
		dao.setKrddag(29);
		dao.setFfdaar(2018);
		dao.setFfdmnd(5);
		dao.setFfddag(25);	
		dao.setBiltxt("T-shirt");
		dao.setBetbet("14");
		dao.setKontov(3000);
		dao.setKsted(100);
		dao.setKbarer(444);
		dao.setMomsk("3");  //TEGN 1

		
		VistranslDao dao2 = new VistranslDao();		
		
		dao2.setFirma("SY");
		dao2.setAktkod("A");
		dao2.setResnr(50000);
		dao2.setBilnr(111);
		dao2.setPosnr(2);
		dao2.setBilaar(2018);
		dao2.setBilmnd(5);
		dao2.setBildag(29);
		dao2.setPeraar(2018);
		dao2.setPernr(5);
		dao2.setKrdaar(2018);
		dao2.setKrdmnd(5);
		dao2.setKrddag(29);
		dao2.setFfdaar(2018);
		dao2.setFfdmnd(5);
		dao2.setFfddag(25);	
		dao2.setBiltxt("Hat");
		dao2.setBetbet("14");
		dao2.setKontov(3000);
		dao2.setKsted(100);
		dao2.setKbarer(444);
		dao2.setMomsk("3");  //TEGN 1		
		
		list.add(dao);		
		list.add(dao2);
		
		return list;
		
	}

	
	
	
}
