package no.systema.visma.integration;

import java.util.ArrayList;
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
import no.systema.jservices.common.dao.VistranskDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.v1client.ApiClient;
import no.systema.visma.v1client.api.CustomerInvoiceApi;
import no.systema.visma.v1client.model.CustomerDto;
import no.systema.visma.v1client.model.CustomerInvoiceDto;
import no.systema.visma.v1client.model.CustomerInvoiceLinesUpdateDto;
import no.systema.visma.v1client.model.CustomerInvoiceUpdateDto;

/**
 * A Wrapper on {@linkplain CustomerInvoiceApi} 
 * 
 * Also see https://integration.visma.net/API-index/#!/CustomerInvoice
 * 
 * @author fredrikmoller
 */	
@Service
public class CustomerInvoice extends Configuration {
	private static Logger logger = Logger.getLogger(CustomerInvoice.class);
	
	@Autowired
	public FirmvisDaoService firmvisDaoService;	
	
	@Autowired
	public CustomerInvoiceApi customerInvoiceApi = new CustomerInvoiceApi(apiClient());	
	
	@Autowired
	public Customer customer;	
	
	@PostConstruct
	public void post_construct() {
		FirmvisDao firmvis = firmvisDaoService.get();

		customerInvoiceApi.getApiClient().setBasePath(firmvis.getVibapa().trim());
		customerInvoiceApi.getApiClient().addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		customerInvoiceApi.getApiClient().addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		customerInvoiceApi.getApiClient().setAccessToken(firmvis.getViacto().trim());			
		
		// customerApi.getApiClient().setDebugging(true);	//Warning...set debugging in VismaNetResponseErrorHandler	
		
	}	
	
	/**
	 * This is the startingpoint for syncronizing SYSPED VISTRANSK with Visma-net CustomerInvoice.
	 * 
	 * @param vistranskDao
	 * @throws RestClientException
	 * @throws HttpClientErrorException
	 */
	public void syncronize(VistranskDao vistranskDao) throws RestClientException,  HttpClientErrorException {
		logger.info("syncronize(ViskundeDao viskundeDao)");
		//For both New and Update
		CustomerInvoiceUpdateDto updateDto = convertToCustomerInvoiceUpdateDto(vistranskDao);			
		
		try {
			
    		CustomerDto customerExistDto = customer.getGetBycustomerCd(String.valueOf(vistranskDao.getFkund()));			
 
    		if (customerExistDto != null) {
    			logger.error("Could not find Customer on number:"+ vistranskDao.getFkund());
    			throw new RuntimeException("Could not find Customer on number:"+ vistranskDao.getFkund());
    			
    		} else {
   
    			//TODO find
//    			getByinvoiceNumber()
    			
    			
    			
    			
    			customerInvoiceCreate(updateDto);
    			logger.info("Kundetransaksjon:"+vistranskDao.getFkund()+ " is created.");
    			
    		}
    		
			
    	} catch (HttpClientErrorException e) {
			logger.error(Helper.logPrefix(vistranskDao.getFkund()));
			logger.error(e.getClass()+" On syncronize.  vistranskDao="+vistranskDao.toString());
			logger.error("message:"+e.getMessage());
			logger.error("status text:"+new String(e.getStatusText()));  //Status text contains Response body from Visma.net
			throw e;
		}
    	catch (RestClientException e) {
    		logger.error(Helper.logPrefix(vistranskDao.getFkund()));
			logger.error(e.getClass()+" On syncronize.  vistranskDao="+vistranskDao.toString());
			throw e;
		}
    	catch (Exception e) {
    		logger.error(Helper.logPrefix(vistranskDao.getFkund()));
			logger.error(e.getClass()+" On syncronize.  vistranskDao="+vistranskDao.toString());
			throw e;
		} 
		
		
	}	
	
