package com.dziga.customer.api.v2;

import com.dziga.customer.domain.v2.Address;
import com.dziga.customer.domain.v2.Customer;
import com.dziga.customer.domain.v2.CustomerList;
import com.dziga.customer.domain.v2.ObjectFactory;
import com.github.dziga.orest.client.ObjectRestClient;
import org.json.JSONException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class CustomerApi {
	
	private ObjectRestClient objectRestClient;
	private Customer customer;
    private CustomerList customers;
    private Address address;
	private ObjectFactory objectFactory = new ObjectFactory();
	private String requestFormat = "xml";
	
	public CustomerApi() {
		objectRestClient = new ObjectRestClient(RestEndpoints.HOST);
		objectRestClient.addHeader("Content-Type", "application/xml, application/json");
		objectRestClient.addHeader("Accept", "application/xml, application/json");
		customer = objectFactory.createCustomer();
        customers = objectFactory.createCustomerList();
        address = objectFactory.createAddress();
	}
	
	public void setCustomerRequestFormat(String requestFormat) {
		this.requestFormat = requestFormat;
	}
	
	public void setCustomerId(long id) {
		customer.setId(id);
	}
	
	public void setCustomerFirstName(String name) {
		customer.setFirstName(name);
	}
	
	public void setCustomerLastName(String name) {
		customer.setLastName(name);
	}
	
	public void setCustomerSignedContractDate(String date) {
		customer.setSignedContractDate(date);
	}
	
	public void setCustomerStreet(String street) {
		address.setStreet(street);
	}
	
	public void setCustomerStreetNumber(int number) {
        address.setStreetNumber(number);
	}
	
	public void setCustomerCity(String city) {
        address.setCity(city);
	}
	
	public void setCustomerPostalCode(Integer postCode) {
        address.setPostalCode(BigInteger.valueOf(postCode));
	}

    public void setCustomerAddress() {
        customer.setAddress(address);
    }
	
	public void getCustomer() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
		customer = (Customer) objectRestClient.getFromService(Customer.class, String.format(RestEndpoints.CUSTOMER, customer.getId()));
	}

	public void createNewCustomer() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
		objectRestClient.setRequestType(requestFormat);
		customer = (Customer) objectRestClient.postToService(Customer.class, customer, RestEndpoints.CUSTOMER_LIST);
	}

	public void editCustomer() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
		objectRestClient.setRequestType(requestFormat);
		customer = (Customer) objectRestClient.putToService(Customer.class, customer, String.format(RestEndpoints.CUSTOMER, customer.getId()));
	}
	
	public int deleteCustomer() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
		return objectRestClient.deleteViaService(String.format(RestEndpoints.CUSTOMER, customer.getId()));
	}

    public void getCustomers() throws NoSuchAlgorithmException, KeyManagementException, JAXBException, URISyntaxException, XMLStreamException, InvalidKeyException, IOException {
        customers = (CustomerList)objectRestClient.getFromService(CustomerList.class, RestEndpoints.CUSTOMER_LIST);
    }

    public void getCustomerFromList(int order) {
        customer = customers.getCustomer().get(order);
    }

	public long getCustomerId() {
		return customer.getId();
	}
	
	public String getCustomerFirstName() {
		return customer.getFirstName();
	}
	
	public String getCustomerLastName() {
		return customer.getLastName();
	}
	
	public String getCustomerSignedContractDate() {
		return customer.getSignedContractDate();
	}
	
	public String getCustomerStreet() {
		return customer.getAddress().getStreet();
	}
	
	public int getCustomerStreetNumber() {
		return customer.getAddress().getStreetNumber();
	}
	
	public String getCustomerCity() {
		return customer.getAddress().getCity();
	}
	
	public Integer getCustomerPostalCode() {
		return (int) customer.getAddress().getPostalCode().longValue();
	}
}
