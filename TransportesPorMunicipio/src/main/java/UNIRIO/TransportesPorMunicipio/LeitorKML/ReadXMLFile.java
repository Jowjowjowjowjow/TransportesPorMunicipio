package UNIRIO.TransportesPorMunicipio.LeitorKML;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ReadXMLFile {

	public static void main(String argv[]) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			List<String> lines = new ArrayList<String>();
			Path file2 = Paths.get("d:\\oi.txt");
			DefaultHandler handler = new DefaultHandler() {

				boolean nomeMunicipio = false;
				boolean codigoMunicipio = false;
				boolean coordenadas = false;

				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
				
					if (qName.equalsIgnoreCase("SimpleData")) {
						if(attributes.getValue("name").equalsIgnoreCase("NM_MUNICIP")) {
							nomeMunicipio = true;
						}else if(attributes.getValue("name").equalsIgnoreCase("CD_GEOCMU")) {
							codigoMunicipio = true;
						}
					}

					if (qName.equalsIgnoreCase("coordinates")) {
						coordenadas = true;
					}

				}

				public void characters(char ch[], int start, int length) throws SAXException {
					if (nomeMunicipio) {
						System.out.println("Nome do município : " + new String(ch, start, length));
						lines.add("Nome do município : " + new String(ch, start, length));
						nomeMunicipio = false;
					}

					if (codigoMunicipio) {
						System.out.println("Codigo do município :" + new String(ch, start, length));
						lines.add("Codigo do município :" + new String(ch, start, length));
						codigoMunicipio = false;
					}

					if (coordenadas) {
						String[] tempCoordenadas;
						//System.out.println("Coordenadas : " + new String(ch, start, length).trim());
						tempCoordenadas = new String(ch, start, length).trim().split(",0");
						for(int i = 0; i < tempCoordenadas.length-1; i++) {
							String[] temp2 = tempCoordenadas[i].trim().split(",");
							System.out.print("X: " +temp2[0] + " Y:" +temp2[1]+" \n");		
							lines.add("X: " +temp2[0] + " Y:" +temp2[1]+" \n");
						}
						coordenadas = false;
					}

				}
				
			};

			File file = new File("d:\\municipiosrj.kml");
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");

			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");

			saxParser.parse(is, handler);

			Files.write(file2, lines, Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}