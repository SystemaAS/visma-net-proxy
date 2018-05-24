package no.systema.visma;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import no.systema.jservices.common.dao.VistranskDao;

public class TestJVistranskTransformer {

	@Before
	public void setUp() throws Exception {
	}



	@Test
	public void testGrouping() {

		List<VistranskHeadDto> list = VistranskTransformer.transform( getList() );
		assertEquals(list.size(), 2);
		
	
	}		
		
	
	private List<VistranskDao> getList() {
		List<VistranskDao> list = new ArrayList<VistranskDao>();
		
		list.add(getVistranskDao(1, 100, 1, "T-shirt"));
		list.add(getVistranskDao(1, 100, 2, "Hat"));
		
		list.add(getVistranskDao(2, 200, 1, "T-shirt XL"));
		list.add(getVistranskDao(2, 200, 2, "Hat XL"));
		
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
