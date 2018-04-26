package no.system.visma.integration;

import static org.junit.Assert.fail;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import no.systema.visma.integration.Customer;

public class TestJCustomer {

	Customer customer;
	
	@Before
	public void setUp() throws Exception {
		customer = new Customer();
	}

	@Test
	public void test() {
		String number = "10016";
		String location = "https://integration.visma.net/API/controller/api/v1/customer/10016";
		
		String diff = doTheThing(location);
		
		System.out.println("diff="+diff);
		
		Assert.assertEquals(number, diff);
	}

	
	private static String doTheThing(String location) {
		String callUrl = "https://integration.visma.net/API/controller/api/v1/customer";
		
		int lastSlash = StringUtils.indexOfDifference(location, callUrl);
		String diff = StringUtils.substring(location, lastSlash +1 );
		return diff;
	}
	
	
}
