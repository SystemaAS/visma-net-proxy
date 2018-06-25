package no.systema.visma.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.SneakyThrows;
import no.systema.jservices.common.dao.ViskulogDao;
import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.VislelogDao;
import no.systema.jservices.common.dao.VisleveDao;
import no.systema.jservices.common.dao.VistranskDao;
import no.systema.jservices.common.dao.VistrlogkDao;
import no.systema.jservices.common.dao.services.BridfDaoService;
import no.systema.jservices.common.dao.services.ViskulogDaoService;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.jservices.common.dao.services.VislelogDaoService;
import no.systema.jservices.common.dao.services.VisleveDaoService;
import no.systema.jservices.common.dao.services.VistranskDaoService;
import no.systema.jservices.common.dao.services.VistrlogkDaoService;
import no.systema.visma.dto.ViskundeDto;
import no.systema.visma.dto.VisleveDto;
import no.systema.visma.dto.VistranskDto;

@RestController
public class DataController {
	private static Logger logger = Logger.getLogger(DataController.class.getName());	

	@Autowired
	ViskulogDaoService viskulogDaoService;

	@Autowired
	VislelogDaoService vislelogDaoService;	
	
	@Autowired
	ViskundeDaoService viskundeDaoService;

	@Autowired
	VisleveDaoService visleveDaoService;	
	
	@Autowired
	VistranskDaoService vistranskDaoService;	
	
	@Autowired
	VistrlogkDaoService vistrlogkDaoService;	
	
	@Autowired
	private BridfDaoService bridfDaoService;
	
	/**
	 * Example :  http://gw.systema.no:8080/visma-net-proxy/viskulog?user=SYSTEMA&kundnr=1&fraDato=20180101
	 * @param kundnr
	 * @param fraDato
	 * @return
	 */
	@RequestMapping(path = "/viskulog", method = RequestMethod.GET)
	public List<ViskulogDao> getViskulog(@RequestParam("user") String user, @RequestParam("kundnr") String kundnr, @RequestParam("fraDato") String fraDato) {
		logger.debug("/viskulog entered...");
		List<ViskulogDao> viskulogDaoList;		
		int qKundnr = 0;
		int qFraDato = 0;

		checkUser(user);

		if( !kundnr.equals("ALL") ){
			qKundnr = Integer.valueOf(kundnr);		
		}
		if( !fraDato.equals("ALL") ){
			qFraDato = Integer.valueOf(fraDato);
		}		
		
		viskulogDaoList = viskulogDaoService.findAllInFirma(qKundnr, qFraDato);
		
		return viskulogDaoList;

	}

	/**
	 * Example :  http://gw.systema.no:8080/visma-net-proxy/vislelog?user=SYSTEMA&levnr=1&fraDato=20180101
	 * @param kundnr
	 * @param fraDato
	 * @return
	 */
	@RequestMapping(path = "/vislelog", method = RequestMethod.GET)
	public List<VislelogDao> getVislelog(@RequestParam("user") String user, @RequestParam("levnr") String levnr, @RequestParam("fraDato") String fraDato) {
		logger.debug("/vislelog entered...");
		List<VislelogDao> vislelogDaoList;		
		int qKundnr = 0;
		int qFraDato = 0;

		checkUser(user);

		if( !levnr.equals("ALL") ){
			qKundnr = Integer.valueOf(levnr);		
		}
		if( !fraDato.equals("ALL") ){
			qFraDato = Integer.valueOf(fraDato);
		}		
		
		vislelogDaoList = vislelogDaoService.findAllInFirma(qKundnr, qFraDato);
		
		return vislelogDaoList;

	}	
	
	
	/**
	 * Example :  http://gw.systema.no:8080/visma-net-proxy/viskunde?user=SYSTEMA&kundnr=1&fraDato=20180101
	 * @param kundnr
	 * @param fraDato
	 * @return
	 */
	@RequestMapping(path = "/viskunde", method = RequestMethod.GET)
	public List<ViskundeDto> getViskunde(@RequestParam("user") String user, @RequestParam("kundnr") String kundnr, @RequestParam("fraDato") String fraDato) {
		logger.debug("/viskunde entered...");
		List<ViskundeDao> viskundeDaoList;		
		int qKundnr = 0;
		int qFraDato = 0;

		checkUser(user);

		if( !kundnr.equals("ALL") ){
			qKundnr = Integer.valueOf(kundnr);		
		}
		if( !fraDato.equals("ALL") ){
			qFraDato = Integer.valueOf(fraDato);
		}			
		
		viskundeDaoList = viskundeDaoService.findAllInFirma(qKundnr, qFraDato);
		
		return convertToViskundeDto(viskundeDaoList);

	}

