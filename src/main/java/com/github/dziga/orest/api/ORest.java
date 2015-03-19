package com.github.dziga.orest.api;

import org.json.JSONException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by n.milivojevic on 3/13/2015.
 */
public interface ORest {

    public void setRequestType (String requestFormat);

    public void addHeader(String headerName, String headerValue);

    public void setNamespaceUri(String namespaceUri);

    public int getResponseCode ();

    public String getResponseBody ();

    public Object getFromService(Class returningModel, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException;

    public Object getFromService(Class returningModel, String servicePath) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException;

    public Object postToService(Class modelClass, Object modelObject, Class returningModel, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException;

    public Object postToService(Class modelClass, Object modelObject, String servicePath, HashMap<String, String> params) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

    public Object postToService(Class modelClass, Object modelObject, Class returningModel, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

    public Object postToService(Class modelClass, Object modelObject, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

    public Object putToService(Class modelClass, Object abstractObject, Class returningClass, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException;

    public Object putToService(Class modelClass, Object abstractObject, String servicePath, HashMap<String, String> params) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

    public Object putToService(Class modelClass, Object abstractObject, Class returningModel, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

    public Object putToService(Class modelClass, Object abstractObject, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

    public int deleteViaService(String servicePath) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException;
}
