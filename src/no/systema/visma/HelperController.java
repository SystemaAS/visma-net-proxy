package no.systema.visma;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import no.systema.jservices.common.dao.services.BridfDaoService;
import no.systema.visma.integration.Customer;
import no.systema.visma.v1client.model.CustomerDto;
/**
 * 
 * 
 * @author fredrikmoller
 * @date 2018-03
 *
 */
@Controller
public class HelperController {
	private static Logger logger = Logger.getLogger(HelperController.class);

	@RequestMapping(value="docs.do", method={RequestMethod.GET, RequestMethod.POST} )
	public ModelAndView viewDocs( HttpSession session, HttpServletRequest request, HttpServletResponse response){
		logger.info("docs.do...");
//		ModelAndView successViewCustomer = new ModelAndView("kalle");
		ModelAndView successViewCustomer = new ModelAndView("espedsgadmin");
		
		logger.info("successViewCustomer.getViewName()="+successViewCustomer.getViewName());
		
		return successViewCustomer;
		
	}	
	
	/**
	 * Example: http://gw.systema.no:8080/visma-net-proxy/getCustomer.do?user=FREDRIK&customerCd=10000
	 */
	@RequestMapping(value="getCustomer.do", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String getCustomer(HttpSession session, HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();

		logger.info("getCustomer.do...");
		
		try {
			String user = request.getParameter("user");
			String userName = bridfDaoService.getUserName(user);
			Assert.notNull(userName, "userName not found in Bridf."); 
			
			String customerCd = request.getParameter("customerCd");
			Assert.notNull(customerCd, "customerCd nust be delivered."); 

	        CustomerDto response = customer.getByCustomerCd(customerCd);
	        logger.info("excute 1");
			logger.debug("response 1="+ReflectionToStringBuilder.toString(response));
	        
	        
//	        response = customer.getByCustomerCd(customerCd);
//	        
//	        logger.info("excute 2");	        
//			logger.debug("response 2="+ReflectionToStringBuilder.toString(response, ToStringStyle.SIMPLE_STYLE));
	        
			sb.append("::Response on customerCd="+customerCd+ " :: \n \n ");
			sb.append(ReflectionToStringBuilder.toString(response));
			
			
		} catch (Exception e) {
			// write std.output error output
			logger.error("ERROR:", e);
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			return "ERROR [JsonResponseOutputterController]" + writer.toString();
		}

		session.invalidate();
		return sb.toString();

	}	
	
	@RequestMapping(value="syncronizeCustomers.do", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String download(HttpSession session, HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
//		List<PrettyPrintAttachments> dagsoppgors = null;

		logger.info("syncronizeCustomers.do...");
		try {
			String user = request.getParameter("user");
//		(user, "user must be delivered."); 
			
			String userName = bridfDaoService.getUserName(user);
//			Assert.notNull(userName, "userName not found in Bridf."); 
			
			transactionManager.syncronizeCustomers();
			sb.append("nån skön text \n \n");
			
//			sb.append(FlipTableConverters.fromIterable(dagsoppgors, PrettyPrintAttachments.class));
			
		} catch (Exception e) {
			// write std.output error output
			e.printStackTrace();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			return "ERROR [JsonResponseOutputterController]" + writer.toString();
		}

		session.invalidate();
		return sb.toString();

	}

	@Autowired
	@Qualifier("bridfDaoService")
	private BridfDaoService bridfDaoService;
	
	@Autowired
	@Qualifier("tsManager")
	private TransactionManager transactionManager;

	@Autowired
	private Customer customer;	
	
	
}
