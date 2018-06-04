package no.systema.visma.integration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import no.systema.visma.v1client.ApiClient;

public abstract class Configuration {

	@Bean
	public ApiClient apiClient(){
		return new ApiClient(restTemplate());
	}
	
	/**
	 * Explicit settings of Jackson ObjectMapper to support Java 8 LocalDateTime.
	 * 
	 * @return ObjectMapper
	 */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
	    return Jackson2ObjectMapperBuilder.json()
	            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
	            .createXmlMapper(false)
	            .build();
    }	
	
	@Bean
	public RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate(Arrays.asList(new MappingJackson2HttpMessageConverter(objectMapper())));
		restTemplate.setInterceptors(Arrays.asList(new VismaClientHttpRequestInterceptor()));
		restTemplate.setErrorHandler(new VismaNetResponseErrorHandler());
		
		return restTemplate;  
	}
}
