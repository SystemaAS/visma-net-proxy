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
import no.systema.visma.sdk.models.InventoryAdjustmentDto;
import no.systema.visma.sdk.models.InventoryAdjustmentUpdateDto;

public class InventoryAdjustmentController extends BaseController {    
    //private static variables for the singleton pattern
    private static Object syncObject = new Object();
    private static InventoryAdjustmentController instance = null;

    /**
     * Singleton pattern implementation 
     * @return The singleton instance of the InventoryAdjustmentController class 
     */
    public static InventoryAdjustmentController getInstance() {
        synchronized (syncObject) {
            if (null == instance) {
                instance = new InventoryAdjustmentController();
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
     * @return    Returns the List<InventoryAdjustmentDto> response from the API call 
     */
    public List<InventoryAdjustmentDto> getInventoryAdjustmentGetAll(
                final String greaterThanValue,
                final String lastModifiedDateTime,
                final String lastModifiedDateTimeCondition,
                final Integer numberToRead,
                final String orderBy,
                final Integer skipRecords
    ) throws Throwable {
        APICallBackCatcher<List<InventoryAdjustmentDto>> callback = new APICallBackCatcher<List<InventoryAdjustmentDto>>();
        getInventoryAdjustmentGetAllAsync(greaterThanValue, lastModifiedDateTime, lastModifiedDateTimeCondition, numberToRead, orderBy, skipRecords, callback);
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
    public void getInventoryAdjustmentGetAllAsync(
                final String greaterThanValue,
                final String lastModifiedDateTime,
                final String lastModifiedDateTimeCondition,
                final Integer numberToRead,
                final String orderBy,
                final Integer skipRecords,
                final APICallBack<List<InventoryAdjustmentDto>> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/inventoryadjustment");

        //process query parameters
        APIHelper.appendUrlWithQueryParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 4677839305658844367L;
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
            private static final long serialVersionUID = 5441106135491255432L;
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
                            List<InventoryAdjustmentDto> _result = APIHelper.deserialize(_responseBody,
                                                        new TypeReference<List<InventoryAdjustmentDto>>(){});

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
     * @param    inventoryAdjustment    Required parameter: Define the data for the inventory adjustment to create
     * @return    Returns the void response from the API call 
     */
    public void createInventoryAdjustmentPost(
                final InventoryAdjustmentUpdateDto inventoryAdjustment
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        createInventoryAdjustmentPostAsync(inventoryAdjustment, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    inventoryAdjustment    Required parameter: Define the data for the inventory adjustment to create
     * @return    Returns the void response from the API call 
     */
    public void createInventoryAdjustmentPostAsync(
                final InventoryAdjustmentUpdateDto inventoryAdjustment,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/inventoryadjustment");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4876380017747964703L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(inventoryAdjustment));

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
     * @param    adjRefNumber    Required parameter: Identifies the inventory item to update
     * @param    adjustment    Required parameter: The data to update for inventory item
     * @return    Returns the void response from the API call 
     */
    public void updateInventoryAdjustmentPut(
                final String adjRefNumber,
                final InventoryAdjustmentUpdateDto adjustment
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        updateInventoryAdjustmentPutAsync(adjRefNumber, adjustment, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    adjRefNumber    Required parameter: Identifies the inventory item to update
     * @param    adjustment    Required parameter: The data to update for inventory item
     * @return    Returns the void response from the API call 
     */
    public void updateInventoryAdjustmentPutAsync(
                final String adjRefNumber,
                final InventoryAdjustmentUpdateDto adjustment,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/inventoryadjustment/{adjRefNumber}");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 4975533278769298651L;
            {
                    put( "adjRefNumber", adjRefNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5410553876230091552L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().putBody(_queryUrl, _headers, APIHelper.serialize(adjustment));

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
     * @param    adjRefNumber    Required parameter: Reference number of the released adjustment to be released
     * @return    Returns the void response from the API call 
     */
    public void createInventoryAdjustmentReleaseDocument(
                final String adjRefNumber
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        createInventoryAdjustmentReleaseDocumentAsync(adjRefNumber, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    adjRefNumber    Required parameter: Reference number of the released adjustment to be released
     * @return    Returns the void response from the API call 
     */
    public void createInventoryAdjustmentReleaseDocumentAsync(
                final String adjRefNumber,
                final APICallBack<Object> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/inventoryadjustment/{adjRefNumber}/action/release");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5326596181517711822L;
            {
                    put( "adjRefNumber", adjRefNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4907922733474214384L;
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
     * @param    inventoryAdjustmentNumber    Required parameter: Identifies the Inventory Adjustment document
     * @return    Returns the InventoryAdjustmentDto response from the API call 
     */
    public InventoryAdjustmentDto getInventoryAdjustmentGet(
                final String inventoryAdjustmentNumber
    ) throws Throwable {
        APICallBackCatcher<InventoryAdjustmentDto> callback = new APICallBackCatcher<InventoryAdjustmentDto>();
        getInventoryAdjustmentGetAsync(inventoryAdjustmentNumber, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        return callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    inventoryAdjustmentNumber    Required parameter: Identifies the Inventory Adjustment document
     * @return    Returns the void response from the API call 
     */
    public void getInventoryAdjustmentGetAsync(
                final String inventoryAdjustmentNumber,
                final APICallBack<InventoryAdjustmentDto> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/inventoryadjustment/{inventoryAdjustmentNumber}");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5558980772015354842L;
            {
                    put( "inventoryAdjustmentNumber", inventoryAdjustmentNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5032814865271352131L;
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
                            InventoryAdjustmentDto _result = APIHelper.deserialize(_responseBody,
                                                        new TypeReference<InventoryAdjustmentDto>(){});

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

}