package GUI.HelperWindows;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Logic.Filter.ProductNameFilter;

/**
 * 
 * @author Felix Fink
 *
 *Helperwindow used to handle the User-Input for the ProductNameFilter
 *It uses the given ProductName to display it in a Textfield and offers the option to change it before
 *returning it to the filter.
 */
public class ProductNameFilterHelperWindow implements Runnable {

	private String productName;
	private ProductNameFilter filter;
	private JFrame frame;
	private JTextField text;


	public ProductNameFilterHelperWindow(String productName, ProductNameFilter filter){
		this.productName = productName;
		this.filter = filter;
	}
	
	public void setVisibile(String productName){
		this.productName = productName;
		frame.setVisible(true);
	}

	@Override
	public void run() {
		frame = new JFrame("Product Name Matcher");
		JPanel panel = new JPanel();
		
		text = new JTextField(productName);
		Label label = new Label("Enter the Primary Name for the Product");
		JButton button = new JButton("Accept");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.err.println(text.getText());
				filter.setnewName(text.getText());
				//frame.setVisible(false);
				frame.dispose();
			}
			
		});	
		LayoutManager manager = new GridLayout(1,4 ,15 ,15);
		panel.setLayout(manager);
		panel.add(label);
		panel.add(text);
		panel.add(button);
		frame.add(panel);
		frame.pack();
		
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
		frame.setVisible(true);
		

	}
	
	
}
