package no.systema.visma.integration;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.v1client.api.SecurityApi;
import no.systema.visma.v1client.model.ContextInformation;

/**
 * This class is for convenience only! <br>
 * 
 * Listing all available company id's <br>
 * 
 * Company id is registered in {@linkplain FirmvisDao}
 * 
 * @author fredrikmoller
 *
 */
@Service
public class Security extends Configuration{

	@Autowired
	public FirmvisDaoService firmvisDaoService;	
	
	@Autowired
	public SecurityApi  securityApi = new SecurityApi(apiClient());

	@PostConstruct
	public void post_construct() {
		FirmvisDao firmvis = firmvisDaoService.get();

		securityApi.getApiClient().setBasePath(firmvis.getVibapa().trim());

		
	}
    
    /**
     * Get the companies available for this token.
     * 
     * <p><b>200</b> - successful operation
     * @param authorization The authorization parameter
     * @return List&lt;ContextInformation&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ContextInformation> getAvailableUserContexts(String authorization) throws RestClientException {
        Object postBody = null;
        
        String path = UriComponentsBuilder.fromPath("/security/api/v1/token/usercontexts").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        final String[] accepts = { 
            "application/json"
        };
        
        final List<MediaType> accept = securityApi.getApiClient().selectHeaderAccept(accepts);
        final String[] contentTypes = { };
        final MediaType contentType = securityApi.getApiClient().selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "vna_oauth" };

        ParameterizedTypeReference<List<ContextInformation>> returnType = new ParameterizedTypeReference<List<ContextInformation>>() {};
        return securityApi.getApiClient().invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }    
    
}
