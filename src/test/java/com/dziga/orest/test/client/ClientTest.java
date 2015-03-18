package com.dziga.orest.test.client;

import com.dziga.orest.client.ObjectRestClient;
import com.dziga.orest.test.client.api.RestEndpoints;
import com.dziga.orest.test.client.domain.Customer;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SuppressWarnings("unused")
public class ClientTest {
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089).notifier(
	        new Slf4jNotifier(true)));

	@Test
	public void customerApiGet() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {

        ObjectRestClient objectRestClient = new ObjectRestClient(RestEndpoints.HOST);
        objectRestClient.addHeader("Content-Type", "application/xml, application/json");
        objectRestClient.addHeader("Accept", "application/xml, application/json");
        Customer customer = new Customer();
        customer.setId(3);

        customer = (Customer) objectRestClient.getFromService(Customer.class, String.format(RestEndpoints.CUSTOMER, customer.getId()));
		
		Assert.assertEquals(3, customer.getId());
		Assert.assertEquals("John", customer.getFirstName());
		Assert.assertEquals("Doe", customer.getLastName());
		Assert.assertEquals("09-10-2015", customer.getSignedContractDate());
		Assert.assertEquals("Backer street", customer.getStreet());
		Assert.assertEquals(3, customer.getStreetNumber());
		Assert.assertEquals("London", customer.getCity());
		Assert.assertEquals(BigInteger.valueOf(200002), customer.getPostalCode());
	}
	
	@Test
	public void customerApiCreate() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {

        ObjectRestClient objectRestClient = new ObjectRestClient(RestEndpoints.HOST);
        objectRestClient.addHeader("Content-Type", "application/xml, application/json");
        objectRestClient.addHeader("Accept", "application/xml, application/json");
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setSignedContractDate("09-10-2015");
        customer.setStreet("Backer street");
        customer.setStreetNumber(3);
        customer.setCity("London");
        customer.setPostalCode(BigInteger.valueOf(200002));
		
        customer = (Customer) objectRestClient.postToService(Customer.class, customer, Customer.class, RestEndpoints.CUSTOMER_LIST);

        Assert.assertEquals(1, customer.getId());
        Assert.assertEquals("John", customer.getFirstName());
        Assert.assertEquals("Doe", customer.getLastName());
        Assert.assertEquals("09-10-2015", customer.getSignedContractDate());
        Assert.assertEquals("Backer street", customer.getStreet());
        Assert.assertEquals(3, customer.getStreetNumber());
        Assert.assertEquals("London", customer.getCity());
        Assert.assertEquals(BigInteger.valueOf(200002), customer.getPostalCode());
	}
	
	@Test
	public void customerApiEdit() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
	
        ObjectRestClient objectRestClient = new ObjectRestClient(RestEndpoints.HOST);
        objectRestClient.addHeader("Content-Type", "application/xml, application/json");
        objectRestClient.addHeader("Accept", "application/xml, application/json");
        Customer customer = new Customer();
		customer.setId(2);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setSignedContractDate("09-10-2015");
		customer.setStreet("Backer street");
		customer.setStreetNumber(3);
		customer.setCity("London");
		customer.setPostalCode(BigInteger.valueOf(200002));
		
        customer = (Customer) objectRestClient.putToService(Customer.class, customer, Customer.class, String.format(RestEndpoints.CUSTOMER, customer.getId()));

		Assert.assertEquals(2, customer.getId());
		Assert.assertEquals("John", customer.getFirstName());
		Assert.assertEquals("Doe", customer.getLastName());
		Assert.assertEquals("09-10-2015", customer.getSignedContractDate());
		Assert.assertEquals("Backer street", customer.getStreet());
		Assert.assertEquals(3, customer.getStreetNumber());
		Assert.assertEquals("London", customer.getCity());
		Assert.assertEquals(BigInteger.valueOf(200002), customer.getPostalCode());
	}
	
	@Test
	public void customerApiDelete() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {

        ObjectRestClient objectRestClient = new ObjectRestClient(RestEndpoints.HOST);
        objectRestClient.addHeader("Content-Type", "application/xml, application/json");
        objectRestClient.addHeader("Accept", "application/xml, application/json");
        Customer customer = new Customer();
		customer.setId(5);
		
		Assert.assertTrue(objectRestClient.deleteViaService(String.format(RestEndpoints.CUSTOMER, customer.getId())) == 200);
	}
	
	@Before
	public void setUp() {
		
		stubFor(get(urlEqualTo("/customers/3")).withHeader("Accept", containing("application/xml"))
				.willReturn(
	            aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/xml")
	                .withBody(
	                    TestConstants.XML_HEADER 
	                            + "<Customer>"
	                            + "<Id>3</Id>"
	                            + "<FirstName>John</FirstName>"
	                            + "<LastName>Doe</LastName>"
	                            + "<SignedContractDate>09-10-2015</SignedContractDate>"
	                            + "<Street>Backer street</Street>"
	                            + "<StreetNumber>3</StreetNumber>"
	                            + "<City>London</City>"
	                            + "<PostalCode>200002</PostalCode>"
	                            + "</Customer>")));
		
		stubFor(post(urlEqualTo("/customers")).withHeader("Accept", containing("application/xml"))
				.withRequestBody(matching(
								".*<Customer>.*"
	                            + ".*<FirstName>John</FirstName>.*"
                        		+ ".*<LastName>Doe</LastName>.*" 
                        		+ ".*<SignedContractDate>09-10-2015</SignedContractDate>.*"
	                            + ".*<Street>Backer street</Street>.*"
	                            + ".*<StreetNumber>3</StreetNumber>.*"
	                            + ".*<City>London</City>.*"
	                            + ".*<PostalCode>200002</PostalCode>.*"
	                            + ".*</Customer>.*"
						))
				.willReturn(
	            aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/xml")
	                .withBody(
	                    TestConstants.XML_HEADER 
	                            + "<Customer>"
	                            + "<Id>1</Id>"
	                            + "<FirstName>John</FirstName>"
	                            + "<LastName>Doe</LastName>"
	                            + "<SignedContractDate>09-10-2015</SignedContractDate>"
	                            + "<Street>Backer street</Street>"
	                            + "<StreetNumber>3</StreetNumber>"
	                            + "<City>London</City>"
	                            + "<PostalCode>200002</PostalCode>"
	                            + "</Customer>")));
		
		stubFor(put(urlEqualTo("/customers/2")).withHeader("Accept", containing("application/xml"))
				.withRequestBody(matching(
								".*<Customer>.*"
								+ ".*<Id>2</Id>.*"
	                            + ".*<FirstName>John</FirstName>.*"
                        		+ ".*<LastName>Doe</LastName>.*" 
                        		+ ".*<SignedContractDate>09-10-2015</SignedContractDate>.*"
	                            + ".*<Street>Backer street</Street>.*"
	                            + ".*<StreetNumber>3</StreetNumber>.*"
	                            + ".*<City>London</City>.*"
	                            + ".*<PostalCode>200002</PostalCode>.*"
	                            + ".*</Customer>.*"
						))
				.willReturn(
	            aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/xml")
	                .withBody(
	                    TestConstants.XML_HEADER 
	                            + "<Customer>"
	                            + "<Id>2</Id>"
	                            + "<FirstName>John</FirstName>"
	                            + "<LastName>Doe</LastName>"
	                            + "<SignedContractDate>09-10-2015</SignedContractDate>"
	                            + "<Street>Backer street</Street>"
	                            + "<StreetNumber>3</StreetNumber>"
	                            + "<City>London</City>"
	                            + "<PostalCode>200002</PostalCode>"
	                            + "</Customer>")));
		
		stubFor(delete(urlEqualTo("/customers/5")).withHeader("Accept", containing("application/xml"))
				.willReturn(
	            aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/xml")
        ));
	}
}
