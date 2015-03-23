/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015. Nikola Milivojevic
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.dziga.orest.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import com.github.dziga.orest.api.ORest;
import org.json.JSONException;

public class ObjectRestClient implements ORest {

    private HttpRestClient rest;
    private int responseCode;
    private RequestFormat requestFormat = RequestFormat.xml;
    private String namespaceUri = "";

    /**
     * Instantiate new object rest client with host address.
     *
     * @param host address
     */
    public ObjectRestClient(String host) {
    	rest = new HttpRestClient(host);
    }

    /**
     * Instantiate new object rest client with
     * schema http(s) and host address.
     *
     * @param scheme http/https
     * @param host address
     */
    public ObjectRestClient(String scheme, String host) {
    	rest = new HttpRestClient(scheme, host);
    }

	private enum RequestFormat { 
        json("json"), xml("xml");
           
        private String requestFormat;
 
        private RequestFormat(String s) {
        	requestFormat = s;
        }
       
        public String getRequestFormat() {
          return requestFormat;
        }
    }

    private enum RequestType { 
        GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");
           
        private String requestType;
 
        private RequestType(String s) {
            requestType = s;
        }
       
        public String getRequestType() {
          return requestType;
        }
    }

    /**
     * Set request format type. Type to which
     * object will be marshaled.
     *
     * @param requestFormat xml/json
     */
    public void setRequestType (String requestFormat) {
    	this.requestFormat = RequestFormat.valueOf(requestFormat);
    }

    /**
     * Add http headers.
     *
     * @param headerName header name
     * @param headerValue header value
     */
    public void addHeader(String headerName, String headerValue) {
		rest.addHeader(headerName, headerValue);
	}

    /**
     * Set namespace URI.
     *
     * @param namespaceUri namespace uri
     */
    public void setNamespaceUri(String namespaceUri) {
        this.namespaceUri = namespaceUri;
    }

    /**
     * Get http last response code.
     *
     * @return last response code
     */
    public int getResponseCode () {
        return responseCode;
    }

    /**
     * Get http last response body.
     *
     * @return last response body
     */
    public String getResponseBody () {
        return rest.getResponseBody();
    }

    /**
     * Check if last response contains header.
     *
     * @param name header name
     * @return header with name exists
     */
    public boolean lastResponseContainsHeader(String name) {
        return rest.containsHeader(name);
    }

    /**
     * Get list of header values by name.
     *
     * @param name header name
     * @return list of header values
     */
    public List<String> getLastResponseHeaderValues (String name) {
        return rest.getResponseHeaderValues(name);
    }

    /**
     * Get all headers from last response.
     *
     * @return all headers from last response in key value pairs
     */
    public Map<String, String> getLastResponseHeaders () {
        return rest.getResponseHeaders();
    }

