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

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

class Marshal {

    static String toXml (Object modelObject, Class modelClass, String namespaceURI) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(modelClass);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter writer = new StringWriter();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        QName qName = new QName(namespaceURI, modelClass.getSimpleName());
        JAXBElement<Object> root = new JAXBElement<Object>(qName, modelClass, modelObject);
        jaxbMarshaller.marshal(root, writer);

        return writer.toString();
    }

    static Object toObject (String input, Class modelClass) throws JAXBException, XMLStreamException, JSONException {
        if (input == null || input.isEmpty()) {
            return new Object();
        }
        if (!input.startsWith("<")) {
        	JSONObject json = new JSONObject(input);
        	input = XML.toString(json);
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(modelClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return unmarshaller.unmarshal(new StreamSource(new StringReader(input)), modelClass).getValue();
    }
	
	static String toJson (Object modelObject, Class modelClass, String namespaceURI) throws JAXBException, JSONException {
		String xml = toXml(modelObject, modelClass, namespaceURI);
		JSONObject json = XML.toJSONObject(xml);
        return json.toString();
	}

}
