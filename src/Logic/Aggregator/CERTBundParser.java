package Logic.Aggregator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import Data.CERTAlert;


/**
 * 
 * @author Felix Fink
 *
 *Parser to read in CERT-Alerts from the webpage https://www.cert-bund.de/overview/AdvisoryShort
 *
 *This parser uses matching of the html source code to identify the parameters.
 */
public class CERTBundParser extends AbstractCertPageParser {

	private String baseLink = "https://www.cert-bund.de";
	private ArrayList<CERTAlert> certAlerts = new ArrayList<CERTAlert>();
	
	public CERTBundParser() {
		super("CERT-Bund Webpage");
	}

	@Override
	public ArrayList<CERTAlert> parsePage() {
		
		ArrayList<String> AlertLinks = new ArrayList<String>();
		/*
		 * Searching through the list of CERT-alerts and collecting links to the respective articles.
		 */
		try {
			Document doc = Jsoup.connect("https://www.cert-bund.de/overview/AdvisoryShort").get();
			
			String htmlsource = doc.toString();
			while(htmlsource.indexOf("<td class=\"search-results-col-4 search-result-0\"> <a class=\"search-result-link\" href=\"") != -1){
				htmlsource = htmlsource.substring(htmlsource.indexOf("<td class=\"search-results-col-4 search-result-0\"> <a class=\"search-result-link\" href=\"") + "<td class=\"search-results-col-4 search-result-0\"> <a class=\"search-result-link\" href=\"".length());
				int endIndex = htmlsource.indexOf("\">");
				AlertLinks.add(htmlsource.substring(0, endIndex));
			}
			
			htmlsource = doc.toString();
			while(htmlsource.indexOf("<td class=\"search-results-col-4 search-result-1\"> <a class=\"search-result-link\" href=\"") != -1){
				htmlsource = htmlsource.substring(htmlsource.indexOf("<td class=\"search-results-col-4 search-result-1\"> <a class=\"search-result-link\" href=\"") + "<td class=\"search-results-col-4 search-result-1\"> <a class=\"search-result-link\" href=\"".length());
				int endIndex = htmlsource.indexOf("\">");
				AlertLinks.add(htmlsource.substring(0, endIndex));
			}
			
			
			/*
			 * Analyzing all the collected articles to aggregate the CERT-messages
			 */
			for(int i = 0; i < AlertLinks.size(); i++ ){
				CERTAlert alert = new CERTAlert();
				alert.setLanguage("de");
				doc = Jsoup.connect(baseLink + AlertLinks.get(i)).get();
				String sourcehtml = doc.toString();
				
				//Identify ID
				sourcehtml = sourcehtml.substring(sourcehtml.indexOf("<h1 class=\"top-header\">") + "<h1 class=\"top-header\">".length());
				alert.setId( sourcehtml.substring(0, sourcehtml.indexOf("</h1>")));
				
				//Identify Risk
				sourcehtml = sourcehtml.substring(sourcehtml.indexOf("<span class=\"severity-text\">Risiko:") + "<span class=\"severity-text\">Risiko:".length());
				alert.setDegree_of_danger(sourcehtml.substring(0, sourcehtml.indexOf("</span>")));
				
				//Identify Title
				sourcehtml = sourcehtml.substring(sourcehtml.indexOf("<td class=\"info-col-1\">Titel:</td>") +  "<td class=\"info-col-1\">Titel:</td>".length() + "<td class=\"info-col-2\">".length() + "\"info-col-2\">".length());
				alert.setTitle(sourcehtml.substring(0, sourcehtml.indexOf("</td>")));
			
				//Identify Date of Creation
				sourcehtml = sourcehtml.substring(sourcehtml.indexOf("<td class=\"info-col-1\">Datum:</td>") +  "<td class=\"info-col-1\">Datum:</td>".length() + "<td class=\"info-col-2\">".length() + "\"info-col-2\">".length());
				DateFormat df = new SimpleDateFormat("dd.mm.yyyy");
				try {
					alert.setCreate_date(df.parse(sourcehtml.substring(0, sourcehtml.indexOf("</td>"))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Identify Productname
				sourcehtml = sourcehtml.substring(sourcehtml.indexOf("<td class=\"info-col-1\">Software:</td>") +  "<td class=\"info-col-1\">Software:</td>".length() + "<td class=\"info-col-2\">".length() + "\"info-col-2\">".length());
				alert.setProduct_name(sourcehtml.substring(0, sourcehtml.indexOf("</td>")));
				
				//Identify Platform
				sourcehtml = sourcehtml.substring(sourcehtml.indexOf("<td class=\"info-col-1\">Plattform:</td>") +  "<td class=\"info-col-1\">Plattform:</td>".length() + "<td class=\"info-col-2\">".length() + "\"info-col-2\">".length());
				alert.setOperating_system(sourcehtml.substring(0, sourcehtml.indexOf("</td>")));
	
				//Identify Platform
				sourcehtml = sourcehtml.substring(sourcehtml.indexOf("<td class=\"info-col-1\">Auswirkung:</td>") +  "<td class=\"info-col-1\">Auswirkung:</td>".length() + "<td class=\"info-col-2\">".length() + "\"info-col-2\">".length());
				alert.setResult(sourcehtml.substring(0, sourcehtml.indexOf("</td>")));
	
				//Identify Type
				sourcehtml = sourcehtml.substring(sourcehtml.indexOf("<td class=\"info-col-1\">Remoteangriff:</td>") +  "<td class=\"info-col-1\">Remoteangriff:</td>".length() + "<td class=\"info-col-2\">".length() + "\"info-col-2\">".length());
				alert.setType("Remoteangriff: " + sourcehtml.substring(0, sourcehtml.indexOf("</td>")));
	
				//Identify Reference
				sourcehtml = sourcehtml.substring(sourcehtml.indexOf("<td class=\"info-col-1\">Bezug:</td>") +  "<td class=\"info-col-1\">Bezug:</td>".length() + "<td class=\"info-col-2\">".length() + "\"info-col-2\">".length());
				alert.setReference(Jsoup.parse(sourcehtml.substring(0, sourcehtml.indexOf("</td>"))).text());
	
				//Identify Desciption
				sourcehtml = sourcehtml.substring(sourcehtml.indexOf("<p class=\"textblock-content\">") + "<p class=\"textblock-content\">".length() );
				alert.setDescription((Jsoup.parse(sourcehtml.substring(0, sourcehtml.indexOf("</td>"))).text()));
				this.certAlerts.add(alert);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this.certAlerts;
	}

}
