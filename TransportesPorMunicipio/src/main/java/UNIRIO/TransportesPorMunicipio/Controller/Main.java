package UNIRIO.TransportesPorMunicipio.Controller;

import java.util.ArrayList;

import UNIRIO.TransportesPorMunicipio.Modelos.BoundingBox;
import UNIRIO.TransportesPorMunicipio.Modelos.Coordenada;
import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Modelos.Poligono;

public class Main {

	public static void main(String[] args) {
		/*ArrayList<Municipio> municipios = LeitorDeArquivosKML.carregaMunicipios();
		ArrayList<String> conteudoArquivoBoundingBox = new ArrayList<String>();
		for(Municipio municipio: municipios) {
			calculaBoundingBox(municipio);
			System.out.println("Nome: " + municipio.getNome());
			conteudoArquivoBoundingBox.add("Nome: " + municipio.getNome());
			System.out.println("Codigo: " + municipio.getCodigoIBGE());
			conteudoArquivoBoundingBox.add("Codigo: " + municipio.getCodigoIBGE());
			System.out.println("Quantidade de polígonos: " + municipio.getPoligonos().size());
			conteudoArquivoBoundingBox.add("Quantidade de polígonos: " + municipio.getPoligonos().size());
			System.out.println("Bounding Box: " + municipio.getBoundingBox().exibeBoundingBox() +"\n");
			conteudoArquivoBoundingBox.add("Bounding Box: " + municipio.getBoundingBox().exibeBoundingBox() +"\n");
			
			if(municipio.getNome().equalsIgnoreCase("Rio de Janeiro")) {
				BaixaArquivoOSM.BaixaArquivo(municipio.getBoundingBox().getMenorLongitude(), municipio.getBoundingBox().getMenorLatitude(), municipio.getBoundingBox().getMaiorLongitude(), municipio.getBoundingBox().getMaiorLatitude());
			}
			
		}
		
		LeitorDeArquivosKML.escreveArquivo("c:\\xml\\ResultadoBoundingBox.txt", conteudoArquivoBoundingBox);
		*/
		LeitorDeArquivosOSM.carregaLocais();

		
		
	}
	
	private static void calculaBoundingBox(Municipio municipio){
		double maiorLatitude = Double.NEGATIVE_INFINITY;
		double menorLatitude = Double.MAX_VALUE;
		double maiorLongitude = Double.NEGATIVE_INFINITY;
		double menorLongitude = Double.MAX_VALUE;
		
		for(Poligono poligono: municipio.getPoligonos()) {
			for(Coordenada coordenada: poligono.getCoordenadas()) {
				if(coordenada.getY() > maiorLatitude) {
					maiorLatitude = coordenada.getY();
				}
				if(coordenada.getY() < menorLatitude) {
					menorLatitude = coordenada.getY();
				}
				if(coordenada.getX() > maiorLongitude) {
					maiorLongitude = coordenada.getX();
				}
				if(coordenada.getX() < menorLongitude) {
					menorLongitude = coordenada.getX();
				}
			}
		}
		BoundingBox boundingBox = new BoundingBox(menorLatitude,maiorLatitude,menorLongitude,maiorLongitude);
		
		municipio.setBoundingBox(boundingBox);
		
	}

}
