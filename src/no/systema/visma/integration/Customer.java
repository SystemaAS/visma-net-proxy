package no.systema.visma.integration;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.v1client.ApiClient;
import no.systema.visma.v1client.api.CustomerApi;
import no.systema.visma.v1client.model.AddressUpdateDto;
import no.systema.visma.v1client.model.ContactInfoUpdateDto;
import no.systema.visma.v1client.model.CustomerClassDto;
import no.systema.visma.v1client.model.CustomerDto;
import no.systema.visma.v1client.model.CustomerUpdateDto;
import no.systema.visma.v1client.model.DtoValueAddressUpdateDto;
import no.systema.visma.v1client.model.DtoValueContactInfoUpdateDto;
import no.systema.visma.v1client.model.DtoValueCustomerStatus;
import no.systema.visma.v1client.model.DtoValueString;

/**
 * A Wrapper on CustomerApi
 * 
 * Also see https://integration.visma.net/API-index/#!/Customer
 * 
 * @author fredrikmoller
 */
@Service
public class Customer  extends Configuration{
	private static Logger logger = Logger.getLogger(Customer.class);
	
	@Autowired
	public FirmvisDaoService firmvisDaoService;	
	
	@Autowired
	public CustomerApi customerApi = new CustomerApi(apiClient());

	@PostConstruct
	public void post_construct() {
		FirmvisDao firmvis = firmvisDaoService.get();

		customerApi.getApiClient().setBasePath(firmvis.getVibapa().trim());
		customerApi.getApiClient().addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		customerApi.getApiClient().addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		customerApi.getApiClient().setAccessToken(firmvis.getViacto().trim());			
		
		//customerApi.getApiClient().setDebugging(true);	//Warning...set debugging in VismaNetResponseErrorHandler	
		
	}
	

	/**
	 * This is the startpoint for syncronizing SYSPED VISKUNDE with Visma-net Customer,.
	 * 
	 * @param viskundeDao
	 * @throws RestClientException
	 * @throws HttpClientErrorException
	 */
	public void syncronize(ViskundeDao viskundeDao) throws RestClientException,  HttpClientErrorException {
		logger.info("syncronize(ViskundeDao viskundeDao)");
		//For both New and Update
		CustomerUpdateDto updateDto = convertToCustomerUpdateDto(viskundeDao);			
		
		try {
			
    		CustomerDto customerExistDto = getGetBycustomerCd(String.valueOf(viskundeDao.getKundnr()));			
 
    		if (customerExistDto != null) {
  
    			customerPutBycustomerCd(String.valueOf(viskundeDao.getKundnr()), updateDto);
    			logger.info("Kunde:"+viskundeDao.getKundnr()+ " is updated.");
    			
    		} else {
    		
    			customerPost(updateDto);
    			logger.info("Kunde:"+viskundeDao.getKundnr()+ " is created.");
    			
    		}
    		
			
    	} catch (HttpClientErrorException e) {
			logger.error(Helper.logPrefix(viskundeDao.getKundnr()));
			logger.error(e.getClass()+" On  syncronizeCustomer.  viskundeDao="+viskundeDao.toString());
			logger.error("message:"+e.getMessage());
			logger.error("status text:"+new String(e.getStatusText()));  //Status text contains Response body from Visma.net
			throw e;
		}
    	catch (RestClientException e) {
    		logger.error(Helper.logPrefix(viskundeDao.getKundnr()));
			logger.error(e.getClass()+" On syncronizeCustomer.  viskundeDao="+viskundeDao.toString());
			throw e;
		}
    	catch (Exception e) {
    		logger.error(Helper.logPrefix(viskundeDao.getKundnr()));
			logger.error(e.getClass()+" On syncronizeCustomer.  viskundeDao="+viskundeDao.toString());
			throw e;
		} 
		
		
	}
	
