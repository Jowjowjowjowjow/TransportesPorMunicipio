package UNIRIO.TransportesPorMunicipio.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

public class LeitorDeArquivosKML {

	private static Municipio municipio = new Municipio();
	private static ArrayList<Municipio> municipios = new ArrayList<Municipio>();
	private static Poligono poligono = new Poligono();
	private static List<String> linhasArquivoResultante = new ArrayList<String>();
	static boolean tagPlacemark = false;
	
	public static ArrayList<Municipio> carregaMunicipios() {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			Path ArquivoDestino = Paths.get("d:\\Resultado.txt");
			DefaultHandler handler = new DefaultHandler() {

				boolean tagNomeDoMunicipio = false;
				boolean codigoMunicipioTag = false;
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
							codigoMunicipioTag = true;
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

				/* Responsável por pegar os valores da tag 
				 * Utiliza StringBuffer e sua função append porque pode ser chamado diversas vezes
				 * para um mesmo valor de tag caso o mesmo seja muito grande*/
				public void characters(char ch[], int start, int length) throws SAXException {
					valorDaTag.append(new String(ch, start, length).trim());
				}

				/* Bloco executado ao final de cada tag */
				public void endElement(String uri, String localName, String nomeDaTag) throws SAXException {

					if (tagNomeDoMunicipio) {
						// System.out.println("Nome do município: " + valorDaTag.toString());
						linhasArquivoResultante.add("Nome do município: " + valorDaTag.toString());
						municipio.setNome(valorDaTag.toString());
						tagNomeDoMunicipio = false;
					}

					if (codigoMunicipioTag) {
						// System.out.println("Codigo do município:" + valorDaTag.toString());
						linhasArquivoResultante.add("Codigo do município: " + valorDaTag.toString());
						municipio.setCodigoIBGE(Integer.parseInt(valorDaTag.toString()));
						codigoMunicipioTag = false;
					}

					if (tagCoordenadas) {
						String[] listaDeCoordenadaXYs;
						/* Transformando uma unica string com diversas coordenadas em um array de strings, cada um 
						 * com uma coordenada diferente */
						//linhasArquivoResultante.add("CoordenadaXYs: " + valorDaTag.toString());
						listaDeCoordenadaXYs = valorDaTag.toString().trim().split(",0 ");
						for (int i = 0; i < listaDeCoordenadaXYs.length; i++) {
							/*
							 * Dividindo uma única coordenada em X e Y, CoordenadaXY[0] = X e CoordenadaXY[1] = Y
							 */
							String[] CoordenadaXY = listaDeCoordenadaXYs[i].trim().split(",");
							linhasArquivoResultante.add("X: " + CoordenadaXY[0] + " Y: "+ CoordenadaXY[1]);
							Coordenada coordenada = new Coordenada(Double.parseDouble(CoordenadaXY[0]),
									Double.parseDouble(CoordenadaXY[1]));
							poligono.addCoordenada(coordenada);
						}
						/*O fim de cada tag de coordenada também é o fim de um polígono */
						municipio.addPoligono(poligono);
						poligono = new Poligono();
						tagCoordenadas = false;	
					}
					
					if(tagPlacemark) {
						if (municipio != null) {
							municipios.add(municipio);
						}
						tagPlacemark = false;
					}
				}
			};

			//JFileChooser file = new JFileChooser();
			//file.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//file.showSaveDialog(null);
			 File kmlDeEntrada = new File("d://municipiossp.kml");
			//InputStream inputStream = new FileInputStream(file.getSelectedFile());
			InputStream inputStream = new FileInputStream(kmlDeEntrada);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			saxParser.parse(is, handler);
			Files.write(ArquivoDestino, linhasArquivoResultante, Charset.forName("UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return municipios;
	}
	public static void escreveArquivo(String destino, List<String> conteudo) {
		Path ArquivoDestino = Paths.get(destino);
		try {
			Files.write(ArquivoDestino, conteudo, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}