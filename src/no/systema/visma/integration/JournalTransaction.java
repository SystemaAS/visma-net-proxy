package no.systema.visma.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.jservices.common.dao.services.ViscrossrDaoService;
import no.systema.jservices.common.util.StringUtils;
import no.systema.visma.dto.VistranshHeadDto;
import no.systema.visma.dto.VistranshLineDto;
import no.systema.visma.v1client.api.JournalTransactionApi;
import no.systema.visma.v1client.model.DtoValueString;
import no.systema.visma.v1client.model.JournalTransactionDto;
import no.systema.visma.v1client.model.JournalTransactionLineUpdateDto;
import no.systema.visma.v1client.model.JournalTransactionLineUpdateDto.OperationEnum;
import no.systema.visma.v1client.model.JournalTransactionUpdateDto;
import no.systema.visma.v1client.model.SegmentUpdateDto;

/**
 * A Wrapper on {@linkplain JournalTransactionApi}
 * 
 * Also see https://integration.visma.net/API-index/#!/JournalTransaction
 * 
 * @author fredrikmoller
 */
@Service
public class JournalTransaction extends Configuration {
	private static Logger logger = Logger.getLogger(JournalTransaction.class);
	
	@Autowired
	public FirmvisDaoService firmvisDaoService;	

	@Autowired
	public ViscrossrDaoService viscrossrDaoService;		
	
	@Autowired
	public JournalTransactionApi journalTransactionApi = new JournalTransactionApi(apiClient());

	@Autowired
	public Supplier supplier;	
	
	@PostConstruct
	public void post_construct() {
		FirmvisDao firmvis = firmvisDaoService.get();

		journalTransactionApi.getApiClient().setBasePath(firmvis.getVibapa().trim());
		journalTransactionApi.getApiClient().addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		journalTransactionApi.getApiClient().addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		journalTransactionApi.getApiClient().setAccessToken(firmvis.getViacto().trim());			
		
		//journalTransactionApi.getApiClient().setDebugging(true);	//Warning...set debugging in VismaClientHttpRequestInterceptor	
		
	}	
	

	/**
	 * This is the startingpoint for syncronizing SYSPED VISTRANSH with
	 * Visma-net JournalTransaction.
	 * 
	 * @param vistranshHeadDto
	 * @throws RestClientException
	 * @throws HttpClientErrorException
	 */
	public void syncronize(VistranshHeadDto vistranshHeadDto) throws RestClientException, HttpClientErrorException, IOException {
		logger.info("syncronize(VistranshHeadDto vistranshHeadDto)");
		logger.info(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));

		mandatoryCheck(vistranshHeadDto);		
		