	/**
	 * Example :  https://gw.systema.no:8443/visma-net-proxy/visleve?user=SYSTEMA&levnr=1&fraDato=20180101
	 * @param kundnr
	 * @param fraDato
	 * @return
	 */
	@RequestMapping(path = "/visleve", method = RequestMethod.GET)
	public List<VisleveDto> getVisleve(@RequestParam("user") String user, @RequestParam("levnr") String levnr, @RequestParam("fraDato") String fraDato) {
		logger.debug("/visleve entered...");
		List<VisleveDao> visleveDaoList;		
		int qLevnr = 0;
		int qFraDato = 0;

		checkUser(user);

		if( !levnr.equals("ALL") ){
			qLevnr = Integer.valueOf(levnr);		
		}
		if( !fraDato.equals("ALL") ){
			qFraDato = Integer.valueOf(fraDato);
		}			
		
		visleveDaoList = visleveDaoService.findAllInFirma(qLevnr, qFraDato);
		
		return convertToVisleveDto(visleveDaoList);

	}	
	
	
	/**
	 * Example :  http://gw.systema.no:8080/visma-net-proxy/vistransk?user=SYSTEMA&kundnr=1&fraDato=20180101
	 * @param kundnr
	 * @param fraDato
	 * @return
	 */
	@RequestMapping(path = "/vistransk", method = RequestMethod.GET)
	@SneakyThrows
	public List<VistranskDto> getVistransk(@RequestParam("user") String user, @RequestParam("kundnr") String kundnr, @RequestParam("bilnr") String bilnr,@RequestParam("fraDato") String fraDato) {
		logger.debug("/vistransk entered...");
		List<VistranskDao> vistranskDaoList;		
		int qKundnr = 0;
		int qBilnr = 0;
		int qFraDato = 0;

		checkUser(user);

		if( !kundnr.equals("ALL") ){
			qKundnr = Integer.valueOf(kundnr);		
		}
		if( !bilnr.equals("ALL") ){
			qBilnr = Integer.valueOf(bilnr);		
		}
		if( !fraDato.equals("ALL") ){
			qFraDato = Integer.valueOf(fraDato);
		}			
		
		vistranskDaoList = vistranskDaoService.findAllInFirma(qKundnr, qBilnr, qFraDato);
		
		return convertToVistranskDto(vistranskDaoList);

	}	
	
	/**
	 * Example :  http://gw.systema.no:8080/visma-net-proxy/vistrlogk?user=SYSTEMA&kundnr=1&fraDato=20180101
	 * @param kundnr
	 * @param fraDato
	 * @return
	 */
	@RequestMapping(path = "/vistrlogk", method = RequestMethod.GET)
	public List<VistrlogkDao> getVistrlogk(@RequestParam("user") String user, @RequestParam("kundnr") String kundnr, @RequestParam("fraDato") String fraDato) {
		logger.debug("/vistrlogk entered...");
		List<VistrlogkDao> vistrlogkDaoList;		
		int qKundnr = 0;
		int qFraDato = 0;

		checkUser(user);

		if( !kundnr.equals("ALL") ){
			qKundnr = Integer.valueOf(kundnr);		
		}
		if( !fraDato.equals("ALL") ){
			qFraDato = Integer.valueOf(fraDato);
		}		
		
		vistrlogkDaoList = vistrlogkDaoService.findAllInFirma(qKundnr, qFraDato);
		
		return vistrlogkDaoList;

	}
	
	private List<VistranskDto> convertToVistranskDto(List<VistranskDao> vistranskDaoList) {
		List<VistranskDto> vistranskDtoList = new ArrayList<VistranskDto>();

		vistranskDaoList.forEach(dao -> {
			VistranskDto dto = new VistranskDto();
			dto.setAktkod(dao.getAktkod());
			dto.setFirma(dao.getFirma());
			dto.setRecnr(dao.getRecnr());
			dto.setBilnr(dao.getBilnr());
			dto.setBetbet(dao.getBetbet());
			dto.setPosnr(dao.getPosnr());
			dto.setKonto(dao.getKonto());
			dto.setKsted(dao.getKsted());
			dto.setKbarer(dao.getKbarer());
			dto.setBiltxt(dao.getBiltxt());
			dto.setSyncda(dao.getSyncda());
			dto.setSyerro(dao.getSyerro());
			
			vistranskDtoList.add(dto);

		});
		
		return vistranskDtoList;
	}

	private List<ViskundeDto> convertToViskundeDto(List<ViskundeDao> viskundeDaoList) {
		List<ViskundeDto> viskundeDtoList = new ArrayList<ViskundeDto>();

		viskundeDaoList.forEach(dao -> {
			ViskundeDto dto = new ViskundeDto();
			dto.setAktkod(dao.getAktkod());
			dto.setFirma(dao.getFirma());
			dto.setKundnr(dao.getKundnr());
			dto.setKnavn(dao.getKnavn());
			dto.setPostnr(dao.getPostnr());
			dto.setValkod(dao.getValkod());
			dto.setSpraak(dao.getSpraak());
			dto.setBetbet(dao.getBetbet());
			dto.setSyland(dao.getSyland());
			dto.setSyncda(dao.getSyncda());
			dto.setSyerro(dao.getSyerro());
			
			viskundeDtoList.add(dto);

		});
		
		return viskundeDtoList;
		
	}	

	private List<VisleveDto> convertToVisleveDto(List<VisleveDao> visleveDaoList) {
		List<VisleveDto> visleveDtoList = new ArrayList<VisleveDto>();

		visleveDaoList.forEach(dao -> {
			VisleveDto dto = new VisleveDto();
			dto.setAktkod(dao.getAktkod());
			dto.setFirma(dao.getFirma());
			dto.setLevnr(dao.getLevnr());
			dto.setLnavn(dao.getLnavn());
			dto.setPostnr(dao.getPostnr());
			dto.setValkod(dao.getValkod());
			dto.setSpraak(dao.getSpraak());
			dto.setBetbet(dao.getBetbet());
			dto.setLand(dao.getLand());
			dto.setSyncda(dao.getSyncda());
			dto.setSyerro(dao.getSyerro());
			
			visleveDtoList.add(dto);

		});
		
		return visleveDtoList;
		
	}	
	
	private void checkUser(String user) {
		if (bridfDaoService.getUserName(user) == null) {
			throw new RuntimeException("ERROR: parameter, user, is not valid!");
		}		
	}
	
	
}
