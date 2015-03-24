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
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

class HttpRestClient {
	private HttpClient httpClient = HttpClientBuilder.create().build();
	private List<Header> headers = new ArrayList<Header>();
    private Map<String, String> queryParams = new HashMap<String, String>();
	private String scheme = "http";
	private String host;
	private String path;
	private String responseBody;
    private HttpResponse lastResponse;


	HttpRestClient(String host, String path) {
		this.host = host;
		this.path = path;
	}
	
	HttpRestClient(String host) {
		this.host = host;
	}
	
	void setRequestPath(String path) {
		this.path = path.trim();
	}

	int Get() throws IOException, URISyntaxException,
			NoSuchAlgorithmException, InvalidKeyException {
		URIBuilder b = new URIBuilder().setScheme(scheme).setHost(host).setPath(path);
		addQueryParams(b);
		URI fullUri = b.build();
		HttpGet getMethod = new HttpGet(fullUri);
		getMethod = (HttpGet) addHeadersToMethod(getMethod);

		processResponse(httpClient.execute(getMethod));
		return getResponseCode();
	}

	int Post(String body) throws URISyntaxException, IOException {
		URIBuilder b = new URIBuilder().setScheme(scheme).setHost(host).setPath(path);
        addQueryParams(b);
        URI fullUri = b.build();
        HttpPost postMethod = new HttpPost(fullUri);
        HttpEntity entity = new StringEntity(body);
        postMethod.setEntity(entity);
        postMethod = (HttpPost) addHeadersToMethod(postMethod);

        processResponse(httpClient.execute(postMethod));

        return getResponseCode();
	}

	int Put(String body) throws IOException, URISyntaxException {
		URIBuilder b = new URIBuilder().setScheme(scheme).setHost(host).setPath(path);
        addQueryParams(b);
        URI fullUri = b.build();
        HttpPut putMethod = new HttpPut(fullUri);
        HttpEntity entity = new StringEntity(body);
        putMethod.setEntity(entity);
        putMethod = (HttpPut) addHeadersToMethod(putMethod);

        processResponse(httpClient.execute(putMethod));

        return getResponseCode();
	}

	int Delete() throws IOException, URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder().setScheme(scheme).setHost(host).setPath(path);
		addQueryParams(uriBuilder);
		URI fullUri = uriBuilder.build();
		HttpDelete deleteMethod = new HttpDelete(fullUri);
		deleteMethod = (HttpDelete) addHeadersToMethod(deleteMethod);
		processResponse(httpClient.execute(deleteMethod));

		return getResponseCode();
	}

	void addHeader(String headerName, String headerValue) {
		Header foundHeader = null;
		for (Header header : headers) {
			if (StringUtils.equalsIgnoreCase(header.getName(), headerName)) {
				foundHeader = header;
				break;
			}
		}
		if (foundHeader != null) {
			headers.remove(foundHeader);
		}
		headers.add(new BasicHeader(headerName, headerValue));
	}
	
	void addQueryParameter(String key, String value) {
		queryParams.put(key, value);
	}
	
	void removeAllQueryParameters() {
		queryParams.clear();
	}

	String getResponseBody() {
		return responseBody;
	}

    boolean containsHeader(String name) {
        return lastResponse.containsHeader(name);
    }

    List<String> getResponseHeaderValues(String name) {
        List<String> values = new ArrayList<String>();
        for (Header header: lastResponse.getHeaders(name)) {
            values.add(header.getValue());
        }
        return values;
    }

    Map<String, String> getResponseHeaders() {
        HashMap<String, String> pair = new HashMap<String, String>();
        for (Header header: lastResponse.getAllHeaders()) {
            pair.put(header.getName(), header.getValue());
        }
        return pair;
    }

	private HttpRequestBase addHeadersToMethod(HttpRequestBase method) {
		for (Header h : headers) {
			method.addHeader(h.getName(), h.getValue());
		}
		return method;
	}

	private void addQueryParams(URIBuilder b) {
		for (Map.Entry<String, String> param : queryParams.entrySet()) {
			b.addParameter(param.getKey(), param.getValue());
		}
	}
	
	private void processResponse(HttpResponse response) throws IOException {
        lastResponse = response;
        HttpEntity entity = response.getEntity();
        responseBody = EntityUtils.toString(entity);
    }

    private int getResponseCode() {
        return lastResponse.getStatusLine().getStatusCode();
    }

}
