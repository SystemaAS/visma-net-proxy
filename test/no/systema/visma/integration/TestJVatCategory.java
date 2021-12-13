package no.systema.visma.integration;

import java.util.List;

import org.apache.logging.log4j.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.visma.v1client.model.VatCategoryDto;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJVatCategory {

	private static Logger logger = LogManager.getLogger(TestJVatCategory.class);	
	
	@Autowired 
	VatCategory vatCategory;

	@Test
	public void testGetVAtCategories() {

		 List<VatCategoryDto> dtoList = vatCategory.vatCategoryGetAllVatCategories();
		
		logger.debug("dtoList="+dtoList);

	}	
	
	
}
