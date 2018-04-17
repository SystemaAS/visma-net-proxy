package no.systema.visma.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import no.systema.visma.Configuration;
import no.systema.visma.v1client.api.CustomerApi;
import no.systema.visma.v1client.model.CustomerDto;

/**
 * A Wrapper on CustomerApi
 * 
 * Also see https://integration.visma.net/API-index/#!/Customer
 * 
 * @author fredrikmoller
 * @date 2018-04
 */
@Service
public class Customer extends Configuration{

	@Autowired
	@Qualifier("no.systema.visma.v1client.api.CustomerApi")
	public CustomerApi customerApi = new CustomerApi(apiClient);
	
	/**
	 * Get a specific customer
	 * 
	 * @param customerCd
	 * @return
	 */
	public CustomerDto getByCustomerCd(String customerCd) {
		return customerApi.customerGetBycustomerCd(customerCd);
	}
	

	
}
