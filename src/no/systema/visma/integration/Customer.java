package no.systema.visma.integration;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import no.systema.visma.HelperController;
import no.systema.visma.v1client.ApiClient;
import no.systema.visma.v1client.api.CustomerApi;
import no.systema.visma.v1client.model.CustomerDto;

/**
 * https://integration.visma.net/API-index/#!/Customer
 * 
 * @author fredrikmoller
 * @date 2018-04-12
 */
public class Customer {
	private static Logger logger = Logger.getLogger(HelperController.class);
	
	public CustomerDto getByCustomerCd(String customerCd) {
	
	apiClient.setBasePath("https://integration.visma.net/API");
	apiClient.addDefaultHeader("ipp-application-type", "Visma.net Financials");
	apiClient.addDefaultHeader("ipp-company-id", "1684147");
	apiClient.setAccessToken("81d21509-a23e-40a3-82d4-b101bb681d0f");
//	apiClient.setDebugging(true);
	
    return customerApi.customerGetBycustomerCd(customerCd);
	
//	logger.debug("response="+ReflectionToStringBuilder.toString(response, ToStringStyle.SIMPLE_STYLE));
    	
	}
	
	@Autowired
	@Qualifier("no.systema.visma.v1client.ApiClient")
	private ApiClient apiClient;
	
	@Autowired
	@Qualifier("no.systema.visma.v1client.api.CustomerApi")
	public CustomerApi customerApi = new CustomerApi(apiClient);	
	
}
