package UNIRIO.TransportesPorMunicipio.Util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public interface ISAXHandler  {
	
	/** Bloco executado em todo início de tag */
	public void startElement(String uri, String localName, String nomeDaTag, Attributes attributos) throws SAXException;

	public void endElement(String uri, String localName, String nomeDaTag) throws SAXException;
	
	public void characters(char ch[], int start, int length) throws SAXException;
	
}
