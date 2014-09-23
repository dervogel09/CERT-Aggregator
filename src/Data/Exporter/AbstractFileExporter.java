package Data.Exporter;

import java.util.ArrayList;

import Data.CERTAlert;

/**
 * 
 * @author Felix Fink
 *
 *Abstract Class to be extended to create File Exports.
 *These classes are to be used to write aggregated Alerts to files.
 */

public abstract class AbstractFileExporter {
	
	private String exporterName;
	/**
	 * Used to define the display name in the GUI
	 * @param exporterName
	 */
	public AbstractFileExporter(String exporterName){
		this.exporterName = exporterName;
	}

	/**
	 * @return the exporterName
	 */
	public String getExporterName() {
		return exporterName;
	}
	
	public abstract void saveToFile(String filename, ArrayList<CERTAlert> alerts);
}
