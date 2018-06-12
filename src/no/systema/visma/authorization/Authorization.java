package no.systema.visma.authorization;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.integration.Configuration;
import no.systema.visma.v1client.ApiClient;

@Service
public class Authorization extends Configuration {
	private static Logger logger = Logger.getLogger(Authorization.class);
	
	@Autowired
	public FirmvisDaoService firmvisDaoService;		
	
	 /**
     * Get the access token
     * 
     * Response Message has StatusCode Created if POST operation succeed
     * <p><b>201</b> - Created
     * @param tokenRequest Defines the data top get the generated accesstoken
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object accessTokenRequestPost(TokenRequestDto tokenRequest) throws RestClientException {
    	logger.info("accessTokenRequestPost(TokenRequestDto tokenRequest)");
    	
    	ApiClient apiClient = apiClient(); 
    	apiClient.setBasePath("https://integration.visma.net/API");
    	
    	Object postBody = tokenRequest;
        
        if (tokenRequest == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tokenRequest' when calling customerPost");
        }
        
        String path = UriComponentsBuilder.fromPath("/security/api/v2/token").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
 
        final String[] accepts = { 
            "application/json", "text/json"
        };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json", "text/json", "application/xml", "text/xml", "application/x-www-form-urlencoded"
        };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "ipp-application-type", "ipp-company-id", "vna_oauth" };

        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }	
	
}
