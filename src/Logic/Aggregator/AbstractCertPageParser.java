package Logic.Aggregator;

import java.util.ArrayList;

import Data.CERTAlert;

/**
 * 
 * @author Felix Fink
 *
 *Abstract class to extend in order to create a parser to read in CERT-messages.
 *The parserName is used as a display name in the GUI.
 */
public abstract class AbstractCertPageParser {

	String parserName;
	
	public AbstractCertPageParser(String parserName){
		this.parserName = parserName;
	}

	public abstract ArrayList<CERTAlert> parsePage();
	
	
	/**
	 * @return the parserName
	 */
	public String getParserName() {
		return parserName;
	}
}
