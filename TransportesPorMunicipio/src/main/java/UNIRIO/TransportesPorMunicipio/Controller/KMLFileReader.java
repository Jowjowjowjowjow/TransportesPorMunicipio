package UNIRIO.TransportesPorMunicipio.Controller;

import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import UNIRIO.TransportesPorMunicipio.Modelos.Coordenada;
import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Modelos.Poligono;
import UNIRIO.TransportesPorMunicipio.Modelos.Tag;
import UNIRIO.TransportesPorMunicipio.Util.ISAXHandler;

public class KMLFileReader extends DefaultHandler implements ISAXHandler {

	private ArrayList<Municipio> counties = new ArrayList<Municipio>();
	private Poligono poligon = new Poligono();
	private Municipio county;

	private boolean placemarkTag = false;
	private boolean countyNameTag = false;
	private boolean countyCodeTag = false;
	private boolean coordinateTag = false;

	private StringBuffer tagValue;
	
	private SAXParser saxParser;
	private InputSource inputSource;
	
	
	public KMLFileReader(SAXParser saxParser, InputSource inputSource) {
		this.saxParser = saxParser;
		this.inputSource = inputSource;
	}

	public ArrayList<Municipio> loadCounties() {
		try {
			saxParser.parse(inputSource, this);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counties;
	}
	
	@Override
	public void startElement(String uri, String localName, String tagName, Attributes attributos) throws SAXException {
		tagValue = new StringBuffer();
		setCurrentFlag(tagName, attributos);
	}
	

	private void setCurrentFlag(String nomeDaTag, Attributes attributos) {
		/** Flag para a tag de SimpleData e diferenciar nome do município do código do município */
		if (nomeDaTag.equalsIgnoreCase(Tag.SIMPLE_DATA.value)) {
			if (attributos.getValue("name").equalsIgnoreCase(Tag.COUNTY_NAME.value)) {
				countyNameTag = true;
			} else if (attributos.getValue("name").equalsIgnoreCase(Tag.COUNTY_CODE.value)) {
				countyCodeTag = true;
			}
		}
		/** Flag para a tag de coordenada */
		if (nomeDaTag.equalsIgnoreCase(Tag.COORDINATES.value)) {
			coordinateTag = true;
		}

		/** Flag para a tag de placemark */
		if (nomeDaTag.equalsIgnoreCase(Tag.PLACEMARK.value)) {
			county = new Municipio();
			placemarkTag = true;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		tagValue.append(new String(ch, start, length).trim());
	}

	@Override
	public void endElement(String uri, String localName, String nomeDaTag) throws SAXException {

		if (countyNameTag) {
			county.setNome(tagValue.toString());
			countyNameTag = false;
		}

		if (countyCodeTag) {
			county.setCodigoIBGE(Integer.parseInt(tagValue.toString()));
			countyCodeTag = false;
		}

		if (coordinateTag) 
			setCoordinatesList(tagValue);

		if (placemarkTag) {
			if (county != null) {
				counties.add(county);
			}
			placemarkTag = false;
		}
	}

	private void setCoordinatesList(StringBuffer tagValue) {
		String[] listaDeCoordenadas;
		/* Dividindo a string de coordenadasTag utilizando ',0' como separador */
		listaDeCoordenadas = tagValue.toString().trim().split(",0 ");
		for (int i = 0; i < listaDeCoordenadas.length; i++) {
			/*
			 * Dividindo uma única coordenada em Longitude e Latitude, CoordenadaXY[0] = Longitude e
			 * CoordenadaXY[1] = Latitude
			 */
			String[] CoordenadaXY = listaDeCoordenadas[i].trim().split(",");
			Coordenada coordenada = new Coordenada(Double.parseDouble(CoordenadaXY[0]),
												   Double.parseDouble(CoordenadaXY[1]));
			poligon.addCoordenada(coordenada);
		}
		/* O fim de cada tag de coordenada também é o fim de um polígono */
		county.addPoligono(poligon);
		poligon = new Poligono();
		coordinateTag = false;
	}

}