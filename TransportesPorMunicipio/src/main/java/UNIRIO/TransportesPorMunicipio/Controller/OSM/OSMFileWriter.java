package UNIRIO.TransportesPorMunicipio.Controller.OSM;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;

import org.xml.sax.InputSource;

import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;

public class OSMFileWriter implements IOSMFileWriter {
	
	private OSMFileReader reader;
	private SAXParser saxParser;
	private InputSource inputSource;
	
	private List<String> resultantFile = new ArrayList<String>();
	private final Path path = Paths.get("//home//gabriel//Resultado.txt");
	
	public OSMFileWriter(SAXParser saxParser, InputSource inputSource) {
		this.saxParser = saxParser;
		this.inputSource = inputSource;
	}
	
	@Override
	public void initializeRead() {		
		reader = new OSMFileReader(saxParser, this, inputSource);
		reader.loadLocals();
	}	
	
	@Override
	public void addLine(NoStreetMap node) {
		resultantFile.add("Nome: " + node.getNome());
		resultantFile.add("Tipo: " + node.getTipo());
	}

	@Override
	public void writeFile() {
		try {
			Files.write(path, resultantFile, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
