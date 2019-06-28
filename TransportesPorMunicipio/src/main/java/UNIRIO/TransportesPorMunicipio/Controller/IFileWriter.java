package UNIRIO.TransportesPorMunicipio.Controller;

import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;

public interface IFileWriter {
	void initializeRead();
	void writeFile();
	void addLine(NoStreetMap node);
}
