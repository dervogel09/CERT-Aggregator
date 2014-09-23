package Data.Exporter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Data.CERTAlert;

public class PlainTextExport extends AbstractFileExporter {

	/**
	 * 
	 * @author Felix Fink
	 * 
	 *Exporting Cert Alerts to a file by writing plain text into a .txt-file.
	 */
	public PlainTextExport(){
		super("Plain Text Export");
	}
	
	@Override
	public void saveToFile(String filename, ArrayList<CERTAlert> alerts){
		try {
			PrintWriter writer = new PrintWriter(filename + ".txt");
			
			if(alerts.size() > 0){
				for(int i = 0; i < alerts.size(); i++){
					this.writeSingleAlert(writer, alerts.get(i));
					writer.write("\n\r\n\r ------\n\r\n\r");
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	private void writeSingleAlert(PrintWriter writer, CERTAlert alert){
		writer.println("ID: " + alert.getId());
		writer.println("Title: " + alert.getTitle());
		writer.println("Manufactorer: " + alert.getManufactorer());
		writer.println("Product Name: " + alert.getProduct_name());
		writer.println("Product Version: " + alert.getProduct_version());
		writer.println("Operating System: " + alert.getOperating_system());
		writer.println("Date of Creation: " + alert.getCreate_date());
		writer.println("Date of the last Update: " + alert.getUpdatde_date());
		writer.println("Type: " + alert.getType());
		writer.println("Language: " + alert.getLanguage());
		writer.println("Description: " + alert.getDescription());
		writer.println("Degree of Danger: " + alert.getDegree_of_danger());
		writer.println("Cause: " + alert.getCause());
		writer.println("Result: " + alert.getResult());
		writer.println("Solution: " + alert.getSolution());
		writer.println("Reference: " + alert.getReference());
		
	}



	
}
