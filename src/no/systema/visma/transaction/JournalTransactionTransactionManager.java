package no.systema.visma.transaction;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import no.systema.jservices.common.dao.Vistransh2Dao;
import no.systema.jservices.common.dao.VistranshDao;
import no.systema.jservices.common.dao.VistrloghDao;
import no.systema.jservices.common.dao.services.FirmDaoService;
import no.systema.jservices.common.dao.services.Vistransh2DaoService;
import no.systema.jservices.common.dao.services.VistranshDaoService;
import no.systema.jservices.common.dao.services.VistrloghDaoService;
import no.systema.visma.dto.PrettyPrintVistranskError;
import no.systema.visma.dto.PrettyPrintVistranslError;
import no.systema.visma.dto.Vistransh2Transformer;
import no.systema.visma.dto.VistranshHeadDto;
import no.systema.visma.dto.VistranshTransformer;
import no.systema.visma.integration.JournalTransaction;
import no.systema.visma.integration.JournalTransaction2;
import no.systema.visma.integration.LogHelper;

@Service
public class JournalTransactionTransactionManager {
	/**
	 * Separate log: ${catalina.home}/logs/log4j_visma-net-proxy-transaction.log
	 */
	private static Logger logger = LoggerFactory.getLogger(JournalTransactionTransactionManager.class);
	
	@Autowired
	JournalTransaction journalTransaction;
	
	@Autowired
	JournalTransaction2 journalTransaction2;
	
	
	@Autowired
	VistranshDaoService vistranshDaoService;

	@Autowired
	Vistransh2DaoService vistransh2DaoService;

	@Autowired
	FirmDaoService firmDaoService;
	
	@Autowired
	VistrloghDaoService vistrloghDaoService;	
	
	/**
	 * Syncronize all VISTRANSH with JournalTransaction in Visma.net <br>
	 * 
	 * @return List of {@link PrettyPrintVistranskError} , could be empty
	 */
	public List<PrettyPrintVistranslError> syncronizeJournalTransaction() {
		logger.info("Syncronizing all records in VISTRANSH -> JournalTransaction.");
		List<PrettyPrintVistranslError> errorList = new ArrayList<PrettyPrintVistranslError>();

		List<VistranshDao> vistranshDaoList = vistranshDaoService.findAll(null);
		List<VistranshHeadDto> headDtolist = VistranshTransformer.transform(vistranshDaoList);

		headDtolist.forEach((headDto) -> {
			try {

				syncronizeJournalTransaction(headDto);

				deleteVistransh(headDto);

			} catch (HttpClientErrorException e) {
				logger.error(e.toString());
				errorList.add(new PrettyPrintVistranslError(headDto.getResnr(), headDto.getBilnr(), LocalDateTime.now(), e.getStatusText()));
				updateVistranshOnError(headDto, e.getStatusText());
				createVistrlogh(headDto, e.getStatusText());
				// continues with next dao in list
			} catch (Exception e) {
				logger.error(e.toString());
				errorList.add(new PrettyPrintVistranslError(headDto.getResnr(), headDto.getBilnr(), LocalDateTime.now(), e.getMessage()));
				updateVistranshOnError(headDto, e.getMessage());
				createVistrlogh(headDto, e.getMessage());
				// continues with next dao in list
			}

		});

		logger.info("Syncronized (" + vistranshDaoList.size() + ") of grouped BILNR in VISTRANSH -> JournalTransaction.");
		logger.info("Error list size=" + errorList.size());

		return errorList;
		
	}
	/**
	 * TEST DB in visma A38
	 * 
	 * @return
	 */
	public List<PrettyPrintVistranslError> syncronizeJournalTransactionTEST() {
		logger.info("Syncronizing all records in VISTRANSH2 -> JournalTransaction.");
		List<PrettyPrintVistranslError> errorList = new ArrayList<PrettyPrintVistranslError>();

		List<Vistransh2Dao> vistranshDaoList = vistransh2DaoService.findAll(null);
		List<VistranshHeadDto> headDtolist = Vistransh2Transformer.transform(vistranshDaoList);

		headDtolist.forEach((headDto) -> {
			try {

				syncronizeJournalTransaction2(headDto);

				deleteVistransh2(headDto);

			} catch (HttpClientErrorException e) {
				logger.error(e.toString());
				errorList.add(new PrettyPrintVistranslError(headDto.getResnr(), headDto.getBilnr(), LocalDateTime.now(), e.getStatusText()));
				updateVistransh2OnError(headDto, e.getStatusText());
				createVistrlogh(headDto, e.getStatusText());
				// continues with next dao in list
			} catch (Exception e) {
				logger.error(e.toString());
				errorList.add(new PrettyPrintVistranslError(headDto.getResnr(), headDto.getBilnr(), LocalDateTime.now(), e.getMessage()));
				updateVistransh2OnError(headDto, e.getMessage());
				createVistrlogh(headDto, e.getMessage());
				// continues with next dao in list
			}

		});

		logger.info("Syncronized (" + vistranshDaoList.size() + ") of grouped BILNR in VISTRANSH -> JournalTransaction.");
		logger.info("Error list size=" + errorList.size());

		return errorList;
		
	}
	
