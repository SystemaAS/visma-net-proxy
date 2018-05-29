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
import no.systema.jservices.common.dao.VistranskDao;
import no.systema.jservices.common.dao.services.BridfDaoService;
import no.systema.jservices.common.dao.services.ViskulogDaoService;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.jservices.common.dao.services.VistranskDaoService;
import no.systema.visma.dto.ViskundeDto;
import no.systema.visma.dto.VistranskDto;

@RestController
public class DataController {
	private static Logger logger = Logger.getLogger(DataController.class.getName());	

	@Autowired
	ViskulogDaoService viskulogDao;
	
	@Autowired
	ViskundeDaoService viskundeDao;

	@Autowired
	VistranskDaoService vistranskDaoService;	
	
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
		
		viskulogDaoList = viskulogDao.findAllInFirma(qKundnr, qFraDato);
		
		return viskulogDaoList;

	}
	
	/**
	 * Example :  http://gw.systema.no:8080/visma-net-proxy/viskunde?user=SYSTEMA&kundnr=1&fraDato=20180101
	 * @param kundnr
	 * @param fraDato
	 * @return
	 */
	@RequestMapping(path = "/viskunde", method = RequestMethod.GET)
	@SneakyThrows
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
		
		viskundeDaoList = viskundeDao.findAllInFirma(qKundnr, qFraDato);
		
		return convertToViskundeDto(viskundeDaoList);

	}

	/**
	 * Example :  http://gw.systema.no:8080/visma-net-proxy/vistransk?user=SYSTEMA&kundnr=1&fraDato=20180101
	 * @param kundnr
	 * @param fraDato
	 * @return
	 */
	@RequestMapping(path = "/vistransk", method = RequestMethod.GET)
	@SneakyThrows
	public List<VistranskDto> getVistransk(@RequestParam("user") String user, @RequestParam("kundnr") String kundnr, @RequestParam("fraDato") String fraDato) {
		logger.debug("/vistransk entered...");
		List<VistranskDao> vistranskDaoList;		
		int qKundnr = 0;
		int qFraDato = 0;

		checkUser(user);

		if( !kundnr.equals("ALL") ){
			qKundnr = Integer.valueOf(kundnr);		
		}
		if( !fraDato.equals("ALL") ){
			qFraDato = Integer.valueOf(fraDato);
		}			
		
		vistranskDaoList = vistranskDaoService.findAllInFirma(qKundnr, qFraDato);
		
		return convertToVistranskDto(vistranskDaoList);

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


	private void checkUser(String user) {
		if (bridfDaoService.getUserName(user) == null) {
			throw new RuntimeException("ERROR: parameter, user, is not valid!");
		}		
	}
	
	
}
