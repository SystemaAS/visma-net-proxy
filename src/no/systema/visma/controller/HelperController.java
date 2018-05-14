package no.systema.visma.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jakewharton.fliptables.FlipTableConverters;

import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.services.BridfDaoService;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.jservices.common.util.Log4jUtils;
import no.systema.visma.PrettyPrintViskundeError;
import no.systema.visma.transaction.TransactionManager;
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
	
	/**
	 * Example: http://gw.systema.no:8080/visma-net-proxy/syncronizeCustomers.do?user=SYSTEMA
	 */
	@RequestMapping(value="syncronizeCustomers.do", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String syncCustomers(HttpSession session, HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();

		logger.info("syncronizeCustomers.do...");
		try {
			String user = request.getParameter("user");
			String userName = bridfDaoService.getUserName(user);
			if (userName == null) {
				logger.error("user is null, must be delivered");
				throw new RuntimeException("ERROR: parameter, user, must be delivered!");
			}
			logger.info("user="+user);
			
			List<PrettyPrintViskundeError> errorList = transactionManager.syncronizeCustomers();
			
			if (errorList.isEmpty()) {
				sb.append("syncronizeCustomers executed without errors. \n \n");
			} else {
				sb.append("syncronizeCustomers executed WITH errors.  \n \n");
			}
			
			sb.append(FlipTableConverters.fromIterable(errorList, PrettyPrintViskundeError.class));
			
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
	
	/**
	 * This method is used for debugging and testing.
	 * 
	 * Example: http://gw.systema.no:8080/visma-net-proxy/syncronizeCustomer.do?user=SYSTEMA&kundnr=1
	 */
	@RequestMapping(value="syncronizeCustomer.do", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String syncCustomer(HttpSession session, HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
//		List<PrettyPrintAttachments> dagsoppgors = null;

		logger.info("syncronizeCustomers.do...");
		try {
			String user = request.getParameter("user");
			String userName = bridfDaoService.getUserName(user);
			if (userName == null) {
				logger.error("user is null, must be delivered");
				throw new RuntimeException("ERROR: parameter, user, must be delivered!");
			}
			logger.info("user="+user);
			
			String kundnr = request.getParameter("kundnr");
			if (kundnr == null) {
				logger.error("kundnr is null, must be delivered");
				throw new RuntimeException("ERROR: parameter, kundnr, must be delivered!");
			}
			logger.info("kundnr"+kundnr);			
			
			transactionManager.syncronizeCustomer(Integer.valueOf(kundnr));
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
	
	/**
	 * 
	 * @Example: http://gw.systema.no:8080/visma-net-proxy/showHistory.do?user=FREDRIK&filename=log4j_visma-net-proxy-transaction.log
	 * 
	 * @param session
	 * @param request, user 
	 * @return status
	 */	
	@RequestMapping(value="showHistory.do", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String showHistory(HttpSession session, HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();

		logger.info("showHistory.do...");
		try {
			String user = request.getParameter("user");
			Assert.notNull(user, "user must be delivered."); 

			String userName = bridfDaoService.getUserName(user);
			Assert.notNull(userName, "userName not found in Bridf."); 

			String fileName = request.getParameter("filename");
			Assert.notNull(fileName, "fileName must be delivered."); 			
			
			
			sb.append(Log4jUtils.getLog4jData(fileName));
			
			
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
	private ViskundeDaoService viskundeDaoService;	
	
	
}
