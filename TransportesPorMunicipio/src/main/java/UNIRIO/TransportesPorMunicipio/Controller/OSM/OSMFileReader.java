package UNIRIO.TransportesPorMunicipio.Controller.OSM;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;
import UNIRIO.TransportesPorMunicipio.Modelos.Node;
import UNIRIO.TransportesPorMunicipio.Util.ISAXHandler;

public class OSMFileReader extends DefaultHandler implements ISAXHandler {
	
	private ArrayList<NoStreetMap> nosStreetMap = new ArrayList<NoStreetMap>();	
	private NoStreetMap streetMapNode = new NoStreetMap();	
	private IOSMFileWriter fileWriter;
	private SAXParser saxParser;
	private InputSource inputSource;
	
	public OSMFileReader(SAXParser saxParser, IOSMFileWriter fileWriter, InputSource inputSource) {
		this.saxParser = saxParser;
		this.fileWriter = fileWriter;
		this.inputSource = inputSource;
	}
	
	public void loadLocals() {
		try {
			saxParser.parse(inputSource, this);
			fileWriter.writeFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}	
	
	@Override
	public void startElement(String uri, String localName, String tagName, Attributes attrs) throws SAXException {					
		if (tagName.equalsIgnoreCase("way") || tagName.equalsIgnoreCase("relation")) {
			if (streetMapNode.getNome() != null && streetMapNode.getTipo() != null && !nodeExistsOnList(streetMapNode)) {
				fileWriter.addLine(streetMapNode);
				nosStreetMap.add(streetMapNode);
			}
			
			if (streetMapNode != null)
				streetMapNode = new NoStreetMap();
		}
		
		if (tagName.equalsIgnoreCase("tag")) {	
			streetMapNode = nodeWithAttributes(attrs, streetMapNode);
		}
	}
	
	private boolean nodeExistsOnList(NoStreetMap streeMapNode) {
		for(NoStreetMap node: nosStreetMap) {
			if(node.getNome().equalsIgnoreCase(streeMapNode.getNome()) && node.getTipo() == streeMapNode.getTipo()) {
				return true;
			}
		}
		return false;
	}

	private NoStreetMap nodeWithAttributes(Attributes attributes, NoStreetMap node) {
		if (attributes.getValue("k").equalsIgnoreCase(Node.AEROPORTO.type) && attributes.getValue("v").equalsIgnoreCase(Node.AEROPORTO.value)) {
			System.out.println("Aeroporto detectado");
			node.setTipo(Node.AEROPORTO);
		} else if (attributes.getValue("k").equalsIgnoreCase(Node.PORTO.type) && attributes.getValue("v").equalsIgnoreCase(Node.PORTO.value)) {
			System.out.println("Porto detectado");
			node.setTipo(Node.PORTO);
		} else if (attributes.getValue("k").equalsIgnoreCase(Node.RODOVIA.type) && attributes.getValue("v").equalsIgnoreCase(Node.RODOVIA.value) ) {
			System.out.println("Rodovia detectada");
			node.setTipo(Node.RODOVIA);	
		} else if (attributes.getValue("k").equalsIgnoreCase(Node.FERROVIA.type) && attributes.getValue("v").equalsIgnoreCase(Node.FERROVIA.value) ) {
			System.out.println("Ferrovia detectada");
			node.setTipo(Node.FERROVIA);
		}
		
		if (attributes.getValue("k").equalsIgnoreCase("name")) {
			node.setNome(attributes.getValue("v"));
		}
		
		return node;
	}

}
