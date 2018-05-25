package no.systema.visma.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.ViskulogDao;
import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.VistranskDao;
import no.systema.jservices.common.dao.services.FirmDaoService;
import no.systema.jservices.common.dao.services.VistranskDaoService;
import no.systema.visma.dto.PrettyPrintVistranskError;
import no.systema.visma.dto.VistranskHeadDto;
import no.systema.visma.dto.VistranskTransformer;
import no.systema.visma.integration.CustomerInvoice;
import no.systema.visma.integration.LogHelper;

@Service
public class CustomerInvoiceTransactionManager {
	/**
	 * Separate log: ${catalina.home}/logs/log4j_visma-net-proxy-transaction.log
	 */
	private static Logger logger = Logger.getLogger(CustomerInvoiceTransactionManager.class);
	
	@Autowired
	CustomerInvoice customerInvoice;
	
	@Autowired
	VistranskDaoService vistranskDaoService;

	@Autowired
	FirmDaoService firmDaoService;
	
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd"); 		
	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
	
	/**
	 * Syncronize all VISTRANSK with CustomerInvoice in Visma.net <br>
	 * 
	 * @return List of {@link PrettyPrintVistranskError} , could be empty
	 */
	public List<PrettyPrintVistranskError> syncronizeCustomerInvoices() {
		logger.info("Syncronizing all records in VISTRANSK -> CustomerInvoice.");
		List<PrettyPrintVistranskError> errorList = new ArrayList<PrettyPrintVistranskError>();

		List<VistranskDao> vistranskDaoList = vistranskDaoService.findAll(null);
		List<VistranskHeadDto> headDtolist = VistranskTransformer.transform( vistranskDaoList );
		
		headDtolist.forEach((headDto) -> {
			try {

				syncronizeCustomerInvoice(headDto);

				//TODO add table logging
//				ViskulogDao viskulogDao = getViskulogDao(dao, null);
//				viskulogDaoService.create(viskulogDao);
//				logger.info("VISKULOG created, dao="+viskulogDao);	
			} 
			catch (HttpClientErrorException e) {
				logger.error(e);
				errorList.add(new PrettyPrintVistranskError(headDto.getRecnr(), headDto.getBilnr(), headDto.getPosnr(), LocalDateTime.now(), e.getStatusText()));
				updateVistranskOnError(headDto, e.getMessage());
				//TODO logtabell
//				ViskulogDao viskulogDao = getViskulogDao(dao, e.getStatusText());
//				viskulogDaoService.create(viskulogDao);
//				logger.info("VISKULOG created, dao="+viskulogDao);			
				//continues with next dao in list
			}		
			catch (Exception e) {
				logger.error(e);
				errorList.add(new PrettyPrintVistranskError(headDto.getRecnr(), headDto.getBilnr(), headDto.getPosnr(), LocalDateTime.now(), e.getMessage()));
				updateVistranskOnError(headDto, e.getMessage());
				//TODO logtabell
//				vistranskDaoService.updateOnError(dao);		
//				ViskulogDao viskulogDao = getViskulogDao(dao, e.getMessage());
//				viskulogDaoService.create(viskulogDao);
//				logger.info("VISKULOG created, dao="+viskulogDao);	
				//continues with next dao in list
			}


		});

		logger.info("Syncronized ("+vistranskDaoList.size()+") in VISTRANSK -> CustomerInvoice.");
		logger.info("Error list size="+errorList.size());
		
		return errorList;
		
	}
	

	/**
	 * Syncronize VISTRANSK with CustomerInvoice in Visma.net
	 * 
	 * @param vistranskHeadDto
	 */
	public void syncronizeCustomerInvoice(VistranskHeadDto vistranskHeadDto) throws RestClientException,  IndexOutOfBoundsException { 
		logger.info(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr(), vistranskHeadDto.getPosnr()));

		try {
			
			customerInvoice.syncronize(vistranskHeadDto);
			logger.info(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr(), vistranskHeadDto.getPosnr()) + " syncronized.");

			deleteVistransk(vistranskHeadDto);
			logger.info("VISTRANSK deleted, dto="+vistranskHeadDto);
			
		} 
		catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr(), vistranskHeadDto.getPosnr()));
			logger.error("Could not syncronize vistransk, due to Visma.net error="+e.getStatusText(), e);  //Status text holds Response body from Visma.net
			throw e;
		} 
		catch (RestClientException | IndexOutOfBoundsException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr(), vistranskHeadDto.getPosnr()));
			logger.error("Could not syncronize vistransk="+vistranskHeadDto, e);
			throw e;
		}
		catch (Exception e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr(), vistranskHeadDto.getPosnr()));
			logger.error("Could not syncronize vistransk="+vistranskHeadDto, e);
			throw e;
			
		}
		
	}

	private void deleteVistransk(VistranskHeadDto vistranskHeadDto) {
		VistranskDao dao = getVistranskDao(vistranskHeadDto);
		vistranskDaoService.delete(dao);
	}

	private void updateVistranskOnError(VistranskHeadDto vistranskHeadDto, String errorText) {
		VistranskDao dao = getVistranskDao(vistranskHeadDto);

		LocalDateTime now = LocalDateTime.now();
		String nowDate = now.format(dateFormatter);
		int syncDa = Integer.valueOf(nowDate);		

		dao.setSyncda(syncDa);
		dao.setSyerro(errorText);		
		
		vistranskDaoService.update(dao);
		
	}	

	private VistranskDao getVistranskDao(VistranskHeadDto vistranskHeadDto) {
		VistranskDao dao = new VistranskDao();
		dao.setRecnr(vistranskHeadDto.getPosnr());
		dao.setBilnr(vistranskHeadDto.getBilnr());
		dao.setPoslnr(vistranskHeadDto.getPosnr());
		
		return dao;
	}


	private ViskulogDao getViskulogDao(ViskundeDao viskundeDao, String errorText) {
		String syerror;
		ViskulogDao dao = new ViskulogDao();
		dao.setFirma(viskundeDao.getFirma());
		dao.setKnavn(viskundeDao.getKnavn());
		dao.setKundnr(viskundeDao.getKundnr());

		if (errorText != null) {
			if (errorText.length() < 200) {
				syerror = errorText;
			}
			int beginIndex = errorText.length() - 199;  //syerro is set to 200
			syerror = errorText.substring(beginIndex);		
			dao.setSyerro(syerror);
			dao.setStatus("ER");
		}

		LocalDateTime now = LocalDateTime.now();
		String nowDate = now.format(dateFormatter);
		String nowTime = now.format(timeFormatter);
		int syncDa = Integer.valueOf(nowDate);
		int synctm = Integer.valueOf(nowTime);	
		dao.setSyncda(syncDa);
		dao.setSynctm(synctm);
		
		return dao;
	}

}
