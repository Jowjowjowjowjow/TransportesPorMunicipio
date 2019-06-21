package UNIRIO.TransportesPorMunicipio.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;

public class BaixaArquivoOSM {

	/*https://www.overpass-api.de/api/xapi_meta?*[bbox=1,2,3,4]
	*onde 
	*1 = menor longitude
	*2 = menor latitude
	*3 = maior longitude
	*4 = maior latitude
	*EX: https://overpass-api.de/api/map?bbox=-43.79610192399996,-23.08240303799993,-43.09903939999998,-22.74605452899993
	*/

	/**
	 * Função com o objetivo de criar a url com a boundingBox do município, chamar a API do openStreetMap com a bounding box do município correspondente
	 * e fazer o download do arquivo osm resultante
	 * @param municipio Município que será baixado
	 * @author Jow
	 */
	public static void BaixaArquivo(Municipio municipio) {
		String caminhoArquivo = "D://"+municipio.getNome()+"_"+municipio.getCodigoIBGE()+".osm";
		try {
			URL url = new URL("https://overpass-api.de/api/map?"
			+ "bbox="+municipio.getBoundingBox().getMenorLongitude()+","+municipio.getBoundingBox().getMenorLatitude()+","+municipio.getBoundingBox().getMaiorLongitude()+","+
			municipio.getBoundingBox().getMaiorLatitude());
		     InputStream is = url.openStream();
		     Utilitarios.criaBaixaEEscreveArquivo(caminhoArquivo, is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       

		
		

		
	}
	
}
