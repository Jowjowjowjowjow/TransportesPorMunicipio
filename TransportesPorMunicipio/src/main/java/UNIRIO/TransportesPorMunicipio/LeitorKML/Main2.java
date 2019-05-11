package UNIRIO.TransportesPorMunicipio.LeitorKML;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


public class Main2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {

				File file = new File("D:\\file.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(Clientes.class);

				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				Clientes customer = (Clientes) jaxbUnmarshaller.unmarshal(file);
				for(Cliente cliente: customer.getClientes()) {
					System.out.println("Cliente: " + cliente.getNome());
				}

			  } catch (JAXBException e) {
				e.printStackTrace();
			  }

	}

}
