package no.systema.visma.integration;

import java.time.LocalDateTime;
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
import no.systema.jservices.common.dao.VistranskDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.v1client.api.CustomerInvoiceApi;
import no.systema.visma.v1client.model.CustomerDto;
import no.systema.visma.v1client.model.CustomerInvoiceDto;
import no.systema.visma.v1client.model.CustomerInvoiceLinesUpdateDto;
import no.systema.visma.v1client.model.CustomerInvoiceUpdateDto;
import no.systema.visma.v1client.model.DtoValueDateTime;
import no.systema.visma.v1client.model.DtoValueString;
import no.systema.visma.v1client.model.SegmentUpdateDto;

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

		// customerApi.getApiClient().setDebugging(true); //Warning...set debugging in VismaNetResponseErrorHandler

	}

	/**
	 * This is the startingpoint for syncronizing SYSPED VISTRANSK with
	 * Visma-net CustomerInvoice.
	 * 
	 * @param vistranskDao
	 * @throws RestClientException
	 * @throws HttpClientErrorException
	 */
	public void syncronize(VistranskDao vistranskDao) throws RestClientException, HttpClientErrorException {
		logger.info("syncronize(VistranskDao vistranskDao)");
		// For both New and Update
		CustomerInvoiceUpdateDto updateDto = convertToCustomerInvoiceUpdateDto(vistranskDao);

		try {
			// Sanity check
			CustomerDto customerExistDto = customer.getGetBycustomerCd(String.valueOf(vistranskDao.getRecnr()));
			if (customerExistDto != null) {
				logger.error("Could not find Customer on number:" + vistranskDao.getRecnr());
				throw new RuntimeException("Could not find Customer on number:" + vistranskDao.getRecnr());
			} else { // do the thing
				String referenceNumber = String.valueOf(vistranskDao.getBilnr());
				CustomerInvoiceDto customerInvoiceExistDto = getByinvoiceNumber(referenceNumber);
				if (customerInvoiceExistDto != null) {

					customerInvoiceUpdateByinvoiceNumber(referenceNumber, updateDto);
					logger.info("Kundetransaksjon:" + vistranskDao.getRecnr() + " is updated.");

				} else {

					customerInvoiceCreate(updateDto);
					logger.info("Kundetransaksjon:" + vistranskDao.getRecnr() + " is created.");

				}
			}

		} catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskDao.getRecnr(), vistranskDao.getBilnr(), vistranskDao.getPosnr()));
			logger.error(e.getClass() + " On syncronize.  vistranskDao=" + vistranskDao.toString());
			logger.error("message:" + e.getMessage());
			logger.error("status text:" + new String(e.getStatusText())); // Status text contains Response body from Visma.net
			throw e;
		} catch (RestClientException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskDao.getRecnr(), vistranskDao.getBilnr(), vistranskDao.getPosnr()));
			logger.error(e.getClass() + " On syncronize.  vistranskDao=" + vistranskDao.toString());
			throw e;
		} catch (Exception e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskDao.getRecnr(), vistranskDao.getBilnr(), vistranskDao.getPosnr()));
			logger.error(e.getClass() + " On syncronize.  vistranskDao=" + vistranskDao.toString());
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
		logger.info(LogHelper.logPrefixCustomer(updateDto.getCustomerNumber()));
		logger.info("customerInvoiceCreate()");

		try {

			customerInvoiceApi.customerInvoiceCreate(updateDto);

		} catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(updateDto.getCustomerNumber(), updateDto.getReferenceNumber(), null)); //TODO fix
			logger.error(e.getClass() + " On customerInvoiceApi.customerInvoiceCreate call. updateDto=" + updateDto.toString());
			throw e;
		} catch (RestClientException | IllegalArgumentException | IndexOutOfBoundsException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(updateDto.getCustomerNumber(), updateDto.getReferenceNumber(), null)); //TODO fix
			logger.error(e.getClass() + " On customerInvoiceApi.customerInvoiceCreate call. updateDto=" + updateDto.toString(), e);
			throw e;
		} catch (Exception e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(updateDto.getCustomerNumber(), updateDto.getReferenceNumber(), null)); //TODO fix
			logger.error(e.getClass() + " On customerInvoiceApi.customerInvoiceCreate call. updateDto=" + updateDto.toString());
			throw e;
		}

	}

	private CustomerInvoiceUpdateDto convertToCustomerInvoiceUpdateDto(VistranskDao vistranskDao) {
		logger.info("convertToCustomerUpdateDto(ViskundeDao viskunde)");
		// Sanity checks
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

		// Head //TODO cache it somehow
		CustomerInvoiceUpdateDto dto = new CustomerInvoiceUpdateDto();
		dto.setCustomerNumber(DtoValueHelper.toDtoString(vistranskDao.getRecnr()));
		dto.setDocumentDate(getDocumentDate(vistranskDao));
		dto.setReferenceNumber(DtoValueHelper.toDtoString(vistranskDao.getRecnr()));
		dto.setFinancialPeriod(getFinancialsPeriod(vistranskDao));
		dto.setCreditTermsId(DtoValueHelper.toDtoString(vistranskDao.getBetbet()));
		dto.setDocumentDueDate(getDocumentDueDate(vistranskDao));
		dto.setCashDiscountDate(getDocumentDueDate(vistranskDao)); // Note: same as DocumentDueDate
		dto.setLocationId(DtoValueHelper.toDtoString("Main")); // TODO verify Main

		// Lines
		dto.setInvoiceLines(getInvoiceLines(vistranskDao));

		return dto;

	}

	private DtoValueDateTime getDocumentDueDate(VistranskDao vistranskDao) {
		DtoValueDateTime dto = new DtoValueDateTime();
		LocalDateTime value = LocalDateTime.of(vistranskDao.getFfdaar(), vistranskDao.getFfdmnd(), vistranskDao.getFfddag(), 0, 0);

		dto.setValue(value);

		return dto;
	}

	private DtoValueString getFinancialsPeriod(VistranskDao vistranskDao) {
		String year = String.valueOf(vistranskDao.getPeraar());
		String month = String.format("%02d", vistranskDao.getPernr()); // pad up to 2 char, ex. 1 -> 01

		return DtoValueHelper.toDtoString(year + month); // ex. 201805

	}

	private DtoValueDateTime getDocumentDate(VistranskDao vistranskDao) {
		DtoValueDateTime dto = new DtoValueDateTime();
		LocalDateTime value = LocalDateTime.of(vistranskDao.getKrdaar(), vistranskDao.getKrdmnd(), vistranskDao.getKrddag(), 0, 0);

		dto.setValue(value);

		return dto;
	}

	private List<CustomerInvoiceLinesUpdateDto> getInvoiceLines(VistranskDao vistranskDao) {
		List<CustomerInvoiceLinesUpdateDto> list = new ArrayList<CustomerInvoiceLinesUpdateDto>();
		CustomerInvoiceLinesUpdateDto updateDto = new CustomerInvoiceLinesUpdateDto();

		updateDto.setQuantity(DtoValueHelper.toDtoDecimal(1)); // Hardcode to 1
		updateDto.setUnitPriceInCurrency(DtoValueHelper.toDtoDecimal(vistranskDao.getBbelop())); // BBELOP 11 2
		updateDto.setVatCodeId(DtoValueHelper.toDtoString(vistranskDao.getMomsk()));
		updateDto.setAccountNumber(DtoValueHelper.toDtoString(vistranskDao.getKonto()));
		updateDto.setSubaccount(Arrays.asList(new SegmentUpdateDto().segmentValue(String.valueOf(vistranskDao.getKbarer()))));
		updateDto.setDescription(DtoValueHelper.toDtoString(vistranskDao.getBiltxt()));

		list.add(updateDto);

		return list;
	}

}
