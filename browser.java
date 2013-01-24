// Copyright Disha Gandhi 2012

/* Assignment1 - Creating a simple web browser in Java
 * Requirements:
 * 1. Render HTML elements including rich text (bold, italics, underline, etc) and images 
 * (see JEditorPane in the Java API)
 * 2. The browser shall support clicking on hyperlinks. 
 * Clicking on the hyperlink will cause the browser to change the page contents to the location 
 * specified in the hyperlink or display an error in a status bar
 * 3. The rendered HTML shall not be editable
 * 4. The browser shall have a forward and back button supporting browser history. 
 * The browser shall retain not less than ten elements in the history.
 * 5. The browser shall have an address bar that shows the current URL. Note that this text can be manually edited. Hitting the enter key will result in the browser attempting to load the URL in the address bar.
 * 6. The browser shall have a home button. Clicking this button will take users to http://www.google.com. As an option, allow users to specify their home page.
 * 7. The browser shall have a status bar showing error conditions (i.e. cannot load page, cannot parse URL, etc).
 * 8. The browser shall use a JFrame as its top level component. The JFrame's title shall be the same as specified in the <title> tag in the current page.
 * 9. The browser shall display scrollbars as necessary if HTML extends past the boundary of the browser frame
 * 10. The browser may support favorites
 * 11. The browser may save page contents locally (consider images, other documents)
 * 12. The browser may have a help system
 * 13. The browser may show up centered on screen
 * 14. The browser may have other cool features that will impress your peers (and the instructor)
 * 
 * Acknowledgements:
 * 1. Read about creating mini browsers from net in general, but did not follow any page
 * http://forums.devx.com/showthread.php?149187-Creating-a-web-browser-in-Java
 * 
 */


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;

public class browser extends JFrame implements HyperlinkListener{
	
	static JEditorPane editor = new JEditorPane();
	static JLabel status = new JLabel();
	
	public browser(String destination) {
		// Total browser
		 
		this.setName("browser");
		this.setTitle("about:blank");
		this.setSize(1000,700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		MyPanel topPanel = new MyPanel(this);

		// need the editor pane to display contents
		editor.setName("editor");
		editor.setEditable(false); // we do not want content to change
		editor.setEditorKit(new HTMLEditorKit()); // we want to be able to render HTML contents
		editor.setContentType("text/html");
		editor.addHyperlinkListener(this);
		
		// Add a scroll pane that displays the contents of this editor
		JScrollPane scroll = new JScrollPane(editor);
		scroll.setName("scrollPane");
		
		this.getContentPane().add(scroll, BorderLayout.CENTER);
		this.getContentPane().add(topPanel, BorderLayout.NORTH);
		this.getContentPane().add(status, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}

	public void hyperlinkUpdate(HyperlinkEvent event) {
		HyperlinkEvent.EventType eventType = event.getEventType();
		if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
			MyPanel.addNewPage(event.getURL().toString(), this);
		}
	}
	
	public static void main(String[] args) {
		
			EventQueue.invokeLater(new Runnable() {
				public void run(){
					String destination = "about:blank";
					new browser(destination);
				}
	});
	}
}
