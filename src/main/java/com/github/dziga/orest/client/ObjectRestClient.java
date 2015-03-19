package com.github.dziga.orest.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
    
    public ObjectRestClient(String host) {
    	rest = new HttpRestClient(host);
    }
    
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

    public void setRequestType (String requestFormat) {
    	this.requestFormat = RequestFormat.valueOf(requestFormat);
    }
    
    public void addHeader(String headerName, String headerValue) {
		rest.addHeader(headerName, headerValue);
	}

    public void setNamespaceUri(String namespaceUri) {
        this.namespaceUri = namespaceUri;
    }
    
    public int getResponseCode () {
        return responseCode;
    }
    
    public String getResponseBody () {
        return rest.getResponseBody();
    }
    
    public Object getFromService(Class returningModel, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException {
        return doRequest(RequestType.GET, null, null, servicePath, params, returningModel);
    }
    
    public Object getFromService(Class returningModel, String servicePath) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException {
        return getFromService(returningModel, servicePath, null);
    }
    
    public Object postToService(Class modelClass, Object modelObject, Class returningModel, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException {
        return doRequest(RequestType.POST, modelObject, modelClass,  servicePath, params, returningModel);
    }
    
    public Object postToService(Class modelClass, Object modelObject, String servicePath, HashMap<String, String> params) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return postToService(modelClass, modelObject, null, servicePath, params);
    }
    
    public Object postToService(Class modelClass, Object modelObject, Class returningModel, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return postToService(modelClass, modelObject, returningModel, servicePath, null);
    }
    
    public Object postToService(Class modelClass, Object modelObject, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return postToService(modelClass, modelObject, null, servicePath, null);
    }

    public Object putToService(Class modelClass, Object abstractObject, Class returningClass, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException {
        return doRequest(RequestType.PUT, abstractObject, modelClass, servicePath, params, returningClass);
    }
    
    public Object putToService(Class modelClass, Object abstractObject, String servicePath, HashMap<String, String> params) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return putToService(modelClass, abstractObject, null, servicePath, params);
    }
    
    public Object putToService(Class modelClass, Object abstractObject, Class returningModel, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return putToService(modelClass, abstractObject, returningModel, servicePath, null);
    }
    
    public Object putToService(Class modelClass, Object abstractObject, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        return putToService(modelClass, abstractObject, null, servicePath, null);
    }
    
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
