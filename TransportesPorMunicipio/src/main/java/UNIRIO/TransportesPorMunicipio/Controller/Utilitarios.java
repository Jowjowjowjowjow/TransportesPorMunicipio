package UNIRIO.TransportesPorMunicipio.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Utilitarios {
	
	/**
	 * Função que cria um arquivo a partir de um caminho + extensão e um conteúdo em uma lista de Strings
	 * @param destino Caminho, nome e extensão do arquivo que será criado
	 * @param conteudo Conteúdo do arquivo a ser criado
	 * @author Jow
	 */
	public static void criaEEscreveArquivo(String destino, List<String> conteudo) {
		Path ArquivoDestino = Paths.get(destino);
		try {
			Files.write(ArquivoDestino, conteudo, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Função que cria um arquivo a partir de um caminho + extensão e um conteúdo em um InputStream
	 * @param destino Caminho, nome e extensão do arquivo que será criado
	 * @param conteudo Conteúdo do arquivo a ser criado
	 * @author Jow
	 */
	public static void criaBaixaEEscreveArquivo(String destino, InputStream conteudo) {
		if(checaSeArquivoExiste(destino)) {
			System.out.println("Arquivo já em cache");
		}else {
			System.out.println("Arquivo não encontrado, Fazendo download");
			try {
				FileOutputStream fileOutput = new FileOutputStream(new File(destino));
				int bytes = 0;
				while((bytes = conteudo.read()) != -1) {
					fileOutput.write(bytes);
				}
			System.out.println("Download finalizado");
			conteudo.close();
			fileOutput.close();
			} catch (FileNotFoundException e) {
				System.out.println("Houve um problema no download: " + e.getMessage());
			} catch (IOException e) {
				System.out.println("Houve um problema no download: " + e.getMessage());
			}
		}
	}
	/**
	 * Função utilitária com objetivo de verificar se um arquivo já existe.
	 * @param Caminho, nome e extensão do arquivo que será checado
	 * @author Jow
	 * @return
	 */
	public static boolean checaSeArquivoExiste(String destino) {
		File file = new File(destino);
		System.out.println("Verificando se o arquivo " + destino + " já existe.");
		return file.exists();
	}

}
