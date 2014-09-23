package Logic.Filter;

import java.util.ArrayList;

import Data.CERTAlert;


/**
 * 
 * @author Felix Fink
 *
 *Abstract class to extend in order to create filters.
 *The filtername is used as a display name in the GUI.
 */
public abstract class AbstractAttributeFilter {

	private String filterName;
	
	public AbstractAttributeFilter(String filterName){
		this.filterName = filterName;
	}
	
	
	public abstract ArrayList<CERTAlert> filterAlerts(ArrayList<CERTAlert> certAlerts);

	/**
	 * @return the filterName
	 */
	public String getFilterName() {
		return filterName;
	}
}
