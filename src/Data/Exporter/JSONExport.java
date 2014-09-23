package Data.Exporter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import Data.CERTAlert;

/**
 * 
 * @author Felix Fink
 * 
 *Exporting Cert Alerts to a file using the JSON-format.
 */
public class JSONExport extends AbstractFileExporter {

	public JSONExport() {
		super("JSON Export");
	}

	/**
	 * Function that writes the given Cert Alerts into a file using the JSON-format with the given filename.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveToFile(String filename, ArrayList<CERTAlert> alerts) {
		try {
			PrintWriter writer = new PrintWriter(filename + ".json");
			JSONObject obj = new JSONObject();
			if(alerts.size() > 0){
				for(int i = 0; i < alerts.size(); i++){
					obj.put("CERT Alert " + (i+1) , this.convertSingleAlert(alerts.get(i)));
				}
			}
			writer.write(obj.toJSONString());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * private helperfunction to transform a single alert into the json format.
	 */
	@SuppressWarnings("unchecked")
	private JSONObject convertSingleAlert(CERTAlert alert){
		JSONObject obj = new JSONObject();
		obj.put( "ID", alert.getId());
		obj.put( "Title" , alert.getTitle() );
		obj.put( "Manufactorer" , alert.getManufactorer() );
		obj.put( "Product Name" , alert.getProduct_name() );
		obj.put( "Product Version" , alert.getProduct_version() );
		obj.put( "Operating System" , alert.getOperating_system() );
		obj.put( "Date of Creation" , alert.getCreate_date() );
		obj.put( "Date of the last Update" , alert.getUpdatde_date() );
		obj.put( "Type" , alert.getType() );
		obj.put("Language", alert.getLanguage());
		obj.put( "Description" , alert.getDescription());
		obj.put( "Degree of Danger" , alert.getDegree_of_danger());
		obj.put( "Cause" , alert.getCause() );
		obj.put( "Result" , alert.getResult() );
		obj.put( "Solution" , alert.getSolution());
		obj.put( "Reference" , alert.getReference());
		return obj;
	}

}
