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
import no.systema.visma.sdk.models.CreateShipmentActionDto;
import no.systema.visma.sdk.models.SalesOrderDto;
import no.systema.visma.sdk.models.SalesOrderUpdateDto;

public class SalesOrderController extends BaseController {    
    //private static variables for the singleton pattern
    private static Object syncObject = new Object();
    private static SalesOrderController instance = null;

    /**
     * Singleton pattern implementation 
     * @return The singleton instance of the SalesOrderController class 
     */
    public static SalesOrderController getInstance() {
        synchronized (syncObject) {
            if (null == instance) {
                instance = new SalesOrderController();
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
     * @param    orderType    Optional parameter: Example: 
     * @param    skipRecords    Optional parameter: Example: 
     * @return    Returns the List<SalesOrderDto> response from the API call 
     */
    public List<SalesOrderDto> getSalesOrderGetAllOrders(
                final String greaterThanValue,
                final String lastModifiedDateTime,
                final String lastModifiedDateTimeCondition,
                final Integer numberToRead,
                final String orderBy,
                final String orderType,
                final Integer skipRecords
    ) throws Throwable {
        APICallBackCatcher<List<SalesOrderDto>> callback = new APICallBackCatcher<List<SalesOrderDto>>();
        getSalesOrderGetAllOrdersAsync(greaterThanValue, lastModifiedDateTime, lastModifiedDateTimeCondition, numberToRead, orderBy, orderType, skipRecords, callback);
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
     * @param    orderType    Optional parameter: Example: 
     * @param    skipRecords    Optional parameter: Example: 
     * @return    Returns the void response from the API call 
     */
    public void getSalesOrderGetAllOrdersAsync(
                final String greaterThanValue,
                final String lastModifiedDateTime,
                final String lastModifiedDateTimeCondition,
                final Integer numberToRead,
                final String orderBy,
                final String orderType,
                final Integer skipRecords,
                final APICallBack<List<SalesOrderDto>> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/salesorder");

        //process query parameters
        APIHelper.appendUrlWithQueryParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5561299407681892709L;
            {
                    put( "greaterThanValue", greaterThanValue );
                    put( "lastModifiedDateTime", lastModifiedDateTime );
                    put( "lastModifiedDateTimeCondition", lastModifiedDateTimeCondition );
                    put( "numberToRead", numberToRead );
                    put( "orderBy", orderBy );
                    put( "orderType", orderType );
                    put( "skipRecords", skipRecords );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4793299050015037059L;
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
                            List<SalesOrderDto> _result = APIHelper.deserialize(_responseBody,
                                                        new TypeReference<List<SalesOrderDto>>(){});

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
     * @param    saleOrderUpdateDto    Required parameter: Defines the data for the Sale Order to create
     * @return    Returns the void response from the API call 
     */
    public void createSalesOrderPost(
                final SalesOrderUpdateDto saleOrderUpdateDto
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        createSalesOrderPostAsync(saleOrderUpdateDto, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    saleOrderUpdateDto    Required parameter: Defines the data for the Sale Order to create
     * @return    Returns the void response from the API call 
     */
    public void createSalesOrderPostAsync(
                final SalesOrderUpdateDto saleOrderUpdateDto,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/salesorder");
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4780341591263551341L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(saleOrderUpdateDto));

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
     * @param    orderNbr    Required parameter: Identifies the So Order
     * @return    Returns the SalesOrderDto response from the API call 
     */
    public SalesOrderDto getSalesOrderGet(
                final String orderNbr
    ) throws Throwable {
        APICallBackCatcher<SalesOrderDto> callback = new APICallBackCatcher<SalesOrderDto>();
        getSalesOrderGetAsync(orderNbr, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        return callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    orderNbr    Required parameter: Identifies the So Order
     * @return    Returns the void response from the API call 
     */
    public void getSalesOrderGetAsync(
                final String orderNbr,
                final APICallBack<SalesOrderDto> callBack
    ) {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/salesorder/{orderNbr}");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5397461811773371226L;
            {
                    put( "orderNbr", orderNbr );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5738509230361529349L;
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
                            SalesOrderDto _result = APIHelper.deserialize(_responseBody,
                                                        new TypeReference<SalesOrderDto>(){});

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
     * @param    createShipmentActionDto    Required parameter: Defines the data for the action
     * @param    saleOrderNumber    Required parameter: Reference number of the sale oreder from which the shipment will be created
     * @return    Returns the void response from the API call 
     */
    public void createSalesOrderCreateShipmentAction(
                final CreateShipmentActionDto createShipmentActionDto,
                final String saleOrderNumber
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        createSalesOrderCreateShipmentActionAsync(createShipmentActionDto, saleOrderNumber, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    createShipmentActionDto    Required parameter: Defines the data for the action
     * @param    saleOrderNumber    Required parameter: Reference number of the sale oreder from which the shipment will be created
     * @return    Returns the void response from the API call 
     */
    public void createSalesOrderCreateShipmentActionAsync(
                final CreateShipmentActionDto createShipmentActionDto,
                final String saleOrderNumber,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/salesorder/{saleOrderNumber}/action/createShipment");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5543748120320243849L;
            {
                    put( "saleOrderNumber", saleOrderNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 4781346265884306812L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().postBody(_queryUrl, _headers, APIHelper.serialize(createShipmentActionDto));

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
     * @param    saleOrderUpdateDto    Required parameter: Defines the data for the Sale Order to update
     * @param    salesOrderNumber    Required parameter: Identifies the Sale Order to update
     * @return    Returns the void response from the API call 
     */
    public void updateSalesOrderPut(
                final SalesOrderUpdateDto saleOrderUpdateDto,
                final String salesOrderNumber
    ) throws Throwable {
        APICallBackCatcher<Object> callback = new APICallBackCatcher<Object>();
        updateSalesOrderPutAsync(saleOrderUpdateDto, salesOrderNumber, callback);
        if(!callback.isSuccess())
            throw callback.getError();
        callback.getResult();
    }

    /**
     * TODO: type endpoint description here
     * @param    saleOrderUpdateDto    Required parameter: Defines the data for the Sale Order to update
     * @param    salesOrderNumber    Required parameter: Identifies the Sale Order to update
     * @return    Returns the void response from the API call 
     */
    public void updateSalesOrderPutAsync(
                final SalesOrderUpdateDto saleOrderUpdateDto,
                final String salesOrderNumber,
                final APICallBack<Object> callBack
    ) throws JsonProcessingException {
        //the base uri for api requests
        String _baseUri = Configuration.baseUri;
        
        //prepare query string for API call
        StringBuilder _queryBuilder = new StringBuilder(_baseUri);
        _queryBuilder.append("/controller/api/v1/salesorder/{salesOrderNumber}");

        //process template parameters
        APIHelper.appendUrlWithTemplateParameters(_queryBuilder, new HashMap<String, Object>() {
            private static final long serialVersionUID = 5279050120567688692L;
            {
                    put( "salesOrderNumber", salesOrderNumber );
            }});
        //validate and preprocess url
        String _queryUrl = APIHelper.cleanUrl(_queryBuilder);

        //load all headers for the outgoing API request
        Map<String, String> _headers = new HashMap<String, String>() {
            private static final long serialVersionUID = 5567335785165613141L;
            {
                    put( "user-agent", "APIMATIC 2.0" );
                    put( "Authorization", String.format("Bearer %1$s", Configuration.oAuthAccessToken) );
            }
        };

        //prepare and invoke the API call request to fetch the response
        final HttpRequest _request = getClientInstance().putBody(_queryUrl, _headers, APIHelper.serialize(saleOrderUpdateDto));

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