package no.systema.visma.integration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.visma.dto.VistranskLineDto;
import no.systema.visma.dto.VistranslHeadDto;
import no.systema.visma.dto.VistranslLineDto;
import no.systema.visma.v1client.api.SupplierInvoiceApi;
import no.systema.visma.v1client.model.CustomerInvoiceDto;
import no.systema.visma.v1client.model.CustomerInvoiceUpdateDto;
import no.systema.visma.v1client.model.DtoValueString;
import no.systema.visma.v1client.model.SegmentUpdateDto;
import no.systema.visma.v1client.model.SupplierDto;
import no.systema.visma.v1client.model.SupplierInvoiceDto;
import no.systema.visma.v1client.model.SupplierInvoiceLineUpdateDto;
import no.systema.visma.v1client.model.SupplierInvoiceLineUpdateDto.OperationEnum;
import no.systema.visma.v1client.model.SupplierInvoiceUpdateDto;

/**
 * A Wrapper on {@linkplain SupplierInvoiceApi}
 * 
 * Also see https://integration.visma.net/API-index/#!/SupplierInvoice
 * 
 * @author fredrikmoller
 */
@Service
public class SupplierInvoice extends Configuration {
	private static Logger logger = Logger.getLogger(SupplierInvoice.class);

	@Autowired
	public FirmvisDaoService firmvisDaoService;

	@Autowired
	public SupplierInvoiceApi supplierInvoiceApi = new SupplierInvoiceApi(apiClient());

	@Autowired
	public Supplier supplier;

	@PostConstruct
	public void post_construct() {
		FirmvisDao firmvis = firmvisDaoService.get();

		supplierInvoiceApi.getApiClient().setBasePath(firmvis.getVibapa().trim());
		supplierInvoiceApi.getApiClient().addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		supplierInvoiceApi.getApiClient().addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		supplierInvoiceApi.getApiClient().setAccessToken(firmvis.getViacto().trim());

//		supplierInvoiceApi.getApiClient().setDebugging(true); //Warning...debugging in VismaClientHttpRequestInceptor

	}	

	/**
	 * This is the startingpoint for syncronizing SYSPED VISTRANSL with
	 * Visma-net SupplierInvoice.
	 * 
	 * @param vistranslHeadDto
	 * @throws RestClientException
	 * @throws HttpClientErrorException
	 */
	public void syncronize(VistranslHeadDto vistranslHeadDto) throws RestClientException, HttpClientErrorException {
		logger.info("syncronize(VistranslHeadDto vistranslHeadDto)");
		logger.info(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getRecnr(), vistranslHeadDto.getBilnr()));

