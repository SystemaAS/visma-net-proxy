package no.systema.visma.integration;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
//import no.systema.jservices.common.util.StringUtils;
import no.systema.visma.Configuration;
import no.systema.visma.v1client.ApiClient;
import no.systema.visma.v1client.api.CustomerApi;
import no.systema.visma.v1client.model.AddressUpdateDto;
import no.systema.visma.v1client.model.CustomerDto;
import no.systema.visma.v1client.model.CustomerUpdateDto;
import no.systema.visma.v1client.model.DtoValueAddressUpdateDto;
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
public class Customer extends Configuration {
	private static Logger logger = Logger.getLogger(Customer.class);
	
	@Autowired
	public Configuration configuration;	

	@Autowired
	public FirmvisDaoService firmvisDaoService;	
	
	@Autowired
	public CustomerApi customerApi = new CustomerApi(apiClient());
	
	@Bean
	public ApiClient apiClient(){
		return new ApiClient();
	}
	
	@PostConstruct
	public void post_construct() {
		FirmvisDao firmvis = firmvisDaoService.get();

		customerApi.getApiClient().setBasePath(firmvis.getVibapa().trim());
		customerApi.getApiClient().addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		customerApi.getApiClient().addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		customerApi.getApiClient().setAccessToken(firmvis.getViacto().trim());			
		
		customerApi.getApiClient().setDebugging(true);		
		
	}
	
    /**
     * Updates a specific customer
     * 
     * NOTE: There is no delete in the api. Work with Status.
     * 
     * Response Message has StatusCode NoContent if PUT operation succeed
     * <p><b>204</b> - NoContent
     * @param number Visma.net number (kundnr.:)
     * @param viskundeDao The data to update for the customer
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void customerPutBycustomerCd(String number, ViskundeDao viskundeDao) throws RestClientException {
    	CustomerDto existingDto = getByCustomerCd(number, viskundeDao.getKundnr());
    	logger.debug("Found CustomerDto="+ReflectionToStringBuilder.toString(existingDto));

    	CustomerUpdateDto updateDto = convertToCustomerUpdateDto(viskundeDao);

    	try {
    		customerApi.customerPutBycustomerCd(number, updateDto);
    		logger.info("Customer updated.");
    		logger.debug("::customerPutBycustomerCd::Response headers="+customerApi.getApiClient().getResponseHeaders());
		} catch (RestClientException e) {
			logger.error(logPrefix(viskundeDao.getKundnr(), number));
			logger.error("ERROR: On customerApi.customerPutBycustomerCd call. number="+number+", viskundeDao="+viskundeDao.toString(), e);
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
    public int customerPost(ViskundeDao viskundeDao) throws RestClientException,IllegalArgumentException, IndexOutOfBoundsException {
    	CustomerUpdateDto updateDto = convertToCustomerUpdateDto(viskundeDao);
    	int number;
    	try {
			customerApi.customerPost(updateDto);
			logger.info("Customer created.");
    		logger.debug("::customerPost::Response headers="+customerApi.getApiClient().getResponseHeaders());
    		number = getGenereratedNumberFromVisma();
    		logger.info("Generated Visma.net number="+number+" for kundr="+viskundeDao.getKundnr());
		} catch (RestClientException  | IllegalArgumentException | IndexOutOfBoundsException e) {
			logger.error("ERROR: On customerApi.customerPost call. viskundeDao="+viskundeDao.toString(), e);
			throw e;
		} 
    	
    	return number;
    }

    /**
     * Since Create Customer is done by POST, there is no response body returned
     * Instead Response Header is used.
     * The key - Location hold a re-direct url with trailing generated number (Kundnr:)
     * 
     * @return number the generated Kundnr.:
     * @throws RuntimeException if Location is not found in Response Headers
     * @throws IndexOutOfBoundsException if , still, Location is not found in Response Headers
     */
    private int getGenereratedNumberFromVisma() throws IndexOutOfBoundsException{
    	String HEADERKEY_LOCATION = "Location";
    	MultiValueMap<String, String> responseHeaders = customerApi.getApiClient().getResponseHeaders();
    	if (responseHeaders.containsKey(HEADERKEY_LOCATION)) {
    		List<String> locationList = responseHeaders.get(HEADERKEY_LOCATION);
    		String location = locationList.get(0);
    		return parseLocationForNumber(location);
    	} else {
    		String errMsg = "Could not find key Location in Response Headers";
    		logger.error(errMsg);
    		throw new RuntimeException(errMsg);
    	}
    	
	}
	
    private int parseLocationForNumber(String location) {
		logger.debug("Location="+location);
    	String basePath = customerApi.getApiClient().getBasePath();
    	String subPath = "/controller/api/v1/customer";	//TODO find a way to remove hardcode.
    	
    	String callUrl = basePath + subPath;
		int lastSlash = StringUtils.indexOfDifference(location, callUrl);
		String numberAsString = StringUtils.substring(location, lastSlash + 1);
		int number = Integer.valueOf(numberAsString);
		logger.debug("number="+number);

    	return number;
	}

