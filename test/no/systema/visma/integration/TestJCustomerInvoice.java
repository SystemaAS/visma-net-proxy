package no.systema.visma.integration;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.jservices.common.dao.VistranskDao;
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
	
//	@Test
//	public void testCustomerInvoiceSync() {
//		String name = "Kalles chokladfabrik (medium)";
//		VistranskDao dao =getMediumDao(10, "123999" ,name);
//
//		customerInvoice.syncronize(dao);
//		
//	}
	
	
	private List<VistranskDao> getList() {
		List<VistranskDao> list = new ArrayList<VistranskDao>();
		
		list.add(getVistranskDao(1, 100, 1, "T-shirt"));
		list.add(getVistranskDao(1, 100, 2, "Hat"));
		
		return list;
		
	}
	
	
	private VistranskDao getVistranskDao(int recnr, int bilnr, int posnr, String biltxt) {
		VistranskDao dao = new VistranskDao();
		dao.setRecnr(recnr);
		dao.setBilnr(bilnr);
		dao.setPosnr(posnr);
		dao.setBiltxt(biltxt);
		
		dao.setAktkod("A");

		
		return dao;
	}	
	
}
