/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import no.system.visma.sdk.exceptions.APIException;
import no.systema.visma.sdk.APIHelper;
import no.systema.visma.sdk.Configuration;
import no.systema.visma.sdk.controllers.syncwrapper.APICallBackCatcher;
import no.systema.visma.sdk.http.client.APICallBack;
import no.systema.visma.sdk.http.client.HttpContext;
import no.systema.visma.sdk.http.request.HttpRequest;
import no.systema.visma.sdk.http.response.HttpResponse;
import no.systema.visma.sdk.http.response.HttpStringResponse;
import no.systema.visma.sdk.models.CreditNoteDto;
import no.systema.visma.sdk.models.CreditNoteUpdateDto;

public class CreditNoteController extends BaseController {    
    //private static variables for the singleton pattern
    private static Object syncObject = new Object();
    private static CreditNoteController instance = null;

    /**
     * Singleton pattern implementation 
     * @return The singleton instance of the CreditNoteController class 
     */
    public static CreditNoteController getInstance() {
        synchronized (syncObject) {
            if (null == instance) {
                instance = new CreditNoteController();
            }
        }
        return instance;
    }

    /**
     * TODO: type endpoint description here
     * @param    greaterThanValue    Optional parameter: Example: 
     * @param    lastModifiedDateTime    Optional parameter: Example: 
     * @param    lastModifiedDateTimeCondition    Optional parameter: Example: 
     * @param    numberToRead    Optional parameter: Example: 
     * @param    orderBy    Optional parameter: Example: 
     * @param    released    Optional parameter: Example: 
     * @param    skipRecords    Optional parameter: Example: 
     * @return    Returns the List<CreditNoteDto> response from the API call 
     */
    public List<CreditNoteDto> getCreditNoteGetAllCreditNotesDto(
                final String greaterThanValue,
                final String lastModifiedDateTime,
                final String lastModifiedDateTimeCondition,
                final Integer numberToRead,
                final String orderBy,
                final Integer released,
                final Integer skipRecords
    ) throws Throwable {
        APICallBackCatcher<List<CreditNoteDto>> callback = new APICallBackCatcher<List<CreditNoteDto>>();
        getCreditNoteGetAllCreditNotesDtoAsync(greaterThanValue, lastModifiedDateTime, lastModifiedDateTimeCondition, numberToRead, orderBy, released, skipRecords, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        return callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    greaterThanValue    Optional parameter: Example: 
     * @param    lastModifiedDateTime    Optional parameter: Example: 
     * @param    lastModifiedDateTimeCondition    Optional parameter: Example: 
     * @param    numberToRead    Optional parameter: Example: 
     * @param    orderBy    Optional parameter: Example: 
     * @param    released    Optional parameter: Example: 
     * @param    skipRecords    Optional parameter: Example: 
     * @return    Returns the void response from the API call 
     */
    public void getCreditNoteGetAllCreditNotesDtoAsync(
                final String greaterThanValue,
                final String lastModifiedDateTime,
                final String lastModifiedDateTimeCondition,
                final Integer numberToRead,
                final String orderBy,
                final Integer released,
                final Integer skipRecords,
                final APICallBack<List<CreditNoteDto>> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/creditNote");

        //process query parameters
        APIHelper.appendUrlWithQueryParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5625199625800375209L;
            {
                    put( "greaterThanValue", greaterThanValue );
                    put( "lastModifiedDateTime", lastModifiedDateTime );
                    put( "lastModifiedDateTimeCondition", lastModifiedDateTimeCondition );
                    put( "numberToRead", numberToRead );
                    put( "orderBy", orderBy );
                    put( "released", released );
                    put( "skipRecords", skipRecords );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4943150352341245280L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "accept", "application/json" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().get(_queryUrl, _headers, null);

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, new APICallBack<HttpResponse>() {
                    public void onSuccess(HttpContext _context, HttpResponse _response) {
                        try {

                            //invoke the callback after response if its not null
                            if (getHttpCallBack() != null)	
                            {
                                getHttpCallBack().OnAfterResponse(_context);
                            }

                            //handle errors defined at the API level
                            validateResponse(_response, _context);

                            //extract result from the http response
                            String _responseBody = ((HttpStringResponse)_response).getBody();
                            List<CreditNoteDto> _result = APIHelper.deserialize(_responseBody,
                                                        new TypeReference<List<CreditNoteDto>>(){});

                            //let the caller know of the success
                            callBack.onSuccess(_context, _result);
                        } catch (APIException error) {
                            //let the caller know of the error
                            callBack.onFailure(_context, error);
                        } catch (IOException ioException) {
                            //let the caller know of the caught IO Exception
                            callBack.onFailure(_context, ioException);
                        } catch (Exception exception) {
                            //let the caller know of the caught Exception
                            callBack.onFailure(_context, exception);
                        }
                    }
                    public void onFailure(HttpContext _context, Throwable _error) {
                        //invoke the callback after response if its not null
                        if (getHttpCallBack() != null)	
                            {
                            getHttpCallBack().OnAfterResponse(_context);
                        }

                        //let the caller know of the failure
                        callBack.onFailure(_context, _error);
                    }
                });
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteUpdateDto    Required parameter: Defines the data for the Credit Note to create
     * @return    Returns the void response from the API call 
     */
    public void createCreditNoteCreateCreditNote(
                final CreditNoteUpdateDto creditNoteUpdateDto
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        createCreditNoteCreateCreditNoteAsync(creditNoteUpdateDto, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteUpdateDto    Required parameter: Defines the data for the Credit Note to create
     * @return    Returns the void response from the API call 
     */
    public void createCreditNoteCreateCreditNoteAsync(
                final CreditNoteUpdateDto creditNoteUpdateDto,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/creditNote");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5156641709088390632L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(creditNoteUpdateDto));

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, new APICallBack<HttpResponse>() {
                    public void onSuccess(HttpContext _context, HttpResponse _response) {
                        try {

                            //invoke the callback after response if its not null
                            if (getHttpCallBack() != null)	
                            {
                                getHttpCallBack().OnAfterResponse(_context);
                            }

                            //handle errors defined at the API level
                            validateResponse(_response, _context);

                            //let the caller know of the success
                            callBack.onSuccess(_context, _context);
                        } catch (APIException error) {
                            //let the caller know of the error
                            callBack.onFailure(_context, error);
                        } catch (Exception exception) {
                            //let the caller know of the caught Exception
                            callBack.onFailure(_context, exception);
                        }
                    }
                    public void onFailure(HttpContext _context, Throwable _error) {
                        //invoke the callback after response if its not null
                        if (getHttpCallBack() != null)	
                            {
                            getHttpCallBack().OnAfterResponse(_context);
                        }

                        //let the caller know of the failure
                        callBack.onFailure(_context, _error);
                    }
                });
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteNumber    Required parameter: Identifies the Credit Note
     * @return    Returns the CreditNoteDto response from the API call 
     */
    public CreditNoteDto getCreditNoteGet(
                final String creditNoteNumber
    ) throws Throwable {
        APICallBackCatcher<CreditNoteDto> callback = new APICallBackCatcher<CreditNoteDto>();
        getCreditNoteGetAsync(creditNoteNumber, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        return callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteNumber    Required parameter: Identifies the Credit Note
     * @return    Returns the void response from the API call 
     */
    public void getCreditNoteGetAsync(
                final String creditNoteNumber,
                final APICallBack<CreditNoteDto> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/creditNote/{creditNoteNumber}");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 4864823406875213112L;
            {
                    put( "creditNoteNumber", creditNoteNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5715487115468120165L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "accept", "application/json" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().get(_queryUrl, _headers, null);

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, new APICallBack<HttpResponse>() {
                    public void onSuccess(HttpContext _context, HttpResponse _response) {
                        try {

                            //invoke the callback after response if its not null
                            if (getHttpCallBack() != null)	
                            {
                                getHttpCallBack().OnAfterResponse(_context);
                            }

                            //handle errors defined at the API level
                            validateResponse(_response, _context);

                            //extract result from the http response
                            String _responseBody = ((HttpStringResponse)_response).getBody();
                            CreditNoteDto _result = APIHelper.deserialize(_responseBody,
                                                        new TypeReference<CreditNoteDto>(){});

                            //let the caller know of the success
                            callBack.onSuccess(_context, _result);
                        } catch (APIException error) {
                            //let the caller know of the error
                            callBack.onFailure(_context, error);
                        } catch (IOException ioException) {
                            //let the caller know of the caught IO Exception
                            callBack.onFailure(_context, ioException);
                        } catch (Exception exception) {
                            //let the caller know of the caught Exception
                            callBack.onFailure(_context, exception);
                        }
                    }
                    public void onFailure(HttpContext _context, Throwable _error) {
                        //invoke the callback after response if its not null
                        if (getHttpCallBack() != null)	
                            {
                            getHttpCallBack().OnAfterResponse(_context);
                        }

                        //let the caller know of the failure
                        callBack.onFailure(_context, _error);
                    }
                });
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteNumber    Required parameter: Identifies the Credit Note to update
     * @param    creditNoteUpdateDto    Required parameter: Defines the data for the Credit Note to update
     * @return    Returns the void response from the API call 
     */
    public void updateCreditNotePut(
                final String creditNoteNumber,
                final CreditNoteUpdateDto creditNoteUpdateDto
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        updateCreditNotePutAsync(creditNoteNumber, creditNoteUpdateDto, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteNumber    Required parameter: Identifies the Credit Note to update
     * @param    creditNoteUpdateDto    Required parameter: Defines the data for the Credit Note to update
     * @return    Returns the void response from the API call 
     */
    public void updateCreditNotePutAsync(
                final String creditNoteNumber,
                final CreditNoteUpdateDto creditNoteUpdateDto,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/creditNote/{creditNoteNumber}");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5749185921195969876L;
            {
                    put( "creditNoteNumber", creditNoteNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5638713856083473618L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().putBody(_queryUrl, _headers, APIHelper.serialize(creditNoteUpdateDto));

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, new APICallBack<HttpResponse>() {
                    public void onSuccess(HttpContext _context, HttpResponse _response) {
                        try {

                            //invoke the callback after response if its not null
                            if (getHttpCallBack() != null)	
                            {
                                getHttpCallBack().OnAfterResponse(_context);
                            }

                            //handle errors defined at the API level
                            validateResponse(_response, _context);

                            //let the caller know of the success
                            callBack.onSuccess(_context, _context);
                        } catch (APIException error) {
                            //let the caller know of the error
                            callBack.onFailure(_context, error);
                        } catch (Exception exception) {
                            //let the caller know of the caught Exception
                            callBack.onFailure(_context, exception);
                        }
                    }
                    public void onFailure(HttpContext _context, Throwable _error) {
                        //invoke the callback after response if its not null
                        if (getHttpCallBack() != null)	
                            {
                            getHttpCallBack().OnAfterResponse(_context);
                        }

                        //let the caller know of the failure
                        callBack.onFailure(_context, _error);
                    }
                });
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteNumber    Required parameter: Reference number of the credit note to be released
     * @param    releaseActionDto    Required parameter: Example: 
     * @return    Returns the void response from the API call 
     */
    public void createCreditNoteReleaseInvoice(
                final String creditNoteNumber,
                final Object releaseActionDto
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        createCreditNoteReleaseInvoiceAsync(creditNoteNumber, releaseActionDto, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteNumber    Required parameter: Reference number of the credit note to be released
     * @param    releaseActionDto    Required parameter: Example: 
     * @return    Returns the void response from the API call 
     */
    public void createCreditNoteReleaseInvoiceAsync(
                final String creditNoteNumber,
                final Object releaseActionDto,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/creditNote/{creditNoteNumber}/action/release");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5422296320126427099L;
            {
                    put( "creditNoteNumber", creditNoteNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4690967182891654814L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(releaseActionDto));

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, new APICallBack<HttpResponse>() {
                    public void onSuccess(HttpContext _context, HttpResponse _response) {
                        try {

                            //invoke the callback after response if its not null
                            if (getHttpCallBack() != null)	
                            {
                                getHttpCallBack().OnAfterResponse(_context);
                            }

                            //handle errors defined at the API level
                            validateResponse(_response, _context);

                            //let the caller know of the success
                            callBack.onSuccess(_context, _context);
                        } catch (APIException error) {
                            //let the caller know of the error
                            callBack.onFailure(_context, error);
                        } catch (Exception exception) {
                            //let the caller know of the caught Exception
                            callBack.onFailure(_context, exception);
                        }
                    }
                    public void onFailure(HttpContext _context, Throwable _error) {
                        //invoke the callback after response if its not null
                        if (getHttpCallBack() != null)	
                            {
                            getHttpCallBack().OnAfterResponse(_context);
                        }

                        //let the caller know of the failure
                        callBack.onFailure(_context, _error);
                    }
                });
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteNumber    Required parameter: Identifies the Credit Note
     * @return    Returns the void response from the API call 
     */
    public void createCreditNoteCreateHeaderAttachment(
                final String creditNoteNumber
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        createCreditNoteCreateHeaderAttachmentAsync(creditNoteNumber, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteNumber    Required parameter: Identifies the Credit Note
     * @return    Returns the void response from the API call 
     */
    public void createCreditNoteCreateHeaderAttachmentAsync(
                final String creditNoteNumber,
                final APICallBack<Object> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/creditNote/{creditNoteNumber}/attachment");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5429719484513560201L;
            {
                    put( "creditNoteNumber", creditNoteNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5563424945265576549L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().post(_queryUrl, _headers, null);

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, new APICallBack<HttpResponse>() {
                    public void onSuccess(HttpContext _context, HttpResponse _response) {
                        try {

                            //invoke the callback after response if its not null
                            if (getHttpCallBack() != null)	
                            {
                                getHttpCallBack().OnAfterResponse(_context);
                            }

                            //handle errors defined at the API level
                            validateResponse(_response, _context);

                            //let the caller know of the success
                            callBack.onSuccess(_context, _context);
                        } catch (APIException error) {
                            //let the caller know of the error
                            callBack.onFailure(_context, error);
                        } catch (Exception exception) {
                            //let the caller know of the caught Exception
                            callBack.onFailure(_context, exception);
                        }
                    }
                    public void onFailure(HttpContext _context, Throwable _error) {
                        //invoke the callback after response if its not null
                        if (getHttpCallBack() != null)	
                            {
                            getHttpCallBack().OnAfterResponse(_context);
                        }

                        //let the caller know of the failure
                        callBack.onFailure(_context, _error);
                    }
                });
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteNumber    Required parameter: Identifies the Credit Note
     * @param    lineNumber    Required parameter: Specifies line number
     * @return    Returns the void response from the API call 
     */
    public void createCreditNoteCreateLineAttachment(
                final String creditNoteNumber,
                final int lineNumber
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        createCreditNoteCreateLineAttachmentAsync(creditNoteNumber, lineNumber, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    creditNoteNumber    Required parameter: Identifies the Credit Note
     * @param    lineNumber    Required parameter: Specifies line number
     * @return    Returns the void response from the API call 
     */
    public void createCreditNoteCreateLineAttachmentAsync(
                final String creditNoteNumber,
                final int lineNumber,
                final APICallBack<Object> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/creditNote/{creditNoteNumber}/{lineNumber}/attachment");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 4887765674770871993L;
            {
                    put( "creditNoteNumber", creditNoteNumber );
                    put( "lineNumber", lineNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5716025220350658145L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().post(_queryUrl, _headers, null);

        //invoke the callback before request if its not null
        if (getHttpCallBack() != null)
        {
            getHttpCallBack().OnBeforeRequest(_request);
        }

        //invoke request and get response
        Runnable _responseTask = new Runnable() {
            public void run() {
                //make the API call
                getClientInstance().executeAsStringAsync(_request, new APICallBack<HttpResponse>() {
                    public void onSuccess(HttpContext _context, HttpResponse _response) {
                        try {

                            //invoke the callback after response if its not null
                            if (getHttpCallBack() != null)	
                            {
                                getHttpCallBack().OnAfterResponse(_context);
                            }

                            //handle errors defined at the API level
                            validateResponse(_response, _context);

                            //let the caller know of the success
                            callBack.onSuccess(_context, _context);
                        } catch (APIException error) {
                            //let the caller know of the error
                            callBack.onFailure(_context, error);
                        } catch (Exception exception) {
                            //let the caller know of the caught Exception
                            callBack.onFailure(_context, exception);
                        }
                    }
                    public void onFailure(HttpContext _context, Throwable _error) {
                        //invoke the callback after response if its not null
                        if (getHttpCallBack() != null)	
                            {
                            getHttpCallBack().OnAfterResponse(_context);
                        }

                        //let the caller know of the failure
                        callBack.onFailure(_context, _error);
                    }
                });
            }
        };

        //execute async using thread pool
        APIHelper.getScheduler().execute(_responseTask);
    }

}