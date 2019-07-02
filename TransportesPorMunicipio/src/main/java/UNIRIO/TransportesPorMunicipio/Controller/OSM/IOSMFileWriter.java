package UNIRIO.TransportesPorMunicipio.Controller.OSM;

import UNIRIO.TransportesPorMunicipio.Modelos.Municipio;
import UNIRIO.TransportesPorMunicipio.Modelos.NoStreetMap;

public interface IOSMFileWriter {
	void initializeRead();
	void addLine(NoStreetMap node);
<<<<<<< HEAD:TransportesPorMunicipio/src/main/java/UNIRIO/TransportesPorMunicipio/Controller/IFileWriter.java
	void addLineKML(Municipio municipio);
=======
	void writeFile();
>>>>>>> d7bdc06b27fa61fa9223e3f01c03c11bf767749d:TransportesPorMunicipio/src/main/java/UNIRIO/TransportesPorMunicipio/Controller/OSM/IOSMFileWriter.java
}
