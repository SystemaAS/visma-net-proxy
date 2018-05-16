package no.systema.visma.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.main.model.SystemaWebUser;
import no.systema.main.util.AppConstants;

@Controller
@SessionAttributes(AppConstants.SYSTEMA_WEB_USER_KEY)
@Scope("session")
public class WebController {
	private static final Logger logger = Logger.getLogger(WebController.class.getName());
	private ModelAndView loginView = new ModelAndView("redirect:logout.do");

	@Autowired
	FirmvisDaoService firmvisDaoService;

	
	@RequestMapping(value = "configuration.do", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView doConfiguration(@ModelAttribute ("firmvis") FirmvisDao firmvis, BindingResult bindingResult, HttpSession session, HttpServletRequest request) {
		SystemaWebUser appUser = (SystemaWebUser)session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
		ModelAndView successView = new ModelAndView("visma_configuration"); 
		Map model = new HashMap();
		logger.info("Inside: configuration");
		
		if (appUser == null) {
			return loginView;
		} else {

			if (request.getMethod().equals(RequestMethod.POST.toString())){ //TODO new
				firmvisDaoService.update(firmvis);
			} 
			
			firmvis = firmvisDaoService.get();

			model.put("firmvis", firmvis);
			successView.addObject("model", model);

			return successView;
		
		}
	}

	//TODO create customer.do
	@RequestMapping(value = "customer.do", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView doCustomer( HttpSession session, HttpServletRequest request) {
		SystemaWebUser appUser = (SystemaWebUser)session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
		ModelAndView successView = new ModelAndView("customer"); 
		Map model = new HashMap();
		logger.info("Inside: customer");
		
		if (appUser == null) {
			return loginView;
		} else {


			
//			firmvis = firmvisDaoService.get();
//
//			model.put("firmvis", firmvis);
			successView.addObject("model", model);

			return successView;
		
		}
	}	

	//TODO create viskulog.do
	@RequestMapping(value = "viskulog.do", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getViskulog( HttpSession session, HttpServletRequest request) {
		SystemaWebUser appUser = (SystemaWebUser)session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
		ModelAndView successView = new ModelAndView("viskulog"); 
		Map model = new HashMap();
		logger.info("Inside: viskulog");
		
		if (appUser == null) {
			return loginView;
		} else {


			
//			firmvis = firmvisDaoService.get();
//
//			model.put("firmvis", firmvis);
			successView.addObject("model", model);

			return successView;
		
		}
	}	
	
	//TODO create viskunde.do
	@RequestMapping(value = "viskunde.do", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getViskunde( HttpSession session, HttpServletRequest request) {
		SystemaWebUser appUser = (SystemaWebUser)session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
		ModelAndView successView = new ModelAndView("viskunde"); 
		Map model = new HashMap();
		logger.info("Inside: viskunde");
		
		if (appUser == null) {
			return loginView;
		} else {


			
//			firmvis = firmvisDaoService.get();
//
//			model.put("firmvis", firmvis);
			successView.addObject("model", model);

			return successView;
		
		}
	}		
	
	
	
	//TODO create supplier.do
	@RequestMapping(value = "supplier.do", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView doSupplier( HttpSession session, HttpServletRequest request) {
		SystemaWebUser appUser = (SystemaWebUser)session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
		ModelAndView successView = new ModelAndView("supplier"); 
		Map model = new HashMap();
		logger.info("Inside: supplier");
		
		if (appUser == null) {
			return loginView;
		} else {


			
//			firmvis = firmvisDaoService.get();
//
//			model.put("firmvis", firmvis);
			successView.addObject("model", model);

			return successView;
		
		}
	}	
	
	
}
