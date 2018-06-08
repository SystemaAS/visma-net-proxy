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

import no.systema.jservices.common.dao.VistranskDao;
import no.systema.jservices.common.dao.VistrlogkDao;
import no.systema.jservices.common.dao.services.FirmDaoService;
import no.systema.jservices.common.dao.services.VistranskDaoService;
import no.systema.jservices.common.dao.services.VistrlogkDaoService;
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
	
	@Autowired
	VistrlogkDaoService VistrlogkDaoService;	
	
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
				
				deleteVistransk(headDto);
				
				createVistrlogk(headDto);

			} 
			catch (HttpClientErrorException e) {
				logger.error(e);
				errorList.add(new PrettyPrintVistranskError(headDto.getRecnr(), headDto.getBilnr(), LocalDateTime.now(), e.getStatusText()));
				updateVistranskOnError(headDto, e.getStatusText());
				createVistrlogk(headDto, e.getStatusText());
				//continues with next dao in list
			}		
			catch (Exception e) {
				logger.error(e);
				errorList.add(new PrettyPrintVistranskError(headDto.getRecnr(), headDto.getBilnr(), LocalDateTime.now(), e.getMessage()));
				updateVistranskOnError(headDto, e.getMessage());
				createVistrlogk(headDto, e.getMessage());
				//continues with next dao in list
			}

		});

		logger.info("Syncronized ("+vistranskDaoList.size()+") of grouped BILNR in VISTRANSK -> CustomerInvoice.");
		logger.info("Error list size="+errorList.size());
		
		return errorList;
		
	}
	
	private void createVistrlogk(VistranskHeadDto headDto) {
		VistrlogkDao vistrlogkDao = getVistrlogkDao(headDto, null);
		VistrlogkDaoService.create(vistrlogkDao);
		logger.info("VISTRLOGK created, dao="+vistrlogkDao);	
		
	}

	private void createVistrlogk(VistranskHeadDto headDto, String errorText) {
		VistrlogkDao vistrlogkDao = getVistrlogkDao(headDto, errorText);
		VistrlogkDaoService.create(vistrlogkDao);
		logger.info("VISTRLOGK created, dao="+vistrlogkDao);	
		
	}	
	
	private void syncronizeCustomerInvoice(VistranskHeadDto vistranskHeadDto) throws RestClientException,  IndexOutOfBoundsException { 
		logger.info(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr()));

		try {
			
			customerInvoice.syncronize(vistranskHeadDto);
			logger.info(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr()) + " syncronized.");

		} 
		catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransk, due to Visma.net error="+e.getStatusText(), e);  //Status text holds Response body from Visma.net
			throw e;
		} 
		catch (RestClientException | IndexOutOfBoundsException e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransk="+vistranskHeadDto, e);
			throw e;
		}
		catch (Exception e) {
			logger.error(LogHelper.logPrefixCustomerInvoice(vistranskHeadDto.getRecnr(), vistranskHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransk="+vistranskHeadDto, e);
			throw e;
			
		}
		
	}

	private void deleteVistransk(VistranskHeadDto vistranskHeadDto) {
		vistranskDaoService.deleteAll(vistranskHeadDto.getFirma(), vistranskHeadDto.getBilnr());
		logger.info("VISTRANSK rows deleted for headDto="+vistranskHeadDto);

	}

	private void updateVistranskOnError(VistranskHeadDto vistranskHeadDto, String errorText) {
		LocalDateTime now = LocalDateTime.now();
		String nowDate = now.format(dateFormatter);
		int syncDa = Integer.valueOf(nowDate);		

		VistranskDao dao = new VistranskDao();
		dao.setFirma(vistranskHeadDto.getFirma());
		dao.setRecnr(vistranskHeadDto.getRecnr());
		dao.setBilnr(vistranskHeadDto.getBilnr());
		dao.setSyncda(syncDa);
		dao.setSyerro(errorText);		
		
		vistranskDaoService.updateOnError(dao);
		
	}	

	private VistrlogkDao getVistrlogkDao(VistranskHeadDto headDto, String errorText) {
		String syerror;
		VistrlogkDao dao = new VistrlogkDao();

		dao.setFirma(headDto.getFirma());
		dao.setBilnr(headDto.getBilnr());
		dao.setBilaar(headDto.getBilaar());
		dao.setBilmnd(headDto.getBilmnd());
		dao.setBildag(headDto.getBildag());

		if (errorText != null) {
			syerror = LogHelper.trimToError(errorText);
			dao.setSyerro(syerror);
			dao.setStatus("ER");
		} else {
			dao.setStatus("OK");			
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
