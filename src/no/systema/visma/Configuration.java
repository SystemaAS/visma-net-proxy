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
	
}
