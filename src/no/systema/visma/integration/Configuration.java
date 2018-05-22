package no.systema.visma.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import no.systema.visma.v1client.ApiClient;

public abstract class Configuration {

	@Bean
	public ApiClient apiClient(){
		return new ApiClient(restTemplate());
	}
	
	@Bean
	public RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new VismaNetResponseErrorHandler());
		return restTemplate;  
	}
}
