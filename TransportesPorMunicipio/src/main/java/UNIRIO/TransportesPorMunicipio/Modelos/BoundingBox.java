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
