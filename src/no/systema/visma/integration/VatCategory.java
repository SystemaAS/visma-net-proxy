package no.systema.visma.integration;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.v1client.api.VatCategoryApi;
import no.systema.visma.v1client.model.CustomerClassDto;
import no.systema.visma.v1client.model.VatCategoryDto;

@Service
public class VatCategory extends Configuration{

	@Autowired
	public FirmvisDaoService firmvisDaoService;	
	
	@Autowired
	public VatCategoryApi  vatCategoryApi = new VatCategoryApi(apiClient());

	@PostConstruct
	public void post_construct() {
		FirmvisDao firmvis = firmvisDaoService.get();

		vatCategoryApi.getApiClient().setBasePath(firmvis.getVibapa().trim());
		vatCategoryApi.getApiClient().addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		//TODO unmark
//		vatCategoryApi.getApiClient().addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		vatCategoryApi.getApiClient().setAccessToken(firmvis.getViacto().trim());			
		
		//customerApi.getApiClient().setDebugging(true);	//Warning...set debugging in VismaClientHttpRequestInterceptor	
		
	}
	
    /**
     * Get a range of VatCategories
     * 
     * <p><b>200</b> - OK
     * @return List&lt;VatCategoryDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<VatCategoryDto> vatCategoryGetAllVatCategories() throws RestClientException {
    	List<VatCategoryDto> dtoList;
    	Integer numberToRead = null;
    	Integer skipRecords = null;
    	String lastModifiedDateTime = null;
    	String lastModifiedDateTimeCondition = null;
    	
		try {
	
			dtoList = vatCategoryApi.vatCategoryGetAllVatCategories(numberToRead, skipRecords, lastModifiedDateTime, lastModifiedDateTimeCondition);	
		
		
		} catch (RestClientException e) {
			throw e;
		}
		return dtoList;
	}	
	
	
	
	
	
	
	
}