    /**
     * Perform GET method to the server with specified parameters.
     *
     * @param returningModel type of expected return object
     * @param servicePath path to resource
     * @param params url parameters
     *
     * @return object who's type matches returningModel
     */
    public Object getFromService(Class returningModel, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException {
        return doRequest(RequestType.GET, null, null, servicePath, params, returningModel);
    }

    /**
     * Perform GET method to the server with specified parameters.
     *
     * @param returningModel type of expected return object
     * @param servicePath path to resource
     *
     * @return object who's type matches returningModel
     */
    public Object getFromService(Class returningModel, String servicePath) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException {
        return getFromService(returningModel, servicePath, null);
    }

    /**
     * Perform POST method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service
     * @param modelObject actual object with values to be posted to service
     * @param returningModel type of expected return object
     * @param servicePath path to resource
     * @param params url parameters
     *
     * @return object who's type matches returningModel
     */
    public Object postToService(Class modelClass, Object modelObject, Class returningModel, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException {
        return doRequest(RequestType.POST, modelObject, modelClass,  servicePath, params, returningModel);
    }

    /**
     * Perform POST method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service (will be used as return model)
     * @param modelObject actual object with values to be posted to service
     * @param servicePath path to resource
     * @param params url parameters
     *
     * @return object who's type matches returningModel
     */
    public Object postToService(Class modelClass, Object modelObject, String servicePath, HashMap<String, String> params) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return postToService(modelClass, modelObject, null, servicePath, params);
    }

    /**
     * Perform POST method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service
     * @param modelObject actual object with values to be posted to service
     * @param returningModel type of expected return object
     * @param servicePath path to resource
     *
     * @return object who's type matches returningModel
     */
    public Object postToService(Class modelClass, Object modelObject, Class returningModel, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return postToService(modelClass, modelObject, returningModel, servicePath, null);
    }

    /**
     * Perform POST method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service (will be used as return model)
     * @param modelObject actual object with values to be posted to service
     * @param servicePath path to resource
     *
     * @return object who's type matches returningModel
     */
    public Object postToService(Class modelClass, Object modelObject, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return postToService(modelClass, modelObject, null, servicePath, null);
    }

    /**
     * Perform PUT method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service
     * @param modelObject actual object with values to be posted to service
     * @param returningModel type of expected return object
     * @param servicePath path to resource
     * @param params url parameters
     *
     * @return object who's type matches returningModel
     */
    public Object putToService(Class modelClass, Object modelObject, Class returningModel, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException {
        return doRequest(RequestType.PUT, modelObject, modelClass, servicePath, params, returningModel);
    }

    /**
     * Perform PUT method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service (will be used as return model)
     * @param modelObject actual object with values to be posted to service
     * @param servicePath path to resource
     * @param params url parameters
     *
     * @return object who's type matches returningModel
     */
    public Object putToService(Class modelClass, Object modelObject, String servicePath, HashMap<String, String> params) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return putToService(modelClass, modelObject, null, servicePath, params);
    }

    /**
     * Perform PUT method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service
     * @param modelObject actual object with values to be posted to service
     * @param returningModel type of expected return object
     * @param servicePath path to resource
     *
     * @return object who's type matches returningModel
     */
    public Object putToService(Class modelClass, Object modelObject, Class returningModel, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return putToService(modelClass, modelObject, returningModel, servicePath, null);
    }

    /**
     * Perform PUT method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service (will be used as return model)
     * @param modelObject actual object with values to be posted to service
     * @param servicePath path to resource
     *
     * @return object who's type matches returningModel
     */
    public Object putToService(Class modelClass, Object modelObject, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return putToService(modelClass, modelObject, null, servicePath, null);
    }

    /**
     * Perform DELETE method to the server with specified parameters.
     *
     * @param servicePath path to resource
     *
     * @return response code
     */
    public int deleteViaService(String servicePath) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException {
        doRequest(RequestType.DELETE, null, null, servicePath, null, null);
        return responseCode;
    }

    private Object doRequest(RequestType requestType, Object modelObject, Class modelClass, String servicePath, HashMap<String, String> params, Class returningModel) throws InvalidKeyException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, KeyManagementException, XMLStreamException, JSONException {
    	if (params != null && !(params.isEmpty())) {
            for (Entry<String, String> param : params.entrySet()) {
            	rest.addQueryParameter(param.getKey(), param.getValue());
            }
        }
        rest.setRequestPath(servicePath);
        if (requestType.getRequestType().equals("GET")) {
        	responseCode = rest.Get();
            if (returningModel == null) {
                return new Object();
            }
        }
        else if (requestType.getRequestType().equals("POST")) {
        	if (requestFormat.getRequestFormat().equals("json")) {
        		responseCode = rest.Post(Marshal.toJson(modelObject, modelClass, namespaceUri));
        	}
        	else {
        		responseCode = rest.Post(Marshal.toXml(modelObject, modelClass, namespaceUri));
        	}
        }
        else if (requestType.getRequestType().equals("PUT")) {
        	if (requestFormat.getRequestFormat().equals("json")) {
        		responseCode = rest.Put(Marshal.toJson(modelObject, modelClass, namespaceUri));
        	}
        	else {
        		responseCode = rest.Put(Marshal.toXml(modelObject, modelClass, namespaceUri));
        	}
        }
        else if (requestType.getRequestType().equals("DELETE")) {
            responseCode = rest.Delete();
            return new Object();
        }
        else {
            throw new IllegalStateException("Not known request type: " + requestType.getRequestType());
        }
        rest.removeAllQueryParameters();
        if (returningModel == null) {
            returningModel = modelClass;
        }
        return Marshal.toObject(rest.getResponseBody(), returningModel);
    }
}
