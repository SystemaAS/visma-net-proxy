package no.systema.visma.integration;

import java.util.List;

import org.apache.logging.log4j.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.v1client.model.ContextInformation;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJSecurity {

	private static Logger logger = LogManager.getLogger(TestJSecurity.class);	
	
	@Autowired 
	Security security;
	
	@Autowired
	FirmvisDaoService firmvisDaoService;

	@Test
	public void testGetAvailableUserContexts() {
		FirmvisDao firmvis = firmvisDaoService.get();

		 List<ContextInformation> dtoList = security.getAvailableUserContexts(firmvis.getViacto());
		
		logger.debug("dtoList="+dtoList);

	}	
	
	
}
