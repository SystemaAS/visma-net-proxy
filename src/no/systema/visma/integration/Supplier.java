package no.systema.visma.integration;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.VisleveDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.jservices.common.util.StringUtils;
import no.systema.visma.v1client.api.SupplierApi;
import no.systema.visma.v1client.model.AddressUpdateDto;
import no.systema.visma.v1client.model.ContactInfoUpdateDto;
import no.systema.visma.v1client.model.CustomerUpdateDto;
import no.systema.visma.v1client.model.DtoValueAddressUpdateDto;
import no.systema.visma.v1client.model.DtoValueContactInfoUpdateDto;
import no.systema.visma.v1client.model.DtoValueCustomerStatus;
import no.systema.visma.v1client.model.DtoValueString;
import no.systema.visma.v1client.model.DtoValueSupplierStatus;
import no.systema.visma.v1client.model.SupplierDto;
import no.systema.visma.v1client.model.SupplierUpdateDto;

/**
 * A Wrapper on SupplierApi
 * 
 * Also see https://integration.visma.net/API-index/#!/Supplier
 * 
 * @author fredrikmoller
 */
public class Supplier extends Configuration {

	private static Logger logger = Logger.getLogger(Customer.class);
	
	@Autowired
	public FirmvisDaoService firmvisDaoService;	
	
	@Autowired
	public SupplierApi supplierApi = new SupplierApi(apiClient());

	@PostConstruct
	public void post_construct() {
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
	//TODO ongoing
	public void syncronize(VisleveDao visleveDao) throws RestClientException,  HttpClientErrorException {
		logger.info("syncronize(VisleveDao visleveDao)");
		logger.info(LogHelper.logPrefixSupplier(visleveDao.getLevnr()));		
		
		try {
			
    		SupplierDto supplierExistDto = getGetBysupplierCd(String.valueOf(visleveDao.getLevnr()));			
 
    		if (supplierExistDto != null) {
    			logger.info("Leverandor:"+visleveDao.getLevnr()+ " exist, trying to update.");

    			SupplierUpdateDto updateDto = convertToSupplierUpdateDto(visleveDao, IUDEnum.UPDATE);			
//    			customerPutBycustomerCd(String.valueOf(visleveDao.getLevnr()), updateDto);
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
		
		return dto;
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
		addressdto.setPostalCode(DtoValueHelper.toDtoString(visleve.getPostnr()));
		addressdto.setCity(DtoValueHelper.toDtoString(visleve.getAdr3()));
		
		dtoValueDto.setValue(addressdto);

		return dtoValueDto;
	}	
	
	
	
	
}
