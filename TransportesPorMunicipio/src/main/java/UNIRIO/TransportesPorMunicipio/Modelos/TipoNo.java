package UNIRIO.TransportesPorMunicipio.Modelos;

public enum TipoNo {

	AEROPORTO("aeroway", "aerodrome"),
	PORTO("landuse", "harbour"),
	RODOVIA("highway", "primary"),
	FERROVIA("railway" ,"rail");
	
	public final String value;
	public final String type;
	
	private TipoNo(String type,String value) {
		this.value = value;
		this.type = type;
	}
}
