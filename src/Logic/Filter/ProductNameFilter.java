package Logic.Filter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import Data.CERTAlert;
import GUI.HelperWindows.ProductNameFilterHelperWindow;


/**
 * 
 * @author Felix Fink
 *
 *Filter to match the product names of the given cert-alerts to a database of preinserted names.
 */
public class ProductNameFilter extends AbstractAttributeFilter {

	HashMap<String,ArrayList<String>> nameToData; 
	HashMap<String,String> dataToName;
	String newName;
	
	
	/**
	 * Constructor that loads - if existing - the database from a file.
	 */
	@SuppressWarnings("unchecked")
	public ProductNameFilter() {
		super("Product Name Mapping");
		try{
			FileInputStream fin = new FileInputStream("ProductNameHashMaps");
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.nameToData = (HashMap<String,ArrayList<String>>) ois.readObject();
			this.dataToName = (HashMap<String,String>) ois.readObject();
			ois.close();
			fin.close();
		}catch (IOException | ClassNotFoundException e){
			this.nameToData = new HashMap<String,ArrayList<String>>();
			this.dataToName = new HashMap<String,String>();

		}
		
	}

	
	/**
	 * Function that matches the product name of the given cert-alerts with the existing data.
	 * Using two HashMaps to reduce the used processing time a helper GUI is used to gather input from the user.
	 */
	@Override
	public ArrayList<CERTAlert> filterAlerts(ArrayList<CERTAlert> certAlerts) {
		for(int i = 0; i < certAlerts.size(); i++){

			if(!this.nameToData.containsKey(certAlerts.get(i).getProduct_name())){
				if(this.dataToName.containsKey(certAlerts.get(i).getProduct_name())){
					certAlerts.get(i).setProduct_name(this.dataToName.get(certAlerts.get(i).getProduct_name()));
				}else{
					System.out.println(i);
					this.setnewName(null);

					ProductNameFilterHelperWindow helper = new ProductNameFilterHelperWindow(certAlerts.get(i).getProduct_name(), this);
					new Thread(helper).start();;
					//helper.setVisibile(certAlerts.get(i).getProduct_name());
					while(this.newName == null){
						try {
							Thread.sleep(50);
							System.out.println(this.newName);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}	
					if(this.dataToName.containsKey(this.newName)){
						this.newName = this.dataToName.get(newName);
					}
					
					if(this.nameToData.containsKey(this.newName)){
						this.nameToData.get(newName).add(certAlerts.get(i).getProduct_name());
						this.dataToName.put(certAlerts.get(i).getProduct_name(), newName);
						certAlerts.get(i).setProduct_name(newName);
					}else{
						ArrayList<String> helperarray = new ArrayList<String>();
						helperarray.add(certAlerts.get(i).getProduct_name());
						this.nameToData.put(newName, helperarray);
						this.dataToName.put(certAlerts.get(i).getProduct_name(), newName);
						certAlerts.get(i).setProduct_name(newName);
						
					}
					this.newName = null;
										
				}
			}
			
		}
		
		/*
		 * Saving the data to file after having gathered new input during the function call.
		 */
		try {
			FileOutputStream fout = new FileOutputStream("ProductNameHashMaps");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this.nameToData);
			oos.writeObject(dataToName);
			oos.close();
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public void setnewName(String name){
		this.newName = name;
	}

}
