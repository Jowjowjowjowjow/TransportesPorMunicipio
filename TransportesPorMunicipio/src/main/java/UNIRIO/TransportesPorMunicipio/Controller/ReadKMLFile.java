package UNIRIO.TransportesPorMunicipio.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import UNIRIO.TransportesPorMunicipio.Modelos.Coordenada;
import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Modelos.Poligono;

public class ReadKMLFile {

	private static Municipio municipio = new Municipio();
	private static ArrayList<Municipio> municipios = new ArrayList<Municipio>();
	private static Poligono poligono = new Poligono();
	private static List<String> lines = new ArrayList<String>();
	static boolean placemarkTag = false;
	
	public static ArrayList<Municipio> carregaMunicipios() {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			Path file2 = Paths.get("C:\\xml\\oi.txt");
			DefaultHandler handler = new DefaultHandler() {

				boolean nomeMunicipioTag = false;
				boolean codigoMunicipioTag = false;
				boolean coordenadasTag = false;
				StringBuffer valorDaTag;
				/* Executada em toda abertura de tag */
				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
					valorDaTag = new StringBuffer();
					/*
					 * Flag para a tag de SimpleData e diferenciar nome do município do código do
					 * município
					 */
					if (qName.equalsIgnoreCase("SimpleData")) {
						if (attributes.getValue("name").equalsIgnoreCase("NM_MUNICIP")) {
							nomeMunicipioTag = true;
						} else if (attributes.getValue("name").equalsIgnoreCase("CD_GEOCMU")) {
							codigoMunicipioTag = true;
						}
					}
					/* Flag para a tag de coordenadasTag */
					if (qName.equalsIgnoreCase("coordinates")) {
						coordenadasTag = true;
					}

					if (qName.equalsIgnoreCase("Placemark")) {
						municipio = new Municipio();
						placemarkTag = true;
					}

				}

				/* Responsável por pegar os valores da tag */
				public void characters(char ch[], int start, int length) throws SAXException {
					valorDaTag.append(new String(ch, start, length).trim());
				}

				/* Executada no final de cada tag */
				public void endElement(String uri, String localName, String qName) throws SAXException {

					if (nomeMunicipioTag) {
						// System.out.println("Nome do município: " + valorDaTag.toString());
						lines.add("Nome do município: " + valorDaTag.toString());
						municipio.setNome(valorDaTag.toString());
						nomeMunicipioTag = false;
					}

					if (codigoMunicipioTag) {
						// System.out.println("Codigo do município:" + valorDaTag.toString());
						lines.add("Codigo do município: " + valorDaTag.toString());
						municipio.setCodigoIBGE(Integer.parseInt(valorDaTag.toString()));
						codigoMunicipioTag = false;
					}

					if (coordenadasTag) {
						String[] tempcoordenadasTag;
						// System.out.println("coordenadasTag : ");
						/* Dividindo a string de coordenadasTag utilizando ',0' como separador */
						//lines.add("Coordenadas: " + valorDaTag.toString());
						tempcoordenadasTag = valorDaTag.toString().trim().split(",0");
						for (int i = 0; i < tempcoordenadasTag.length; i++) {
							/*
							 * Dividindo novamente a string de coordenadasTag, dessa vez utilizando ',' como
							 * separador
							 */
							String[] tempcoordenadasTag2 = tempcoordenadasTag[i].trim().split(",");
							// System.out.println("X: " +tempcoordenadasTag2[0] + " Y: "
							// +tempcoordenadasTag2[1]);
							lines.add("X: " + tempcoordenadasTag2[0] + " Y: "+ tempcoordenadasTag2[1]);
							Coordenada coordenada = new Coordenada(Double.parseDouble(tempcoordenadasTag2[0]),
									Double.parseDouble(tempcoordenadasTag2[1]));
							poligono.addCoordenada(coordenada);
						}
						/*O fim de cada tag de coordenada também é o fim de um polígono */
						municipio.addPoligono(poligono);
						poligono = new Poligono();
						coordenadasTag = false;	
					}
					
					if(placemarkTag) {
						if (municipio != null) {
							municipios.add(municipio);
						}
						placemarkTag = false;
					}
				}
			};

			//JFileChooser file = new JFileChooser();
			//file.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//file.showSaveDialog(null);
			 File file = new File("c:/xml/municipiosrj.kml");
			//InputStream inputStream = new FileInputStream(file.getSelectedFile());
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			saxParser.parse(is, handler);
			Files.write(file2, lines, Charset.forName("UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return municipios;
	}

}