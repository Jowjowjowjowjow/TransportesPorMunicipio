package UNIRIO.TransportesPorMunicipio.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import UNIRIO.TransportesPorMunicipio.Modelos.Coordenada;
import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;
import UNIRIO.TransportesPorMunicipio.Modelos.Poligono;

public class LeitorDeArquivosKML {

	private ArrayList<Municipio> municipios = new ArrayList<Municipio>();
	IFileWriter kmlWriter;
	SAXParser saxParser;
	StringBuffer valorDaTag = new StringBuffer();
	
	public LeitorDeArquivosKML(SAXParser saxParser, IFileWriter kmlWriter) {
		this.saxParser = saxParser;
		this.kmlWriter = kmlWriter;
	}
	
	public void carregaMunicipios() {
		try {
			DefaultHandler handler = new DefaultHandler() {
				Municipio municipio = new Municipio();
				StringBuffer valorDaTag;
				/* Bloco executado em todo início de tag */
				public void startElement(String uri, String localName, String nomeDaTag, Attributes attributos)	throws SAXException {
					valorDaTag = new StringBuffer();
					/*
					 * Flag para a tag de SimpleData e diferenciar nome do município do código do
					 * município
					 */
					if (nomeDaTag.equalsIgnoreCase("Placemark")) {
						if ( (attributos.getValue("name").equalsIgnoreCase("NM_MUNICIP") == true) && (attributos.getValue("name").equalsIgnoreCase("CD_GEOCMU") == true) ){
							kmlWriter.addLineKML(municipio);
							municipios.add(municipio);
						}
						municipio = new Municipio();						
					}
					
					if(nomeDaTag.equalsIgnoreCase("SimpleData")) {
						municipio = atributosMunicipios(attributos, nomeDaTag, municipio);
					}
				}

				/*
				 * Responsável por pegar os valores entre as tags. Utiliza StringBuffer e sua
				 * função append porque pode ser chamado diversas vezes para um mesmo valor de
				 * tag caso o mesmo seja muito grande, o que ocorre na tag Coordinates
				 */
				public void characters(char ch[], int start, int length) throws SAXException {
					valorDaTag.append(new String(ch, start, length).trim());
				}
				
				
			};
			File kmlDeEntrada = new File("C:\\xml\\municipiosrj.kml");
			InputStream inputStream = new FileInputStream(kmlDeEntrada);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			saxParser.parse(is, handler);			
			kmlWriter.writeFile();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Municipio atributosMunicipios(Attributes attributos, String nomeDaTag, Municipio municipio) {
		Poligono poligono = new Poligono();
		if (nomeDaTag.equalsIgnoreCase("SimpleData")) {
			if ( (attributos.getValue("name").equalsIgnoreCase("NM_MUNICIP") == true) && (attributos.getValue("name").equalsIgnoreCase("CD_GEOCMU") == true) ){
				municipio.setNome(valorDaTag.toString());
				municipio.setCodigoIBGE(Integer.parseInt(valorDaTag.toString()));
			}
		}
		/* Flag para a tag de coordenada */
		if (nomeDaTag.equalsIgnoreCase("coordinates")) {
			String[] listaDeCoordenadas;
			/* Dividindo a string de coordenadasTag utilizando ',0' como separador */
			listaDeCoordenadas = valorDaTag.toString().trim().split(",0");
			for (int i = 0; i < listaDeCoordenadas.length; i++) {								
				/*
				 * Dividindo uma única coordenada em Longitude e Latitude, CoordenadaXY[0] = Longitude e
				 * CoordenadaXY[1] = Latitude
				 */
				String[] CoordenadaXY = listaDeCoordenadas[i].trim().split(",");
				Coordenada coordenada = new Coordenada(Double.parseDouble(CoordenadaXY[0]),	Double.parseDouble(CoordenadaXY[1]));
				poligono.addCoordenada(coordenada);
			}
			/* O fim de cada tag de coordenada também é o fim de um polígono */
			municipio.addPoligono(poligono);
			poligono = new Poligono();
		}
		
		return municipio;
	}
	 
}