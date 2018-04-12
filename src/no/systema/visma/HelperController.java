package no.systema.visma;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import no.systema.jservices.common.dao.services.BridfDaoService;
import no.systema.visma.integration.TransactionManager;
import no.systema.visma.v1client.ApiClient;
import no.systema.visma.v1client.api.CustomerApi;
import no.systema.visma.v1client.model.CustomerDto;
/**
 * 
 * 
 * @author fredrikmoller
 * @date 2018-03
 *
 */

@Configuration
@ComponentScan(basePackages = {"no.systema", "org.springframework.web.client"})
@Controller
public class HelperController {
	private static Logger logger = Logger.getLogger(HelperController.class.getSimpleName());


	/**
	 * @Example: http://gw.systema.no:8080/visma-net-proxy/getCustomer.do?user=FREDRIK&id=10000
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getCustomer.do", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String getCustomer(HttpSession session, HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
//		List<PrettyPrintAttachments> dagsoppgors = null;

		logger.info("getCustomer.do...");
		try {
			String user = request.getParameter("user");
			
			logger.info("bridfDaoService="+bridfDaoService);
			
			String userName = bridfDaoService.getUserName(user);
			
			String customerCd = request.getParameter("id");

//			List<CustomerDto> customerList = null; 
//			customerList = client.getCustomer().getCustomerGetAll(null, null, null, null, null, null, null, null, null, null, null);
//			assertNotNull("customerList should not be null", customerList);
			
//			customerList.forEach((dto) ->System.out.println(ReflectionToStringBuilder.toString(dto)));
			
//			final ApiClient apiClient = new ApiClient(); //Using default BufferingClientHttpRequestFactory
			
			apiClient.setBasePath("https://integration.visma.net/API");
			apiClient.addDefaultHeader("ipp-application-type", "Visma.net Financials");
			apiClient.addDefaultHeader("ipp-company-id", "1684147");
			apiClient.setAccessToken("81d21509-a23e-40a3-82d4-b101bb681d0f");
			
			
//			final CustomerApi api = new CustomerApi(apiClient);
			
	        CustomerDto response = api.customerGetBycustomerCd(customerCd);
			
			
			sb.append("nån skön text \n \n");
			
			
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
	@Qualifier("no.systema.visma.v1client.ApiClient")
	private ApiClient apiClient;
	
	@Autowired
	@Qualifier("no.systema.visma.v1client.api.CustomerApi")
	public CustomerApi api = new CustomerApi(apiClient);
	
	
}
