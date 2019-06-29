package UNIRIO.TransportesPorMunicipio.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;
import UNIRIO.TransportesPorMunicipio.Modelos.TipoNo;

public class LeitorDeArquivosOSM {
	
	private ArrayList<NoStreetMap> nosStreetMap = new ArrayList<NoStreetMap>();	
	IFileWriter fileWriter;
	SAXParser saxParser;
	
	public LeitorDeArquivosOSM(SAXParser saxParser, IFileWriter fileWriter) {
		this.saxParser = saxParser;
		this.fileWriter = fileWriter;
	}
	
	public void carregaLocais() {
		try {
			DefaultHandler handler = new DefaultHandler() {
				NoStreetMap streetMapNode = new NoStreetMap();				
				/**
				 * Bloco executado em todo início de tag 
				 * */
				public void startElement(String uri, String localName, String nomeDaTag, Attributes atributos) throws SAXException {					
					if (nomeDaTag.equalsIgnoreCase("way") || nomeDaTag.equalsIgnoreCase("relation")) {
						if (streetMapNode.getNome() != null && streetMapNode.getTipo() != null && !nodeNotExistsOnList(streetMapNode)) {
							fileWriter.addLine(streetMapNode);
							nosStreetMap.add(streetMapNode);
						}
						streetMapNode = new NoStreetMap();
					}
					
					if (nomeDaTag.equalsIgnoreCase("tag")) {	
						streetMapNode = nodeWithAttributes(atributos, streetMapNode);
					}
				}
			};

			File osmDeEntrada = new File("c://xml//municipio.osm");
			InputStream inputStream = new FileInputStream(osmDeEntrada);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			saxParser.parse(is, handler);
			
			fileWriter.writeFile();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	private boolean nodeNotExistsOnList(NoStreetMap streeMapNode) {
		for(NoStreetMap node: nosStreetMap) {
			if(node.getNome().equalsIgnoreCase(streeMapNode.getNome()) && node.getTipo() == streeMapNode.getTipo()) {
				return true;
			}
		}
		return false;
	}

	private NoStreetMap nodeWithAttributes(Attributes attributes, NoStreetMap node) {
		if (attributes.getValue("k").equalsIgnoreCase(TipoNo.AEROPORTO.type) && attributes.getValue("v").equalsIgnoreCase(TipoNo.AEROPORTO.value)) {
			System.out.println("Aeroporto detectado");
			node.setTipo(TipoNo.AEROPORTO);
		} else if (attributes.getValue("k").equalsIgnoreCase(TipoNo.PORTO.type) && attributes.getValue("v").equalsIgnoreCase(TipoNo.PORTO.value)) {
			System.out.println("Porto detectado");
			node.setTipo(TipoNo.PORTO);
		} else if (attributes.getValue("k").equalsIgnoreCase(TipoNo.RODOVIA.type) && attributes.getValue("v").equalsIgnoreCase(TipoNo.RODOVIA.value) ) {
			System.out.println("Rodovia detectada");
			node.setTipo(TipoNo.RODOVIA);	
		} else if (attributes.getValue("k").equalsIgnoreCase(TipoNo.FERROVIA.type) && attributes.getValue("v").equalsIgnoreCase(TipoNo.FERROVIA.value) ) {
			System.out.println("Ferrovia detectada");
			node.setTipo(TipoNo.FERROVIA);
		}
		
		if (attributes.getValue("k").equalsIgnoreCase("name")) {
			node.setNome(attributes.getValue("v"));
		}
		
		return node;
	}

}
