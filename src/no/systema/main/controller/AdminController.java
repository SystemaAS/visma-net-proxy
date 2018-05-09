package no.systema.main.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import no.systema.jservices.common.dto.SingleValueDto;
import no.systema.jservices.common.json.JsonDtoContainer;
import no.systema.jservices.common.json.JsonReader;
import no.systema.jservices.common.util.StringUtils;
import no.systema.main.model.SystemaWebUser;
import no.systema.main.service.UrlCgiProxyService;
import no.systema.main.util.AppConstants; 
import no.systema.z.main.maintenance.model.jsonjackson.dbtable.JsonMaintMainCundfRecord;
import no.systema.z.main.maintenance.url.store.MaintenanceMainUrlDataStore;

@Controller
@SessionAttributes(AppConstants.SYSTEMA_WEB_USER_KEY)
@Scope("session")
public class AdminController {
	private static final Logger logger = Logger.getLogger(AdminController.class.getName());
	private ModelAndView loginView = new ModelAndView("login");
//	private CodeDropDownMgr codeDropDownMgr = new CodeDropDownMgr();

	/**
	 * @Example
	 * 		http://localhost:8080/espedsg/report_dashboard.do?report=report_fortolling_no
	 * 
	 *
	 * @return
	 */
	@RequestMapping(value = "report_dashboard.do", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView doReportDashboard(HttpSession session, HttpServletRequest request) {
		SystemaWebUser appUser = (SystemaWebUser)session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
		ModelAndView successView = new ModelAndView(); 
		Map model = new HashMap();
		logger.info("Inside: doReportDashboard");
		
		String report = request.getParameter("report");
		logger.info("report="+report);
		
		if(appUser==null){
			return loginView;
		}else{

			
			if (StringUtils.hasValue(report)) {
				successView.setViewName(report);
			} 

			successView.addObject("model", model);			
			successView.addObject("httpRootCgi", AppConstants.HTTP_ROOT_CGI);		
			
			return successView;
		}
	}

//	/**
//	 * This method serve as data populater for all child windows for Report analyses.
//	 * 
//	 * 
//	 * @param session
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="report_dashboard_childwindow_codes.do",  method={RequestMethod.GET} )
//	public ModelAndView getCodes(HttpSession session, HttpServletRequest request){
//		ModelAndView successViewCustomer = new ModelAndView("mainmaintenance_childwindow_customer");
//		SystemaWebUser appUser = (SystemaWebUser)session.getAttribute(AppConstants.SYSTEMA_WEB_USER_KEY);
//		Map model = new HashMap();
//		String caller = request.getParameter("caller");  //Field in jsp
//		
//		if (appUser == null) {
//			return this.loginView;
//		} else {
//
//			List list = getCodeList(appUser, caller);
//			model.put("codeList", list);
//			model.put("caller", caller);
//
//			//setLabels(appUser, model, caller);
//			
//			if ("selectKundenr".equals(caller) || "selectKundenr_avs".equals(caller)) { // Reuse of mainmaintenance_childwindow_customer.jsp
//				model.put("ctype", caller);
//				successViewCustomer.addObject("model", model);
//				return successViewCustomer;
//			} else {
//				throw new IllegalArgumentException(caller + " is not supported.");
//			}
//		}
//	}	
	

	
//	private List<ChildWindowKode> getCodeList(SystemaWebUser appUser, String caller) {
//		List<ChildWindowKode> list = null;
//
//		if ("selectKundenr".equals(caller)) { 
//			list = getKunder(appUser);
//		} else if ("selectKundenr_avs".equals(caller)) { 
//			list = getKunder(appUser);
//		} 		
//		else {
//			throw new IllegalArgumentException(caller + " is not supported.");
//		}
//
//		return list;
//	}
	
//	private List<ChildWindowKode> getKunder(SystemaWebUser appUser) {
//		Collection<JsonMaintMainCundfRecord> list = fetchList(appUser.getUser(), null, null);
//		List<ChildWindowKode> kodeList = new ArrayList<ChildWindowKode>();
//		ChildWindowKode kode = null;
//		for (JsonMaintMainCundfRecord record : list) {
//			kode = getChildWindowKode(record);
//			kodeList.add(kode);
//		}
//
//		return kodeList;
//	}
	
	private ChildWindowKode getChildWindowKode(JsonMaintMainCundfRecord record) {
		ChildWindowKode kode = new ChildWindowKode();
		kode.setCode(record.getKundnr());
		kode.setDescription(record.getKnavn());
		return kode;
	}	
	
	
//	private Collection<JsonMaintMainCundfRecord> fetchList(String applicationUser, String kundnr, String firma) {
//		String BASE_URL = MaintenanceMainUrlDataStore.MAINTENANCE_MAIN_BASE_SYCUNDFR_GET_LIST_URL;
//		StringBuilder urlRequestParams = new StringBuilder();
//		urlRequestParams.append("user=" + applicationUser);
//		if (kundnr != null && firma != null) {
//			urlRequestParams.append("&kundnr=" + kundnr);
//			urlRequestParams.append("&firma=" + firma);
//		}
//
//		logger.info("URL: " + BASE_URL);
//		logger.info("PARAMS: " + urlRequestParams.toString());
//		String jsonPayload = this.urlCgiProxyService.getJsonContent(BASE_URL, urlRequestParams.toString());
//		Collection<JsonMaintMainCundfRecord> list = new ArrayList<JsonMaintMainCundfRecord>();
//		if (jsonPayload != null) {
//			jsonPayload = jsonPayload.replaceFirst("Customerlist", "customerlist");
//			JsonMaintMainCundfContainer container = this.maintMainCundfService.getList(jsonPayload);
//			if (container != null) {
//				list = container.getList();
///*		        for(JsonMaintMainCundfRecord record : list){
//	        	  logger.info("record:" + record.toString());
//	        	}	
//*/			}
//		}
//
//		return list;
//	}	
//	

	
	private void populateCodesHtmlDropDownsFromJsonYear(Map model, SystemaWebUser appUser) {
		JsonReader<JsonDtoContainer<SingleValueDto>> jsonReader = new JsonReader<JsonDtoContainer<SingleValueDto>>();
		jsonReader.set(new JsonDtoContainer<SingleValueDto>());
		String BASE_URL = MaintenanceMainUrlDataStore.MAINTENANCE_MAIN_BASE_HEADF_YEARS_GET_URL;
		StringBuilder urlRequestParams = new StringBuilder();
		urlRequestParams.append("user=" + appUser.getUser());
		logger.info("URL: " + BASE_URL);
		logger.info("PARAMS: " + urlRequestParams.toString());
		String jsonPayload = urlCgiProxyService.getJsonContent(BASE_URL, urlRequestParams.toString());
		logger.info("jsonPayload=" + jsonPayload);
		List<SingleValueDto> list = new ArrayList();
		JsonDtoContainer<SingleValueDto> container = (JsonDtoContainer<SingleValueDto>) jsonReader.get(jsonPayload);
		if (container != null) {
			list = (List) container.getDtoList();
		}
		
		model.put("yearList", list);
		
	}
	

	@Qualifier ("urlCgiProxyService")
	private UrlCgiProxyService urlCgiProxyService;
	@Autowired
	@Required
	public void setUrlCgiProxyService (UrlCgiProxyService value){ this.urlCgiProxyService = value; }
	public UrlCgiProxyService getUrlCgiProxyService(){ return this.urlCgiProxyService; }	
	
	
	
}
