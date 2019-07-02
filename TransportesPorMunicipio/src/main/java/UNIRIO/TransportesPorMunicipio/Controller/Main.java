package UNIRIO.TransportesPorMunicipio.Controller;

<<<<<<< HEAD
=======
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
<<<<<<< HEAD
>>>>>>> d7bdc06b27fa61fa9223e3f01c03c11bf767749d
=======
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
>>>>>>> 32888036c960c53bf91aa8ac83ee24c8fec5e9a5
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.SAXParser;

<<<<<<< HEAD
import UNIRIO.TransportesPorMunicipio.Modelos.BoundingBox;
import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
=======
import org.xml.sax.InputSource;

import UNIRIO.TransportesPorMunicipio.Controller.OSM.OSMFileDownloader;
import UNIRIO.TransportesPorMunicipio.Controller.OSM.OSMFileWriter;
import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Util.FileManager;
import UNIRIO.TransportesPorMunicipio.Util.SAXManager;
>>>>>>> d7bdc06b27fa61fa9223e3f01c03c11bf767749d


public class Main {

<<<<<<< HEAD
	public static void main(String[] args) {

		SAXManager saxManager = new SAXManager();
		SAXParser parser = saxManager.getSaxParser();

		FileWriter fileWriter = new FileWriter(parser);
		KMLWriter kmlWriter = new KMLWriter(parser);
		fileWriter.initializeRead();
		fileWriter.initializeRead();
				
	/*	ArrayList<Municipio> municipios = LeitorDeArquivosKML.carregaMunicipios();
		ArrayList<String> conteudoArquivoBoundingBox = new ArrayList<String>();
		for(Municipio municipio: municipios) {
			municipio.setBoundingBox(BoundingBox.calculaBoundingBox(municipio));
			System.out.println("Nome: " + municipio.getNome());
			conteudoArquivoBoundingBox.add("Nome: " + municipio.getNome());
			System.out.println("Codigo: " + municipio.getCodigoIBGE());
			conteudoArquivoBoundingBox.add("Codigo: " + municipio.getCodigoIBGE());
			System.out.println("Quantidade de polígonos: " + municipio.getPoligonos().size());
			conteudoArquivoBoundingBox.add("Quantidade de polígonos: " + municipio.getPoligonos().size());
			System.out.println("Bounding Box: " + municipio.getBoundingBox().exibeBoundingBox() +"\n");
			conteudoArquivoBoundingBox.add("Bounding Box: " + municipio.getBoundingBox().exibeBoundingBox() +"\n");		
			Utilitarios.criaEEscreveArquivo("D:\\ResultadoBoundingBox.txt", conteudoArquivoBoundingBox);
		}
	*/	
	/*	for(Municipio municipio: municipios) {
			if(municipio.getNome().equalsIgnoreCase("Rio de Janeiro")) {
				BaixaArquivoOSM.BaixaArquivo(municipio);
			}
		}*/
=======
	private static File file;
	private static Reader reader;
	private static InputSource inputSource;
	private static InputStream inputStream;
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		
		Boolean alreadyHasOSMFile = false;
		
		SAXManager saxManager = new SAXManager();
		SAXParser saxParser = saxManager.getSaxParser();
		
		if(!alreadyHasOSMFile) {
			file = new File("D:\\municipiosbr.kml");
			configFile();
			readKMLFile(inputSource, saxParser);
			
		} else {
			file = new File("D:\\xxxxx.osm");
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
	
	private static URL setUrl(Municipio municipio) {
		try {
			return new URL("https://overpass-api.de/api/map?" + 
					"bbox=" + 
					municipio.getBoundingBox().getMenorLongitude( ) +
					"," + municipio.getBoundingBox().getMenorLatitude() + 
					"," + municipio.getBoundingBox().getMaiorLongitude() + 
					"," + municipio.getBoundingBox().getMaiorLatitude());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static void readOSMFile(InputSource inputSource, SAXParser saxParser) {
		OSMFileWriter fileWriter = new OSMFileWriter(saxParser, inputSource);
		fileWriter.initializeRead();
	}
	
	private static void readKMLFile(InputSource inputSource, SAXParser saxParser) {
		KMLFileReader leitor = new KMLFileReader(saxParser, inputSource);
		ArrayList<Municipio> municipios = leitor.loadCounties();
		System.out.println("Número de municípios: "+ municipios.size());
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o nome do município");
		String nomeMunicipio = scanner.nextLine();
		ArrayList<Municipio> listaMunicipios = new ArrayList<Municipio>();
		for(Municipio municipio: municipios) {
			if(municipio.getNome().toLowerCase().equalsIgnoreCase(nomeMunicipio)) {
				listaMunicipios.add(municipio);
			}
		}
		
		Municipio municipioSelecionado = null;		
		if(!listaMunicipios.isEmpty()) {
			if(listaMunicipios.size() == 1) {
				
				municipioSelecionado = listaMunicipios.get(0);
				System.out.println("Município: " + municipioSelecionado.getNome() + " Código IBGE: " + municipioSelecionado.getCodigoIBGE());
			} else if(listaMunicipios.size() > 1) {
				for(Municipio municipio: listaMunicipios) {
					System.out.println("Município: " + municipio.getNome() + " Código IBGE: " + municipio.getCodigoIBGE());
				}
				System.out.println("Digite o código IBGE do município");
				String codigoIBGE = scanner.nextLine();
				for(Municipio municipio: listaMunicipios) {
					if(municipio.getCodigoIBGE() == Integer.parseInt(codigoIBGE)) {
						municipioSelecionado = municipio;
						System.out.println("Município: " + municipioSelecionado.getNome() + " Código IBGE: " + municipioSelecionado.getCodigoIBGE());
					}
				}
				if(municipioSelecionado == null) {
					System.out.println("Nenhum município encontrado com esse código, tente novamente.");
				}
			}
		} else {
			System.out.println("Nenhum município encontrado com esse nome");
		}
		
		if(municipioSelecionado != null) {
			municipioSelecionado.setBoundingBox(municipioSelecionado.calculaBoundingBox());
			Path path = Paths.get("D:\\"+municipioSelecionado.getNome()+"_"+municipioSelecionado.getCodigoIBGE()+".osm");
			FileManager fileManager = null;
			fileManager = new FileManager();
			OSMFileDownloader fileDownloader = null;
			try {
				fileDownloader = new OSMFileDownloader(fileManager,path,setUrl(municipioSelecionado).openStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			fileDownloader.downloadFile();
			
			file = path.toFile();
			try {
				inputStream = null;
				configFile();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			readOSMFile(inputSource, saxParser);
		}
		
		
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
>>>>>>> d7bdc06b27fa61fa9223e3f01c03c11bf767749d
	}
}
