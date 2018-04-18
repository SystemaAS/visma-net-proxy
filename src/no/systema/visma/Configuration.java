package no.systema.visma;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import no.systema.visma.v1client.ApiClient;

/**
 * This class is managing the ApiClient settings, e.g. accessToken.
 * 
 * @author fredrikmoller
 */
public abstract class Configuration {
	protected static Logger logger = Logger.getLogger(Configuration.class);
	
	@Autowired
	@Qualifier("no.systema.visma.v1client.ApiClient")
	protected ApiClient apiClient;
	
	@PostConstruct
	private void postconstruct() {
	
		apiClient.setBasePath("https://integration.visma.net/API");
		apiClient.addDefaultHeader("ipp-application-type", "Visma.net Financials");
		apiClient.addDefaultHeader("ipp-company-id", "1684147");
		apiClient.setAccessToken("81d21509-a23e-40a3-82d4-b101bb681d0f");		
		
		//apiClient.setDebugging(true); // warning...
		
	}
	
}
