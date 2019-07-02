package UNIRIO.TransportesPorMunicipio.Controller.OSM;

import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;

public interface IOSMFileWriter {
	void initializeRead();
	void addLine(NoStreetMap node);
	void writeFile();
}
