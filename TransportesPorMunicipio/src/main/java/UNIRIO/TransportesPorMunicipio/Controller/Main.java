package UNIRIO.TransportesPorMunicipio.Controller;

import java.util.ArrayList;

import UNIRIO.TransportesPorMunicipio.Modelos.BoundingBox;
import UNIRIO.TransportesPorMunicipio.Modelos.Coordenada;
import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Modelos.Poligono;

public class Main {

	public static void main(String[] args) {
		ArrayList<Municipio> municipios = LeitorDeArquivosKML.carregaMunicipios();
		ArrayList<String> conteudoArquivoBoundingBox = new ArrayList<String>();
		for(Municipio municipio: municipios) {
			municipio.setBoundingBox(BoundingBox.calculaBoundingBox(municipio));
			System.out.println("Nome: " + municipio.getNome());
			conteudoArquivoBoundingBox.add("Nome: " + municipio.getNome());
			System.out.println("Codigo: " + municipio.getCodigoIBGE());
			conteudoArquivoBoundingBox.add("Codigo: " + municipio.getCodigoIBGE());
			System.out.println("Quantidade de polígonos: " + municipio.getPoligonos().size());
			conteudoArquivoBoundingBox.add("Quantidade de polígonos: " + municipio.getPoligonos().size());
			System.out.println("Bounding Box: " + municipio.getBoundingBox().exibeBoundingBox() +"\n");
			conteudoArquivoBoundingBox.add("Bounding Box: " + municipio.getBoundingBox().exibeBoundingBox() +"\n");		
			Utilitarios.criaEEscreveArquivo("D:\\ResultadoBoundingBox.txt", conteudoArquivoBoundingBox);
		}
		
		for(Municipio municipio: municipios) {
			if(municipio.getNome().equalsIgnoreCase("Rio de Janeiro")) {
				BaixaArquivoOSM.BaixaArquivo(municipio);
				LeitorDeArquivosOSM.carregaLocais(municipio);
			}
		}
		
		
		

		
		
	}
	


}
