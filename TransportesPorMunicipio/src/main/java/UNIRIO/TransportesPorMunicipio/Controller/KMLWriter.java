package UNIRIO.TransportesPorMunicipio.Controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;

import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;

public class KMLWriter implements IFileWriter {
	private LeitorDeArquivosKML reader;
	private SAXParser saxParser;
	
	private List<String> resultantFile = new ArrayList<String>();
	private final Path path = Paths.get("//home//gabriel//oi.txt");
	
	public KMLWriter(SAXParser saxParser) {
		this.saxParser = saxParser;
	}
	
	@Override
	public void initializeRead() {
		reader = new LeitorDeArquivosKML(saxParser, this);
		reader.carregaMunicipios();
		
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

	@Override
	public void addLine(NoStreetMap node) {
		// TODO Auto-generated method stub
		
	}
	
	public void addLineKML(Municipio municipio) {
		resultantFile.add("Nome: " + municipio.getNome());
		resultantFile.add("Tipo: " + municipio.getCodigoIBGE());
		resultantFile.add("Coordenadas: " + municipio.getBoundingBox());
		
	}
}
