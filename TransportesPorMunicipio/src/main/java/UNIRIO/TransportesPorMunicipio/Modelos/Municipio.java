package UNIRIO.TransportesPorMunicipio.Modelos;

import lombok.Getter;
import lombok.Setter;

public class Municipio {

	@Getter @Setter
	private String nome;
	@Getter @Setter
	private int codigoIBGE;
	@Getter @Setter
	private Poligono poligonos[];
}
