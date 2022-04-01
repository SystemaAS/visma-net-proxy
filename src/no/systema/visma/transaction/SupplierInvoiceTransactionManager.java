package no.systema.visma.transaction;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.Vistransl2Dao;
import no.systema.jservices.common.dao.VistranslDao;
import no.systema.jservices.common.dao.VistrloglDao;
import no.systema.jservices.common.dao.services.FirmDaoService;
import no.systema.jservices.common.dao.services.Vistransl2DaoService;
import no.systema.jservices.common.dao.services.VistranslDaoService;
import no.systema.jservices.common.dao.services.VistrloglDaoService;
import no.systema.visma.dto.PrettyPrintVistranskError;
import no.systema.visma.dto.PrettyPrintVistranslError;
import no.systema.visma.dto.Vistransl2Transformer;
import no.systema.visma.dto.VistranslHeadDto;
import no.systema.visma.dto.VistranslTransformer;
import no.systema.visma.integration.LogHelper;
import no.systema.visma.integration.SupplierInvoice;
import no.systema.visma.integration.SupplierInvoice2;

@Service
public class SupplierInvoiceTransactionManager {
	/**
	 * Separate log: ${catalina.home}/logs/log4j_visma-net-proxy-transaction.log
	 */
	private static Logger logger = LoggerFactory.getLogger(SupplierInvoiceTransactionManager.class);
	
	@Autowired
	SupplierInvoice supplierInvoice;
	
	@Autowired
	SupplierInvoice2 supplierInvoice2;
	
	@Autowired
	VistranslDaoService vistranslDaoService;
	@Autowired
	Vistransl2DaoService vistransl2DaoService;

	@Autowired
	FirmDaoService firmDaoService;
	
	@Autowired
	VistrloglDaoService vistrloglDaoService;	
	
