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

    /**
     * Set request format type. Type to which
     * object will be marshaled.
     * Request type can be 'xml' or 'json'.
     *
     * @param requestFormat
     */
    public void setRequestType (String requestFormat);

    /**
     * Add http headers.
     *
     * @param headerName
     * @param headerValue
     */
    public void addHeader(String headerName, String headerValue);

    /**
     * Set namespace URI.
     *
     * @param namespaceUri
     */
    public void setNamespaceUri(String namespaceUri);

    /**
     * Get http last response code.
     *
     * @return last response code
     */
    public int getResponseCode ();

    /**
     * Get http last response body.
     *
     * @return last response body
     */
    public String getResponseBody ();

    /**
     * Perform GET method to the server with specified parameters.
     *
     * @param returningModel type of expected return object
     * @param servicePath path to resource
     * @param params url parameters
     *
     * @return object who's type matches returningModel
     *
     * @throws InvalidKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public Object getFromService(Class returningModel, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException;

    /**
     * Perform GET method to the server with specified parameters.
     *
     * @param returningModel type of expected return object
     * @param servicePath path to resource
     *
     * @return object who's type matches returningModel
     *
     * @throws InvalidKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public Object getFromService(Class returningModel, String servicePath) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException;

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
     *
     * @throws InvalidKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public Object postToService(Class modelClass, Object modelObject, Class returningModel, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException;

    /**
     * Perform POST method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service (will be used as return model)
     * @param modelObject actual object with values to be posted to service
     * @param servicePath path to resource
     * @param params url parameters
     *
     * @return object who's type matches returningModel
     *
     * @throws InvalidKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public Object postToService(Class modelClass, Object modelObject, String servicePath, HashMap<String, String> params) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

    /**
     * Perform POST method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service
     * @param modelObject actual object with values to be posted to service
     * @param returningModel type of expected return object
     * @param servicePath path to resource
     *
     * @return object who's type matches returningModel
     *
     * @throws InvalidKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public Object postToService(Class modelClass, Object modelObject, Class returningModel, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

    /**
     * Perform POST method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service (will be used as return model)
     * @param modelObject actual object with values to be posted to service
     * @param servicePath path to resource
     *
     * @return object who's type matches returningModel
     *
     * @throws InvalidKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public Object postToService(Class modelClass, Object modelObject, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

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
     *
     * @throws InvalidKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public Object putToService(Class modelClass, Object modelObject, Class returningModel, String servicePath, HashMap<String, String> params) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException;

    /**
     * Perform PUT method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service (will be used as return model)
     * @param modelObject actual object with values to be posted to service
     * @param servicePath path to resource
     * @param params url parameters
     *
     * @return object who's type matches returningModel
     *
     * @throws InvalidKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public Object putToService(Class modelClass, Object modelObject, String servicePath, HashMap<String, String> params) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

    /**
     * Perform PUT method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service
     * @param modelObject actual object with values to be posted to service
     * @param returningModel type of expected return object
     * @param servicePath path to resource
     *
     * @return object who's type matches returningModel
     *
     * @throws InvalidKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public Object putToService(Class modelClass, Object modelObject, Class returningModel, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

    /**
     * Perform PUT method to the server with specified parameters.
     *
     * @param modelClass type of object being posted to service (will be used as return model)
     * @param modelObject actual object with values to be posted to service
     * @param servicePath path to resource
     *
     * @return object who's type matches returningModel
     *
     * @throws InvalidKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public Object putToService(Class modelClass, Object modelObject, String servicePath) throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException;

    /**
     * Perform DELETE method to the server with specified parameters.
     *
     * @param servicePath path to resource
     *
     * @return response code
     *
     * @throws InvalidKeyException
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws URISyntaxException
     * @throws IOException
     * @throws JAXBException
     * @throws XMLStreamException
     * @throws JSONException
     */
    public int deleteViaService(String servicePath) throws InvalidKeyException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, JAXBException, XMLStreamException, JSONException;
}
