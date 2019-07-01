package UNIRIO.TransportesPorMunicipio.Controller;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import UNIRIO.TransportesPorMunicipio.Modelos.ISAXHandler;
import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;
import UNIRIO.TransportesPorMunicipio.Modelos.TipoNo;

public class OSMFileReader extends DefaultHandler implements ISAXHandler {
	
	private ArrayList<NoStreetMap> nosStreetMap = new ArrayList<NoStreetMap>();	
	private NoStreetMap streetMapNode = new NoStreetMap();	
	private IFileWriter fileWriter;
	private SAXParser saxParser;
	private InputSource inputSource;
	
	public OSMFileReader(SAXParser saxParser, IFileWriter fileWriter, InputSource inputSource) {
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
