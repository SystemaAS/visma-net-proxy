package no.systema.visma.transaction;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.jakewharton.fliptables.FlipTableConverters;

import no.systema.jservices.common.dao.VistranskDao;
import no.systema.jservices.common.dao.services.VistranskDaoService;
import no.systema.visma.dto.PrettyPrintVistranskError;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJCustomerInvoiceTransactionManager {

	private static Logger logger = Logger.getLogger(CustomerTransactionManager.class);	
	
	@Autowired
	CustomerInvoiceTransactionManager transactionManager;
	
	@Autowired
	VistranskDaoService vistranskDaoService;

	
	@Test
	public void testSyncCustomerFromDB() {
		
		List<PrettyPrintVistranskError> errorList = transactionManager.syncronizeCustomerInvoices();
		logger.info(FlipTableConverters.fromIterable(errorList, PrettyPrintVistranskError.class));
		
	}	
	
	@Test
	public void clean() {

		vistranskDaoService.deleteAll(null);
		
	}	
	
	

	@Test
	public void testSyncCustomerValid() {

		vistranskDaoService.deleteAll(null);
		
		
		setupValid();
		
		List<PrettyPrintVistranskError> errorList = transactionManager.syncronizeCustomerInvoices();
		logger.info(FlipTableConverters.fromIterable(errorList, PrettyPrintVistranskError.class));

		assertResultValid();
		
		assertEquals("Should not contains errors", 0, errorList.size());
		
	}
	
	private void setupValid() {
		getValidVistranskDaos().forEach((vk) -> {
			vistranskDaoService.create(vk);
		});
	}
	
	private void assertResultValid() {
		getValidVistranskDaos().forEach((vk) ->{
			assertNull(vistranskDaoService.find(vk));
		});
	}	
	
	private List<VistranskDao> getValidVistranskDaos() {
		List<VistranskDao> list = new ArrayList<VistranskDao>();
		VistranskDao dao = new VistranskDao();		
		
		dao.setFirma("SY");
		dao.setAktkod("A");
		dao.setRecnr(10);
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
		dao.setKonto(3000);
		dao.setKsted(100);
		dao.setKbarer(444);
		dao.setMomsk("3");  //TEGN 1
		
		list.add(dao);		
//		list.add(dao2);
		
		return list;
		
	}

	
	
	
}