		try {
			// Sanity check 1
			String batchNumber = String.valueOf(vistranshHeadDto.getBilnr());
			JournalTransactionDto journalTransactionExistDto = getJournalTransactionByBatchnr(batchNumber);
			if (journalTransactionExistDto != null) {
				String errMsg = String.format("FAKTURA:fakturanr: %s already exist, updates not allowed!", vistranshHeadDto.getBilnr());
				logger.error(errMsg);
    			throw new RuntimeException(errMsg);
			} 
			// Do the thing, if no exception from above
			Resource attachment = null;
			if (StringUtils.hasValue(vistranshHeadDto.getPath())) {
				attachment = DtoValueHelper.getAttachment(vistranshHeadDto.getPath());
			}
			JournalTransactionUpdateDto updateDto = convertToJournalTransactionUpdateDto(vistranshHeadDto);
			journalTransactionPost(updateDto, attachment);
			logger.info("fakturanr:" + vistranshHeadDto.getBilnr() + " is inserted.");


		} catch (HttpClientErrorException e) {
			logger.info(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));
			logger.error(e.getClass() + " On syncronize.  vistranshHeadDto=" + vistranshHeadDto.toString());
			logger.error("message:" + e.getMessage());
			logger.error("status text:" + new String(e.getStatusText())); // Status text contains Response body from Visma.net
			throw e;
		} catch (RestClientException e) {
			logger.info(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));
			logger.error(e.getClass() + " On syncronize.  vistranshHeadDto=" + vistranshHeadDto.toString());
			throw e;
		} catch (Exception e) {
			logger.info(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));
			logger.error(e.getClass() + " On syncronize.  vistranshHeadDto=" + vistranshHeadDto.toString());
			throw e;
		}

	}

    /**
     * Create a Journal Transaction
     * Response Message has StatusCode Created if POST operation succeed
     * <p><b>201</b> - Created
     * @param journalTransaction Defines the data for the Journal Transaction to create
     * @param attachment can be null
     * @throws RestClientException if an error occurs while attempting to invoke the API
     * @throws IOException if problem with attachment
     */
	public void journalTransactionPost(JournalTransactionUpdateDto updateDto, Resource attachment) throws RestClientException, IOException  {
		logger.info("supplierInvoiceCreate(SupplierInvoiceUpdateDto updateDto, Resource attachment)");

		try {

			journalTransactionApi.journalTransactionPost(updateDto);
			//TODO
//			if (attachment != null) {
//				attachInvoiceFile(updateDto.getReferenceNumber().getValue(), attachment);
//			}

		} catch (HttpClientErrorException e) {
//			logger.error("HttpClientErrorException::"+LogHelper.logPrefixJournalTransaction(updateDto.getBatchNumber())); 
			logger.error(e.getClass() + " On journalTransactionApi.journalTransactionPost call. updateDto=" + updateDto.toString());
			throw e;
		} catch (RestClientException | IllegalArgumentException | IndexOutOfBoundsException e) {
//			logger.error("RestClientException | IllegalArgumentException | IndexOutOfBoundsException::"+LogHelper.logPrefixJournalTransaction(updateDto.getBatchNumber())); 
			logger.error(e.getClass() + " On journalTransactionApi.journalTransactionPost call. updateDto=" + updateDto.toString(), e);
			throw e;
		} catch (Exception e) {
//			logger.error("Exception::"+LogHelper.logPrefixJournalTransaction(updateDto.getBatchNumber())); 
			logger.error(e.getClass() + " On journalTransactionApi.journalTransactionPost call. updateDto=" + updateDto.toString());
			throw e;
		}

	}	
	
	private JournalTransactionUpdateDto convertToJournalTransactionUpdateDto(VistranshHeadDto vistranshHeadDto) {
		logger.info("convertToJournalTransactionUpdateDto(VistranshHeadDto vistranshHeadDto)");
		
		// Head
		JournalTransactionUpdateDto dto = new JournalTransactionUpdateDto();
		//TODO ta ställning till om manuell numrering, nu auto.
		//dto.setBatchNumber(DtoValueHelper.toDtoString(vistranshHeadDto.getBilnr()));
		dto.setDescription(DtoValueHelper.toDtoString("Bilagsnr:"+vistranshHeadDto.getBilnr()));
		dto.setFinancialPeriod(getFinancialsPeriod(vistranshHeadDto));
		dto.setTransactionDate(DtoValueHelper.toDtoValueDateTime(vistranshHeadDto.getBilaar(), vistranshHeadDto.getBilmnd(), vistranshHeadDto.getBildag()));
		if (StringUtils.hasValue(vistranshHeadDto.getValkox())) {
			dto.setCurrencyId(DtoValueHelper.toDtoString(vistranshHeadDto.getValkox()));
			dto.setExchangeRate(DtoValueHelper.toDtoValueDecimal(vistranshHeadDto.getValku1()));
		} else {
			dto.setCurrencyId(DtoValueHelper.toDtoString("NOK"));
		}	
		//End head
		
		// Invoice Lines  
		dto.setJournalTransactionLines(getLines(vistranshHeadDto.getLines()));

		return dto;

	}	

	private DtoValueString getFinancialsPeriod(VistranshHeadDto vistranshHeadDto) {
		String year = String.valueOf(vistranshHeadDto.getPeraar());
		String month = String.format("%02d", vistranshHeadDto.getPernr()); // pad up to 2 char, ex. 1 -> 01

		return DtoValueHelper.toDtoString(year + month); // ex. 201805

	}	
	
	private List<JournalTransactionLineUpdateDto> getLines(List<VistranshLineDto> lineDtoList) {
		List<JournalTransactionLineUpdateDto> updateDtoList = new ArrayList<JournalTransactionLineUpdateDto>();

		lineDtoList.forEach(lineDto -> {

			mandatoryCheck(lineDto);
			
			JournalTransactionLineUpdateDto updateDto = new JournalTransactionLineUpdateDto();
			if (lineDto.getFakkre().equals("K")) {
				updateDto.setCreditAmountInCurrency(DtoValueHelper.toDtoValueDecimal(lineDto.getNbelpo()));
			} else { //FAKKRE = F
				updateDto.setDebitAmountInCurrency(DtoValueHelper.toDtoValueDecimal(lineDto.getNbelpo()));
			}			
			updateDto.setLineNumber(DtoValueHelper.toDtoValueInt32((lineDto.getPosnr())));
//			updateDto.setVatCodeId(getVatCodeId(lineDto.getMomsk()));  
			updateDto.setAccountNumber(DtoValueHelper.toDtoString(lineDto.getKontov()));
			updateDto.setSubaccount(getSubaccount(lineDto));
			updateDto.setTransactionDescription(DtoValueHelper.toDtoString(lineDto.getBiltxt()));
			updateDto.setOperation(OperationEnum.INSERT);
			
			updateDtoList.add(updateDto);
			
		});
		
		return updateDtoList;
		
	}	

	private List<SegmentUpdateDto> getSubaccount(VistranshLineDto lineDto) {
		List<SegmentUpdateDto> dtoList = new ArrayList<SegmentUpdateDto>();
		int AVDELING = 1; //Ref in Visma.net
		
		SegmentUpdateDto updateAvdDto = new SegmentUpdateDto();
		updateAvdDto.setSegmentId(AVDELING);
		updateAvdDto.setSegmentValue(String.valueOf(lineDto.getKsted()));
		dtoList.add(updateAvdDto);
		
		return dtoList;
	}		
	
	JournalTransactionDto getJournalTransactionByBatchnr(String batchnr) {
		JournalTransactionDto journalTransactionExistDto;
		try {

			journalTransactionExistDto = journalTransactionApi.journalTransactionGetSpecificJournalTransactionsByjournalTransactionNumber(batchnr);

		} catch (HttpClientErrorException e) {
			logger.info("message:" + e.getMessage() + ", journalTransactionExistDto is null, continue...");
			journalTransactionExistDto = null;
			// continue
		}

		return journalTransactionExistDto;
	}
	
	// Sanity checks
	private void mandatoryCheck(VistranshHeadDto vistranshHeadDto) {
		if (vistranshHeadDto.getBilnr() == 0) {
			String errMsg = "BILNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranshHeadDto.getBilaar() == 0) {
			String errMsg = "BILAAR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranshHeadDto.getBilmnd() == 0) {
			String errMsg = "BILMND can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranshHeadDto.getBildag() == 0) {
			String errMsg = "BILDAG can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (vistranshHeadDto.getValkox() != null) {
			if (vistranshHeadDto.getValku1() == null || vistranshHeadDto.getValku1().equals(BigDecimal.ZERO)) {
				String errMsg = "VALKU1 can not be 0, if VALKOX is set, VALKOX=" + vistranshHeadDto.getValkox();
				logger.error(errMsg);
				throw new RuntimeException(errMsg);
			}
		}
		if (vistranshHeadDto.getFakkre() == null) {
			String errMsg = "FAKKRE can not be empty";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
	}
	
	//Sanity checks
	private void mandatoryCheck(VistranshLineDto lineDto) {
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
		if (lineDto.getKontov() == 0) {
			String errMsg = "KONTOV can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (lineDto.getKsted() == 0) {
			String errMsg = "KSTED can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		if (lineDto.getFakkre() == null || lineDto.getFakkre().isEmpty()) {
			String errMsg = "FAKKRE can not be empty";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
	}
	
	
	/** used for poc-test*/
	List<JournalTransactionDto> journalTransactionGetAllJournalTransactions() {
		String greaterThanValue = null;
		Integer numberToRead = null;
        Integer skipRecords = null;
        String orderBy = null;
        String lastModifiedDateTime = null;
        String lastModifiedDateTimeCondition = null;
        String customerSupplierStart = null;
        String customerSupplierEnd = null;
        Integer released = null;
        Integer pageNumber = null;
        Integer pageSize = null;		
		
		
		List<JournalTransactionDto> list = journalTransactionApi.journalTransactionGetAllJournalTransactions(greaterThanValue, numberToRead, skipRecords, orderBy, lastModifiedDateTime, lastModifiedDateTimeCondition,
				customerSupplierStart, customerSupplierEnd, released, pageNumber, pageSize);	
		
		return list;
		
	}
	
	
}
