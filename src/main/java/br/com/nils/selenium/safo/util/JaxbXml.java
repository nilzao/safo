package br.com.nils.selenium.safo.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class JaxbXml {

	public static void saveObject(Serializable object, String filename) throws JAXBException, IOException {
		saveObjWithoutXmlRootAnnotation(object, filename);
	}

	@SuppressWarnings("unchecked")
	public static void saveObjWithoutXmlRootAnnotation(Object object, String filename) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			QName qname = new QName("", object.getClass().getName());
			StringWriter stringWriter = new StringWriter();
			JAXBElement<Object> jaxbElement = new JAXBElement<Object>(qname, (Class<Object>) object.getClass(), null, object);
			jaxbMarshaller.marshal(jaxbElement, stringWriter);
			Files.write(Paths.get(filename), stringWriter.toString().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Serializable loadObject(String filename) throws IOException, JAXBException, XMLStreamException, FactoryConfigurationError, ClassNotFoundException {
		String xmlStr = new String(Files.readAllBytes(Paths.get(filename)));
		return loadObjectStrXml(xmlStr);
	}

	public static Serializable loadObjectStrXml(String xmlStr) {
		Object object = null;
		try {
			Document xmlToDocument = xmlToDocument(xmlStr);
			String className = xmlToDocument.getDocumentElement().getNodeName();
			Class<?> classz = Class.forName(className);
			JAXBContext jaxbContext = JAXBContext.newInstance(classz);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xmlStr);
			XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(reader);
			XMLReaderWithoutNamespace xr = new XMLReaderWithoutNamespace(xsr);
			object = jaxbUnmarshaller.unmarshal(xr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Serializable) object;
	}

	private static Document xmlToDocument(String stringXml) throws ParserConfigurationException, SAXException, IOException {
		Document doc = null;
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(stringXml));
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(is);
		return doc;
	}

}
