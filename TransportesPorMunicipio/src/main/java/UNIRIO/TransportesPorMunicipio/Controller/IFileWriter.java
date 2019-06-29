package UNIRIO.TransportesPorMunicipio.Controller;

import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;

public interface IFileWriter {
	void initializeRead();
	void writeFile();
	void addLine(NoStreetMap node);
	void addLineKML(Municipio municipio);
}
