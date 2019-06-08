package UNIRIO.TransportesPorMunicipio.Modelos;

public class NoStreetMap {

	String nome = null;
	TipoNo tipo = null;
	
	NoStreetMap(String nome, TipoNo tipo){
		this.nome = nome;
		this.tipo = tipo;
	}
	
	public NoStreetMap() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public TipoNo getTipo() {
		return tipo;
	}
	public void setTipo(TipoNo tipo) {
		this.tipo = tipo;
	}
	
	
}