	private void createVistrlogh(VistranshHeadDto headDto) {
		VistrloghDao vistrloghDao = getVistrloghDao(headDto, null);
		vistrloghDaoService.create(vistrloghDao);
		logger.debug("VISTRLOGH created, dao="+vistrloghDao);	
		
	}

	private void createVistrlogh(VistranshHeadDto headDto, String errorText) {
		VistrloghDao vistrloghDao = getVistrloghDao(headDto, errorText);
		vistrloghDaoService.create(vistrloghDao);
		logger.debug("VISTRLOGH created, dao="+vistrloghDao);	
		
	}	
	
	private void syncronizeJournalTransaction(VistranshHeadDto vistranshHeadDto) throws RestClientException,  IndexOutOfBoundsException { 
		logger.info("syncronizeJournalTransaction:"+LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));

		try {
			
			journalTransaction.syncronize(vistranshHeadDto);
			logger.info(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()) + " syncronized.");

		} 
		catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransh, due to Visma.net error="+e.getStatusText());  //Status text holds Response body from Visma.net
			throw e;
		} 
		catch (RestClientException | IndexOutOfBoundsException e) {
			logger.error(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransh="+vistranshHeadDto, e);
			throw e;
		}
		catch (IOException e) {
			logger.error(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransh="+vistranshHeadDto, e);
			throw new RuntimeException("Could not find file", e.getCause());
		}		
		catch (Exception e) {
			logger.error(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransh="+vistranshHeadDto, e);
			throw e;
			
		}
		
	}
	
	private void syncronizeJournalTransaction2(VistranshHeadDto vistranshHeadDto) throws RestClientException,  IndexOutOfBoundsException { 
		logger.info("syncronizeJournalTransaction2:"+LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));

		try {
			
			journalTransaction2.syncronize(vistranshHeadDto);
			logger.info(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()) + " syncronized.");

		} 
		catch (HttpClientErrorException e) {
			logger.error(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransh2, due to Visma.net error="+e.getStatusText());  //Status text holds Response body from Visma.net
			throw e;
		} 
		catch (RestClientException | IndexOutOfBoundsException e) {
			logger.error(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransh2="+vistranshHeadDto, e);
			throw e;
		}
		catch (IOException e) {
			logger.error(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransh2="+vistranshHeadDto, e);
			throw new RuntimeException("Could not find file", e.getCause());
		}		
		catch (Exception e) {
			logger.error(LogHelper.logPrefixJournalTransaction(vistranshHeadDto.getBilnr()));
			logger.error("Could not syncronize vistransh2="+vistranshHeadDto, e);
			throw e;
			
		}
		
	}

	private void deleteVistransh(VistranshHeadDto vistranshHeadDto) {
		vistranshDaoService.deleteAll(vistranshHeadDto.getFirma(), vistranshHeadDto.getBilnr());
		logger.info("VISTRANSH rows deleted for headDto="+vistranshHeadDto);

	}
	/**
	 * TEST DB A38
	 * @param vistranshHeadDto
	 */
	private void deleteVistransh2(VistranshHeadDto vistranshHeadDto) {
		vistransh2DaoService.deleteAll(vistranshHeadDto.getFirma(), vistranshHeadDto.getBilnr());
		logger.info("VISTRANSH2 rows deleted for headDto="+vistranshHeadDto);

	}

	private void updateVistranshOnError(VistranshHeadDto vistranshHeadDto, String errorText) {
		VistranshDao dao = new VistranshDao();
		dao.setFirma(vistranshHeadDto.getFirma());
		dao.setBilnr(vistranshHeadDto.getBilnr());

		int[] dato = LogHelper.getNowDato();			
		dao.setSyncda(dato[0]);
		dao.setSyerro(errorText);		
		
		vistranshDaoService.updateOnError(dao);
		
	}
	/**
	 * TEST DB A38
	 * @param vistranshHeadDto
	 * @param errorText
	 */
	
	private void updateVistransh2OnError(VistranshHeadDto vistranshHeadDto, String errorText) {
		Vistransh2Dao dao = new Vistransh2Dao();
		dao.setFirma(vistranshHeadDto.getFirma());
		dao.setBilnr(vistranshHeadDto.getBilnr());

		int[] dato = LogHelper.getNowDato();			
		dao.setSyncda(dato[0]);
		dao.setSyerro(errorText);		
		
		vistransh2DaoService.updateOnError(dao);
		
	}	

	private VistrloghDao getVistrloghDao(VistranshHeadDto headDto, String errorText) {
		String syerror;
		VistrloghDao dao = new VistrloghDao();
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
