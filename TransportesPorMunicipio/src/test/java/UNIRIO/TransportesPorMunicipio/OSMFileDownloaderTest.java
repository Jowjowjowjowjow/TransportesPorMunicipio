package UNIRIO.TransportesPorMunicipio;

import java.io.InputStream;
import java.nio.file.Path;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import UNIRIO.TransportesPorMunicipio.Controller.OSM.OSMFileDownloader;
import UNIRIO.TransportesPorMunicipio.Util.FileManager;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class OSMFileDownloaderTest {
	
	InputStream inputStream = Mockito.mock(InputStream.class);
	FileManager fileManager = Mockito.mock(FileManager.class);
	Path filePath = Mockito.mock(Path.class);
	
	OSMFileDownloader subject = Mockito.mock(OSMFileDownloader.class);
	
	@Test
	public void shouldStartDownloadFile() {
		//when
		subject = new OSMFileDownloader(fileManager, filePath, inputStream);
		//give
		subject.downloadFile();
		//then
		Mockito.verify(fileManager, Mockito.times(1)).downloadFile(filePath, inputStream);
	}
	
}
