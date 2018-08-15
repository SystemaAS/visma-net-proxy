/**
 * 
 */
package no.systema.visma.integration.extended;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

	private static Logger logger = Logger.getLogger(CustomerInvoiceApiExtended.class);

	@Autowired
	public FirmvisDaoService firmvisDaoService;
	
	/**
	 * @param apiClient
	 */
	public CustomerInvoiceApiExtended(ApiClient apiClient) {
		super(apiClient);
	}
	

    public Object customerInvoiceCreateHeaderAttachmentByinvoiceNumber(String invoiceNumber, Resource attachment) throws IOException {
    	Object postBody = null;

    	//TODO, kör med, och sedan försök ta bort. ApiClient init i constructor
    	//TODO fixa attachment isf getTestFile
    	ApiClient apiClient2 = new ApiClient();
		FirmvisDao firmvis = firmvisDaoService.get();
		apiClient2.setBasePath(firmvis.getVibapa().trim());
		apiClient2.addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		apiClient2.addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		apiClient2.setAccessToken(firmvis.getViacto().trim());	        
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("invoiceNumber", invoiceNumber);
        String path = UriComponentsBuilder.fromPath("/controller/api/v1/customerinvoice/{invoiceNumber}/attachment").buildAndExpand(uriVariables).toUriString();
      
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        headerParams.setContentType(MediaType.MULTIPART_FORM_DATA);
        
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        formParams.add("file",getTestFile());         
        
        final String[] accepts = {"application/json", "text/json"};    
        final List<MediaType> accept = apiClient2.selectHeaderAccept(accepts); 
        final String[] contentTypes = {MediaType.MULTIPART_FORM_DATA_VALUE};
        final MediaType contentType = apiClient2.selectHeaderContentType(contentTypes);       
        String[] authNames = new String[] { "ipp-application-type", "ipp-company-id", "vna_oauth" };       
        ParameterizedTypeReference<Object> returnType = new ParameterizedTypeReference<Object>() {};
        
        return apiClient2.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
               
    }
	
	
    public static Resource getTestFile() throws IOException {
        Path testFile = Files.createTempFile("test-file", ".txt");
        StringBuilder txt = new StringBuilder("Hello World !!, This is a test file, generated:").append(LocalDateTime.now().toString());
        Files.write(testFile, txt.toString().getBytes());

        return new FileSystemResource(testFile.toFile());
    }	
	
	
}
