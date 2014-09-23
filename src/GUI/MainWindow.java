package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

import Data.CERTAlert;
import Data.Exporter.AbstractFileExporter;
import Logic.Aggregator.AbstractCertPageParser;
import Logic.Filter.AbstractAttributeFilter;


/**
 * 
 * @author Felix Fink
 *Main GUI, that is divided into 3 areas to select the wanted aggregator, filter and exporter, with a information text field below it.
 */
public class MainWindow {
	
	private ArrayList<AbstractCertPageParser> parserList = new ArrayList<AbstractCertPageParser>();
	private ArrayList<AbstractAttributeFilter> activeFilterList = new ArrayList<AbstractAttributeFilter>();
	private ArrayList<AbstractAttributeFilter> inactiveFilterList = new ArrayList<AbstractAttributeFilter>();
	private ArrayList<AbstractFileExporter> fileExporter = new ArrayList<AbstractFileExporter>();
	private JTextArea messageOutput;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MainWindow(ArrayList<AbstractCertPageParser> parserList, ArrayList<AbstractAttributeFilter> filterList, ArrayList<AbstractFileExporter> fileExporter){
		
		
		/**
		 * Window initialization with combined GridLayouts
		 */
		this.parserList = parserList;
		this.inactiveFilterList = filterList;
		this.fileExporter = fileExporter;
		
		JFrame frame = new JFrame("CERT Aggregator");
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(2,1);
		frame.setLayout(layout);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LayoutManager manager = new GridLayout(2,5 ,15 ,15);
		panel.setLayout(manager);

		/*
		 * Labels as Description / Placeholder in the GridLayout
		 */
		
		Label parserLabel = new Label("Parser");
		panel.add(parserLabel);
		
		Label avtivatedFilterLabel = new Label("active Filters");
		panel.add(avtivatedFilterLabel);
		
		Label spaceholder = new Label("");
		panel.add(spaceholder);
		
		Label spaceholder2 = new Label("");
		panel.add(spaceholder2);
		
		
		Label deavtivatedFilterLabel = new Label("inactive Filters");
		panel.add(deavtivatedFilterLabel);
		
		Label spaceholder3 = new Label("");
		panel.add(spaceholder3);
		
		JTextField filename = new JTextField("Filename");
		panel.add(filename);
		/*
		 * List for existing parsers
		 */
		JList parser = new JList();
		parser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		parser.setLayoutOrientation(JList.VERTICAL);
		parser.setVisibleRowCount(-1);
		DefaultListModel<String> parserNames = new DefaultListModel<String>();
		parser.setModel(parserNames);
		for (int i = 0; i < this.parserList.size(); i++) {
			parserNames.addElement(this.parserList.get(i).getParserName());
		}
		
		panel.add(parser);
		
		
		/*
		 * List for active Filters
		 */
		
		JList activeFilter = new JList();
		activeFilter.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activeFilter.setLayoutOrientation(JList.VERTICAL);
		activeFilter.setVisibleRowCount(-1);
		DefaultListModel<String> activeFilterNames = new DefaultListModel<String>();
		activeFilter.setModel(activeFilterNames);
		panel.add(activeFilter);
		
		
		/*
		 * Button to add / remove filters
		 */
		JButton addFilter = new JButton("<<");

		panel.add(addFilter);
		
		JButton removeFilter = new JButton(">>");
		panel.add(removeFilter);
		
		/*
		 * List for deactive filters		
		 */
		JList deactivatedFilter = new JList();
		deactivatedFilter.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		deactivatedFilter.setLayoutOrientation(JList.VERTICAL);
		deactivatedFilter.setVisibleRowCount(-1);
		DefaultListModel<String> deactivatedFilterNames = new DefaultListModel<String>();
		deactivatedFilter.setModel(deactivatedFilterNames);
		for(int a = 0; a < this.inactiveFilterList.size(); a++){
			deactivatedFilterNames.addElement(this.inactiveFilterList.get(a).getFilterName());
		}
		panel.add(deactivatedFilter);
		
		/*
		 * Button to execute the program with the given selection
		 */
		JButton executeAggregation = new JButton("Execute");
		panel.add(executeAggregation);
		
		
		/*
		 * List for the FileExports
		 */
		JList fileExports = new JList();
		fileExports.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileExports.setLayoutOrientation(JList.VERTICAL);
		fileExports.setVisibleRowCount(-1);
		DefaultListModel<String> fileExportNames = new DefaultListModel<String>();
		fileExports.setModel(fileExportNames);
		for(int b = 0; b < this.fileExporter.size(); b++){
			fileExportNames.addElement(this.fileExporter.get(b).getExporterName());
		}
		panel.add(fileExports);
		
		
	
		/**
		 * ActionListener of the "execute"-button using a SwingWorker
		 */
		
		executeAggregation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>(){

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						messageOutput.setText( messageOutput.getText() + "\nStarting Operation:");

						
						messageOutput.setText( messageOutput.getText() + "\nBeginning Parsing...");
						ArrayList<CERTAlert> alerts = parserList.get(parser.getSelectedIndex()).parsePage();
						messageOutput.setText( messageOutput.getText() + "\nFinished Parsing.");

						for(int i = 0; i < activeFilterList.size(); i++){
							messageOutput.setText( messageOutput.getText() + "\nStarting Filter: " + activeFilterList.get(i).getFilterName());
							activeFilterList.get(i).filterAlerts(alerts);
							messageOutput.setText( messageOutput.getText() + "\nFinished Filter: " + activeFilterList.get(i).getFilterName());
						}
						
						if(fileExports.getSelectedIndex() != -1){
							messageOutput.setText( messageOutput.getText() + "\nBeginning Saving...");
							fileExporter.get(fileExports.getSelectedIndex()).saveToFile(filename.getText(), alerts);
							messageOutput.setText( messageOutput.getText() + "\nFinished Saving.");
						}
						
						messageOutput.setText( messageOutput.getText() + "\nFinished Operation");

					
						return null;
					}
					
				};
				worker.execute();
				
			}	

			
		});
		
		/*
		 * ActionListeners to add / remove filters
		 */
		
		addFilter.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				int index = deactivatedFilter.getSelectedIndex();
				String filterName = deactivatedFilterNames.get(index);
				deactivatedFilterNames.remove(index);
				activeFilterNames.addElement(filterName);
				AbstractAttributeFilter filter = inactiveFilterList.remove(index);
				activeFilterList.add(filter);
				frame.pack();
			}
			
		});
		
		
		removeFilter.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				int index = activeFilter.getSelectedIndex();
				String filterName = activeFilterNames.get(index);
				activeFilterNames.remove(index);
				deactivatedFilterNames.addElement(filterName);
				AbstractAttributeFilter filter = activeFilterList.remove(index);
				inactiveFilterList.add(filter);
				frame.pack();
			}
			
		});
		

		/**
		 * Formatting of the Window
		 */
		JPanel panel2 = new JPanel();
		messageOutput = new JTextArea(10,80);
		messageOutput.setSize(200, 50);
		JScrollPane scrollPane = new JScrollPane(messageOutput);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel2.add(scrollPane);
		

		frame.add(panel);
		frame.add(panel2);
		frame.pack();
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
		frame.setVisible(true);
	}
	
}
