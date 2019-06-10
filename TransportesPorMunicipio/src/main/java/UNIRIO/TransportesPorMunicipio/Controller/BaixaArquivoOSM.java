package UNIRIO.TransportesPorMunicipio.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BaixaArquivoOSM {

	/*https://www.overpass-api.de/api/xapi_meta?*[bbox=1,2,3,4]
	onde 
	1 = menor longitude
	2 = menor latitude
	3 = maior longitude
	4 = maior latitude*/

	public static void BaixaArquivo(double menorLongitude, double menorLatitude, double maiorLongitude,
			double maiorLatitude) {
		
		try {
        URL url = new URL("https://www.overpass-api.de/api/xapi_meta?*"
        + "[bbox="+menorLongitude+","+menorLatitude+","+maiorLongitude+","+maiorLatitude+"]");
        System.out.println("URL: " + "https://www.overpass-api.de/api/xapi_meta?way"
                + "[bbox="+menorLongitude+","+menorLatitude+","+maiorLongitude+","+maiorLatitude+"]");
        File file = new File("c:\\xml\\municipio.osm");

        InputStream is = url.openStream();
        FileOutputStream fos = new FileOutputStream(file);
        int bytes = 0;
        System.out.println("Download iniciado");
        while ((bytes = is.read()) != -1) {
        	System.out.println("Download em " + bytes  + " bytes");
            fos.write(bytes);
        }
        System.out.println("Download finalizado");

        is.close();

        fos.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
