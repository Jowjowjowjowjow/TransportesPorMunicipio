package UNIRIO.TransportesPorMunicipio.Modelos;

public class BoundingBox {
	private double menorLatitude;
	private double maiorLatitude;
	private double menorLongitude;
	private double maiorLongitude;
	
	public BoundingBox(double menorLatitude, double maiorLatitude, double menorLongitude, double maiorLongitude ){
		this.menorLatitude = menorLatitude;
		this.maiorLatitude = maiorLatitude;
		this.menorLongitude = menorLongitude;
		this.maiorLongitude = maiorLongitude;	
	}

	/**
	 * Função com o objetivo de retornar a Bounding Box em forma de String
	 * @author Jow
	 * @return Maior/Menor Latitude/Longitude
	 */
	public String exibeBoundingBox() {
		return new String("\nMenor Latitude: " +this.menorLatitude
		+"\nMaior Latitude: " + this.maiorLatitude
		+"\nMenor Longitude: "+ this.menorLongitude
		+"\nMaior Longitude: "+ this.maiorLongitude);
	}
	
	/**
	 * Função com função de calcular a Bounding Box de um município
	 * @author Jow
	 * @param municipio Município que terá a Bounding Box calculada
	 * @return BoundingBox do município
	 */
	public BoundingBox calculaBoundingBox(Municipio municipio){
		double maiorLatitude = Double.NEGATIVE_INFINITY;
		double menorLatitude = Double.MAX_VALUE;
		double maiorLongitude = Double.NEGATIVE_INFINITY;
		double menorLongitude = Double.MAX_VALUE;
		
		for(Poligono poligono: municipio.getPoligonos()) {
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


	public double getMenorLatitude() {
		return menorLatitude;
	}


	public void setMenorLatitude(double menorLatitude) {
		this.menorLatitude = menorLatitude;
	}


	public double getMaiorLatitude() {
		return maiorLatitude;
	}


	public void setMaiorLatitude(double maiorLatitude) {
		this.maiorLatitude = maiorLatitude;
	}


	public double getMenorLongitude() {
		return menorLongitude;
	}


	public void setMenorLongitude(double menorLongitude) {
		this.menorLongitude = menorLongitude;
	}


	public double getMaiorLongitude() {
		return maiorLongitude;
	}


	public void setMaiorLongitude(double maiorLongitude) {
		this.maiorLongitude = maiorLongitude;
	}
}
