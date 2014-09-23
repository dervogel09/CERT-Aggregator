package Logic.Filter;

import java.util.ArrayList;


import Data.CERTAlert;
/**
 * 
 * @author Felix Fink
 *
 *Filter to reduce the given amount of cert-alerts by using the value of degree_of_danger and 
 *removing all alerts with a value of 5 or lower, as long as the parameter has previously been
 *transformed into a scale from 1 to 10.
 */
public class RiskAboveAverageFilter extends AbstractAttributeFilter {

	public RiskAboveAverageFilter() {
		super("Danger above Average-Filter");
	}

	@Override
	public ArrayList<CERTAlert> filterAlerts(ArrayList<CERTAlert> certAlerts) {
		for(int i = 0; i < certAlerts.size(); i++){
			try{
				if(Integer.parseInt(certAlerts.get(i).getDegree_of_danger()) < 6){
					certAlerts.remove(i);
					i = i -1;
				}	
			}catch(NumberFormatException e){
				
			}
		}
		return certAlerts;
	}

}
