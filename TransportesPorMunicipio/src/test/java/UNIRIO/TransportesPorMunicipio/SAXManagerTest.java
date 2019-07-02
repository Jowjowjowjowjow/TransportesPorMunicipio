package UNIRIO.TransportesPorMunicipio;

import static org.junit.Assert.assertNotNull;

import javax.xml.parsers.SAXParser;

import org.junit.Test;
import org.mockito.Mockito;

import UNIRIO.TransportesPorMunicipio.Util.SAXManager;

public class SAXManagerTest {
	
	SAXManager saxManager = Mockito.mock(SAXManager.class);
 
	@Test
	public void shouldGetAValidSaxParser() {
		saxManager = new SAXManager();
		
		SAXParser parser = saxManager.getSaxParser();
		
		assertNotNull(parser);
	
	}
	
}
