/**
 * 
 */
package no.systema.visma.integration.extended;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
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
import no.systema.visma.v1client.ApiClient;
import no.systema.visma.v1client.api.CustomerInvoiceApi;

/**
 * This class is overriding methods in {@linkplain CustomerInvoiceApi}.
 * 
 * Typically for attachments. Support of {@linkplain MediaType.MULTIPART_FORM_DATA}
 * 
 * @author fredrikmoller
 * @date 2018-08-14
 *
 */
@Service
public class CustomerInvoiceApiExtended extends CustomerInvoiceApi {

	private static Logger logger = LoggerFactory.getLogger(CustomerInvoiceApiExtended.class);

	@Autowired
	public FirmvisDaoService firmvisDaoService;
	
	/**
	 * @param apiClient
	 */
	@Autowired
	public CustomerInvoiceApiExtended(ApiClient apiClient) {
		super(apiClient);
	}
	
    /**
     * Creates an attachment and associates it with an invoice. If the file already exists, a new revision is created.
     * Response Message has StatusCode Created if POST operation succeed <br>
     * 
     * This method is overriding the corresponding Swagger-codegen generated method by adding a {@linkplain Resource} as {@linkplain MediaType.MULTIPART_FORM_DATA} to formParam<br>
     * 
     * <p><b>201</b> - Created
     * @param invoiceNumber Identifies the invoice
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     * @see CustomerInvoiceApi
     */
    public Object customerInvoiceCreateHeaderAttachmentByinvoiceNumber(String invoiceNumber, Resource attachment) throws IOException {
		logger.info("customerInvoiceCreateHeaderAttachmentByinvoiceNumber(String invoiceNumber, Resource attachment)");
    	Object postBody = null;
 
    	ApiClient apiClient = new ApiClient();  //using default RestTemplate
		FirmvisDao firmvis = firmvisDaoService.get();
		apiClient.setBasePath(firmvis.getVibapa().trim());
		apiClient.addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		apiClient.addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		apiClient.setAccessToken(firmvis.getViacto().trim()); 

        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("invoiceNumber", invoiceNumber);
        String path = UriComponentsBuilder.fromPath("/controller/api/v1/customerinvoice/{invoiceNumber}/attachment").buildAndExpand(uriVariables).toUriString();
      
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
