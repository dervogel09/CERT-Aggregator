package Logic;

import java.util.ArrayList;
import Data.Exporter.AbstractFileExporter;
import Data.Exporter.JSONExport;
import Data.Exporter.PlainTextExport;
import GUI.MainWindow;
import Logic.Aggregator.CERTBundParser;
import Logic.Aggregator.AbstractCertPageParser;
import Logic.Aggregator.USCertRSSFeedParser;
import Logic.Filter.DateStandardizationAmerican;
import Logic.Filter.DegreeOfDangerMappingFilter;
import Logic.Filter.ProductNameFilter;
import Logic.Filter.RiskAboveAverageFilter;
import Logic.Filter.AbstractAttributeFilter;
/**
 * 
 * @author Felix  Fink
 *
 *Class to contain the main-function and configure the components.
 */
public class Main {

	public static void main(String[] args) {
		/*
		 * Initialising Program, the three ArrayLists are used to register the created Extensions
		 * in the thre Categories CERT-Parser, Filter and Exporter.
		 */
		
		ArrayList<AbstractCertPageParser> parserList = new ArrayList<AbstractCertPageParser>();
		ArrayList<AbstractAttributeFilter> filterList = new ArrayList<AbstractAttributeFilter>();
		ArrayList<AbstractFileExporter> fileExporterList = new ArrayList<AbstractFileExporter>();
		
		/*
		 * Register your created Parser here by adding it to parserList.
		 */
		parserList.add(new CERTBundParser());
		parserList.add(new USCertRSSFeedParser());

		/*
		 * Register your created Filter here by adding it to filterList.
		 */
		filterList.add(new DateStandardizationAmerican());
		ProductNameFilter nameFilter = new ProductNameFilter();
		filterList.add(nameFilter);
		filterList.add(new DegreeOfDangerMappingFilter());
		filterList.add(new RiskAboveAverageFilter());
		
		/*
		 * Register your created Exporter here by adding it to fileExporterList.
		 */
		fileExporterList.add(new PlainTextExport());
		fileExporterList.add(new JSONExport());
		

		//MainWindow window = 
		new MainWindow(parserList, filterList, fileExporterList);
		
	}

}
