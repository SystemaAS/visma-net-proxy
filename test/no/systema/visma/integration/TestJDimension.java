package no.systema.visma.integration;

import java.util.List;

import org.apache.logging.log4j.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.visma.v1client.model.DtoSegment;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJDimension {

	private static Logger logger = LogManager.getLogger(TestJDimension.class);	
	
	@Autowired 
	Dimension dimension;

	@Test
	public void testDimensionGetDimensionList() {

		 List<String> dtoList = dimension.dimensionGetDimensionList();
		
		logger.debug("dtoList="+dtoList);

	}	

	@Test
	public void testDimensionGetSubaccountDimensionList() {

		DtoSegment dto = dimension.dimensionGetSubaccountDimensionList(1);

		logger.debug("dto (1)=" + dto);
		
		dto = dimension.dimensionGetSubaccountDimensionList(2);

		logger.debug("dto (2)=" + dto);		
		
		
		

	}		
	
	
	
	
	
}
