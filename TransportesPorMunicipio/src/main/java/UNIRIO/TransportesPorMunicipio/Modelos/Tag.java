package UNIRIO.TransportesPorMunicipio.Modelos;

public enum Tag {
	
	SIMPLE_DATA ("SIMPLEDATA"),
	COUNTY_NAME ("NM_MUNICIP"),
	COUNTY_CODE ("CD_GEOCMU"),
	PLACEMARK ("PLACEMARK"),
	COORDINATES ("COORDINATES");
	
	public final String value;
	
	Tag (String value) {
		this.value = value;
	}
	
}
