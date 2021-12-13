package no.systema.visma.integration;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.VisleveDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.jservices.common.util.StringUtils;
import no.systema.visma.v1client.api.SupplierApi;
import no.systema.visma.v1client.model.AddressUpdateDto;
import no.systema.visma.v1client.model.ContactInfoUpdateDto;
import no.systema.visma.v1client.model.CustomerInvoiceLinesUpdateDto;
import no.systema.visma.v1client.model.DtoValueAddressUpdateDto;
import no.systema.visma.v1client.model.DtoValueContactInfoUpdateDto;
import no.systema.visma.v1client.model.DtoValueString;
import no.systema.visma.v1client.model.DtoValueSupplierStatus;
import no.systema.visma.v1client.model.SupplierDto;
import no.systema.visma.v1client.model.SupplierPaymentMethodDetailUpdateDto;
import no.systema.visma.v1client.model.SupplierUpdateDto;

/**
 * A Wrapper on SupplierApi
 * 
 * Also see https://integration.visma.net/API-index/#!/Supplier
 * 
 * @author fredrikmoller
 */
@Service
public class Supplier extends Configuration {

	private static Logger logger = LogManager.getLogger(Customer.class);
	
	@Autowired
	FirmvisDaoService firmvisDaoService;	
	
	@Autowired
	SupplierApi supplierApi = new SupplierApi(apiClient());

	@PostConstruct
	void post_construct() {
		FirmvisDao firmvis = firmvisDaoService.get();

		supplierApi.getApiClient().setBasePath(firmvis.getVibapa().trim());
		supplierApi.getApiClient().addDefaultHeader("ipp-application-type", firmvis.getViapty().trim());
		supplierApi.getApiClient().addDefaultHeader("ipp-company-id", firmvis.getVicoid().trim());
		supplierApi.getApiClient().setAccessToken(firmvis.getViacto().trim());			
		
		//supplierApi.getApiClient().setDebugging(true);	//Warning...set debugging in VismaClientHttpRequestInterceptor	
		
	}

