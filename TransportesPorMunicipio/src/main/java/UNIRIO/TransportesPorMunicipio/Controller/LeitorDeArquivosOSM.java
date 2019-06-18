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

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;
import UNIRIO.TransportesPorMunicipio.Modelos.TipoNo;

public class LeitorDeArquivosOSM {
	private static NoStreetMap noStreetMap = new NoStreetMap();
	private static ArrayList<NoStreetMap> nosStreetMap = new ArrayList<NoStreetMap>();
	private static List<String> linhasArquivoResultante = new ArrayList<String>();
	static boolean tagWay = false;
	static String nome = "";
	public static ArrayList<NoStreetMap> carregaLocais() {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			Path ArquivoDestino = Paths.get("c:\\xml\\ResultadoFiltragemOSM.txt");
			DefaultHandler handler = new DefaultHandler() {

				//boolean tagNomeDoMunicipio = false;
				//boolean codigoMunicipioTag = false;
				//boolean tagCoordenadas = false;
				boolean tagNome = false;
				boolean tagAeroporto = false;
				StringBuffer valorDaTag;
				
				/* Bloco executado em todo início de tag */
				public void startElement(String uri, String localName, String nomeDaTag, Attributes atributos)
						throws SAXException {
					valorDaTag = new StringBuffer();
					/* Flag para a tag de coordenada */
					if (nomeDaTag.equalsIgnoreCase("way") || nomeDaTag.equalsIgnoreCase("relation")) {
						if (noStreetMap.getNome() != null && noStreetMap.getTipo() != null && !existeNo(noStreetMap)) {
							
							linhasArquivoResultante.add("Nome: " + noStreetMap.getNome());
							linhasArquivoResultante.add("Tipo: " + noStreetMap.getTipo());
							nosStreetMap.add(noStreetMap);
						}
						noStreetMap = new NoStreetMap();
						tagWay = true;
					}
					
					if (nomeDaTag.equalsIgnoreCase("tag")) {
						if (atributos.getValue("k").equalsIgnoreCase("aeroway") && atributos.getValue("v").equalsIgnoreCase("aerodrome")) {
							System.out.println("Aeroporto detectado");
							noStreetMap.setTipo(TipoNo.AEROPORTO);
							//tagAeroporto = true;
						}else if (atributos.getValue("k").equalsIgnoreCase("landuse") && atributos.getValue("v").equalsIgnoreCase("harbour")) {
							System.out.println("Porto detectado");
							noStreetMap.setTipo(TipoNo.PORTO);
						}else if (atributos.getValue("k").equalsIgnoreCase("Highway") && atributos.getValue("v").equalsIgnoreCase("primary") ) {
							System.out.println("Rodovia detectada");
							noStreetMap.setTipo(TipoNo.RODOVIA);	
						}else if (atributos.getValue("k").equalsIgnoreCase("railway") && atributos.getValue("v").equalsIgnoreCase("rail") ) {
							System.out.println("Ferrovia detectada");
							noStreetMap.setTipo(TipoNo.FERROVIA);
						}
						
						if (atributos.getValue("k").equalsIgnoreCase("name")) {
							//System.out.println("Nome detectado");
							noStreetMap.setNome(atributos.getValue("v"));
							tagNome = true;
						}
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

					if (tagNome) {
						// System.out.println("Nome do município: " + valorDaTag.toString());
						//linhasArquivoResultante.add("Nome do município: " + valorDaTag.toString());
						tagNome = false;
					}

					if (tagAeroporto) {
						// System.out.println("Codigo do município:" + valorDaTag.toString());
						//linhasArquivoResultante.add("Codigo do município: " + valorDaTag.toString());
						tagAeroporto = false;
					}
					
					if(tagWay) {
						tagWay = false;
					}
				}
			};

			//JFileChooser file = new JFileChooser();
			//file.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//file.showSaveDialog(null);
			 File osmDeEntrada = new File("c://xml//municipio.osm");
			//InputStream inputStream = new FileInputStream(file.getSelectedFile());
			InputStream inputStream = new FileInputStream(osmDeEntrada);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			saxParser.parse(is, handler);
			Files.write(ArquivoDestino, linhasArquivoResultante, Charset.forName("UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return nosStreetMap;
	}
	public static void escreveArquivo(String destino, List<String> conteudo) {
		Path ArquivoDestino = Paths.get(destino);
		try {
			Files.write(ArquivoDestino, conteudo, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean existeNo(NoStreetMap noStreetMap) {
		boolean achou = false;
		for(NoStreetMap no: nosStreetMap) {
			if(no.getNome().equalsIgnoreCase(noStreetMap.getNome()) && no.getTipo()==noStreetMap.getTipo()) {
				achou = true;
			}
		}
		return achou;
	}

}
