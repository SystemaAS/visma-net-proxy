package no.systema.visma.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.systema.jservices.common.dao.ViskulogDao;
import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.services.BridfDaoService;
import no.systema.jservices.common.dao.services.ViskulogDaoService;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.visma.ViskundeDto;

@RestController
public class DataController {
	private static Logger logger = Logger.getLogger(DataController.class.getName());	

	@Autowired
	ViskulogDaoService viskulogDao;
	
	@Autowired
	ViskundeDaoService viskundeDao;
	
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

		checkUser(user);

		//TODO add support for kundnr and fraDato
		return viskulogDao.findAll(null);

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

		checkUser(user);

		//TODO add support for kundnr and fraDato
		List<ViskundeDao> viskundeDaoList = viskundeDao.findAll(null);
		
		return convertToDto(viskundeDaoList);
		

	}


	private List<ViskundeDto> convertToDto(List<ViskundeDao> viskundeDaoList) {
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
			dto.setSyncda(LocalDate.ofEpochDay(dao.getSyncda()));
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
