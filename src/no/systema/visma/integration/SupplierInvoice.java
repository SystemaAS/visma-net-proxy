package no.systema.visma.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.jservices.common.dao.services.ViscrossrDaoService;
import no.systema.jservices.common.util.StringUtils;
import no.systema.jservices.common.values.ViscrossrKoder;
import no.systema.visma.dto.VistranslHeadDto;
import no.systema.visma.dto.VistranslLineDto;
import no.systema.visma.integration.extended.SupplierInvoiceApiExtended;
import no.systema.visma.v1client.api.SupplierInvoiceApi;
import no.systema.visma.v1client.model.DtoValueNullableSupplierInvoiceTypes.ValueEnum;
import no.systema.visma.v1client.model.DtoValueString;
import no.systema.visma.v1client.model.ReleaseSupplierInvoiceActionResultDto;
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
	private static Logger logger = LoggerFactory.getLogger(SupplierInvoice.class);

	@Autowired
	public FirmvisDaoService firmvisDaoService;
	
	@Autowired
	public ViscrossrDaoService viscrossrDaoService;	

	@Autowired
	public SupplierInvoiceApiExtended supplierInvoiceApi = new SupplierInvoiceApiExtended(apiClient());

	@Autowired
	public Supplier supplier;
	
	private FirmvisDao firmvisDao;

	@PostConstruct
	public void post_construct() {
		firmvisDao = firmvisDaoService.get();

		supplierInvoiceApi.getApiClient().setBasePath(firmvisDao.getVibapa().trim());
		supplierInvoiceApi.getApiClient().addDefaultHeader("ipp-application-type", firmvisDao.getViapty().trim());
		supplierInvoiceApi.getApiClient().addDefaultHeader("ipp-company-id", firmvisDao.getVicoid().trim());
		supplierInvoiceApi.getApiClient().setAccessToken(firmvisDao.getViacto().trim());

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
	public void syncronize(VistranslHeadDto vistranslHeadDto) throws RestClientException, HttpClientErrorException, IOException {
		logger.info("syncronize(VistranslHeadDto vistranslHeadDto)");
		logger.info(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));

		mandatoryCheck(vistranslHeadDto);		
		
		String country;
		
		try {
			// Sanity check 1
			SupplierDto supplierExistDto = supplier.getGetBysupplierCd(String.valueOf(vistranslHeadDto.getResnr()));
			if (supplierExistDto == null) {
				logger.error("Could not find Supplier on number:" + vistranslHeadDto.getResnr());
				throw new RuntimeException("Could not find Supplier on number:" + vistranslHeadDto.getResnr());
			} else { //set country
				country = supplierExistDto.getMainAddress().getCountry().getId();
			}
			// Sanity check 2
			String referenceNumber = String.valueOf(vistranslHeadDto.getBilnr());
			SupplierInvoiceDto supplierInvoiceExistDto = getByinvoiceNumber(referenceNumber);
			if (supplierInvoiceExistDto != null) {
				String errMsg = String.format("INNG.FAKTURA:fakturanr: %s already exist, updates not allowed!", vistranslHeadDto.getBilnr());
				logger.error(errMsg);
    			throw new RuntimeException(errMsg);
			} 
			// Do the thing, if no exception from above
			Resource attachment = null;
			if (StringUtils.hasValue(vistranslHeadDto.getPath())) {
				attachment = DtoValueHelper.getAttachment(vistranslHeadDto.getPath());
			}
			SupplierInvoiceUpdateDto updateDto = convertToSupplierInvoiceUpdateDto(vistranslHeadDto, country);
			supplierInvoicePost(updateDto, attachment);
			logger.info("INNG.FAKTURA:fakturanr:" + vistranslHeadDto.getBilnr() + " is inserted.");


		} catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));
			logger.error(e.getClass() + " On syncronize.  vistranslHeadDto=" + vistranslHeadDto.toString());
			logger.error("message:" + e.getMessage());
			logger.error("status text:" + new String(e.getStatusText())); // Status text contains Response body from Visma.net
			throw e;
		} catch (RestClientException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));
			logger.error(e.getClass() + " On syncronize.  vistranslHeadDto=" + vistranslHeadDto.toString());
			throw e;
		} catch (Exception e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));
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
	 * @param invoice
	 *            Defines the data for the Invoice to create
	 * @param attachment can be null
	 * @throws RestClientException
	 *             if an error occurs while attempting to invoke the API
     * @throws IOException if attachment not found.  
	 */
	public void supplierInvoicePost(SupplierInvoiceUpdateDto updateDto, Resource attachment) throws RestClientException, IOException  {
		logger.info("supplierInvoiceCreate(SupplierInvoiceUpdateDto updateDto, Resource attachment)");
		logger.info(LogHelper.logPrefixSupplierInvoice(updateDto.getSupplierNumber().getValue(), updateDto.getReferenceNumber().getValue())); 

		try {

			supplierInvoiceApi.supplierInvoicePost(updateDto);
			if (attachment != null) {
				attachInvoiceFile(updateDto.getReferenceNumber().getValue(), updateDto.getDocumentType().getValue(), attachment);
			}
			
			if (firmvisDao.getVirelk() == 1) {
				releaseInvoice(updateDto.getReferenceNumber().getValue());
			}

		} catch (HttpClientErrorException e) {
			logger.error("HttpClientErrorException::"+LogHelper.logPrefixSupplierInvoice(updateDto.getSupplierNumber().getValue(), updateDto.getReferenceNumber().getValue())); 
			logger.error(e.getClass() + " On supplierInvoiceApi.supplierInvoicePost call. updateDto=" + updateDto.toString());
			throw e;
		} catch (RestClientException | IllegalArgumentException | IndexOutOfBoundsException e) {
			logger.error("RestClientException | IllegalArgumentException | IndexOutOfBoundsException::"+LogHelper.logPrefixSupplierInvoice(updateDto.getSupplierNumber().getValue(), updateDto.getReferenceNumber().getValue()));
			logger.error(e.getClass() + " On supplierInvoiceApi.supplierInvoicePost call. updateDto=" + updateDto.toString(), e);
			throw e;
		} catch (Exception e) {
			logger.error("Exception::"+LogHelper.logPrefixSupplierInvoice(updateDto.getSupplierNumber().getValue(), updateDto.getReferenceNumber().getValue()));
			logger.error(e.getClass() + " On supplierInvoiceApi.supplierInvoicePost call. updateDto=" + updateDto.toString());
			throw e;
		}

	}	

	void attachInvoiceFile(String bilnr, ValueEnum documentType, Resource file) throws IOException {
		logger.info("attachInvoiceFile(bilnr="+bilnr+", filename="+file.getFilename()+", file.contentLength="+file.contentLength());
		supplierInvoiceApi.supplierInvoiceCreateHeaderAttachmentByinvoiceNumber_v8(bilnr, documentType,file);
	}
  
	ReleaseSupplierInvoiceActionResultDto releaseInvoice(String invoiceNumber) throws RestClientException {
		logger.info("releaseInvoice("+invoiceNumber+")");
		return supplierInvoiceApi.supplierInvoiceReleaseInvoiceByinvoiceNumber(invoiceNumber);
	}	
	
	private SupplierInvoiceUpdateDto convertToSupplierInvoiceUpdateDto(VistranslHeadDto vistranslHeadDto, String country) {
		logger.info("convertToSupplierInvoiceUpdateDto(VistranslHeadDto vistranslHeadDto, String country)");
		
		// Head
		SupplierInvoiceUpdateDto dto = new SupplierInvoiceUpdateDto();
		dto.setReferenceNumber(DtoValueHelper.toDtoString(vistranslHeadDto.getBilnr()));
		if (vistranslHeadDto.getFakkre().equals("K")) {
			dto.setDocumentType(DtoValueHelper.getSupplierInvoiceType(ValueEnum.DEBITADJ));
		} else { //FAKKRE = F
			dto.setDocumentType(DtoValueHelper.getSupplierInvoiceType(ValueEnum.INVOICE));
		}
		dto.setSupplierNumber(DtoValueHelper.toDtoString(vistranslHeadDto.getResnr()));
		dto.setSupplierReference(DtoValueHelper.toDtoString(vistranslHeadDto.getKrnr()));
		dto.setFinancialPeriod(getFinancialsPeriod(vistranslHeadDto));
		dto.setCreditTermsId(DtoValueHelper.toDtoString(vistranslHeadDto.getBetbet()));
		dto.setPaymentRefNo(DtoValueHelper.toDtoString(vistranslHeadDto.getLkid()));
		dto.setDate(DtoValueHelper.toDtoValueDateTime(vistranslHeadDto.getKrdaar(), vistranslHeadDto.getKrdmnd(), vistranslHeadDto.getKrddag()));
		dto.setDueDate(DtoValueHelper.toDtoValueDateTime(vistranslHeadDto.getFfdaar(), vistranslHeadDto.getFfdmnd(), vistranslHeadDto.getFfddag()));
		if (StringUtils.hasValue(vistranslHeadDto.getValkox())) {
			dto.setCurrencyId(DtoValueHelper.toDtoString(vistranslHeadDto.getValkox()));
			dto.setExchangeRate(DtoValueHelper.toDtoValueDecimal(vistranslHeadDto.getValku1()));
		} else {
			dto.setCurrencyId(DtoValueHelper.toDtoString("NOK"));
		}
		
		/* email: WML 24.jan.2022 via Svein UC-2
		2. When Country of supplier in Systema = 'NL' then supplier tax zone on Purchase invoice should be set to '21' ( NL Domestic)
		   When Country = an EU country (using Systema table), but not NL then tax zone should be set to '22' (NL EU)
		   When Country = other then tax zone should be set to '23' (NL IMP)
		*/
		if(StringUtils.hasValue(country)) {
			//Inactivated 21.March.2022 - Sveins email. WML is not prepare. Remove this commented line when GO
			//dto.setSupplierTaxZone(DtoValueHelper.toDtoString(this.getSupplierTaxZone(country)));
		}
		//End head
		
		// Invoice Lines  
		dto.setInvoiceLines(getInvoiceLines(vistranslHeadDto.getLines(), country));

		return dto;

	}


	/** SYSPED-KODTS2 -dbtable where KS2PRE = 'B' (EU LAND)
	 S2      AT    ØSTERRIKE                                                          B          
	 S2      BE    BELGIA                                                     FR      B          
	 S2      BG    BULGARIA                                                           B          
	 S2      CY    KYPROS                                                             B          
	 S2      CZ    TSJEKKIA                                                   CS      B          
	 S2      DE    TYSKLAND                                                   DE      B          
	 S2      DK    DANMARK                                                    DA      B          
	 S2      EE    ESTLAND                                                    ET      B          
	 S2      ES    SPANIA                                                     ES      B          
	 S2      FI    FINLAND                                                    FI      B          
	 S2      FL    FREISTADT LIECHTENSTEIN                                            B          
	 S2      FR    FRANKRIKE                                                  FR      B          
	 S2      GR    HELLAS                                                     EL      B          
	 S2      HU    UNGARN                                                     HU      B          
	 S2      IE    IRLAND                                                     EN      B          
	 S2      IT    ITALIA                                                     IT      B          
	 S2      LI    LIECHTENSTEIN                                              FR      B          
	 S2      LT    LITAUEN                                                    LT      B          
	 S2      LU    LUXEMBOURG                                                 FR      B   
	 S2      LV    LATVIA                                                     LA      B          
	S2      MT    MALTA                                                              B          
	S2      NL    NEDERLAND                                                  NL      B          
	S2      PL    POLEN                                                      PL      B          
	S2      PT    PORTUGAL                                                   PT      B          
	S2      RO    ROMANIA                                                    RO      B          
	S2      SE    SVERIGE                                                    SV      B          
	S2      SI    SLOVENIA                                                   SL      B          
	S2      SK    SLOVAKIA                                                   SK      B                 
	 
	 */
	private String getSupplierTaxZone(String country) {
		String retval = "";
		//should be fetched from SYSPED- KODTS2 as above...one rainy day ...
		String[] euCountries = {"AT","BE","BG","CY","CZ","DE","DK","EE","ES","FI","FL","FR","GR","HU","IE","IT","LI","LT","LU","LV","MT","NL","PL","PT", "RO",
								"SE","SI","SK"};
		
		if (StringUtils.hasValue(country)){
			if("NL".equals(country)) {
				retval = "21";
			
			}else if(!"NL".equals(country)) {
				if(Arrays.asList(euCountries).contains(country)){
					retval = "22";
				}else {
					retval = "23";
				}
			
			}
		}
		return retval;
	}
	
	
	private DtoValueString getFinancialsPeriod(VistranslHeadDto vistranslHeadDto) {
		String year = String.valueOf(vistranslHeadDto.getPeraar());
		String month = String.format("%02d", vistranslHeadDto.getPernr()); // pad up to 2 char, ex. 1 -> 01

		return DtoValueHelper.toDtoString(year + month); // ex. 201805

	}	
	
	private List<SupplierInvoiceLineUpdateDto> getInvoiceLines(List<VistranslLineDto> lineDtoList, String country) {
		List<SupplierInvoiceLineUpdateDto> updateDtoList = new ArrayList<SupplierInvoiceLineUpdateDto>();
		int DEPARTMENT_ROTTERDAM = 2;
		int VISMA_BRANCH_ROTTERDAM = 10;
		
		lineDtoList.forEach(lineDto -> {

			mandatoryCheck(lineDto);
			
			SupplierInvoiceLineUpdateDto updateDto = new SupplierInvoiceLineUpdateDto();
			updateDto.setLineNumber(DtoValueHelper.toDtoValueInt32((lineDto.getPosnr())));
			updateDto.setQuantity(DtoValueHelper.toDtoValueDecimal(1.0)); // Hardcode to 1
			updateDto.setUnitCostInCurrency(DtoValueHelper.toDtoValueNullableDecimal(lineDto.getNbelpo()));
			updateDto.setVatCodeId(getVatCodeId(lineDto.getMomsk(), country));  
			updateDto.setAccountNumber(DtoValueHelper.toDtoString(lineDto.getKontov()));
			updateDto.setSubaccount(getSubaccount(lineDto));
			updateDto.setTransactionDescription(DtoValueHelper.toDtoString(lineDto.getBiltxt()));
			updateDto.setOperation(OperationEnum.INSERT);
			
			//When department = '2' (Rotterdam) then the booking needs to change:
			//(a)the Branchcode '10' needs to be filled, next to the costcentre that is already set in the current integration
			if(lineDto.getKsted() == DEPARTMENT_ROTTERDAM) {
			  //Inactivated 21.March.2022 - Sveins email. WML is not prepare. Remove this commented line when GO	
			  //updateDto.setBranch(DtoValueHelper.toDtoString(VISMA_BRANCH_ROTTERDAM));
			  //updateDto.setBranchNumber(DtoValueHelper.toDtoString(VISMA_BRANCH_ROTTERDAM));
			}
			updateDtoList.add(updateDto);
			
		});
		
		return updateDtoList;
		
	}	
	
	private DtoValueString getVatCodeId(String momsk, String country) {
		String vismaCodeId = null;
		
		if ("NO".equals(country)) {
			vismaCodeId = viscrossrDaoService.getVismaCodeId(momsk,ViscrossrKoder.MK_NO);
		} else {
			vismaCodeId = viscrossrDaoService.getVismaCodeId(momsk,ViscrossrKoder.MK);
		}
		if (vismaCodeId == null) {
			throw new RuntimeException("No Visma.net value found in VISCROSSR for SYSPED value:"+momsk);
		}
		
		return DtoValueHelper.toDtoString(vismaCodeId);
		
	}
	
	private List<SegmentUpdateDto> getSubaccount(VistranslLineDto lineDto) {
		List<SegmentUpdateDto> dtoList = new ArrayList<SegmentUpdateDto>();
		int AVDELING = 1; //Ref in Visma.net
//		int PROJEKT = 2;  //Ref in Visma.net
		
		//Avdeling
		SegmentUpdateDto updateAvdDto = new SegmentUpdateDto();
		updateAvdDto.setSegmentId(AVDELING);

		String kstedString = String.valueOf(lineDto.getKsted());
		String kstedLeftPadded = org.apache.commons.lang3.StringUtils.leftPad(kstedString, 4, '0');
		updateAvdDto.setSegmentValue(kstedLeftPadded);

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
		if (vistranslHeadDto.getResnr() == 0) {
			String errMsg = "RESNR can not be 0";
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
		if (vistranslHeadDto.getLkid() == null) {
			String errMsg = "LKID can not be empty";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		if (vistranslHeadDto.getValkox() != null) {
			if (vistranslHeadDto.getValku1() == null || vistranslHeadDto.getValku1().equals(BigDecimal.ZERO)) {
				String errMsg = "VALKU1 can not be 0, if VALKOX is set, VALKOX="+vistranslHeadDto.getValkox() ;
				logger.error(errMsg);
				throw new RuntimeException(errMsg);
			}
		}
		if (vistranslHeadDto.getKrnr() == null) {
			String errMsg = "KRNR can not be empty";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranslHeadDto.getFakkre() == null) {
			String errMsg = "FAKKRE can not be empty";
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
		if (lineDto.getNbelpo() == null || lineDto.getNbelpo().equals(BigDecimal.ZERO)) {
			String errMsg = "NBELPO can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (lineDto.getMomsk() == null  || lineDto.getMomsk().isEmpty()) {
			String errMsg = "MOMSK can not be empty";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		if (lineDto.getKontov() == 0) {
			String errMsg = "KONTOV can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
		//Ksted kan vara 0, interimsföring
//		if (lineDto.getKsted() == 0) {
//			String errMsg = "KSTED can not be 0";
//			logger.error(errMsg);
//			throw new RuntimeException(errMsg);
//		}
		if (lineDto.getBiltxt() == null || lineDto.getBiltxt().isEmpty()) {
			String errMsg = "BILTXT can not be empty";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}	
		
		
	}
	
}
