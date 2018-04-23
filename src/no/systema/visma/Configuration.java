package no.systema.visma;

import org.apache.log4j.Logger;

import no.systema.visma.v1client.ApiClient;

/**
 * This class is managing the ApiClient settings, e.g. accessToken.
 * 
 * @author fredrikmoller
 */
public abstract class Configuration {
	
	/**
	 * 	## Recommendation
	 *
	 * It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.
	 * @return a APIClient with debugging=true
	 */
	public  ApiClient getApiClient() {
		ApiClient apiClient = new ApiClient();

		//TODO GEt values from database
		apiClient.setBasePath("https://integration.visma.net/API");
		apiClient.addDefaultHeader("ipp-application-type", "Visma.net Financials");
		apiClient.addDefaultHeader("ipp-company-id", "1684147");
		apiClient.setAccessToken("81d21509-a23e-40a3-82d4-b101bb681d0f");		
		
		apiClient.setDebugging(true);
		
		return apiClient;
	}
	
}