	/**
	 * Get a specific Customer
	 * 
	 * @param number
	 * @return CustomerDto return null if not found.
	 */
	public CustomerDto getGetBycustomerCd(String number) {
		logger.info("getGetBycustomerCd(String number)");
		CustomerDto customerExistDto;

		try {

			customerExistDto = customerApi.customerGetBycustomerCd(number);
			
		} catch (HttpClientErrorException e) {
			logger.info("message:" + e.getMessage()+ ", customerExistDto is null, continue...");
			customerExistDto = null;
			// continue
		}

		return customerExistDto;

	}
	
	
	/**
     * Updates a specific customer
     * 
     * 
     * Response Message has StatusCode NoContent if PUT operation succeed
     * <p><b>204</b> - NoContent
     * @param number Visma.net number (kundnr.:)
     * @param viskundeDao The data to update for the customer
     * @throws RestClientException if an error occurs while attempting to invoke the API
     * @throws HttpClientErrorException when an HTTP 4xx is received. Typically when indata is wrong
     */
    private void customerPutBycustomerCd(String number, CustomerUpdateDto customerUpdateDto) throws RestClientException, HttpClientErrorException {
    	logger.info(Helper.logPrefix(number));
    	logger.info("customerPutBycustomerCd()"); 
    	
    	try {

    		customerApi.customerPutBycustomerCd(number, customerUpdateDto);

    	} catch (HttpClientErrorException e) {
			logger.error(Helper.logPrefix(number));
			logger.error(e.getClass()+" On  customerApi.customerPutBycustomerCd call. number="+number+", customerUpdateDto="+customerUpdateDto.toString());
			logger.error("message:"+e.getMessage());
			logger.error("status text:"+new String(e.getStatusText()));  //Status text contains Response body from Visma.net
			throw e;
		}
    	catch (RestClientException e) {
			logger.error(Helper.logPrefix(number));
			logger.error(e.getClass()+" On customerApi.customerPutBycustomerCd call. number="+number+", customerUpdateDto="+customerUpdateDto.toString());
			throw e;
		}
    	catch (Exception e) {
			logger.error(Helper.logPrefix(number));
			logger.error(e.getClass()+" On customerApi.customerPutBycustomerCd call. number="+number+", customerUpdateDto="+customerUpdateDto.toString());
			throw e;
		} 
    	
    }
    
	/**
     * Creates a customer
     * Response Message has StatusCode Created if POST operation succeed
     * <p><b>201</b> - Created
     * @param viskundeDao Defines the data for the customer to create
     * @return int generated number from Visma.net
     * @throws RestClientException if an error occurs while attempting to invoke the API
     * @throws IllegalArgumentException if Location cannot be found in Response Headers
     * @throws IndexOutOfBoundsException if, still,  Location cannot be found in Response Headers
     */
    private void customerPost(CustomerUpdateDto updateDto) throws RestClientException,IllegalArgumentException, IndexOutOfBoundsException {
    	logger.info(Helper.logPrefix(updateDto.getNumber()));
    	logger.info("customerPost()"); 
    	
    	try {

    		customerApi.customerPost(updateDto);

    		
    	} catch (HttpClientErrorException e) {
			logger.error(Helper.logPrefix(updateDto.getNumber()));
			logger.error(e.getClass()+" On customerApi.customerPost call. updateDto="+updateDto.toString());
			logger.error("message:"+e.getMessage());
			logger.error("status text:"+new String(e.getStatusText()));  //Status text contains Response body from Visma.net
			throw e;
		}
    	catch (RestClientException  | IllegalArgumentException | IndexOutOfBoundsException e) {
			logger.error(e.getClass()+" On customerApi.customerPost call. updateDto="+updateDto.toString(), e);
			throw e;
		} 
    	catch (Exception e) {
			logger.error(Helper.logPrefix(updateDto.getNumber()));
			logger.error(e.getClass()+" On customerApi.customerPost call. updateDto="+updateDto.toString());
			throw e;
		}     	
    	
    }


