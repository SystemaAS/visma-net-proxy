package no.systema.visma.integration;

import java.util.List;

import org.slf4j.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.visma.v1client.model.DtoValueBoolean;
import no.systema.visma.v1client.model.SubAccountDto;
import no.systema.visma.v1client.model.SubAccountUpdateDto;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJSubaccount {

	private static Logger logger = LoggerFactory.getLogger(TestJSubaccount.class);	
	
	@Autowired 
	Subaccount subaccount;

	@Test
	public void testGetAllSubaccounts() {

		 List<SubAccountDto> dtoList = subaccount.subaccountGetAllSubaccounts();
		
		logger.debug("dtoList="+dtoList);

	}	

	@Test
	public void testGetSubaccountById() {

		//USE Dimension
		 SubAccountDto dto = subaccount.subaccountGetSubaccountBysubAccountId("0000");
		
		logger.debug("dto="+dto);

	}	
	
	@Test
	public void testSubaccountPost() {

		SubAccountUpdateDto  subAccountUpdateDto = new SubAccountUpdateDto();
		subAccountUpdateDto.setDescription(DtoValueHelper.toDtoString("beskrivning"));
		subAccountUpdateDto.setSubaccountCd(DtoValueHelper.toDtoString("1")); //Avdeling
		subAccountUpdateDto.setSubaccountId(DtoValueHelper.toDtoString("0002")); //löpande under...
		
		
		subaccount.subaccountPost(subAccountUpdateDto);


	}	
	
	
	
	
	
}
