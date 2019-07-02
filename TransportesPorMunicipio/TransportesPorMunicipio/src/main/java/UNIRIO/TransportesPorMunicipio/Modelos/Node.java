package UNIRIO.TransportesPorMunicipio.Modelos;

public enum Node {

	AEROPORTO("aeroway", "aerodrome"),
	PORTO("landuse", "harbour"),
	RODOVIA("highway", "primary"),
	FERROVIA("railway" ,"rail");
	
	public final String value;
	public final String type;
	
	private Node(String type,String value) {
		this.value = value;
		this.type = type;
	}
}
