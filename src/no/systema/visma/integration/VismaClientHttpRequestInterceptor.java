/**
 * 
 */
package no.systema.visma.integration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import no.systema.visma.v1client.ApiClient;

/**
 * Intercepts client-side HTTP requests. This class is {@linkplain
 * org.springframework.web.client.RestTemplate#setInterceptors(java.util.List) registered} with
 * {@link org.springframework.web.client.RestTemplate RestTemplate} on {@link ApiClient} in {@link Configuration}, as to modify the outgoing {@link ClientHttpRequest}
 * and/or the incoming {@link ClientHttpResponse}.
 *
 * <p>The main entry point for interceptors is {@link #intercept(HttpRequest, byte[], ClientHttpRequestExecution)}.
 * 
 * @author fredrikmoller
 *
 */
public class VismaClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
	private static Logger logger = LogManager.getLogger(VismaClientHttpRequestInterceptor.class);
	
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws UnsupportedEncodingException {
    	logger.info("URI: " + request.getURI());
    	logger.info("HTTP Method: " + request.getMethod());
    	logger.info("HTTP Headers: " + headersToString(request.getHeaders()));
    	logger.info("Request Body: " + new String(body, StandardCharsets.UTF_8));
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
    	logger.info("HTTP Status Code: " + response.getRawStatusCode());
    	logger.info("Status Text: " + response.getStatusText());
    	logger.info("HTTP Headers: " + headersToString(response.getHeaders()));
    	//    	logger.info("Response Body: " + bodyToString(response.getBody())); InputStream closed here.
    }

    private String headersToString(HttpHeaders headers) {
        StringBuilder builder = new StringBuilder();
        for(Entry<String, List<String>> entry : headers.entrySet()) {
            builder.append(entry.getKey()).append("=[");
            for(String value : entry.getValue()) {
                builder.append(value).append(",");
            }
            builder.setLength(builder.length() - 1); // Get rid of trailing comma
            builder.append("],");
        }
        builder.setLength(builder.length() - 1); // Get rid of trailing comma
        return builder.toString();
    }
    
//    private String bodyToString(InputStream body) throws IOException {
//        StringBuilder builder = new StringBuilder();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(body, StandardCharsets.UTF_8));
//        String line = bufferedReader.readLine();
//        while (line != null) {
//            builder.append(line).append(System.lineSeparator());
//            line = bufferedReader.readLine();
//        }
//        bufferedReader.close();
//        return builder.toString();
//    }
	

}
