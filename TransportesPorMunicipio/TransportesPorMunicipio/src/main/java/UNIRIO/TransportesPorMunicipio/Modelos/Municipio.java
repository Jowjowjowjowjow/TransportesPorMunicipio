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
	
	/**
	 * Função com função de calcular a Bounding Box de um município
	 * @author Jow
	 * @param municipio Município que terá a Bounding Box calculada
	 * @return BoundingBox do município
	 */
	public BoundingBox calculaBoundingBox(){
		double maiorLatitude = Double.NEGATIVE_INFINITY;
		double menorLatitude = Double.MAX_VALUE;
		double maiorLongitude = Double.NEGATIVE_INFINITY;
		double menorLongitude = Double.MAX_VALUE;
		
		for(Poligono poligono: this.getPoligonos()) {
			for(Coordenada coordenada: poligono.getCoordenadas()) {
				if(coordenada.getLatitude() > maiorLatitude) {
					maiorLatitude = coordenada.getLatitude();
				}
				if(coordenada.getLatitude() < menorLatitude) {
					menorLatitude = coordenada.getLatitude();
				}
				if(coordenada.getLongitude() > maiorLongitude) {
					maiorLongitude = coordenada.getLongitude();
				}
				if(coordenada.getLongitude() < menorLongitude) {
					menorLongitude = coordenada.getLongitude();
				}
			}
		}
		BoundingBox boundingBox = new BoundingBox(menorLatitude,maiorLatitude,menorLongitude,maiorLongitude);
		
		return boundingBox;
		
	}


	
}
