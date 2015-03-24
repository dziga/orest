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

package com.github.dziga.orest.test.client;

import com.github.dziga.orest.api.ORest;
import com.github.dziga.orest.client.ObjectRestClient;
import com.github.dziga.orest.test.client.api.RestEndpoints;
import com.github.dziga.orest.test.client.domain.Customer;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SuppressWarnings("unused")
public class ClientTest {

    private ORest objectRestClient;

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089).notifier(
	        new Slf4jNotifier(true)));

	@Test
	public void customerApiGet() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {

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

        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setSignedContractDate("09-10-2015");
        customer.setStreet("Backer street");
        customer.setStreetNumber(3);
        customer.setCity("London");
        customer.setPostalCode(BigInteger.valueOf(200002));
		
        customer = (Customer) objectRestClient.postToService(Customer.class, customer, RestEndpoints.CUSTOMER_LIST);

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
	
        Customer customer = new Customer();
		customer.setId(2);
		customer.setFirstName("John");
		customer.setLastName("Doe");
		customer.setSignedContractDate("09-10-2015");
		customer.setStreet("Backer street");
		customer.setStreetNumber(3);
		customer.setCity("London");
		customer.setPostalCode(BigInteger.valueOf(200002));
		
        customer = (Customer) objectRestClient.putToService(Customer.class, customer, String.format(RestEndpoints.CUSTOMER, customer.getId()));

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

        Customer customer = new Customer();
		customer.setId(5);
		
		Assert.assertTrue(objectRestClient.deleteViaService(String.format(RestEndpoints.CUSTOMER, customer.getId())) == 200);
	}

    @Test
    public void customerHeaders() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {
        List<String> expectedContentTypeHeaders = new ArrayList<String>();
        expectedContentTypeHeaders.add("application/xml");
        Map<String, String> allExpectedHeaders = new HashMap<String, String>();
        allExpectedHeaders.put("Content-Length", "298");
        allExpectedHeaders.put("Content-Type", "application/xml");

        Customer customer = new Customer();
        customer.setId(3);

        customer = (Customer) objectRestClient.getFromService(Customer.class, String.format(RestEndpoints.CUSTOMER, customer.getId()));

        Assert.assertEquals(3, customer.getId());
        Assert.assertTrue(objectRestClient.lastResponseContainsHeader("Content-Type"));
        Assert.assertEquals(expectedContentTypeHeaders, objectRestClient.getLastResponseHeaderValues("Content-Type"));
        Assert.assertEquals(2, allExpectedHeaders.size());
    }

    @Test
    public void responseCodeAndBody() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {

        Customer customer = new Customer();
        customer.setId(3);

        customer = (Customer) objectRestClient.getFromService(Customer.class, String.format(RestEndpoints.CUSTOMER, customer.getId()));

        Assert.assertEquals(3, customer.getId());
        Assert.assertFalse(objectRestClient.getResponseBody().isEmpty());
        Assert.assertEquals(200, objectRestClient.getResponseCode());
    }

    @Test
    public void emptyBody() throws NoSuchAlgorithmException, KeyManagementException, JAXBException, URISyntaxException, XMLStreamException, InvalidKeyException, IOException {
        Customer customer = new Customer();
        customer.setId(3);

        Object empty = objectRestClient.putToService(Customer.class, customer, String.format(RestEndpoints.CUSTOMER, customer.getId()));

        Assert.assertNotNull(customer);
    }

    @Test
    public void paramTest() throws NoSuchAlgorithmException, KeyManagementException, JAXBException, URISyntaxException, XMLStreamException, InvalidKeyException, IOException {
        Customer customer = new Customer();
        customer.setId(3);

        HashMap<String,String> params = new HashMap<String, String>();
        params.put("param", "do");

        customer = (Customer) objectRestClient.getFromService(Customer.class, String.format(RestEndpoints.CUSTOMER, customer.getId()), params);

        Assert.assertEquals(3, customer.getId());
    }
	
	@Before
	public void setUp() {

        objectRestClient = new ObjectRestClient(RestEndpoints.HOST);
        objectRestClient.addHeader("Content-Type", "application/xml, application/json");
        objectRestClient.addHeader("Accept", "application/xml, application/json");

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

        stubFor(get(urlEqualTo("/customers/3?param=do")).withHeader("Accept", containing("application/xml"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/xml")
                                .withBody(
                                        TestConstants.XML_HEADER
                                                + "<Customer>"
                                                + "<Id>3</Id>"
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

        stubFor(put(urlEqualTo("/customers/8")).withHeader("Accept", containing("application/xml"))
            .willReturn(
                    aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/xml")
            ));
	}
}
