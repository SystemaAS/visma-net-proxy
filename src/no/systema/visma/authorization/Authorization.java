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
import no.systema.visma.authorization.HttpBasicAuthApiClient.CollectionFormat;
import no.systema.visma.integration.Configuration;

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
     * @param tokenRequest Defines the data to get the generated accesstoken
     * @return TokenResponseDto the response, including the accesstoken
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public TokenResponseDto accessTokenRequestPost(TokenRequestDto tokenRequest, String client_id, String client_secret) throws RestClientException {
    	logger.info("accessTokenRequestPost(TokenRequestDto tokenRequest, String client_id, String client_secret)");
    	
    	HttpBasicAuthApiClient httpBasicAuthapiClient = httpBasicAuthApiClient(); 
    	httpBasicAuthapiClient.setBasePath("https://integration.visma.net/API");
    	httpBasicAuthapiClient.setUsername(client_id);
    	httpBasicAuthapiClient.setPassword(client_secret);
    	
    	Object postBody = null;  //Not in use
        
        if (tokenRequest == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'tokenRequest' when calling accessTokenRequestPost");
        }
        if (client_id == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'client_id' when calling accessTokenRequestPost");
        }  
        if (client_secret == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'client_secret' when calling accessTokenRequestPost");
        }        
        
        String path = UriComponentsBuilder.fromPath("/security/api/v2/token").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
              
        formParams.putAll(httpBasicAuthapiClient.parameterToMultiObjectValueMap(CollectionFormat.MULTI, "code", tokenRequest.getCode()));       
        formParams.putAll(httpBasicAuthapiClient.parameterToMultiObjectValueMap(CollectionFormat.MULTI, "grant_type", tokenRequest.getGrantType()));       
        formParams.putAll(httpBasicAuthapiClient.parameterToMultiObjectValueMap(CollectionFormat.MULTI, "redirect_uri", tokenRequest.getRedirectUri()));       
        
        final String[] accepts = { 
            "application/json", "text/json"
        };
        final List<MediaType> accept = httpBasicAuthapiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
        	"application/x-www-form-urlencoded"
        };
        final MediaType contentType = httpBasicAuthapiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "vna_oauth" };

        ParameterizedTypeReference<TokenResponseDto> returnType = new ParameterizedTypeReference<TokenResponseDto>() {};
        return httpBasicAuthapiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }	
	
}
