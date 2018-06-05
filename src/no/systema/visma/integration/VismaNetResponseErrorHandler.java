package no.systema.visma.integration;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.UnknownHttpStatusCodeException;


/**
 * This errorHandler replaces the DefaultResponseErrorHandler
 * Used to specify Visma.net response body into Status Text.
 * 
 * RestTemplate is set when creating the ApiClient.
 * 
 * @author fredrikmoller
 *
 */
public class VismaNetResponseErrorHandler implements ResponseErrorHandler {
	private static Logger logger = Logger.getLogger(VismaNetResponseErrorHandler.class);
	
	
	/**
	 * Delegates to {@link #hasError(HttpStatus)} with the response status code.
	 */
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		HttpStatus statusCode = HttpStatus.valueOf(response.getRawStatusCode());
		return (statusCode != null && hasError(statusCode));
	}	
	
	
	/**
	 * Template method called from {@link #hasError(ClientHttpResponse)}.
	 * <p>The default implementation checks if the given status code is
	 * {@link HttpStatus.Series#CLIENT_ERROR CLIENT_ERROR} or
	 * {@link HttpStatus.Series#SERVER_ERROR SERVER_ERROR}.
	 * Can be overridden in subclasses.
	 * @param statusCode the HTTP status code
	 * @return {@code true} if the response has an error; {@code false} otherwise
	 */
	protected boolean hasError(HttpStatus statusCode) {
		return (statusCode.series() == HttpStatus.Series.CLIENT_ERROR ||
				statusCode.series() == HttpStatus.Series.SERVER_ERROR);
	}	
	

	/**
	 * Delegates to {@link #handleError(ClientHttpResponse, HttpStatus)} with the response status code.
	 */
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		HttpStatus statusCode = HttpStatus.valueOf(response.getRawStatusCode());
		if (statusCode == null) {
			throw new UnknownHttpStatusCodeException(response.getRawStatusCode(), response.getStatusText(),
					response.getHeaders(), getResponseBody(response), getCharset(response));
		}
		handleError(response, statusCode);
	}	
	

	/**
	 * Handle the error in the given response with the given resolved status code.
	 * <p>This default implementation throws a {@link HttpClientErrorException}  if the response status code is {@link org.springframework.http.HttpStatus.Series#CLIENT_ERROR}, 
	 * a {@link HttpServerErrorException} if it is {@link org.springframework.http.HttpStatus.Series#SERVER_ERROR},
	 * and a {@link RestClientException} in other cases.
	 * 
	 * The {@link HttpClientErrorException} is narrowed into {@link new HttpClientErrorException(statusCode, statusText)}, where statusText is the response body.
	 * 
	 * @since 5.0
	 */
	protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
		String responseBody;
		switch (statusCode.series()) {
			case CLIENT_ERROR:
				responseBody =  new String(getResponseBody(response), getCharset(response));
				logger.error("CLIENT_ERROR:, response body="+responseBody);
//				throw new HttpClientErrorException(statusCode, response.getStatusText(),
//						response.getHeaders(), getResponseBody(response), getCharset(response));
				throw new HttpClientErrorException(statusCode, LogHelper.trimToError(responseBody));
			case SERVER_ERROR:
				responseBody =  new String(getResponseBody(response), getCharset(response));
				logger.error("SERVER_ERROR:, response body="+responseBody);
//				throw new HttpServerErrorException(statusCode, response.getStatusText(),
//						response.getHeaders(), getResponseBody(response), getCharset(response));
				throw new HttpClientErrorException(statusCode, LogHelper.trimToError(responseBody));
			default:
				responseBody =  new String(getResponseBody(response), getCharset(response));
				logger.error("default:, response body="+responseBody);				
				throw new UnknownHttpStatusCodeException(statusCode.value(), response.getStatusText(),
						response.getHeaders(), getResponseBody(response), getCharset(response));
		}
	}	
	
	/**
	 * Read the body of the given response (for inclusion in a status exception).
	 * @param response the response to inspect
	 * @return the response body as a byte array,
	 * or an empty byte array if the body could not be read
	 * @since 4.3.8
	 */
	protected byte[] getResponseBody(ClientHttpResponse response) {
		try {
			return FileCopyUtils.copyToByteArray(response.getBody());
		}
		catch (IOException ex) {
			// ignore
		}
		return new byte[0];
	}	

	/**
	 * Determine the charset of the response (for inclusion in a status exception).
	 * @param response the response to inspect
	 * @return the associated charset, or {@code null} if none
	 * @since 4.3.8
	 */
	protected Charset getCharset(ClientHttpResponse response) {
		HttpHeaders headers = response.getHeaders();
		MediaType contentType = headers.getContentType();
		return (contentType != null ? contentType.getCharset() : null);
	}	
	
	
}
