package no.systema.visma.integration;

import static org.junit.Assert.*;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import no.systema.visma.v1client.ApiClient;
import no.systema.visma.v1client.api.CustomerApi;
import no.systema.visma.v1client.model.CustomerDto;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={Customer.class, ApiClient.class, RestTemplate.class, CustomerApi.class}, loader=AnnotationConfigContextLoader.class)
public class TestJCustomer {

	@Autowired 
	Customer customer;

	@Test
	public void testCustomerStress1() {
		String cd = "10000";
		CustomerDto dto =customer.getByCustomerCd(cd);
		
		assertNotNull(dto);
//		System.out.println("dto="+ReflectionToStringBuilder.toString(dto));
		assertEquals(dto.getName(),"QVILLER, THEODOR AS");

		
	}
	
	@Test
	public void testCustomerStress2() {
		String cd = "10000";
		CustomerDto dto =customer.getByCustomerCd(cd);
		
		assertNotNull(dto);
//		System.out.println("dto="+ReflectionToStringBuilder.toString(dto));
		assertEquals(dto.getName(),"QVILLER, THEODOR AS");

		
	}	
	
	@Test
	public void testCustomerStress3() {
		String cd = "10000";
		CustomerDto dto =customer.getByCustomerCd(cd);
		
		assertNotNull(dto);
//		System.out.println("dto="+ReflectionToStringBuilder.toString(dto));
		assertEquals(dto.getName(),"QVILLER, THEODOR AS");

		
	}	
	
	@Test
	public void testCustomerStress4() {
		String cd = "10000";
		CustomerDto dto =customer.getByCustomerCd(cd);
		
		assertNotNull(dto);
//		System.out.println("dto="+ReflectionToStringBuilder.toString(dto));
		assertEquals(dto.getName(),"QVILLER, THEODOR AS");

		
	}	
	
	@Test
	public void testCustomerStress5() {
		String cd = "10000";
		CustomerDto dto =customer.getByCustomerCd(cd);
		
		assertNotNull(dto);
//		System.out.println("dto="+ReflectionToStringBuilder.toString(dto));
		assertEquals(dto.getName(),"QVILLER, THEODOR AS");

		
	}	
	
}
