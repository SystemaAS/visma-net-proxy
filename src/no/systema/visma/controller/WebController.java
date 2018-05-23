package no.systema.visma.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import no.systema.jservices.common.dao.CundfDao;
import no.systema.jservices.common.dao.FirmvisDao;
import no.systema.jservices.common.dao.services.BridfDaoService;
import no.systema.jservices.common.dao.services.CundfDaoService;
import no.systema.jservices.common.dao.services.FirmvisDaoService;
import no.systema.jservices.common.util.Log4jUtils;
import no.systema.jservices.common.util.StringUtils;
import no.systema.main.model.SystemaWebUser;
import no.systema.main.util.AppConstants;
import no.systema.main.util.StringManager;
import no.systema.main.validator.LoginValidator;
import no.systema.visma.PrettyPrintViskundeError;
import no.systema.visma.transaction.CustomerTransactionManager;

@Controller
@SessionAttributes(AppConstants.SYSTEMA_WEB_USER_KEY)
@Scope("session")
public class WebController {
	private static final Logger logger = Logger.getLogger(WebController.class.getName());
	private ModelAndView loginView = new ModelAndView("redirect:logout.do");
	private StringManager strMgr = new StringManager();
	private LoginValidator loginValidator = new LoginValidator();

	@Autowired
	FirmvisDaoService firmvisDaoService;

	@Autowired
	CundfDaoService cundfDaoService;

	@Autowired
	BridfDaoService bridfDaoService;
	
	@Autowired
	CustomerTransactionManager transactionManager;
	
