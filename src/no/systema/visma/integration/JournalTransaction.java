package no.systema.visma.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.*;
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
import no.systema.visma.v1client.model.ReleaseJournalTransactionActionResultDto;
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
	private static Logger logger = LogManager.getLogger(JournalTransaction.class);
	
	@Autowired
	public FirmvisDaoService firmvisDaoService;	

	@Autowired
	public ViscrossrDaoService viscrossrDaoService;		
	
	@Autowired
	public JournalTransactionApi journalTransactionApi = new JournalTransactionApi(apiClient());

	@Autowired
	public Supplier supplier;	
	
	private FirmvisDao firmvisDao;
	
	@PostConstruct
	public void post_construct() {
		firmvisDao = firmvisDaoService.get();

		journalTransactionApi.getApiClient().setBasePath(firmvisDao.getVibapa().trim());
		journalTransactionApi.getApiClient().addDefaultHeader("ipp-application-type", firmvisDao.getViapty().trim());
		journalTransactionApi.getApiClient().addDefaultHeader("ipp-company-id", firmvisDao.getVicoid().trim());
		journalTransactionApi.getApiClient().setAccessToken(firmvisDao.getViacto().trim());			
		
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
			if (firmvisDao.getVirelh() == 1) {
				String invoiceNumber = getJournalTransactiontoRelease(updateDto.getBatchNumber().getValue());
				releaseInvoice(invoiceNumber);
			}

		} catch (HttpClientErrorException e) {
			logger.error(e.getClass() + " On journalTransactionApi.journalTransactionPost call. updateDto=" + updateDto.toString());
			throw e;
		} catch (RestClientException | IllegalArgumentException | IndexOutOfBoundsException e) {
			logger.error(e.getClass() + " On journalTransactionApi.journalTransactionPost call. updateDto=" + updateDto.toString(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getClass() + " On journalTransactionApi.journalTransactionPost call. updateDto=" + updateDto.toString());
			throw e;
		}

	}	
	
	private JournalTransactionUpdateDto convertToJournalTransactionUpdateDto(VistranshHeadDto vistranshHeadDto) {
		logger.info("convertToJournalTransactionUpdateDto(VistranshHeadDto vistranshHeadDto)");
		
		// Head
		JournalTransactionUpdateDto dto = new JournalTransactionUpdateDto();
		/** NOTE: setBatchNumber is used solely for transferring BILNR to releaseInvoice() */
		dto.setBatchNumber(DtoValueHelper.toDtoString(vistranshHeadDto.getBilnr()));
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

	ReleaseJournalTransactionActionResultDto releaseInvoice(String invoiceNumber) throws RestClientException {
		logger.info("releaseInvoice("+invoiceNumber+")");
		return journalTransactionApi.journalTransactionReleaseJournalTransactionByjournalTransactionNumber(invoiceNumber);
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

//		updateAvdDto.setSegmentValue(String.valueOf(lineDto.getKsted())); //Ksted: ref in Visma.net
		
		
		String kstedString = String.valueOf(lineDto.getKsted());
		String kstedLeftPadded = org.apache.commons.lang3.StringUtils.leftPad(kstedString, 4, '0');
		updateAvdDto.setSegmentValue(kstedLeftPadded);

		
		dtoList.add(updateAvdDto);
		
		return dtoList;
	}		
	

	private String getJournalTransactiontoRelease(String bilnr) {
		Integer BALANCED = 0;
		List<JournalTransactionDto> balancedList = getJournalTransactionsByReleased(BALANCED);
	
		//Sanity check
		List<JournalTransactionDto> jtList = balancedList.stream()                       
                .filter(dto -> dto.getDescription().endsWith(bilnr))
                .collect(Collectors.toList());
		if (jtList.size() > 1) {
			String errMsg = "Found more than one JournalTransaction on BILNR="+bilnr;
			throw new RuntimeException(errMsg+ ". Found "+jtList.size()+ " JournalTransactions");
		}
		
		JournalTransactionDto jt = balancedList.stream()                       
	                .filter(dto -> dto.getDescription().endsWith(bilnr))      
	                .findFirst() 
	                .orElse(null);    	

		if (jt == null) {
			String errMsg = "JournalTransactionDto is null! Searched on BILNR " + bilnr + " as ending part of description.";
			throw new RuntimeException(errMsg);

		}
		
		return jt.getBatchNumber();
		
	}
	
	private List<JournalTransactionDto> getJournalTransactionsByReleased(Integer released) {
        String greaterThanValue = null;
        Integer numberToRead = null;
        Integer skipRecords = null;
        String orderBy = null;
        String lastModifiedDateTime = null;
        String lastModifiedDateTimeCondition = null;
        String customerSupplierStart = null;
        String customerSupplierEnd = null;
//        Integer released = 0; //0 = Balanced
        Integer pageNumber = null;
        Integer pageSize = null;
        List<JournalTransactionDto> response = journalTransactionApi.journalTransactionGetAllJournalTransactions(greaterThanValue, numberToRead, skipRecords, orderBy, lastModifiedDateTime, lastModifiedDateTimeCondition, customerSupplierStart, customerSupplierEnd, released, pageNumber, pageSize);

        return response;
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
		//Kan vara 0
//		if (lineDto.getKsted() == 0) {
//			String errMsg = "KSTED can not be 0";
//			logger.error(errMsg);
//			throw new RuntimeException(errMsg);
//		}
		if (lineDto.getFakkre() == null || lineDto.getFakkre().isEmpty()) {
			String errMsg = "FAKKRE can not be empty";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}		
	}
	
	
}
