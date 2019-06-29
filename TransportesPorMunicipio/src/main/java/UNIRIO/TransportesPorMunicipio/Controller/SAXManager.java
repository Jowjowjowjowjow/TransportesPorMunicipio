package UNIRIO.TransportesPorMunicipio.Controller;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class SAXManager {
	
	private static SAXParserFactory SAXFactory;
	private SAXParser saxParser;
	
	public SAXManager() { 
		try {
			this.saxParser = getInstance().newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SAXParserFactory getInstance() {
		if (SAXFactory == null) {
			return SAXParserFactory.newInstance();
		} else {
			return SAXFactory;
		}
	}

	public SAXParser getSaxParser() {
		return saxParser;
	}
}