    private CustomerInvoiceUpdateDto convertToCustomerInvoiceUpdateDto(VistranskDao vistranskDao) {
    	logger.info("convertToCustomerUpdateDto(ViskundeDao viskunde)");
    	//Sanity checks
		if (vistranskDao.getRecnr() == 0) {
			String errMsg = "RECNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		} 
		if (vistranskDao.getPosnr() == 0) {
			String errMsg = "POSNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		} 
		
		//Head
		CustomerInvoiceUpdateDto dto = new CustomerInvoiceUpdateDto();
		dto.setCustomerNumber(Helper.toDtoString(vistranskDao.getRecnr()));

		
		
		
		
		
//		dto.set
		
		
		
		
		
		dto.setCreditTermsId(Helper.toDtoString(vistranskDao.getBetbet())); 
		
		//Lines //TODO
		dto.setInvoiceLines(getInvoiceLines(vistranskDao));
		
		//TODO the rest....
		
		return dto;
   
    	
    	
    	
    	
    	
    	// TODO Auto-generated method stub

   /* 	
        sb.append("    paymentMethodId: ").append(toIndentedString(paymentMethodId)).append("\n");
        sb.append("    creditTermsId: ").append(toIndentedString(creditTermsId)).append("\n");
        sb.append("    currencyId: ").append(toIndentedString(currencyId)).append("\n");
        sb.append("    customerRefNumber: ").append(toIndentedString(customerRefNumber)).append("\n");
        sb.append("    cashDiscountDate: ").append(toIndentedString(cashDiscountDate)).append("\n");
        sb.append("    documentDueDate: ").append(toIndentedString(documentDueDate)).append("\n");
        sb.append("    externalReference: ").append(toIndentedString(externalReference)).append("\n");
        sb.append("    exchangeRate: ").append(toIndentedString(exchangeRate)).append("\n");
        sb.append("    domesticServicesDeductibleDocument: ").append(toIndentedString(domesticServicesDeductibleDocument)).append("\n");
        sb.append("    rotRutDetails: ").append(toIndentedString(rotRutDetails)).append("\n");
        sb.append("    paymentReference: ").append(toIndentedString(paymentReference)).append("\n");
        sb.append("    contact: ").append(toIndentedString(contact)).append("\n");
        sb.append("    project: ").append(toIndentedString(project)).append("\n");
        sb.append("    taxDetailLines: ").append(toIndentedString(taxDetailLines)).append("\n");
        sb.append("    invoiceLines: ").append(toIndentedString(invoiceLines)).append("\n");
        sb.append("    referenceNumber: ").append(toIndentedString(referenceNumber)).append("\n");
        sb.append("    customerNumber: ").append(toIndentedString(customerNumber)).append("\n");
        sb.append("    documentDate: ").append(toIndentedString(documentDate)).append("\n");
        sb.append("    hold: ").append(toIndentedString(hold)).append("\n");
        sb.append("    postPeriod: ").append(toIndentedString(postPeriod)).append("\n");
        sb.append("    financialPeriod: ").append(toIndentedString(financialPeriod)).append("\n");
        sb.append("    invoiceText: ").append(toIndentedString(invoiceText)).append("\n");
        sb.append("    locationId: ").append(toIndentedString(locationId)).append("\n");
        sb.append("    salesPersonID: ").append(toIndentedString(salesPersonID)).append("\n");
        sb.append("    note: ").append(toIndentedString(note)).append("\n");
        sb.append("    branchNumber: ").append(toIndentedString(branchNumber)).append("\n");
        sb.append("    cashAccount: ").append(toIndentedString(cashAccount)).append("\n");   	
    	
   */ 	
	}

	private List<CustomerInvoiceLinesUpdateDto> getInvoiceLines(VistranskDao vistranskDao) {
		List<CustomerInvoiceLinesUpdateDto> list = new ArrayList<CustomerInvoiceLinesUpdateDto>();
		CustomerInvoiceLinesUpdateDto updateDto = new CustomerInvoiceLinesUpdateDto();
		updateDto.setQuantity(Helper.toDtoDecimal(vistranskDao.getAnt())); // ANT 9  3
		updateDto.setUnitPriceInCurrency(Helper.toDtoDecimal(vistranskDao.getBbelop())); //BBELOP 11 2
		
		
		//TODO the rest....
		
		
		list.add(updateDto);
		
		return list;
	}

	   /**
     * Get a specific Invoice
     * Data for Customer Invoice
     * <p><b>200</b> - OK
     * @param invoiceNumber Identifies the Invoice
     * @return CustomerInvoiceDto
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CustomerInvoiceDto getByinvoiceNumber(String invoiceNumber) throws RestClientException {
		logger.info("getByinvoiceNumber(String invoiceNumber)");
		CustomerInvoiceDto customerInvoiceExistDto;

		try {

			customerInvoiceExistDto = customerInvoiceApi.customerInvoiceGetByinvoiceNumber(invoiceNumber);
			
		} catch (HttpClientErrorException e) {
			logger.info("message:" + e.getMessage()+ ", customerInvoiceExistDto is null, continue...");
			customerInvoiceExistDto = null;
			// continue
		}

		return customerInvoiceExistDto;    	
    	
    }
	
	
	/**
     * Update a specific Invoice
     * Response Message has StatusCode NoContent if PUT operation succeed
     * <p><b>204</b> - NoContent
     * @param invoiceNumber Identifies the Invoice to update
     * @param invoice Defines the data for the Invoice to update
     * @return Object
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void customerInvoiceUpdateByinvoiceNumber(String invoiceNumber, CustomerInvoiceUpdateDto invoice) throws RestClientException {
    	
    	
    	
    	//TODO impl.
    	
    	
    	
    	
    }
	
	
	
	
	
	/**
     * Create an Invoice
     * Response Message has StatusCode Created if POST operation succeed
     * <p><b>201</b> - Created
     * @param invoice Defines the data for the Invoice to create
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void customerInvoiceCreate(CustomerInvoiceUpdateDto updateDto) throws RestClientException {	
	
    	logger.info(Helper.logPrefix(updateDto.getCustomerNumber()));
    	logger.info("customerInvoiceCreate()"); 
    	
    	try {

    		customerInvoiceApi.customerInvoiceCreate(updateDto);

    		
    	} catch (HttpClientErrorException e) {
    	   	logger.info(Helper.logPrefix(updateDto.getCustomerNumber()));
			logger.error(e.getClass()+" On customerInvoiceApi.customerInvoiceCreate call. updateDto="+updateDto.toString());
			logger.error("message:"+e.getMessage());
			logger.error("status text:"+new String(e.getStatusText()));  //Status text contains Response body from Visma.net
			throw e;
		}
    	catch (RestClientException  | IllegalArgumentException | IndexOutOfBoundsException e) {
			logger.error(e.getClass()+" On customerInvoiceApi.customerInvoiceCreate call. updateDto="+updateDto.toString(), e);
			throw e;
		} 
    	catch (Exception e) {
    	   	logger.info(Helper.logPrefix(updateDto.getCustomerNumber()));
			logger.error(e.getClass()+" On customerInvoiceApi.customerInvoiceCreate call. updateDto="+updateDto.toString());
			throw e;
		}     	   
    
    }
	
	
}