		try {
			// Sanity check 1
			SupplierDto supplierExistDto = supplier.getGetBysupplierCd(String.valueOf(vistranslHeadDto.getRecnr()));
			if (supplierExistDto == null) {
				logger.error("Could not find Supplier on number:" + vistranslHeadDto.getRecnr());
				throw new RuntimeException("Could not find Supplier on number:" + vistranslHeadDto.getRecnr());
			} else { // Sanity check 2
				String referenceNumber = String.valueOf(vistranslHeadDto.getBilnr());
				SupplierInvoiceDto supplierInvoiceExistDto = getByinvoiceNumber(referenceNumber);

				if (supplierInvoiceExistDto != null) {
					String errMsg = String.format("Fakturanr: %s already exist, updates not allowed!", vistranslHeadDto.getBilnr());
					logger.error(errMsg);
	    			throw new RuntimeException(errMsg);
				} else {   // do the thing
					SupplierInvoiceUpdateDto updateDto = convertToSupplierInvoiceUpdateDto(vistranslHeadDto);

					supplierInvoicePost(updateDto);
					logger.info("Fakturanr:" + vistranslHeadDto.getBilnr() + " is inserted.");

				}
			}

		} catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getRecnr(), vistranslHeadDto.getBilnr()));
			logger.error(e.getClass() + " On syncronize.  vistranslHeadDto=" + vistranslHeadDto.toString());
			logger.error("message:" + e.getMessage());
			logger.error("status text:" + new String(e.getStatusText())); // Status text contains Response body from Visma.net
			throw e;
		} catch (RestClientException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getRecnr(), vistranslHeadDto.getBilnr()));
			logger.error(e.getClass() + " On syncronize.  vistranslHeadDto=" + vistranslHeadDto.toString());
			throw e;
		} catch (Exception e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getRecnr(), vistranslHeadDto.getBilnr()));
			logger.error(e.getClass() + " On syncronize.  vistranslHeadDto=" + vistranslHeadDto.toString());
			throw e;
		}

	}
	
	/**
	 * Get a specific Invoice Data for Supplier Invoice
	 * <p>
	 * <b>200</b> - OK
	 * 
	 * @param invoiceNumber
	 *            Identifies the Invoice
	 * @return SupplierInvoiceDto
	 * @throws RestClientException
	 *             if an error occurs while attempting to invoke the API
	 */
	public SupplierInvoiceDto getByinvoiceNumber(String invoiceNumber) throws RestClientException {
		logger.info("getByinvoiceNumber(String invoiceNumber)");
		SupplierInvoiceDto supplierInvoiceExistDto;

		try {

			supplierInvoiceExistDto = supplierInvoiceApi.supplierInvoiceGetByinvoiceNumber(invoiceNumber);

		} catch (HttpClientErrorException e) {
			logger.info("message:" + e.getMessage() + ", supplierInvoiceExistDto is null, continue...");
			supplierInvoiceExistDto = null;
			// continue
		}

		return supplierInvoiceExistDto;

	}	
	
	
	/**
	 * Create an Invoice Response 
	 * Message has StatusCode Created if POST operation succeed
	 * <p>
	 * <b>201</b> - Created
	 * 
	 * @param invoice
	 *            Defines the data for the Invoice to create
	 * @throws RestClientException
	 *             if an error occurs while attempting to invoke the API
	 */
	public void supplierInvoicePost(SupplierInvoiceUpdateDto updateDto) throws RestClientException {
		logger.info("supplierInvoiceCreate(SupplierInvoiceUpdateDto updateDto)");
		logger.info(LogHelper.logPrefixSupplierInvoice(updateDto.getSupplierNumber(), updateDto.getReferenceNumber())); 

		try {

			supplierInvoiceApi.supplierInvoicePost(updateDto);

		} catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(updateDto.getSupplierNumber(), updateDto.getReferenceNumber())); 
			logger.error(e.getClass() + " On supplierInvoiceApi.supplierInvoicePost call. updateDto=" + updateDto.toString());
			throw e;
		} catch (RestClientException | IllegalArgumentException | IndexOutOfBoundsException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(updateDto.getSupplierNumber(), updateDto.getReferenceNumber()));
			logger.error(e.getClass() + " On supplierInvoiceApi.supplierInvoicePost call. updateDto=" + updateDto.toString(), e);
			throw e;
		} catch (Exception e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(updateDto.getSupplierNumber(), updateDto.getReferenceNumber()));
			logger.error(e.getClass() + " On supplierInvoiceApi.supplierInvoicePost call. updateDto=" + updateDto.toString());
			throw e;
		}

	}	
	
	private SupplierInvoiceUpdateDto convertToSupplierInvoiceUpdateDto(VistranslHeadDto vistranslHeadDto) {
		logger.info("convertToSupplierInvoiceUpdateDto(VistranslHeadDto vistranslHeadDto)");
		
		mandatoryCheck(vistranslHeadDto);
		
		// Head
		SupplierInvoiceUpdateDto dto = new SupplierInvoiceUpdateDto();
		dto.setSupplierNumber(DtoValueHelper.toDtoString(vistranslHeadDto.getRecnr()));
		dto.setReferenceNumber(DtoValueHelper.toDtoString(vistranslHeadDto.getBilnr()));
		dto.setFinancialPeriod(getFinancialsPeriod(vistranslHeadDto));
		dto.setCreditTermsId(DtoValueHelper.toDtoString(vistranslHeadDto.getBetbet()));
//		dto.setLocationId(DtoValueHelper.toDtoString("Main")); // TODO verify Main, behövs den?
		dto.setDueDate(DtoValueHelper.toDtoValueDateTime(vistranslHeadDto.getFfdaar(), vistranslHeadDto.getFfdmnd(), vistranslHeadDto.getFfddag()));
		// Note: same as DueDate
		dto.setCashDiscountDate(DtoValueHelper.toDtoValueDateTime(vistranslHeadDto.getFfdaar(), vistranslHeadDto.getFfdmnd(), vistranslHeadDto.getFfddag()));		
		//End head
		
		// Invoice Lines  
		dto.setInvoiceLines(getInvoiceLines(vistranslHeadDto.getLines()));

		return dto;

	}	

	private DtoValueString getFinancialsPeriod(VistranslHeadDto vistranslHeadDto) {
		String year = String.valueOf(vistranslHeadDto.getPeraar());
		String month = String.format("%02d", vistranslHeadDto.getPernr()); // pad up to 2 char, ex. 1 -> 01

		return DtoValueHelper.toDtoString(year + month); // ex. 201805

	}	
	
	private List<SupplierInvoiceLineUpdateDto> getInvoiceLines(List<VistranslLineDto> lineDtoList) {
		List<SupplierInvoiceLineUpdateDto> updateDtoList = new ArrayList<SupplierInvoiceLineUpdateDto>();

		lineDtoList.forEach(lineDto -> {

			mandatoryCheck(lineDto);
			
			SupplierInvoiceLineUpdateDto updateDto = new SupplierInvoiceLineUpdateDto();
			updateDto.setLineNumber(DtoValueHelper.toDtoValueInt32((lineDto.getPosnr())));
			updateDto.setQuantity(DtoValueHelper.toDtoDecimal(1.0)); // Hardcode to 1
			updateDto.setUnitCostInCurrency(DtoValueHelper.toDtoValueNullableDecimal(lineDto.getBbelop())); // BBELOP 11 2
			//TODO formodlinge behövs någon form av cross-ref, Visma har 2 tegn , SYSPED 1 TEGN
			updateDto.setVatCodeId(DtoValueHelper.toDtoString(lineDto.getMomsk()));  
			updateDto.setAccountNumber(DtoValueHelper.toDtoString(lineDto.getKonto()));
			updateDto.setSubaccount(getSubaccount(lineDto));
//			updateDto.setDescription(DtoValueHelper.toDtoString(lineDto.getBiltxt()));
			updateDto.setTransactionDescription(DtoValueHelper.toDtoString(lineDto.getBiltxt()));
			updateDto.setOperation(OperationEnum.INSERT);
			
			updateDtoList.add(updateDto);
			
		});
		
		return updateDtoList;
		
	}	
	
	
	private List<SegmentUpdateDto> getSubaccount(VistranslLineDto lineDto) {
		List<SegmentUpdateDto> dtoList = new ArrayList<SegmentUpdateDto>();
		int AVDELING = 1; //Ref in Visma.net
//		int PROJEKT = 2;  //Ref in Visma.net
		
		//Avdeling
		SegmentUpdateDto updateAvdDto = new SegmentUpdateDto();
		updateAvdDto.setSegmentId(AVDELING);
		updateAvdDto.setSegmentValue(String.valueOf(lineDto.getKbarer()));
		dtoList.add(updateAvdDto);
		
//		//Projekt
//		SegmentUpdateDto updateProjDto = new SegmentUpdateDto();
//		updateProjDto.setSegmentId(PROJEKT);
//		updateProjDto.setSegmentValue(String.valueOf(lineDto.getProsnr()));
		
//		dtoList.add(updateProjDto);
		
		return dtoList;
	}	
	
	
	
	// Sanity checks
	private void mandatoryCheck(VistranslHeadDto vistranslHeadDto) {
		if (vistranslHeadDto.getRecnr() == 0) {
			String errMsg = "RECNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranslHeadDto.getBilnr() == 0) {
			String errMsg = "BILNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranslHeadDto.getBetbet() == null || vistranslHeadDto.getBetbet().isEmpty()) {
			String errMsg = "BETBET can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranslHeadDto.getKrdaar() == 0) {
			String errMsg = "KRDAAR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}	
		if (vistranslHeadDto.getKrdmnd() == 0) {
			String errMsg = "KRDMND can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranslHeadDto.getKrddag() == 0) {
			String errMsg = "KRDDAG can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		if (vistranslHeadDto.getFfdaar() == 0) {
			String errMsg = "FFDAAR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		if (vistranslHeadDto.getFfdmnd() == 0) {
			String errMsg = "FFDMND can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}	
		if (vistranslHeadDto.getFfddag() == 0) {
			String errMsg = "FFDDAG can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		
	}	
	
	//Sanity checks
	private void mandatoryCheck(VistranslLineDto lineDto) {
		if (lineDto.getPosnr() == 0) {
			String errMsg = "POSNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (lineDto.getBbelop() == null || lineDto.getBbelop().equals(BigDecimal.ZERO)) {
			String errMsg = "BBELOP can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (lineDto.getMomsk() == null  || lineDto.getMomsk().isEmpty()) {
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
		if (lineDto.getBiltxt() == null || lineDto.getBiltxt().isEmpty()) {
			String errMsg = "BILTXT can not be empty";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		
	}
	
	
	
	
}
