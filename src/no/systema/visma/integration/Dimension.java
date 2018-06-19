package no.systema.visma.integration;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.v1client.api.DimensionApi;
import no.systema.visma.v1client.model.DtoSegment;

@Service
public class Dimension extends Configuration{

	@Autowired
	public FirmvisDaoService firmvisDaoService;	
	
	@Autowired
	public DimensionApi  dimensionApi = new DimensionApi(apiClient());

	@PostConstruct
	public void post_construct() {
		FirmvisDao firmvis = firmvisDaoService.get();

		dimensionApi.getApiClient().setBasePath(firmvis.getVibapa().trim());
		dimensionApi.getApiClient().addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		dimensionApi.getApiClient().addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		dimensionApi.getApiClient().setAccessToken(firmvis.getViacto().trim());			
		
		//dimensionApi.getApiClient().setDebugging(true);	//Warning...set debugging in VismaClientHttpRequestInterceptor	
		
	}
	
    /**
     * Get a list of all Dimension names/IDs
     * 
     * <p><b>200</b> - OK
     * @return List&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
	public List<String> dimensionGetDimensionList() throws RestClientException {
		List<String> dtoList;

		try {

			dtoList = dimensionApi.dimensionGetDimensionList();

		} catch (RestClientException e) {
			throw e;
		}

		return dtoList;
	}	
	
	 /**
     * Get a specific Segment for Dimension SUBACCOUNT
     * 
     * <p><b>200</b> - OK
     * @param segmentId Identifies the Segment
     * @return DtoSegment
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
	public DtoSegment dimensionGetSubaccountDimensionList(int segmentId) throws RestClientException {
		DtoSegment dto;

		try {

			dto = dimensionApi.dimensionGetSegmentBydimensionIdsegmentId(DimensionEnum.SUBACCOUNT.toString(), segmentId);

		} catch (RestClientException e) {
			throw e;
		}

		return dto;
	}
	
	
	public enum DimensionEnum {

		SUBACCOUNT("SUBACCOUNT");

		private String value;

		DimensionEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
	}

	
	
}
