package no.systema.visma.integration;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.VisavdDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.v1client.api.SubaccountApi;
import no.systema.visma.v1client.model.CustomerUpdateDto;
import no.systema.visma.v1client.model.SubAccountDto;
import no.systema.visma.v1client.model.SubAccountUpdateDto;

/**
 * A Wrapper on SubaccountApi
 * 
 * Also see https://integration.visma.net/API-index/#!/Subaccount
 * 
 * @author fredrikmoller
 */
@Service
public class Subaccount extends Configuration{
	private static Logger logger = LogManager.getLogger(Subaccount.class);
	
	@Autowired
	public FirmvisDaoService firmvisDaoService;	
	
	@Autowired
	public SubaccountApi subaccountApi = new SubaccountApi(apiClient());

	@PostConstruct
	public void post_construct() {
		FirmvisDao firmvis = firmvisDaoService.get();

		subaccountApi.getApiClient().setBasePath(firmvis.getVibapa().trim());
		subaccountApi.getApiClient().addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		subaccountApi.getApiClient().addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		subaccountApi.getApiClient().setAccessToken(firmvis.getViacto().trim());			
		
		//subaccountApi.getApiClient().setDebugging(true);	//Warning...set debugging in VismaClientHttpRequestInterceptor	
		
	}
	
	/**
	 * This is the startpoint for syncronizing SYSPED VISKUNDE with Visma-net Customer,.
	 * 
	 * @param viskundeDao
	 * @throws RestClientException
	 * @throws HttpClientErrorException
	 */
	public void syncronize(Object obj) throws RestClientException,  HttpClientErrorException {
		logger.info("syncronize(ViskundeDao viskundeDao)");
		logger.info(LogHelper.logPrefixSubaccount(obj));		
		
		try {
			
//    		SubAccountDto customerExistDto = subaccountGetSubaccountBysubAccountId(String.valueOf(viskundeDao.getKundnr()));			
 
//    		if (customerExistDto != null) {
//    			logger.info("Kunde:"+viskundeDao.getKundnr()+ " exist, trying to update.");
//
//    			CustomerUpdateDto updateDto = convertToCustomerUpdateDto(viskundeDao, IUDEnum.UPDATE);			
//    			customerPutBycustomerCd(String.valueOf(viskundeDao.getKundnr()), updateDto);
//    			logger.info("Kunde:"+viskundeDao.getKundnr()+ " is updated.");
//
//    		} else {
//
//    			logger.info("Kunde:"+viskundeDao.getKundnr()+ " does not exist, Trying to insert.");
//    			CustomerUpdateDto updateDto = convertToCustomerUpdateDto(viskundeDao, IUDEnum.INSERT);			
//    			customerPost(updateDto);
//    			logger.info("Kunde:"+viskundeDao.getKundnr()+ " is inserted.");
//
//    		}
    		
			
    	} catch (HttpClientErrorException e) {
//			logger.error(LogHelper.Subaccount(viskundeDao.getKundnr()));
//			logger.error(e.getClass()+" On  syncronizeCustomer.  viskundeDao="+viskundeDao.toString());
			logger.error("message:"+e.getMessage());
			logger.error("status text:"+new String(e.getStatusText()));  //Status text contains Response body from Visma.net
			throw e;
		}
    	catch (RestClientException e) {
//    		logger.error(LogHelper.Subaccount(viskundeDao.getKundnr()));
//			logger.error(e.getClass()+" On syncronizeCustomer.  viskundeDao="+viskundeDao.toString());
			throw e;
		}
    	catch (Exception e) {
//    		logger.error(LogHelper.Subaccount(viskundeDao.getKundnr()));
//			logger.error(e.getClass()+" On syncronizeCustomer.  viskundeDao="+viskundeDao.toString());
			throw e;
		} 
		
		
	}

	//TODO full insert saknas i SubAccountUpdateDto
	  private SubAccountUpdateDto convertToSubAccountUpdateDto(VisavdDao visavd) {
	    	logger.info("SubAccountUpdateDto(ViskundeDao viskunde, IUDEnum status)");
	    	//Sanity checks
			if (visavd.getSyavd() == 0) {
				String errMsg = "SYAVD can not be 0";
				logger.error(errMsg);
				throw new RuntimeException(errMsg);
			} 
		
			SubAccountUpdateDto dto = new SubAccountUpdateDto();
/*			alla som finns...?
			dto.setActive(active);
			dto.setDescription(description);
			dto.setSubaccountCd(subaccountCd);
			dto.setSubaccountId(subaccountId);
*/			
			
			return dto;
		}	
	
	
	
	
	
	/**
     * Create a Subaccount
     * Response Message has StatusCode Created if POST operation succeed
     * <p><b>201</b> - Created
     * @param subAccountUpdateDto Defines the data for Subaccount to create
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void subaccountPost(SubAccountUpdateDto subAccountUpdateDto) throws RestClientException {	
	
		try {

			subaccountApi.subaccountPost(subAccountUpdateDto);

		} catch (HttpClientErrorException e) {
//			logger.error(LogHelper.logPrefixSubaccount(subAccountUpdateDto));		
			logger.error(e.getClass() + " On  subaccountApi.subaccountPost call, subAccountUpdateDto=" + subAccountUpdateDto.toString());
			logger.error("message:" + e.getMessage());
			logger.error("status text:" + new String(e.getStatusText())); //Status text contains Response body from Visma.net
			throw e;
		} catch (RestClientException e) {
//			logger.error(LogHelper.logPrefixSubaccount(subAccountUpdateDto));	
			logger.error(e.getClass() + " On subaccountApi.subaccountPost, subAccountUpdateDto=" + subAccountUpdateDto.toString());
			throw e;
		} catch (Exception e) {
//			logger.error(LogHelper.logPrefixSubaccount(subAccountUpdateDto));	
			logger.error(e.getClass() + " subaccountApi.subaccountPost, subAccountUpdateDto=" + subAccountUpdateDto.toString());
			throw e;
		}
    
   }
	
	
	
    /**
     * Get all SubAccounts
     * 
     * <p><b>200</b> - OK
     * @return List&lt;SubAccountDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<SubAccountDto> subaccountGetAllSubaccounts() throws RestClientException,  HttpClientErrorException {
	
	  return subaccountApi.subaccountGetAllSubaccounts();	
	
    }
	
    /**
     * Get a specific SubAccount
     * Data for SubAccount
     * <p><b>200</b> - OK
     * @param subAccountId Identifies the SubAccount
     * @return SubAccountDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SubAccountDto subaccountGetSubaccountBysubAccountId(String subAccountId) throws RestClientException,  HttpClientErrorException{
    	
		logger.info("subaccountGetSubaccountBysubAccountId(String subAccountId)");
		SubAccountDto subAccountExistDto;

		try {

			subAccountExistDto = subaccountApi.subaccountGetSubaccountBysubAccountId(subAccountId);
			
		} catch (HttpClientErrorException e) {
			logger.info("message:" + e.getMessage()+ ", subAccountExistDto is null, continue...");
			subAccountExistDto = null;
			// continue
		}

		return subAccountExistDto;  
    	
    }
    
}
