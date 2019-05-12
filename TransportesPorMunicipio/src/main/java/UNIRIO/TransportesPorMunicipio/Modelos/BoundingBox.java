package UNIRIO.TransportesPorMunicipio.Modelos;

public class BoundingBox {
	//Ponto esquerdo superior
	private Coordenada x1;
	//Ponto direito superior
	private Coordenada x2;
	//Ponto esquerdo inferior
	private Coordenada x3;
	//Ponto direito superior
	private Coordenada x4;
	
	public BoundingBox(Coordenada x1, Coordenada x2, Coordenada x3, Coordenada x4){
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.x4 = x4;
	}

	public Coordenada getX1() {
		return x1;
	}

	public void setX1(Coordenada x1) {
		this.x1 = x1;
	}

	public Coordenada getX2() {
		return x2;
	}

	public void setX2(Coordenada x2) {
		this.x2 = x2;
	}

	public Coordenada getX3() {
		return x3;
	}

	public void setX3(Coordenada x3) {
		this.x3 = x3;
	}

	public Coordenada getX4() {
		return x4;
	}

	public void setX4(Coordenada x4) {
		this.x4 = x4;
	}
	
	public String exibeBoundingBox() {
		return new String("\nX1 X: " +x1.getX() + " Y: " +x1.getY()
		+"\nX2 X: "+x2.getX() + " Y: "+x2.getY()
		+"\nX3 X: "+x3.getX() + " Y: "+x3.getY()
		+"\nX4 X: "+x4.getX() + " Y: "+x4.getY());
	}
	
	
	
}
