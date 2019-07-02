package UNIRIO.TransportesPorMunicipio;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;

import org.junit.Test;
import org.mockito.Mockito;

import UNIRIO.TransportesPorMunicipio.Util.FileManager;

public class FileManagerTest {
	
	FileManager subject = Mockito.mock(FileManager.class);
	
	@Test
	public void shouldCreateFile() {
		subject = new FileManager();
		
		Path path = Paths.get("//home//gabriel//testFile");
		List<String> fileContent = new ArrayList<String>();	
		fileContent.add("TEST TEXT");
		
		subject.createFile(path, fileContent);
		
		assertTrue("Arquivo não foi encontrado", Files.exists(path));
	}
	
	@Test
	public void shouldCreateFilled() throws Exception {
		subject = new FileManager();
		
		Path path = Paths.get("//home//gabriel//testFile");
		List<String> fileContent = new ArrayList<String>();	
		fileContent.add("TEST TEXT");
		
		subject.createFile(path, fileContent);
		
		assertTrue("O arquivo está vazio", Files.size(path) > 0);
	}
}