	/**
	 * Example: http://gw.systema.no:8080/visma-net-proxy/syncronizeCustomers.do?user=SYSTEMA
	 */
	@RequestMapping(value="syncronizeCustomers.do", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String syncCustomers(@RequestParam("user") String user, HttpSession session, HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		logger.info("syncronizeCustomers.do...");

		checkUser(user);

		List<PrettyPrintViskundeError> errorList = transactionManager.syncronizeCustomers();

		if (errorList.isEmpty()) {
			sb.append("syncronizeCustomers executed without errors. \n \n");
		} else {
			sb.append("syncronizeCustomers executed WITH errors.  \n \n");
		}

//		sb.append(FlipTableConverters.fromIterable(errorList, PrettyPrintViskundeError.class));

		if (request.getMethod().equals(RequestMethod.GET.toString())) {
			session.invalidate();
		}

		return sb.toString();

	}	
	
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

	@RequestMapping(value = "customer.do", method={RequestMethod.GET})
	public ModelAndView doCustomer(HttpSession session, HttpServletRequest request) {
		SystemaWebUser appUser = (SystemaWebUser) session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
		ModelAndView successView = new ModelAndView("customer");
		logger.info("Inside: customer");

		if (appUser == null) {
			return loginView;
		} else {
			return successView;
		}
		
	}	

	@RequestMapping(value = "viskulog.do", method={RequestMethod.GET})
	public ModelAndView getViskulog(HttpSession session, HttpServletRequest request) {
		SystemaWebUser appUser = (SystemaWebUser) session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
		ModelAndView successView = new ModelAndView("viskulog");
		logger.info("Inside: viskulog");

		if (appUser == null) {
			return loginView;
		} else {
			return successView;
		}
	}	
	
	@RequestMapping(value = "viskunde.do", method={RequestMethod.GET})
	public ModelAndView getViskunde(HttpSession session, HttpServletRequest request) {
		SystemaWebUser appUser = (SystemaWebUser) session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
		ModelAndView successView = new ModelAndView("viskunde");
		logger.info("Inside: viskunde");

		if (appUser == null) {
			return loginView;
		} else {
			return successView;
		}
	}	
	
	
	@RequestMapping(value = "supplier.do", method={RequestMethod.GET})
	public ModelAndView doSupplier(HttpSession session, HttpServletRequest request) {
		SystemaWebUser appUser = (SystemaWebUser) session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
		ModelAndView successView = new ModelAndView("supplier");
		logger.info("Inside: supplier");

		if (appUser == null) {
			return loginView;
		} else {
			return successView;
		}
	}

	/**
	 * This method serve as data populater for all child windows.
	 * 
	 */
	@RequestMapping(value="childwindow_codes.do",  method={RequestMethod.GET} )
	public ModelAndView getCodes(HttpSession session, HttpServletRequest request){
		ModelAndView successViewCustomer = new ModelAndView("mainmaintenance_childwindow_customer");
		SystemaWebUser appUser = (SystemaWebUser)session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
		Map model = new HashMap();
		String caller = request.getParameter("caller");  //Field in jsp
		
		if (appUser == null) {
			return this.loginView;
		} else {

			model.put("caller", caller);

			if ("selectKundenr".equals(caller)) { // Reuse of mainmaintenance_childwindow_customer.jsp
				model.put("ctype", caller);
				successViewCustomer.addObject("model", model);
				return successViewCustomer;
			} else {
				throw new IllegalArgumentException(caller + " is not supported.");
			}
		}
	}	

	/**
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="mainmaintenance_childwindow_customer.do", params="action=doFind",  method={RequestMethod.GET, RequestMethod.POST} )
	public ModelAndView searchCustomer(HttpSession session, HttpServletRequest request){
		logger.info("Inside mainmaintenance_childwindow_customer");
		
		ModelAndView successView = new ModelAndView("mainmaintenance_childwindow_customer");
		Map model = new HashMap();
		String callerType = request.getParameter("ctype");
		String firma = request.getParameter("firma");
		String customerName = request.getParameter("sonavn");
		String customerNr = request.getParameter("knr");
		logger.info("callerType:" + callerType);
		logger.info("customerName:" + customerName);
		SystemaWebUser appUser = this.loginValidator.getValidUser(session);
		//fallback to FIRMA
		if(strMgr.isNull(firma)){
			firma = appUser.getCompanyCode();
		}
		
		if(appUser==null){
			return this.loginView;
				
		}else{
			Map<String, Object> params = new HashMap<String, Object>();
			List<CundfDao> list = new ArrayList<CundfDao>();

			if( StringUtils.hasValue(customerNr) ){
				params.put("kundnr", customerNr);				
			}
			if( StringUtils.hasValue(customerName) ){
				params.put("knavn", "%" + customerName + "%" );	
			}			
			
			if (params.isEmpty()){
				list = cundfDaoService.findAllInFirma(null);
			} else {
				list = cundfDaoService.findAllInFirma(params);				
			}
			
			model.put("customerList", list);
			model.put("sonavn", customerName);
			model.put("knr", customerNr);
			model.put("ctype", callerType);
			model.put("firma", firma);
			
			successView.addObject("model", model);
			
	    	return successView;	
		  	
		}
		
	}
	

	/**
	 * 
	 * @Example: http://gw.systema.no:8080/visma-net-proxy/showHistory.do?user=SYSTEMA&filename=log4j_visma-net-proxy-transaction.log
	 * 
	 * @param session
	 * @param request, user 
	 * @return status
	 */	
	@RequestMapping(value = "showHistory.do", method = { RequestMethod.GET })
	@ResponseBody
	public String showHistory(@RequestParam("user") String user, HttpSession session, HttpServletRequest request) {
		logger.info("showHistory.do...");
		StringBuilder logResult = new StringBuilder();

		checkUser(user);

		String fileName = request.getParameter("filename");
		Assert.notNull(fileName, "fileName must be delivered.");

		try {
			logResult.append(Log4jUtils.getLog4jData(fileName));
		} catch (IOException e) {
			logger.info(e);
			logResult.append(e);
		} finally {
			session.invalidate();
		}

		return logResult.toString();

	}	

	private void checkUser(String user) {
		if (bridfDaoService.getUserName(user) == null) {
			throw new RuntimeException("ERROR: parameter, user, is not valid!");
		}		
	}
	
	
	
	
}
