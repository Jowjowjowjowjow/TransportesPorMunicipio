package UNIRIO.TransportesPorMunicipio.Modelos;

import java.util.ArrayList;

public class Municipio {

	private String nome;
	private int codigoIBGE;
	private ArrayList<Poligono> poligonos = new ArrayList<Poligono>();
	private BoundingBox boundingBox;
	
	public void addPoligono(Poligono poligono) {
		this.poligonos.add(poligono);
	}
	public ArrayList<Poligono> getPoligonos() {
		return poligonos;
	}
	public void setPoligonos(ArrayList<Poligono> poligonos) {
		this.poligonos = poligonos;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getCodigoIBGE() {
		return codigoIBGE;
	}
	public void setCodigoIBGE(int codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	
}
