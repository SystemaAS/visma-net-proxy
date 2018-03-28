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
import no.systema.visma.sdk.models.InventoryReceiptDto;
import no.systema.visma.sdk.models.InventoryReceiptUpdateDto;

public class InventoryReceiptController extends BaseController {    
    //private static variables for the singleton pattern
    private static Object syncObject = new Object();
    private static InventoryReceiptController instance = null;

    /**
     * Singleton pattern implementation 
     * @return The singleton instance of the InventoryReceiptController class 
     */
    public static InventoryReceiptController getInstance() {
        synchronized (syncObject) {
            if (null == instance) {
                instance = new InventoryReceiptController();
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
     * @param    skipRecords    Optional parameter: Example: 
     * @return    Returns the List<InventoryReceiptDto> response from the API call 
     */
    public List<InventoryReceiptDto> getInventoryReceiptGetAll(
                final String greaterThanValue,
                final String lastModifiedDateTime,
                final String lastModifiedDateTimeCondition,
                final Integer numberToRead,
                final String orderBy,
                final Integer skipRecords
    ) throws Throwable {
        APICallBackCatcher<List<InventoryReceiptDto>> callback = new APICallBackCatcher<List<InventoryReceiptDto>>();
        getInventoryReceiptGetAllAsync(greaterThanValue, lastModifiedDateTime, lastModifiedDateTimeCondition, numberToRead, orderBy, skipRecords, callback);
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
     * @param    skipRecords    Optional parameter: Example: 
     * @return    Returns the void response from the API call 
     */
    public void getInventoryReceiptGetAllAsync(
                final String greaterThanValue,
                final String lastModifiedDateTime,
                final String lastModifiedDateTimeCondition,
                final Integer numberToRead,
                final String orderBy,
                final Integer skipRecords,
                final APICallBack<List<InventoryReceiptDto>> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/inventoryReceipt");

        //process query parameters
        APIHelper.appendUrlWithQueryParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 4970587138270189978L;
            {
                    put( "greaterThanValue", greaterThanValue );
                    put( "lastModifiedDateTime", lastModifiedDateTime );
                    put( "lastModifiedDateTimeCondition", lastModifiedDateTimeCondition );
                    put( "numberToRead", numberToRead );
                    put( "orderBy", orderBy );
                    put( "skipRecords", skipRecords );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5419158607065677657L;
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
                            List<InventoryReceiptDto> _result = APIHelper.deserialize(_responseBody,
                                                        new TypeReference<List<InventoryReceiptDto>>(){});

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
     * @param    inventoryReceipt    Required parameter: Define the data for the inventory issue to create
     * @return    Returns the void response from the API call 
     */
    public void createInventoryReceiptPost(
                final InventoryReceiptUpdateDto inventoryReceipt
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        createInventoryReceiptPostAsync(inventoryReceipt, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    inventoryReceipt    Required parameter: Define the data for the inventory issue to create
     * @return    Returns the void response from the API call 
     */
    public void createInventoryReceiptPostAsync(
                final InventoryReceiptUpdateDto inventoryReceipt,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/inventoryReceipt");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4637201336292967978L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(inventoryReceipt));

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
     * @param    inventoryReceiptNumber    Required parameter: Identifies the Inventory Issue document
     * @return    Returns the InventoryReceiptDto response from the API call 
     */
    public InventoryReceiptDto getInventoryReceiptGet(
                final String inventoryReceiptNumber
    ) throws Throwable {
        APICallBackCatcher<InventoryReceiptDto> callback = new APICallBackCatcher<InventoryReceiptDto>();
        getInventoryReceiptGetAsync(inventoryReceiptNumber, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        return callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    inventoryReceiptNumber    Required parameter: Identifies the Inventory Issue document
     * @return    Returns the void response from the API call 
     */
    public void getInventoryReceiptGetAsync(
                final String inventoryReceiptNumber,
                final APICallBack<InventoryReceiptDto> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/inventoryReceipt/{inventoryReceiptNumber}");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 4947161178696395124L;
            {
                    put( "inventoryReceiptNumber", inventoryReceiptNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5652721414509317201L;
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
                            InventoryReceiptDto _result = APIHelper.deserialize(_responseBody,
                                                        new TypeReference<InventoryReceiptDto>(){});

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
     * @param    invoiceNumber    Required parameter: Reference number of the released issue to be released
     * @return    Returns the void response from the API call 
     */
    public void createInventoryReceiptReleaseDocument(
                final String invoiceNumber
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        createInventoryReceiptReleaseDocumentAsync(invoiceNumber, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    invoiceNumber    Required parameter: Reference number of the released issue to be released
     * @return    Returns the void response from the API call 
     */
    public void createInventoryReceiptReleaseDocumentAsync(
                final String invoiceNumber,
                final APICallBack<Object> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/inventoryReceipt/{invoiceNumber}/action/release");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 4754791455390221768L;
            {
                    put( "invoiceNumber", invoiceNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5734746086975243373L;
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
     * @param    receipt    Required parameter: The data to update for inventory receipt
     * @param    receiptRefNumber    Required parameter: Identifies the inventory receipt to update
     * @return    Returns the void response from the API call 
     */
    public void updateInventoryReceiptPut(
                final InventoryReceiptUpdateDto receipt,
                final String receiptRefNumber
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        updateInventoryReceiptPutAsync(receipt, receiptRefNumber, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    receipt    Required parameter: The data to update for inventory receipt
     * @param    receiptRefNumber    Required parameter: Identifies the inventory receipt to update
     * @return    Returns the void response from the API call 
     */
    public void updateInventoryReceiptPutAsync(
                final InventoryReceiptUpdateDto receipt,
                final String receiptRefNumber,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/inventoryReceipt/{receiptRefNumber}");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5437590582349470831L;
            {
                    put( "receiptRefNumber", receiptRefNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4810724076551767844L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().putBody(_queryUrl, _headers, APIHelper.serialize(receipt));

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