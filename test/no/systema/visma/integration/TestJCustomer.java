package no.systema.visma.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.services.FirmvisDaoServiceImpl;
import no.systema.visma.Configuration;
import no.systema.visma.v1client.ApiClient;
import no.systema.visma.v1client.api.CustomerApi;
import no.systema.visma.v1client.model.CustomerDto;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJCustomer {

	@Autowired 
	Customer customer;
	
	@Test
	public void testCustomerStress1() {
		String cd = "10000";
		CustomerDto dto =customer.getByCustomerCd(cd, 111);
		assertNotNull(dto);
		System.out.println("dto="+ReflectionToStringBuilder.toString(dto));
		
		System.out.println("dto.getAccountReference()="+dto.getAccountReference());
		assertEquals(dto.getName(),"QVILLER, THEODOR AS");
		
	}
	

	@Test
	public void testCustomerCreateSmall() {
		String name = "Kalles chokladfabrik (small)";
		ViskundeDao dao =getSmallDao(10, name);
		
		Object postObject = customer.customerPost(dao);
		System.out.println("dto="+ReflectionToStringBuilder.toString(postObject));
		assertNotNull(postObject);

	}
	
	@Test
	public void testCustomerCreateMedium() {
		String name = "Kalles chokladfabrik (medium)";
		ViskundeDao dao =getMediumDao(10, "123999" ,name);
		int number = customer.customerPost(dao);
		assertNotNull(number);
		
	}

	@Test
	public void testCustomerUpdate() {
		String name = "Kalles chokladfabrik (medium)";
		ViskundeDao dao =getMediumDao(1,"123456" ,name);
		dao.setKnavn("Kalles chokladfabrik (UPDATE 11:41)");
		
		customer.customerPutBycustomerCd("1", dao);
		
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
	

	private ViskundeDao getSmallDao(int kundnr, String name) {
		ViskundeDao dao = new ViskundeDao();
		dao.setKundnr(kundnr);
		dao.setKnavn(name);
		return dao;
	}		
	
	private ViskundeDao getMediumDao(int kundnr, String syrg, String name) {
		ViskundeDao dao = new ViskundeDao();
		dao.setKundnr(kundnr);
		dao.setKnavn(name);
		dao.setAdr1("adr1");
		dao.setAdr2("adr2");
		dao.setAdr3("adr3");
		dao.setSyland("NO");
		dao.setPostnr(6001);
		dao.setSyrg(syrg);
		dao.setAktkod("A"); //I

		
		return dao;
	}	
	
	
	@Test
	public void testLocationParsing() {
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
