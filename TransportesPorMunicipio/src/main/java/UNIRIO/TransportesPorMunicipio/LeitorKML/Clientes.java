package UNIRIO.TransportesPorMunicipio.LeitorKML;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "clientes")
public class Clientes {

	
	List<Cliente> clientes;

	public List<Cliente> getClientes() {
		return clientes;
	}

	@XmlElement (name = "cliente")
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}



	
}
