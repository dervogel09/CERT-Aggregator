package Logic.Filter;

import java.util.ArrayList;

import Data.CERTAlert;


/**
 * 
 * @author Felix Fink
 *
 *Filter to map the degree of danger used by www.cert-bund.de to a format based on a metric from 1 to 10.
 */
public class DegreeOfDangerMappingFilter extends AbstractAttributeFilter {

	public DegreeOfDangerMappingFilter() {
		super("CERT-Bund Mapping Danger");
	}

	@Override
	public ArrayList<CERTAlert> filterAlerts(ArrayList<CERTAlert> certAlerts) {
		for(int i = 0; i < certAlerts.size(); i++){
			if(certAlerts.get(i).getDegree_of_danger() != null){
				switch(certAlerts.get(i).getDegree_of_danger()){
				case(" sehr hoch"):
					certAlerts.get(i).setDegree_of_danger("10");
					break;
				case(" hoch"):
					certAlerts.get(i).setDegree_of_danger("8");
					break;
				case(" mittel"):
					certAlerts.get(i).setDegree_of_danger("6");
					break;
				case(" niedrig"):
					certAlerts.get(i).setDegree_of_danger("4");
					break;
				case(" sehr niedrig"):
					certAlerts.get(i).setDegree_of_danger("2");
					break;
				default:
					System.out.println(certAlerts.get(i).getDegree_of_danger());
					break;
				}	
			}
		}
		return certAlerts;
	}

}