	/**
	 * This is the startpoint for syncronizing SYSPED VISLEVE with Visma-net Supplier.
	 * 
	 * @param visleveDao
	 * @throws RestClientException
	 * @throws HttpClientErrorException
	 */
	public void syncronize(VisleveDao visleveDao) throws RestClientException,  HttpClientErrorException {
		logger.info("syncronize(VisleveDao visleveDao)");
		logger.info(LogHelper.logPrefixSupplier(visleveDao.getLevnr()));		
		
		try {
			
    		SupplierDto supplierExistDto = getGetBysupplierCd(String.valueOf(visleveDao.getLevnr()));			
 
    		if (supplierExistDto != null) {
    			logger.info("Leverandor:"+visleveDao.getLevnr()+ " exist, trying to update.");

    			SupplierUpdateDto updateDto = convertToSupplierUpdateDto(visleveDao, IUDEnum.UPDATE);			
    			supplierPutBysupplierCd(String.valueOf(visleveDao.getLevnr()), updateDto);
    			logger.info("Leverandor:"+visleveDao.getLevnr()+ " is updated.");
    		} else {

    			logger.info("Leverandor:"+visleveDao.getLevnr()+ " does not exist, Trying to insert.");
    			SupplierUpdateDto updateDto = convertToSupplierUpdateDto(visleveDao, IUDEnum.INSERT);			
    			supplierPost(updateDto);
    			logger.info("Leverandor:"+visleveDao.getLevnr()+ " is inserted.");

    		}
    		
			
    	} catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixSupplier(visleveDao.getLevnr()));
			logger.error(e.getClass()+" On  syncronize.  visleveDao="+visleveDao.toString());
			logger.error("message:"+e.getMessage());
			logger.error("status text:"+new String(e.getStatusText()));  //Status text contains Response body from Visma.net
			throw e;
		}
    	catch (RestClientException e) {
    		logger.error(LogHelper.logPrefixSupplier(visleveDao.getLevnr()));
			logger.error(e.getClass()+" On syncronizeCustomer.  visleveDao="+visleveDao.toString());
			throw e;
		}
    	catch (Exception e) {
    		logger.error(LogHelper.logPrefixSupplier(visleveDao.getLevnr()));
			logger.error(e.getClass()+" On syncronize.  visleveDao="+visleveDao.toString());
			throw e;
		} 
		
	}

	
	/**
     * Updates a specific supplier
     * 
     * 
     * Response Message has StatusCode NoContent if PUT operation succeed
     * <p><b>204</b> - NoContent
     * @param number Visma.net number (levnr.:)
     * @param supplierUpdateDto The data to update for the supplier
     * @throws RestClientException if an error occurs while attempting to invoke the API
     * @throws HttpClientErrorException when an HTTP 4xx is received. Typically when indata is wrong
     */
    private void supplierPutBysupplierCd(String number, SupplierUpdateDto supplierUpdateDto) throws RestClientException, HttpClientErrorException {
    	logger.info(LogHelper.logPrefixSupplier(number));
    	logger.info("supplierPutBysupplierCd(String number, SupplierUpdateDto supplierUpdateDt)"); 
    	
    	try {

    		supplierApi.supplierPutBysupplierCd(number, supplierUpdateDto);

    	} catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixSupplier(number));
			logger.error(e.getClass()+" On  supplierApi.supplierPutBysupplierCd call. number="+number+", supplierUpdateDto="+supplierUpdateDto.toString());
			logger.error("message:"+e.getMessage());
			logger.error("status text:"+new String(e.getStatusText()));  //Status text contains Response body from Visma.net
			throw e;
		}
    	catch (RestClientException e) {
    		logger.error(LogHelper.logPrefixSupplier(number));
			logger.error(e.getClass()+" On supplierApi.supplierPutBysupplierCd call. number="+number+", customesupplierUpdateDtorUpdateDto="+supplierUpdateDto.toString());
			throw e;
		}
    	catch (Exception e) {
    		logger.error(LogHelper.logPrefixSupplier(number));
			logger.error(e.getClass()+" On supplierApi.supplierPutBysupplierCd call. number="+number+", supplierUpdateDto="+supplierUpdateDto.toString());
			throw e;
		} 
    	
    }	
	
    /**
     * Create a supplier
     * Response Message has StatusCode Created if POST operation succeed
     * <p><b>201</b> - Created
     * @param supplier Define the data for the supplier to create
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    private void supplierPost(SupplierUpdateDto updateDto) throws RestClientException,IllegalArgumentException, IndexOutOfBoundsException {
    	logger.info(LogHelper.logPrefixSupplier(updateDto.getNumber()));
    	logger.info("supplierPost(SupplierUpdateDto updateDto)"); 
    	
    	try {

    		supplierApi.supplierPost(updateDto);

    		
    	} catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixSupplier(updateDto.getNumber()));
			logger.error(e.getClass()+" On customerApi.customerPost call. updateDto="+updateDto.toString());
			logger.error("message:"+e.getMessage());
			logger.error("status text:"+new String(e.getStatusText()));  //Status text contains Response body from Visma.net
			throw e;
		}
    	catch (RestClientException  | IllegalArgumentException | IndexOutOfBoundsException e) {
    		logger.error(LogHelper.logPrefixSupplier(updateDto.getNumber()));
    		logger.error(e.getClass()+" On customerApi.customerPost call. updateDto="+updateDto.toString(), e);
			throw e;
		} 
    	catch (Exception e) {
			logger.error(LogHelper.logPrefixSupplier(updateDto.getNumber()));
			logger.error(e.getClass()+" On customerApi.customerPost call. updateDto="+updateDto.toString());
			throw e;
		}     	
    	
    }
	
    /**
	 * Get a specific Supplier
	 * 
	 * @param number
	 * @return SupplierDto return null if not found.
	 */
	SupplierDto getGetBysupplierCd(String number) {
		logger.info(LogHelper.logPrefixSupplier(number));
		logger.info("getGetBysupplierCd(String number)");
		SupplierDto supplierExistDto;

		try {

			supplierExistDto = supplierApi.supplierGetBysupplierCd(number);
			
		} catch (HttpClientErrorException e) {
			logger.info("message:" + e.getMessage()+ ", supplierExistDto is null, continue...");
			supplierExistDto = null;
			// continue
		}

		return supplierExistDto;

	}	

	List<SupplierDto> supplierGetAll() {
		String greaterThanValue = null;
		Integer numberToRead = null;
		Integer skipRecords = null;
		String orderBy = null;
		String lastModifiedDateTime = null;
		String lastModifiedDateTimeCondition = null;
		String name = null;
		String status = null;
		String vatRegistrationId = null;
		String corporateId = null;
		String attributes = null;
		return supplierApi.supplierGetAll(greaterThanValue, numberToRead, skipRecords, orderBy, lastModifiedDateTime, lastModifiedDateTimeCondition, name, status, vatRegistrationId, corporateId,
				attributes);
	}
	
	/**
	 * 
	 * Convert VISLEVE data into Supplier. </br></br>
	 * 
	 * For mapping specification see Systema Google Drive /Losning1/Visma migration </br></br>
	 * 
	 * Structure also found here: https://github.com/SystemaAS/visma-net-v1client/blob/master/docs/SupplierUpdateDto.md
	 * 
	 * 
	 * @param visleve VISLEVE
	 * @param status 
	 * @return SupplierUpdateDto
	 * @throws RuntimeException if Levnr is 0.
	 */
    private SupplierUpdateDto convertToSupplierUpdateDto(VisleveDao visleve, IUDEnum status) {
    	logger.info("convertToSupplierUpdateDto(VisleveDao visleve, IUDEnum status)");
    	//Sanity checks
		if (visleve.getLevnr() == 0) {
			String errMsg = "LEVNR can not be 0";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		} 
	
		SupplierUpdateDto dto = new SupplierUpdateDto();
		if (status.equals(IUDEnum.INSERT)) {
			dto.setNumber(DtoValueHelper.toDtoString(visleve.getLevnr()));
		}
		dto.setName(DtoValueHelper.toDtoString(visleve.getLnavn()));
		dto.setCorporateId(DtoValueHelper.toDtoString(visleve.getRnrale())); 
		dto.setMainAddress(getMainAddress(visleve));
		dto.setStatus(getStatus(visleve));
		dto.setMainContact(getMainContact(visleve));
		dto.setCreditTermsId(DtoValueHelper.toDtoString(visleve.getBetbet())); 
		dto.setCurrencyId(DtoValueHelper.toDtoString(visleve.getValkod()));
		dto.setSupplierClassId(getSupplierClassId(visleve));
		if (!StringUtils.hasValue(visleve.getLand()) || "NO".equals(visleve.getLand())) {
			dto.setSupplierPaymentMethodDetails(getSupplierPaymentsMetodDetails(visleve));
		}
		
		return dto;
	}

    /* Supporting only Norwegian - BANKKONTO*/
    private List<SupplierPaymentMethodDetailUpdateDto> getSupplierPaymentsMetodDetails(VisleveDao visleve) {
		List<SupplierPaymentMethodDetailUpdateDto> updateDtoList = new ArrayList<SupplierPaymentMethodDetailUpdateDto>();

		SupplierPaymentMethodDetailUpdateDto updateDto = new SupplierPaymentMethodDetailUpdateDto();
		updateDto.setPaymentMethodDetailDescription("BANKKONTO");
		updateDto.setPaymentMethodDetailValue(DtoValueHelper.toDtoString(visleve.getBankg()));

		updateDtoList.add(updateDto);

		return updateDtoList;

	}

	private DtoValueString getSupplierClassId(VisleveDao visleve) {
		String leveProfilId;

		if (!StringUtils.hasValue(visleve.getLand()) || "NO".equals(visleve.getLand())) {
			leveProfilId = "1";
		} else {
			leveProfilId = "2";
		}

		return DtoValueHelper.toDtoString(leveProfilId);

	}    
    
	private DtoValueContactInfoUpdateDto getMainContact(VisleveDao visleve) {
		DtoValueContactInfoUpdateDto dtoValue = new DtoValueContactInfoUpdateDto();
		
		ContactInfoUpdateDto infoDto = new ContactInfoUpdateDto();
		infoDto.setName(DtoValueHelper.toDtoString(visleve.getLnavn()));
		infoDto.setAttention(DtoValueHelper.toDtoString(visleve.getKpers()));
		infoDto.setPhone1(DtoValueHelper.toDtoString(visleve.getTlf()));
		
		dtoValue.setValue(infoDto);
		
		return dtoValue;
	}    
    
	private DtoValueSupplierStatus getStatus(VisleveDao visleve) {
		//sanity check
		if (visleve.getAktkod() == null) {
			String errMsg = "AKTKOD can not be null";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);			
		}
		DtoValueSupplierStatus dtoValue = new DtoValueSupplierStatus();
		
		if (visleve.getAktkod().equals("A")) { 
			dtoValue.setValue(DtoValueSupplierStatus.ValueEnum.ACTIVE);
		} else if (visleve.getAktkod().equals("I")) {
			dtoValue.setValue(DtoValueSupplierStatus.ValueEnum.INACTIVE);
		} else {
			String errMsg = "AKTKOD must be A or I. Fallback, setting status to ONHOLD";
			logger.error(errMsg);
			dtoValue.setValue(DtoValueSupplierStatus.ValueEnum.ONHOLD);
		}
		
		return dtoValue;
	}   
    
	private DtoValueAddressUpdateDto getMainAddress(VisleveDao visleve) {
		DtoValueAddressUpdateDto dtoValueDto = new DtoValueAddressUpdateDto();

		AddressUpdateDto addressdto = new AddressUpdateDto();
		addressdto.setAddressLine1(DtoValueHelper.toDtoString(visleve.getAdr1()));
		addressdto.setAddressLine2(DtoValueHelper.toDtoString(visleve.getAdr2()));
		addressdto.setAddressLine3(DtoValueHelper.toDtoString(visleve.getAdr3()));
		addressdto.countryId(DtoValueHelper.toDtoString(visleve.getLand()));
		if (visleve.getPostnr() > 0) {
			addressdto.setPostalCode(DtoValueHelper.toDtoStringLeftPaddingZeros(visleve.getPostnr(), 4));
		} else {
			addressdto.setPostalCode(DtoValueHelper.toDtoString(visleve.getPostnu()));
		}
		addressdto.setCity(DtoValueHelper.toDtoString(visleve.getAdr3()));
		
		dtoValueDto.setValue(addressdto);

		return dtoValueDto;
	}	
	
}
