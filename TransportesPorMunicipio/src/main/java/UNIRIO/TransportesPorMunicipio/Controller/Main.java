package UNIRIO.TransportesPorMunicipio.Controller;

import java.util.ArrayList;

import UNIRIO.TransportesPorMunicipio.Modelos.BoundingBox;
import UNIRIO.TransportesPorMunicipio.Modelos.Coordenada;
import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Modelos.Poligono;

public class Main {

	public static void main(String[] args) {
		ArrayList<Municipio> municipios = ReadKMLFile.carregaMunicipios();
		
		for(Municipio municipio: municipios) {
			calculaBoundingBox(municipio);
			System.out.println("Nome: " + municipio.getNome());
			System.out.println("Codigo: " + municipio.getCodigoIBGE());
			System.out.println("Quantidade de polígonos: " + municipio.getPoligonos().size());
			System.out.println("Bounding Box: " + municipio.getBoundingBox().exibeBoundingBox() +"\n");
		}

	}
	
	private static void calculaBoundingBox(Municipio municipio){
		double maiorX = Double.NEGATIVE_INFINITY;
		double menorX = Double.MAX_VALUE;
		double maiorY = Double.NEGATIVE_INFINITY;
		double menorY = Double.MAX_VALUE;
		
		for(Poligono poligono: municipio.getPoligonos()) {
			for(Coordenada coordenada: poligono.getCoordenadas()) {
				if(coordenada.getX() > maiorX) {
					maiorX = coordenada.getX();
				}
				if(coordenada.getX() < menorX) {
					menorX = coordenada.getX();
				}
				if(coordenada.getY() > maiorY) {
					maiorY = coordenada.getY();
				}
				if(coordenada.getY() < menorY) {
					menorY = coordenada.getY();
				}
			}
		}
		
		//Canto superior esquerdo
		Coordenada x1 = new Coordenada(menorX, maiorY);
		//Canto superior direito
		Coordenada x2 = new Coordenada(maiorX, maiorY);
		//Canto inferior esquerdo
		Coordenada x3 = new Coordenada(menorX, menorY);
		//Canto inferior direito
		Coordenada x4 = new Coordenada(maiorX, menorY);
		
		BoundingBox boundingBox = new BoundingBox(x1,x2,x3,x4);
		
		municipio.setBoundingBox(boundingBox);
		
	}

}
