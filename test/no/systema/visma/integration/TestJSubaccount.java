package no.systema.visma.integration;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.visma.v1client.model.SubAccountDto;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJSubaccount {

	private static Logger logger = Logger.getLogger(TestJSubaccount.class);	
	
	@Autowired 
	Subaccount subaccount;

	@Test
	public void testGetAllSubaccounts() {

		 List<SubAccountDto> dtoList = subaccount.subaccountGetAllSubaccounts();
		
		logger.debug("dtoList="+dtoList);

	}	

	@Test
	public void testGetSubaccountById() {

		
		//TODO verify that get by id works, now it seems not to
		 SubAccountDto dto = subaccount.subaccountGetSubaccountBysubAccountId("25");
		
		logger.debug("dto="+dto);

	}	
	
	
	
	
}
