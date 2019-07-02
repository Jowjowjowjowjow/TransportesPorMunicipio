package UNIRIO.TransportesPorMunicipio.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;

import org.xml.sax.InputSource;

import UNIRIO.TransportesPorMunicipio.Controller.OSM.OSMFileWriter;
import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Util.SAXManager;


public class Main {

	private static File file;
	private static Reader reader;
	private static InputSource inputSource;
	private static InputStream inputStream;
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		
		Boolean alreadyHasOSMFile = true;
		
		SAXManager saxManager = new SAXManager();
		SAXParser saxParser = saxManager.getSaxParser();
		
		if(!alreadyHasOSMFile) {
			file = new File("c://xml//municipiosbr.kml");
			configFile();
			readKMLFile(inputSource, saxParser);
			
		} else {
			file = new File("c://xml//municipio.osm");
			configFile();
			readOSMFile(inputSource, saxParser);
		}
		
	}
	
	private static void configFile() throws FileNotFoundException, UnsupportedEncodingException {
		inputStream = new FileInputStream(file);
		reader = new InputStreamReader(inputStream, "UTF-8");
		inputSource = new InputSource(reader);
		inputSource.setEncoding("UTF-8");	
	} 
	
	private static void readOSMFile(InputSource inputSource, SAXParser saxParser) {
		OSMFileWriter fileWriter = new OSMFileWriter(saxParser, inputSource);
		fileWriter.initializeRead();
	}
	
	private static void readKMLFile(InputSource inputSource, SAXParser saxParser) {
		KMLFileReader leitor = new KMLFileReader(saxParser, inputSource);
		ArrayList<Municipio> municipios = leitor.loadCounties();
		System.out.println(municipios.size());
		
//		ArrayList<String> conteudoArquivoBoundingBox = new ArrayList<String>();
//		for(Municipio municipio: municipios) {
//			municipio.setBoundingBox(BoundingBox.calculaBoundingBox(municipio));
//			System.out.println("Nome: " + municipio.getNome());
//			conteudoArquivoBoundingBox.add("Nome: " + municipio.getNome());
//			System.out.println("Codigo: " + municipio.getCodigoIBGE());
//			conteudoArquivoBoundingBox.add("Codigo: " + municipio.getCodigoIBGE());
//			System.out.println("Quantidade de polígonos: " + municipio.getPoligonos().size());
//			conteudoArquivoBoundingBox.add("Quantidade de polígonos: " + municipio.getPoligonos().size());
//			System.out.println("Bounding Box: " + municipio.getBoundingBox().exibeBoundingBox() +"\n");
//			conteudoArquivoBoundingBox.add("Bounding Box: " + municipio.getBoundingBox().exibeBoundingBox() +"\n");		
//			Utilitarios.criaEEscreveArquivo("D:\\ResultadoBoundingBox.txt", conteudoArquivoBoundingBox);
//		}
		
//		for(Municipio municipio: municipios) {
//			if(municipio.getNome().equalsIgnoreCase("Rio de Janeiro")) {
//				BaixaArquivoOSM.BaixaArquivo(municipio);
			//}
		//}
	}
}
