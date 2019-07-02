package UNIRIO.TransportesPorMunicipio.Controller.OSM;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Util.FileManager;

/** 
 * https://www.overpass-api.de/api/xapi_meta?*[bbox=1,2,3,4]
* onde 
* 1 = menor longitude
* 2 = menor latitude
* 3 = maior longitude
* 4 = maior latitude
* EX: https://overpass-api.de/api/map?bbox=-43.79610192399996,-23.08240303799993,-43.09903939999998,-22.74605452899993
*/

public class OSMFileDownloader {

	private Municipio municipio;
	private FileManager fileManager;
	private Path filePath;
	
	public OSMFileDownloader(Municipio municipio, FileManager fileManager, Path filePath) {
		this.fileManager = fileManager;
		this.municipio = municipio;
		this.filePath = filePath;
	}
	
	/**
	 * Função com o objetivo de criar a url com a boundingBox do município, chamar a API do openStreetMap com a bounding box do município correspondente
	 * e fazer o download do arquivo osm resultante
	 * @param municipio Município que será baixado
	 * @author Jow
	 */
	public void downloadFile() {
		try {
		     InputStream is = setUrl().openStream();
		     fileManager.downloadFile(filePath, is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private URL setUrl() {
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
	
}
