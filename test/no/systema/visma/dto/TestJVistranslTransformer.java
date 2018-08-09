package no.systema.visma.dto;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.jservices.common.dao.VistranslDao;
import no.systema.jservices.common.dao.services.VistranslDaoService;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJVistranslTransformer {

	@Autowired
	VistranslDaoService vistranslDaoService;
	
	
	
	@Test
	public void testGrouping() {

		List<VistranslHeadDto> list = VistranslTransformer.transform( vistranslDaoService.findAll(null) );
		assertEquals("heads",2,list.size());

		
		VistranslHeadDto head1 =list.get(0);
		List<VistranslLineDto>  lines1 =head1.getLines();
		
		assertEquals("lines",2,lines1.size());
	
	}	

	@Test
	public void testG() {
		
		Map<Integer, List<VistranslDao>> groupedByBilnr = 
				 vistranslDaoService.findAll(null)
				.stream()
				.collect(groupingBy(VistranslDao::getBilnr));
		
		
		System.out.println("groupedByBilnr.keySet="+groupedByBilnr.keySet());
		
		System.out.println("groupedByBilnr.keySet="+groupedByBilnr.keySet());
		
		groupedByBilnr.keySet().forEach(bilnr -> {
			System.out.println("bilnr="+bilnr);
			
			List<VistranslDao> list = groupedByBilnr.get(bilnr);
			
			System.out.println("list="+list);
			
		});
		
		
//		groupedByBilnr.get(
		
	}
	
	
	
}
