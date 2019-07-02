package UNIRIO.TransportesPorMunicipio.Modelos;

import java.util.ArrayList;

public class Poligono {
	private ArrayList<Coordenada> coordenadas = new ArrayList<Coordenada>();
	
	public void addCoordenada(Coordenada coordenada) {
		this.coordenadas.add(coordenada);
	}
	public ArrayList<Coordenada> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(ArrayList<Coordenada> coordenadas) {
		this.coordenadas = coordenadas;
	}

}