	/**
     * Get a range of customers
     * 
     * <p><b>200</b> - OK
     * @param greaterThanValue The greaterThanValue parameter
     * @param numberToRead The numberToRead parameter
     * @param skipRecords The skipRecords parameter
     * @param name The name parameter
     * @param status The status parameter
     * @param corporateId  Correspond to SYRG
     * @param vatRegistrationId The vatRegistrationId parameter
     * @param email The email parameter
     * @param phone The phone parameter
     * @param lastModifiedDateTime The lastModifiedDateTime parameter
     * @param lastModifiedDateTimeCondition The lastModifiedDateTimeCondition parameter
     * @param createdDateTime The createdDateTime parameter
     * @param createdDateTimeCondition The createdDateTimeCondition parameter
     * @param attributes The attributes parameter
     * @param pageNumber The pageNumber parameter
     * @param pageSize The pageSize parameter
     * @return List&lt;CustomerDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
	public List<CustomerDto> customerGetAll(String greaterThanValue, Integer numberToRead, Integer skipRecords,
			String name, String status, String corporateId, String vatRegistrationId, String email, String phone,
			String lastModifiedDateTime, String lastModifiedDateTimeCondition, String createdDateTime,
			String createdDateTimeCondition, String attributes, Integer pageNumber, Integer pageSize)
			throws RestClientException {

		return customerApi.customerGetAll(greaterThanValue, numberToRead, skipRecords, name, status, corporateId,
				vatRegistrationId, email, phone, lastModifiedDateTime, lastModifiedDateTimeCondition, createdDateTime,
				createdDateTimeCondition, attributes, pageNumber, pageSize);

	}
    
	/**
	 * 
	 * Convert VISKUNDE data into Customer. </br></br>
	 * 
	 * For mapping specification see Systema Google Drive /Losning1/Visma migration </br></br>
	 * 
	 * Structure also found here: https://github.com/SystemaAS/visma-net-v1client/blob/master/docs/CustomerUpdateDto.md
	 * 
	 * 
	 * @param viskunde VISKUNDE
	 * @return CustomerUpdateDto
	 * @throws RuntimeException if Kundnr is 0.
	 */
    public CustomerUpdateDto convertToCustomerUpdateDto(ViskundeDao viskunde) {
    	logger.info("convertToCustomerUpdateDto(ViskundeDao viskunde)");
    	//Sanity checks
		if (viskunde.getKundnr() == 0) {
			String errMsg = "KUNDNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		} 
	
		CustomerUpdateDto dto = new CustomerUpdateDto();
		dto.setNumber(Helper.toDtoString(viskunde.getKundnr()));
		dto.setName(Helper.toDtoString(viskunde.getKnavn()));
		dto.setCorporateId(Helper.toDtoString(viskunde.getSyrg())); 
		dto.setMainAddress(getMainAddress(viskunde));
		dto.setStatus(getStatus(viskunde));
		dto.setMainContact(getMainContact(viskunde));
		
		dto.setCreditTermsId(Helper.toDtoString(viskunde.getBetbet())); 
		
		dto.setCustomerClassId(Helper.toDtoString(1)); //Hardcode
		
		return dto;
	}

	private DtoValueContactInfoUpdateDto getMainContact(ViskundeDao viskunde) {
		DtoValueContactInfoUpdateDto dtoValue = new DtoValueContactInfoUpdateDto();
		
		ContactInfoUpdateDto infoDto = new ContactInfoUpdateDto();
		infoDto.setName(Helper.toDtoString(viskunde.getKnavn()));
		infoDto.setAttention(Helper.toDtoString(viskunde.getKpers()));
		infoDto.setEmail(Helper.toDtoString(viskunde.getSyepos()));
		infoDto.setPhone1(Helper.toDtoString(viskunde.getTlf()));
		
		dtoValue.setValue(infoDto);
		
		return dtoValue;
	}

	private DtoValueCustomerStatus getStatus(ViskundeDao viskunde) {
		//sanity check
		if (viskunde.getAktkod() == null) {
			String errMsg = "AKTKOD can not be null";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);			
		}
		DtoValueCustomerStatus dtoValue = new DtoValueCustomerStatus();
		
		if (viskunde.getAktkod().equals("A")) { 
			dtoValue.setValue(DtoValueCustomerStatus.ValueEnum.ACTIVE);
		} else if (viskunde.getAktkod().equals("I")) {
			dtoValue.setValue(DtoValueCustomerStatus.ValueEnum.INACTIVE);
		} else {
			String errMsg = "AKTKOD must be A or I. Fallback, setting status to ONHOLD";
			logger.error(errMsg);
			dtoValue.setValue(DtoValueCustomerStatus.ValueEnum.ONHOLD);
		}
		
		return dtoValue;
	}


	private DtoValueAddressUpdateDto getMainAddress(ViskundeDao viskunde) {
		DtoValueAddressUpdateDto dtoValueDto = new DtoValueAddressUpdateDto();

		AddressUpdateDto addressdto = new AddressUpdateDto();
		addressdto.setAddressLine1(Helper.toDtoString(viskunde.getAdr1()));
		addressdto.setAddressLine2(Helper.toDtoString(viskunde.getAdr2()));
		addressdto.setAddressLine3(Helper.toDtoString(viskunde.getAdr3()));
		addressdto.countryId(Helper.toDtoString(viskunde.getSyland()));
		addressdto.setPostalCode(Helper.toDtoString(viskunde.getPostnr()));
		addressdto.setCity(Helper.toDtoString(viskunde.getAdr3()));
		
		dtoValueDto.setValue(addressdto);

		return dtoValueDto;
	}

    /**
     * Get Customer Classes
     * 
     * Kundeprofil:
     * 
     * <p><b>200</b> - OK
     * @return List&lt;CustomerClassDto&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<CustomerClassDto> customerGetCustomerClasses() throws RestClientException {
    	List<CustomerClassDto> dtoList;
		try {
			dtoList = customerApi.customerGetCustomerClasses();
		} catch (RestClientException e) {
			throw e;
		}
		return dtoList;
	}	
	
}
