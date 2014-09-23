package Logic.Aggregator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import Data.CERTAlert;


/**
 * 
 * @author Felix Fink
 *
 *Parser to read in CERT-Alerts from the RSS-feed https://www.us-cert.gov/ncas/alerts
 *
 *This parser uses regular expressions to identify the parameters.
 */
public class USCertRSSFeedParser extends AbstractCertPageParser {

	private String rssFeedLink = "https://www.us-cert.gov/ncas/alerts";
	private String baseLink = "https://www.us-cert.gov";
	private ArrayList<CERTAlert> certAlerts = new ArrayList<CERTAlert>();

	
	public USCertRSSFeedParser() {
		super("US CERT RSS-Feed");
	}

	@Override
	public ArrayList<CERTAlert> parsePage() {
		Document doc = new Document("");
		ArrayList<String> alertLinks = new ArrayList<String>();

		try {
			doc = Jsoup.connect(this.rssFeedLink).get();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * Identifying the referenced cert-alert articles
		 */
		
		Pattern p = Pattern.compile("class=\"views-row views-row-[0-9] views-row-((odd)|(even))( views-row-first)?( views-row-last)?\"> <span class=\"document_id\">[a-zA-Z0-9-]* : </span> <span class=\"document_title\"><a href=\"");
		Matcher m = p.matcher(doc.toString());
		while(m.find()){

			alertLinks.add(this.baseLink + doc.toString().substring(m.end(), doc.toString().indexOf("\"", m.end())));

		}
		
		/*
		 * Going through the collected articles to extreact the cert-alerts
		 */
		
		for(int i = 0; i < alertLinks.size(); i++){
			try {
				doc = Jsoup.connect(alertLinks.get(i)).get();
				CERTAlert alert = new CERTAlert();
				String htmlcode = doc.toString();
				
				alert.setId(doc.toString().substring(doc.toString().indexOf("<h1 id=\"page-title\"") + "<h1 id=\"page-title\"".length() , doc.toString().indexOf("</h1>")));
				
				alert.setTitle( doc.toString().substring(doc.toString().indexOf("<h2 id=\"page-sub-title\">") + "<h2 id=\"page-sub-title\">".length() , doc.toString().indexOf("</h2>")));
				
				
				alert.setProduct_name(Jsoup.parse(
							htmlcode.substring(htmlcode.indexOf("<div class=\"field field-name-field-alert-systems-affected field-type-text-long field-label-hidden\">"), htmlcode.indexOf("<a id=\"overview\"></a>"))).text());
				
				alert.setDescription(Jsoup.parse(
						htmlcode.substring(htmlcode.indexOf("<div class=\"field field-name-field-alert-overview field-type-text-long field-label-hidden\">"), htmlcode.indexOf("<a id=\"description\"></a>"))).text());
				
				alert.setCause(Jsoup.parse(
						htmlcode.substring(htmlcode.indexOf("<div class=\"field field-name-body field-type-text-with-summary field-label-hidden\">"), htmlcode.indexOf("<a id=\"impact\"></a>"))).text());
				
				alert.setResult(Jsoup.parse(
						htmlcode.substring(htmlcode.indexOf("<div class=\"field field-name-field-alert-impact field-type-text-long field-label-hidden\">"), htmlcode.indexOf("<a id=\"solution\"></a>"))).text());
				
				alert.setSolution(Jsoup.parse(
						htmlcode.substring(htmlcode.indexOf("<div class=\"field field-name-field-alert-solution field-type-text-long field-label-hidden\">"), htmlcode.indexOf("<a id=\"references\"></a>"))).text());

				alert.setReference(Jsoup.parse(
						htmlcode.substring(htmlcode.indexOf("<div class=\"field field-name-field-alert-references field-type-link-field field-label-hidden clearfix\">"), htmlcode.indexOf("<a id=\"revisions\"></a>"))).text());
				
				this.certAlerts.add(alert);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return this.certAlerts;
	}
	

}
