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
import UNIRIO.TransportesPorMunicipio.Modelos.Poligono;

public class LeitorDeArquivosKML {

	private static Municipio municipio = new Municipio();
	private static ArrayList<Municipio> municipios = new ArrayList<Municipio>();
	private static Poligono poligono = new Poligono();
	
	public static ArrayList<Municipio> carregaMunicipios() {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				boolean tagPlacemark = false;
				boolean tagNomeDoMunicipio = false;
				boolean tagCodigoMunicipio = false;
				boolean tagCoordenadas = false;
				StringBuffer valorDaTag;

				/* Bloco executado em todo início de tag */
				public void startElement(String uri, String localName, String nomeDaTag, Attributes attributos)
						throws SAXException {
					valorDaTag = new StringBuffer();

					/*
					 * Flag para a tag de SimpleData e diferenciar nome do município do código do
					 * município
					 */
					if (nomeDaTag.equalsIgnoreCase("SimpleData")) {
						if (attributos.getValue("name").equalsIgnoreCase("NM_MUNICIP")) {
							tagNomeDoMunicipio = true;
						} else if (attributos.getValue("name").equalsIgnoreCase("CD_GEOCMU")) {
							tagCodigoMunicipio = true;
						}
					}
					/* Flag para a tag de coordenada */
					if (nomeDaTag.equalsIgnoreCase("coordinates")) {
						tagCoordenadas = true;
					}

					/* Flag para a tag de placemark*/
					if (nomeDaTag.equalsIgnoreCase("Placemark")) {
						municipio = new Municipio();
						tagPlacemark = true;
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

				/* Bloco executado ao final de cada tag */
				public void endElement(String uri, String localName, String nomeDaTag) throws SAXException {

					if (tagNomeDoMunicipio) {
						// System.out.println("Nome do município: " + valorDaTag.toString());
						municipio.setNome(valorDaTag.toString());
						tagNomeDoMunicipio = false;
					}

					if (tagCodigoMunicipio) {
						// System.out.println("Codigo do município:" + valorDaTag.toString());
						municipio.setCodigoIBGE(Integer.parseInt(valorDaTag.toString()));
						tagCodigoMunicipio = false;
					}

					if (tagCoordenadas) {
						String[] listaDeCoordenadas;
						/* Dividindo a string de coordenadasTag utilizando ',0' como separador */
						listaDeCoordenadas = valorDaTag.toString().trim().split(",0");
						for (int i = 0; i < listaDeCoordenadas.length; i++) {
							
							/*
							 * Dividindo uma única coordenada em Longitude e Latitude, CoordenadaXY[0] = Longitude e
							 * CoordenadaXY[1] = Latitude
							 */
							String[] CoordenadaXY = listaDeCoordenadas[i].trim().split(",");
							Coordenada coordenada = new Coordenada(Double.parseDouble(CoordenadaXY[0]),
									Double.parseDouble(CoordenadaXY[1]));
							poligono.addCoordenada(coordenada);
						}
						/* O fim de cada tag de coordenada também é o fim de um polígono */
						municipio.addPoligono(poligono);
						poligono = new Poligono();
						tagCoordenadas = false;
					}

					if (tagPlacemark) {
						if (municipio != null) {
							municipios.add(municipio);
						}
						tagPlacemark = false;
					}
				}
			};

			
			File kmlDeEntrada = new File("D:\\municipiosrj.kml");
			InputStream inputStream = new FileInputStream(kmlDeEntrada);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			saxParser.parse(is, handler);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return municipios;
	}

}