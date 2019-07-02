package UNIRIO.TransportesPorMunicipio.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.SAXParser;

import org.xml.sax.InputSource;

import UNIRIO.TransportesPorMunicipio.Controller.OSM.OSMFileDownloader;
import UNIRIO.TransportesPorMunicipio.Controller.OSM.OSMFileWriter;
import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Util.FileManager;
import UNIRIO.TransportesPorMunicipio.Util.SAXManager;


public class Main {

	private static File file;
	private static Reader reader;
	private static InputSource inputSource;
	private static InputStream inputStream;
	private static Scanner scanner;
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		
		Boolean alreadyHasOSMFile = false;
		
		SAXManager saxManager = new SAXManager();
		SAXParser saxParser = saxManager.getSaxParser();
		
		if(!alreadyHasOSMFile) {
			file = new File("//home//gabriel//municipiosbr.kml");
			configFile();
			readKMLFile(saxParser);
			
		} else {
			file = new File("//home//gabriel//municipio.osm");
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
	
	private static void readKMLFile(SAXParser saxParser) {
		KMLFileReader leitor = new KMLFileReader(saxParser, inputSource);
		ArrayList<Municipio> municipios = leitor.loadCounties();
		
		scanner = new Scanner(System.in);
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
			
			Path path = Paths.get("//home//gabriel//"+municipioSelecionado.getNome()+"_"+municipioSelecionado.getCodigoIBGE()+".osm");
			
			FileManager fileManager = new FileManager();
			
			OSMFileDownloader fileDownloader = null;
			
			try {
				inputStream = setUrl(municipioSelecionado).openStream();
				fileDownloader = new OSMFileDownloader(fileManager, path, inputStream);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			fileDownloader.downloadFile();
			
			file = path.toFile();
			try {
				configFile();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			readOSMFile(inputSource, saxParser);
		}
	}
}
