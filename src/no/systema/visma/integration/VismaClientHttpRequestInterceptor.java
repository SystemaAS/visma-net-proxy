/**
 * 
 */
package no.systema.visma.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;

/**
 * @author fredrikmoller
 *
 */
public class VismaClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
	private static Logger logger = Logger.getLogger(VismaClientHttpRequestInterceptor.class);
	
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
	
	
//	
//	
//	/* (non-Javadoc)
//	 * @see org.springframework.http.client.ClientHttpRequestInterceptor#intercept(org.springframework.http.HttpRequest, byte[], org.springframework.http.client.ClientHttpRequestExecution)
//	 */
//	@Override
//	public ClientHttpResponse intercept(HttpRequest arg0, byte[] arg1, ClientHttpRequestExecution arg2) throws IOException {
//		
//		logger.info("HELLO.....");
//	
//		
//		return null;
//	}

}
