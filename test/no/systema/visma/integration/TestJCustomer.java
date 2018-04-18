package no.systema.visma.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.visma.v1client.ApiClient;
import no.systema.visma.v1client.api.CustomerApi;
import no.systema.visma.v1client.model.CustomerDto;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={Customer.class, ApiClient.class, RestTemplate.class, CustomerApi.class}, loader=AnnotationConfigContextLoader.class)
public class TestJCustomer {

	@Autowired 
	Customer customer;
	
	int cd = 9999;
	String syrg = "syrg";

	@Test
	public void testCustomerStress1() {
		String cd = "10007";
		CustomerDto dto =customer.getByCustomerCd(cd);
		
		assertNotNull(dto);
		System.out.println("dto="+ReflectionToStringBuilder.toString(dto));
		
		System.out.println("dto.getAccountReference()="+dto.getAccountReference());
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
	
	@Test
	public void testCustomerCreateSmall() {
		String name = "Kalles chokladfabrik (small)";
		ViskundeDao dao =getSmallDao(cd, name);
		
		Object postObject = customer.customerPost(dao);
		System.out.println("dto="+ReflectionToStringBuilder.toString(postObject));
		assertNotNull(postObject);

		
	}
	
	@Test
	public void testCustomerCreateMedium() {
		String name = "Kalles chokladfabrik (medium)";
		ViskundeDao dao =getMediumDao(cd,syrg ,name);
		
		Object postObject = customer.customerPost(dao);
		System.out.println("dto="+ReflectionToStringBuilder.toString(postObject));
		assertNotNull(postObject);

		
	}
	
	@Test
	public void testCustomerGetByOrgnnr() {
		String greaterThanValue = null;
		Integer numberToRead = null;
		Integer skipRecords = null;
		String name = null;
		String status = null;
		String corporateId = "syrg";
		String vatRegistrationId = null;
		String email = null;
		String phone = null;
		String lastModifiedDateTime = null;
		String lastModifiedDateTimeCondition = null;
		String createdDateTime = null;
		String createdDateTimeCondition = null;
		String attributes = null;
		Integer pageNumber = null;
		Integer pageSize = null;

		List<CustomerDto> responseList = customer.customerGetAll(greaterThanValue, numberToRead, skipRecords, name, status,
				corporateId, vatRegistrationId, email, phone, lastModifiedDateTime, lastModifiedDateTimeCondition,
				createdDateTime, createdDateTimeCondition, attributes, pageNumber, pageSize);
		
		assertNotNull(responseList);
		System.out.println("responseList.size="+responseList.size());
		
		responseList.forEach((dto) -> System.out.println("dto="+ReflectionToStringBuilder.toString(dto)));
		
		
	}	
	

	private ViskundeDao getSmallDao(int cd, String name) {
		ViskundeDao dao = new ViskundeDao();
		dao.setKundnr(cd);
		dao.setKnavn(name);
		return dao;
	}		
	
	private ViskundeDao getMediumDao(int cd, String syrg, String name) {
		ViskundeDao dao = new ViskundeDao();
		dao.setKundnr(cd);
		dao.setKnavn(name);
		dao.setAdr1("adr1");
		dao.setAdr2("adr2");
		dao.setAdr3("adr3");
		dao.setSyland("NO");
		dao.setPostnr(6001);
		dao.setSyrg(syrg);

		
		return dao;
	}	
	
	
	
	
	
}
