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
import no.systema.visma.v1client.api.SupplierInvoiceApi;
import no.systema.visma.v1client.model.DtoValueNullableSupplierInvoiceTypes.ValueEnum;

/**
 * This class is overriding methods in {@linkplain SupplierInvoiceApi}.
 * 
 * Typically for attachments. Support of {@linkplain MediaType.MULTIPART_FORM_DATA}
 * 
 * @author fredrikmoller
 * @date 2018-08-21
 *
 */
@Service
public class SupplierInvoiceApiExtended extends SupplierInvoiceApi {

	private static Logger logger = LoggerFactory.getLogger(SupplierInvoiceApiExtended.class);

	@Autowired
	public FirmvisDaoService firmvisDaoService;
	
	/**
	 * @param apiClient
	 */
	@Autowired
	public SupplierInvoiceApiExtended(ApiClient apiClient) {
		super(apiClient);
	}
	
	 /**
     * Creates an attachment and associates it with a supplier invoice. If the file already exists, a new revision is created.
     * Response Message has StatusCode Created if POST operation succeed
     * <p><b>201</b> - Created
     * @param invoiceNumber Identifies the supplier invoice
     * @param attachment
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     * @deprecated  As of Visma.net API release 8, replaced by {@link #supplierInvoiceCreateHeaderAttachmentByinvoiceNumber_v8}
     */
	@Deprecated
    public Object supplierInvoiceCreateHeaderAttachmentByinvoiceNumber(String invoiceNumber, Resource attachment) throws RestClientException {
		logger.info("supplierInvoiceCreateHeaderAttachmentByinvoiceNumber(String invoiceNumber, Resource attachment)");
    	Object postBody = null;

    	ApiClient apiClient = new ApiClient(); //using default RestTemplate
		FirmvisDao firmvis = firmvisDaoService.get();
		apiClient.setBasePath(firmvis.getVibapa().trim());
		apiClient.addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		apiClient.addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		apiClient.setAccessToken(firmvis.getViacto().trim());       
 
        if (invoiceNumber == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'invoiceNumber' when calling supplierInvoiceCreateHeaderAttachmentByinvoiceNumber");
        }
        if (attachment == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'attachment' when calling supplierInvoiceCreateHeaderAttachmentByinvoiceNumber");
        }  
        
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("invoiceNumber", invoiceNumber);
        String path = UriComponentsBuilder.fromPath("/controller/api/v1/supplierInvoice/{invoiceNumber}/attachment").buildAndExpand(uriVariables).toUriString();
        
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

	/**
	 * Creates an attachment and associates it with a supplier invoice. If the
	 * file already exists, a new revision is created. Response Message has
	 * StatusCode Created if POST operation succeed
	 * <p>
	 * <b>201</b> - Created
	 * 
	 * @param invoiceNumber
	 *            Identifies the supplier invoice
	 * @param documentType 
	 * @param attachment
	 * @return Object
	 * @throws RestClientException
	 *             if an error occurs while attempting to invoke the API
	 */
	public Object supplierInvoiceCreateHeaderAttachmentByinvoiceNumber_v8(String invoiceNumber, ValueEnum documentType, Resource attachment) throws RestClientException {
		logger.info("supplierInvoiceCreateHeaderAttachmentByinvoiceNumber(String invoiceNumber, ValueEnum documentType, Resource attachment)");
		Object postBody = null;

		ApiClient apiClient = new ApiClient(); // using default RestTemplate
		FirmvisDao firmvis = firmvisDaoService.get();
		apiClient.setBasePath(firmvis.getVibapa().trim());
		apiClient.addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		apiClient.addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		apiClient.setAccessToken(firmvis.getViacto().trim());

		if (invoiceNumber == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'invoiceNumber' when calling supplierInvoiceCreateHeaderAttachmentByinvoiceNumber");
		}
		if (documentType == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'documentType' when calling supplierInvoiceCreateHeaderAttachmentByinvoiceNumber");
		}
		if (attachment == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'attachment' when calling supplierInvoiceCreateHeaderAttachmentByinvoiceNumber");
		}

		final Map<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("invoiceNumber", invoiceNumber);
		uriVariables.put("documentType", documentType.toString());
		String path = UriComponentsBuilder.fromPath("/controller/api/v1/supplierInvoice/documentType/{documentType}/{invoiceNumber}/attachment").buildAndExpand(uriVariables).toUriString();
		
		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
		final HttpHeaders headerParams = new HttpHeaders();
		headerParams.setContentType(MediaType.MULTIPART_FORM_DATA);

		final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
		formParams.add("file", attachment);

		final String[] accepts = { "application/json", "text/json" };
		final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
		final String[] contentTypes = { MediaType.MULTIPART_FORM_DATA_VALUE };
		final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);
		String[] authNames = new String[] { "ipp-application-type", "ipp-company-id", "vna_oauth" };
		ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {
		};

		return apiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
	}
    
}
