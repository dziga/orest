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

    public static String toXml (Object modelObject, Class modelClass, String namespaceURI) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(modelClass);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter writer = new StringWriter();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        QName qName = new QName(namespaceURI, modelClass.getSimpleName());
        JAXBElement<Object> root = new JAXBElement<Object>(qName, modelClass, modelObject);
        jaxbMarshaller.marshal(root, writer);

        return writer.toString();
    }

    public static Object toObject (String input, Class modelClass) throws JAXBException, XMLStreamException, JSONException {
        if (input == null || input.isEmpty()) {
            return null;
        }
        if (!input.startsWith("<")) {
        	JSONObject json = new JSONObject(input);
        	input = XML.toString(json);
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(modelClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return unmarshaller.unmarshal(new StreamSource(new StringReader(input)), modelClass).getValue();
    }
	
	public static String toJson (Object modelObject, Class modelClass, String namespaceURI) throws JAXBException, JSONException {
		String xml = toXml(modelObject, modelClass, namespaceURI);
		JSONObject json = XML.toJSONObject(xml);
        return json.toString();
	}

}