	/**
     * Get a range of customers
     * 
     * Used for query in Visma-net API. e.g. add VISKUNDE.SYRG to corporateId
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
    	//Sanity checks
		if (viskunde.getKundnr() == 0) {
			String errMsg = "KUNDNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		} 
	
		CustomerUpdateDto dto = new CustomerUpdateDto();
		dto.setAccountReference(toDtoString(viskunde.getKundnr()));
		dto.setName(toDtoString(viskunde.getKnavn()));
		dto.setCorporateId(toDtoString(viskunde.getSyrg()));  //used in query in API
		
		dto.setMainAddress(getMainAddress(viskunde));
		dto.setStatus(getStatus(viskunde));

		
		//TODO the rest.....
		
		//TESTING
		//dto.setCreditTermsId(new DtoValueString().value("asdfasdfasdfasdfasdfasdfasdfsadfasdfasdfasdfasdfasdfasdfasdfasdf"));
    	//REMOVE
		return dto;
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
		addressdto.setAddressLine1(toDtoString(viskunde.getAdr1()));
		addressdto.setAddressLine2(toDtoString(viskunde.getAdr2()));
		addressdto.setAddressLine3(toDtoString(viskunde.getAdr3()));
		addressdto.countryId(toDtoString(viskunde.getSyland()));
		addressdto.setPostalCode(toDtoString(viskunde.getPostnr()));
		addressdto.setCity(toDtoString(viskunde.getAdr3()));
		
		dtoValueDto.setValue(addressdto);

		return dtoValueDto;
	}


/* alla   	
        sb.append("    : ").append(toIndentedString(number)).append("\n");  /
        sb.append("    : ").append(toIndentedString(name)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    accountReference: ").append(toIndentedString(accountReference)).append("\n");
        sb.append("    parentRecordNumber: ").append(toIndentedString(parentRecordNumber)).append("\n");
        sb.append("    currencyId: ").append(toIndentedString(currencyId)).append("\n");
        sb.append("    creditLimit: ").append(toIndentedString(creditLimit)).append("\n");
        sb.append("    creditDaysPastDue: ").append(toIndentedString(creditDaysPastDue)).append("\n");
        sb.append("    overrideWithClassValues: ").append(toIndentedString(overrideWithClassValues)).append("\n");
        sb.append("    customerClassId: ").append(toIndentedString(customerClassId)).append("\n");
        sb.append("    creditTermsId: ").append(toIndentedString(creditTermsId)).append("\n");
        sb.append("    printInvoices: ").append(toIndentedString(printInvoices)).append("\n");
        sb.append("    acceptAutoInvoices: ").append(toIndentedString(acceptAutoInvoices)).append("\n");
        sb.append("    sendInvoicesByEmail: ").append(toIndentedString(sendInvoicesByEmail)).append("\n");
        sb.append("    printStatements: ").append(toIndentedString(printStatements)).append("\n");
        sb.append("    sendStatementsByEmail: ").append(toIndentedString(sendStatementsByEmail)).append("\n");
        sb.append("    printMultiCurrencyStatements: ").append(toIndentedString(printMultiCurrencyStatements)).append("\n");
        sb.append("    vatRegistrationId: ").append(toIndentedString(vatRegistrationId)).append("\n");
        sb.append("    corporateId: ").append(toIndentedString(corporateId)).append("\n");
        sb.append("    vatZoneId: ").append(toIndentedString(vatZoneId)).append("\n");
        sb.append("    note: ").append(toIndentedString(note)).append("\n");
        sb.append("    mainAddress: ").append(toIndentedString(mainAddress)).append("\n");
        sb.append("    mainContact: ").append(toIndentedString(mainContact)).append("\n");
        sb.append("    creditVerification: ").append(toIndentedString(creditVerification)).append("\n");
        sb.append("    invoiceAddress: ").append(toIndentedString(invoiceAddress)).append("\n");
        sb.append("    invoiceContact: ").append(toIndentedString(invoiceContact)).append("\n");
        sb.append("    statementType: ").append(toIndentedString(statementType)).append("\n");
        sb.append("    deliveryAddress: ").append(toIndentedString(deliveryAddress)).append("\n");
        sb.append("    deliveryContact: ").append(toIndentedString(deliveryContact)).append("\n");
        sb.append("    priceClassId: ").append(toIndentedString(priceClassId)).append("\n");
        sb.append("    directDebitLines: ").append(toIndentedString(directDebitLines)).append("\n");
        sb.append("    attributeLines: ").append(toIndentedString(attributeLines)).append("\n");
 */  			
		

	private DtoValueString toDtoString(java.lang.Object o) {
		if (o == null) {
			logger.error("Object is null.");
			return null;
		}

		return new DtoValueString().value(o.toString());

	}
	
	/**
	 * Get a specific customer.
	 * @param kundnre 
	 * 
	 * @param customerCd - Visma-net generated number in api
	 * @return CustomerDto - the customer
	 * @throws RestClientException if Customer is not found
	 */
	public CustomerDto getByCustomerCd(String number, int kundnr) throws RestClientException{
		CustomerDto dto;
		try {
			dto = customerApi.customerGetBycustomerCd(number);
		} catch (RestClientException e) {
			logger.error(logPrefix(kundnr, number));
			logger.error("Could not find Customer on Visma.net number="+number+" and SYSPED kundnr="+kundnr);
			throw e;
		}
		return dto;
	}

	public static String logPrefix(int kundnr, String number) {
		StringBuilder sb = new StringBuilder();
		sb.append("::KUNDNR:").append(kundnr).append(", number:"+number);
		
		return sb.toString();
		
	}
	
}
