package no.systema.visma.integration;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.dto.VistranskHeadDto;
import no.systema.visma.dto.VistranskLineDto;
import no.systema.visma.v1client.api.CustomerInvoiceApi;
import no.systema.visma.v1client.model.CustomerDto;
import no.systema.visma.v1client.model.CustomerInvoiceDto;
import no.systema.visma.v1client.model.CustomerInvoiceLinesUpdateDto;
import no.systema.visma.v1client.model.CustomerInvoiceLinesUpdateDto.OperationEnum;
import no.systema.visma.v1client.model.CustomerInvoiceUpdateDto;
import no.systema.visma.v1client.model.DtoValueDateTime;
import no.systema.visma.v1client.model.DtoValueString;
import no.systema.visma.v1client.model.SegmentUpdateDto;
import no.systema.visma.v1client.model.TaxDetailUpdateDto;

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

//		customerInvoiceApi.getApiClient().setDebugging(true); //Warning...debugging in VismaClientHttpRequestInceptor

	}

	/**
	 * This is the startingpoint for syncronizing SYSPED VISTRANSK with
	 * Visma-net CustomerInvoice.
	 * 
	 * @param vistranskHeadDto
	 * @throws RestClientException
	 * @throws HttpClientErrorException
	 */
	public void syncronize(VistranskHeadDto vistranskHeadDto) throws RestClientException, HttpClientErrorException {
		logger.info(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr(), vistranskHeadDto.getPosnr()));
		logger.info("syncronize(VistranskHeadDto vistranskHeadDto)");

		// For both New and Update
		CustomerInvoiceUpdateDto updateDto = convertToCustomerInvoiceUpdateDto(vistranskHeadDto);

		try {
			// Sanity check
			CustomerDto customerExistDto = customer.getGetBycustomerCd(String.valueOf(vistranskHeadDto.getRecnr()));
			if (customerExistDto == null) {
				logger.error("Could not find Customer on number:" + vistranskHeadDto.getRecnr());
				throw new RuntimeException("Could not find Customer on number:" + vistranskHeadDto.getRecnr());
			} else { // do the thing
				String referenceNumber = String.valueOf(vistranskHeadDto.getBilnr());
				CustomerInvoiceDto customerInvoiceExistDto = getByinvoiceNumber(referenceNumber);
				if (customerInvoiceExistDto != null) {

					customerInvoiceUpdateByinvoiceNumber(referenceNumber, updateDto);
					logger.info("Kundetransaksjon:" + vistranskHeadDto.getRecnr() + " is updated.");

				} else {

					customerInvoiceCreate(updateDto);
					logger.info("Kundetransaksjon:" + vistranskHeadDto.getRecnr() + " is created.");

				}
			}

		} catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr(), vistranskHeadDto.getPosnr()));
			logger.error(e.getClass() + " On syncronize.  vistranskHeadDto=" + vistranskHeadDto.toString());
			logger.error("message:" + e.getMessage());
			logger.error("status text:" + new String(e.getStatusText())); // Status text contains Response body from Visma.net
			throw e;
		} catch (RestClientException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr(), vistranskHeadDto.getPosnr()));
			logger.error(e.getClass() + " On syncronize.  vistranskHeadDto=" + vistranskHeadDto.toString());
			throw e;
		} catch (Exception e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr(), vistranskHeadDto.getPosnr()));
			logger.error(e.getClass() + " On syncronize.  vistranskHeadDto=" + vistranskHeadDto.toString());
			throw e;
		}

	}

	/**
	 * Get a specific Invoice Data for Customer Invoice
	 * <p>
	 * <b>200</b> - OK
	 * 
	 * @param invoiceNumber
	 *            Identifies the Invoice
	 * @return CustomerInvoiceDto
	 * @throws RestClientException
	 *             if an error occurs while attempting to invoke the API
	 */
	public CustomerInvoiceDto getByinvoiceNumber(String invoiceNumber) throws RestClientException {
		logger.info("getByinvoiceNumber(String invoiceNumber)");
		CustomerInvoiceDto customerInvoiceExistDto;

		try {

			customerInvoiceExistDto = customerInvoiceApi.customerInvoiceGetByinvoiceNumber(invoiceNumber);

		} catch (HttpClientErrorException e) {
			logger.info("message:" + e.getMessage() + ", customerInvoiceExistDto is null, continue...");
			customerInvoiceExistDto = null;
			// continue
		}

		return customerInvoiceExistDto;

	}

	/**
	 * Update a specific Invoice Response Message has StatusCode NoContent if
	 * PUT operation succeed
	 * <p>
	 * <b>204</b> - NoContent
	 * 
	 * @param invoiceNumber
	 *            Identifies the Invoice to update
	 * @param invoice
	 *            Defines the data for the Invoice to update
	 * @throws RestClientException
	 *             if an error occurs while attempting to invoke the API
	 */
	public void customerInvoiceUpdateByinvoiceNumber(String invoiceNumber, CustomerInvoiceUpdateDto invoice) throws RestClientException {

		// TODO impl. invoice update

	}

	/**
	 * Create an Invoice Response Message has StatusCode Created if POST
	 * operation succeed
	 * <p>
	 * <b>201</b> - Created
	 * 
	 * @param invoice
	 *            Defines the data for the Invoice to create
	 * @throws RestClientException
	 *             if an error occurs while attempting to invoke the API
	 */
	public void customerInvoiceCreate(CustomerInvoiceUpdateDto updateDto) throws RestClientException {
		logger.info(LogHelper.logPrefixCustomerInvoice(updateDto.getCustomerNumber(), updateDto.getReferenceNumber(), null)); 
		logger.info("customerInvoiceCreate(CustomerInvoiceUpdateDto updateDt)");

		try {

			customerInvoiceApi.customerInvoiceCreate(updateDto);

		} catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(updateDto.getCustomerNumber(), updateDto.getReferenceNumber(), null)); 
			logger.error(e.getClass() + " On customerInvoiceApi.customerInvoiceCreate call. updateDto=" + updateDto.toString());
			throw e;
		} catch (RestClientException | IllegalArgumentException | IndexOutOfBoundsException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(updateDto.getCustomerNumber(), updateDto.getReferenceNumber(), null));
			logger.error(e.getClass() + " On customerInvoiceApi.customerInvoiceCreate call. updateDto=" + updateDto.toString(), e);
			throw e;
		} catch (Exception e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(updateDto.getCustomerNumber(), updateDto.getReferenceNumber(), null));
			logger.error(e.getClass() + " On customerInvoiceApi.customerInvoiceCreate call. updateDto=" + updateDto.toString());
			throw e;
		}

	}

	private CustomerInvoiceUpdateDto convertToCustomerInvoiceUpdateDto(VistranskHeadDto vistranskHeadDto) {
		logger.info("convertToCustomerInvoiceUpdateDto(VistranskHeadDto vistranskHeadDto)");
		
		mandatoryCheck(vistranskHeadDto);
		
		// Head
		CustomerInvoiceUpdateDto dto = new CustomerInvoiceUpdateDto();
		dto.setCustomerNumber(DtoValueHelper.toDtoString(vistranskHeadDto.getRecnr()));
		//TODO find right Date format
		//		dto.setDocumentDate((DtoValueHelper.toDtoValueDateTime(vistranskHeadDto.getFfdaar(), vistranskHeadDto.getFfdmnd(), vistranskHeadDto.getFfddag())));
		dto.setReferenceNumber(DtoValueHelper.toDtoString(vistranskHeadDto.getRecnr()));
		dto.setFinancialPeriod(getFinancialsPeriod(vistranskHeadDto));
		dto.setCreditTermsId(DtoValueHelper.toDtoString(vistranskHeadDto.getBetbet()));
//		dto.setDocumentDueDate(DtoValueHelper.toDtoValueDateTime(vistranskHeadDto.getFfdaar(), vistranskHeadDto.getFfdmnd(), vistranskHeadDto.getFfddag()));
		// Note: same as DocumentDueDate
//		dto.setCashDiscountDate(DtoValueHelper.toDtoValueDateTime(vistranskHeadDto.getFfdaar(), vistranskHeadDto.getFfdmnd(), vistranskHeadDto.getFfddag()));		
		dto.setLocationId(DtoValueHelper.toDtoString("Main")); // TODO verify Main

		//Tax lines
//		dto.setTaxDetailLines(getTaxDetailLines(vistranskHeadDto.getLines()));
		
		
		// Lines
		dto.setInvoiceLines(getInvoiceLines(vistranskHeadDto.getLines()));

		return dto;

	}

	// Sanity checks
	private void mandatoryCheck(VistranskHeadDto vistranskHeadDto) {
		if (vistranskHeadDto.getRecnr() == 0) {
			String errMsg = "RECNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranskHeadDto.getBilnr() == 0) {
			String errMsg = "BILNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranskHeadDto.getPosnr() == 0) {
			String errMsg = "POSNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranskHeadDto.getKrdaar() == 0) {
			String errMsg = "KRDAAR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}	
		if (vistranskHeadDto.getKrdmnd() == 0) {
			String errMsg = "KRDMND can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranskHeadDto.getKrddag() == 0) {
			String errMsg = "KRDDAG can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		if (vistranskHeadDto.getFfdaar() == 0) {
			String errMsg = "FFDAAR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		if (vistranskHeadDto.getFfdmnd() == 0) {
			String errMsg = "FFDMND can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}	
		if (vistranskHeadDto.getFfddag() == 0) {
			String errMsg = "FFDDAG can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		
		
	}

//	private DtoValueDateTime getDocumentDueDate(VistranskHeadDto vistranskHeadDto) {
//		DtoValueDateTime dto = new DtoValueDateTime();
//		LocalDateTime value = LocalDateTime.of(vistranskHeadDto.getFfdaar(), vistranskHeadDto.getFfdmnd(), vistranskHeadDto.getFfddag(), 10, 10, 10);
////		OffsetDateTime value = OffsetDateTime.of(vistranskHeadDto.getKrdaar(), vistranskHeadDto.getKrdmnd(), vistranskHeadDto.getKrddag(), 0, 0, 0, 0, ZoneOffset.UTC);		
//
//		dto.setValue(value);
//
//		return dto;
//	}
//
//	private DtoValueDateTime getDocumentDate(VistranskHeadDto vistranskHeadDto) {
//		DtoValueDateTime dto = new DtoValueDateTime();
//		LocalDateTime value = LocalDateTime.of(vistranskHeadDto.getFfdaar(), vistranskHeadDto.getFfdmnd(), vistranskHeadDto.getFfddag(), 10, 10, 10);
//
////		OffsetDateTime value = OffsetDateTime.of(vistranskHeadDto.getKrdaar(), vistranskHeadDto.getKrdmnd(), vistranskHeadDto.getKrddag(), 0, 0, 0, 0, ZoneOffset.UTC);		
//		
//		logger.info("value" + value);
//		logger.info("now=" + LocalDateTime.now());
//
//		value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//
//		String text = value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//		LocalDateTime parsedDate = LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//
//		logger.info("parsedDate" + parsedDate);
//		
//		dto.setValue(value);
//
//		return dto;
//	}	
	
	
	
	private DtoValueString getFinancialsPeriod(VistranskHeadDto vistranskHeadDto) {
		String year = String.valueOf(vistranskHeadDto.getPeraar());
		String month = String.format("%02d", vistranskHeadDto.getPernr()); // pad up to 2 char, ex. 1 -> 01

		return DtoValueHelper.toDtoString(year + month); // ex. 201805

	}

	private List<TaxDetailUpdateDto> getTaxDetailLines(List<VistranskLineDto> lineDtoList) {
		List<TaxDetailUpdateDto> updateDtoList = new ArrayList<TaxDetailUpdateDto>();

		lineDtoList.forEach(lineDto -> {

			mandatoryCheck(lineDto);
			
			TaxDetailUpdateDto updateDto = new TaxDetailUpdateDto();
//			updateDto.setTaxId(DtoValueHelper.toDtoString(lineDto.getMomsk())); //TODO funkar inte
			//TODO the rest of taxLines
			updateDtoList.add(updateDto);
			
		});		
		
		return updateDtoList;
	}	
	
	
	
	
	
	private List<CustomerInvoiceLinesUpdateDto> getInvoiceLines(List<VistranskLineDto> lineDtoList) {
		List<CustomerInvoiceLinesUpdateDto> updateDtoList = new ArrayList<CustomerInvoiceLinesUpdateDto>();

		lineDtoList.forEach(lineDto -> {

			mandatoryCheck(lineDto);
			
			CustomerInvoiceLinesUpdateDto updateDto = new CustomerInvoiceLinesUpdateDto();
			updateDto.setLineNumber(DtoValueHelper.toDtoValueInt32((lineDto.getPosnr())));
			updateDto.setQuantity(DtoValueHelper.toDtoDecimal(1.0)); // Hardcode to 1
			updateDto.setUnitPriceInCurrency(DtoValueHelper.toDtoDecimal(lineDto.getBbelop())); // BBELOP 11 2
			updateDto.setVatCodeId(DtoValueHelper.toDtoString(lineDto.getMomsk()));  //?
			updateDto.setAccountNumber(DtoValueHelper.toDtoString(lineDto.getKonto()));
			updateDto.setSubaccount(Arrays.asList(new SegmentUpdateDto().segmentValue(String.valueOf(lineDto.getKbarer()))));
			updateDto.setDescription(DtoValueHelper.toDtoString(lineDto.getBiltxt()));
			updateDto.setOperation(OperationEnum.INSERT);  //TODO respect New and update, now hardcode for testing
			
			updateDtoList.add(updateDto);
			
		});
		
		return updateDtoList;
		
	}

	//Sanity checks
	private void mandatoryCheck(VistranskLineDto lineDto) {
		if (lineDto.getPosnr() == 0) {
			String errMsg = "POSNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (lineDto.getBbelop() == null) {
			String errMsg = "BBELOP can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (lineDto.getMomsk() == null) {
			String errMsg = "MOMSK can not be empty";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		if (lineDto.getKonto() == 0) {
			String errMsg = "KONTO can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		if (lineDto.getKbarer() == 0) {
			String errMsg = "KBARER can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (lineDto.getBiltxt() == null) {
			String errMsg = "BILTXT can not be empty";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		if (lineDto.getMomsk() == null) {
			String errMsg = "MOMSK can not be empty";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}	
		if (lineDto.getKonto() == 0) {
			String errMsg = "KONTO can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}			
		
	}

}
