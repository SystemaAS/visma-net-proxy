package no.systema.visma.integration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import no.systema.visma.authorization.HttpBasicAuthApiClient;
import no.systema.visma.v1client.ApiClient;

/**
 * This class is used in every integration class. <br>
 * 
 * Responsible for: <br>
 * <li>Setting up http clients; {@linkplain ApiClient} and {@linkplain HttpBasicAuthApiClient}
 * <li>Configuration for {@linkplain ObjectMapper} to support Java 8 LocalDateTime
 * <li>Initializating {@linkplain RestTemplate} with {@linkplain HttpMessageConverter} and {@linkplain ClientHttpRequestInterceptor}
 * 
 * @author fredrikmoller
 *
 */
public abstract class Configuration {

	/**
	 * Used for entering the API
	 * 
	 * @return
	 */
	@Bean
	public ApiClient apiClient(){
		return new ApiClient(restTemplate());
	}

	/**
	 * Used for retrieving token.
	 * 
	 * @return
	 */
	@Bean
	public HttpBasicAuthApiClient httpBasicAuthApiClient(){
		return new HttpBasicAuthApiClient(restTemplateDefault());
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
	
	/**
	 * Initialize  {@linkplain RestTemplate} with {@linkplain MappingJackson2HttpMessageConverter} and <br>
	 * a Interceptor, {@linkplain VismaClientHttpRequestInterceptor}
	 * 
	 * @return RestTemplate
	 */
    @Bean
	public RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate(Arrays.asList(new MappingJackson2HttpMessageConverter(objectMapper())));
		restTemplate.setInterceptors(Arrays.asList(new VismaClientHttpRequestInterceptor()));
		restTemplate.setErrorHandler(new VismaResponseErrorHandler());
		
		return restTemplate;  
	}

	/**
	 * Initialize  {@linkplain RestTemplate} with default {@linkplain HttpMessageConverter} and <br>
	 * a Interceptor, {@linkplain VismaClientHttpRequestInterceptor}
	 * 
	 * @return RestTemplate
	 */
    @Bean
	public RestTemplate restTemplateDefault(){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(Arrays.asList(new VismaClientHttpRequestInterceptor()));
		restTemplate.setErrorHandler(new VismaResponseErrorHandler());
		
		return restTemplate;  
	}	
	
}
