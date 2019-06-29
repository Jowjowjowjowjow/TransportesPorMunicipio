package UNIRIO.TransportesPorMunicipio.Controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;

import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;

public class FileWriter implements IFileWriter {
	
	private LeitorDeArquivosOSM reader;
	private SAXParser saxParser;
	
	private List<String> resultantFile = new ArrayList<String>();
	private final Path path = Paths.get("//home//gabriel//bla.txt");
	
	public FileWriter(SAXParser saxParser) {
		this.saxParser = saxParser;
	}
	
	@Override
	public void initializeRead() {		
		reader = new LeitorDeArquivosOSM(saxParser, this);
		reader.carregaLocais();
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
