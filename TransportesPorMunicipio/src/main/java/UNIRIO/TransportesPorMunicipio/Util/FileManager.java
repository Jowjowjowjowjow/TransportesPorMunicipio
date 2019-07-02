package UNIRIO.TransportesPorMunicipio.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileManager {
	
	public FileManager() {
		
	}	
	
	/***
	 * Método para criar uma arquivo
	 * @authors Gabriel Nogueira, Jonathan Santos e Paulo Henrique
	 * @param path
	 * @param fileContent
	 */
	public void createFile(Path path, List<String> fileContent) {
		try {
			Files.write(path, fileContent, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/***
	 * Método para realizar o download de um arquivo osm baseado no content e salva no path especificado
	 * @param path
	 * @param content
	 */
	public void downloadFile(Path path, InputStream content) {
		if(alreadyExistsFile(path)) {
			System.out.println("Arquivo já em cache");
		} else {
			try {
				System.out.println("Fazendo download do arquivo. Pode demorar dependendo do município");
				FileOutputStream fileOutputStream = new FileOutputStream(new File(path.toString()));
				int bytes = 0;
				while((bytes = content.read()) != -1) {
					fileOutputStream.write(bytes);
				}	
			System.out.println("Download finalizado");
			content.close();
			fileOutputStream.close();
			} catch (FileNotFoundException e) {
				System.out.println("Houve um problema no download: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Houve um problema no download: " + e.getMessage());
			}
		}
	}
	
	/**
	 * Método utilitário com objetivo de verificar se um arquivo já existe.
	 * @param Caminho, nome e extensão do arquivo que será checado
	 * @author Jow
	 * @return
	 */
	private boolean alreadyExistsFile(Path path) {
		File file = new File(path.toString());
		System.out.println("Verificando se o arquivo " + path + " já existe.");
		return file.exists();
	}

}
