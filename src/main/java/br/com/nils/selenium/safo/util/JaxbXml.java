package br.com.nils.selenium.safo.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class JaxbXml {

	public static void saveObject(Serializable object, String filename) throws JAXBException, IOException {
		StringWriter stringWriter = new StringWriter();

		JAXBContext jc = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		marshaller.marshal(object, stringWriter);
		Files.write(Paths.get(filename), stringWriter.toString().getBytes());
	}

	public static Serializable loadObject(String filename)
			throws IOException, JAXBException, XMLStreamException, FactoryConfigurationError, ClassNotFoundException {
		String xmlStr = new String(Files.readAllBytes(Paths.get(filename)));
		int indexOf = xmlStr.indexOf("\n") - 1;
		String className = xmlStr.substring(1, indexOf);
		Class<?> classz = Class.forName(className);
		StringReader reader = new StringReader(xmlStr);
		JAXBContext jaxbContext = JAXBContext.newInstance(classz);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(reader);
		XMLReaderWithoutNamespace xr = new XMLReaderWithoutNamespace(xsr);
		Object object = jaxbUnmarshaller.unmarshal(xr);
		return (Serializable) object;
	}

}
