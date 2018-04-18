package no.systema.visma.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.visma.Configuration;
import no.systema.visma.v1client.api.CustomerApi;
import no.systema.visma.v1client.model.AddressUpdateDto;
import no.systema.visma.v1client.model.CustomerDto;
import no.systema.visma.v1client.model.CustomerUpdateDto;
import no.systema.visma.v1client.model.DtoValueAddressUpdateDto;
import no.systema.visma.v1client.model.DtoValueString;

/**
 * A Wrapper on CustomerApi
 * 
 * Also see https://integration.visma.net/API-index/#!/Customer
 * 
 * @author fredrikmoller
 */
@Service
public class Customer extends Configuration{
	@Autowired
	@Qualifier("no.systema.visma.v1client.api.CustomerApi")
	public CustomerApi customerApi = new CustomerApi(apiClient);
	
//	private static final int NEW = -1;
//	private static final int  UPDATE = -2;
	
	/**
	 * Get a specific customer
	 * 
	 * @param customerCd - Visma-net generated number in api
	 * @return CustomerDto - the customer
	 */
	public CustomerDto getByCustomerCd(String customerCd) {
		return customerApi.customerGetBycustomerCd(customerCd);
	}
	

    /**
     * Creates a customer
     * Response Message has StatusCode Created if POST operation succeed
     * <p><b>201</b> - Created
     * @param viskundeDao Defines the data for the customer to create
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object customerPost(ViskundeDao viskundeDao) throws RestClientException {
    	CustomerUpdateDto updateDto = convertToCustomerUpdateDto(viskundeDao);
 
    	logger.debug("Returning new Customer Object.");
    	return customerApi.customerPost(updateDto);
    }

    /**
     * Updates a specific customer
     * Response Message has StatusCode NoContent if PUT operation succeed
     * <p><b>204</b> - NoContent
     * @param viskundeDao The data to update for the customer
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Object customerPutBycustomerCd(ViskundeDao viskundeDao) throws RestClientException {
    	CustomerUpdateDto updateDto = convertToCustomerUpdateDto(viskundeDao);
 
    	logger.debug("Returning updated Customer Object.");
    	return customerApi.customerPutBycustomerCd(String.valueOf(viskundeDao.getKundnr()), updateDto);
    }
    

    /**
     * Get a range of customers
     * 
     * Used for query in Visma-net API. e.g add VISKUNDE.SYRG to corporateId
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
	 * <b>::Mapping::</b>
	 * <table border="1">
	 * 	<tbody>
	 * 	<tr>
	 * 		<th>VISKUNDE</th>
	 * 		<th>Customer API</th>
	 * 		<th>Visma.net Financials</th>
	 * </tr>
	 * 	<tr>
	 * 		<td>KUNDNR</td>
	 * 		<td>accountReference</td>
	 * 		<td>Vårt lev.- / kundenr:</td>
	 * </tr>
	 * 	<tr>
	 * 		<td>KNAVN</td>
	 * 		<td>name</td>
	 * 		<td>Kundenavn:</td>
	 * </tr>
	 * 	<tr>
	 * 		<td>SYRG</td>
	 * 		<td>corporateId</td>
	 * 		<td>Organisasjonsnr.:</td>
	 * </tr>
	 * </tbody>
	 * 
	 * </table>
	 * 
	 * </br></br>
	 * 
	 * Structure also found here: https://github.com/SystemaAS/visma-net-v1client/blob/master/docs/CustomerUpdateDto.md
	 * 
	 * 
	 * @param viskunde VISKUNDE
	 * @return CustomerUpdateDto
	 */
    private CustomerUpdateDto convertToCustomerUpdateDto(ViskundeDao viskunde) {
    	//Sanity checks
		if (viskunde.getKundnr() == 0) {
			String errMsg = "Kundnr can not be 0";
			logger.fatal("FATAL:"+errMsg);
			throw new RuntimeException(errMsg);
		} else if (viskunde.getSyrg() == null) {
			String errMsg = "Syrg (orgnnr) can not be null.";
			logger.fatal("FATAL:"+errMsg);
			throw new RuntimeException(errMsg);
		}
	
//		CustomerUpdateDto dto = new CustomerUpdateDto().number(toDtoString(viskunde.getKundnr()));
		CustomerUpdateDto dto = new CustomerUpdateDto();
		dto.setAccountReference(toDtoString(viskunde.getKundnr()));
		dto.setName(toDtoString(viskunde.getKnavn()));
		dto.setCorporateId(toDtoString(viskunde.getSyrg()));  //used in query in API
		
		dto.setMainAddress(getMainAddress(viskunde));

		
		
		
		
		
		
		
		//TODO the rest.....
    	
		return dto;
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
	
	
}