	/**
	 * Syncronize all VISTRANSK with CustomerInvoice in Visma.net <br>
	 * 
	 * @return List of {@link PrettyPrintVistranskError} , could be empty
	 */
	public List<PrettyPrintVistranslError> syncronizeSupplierInvoices() {
		logger.info("Syncronizing all records in VISTRANSL -> SupplierInvoice.");
		List<PrettyPrintVistranslError> errorList = new ArrayList<PrettyPrintVistranslError>();

		List<VistranslDao> vistranslDaoList = vistranslDaoService.findAll(null);
		List<VistranslHeadDto> headDtolist = VistranslTransformer.transform( vistranslDaoList );
		
		headDtolist.forEach((headDto) -> {
			try {

				syncronizeSupplierInvoice(headDto);
				
				deleteVistransl(headDto);

			} 
			catch (HttpClientErrorException e) {
				logger.error(e.toString());
				errorList.add(new PrettyPrintVistranslError(headDto.getResnr(), headDto.getBilnr(), LocalDateTime.now(), e.getStatusText()));
				updateVistranslOnError(headDto, e.getStatusText());
				createVistrlogl(headDto, e.getStatusText());
				//continues with next dao in list
			}		
			catch (Exception e) {
				logger.error(e.toString());
				errorList.add(new PrettyPrintVistranslError(headDto.getResnr(), headDto.getBilnr(), LocalDateTime.now(), e.getMessage()));
				updateVistranslOnError(headDto, e.getMessage());
				createVistrlogl(headDto, e.getMessage());
				//continues with next dao in list
			}

		});

		logger.info("Syncronized ("+vistranslDaoList.size()+") of grouped BILNR in VISTRANSL -> SupplierInvoice.");
		logger.info("Error list size="+errorList.size());
		
		return errorList;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<PrettyPrintVistranslError> syncronizeSupplierInvoicesTEST() {
		logger.info("Syncronizing all records in VISTRANSL2 -> SupplierInvoice.");
		List<PrettyPrintVistranslError> errorList = new ArrayList<PrettyPrintVistranslError>();

		List<Vistransl2Dao> vistranslDaoList = vistransl2DaoService.findAll(null);
		List<VistranslHeadDto> headDtolist = Vistransl2Transformer.transform( vistranslDaoList );
		
		headDtolist.forEach((headDto) -> {
			try {

				syncronizeSupplierInvoice2(headDto);
				
				deleteVistransl(headDto);

			} 
			catch (HttpClientErrorException e) {
				logger.error(e.toString());
				errorList.add(new PrettyPrintVistranslError(headDto.getResnr(), headDto.getBilnr(), LocalDateTime.now(), e.getStatusText()));
				updateVistranslOnError(headDto, e.getStatusText());
				createVistrlogl(headDto, e.getStatusText());
				//continues with next dao in list
			}		
			catch (Exception e) {
				logger.error(e.toString());
				errorList.add(new PrettyPrintVistranslError(headDto.getResnr(), headDto.getBilnr(), LocalDateTime.now(), e.getMessage()));
				updateVistranslOnError(headDto, e.getMessage());
				createVistrlogl(headDto, e.getMessage());
				//continues with next dao in list
			}

		});

		logger.info("Syncronized ("+vistranslDaoList.size()+") of grouped BILNR in VISTRANSL2 -> SupplierInvoice.");
		logger.info("Error list size="+errorList.size());
		
		return errorList;
		
	}
	
	
	private void createVistrlogl(VistranslHeadDto headDto) {
		VistrloglDao vistrloglDao = getVistrloglDao(headDto, null);
		vistrloglDaoService.create(vistrloglDao);
		logger.debug("VISTRLOGL created, dao="+vistrloglDao);	
		
	}

	private void createVistrlogl(VistranslHeadDto headDto, String errorText) {
		VistrloglDao vistrloglDao = getVistrloglDao(headDto, errorText);
		vistrloglDaoService.create(vistrloglDao);
		logger.debug("VISTRLOGL created, dao="+vistrloglDao);	
		
	}	
	
	private void syncronizeSupplierInvoice(VistranslHeadDto vistranslHeadDto) throws RestClientException,  IndexOutOfBoundsException { 
		logger.info("syncronizeSupplierInvoice"+LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));

		try {
			
			supplierInvoice.syncronize(vistranslHeadDto);
			logger.info(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()) + " syncronized.");

		} 
		catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransl, due to Visma.net error="+e.getStatusText());  //Status text holds Response body from Visma.net
			throw e;
		} 
		catch (RestClientException | IndexOutOfBoundsException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransl="+vistranslHeadDto, e);
			throw e;
		}
		catch (IOException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransl="+vistranslHeadDto, e);
			throw new RuntimeException("Could not find file", e.getCause());
		}		
		catch (Exception e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransl="+vistranslHeadDto, e);
			throw e;
			
		}
		
	}
	private void syncronizeSupplierInvoice2(VistranslHeadDto vistranslHeadDto) throws RestClientException,  IndexOutOfBoundsException { 
		logger.info("syncronizeSupplierInvoice"+LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));

		try {
			
			supplierInvoice2.syncronize(vistranslHeadDto);
			logger.info(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()) + " syncronized.");

		} 
		catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransl, due to Visma.net error="+e.getStatusText());  //Status text holds Response body from Visma.net
			throw e;
		} 
		catch (RestClientException | IndexOutOfBoundsException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransl="+vistranslHeadDto, e);
			throw e;
		}
		catch (IOException e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransl="+vistranslHeadDto, e);
			throw new RuntimeException("Could not find file", e.getCause());
		}		
		catch (Exception e) {
			logger.error(LogHelper.logPrefixSupplierInvoice(vistranslHeadDto.getResnr(), vistranslHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransl="+vistranslHeadDto, e);
			throw e;
			
		}
		
	}

	private void deleteVistransl(VistranslHeadDto vistranslHeadDto) {
		vistranslDaoService.deleteAll(vistranslHeadDto.getFirma(), vistranslHeadDto.getBilnr());
		logger.info("VISTRANSL rows deleted for headDto="+vistranslHeadDto);

	}

	private void updateVistranslOnError(VistranslHeadDto vistranslHeadDto, String errorText) {
		VistranslDao dao = new VistranslDao();
		dao.setFirma(vistranslHeadDto.getFirma());
		dao.setResnr(vistranslHeadDto.getResnr());
		dao.setBilnr(vistranslHeadDto.getBilnr());

		int[] dato = LogHelper.getNowDato();			
		dao.setSyncda(dato[0]);
		dao.setSyerro(errorText);		
		
		vistranslDaoService.updateOnError(dao);
		
	}	

	private VistrloglDao getVistrloglDao(VistranslHeadDto headDto, String errorText) {
		String syerror;
		VistrloglDao dao = new VistrloglDao();

		dao.setFirma(headDto.getFirma());
		dao.setBilnr(headDto.getBilnr());
		dao.setBilaar(headDto.getKrdaar());
		dao.setBilmnd(headDto.getKrdmnd());
		dao.setBildag(headDto.getKrddag());

		if (errorText != null) {
			syerror = LogHelper.trimToError(errorText);
			dao.setSyerro(syerror);
			dao.setStatus("ER");
		} else {
			dao.setStatus("OK");			
		}

		int[] dato = LogHelper.getNowDato();	
		dao.setSyncda(dato[0]);
		dao.setSynctm(dato[1]);
		
		return dao;
	}	

}
