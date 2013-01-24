
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyPanel extends JPanel {
			
	static JButton backButton = new JButton("<<==");
	static JButton forwardButton = new JButton("==>>");
	static int currentPage = -1;
	static ArrayList<String> pageList = new ArrayList<String>();
	static JTextField url;

	public MyPanel(final browser frame) {
	
		this.setName("topPanel");
		this.setLayout(new FlowLayout());
		
		//addNewPage("http://www.google.com", frame);
		
		// Add a button for going back
		backButton.setName("backButton");
		backButton.setEnabled(false);
		backButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {		
				//URL presentPage = editor.getPage();
			System.out.println("\nPressed back Button on"+currentPage);
			System.out.println("Currently on" + pageList.get(currentPage));
			if (currentPage > 0){
			try {
				url.setText(pageList.get(currentPage - 1));
				browser.editor.setPage(pageList.get(currentPage - 1));
				URL title = browser.editor.getPage();
				String t = getTitle(title);
				frame.setTitle(t);
				browser.status.setText("Loaded previous page" + (currentPage - 1));
				currentPage = currentPage - 1;
					} catch (IOException e) {
						browser.status.setText("Error: " + e.getMessage());
					}
				}

				if (currentPage == 0){
					backButton.setEnabled(false);
				}
				
				forwardButton.setEnabled(true);
				printPageList();
			}
		});		
		this.add(backButton);
		
		// Add a button for going forward
		forwardButton = new JButton("==>>");
		forwardButton.setName("forwardButton");
		forwardButton.setEnabled(false);
		forwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (currentPage < (pageList.size() - 1)){
					try {
						url.setText(pageList.get(currentPage + 1));
						browser.editor.setPage(pageList.get(currentPage + 1));
						frame.setTitle(getTitle(browser.editor.getPage()));
						browser.status.setText("Loaded next page" + (currentPage + 1));
						currentPage = currentPage + 1;
					} catch (IOException e) {
						browser.status.setText("Error: " + e.getMessage());
						browser.editor.setText("");
					}
				}

				if (currentPage == (pageList.size() - 1)){
					forwardButton.setEnabled(false);
				}
				
				backButton.setEnabled(true);
				printPageList();
			}
		});		
		this.add(forwardButton);
		

		// text field to enter URL
		this.add(new JLabel("Enter url:"));
		url = new JTextField(30);
		url.setName("textField");
		url.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				addNewPage(url.getText(), frame);
			}
		});
		this.add(url);
		
		// add Go Button
		JButton Go = new JButton("GO");
		Go.setName("Go");
		Go.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNewPage(url.getText(), frame);
			}
		});	
		this.add(Go);

		// add Home button
		JButton home = new JButton("HOME");
		home.setName("Home");
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNewPage("http://www.google.com", frame);
			}
		});	
		this.add(home);
		
	}
	
	public static void addNewPage (String toDisplay, browser frame){
		try {
			// get url of current page
			URL current = browser.editor.getPage();
			
			if ((url.getText() == null) || (url.getText() != toDisplay)){
				// set new page to JField and editor pane
				url.setText(toDisplay);
				browser.editor.setPage(toDisplay);
			
				String title = getTitle(browser.editor.getPage());
				frame.setTitle(title);
			
				pageList.add(toDisplay);
				currentPage ++;
		
				if (currentPage >= 1){
					backButton.setEnabled(true);
				}
				forwardButton.setEnabled(false);
				browser.status.setText("Successfully loaded page");
			}
		} catch (IOException e) {
			browser.status.setText("Error: " + e.getMessage());
			browser.editor.setText("");
		}
		printPageList();
	}
	
	public static String getTitle(URL page){
		
		String title = null;
		try
		    {
		      //open file
		      BufferedReader in = new BufferedReader(new InputStreamReader(page.openStream()) );
		      String line = in.readLine();
		      while(line != null)
		      {		    	 
		    	if (line.indexOf("<title>") >= 0){
		    		int index1 = line.indexOf("<title>");
		    		int lastIndex = index1 + "<title>".length();
		    		int index2 = line.indexOf("</title>");
		    		title = line.substring(lastIndex, index2);
		    		break;
		    	}
		    	line = in.readLine();
		      }
		    }
		catch(IOException e){
			browser.status.setText("Title not found");
		}
		
		return title;
		
	}
	
	public static void printPageList (){
		int i=0;
		System.out.println("Printing page list: ");
		while (i < pageList.size()){
			System.out.println(pageList.get(i));
			i++;
		}
		//System.out.println("Current page "+getTitle(browser.editor.getPage()));
	}



}
