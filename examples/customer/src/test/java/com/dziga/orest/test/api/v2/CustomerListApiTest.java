package com.dziga.orest.test.api.v2;

import com.dziga.customer.api.v2.CustomerApi;
import com.dziga.orest.test.TestConstants;
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
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class CustomerListApiTest {
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089).notifier(
	        new Slf4jNotifier(true)));

	@Test
	public void customerApiGet() throws KeyManagementException, InvalidKeyException, NoSuchAlgorithmException, JAXBException, URISyntaxException, IOException, XMLStreamException, JSONException {

        CustomerApi customer = new CustomerApi();

        customer.getCustomers();

        customer.getCustomerFromList(0);

        Assert.assertEquals(1, customer.getCustomerId());
        Assert.assertEquals("John", customer.getCustomerFirstName());
        Assert.assertEquals("Doe", customer.getCustomerLastName());
        Assert.assertEquals("09-10-2015", customer.getCustomerSignedContractDate());
        Assert.assertEquals("Backer street", customer.getCustomerStreet());
        Assert.assertEquals(3, customer.getCustomerStreetNumber());
        Assert.assertEquals("London", customer.getCustomerCity());
        Assert.assertEquals(Integer.valueOf(200002), customer.getCustomerPostalCode());

        customer.getCustomerFromList(1);

        Assert.assertEquals(2, customer.getCustomerId());
        Assert.assertEquals("Janny", customer.getCustomerFirstName());
        Assert.assertEquals("Jane", customer.getCustomerLastName());
        Assert.assertEquals("09-09-2015", customer.getCustomerSignedContractDate());
        Assert.assertEquals("London street", customer.getCustomerStreet());
        Assert.assertEquals(5, customer.getCustomerStreetNumber());
        Assert.assertEquals("London", customer.getCustomerCity());
        Assert.assertEquals(Integer.valueOf(22), customer.getCustomerPostalCode());
	}
	
	@Before
	public void setUp() {
		
		stubFor(get(urlEqualTo("/v2/customers")).withHeader("Accept", containing("application/xml"))
				.willReturn(
	            aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/xml")
	                .withBody(
	                    TestConstants.XML_HEADER
                                + "<CustomerList>"
                                + "<Customer>"
                                + "<Id>1</Id>"
                                + "<FirstName>John</FirstName>"
                                + "<LastName>Doe</LastName>"
                                + "<SignedContractDate>09-10-2015</SignedContractDate>"
                                + "<Address>"
                                + "<Street>Backer street</Street>"
                                + "<StreetNumber>3</StreetNumber>"
                                + "<City>London</City>"
                                + "<PostalCode>200002</PostalCode>"
                                + "</Address>"
                                + "</Customer>"
	                            + "<Customer>"
	                            + "<Id>2</Id>"
	                            + "<FirstName>Janny</FirstName>"
	                            + "<LastName>Jane</LastName>"
	                            + "<SignedContractDate>09-09-2015</SignedContractDate>"
                                + "<Address>"
	                            + "<Street>London street</Street>"
	                            + "<StreetNumber>5</StreetNumber>"
	                            + "<City>London</City>"
	                            + "<PostalCode>22</PostalCode>"
                                + "</Address>"
                                + "</Customer>"
	                            + "</CustomerList>")));
	}
}
