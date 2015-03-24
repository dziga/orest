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

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SuppressWarnings("unused")
public class ClientJsonTest {
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089).notifier(
	        new Slf4jNotifier(true)));

	@Before
	public void setUp() {
		stubFor(post(urlEqualTo("/customers")).withHeader("Accept", containing("application/json"))
				.withRequestBody(matchingJsonPath("$.Customer"))
			    .withRequestBody(matchingJsonPath("$..FirstName"))
			    .withRequestBody(matchingJsonPath("$..LastName"))
			    .withRequestBody(matchingJsonPath("$..SignedContractDate"))
			    .withRequestBody(matchingJsonPath("$..Street"))
			    .withRequestBody(matchingJsonPath("$..StreetNumber"))
			    .withRequestBody(matchingJsonPath("$..City"))
			    .withRequestBody(matchingJsonPath("$..PostalCode"))
				.willReturn(
	            aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/json")
	                .withBody(
							"{\"Customer\": " +
							"{\"Id\": \"0\"," +
							"\"FirstName\":\"John\"," +
							"\"LastName\":\"Doe\"," +
							"\"SignedContractDate\": \"09-10-2015\"," +
							"\"Street\":\"Backer street\"," +
							"\"StreetNumber\": \"3\"," +
							"\"City\":\"London\"," +
							"\"PostalCode\":\"20002\"}}"
							)));
	}
	
	@Test
	public void customerApiPost() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {

        ObjectRestClient objectRestClient = new ObjectRestClient(RestEndpoints.HOST);
        objectRestClient.addHeader("Content-Type", "application/xml, application/json");
        objectRestClient.addHeader("Accept", "application/xml, application/json");
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setSignedContractDate("09-10-2015");
        customer.setStreet("Backer street");
        customer.setStreetNumber(3);
        customer.setCity("London");
        customer.setPostalCode(BigInteger.valueOf(20002));

        objectRestClient.setRequestType("json");
        customer = (Customer) objectRestClient.postToService(Customer.class, customer, Customer.class, RestEndpoints.CUSTOMER_LIST);

        Assert.assertEquals("John", customer.getFirstName());
        Assert.assertEquals("Doe", customer.getLastName());
        Assert.assertEquals("09-10-2015", customer.getSignedContractDate());
        Assert.assertEquals("Backer street", customer.getStreet());
        Assert.assertEquals(3, customer.getStreetNumber());
        Assert.assertEquals("London", customer.getCity());
        Assert.assertEquals(BigInteger.valueOf(20002), customer.getPostalCode());
	}
}
