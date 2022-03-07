/**
 * 
 */
package no.systema.visma.integration.extended;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
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

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.v1client.ApiClient;
import no.systema.visma.v1client.api.CustomerCreditNoteApi;

/**
 * This class is overriding methods in {@linkplain CustomerCreditNoteApi}.
 * 
 * Typically for attachments. Support of {@linkplain MediaType.MULTIPART_FORM_DATA}
 * 
 * @author fredrikmoller
 * @date 2018-08-14
 *
 */
@Service
public class CustomerCreditNoteApiExtended extends CustomerCreditNoteApi {

	private static Logger logger = LoggerFactory.getLogger(CustomerCreditNoteApiExtended.class);

	@Autowired
	public FirmvisDaoService firmvisDaoService;
	
	/**
	 * @param apiClient
	 */
	@Autowired
	public CustomerCreditNoteApiExtended(ApiClient apiClient) {
		super(apiClient);
	}
	
    /**
     * Creates an attachment and associates it with a credit note. If the file already exists, a new revision is created.
     * Response Message has StatusCode Created if POST operation succeed
     * <p><b>201</b> - Created
     * @param creditNoteNumber Identifies the credit note
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object customerCreditNoteCreateHeaderAttachmentBycreditNoteNumber(String creditNoteNumber, Resource attachment) throws RestClientException {
		logger.info("customerCreditNoteCreateHeaderAttachmentBycreditNoteNumber(String creditNoteNumber, Resource attachment)");
    	Object postBody = null;

    	ApiClient apiClient = new ApiClient(); //using default RestTemplate
		FirmvisDao firmvis = firmvisDaoService.get();
		apiClient.setBasePath(firmvis.getVibapa().trim());
		apiClient.addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		apiClient.addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		apiClient.setAccessToken(firmvis.getViacto().trim());         
        
        // verify the required parameter 'creditNoteNumber' is set
        if (creditNoteNumber == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'creditNoteNumber' when calling customerCreditNoteCreateHeaderAttachmentBycreditNoteNumber");
        }
        
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("creditNoteNumber", creditNoteNumber);
        String path = UriComponentsBuilder.fromPath("/controller/api/v1/customerCreditNote/{creditNoteNumber}/attachment").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        headerParams.setContentType(MediaType.MULTIPART_FORM_DATA);
        
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        formParams.add("file",attachment);   
        
        final String[] accepts = {"application/json", "text/json"};    
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts); 
        final String[] contentTypes = {MediaType.MULTIPART_FORM_DATA_VALUE};
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);       
        String[] authNames = new String[] { "ipp-application-type", "ipp-company-id", "vna_oauth" };       
        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
 
        return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }    
    
    
    
    
	
}
