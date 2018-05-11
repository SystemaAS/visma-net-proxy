package no.systema.main.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.systema.jservices.common.dao.ViskulogDao;
import no.systema.jservices.common.dao.services.BridfDaoService;
import no.systema.jservices.common.dao.services.ViskulogDaoService;

@RestController
public class DataController {
	private static Logger logger = Logger.getLogger(DataController.class.getName());	

	@Autowired
	ViskulogDaoService viskulogDao;
	
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

		String userName = bridfDaoService.getUserName(user);
		if (userName == null) {
			logger.error("user is null, must be delivered");
			throw new RuntimeException("ERROR: parameter, user, must be delivered!");
		}

		//TODO add support for kundnr and fraDato
		return viskulogDao.findAll(null);

	}
}
