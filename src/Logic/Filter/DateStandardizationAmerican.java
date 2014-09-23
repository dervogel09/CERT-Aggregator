package Logic.Filter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Data.CERTAlert;
/**
 * 
 * @author Felix Fink
 *
 *Filter to parse the date of creation and the date of the last update of the given cert-alerts
 *to the american date format MM.DD.YYYY
 */
public class DateStandardizationAmerican extends AbstractAttributeFilter {

	public DateStandardizationAmerican() {
		super("American Date Standard");
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<CERTAlert> filterAlerts(ArrayList<CERTAlert> certAlerts) {
		for(CERTAlert a : certAlerts){
			DateFormat df = new SimpleDateFormat("mm.dd.yyyy");
			try {
				if(a.getCreate_date()!= null)
					a.setCreate_date(df.parse(a.getCreate_date().toString()));
				if(a.getUpdatde_date() != null)
					a.setUpdatde_date(df.parse(a.getUpdatde_date().toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
