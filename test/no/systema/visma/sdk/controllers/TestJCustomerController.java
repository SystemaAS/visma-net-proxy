package no.systema.visma.sdk.controllers;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Before;
import org.junit.Test;

import no.systema.visma.sdk.VismaNetAPIClient;
import no.systema.visma.sdk.models.CustomerDto;

public class TestJCustomerController {

	VismaNetAPIClient client = null;
	
	String bearer = "81d21509-a23e-40a3-82d4-b101bb681d0f";
	
	
	//TODO   hur/var??
//	ipp-application-type: Visma.net Financials
//	ipp-company-id: 1684147
	
/*	
	GET /API/controller/api/v1/customer HTTP/1.1
Host: integration.visma.net
Accept: application/json
ipp-application-type: Visma.net Financials
ipp-company-id: 1684147
Authorization: Bearer 81d21509-a23e-40a3-82d4-b101bb681d0f
Cache-Control: no-cache
Postman-Token: e499da89-2ab1-4c5b-aea6-6f5fbcac1f9a

 [DEBUG] org.apache.http.headers:124 - http-outgoing-0 >> GET /API/controller/api/v1/customer?corporateId=1684147 HTTP/1.1
2018-03-19 14:40:59.769 [DEBUG] org.apache.http.headers:127 - http-outgoing-0 >> Authorization: Bearer 81d21509-a23e-40a3-82d4-b101bb681d0f
2018-03-19 14:40:59.770 [DEBUG] org.apache.http.headers:127 - http-outgoing-0 >> accept-encoding: gzip
2018-03-19 14:40:59.770 [DEBUG] org.apache.http.headers:127 - http-outgoing-0 >> user-agent: APIMATIC 2.0
2018-03-19 14:40:59.771 [DEBUG] org.apache.http.headers:127 - http-outgoing-0 >> accept: application/json
2018-03-19 14:40:59.771 [DEBUG] org.apache.http.headers:127 - http-outgoing-0 >> Host: integration.visma.net
2018-03-19 14:40:59.771 [DEBUG] org.apache.http.headers:127 - http-outgoing-0 >> Connection: Keep-Alive	
	
	
	
	
	
*/	
	
	@Before
	public void setUp() throws Exception {
		client = new VismaNetAPIClient(bearer);
	}

	
	
	@Test
	public void testGetAll() {
		try {
			List<CustomerDto> customerList = null; 
			customerList = client.getCustomer().getCustomerGetAll(null, null, null, null, null, null, null, null, null, null, null);
			assertNotNull("customerList should not be null", customerList);
			
			customerList.forEach((dto) ->System.out.println(ReflectionToStringBuilder.toString(dto)));
			
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
