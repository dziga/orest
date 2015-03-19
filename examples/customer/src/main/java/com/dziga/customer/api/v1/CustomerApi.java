package com.dziga.customer.api.v1;

import com.dziga.customer.domain.v1.Customer;
import com.dziga.customer.domain.v1.ObjectFactory;
import com.github.dziga.orest.client.ObjectRestClient;
import org.json.JSONException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class CustomerApi {
	
	private ObjectRestClient objectRestClient;
	private Customer customer;
	private ObjectFactory objectFactory = new ObjectFactory();
	
	public CustomerApi() {
		objectRestClient = new ObjectRestClient(RestEndpoints.HOST);
		objectRestClient.addHeader("Content-Type", "application/xml");
		objectRestClient.addHeader("Accept", "application/xml");
		customer = objectFactory.createCustomer();
	}
	
	public void setCustomerId(long id) {
		customer.setId(id);
	}
	
	public void setCustomerFirstName(String name) {
		customer.setFirstname(name);
	}
	
	public void setCustomerLastName(String name) {
		customer.setLastname(name);
	}
	
	public void setCustomerStreet(String street) {
		customer.setStreet(street);
	}
	
	public void setCustomerCity(String city) {
		customer.setCity(city);
	}
	
	public void getCustomer() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
		customer = (Customer) objectRestClient.getFromService(Customer.class, String.format(RestEndpoints.CUSTOMER, customer.getId()));
	}
	
	public void createNewCustomer() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
		customer = (Customer) objectRestClient.postToService(Customer.class, customer, RestEndpoints.CUSTOMER_LIST);
	}
	
	public void editCustomer() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
		customer = (Customer) objectRestClient.putToService(Customer.class, customer, String.format(RestEndpoints.CUSTOMER, customer.getId()));
	}
	
	public int deleteCustomer() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
		return objectRestClient.deleteViaService(String.format(RestEndpoints.CUSTOMER, customer.getId()));
	}
	
	public long getCustomerId() {
		return customer.getId();
	}
	
	public String getCustomerFirstName() {
		return customer.getFirstname();
	}
	
	public String getCustomerLastName() {
		return customer.getLastname();
	}
	
	public String getCustomerStreet() {
		return customer.getStreet();
	}
	
	public String getCustomerCity() {
		return customer.getCity();
	}
}
