package UNIRIO.TransportesPorMunicipio.LeitorKML;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Main {
	public static void main(String[] args) {

	  Cliente cliente = new Cliente();
	  cliente.setId(100);
	  cliente.setNome("mkyong");
	  cliente.setIdade(29);

	  try {

		File file = new File("D:\\file.xml");

		JAXBContext jaxbContext = JAXBContext.newInstance(Cliente.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(cliente, file);
		jaxbMarshaller.marshal(cliente, System.out);

	      } catch (JAXBException e) {
		e.printStackTrace();
	      }

	}
}