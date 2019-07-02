package UNIRIO.TransportesPorMunicipio.Modelos;

public class NoStreetMap {

	String nome = null;
	Node tipo = null;
	
	NoStreetMap(String nome, Node tipo){
		this.nome = nome;
		this.tipo = tipo;
	}
	
	public NoStreetMap() {
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Node getTipo() {
		return tipo;
	}
	public void setTipo(Node tipo) {
		this.tipo = tipo;
	}
	
	
}
