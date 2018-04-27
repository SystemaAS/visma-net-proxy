package no.systema.visma;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.v1client.ApiClient;

/**
 * This class is managing the ApiClient settings, e.g. accessToken.
 * 
 * @author fredrikmoller
 */
//public abstract class Configuration {
public class Configuration {
	
	@Autowired
	public FirmvisDaoService firmvisDaoService;
	
	
	@PostConstruct
	public void post_contruct() {
		FirmvisDao firmvis = firmvisDaoService.get();
		
//		customerApi.getApiClient().setBasePath(firmvis.getVibapa().trim());
//		customerApi.getApiClient().addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
//		customerApi.getApiClient().addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
//		customerApi.getApiClient().setAccessToken(firmvis.getViacto().trim());			
//		
//		customerApi.getApiClient().setDebugging(true);				
		
		
		
	}
	
	/**
	 * 	## Recommendation
	 *
	 * It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.
	 * 
	 * Properties to ApiClient is set by values from FIRMVIS.
	 * FIRMVIS data is retrieved in run-time.
	 * 
	 * @return a APIClient with debugging=true
	 */
	public  ApiClient getApiClient() {
		ApiClient apiClient = new ApiClient();

		FirmvisDao firmvis = firmvisDaoService.get();
//		
//		System.out.println("firmvis="+firmvis);
		
		//TODO GEt values from database
//		apiClient.setBasePath("https://integration.visma.net/API");
//		apiClient.addDefaultHeader("ipp-application-type", "Visma.net Financials");
//		apiClient.addDefaultHeader("ipp-company-id", "1684147");
//		apiClient.setAccessToken("81d21509-a23e-40a3-82d4-b101bb681d0f");		
//		
//		apiClient.setDebugging(true);
		
		return apiClient;
	}
	
}
