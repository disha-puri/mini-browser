import static org.junit.Assert.*;

import java.awt.Container;

import javax.swing.JFrame;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.uispec4j.Button;
import org.uispec4j.Key;
import org.uispec4j.Panel;
import org.uispec4j.Trigger;
import org.uispec4j.UISpec4J;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowInterceptor;


public class browserTest extends TestCase{
	
	static{
		UISpec4J.init();
	}

	@Test
	public void testButtons(){
		WindowInterceptor.init(new Trigger(){
			
		public void run() throws Exception {
			Panel panel = new Panel (new browser("about:blank"));
		    Assert.assertFalse(panel.getButton("backButton").getAwtComponent().isEnabled());
		    Assert.assertFalse(panel.getButton("forwardButton").getAwtComponent().isEnabled());		
	}
		});
	}
	
	
	@Test
	public void clickHome() {
		WindowInterceptor.init(new Trigger(){
			
			public void run() throws Exception {
				Panel panel = new Panel (new browser("about:blank"));
				panel.getButton("Home").click();
				Assert.assertEquals(panel.getTextBox("textField").getText(), "http://www.google.com");
		}
	});

	}
	
	@Test
	public void clickBack() {
		WindowInterceptor.init(new Trigger(){
			
			public void run() throws Exception {
				Panel panel = new Panel (new browser("about:blank"));
				panel.getButton("Home").click();
				Assert.assertEquals(panel.getTextBox("textField").getText(), "http://www.google.com");
				panel.getTextBox("textField").setText("http://cecs.pdx.edu/~xie");
				panel.getButton("Go").click();
				Assert.assertTrue(panel.getButton("backButton").getAwtComponent().isEnabled());
				panel.getButton("backButton").click();
				Assert.assertEquals(panel.getTextBox("textField").getText(), "http://www.google.com");
				Assert.assertTrue(panel.getButton("forwardButton").getAwtComponent().isEnabled());
		}
	});

	}
	
	@Test
	public void clickForward() {
		WindowInterceptor.init(new Trigger(){
			
			public void run() throws Exception {
				Panel panel = new Panel (new browser("about:blank"));
				panel.getButton("Home").click();
				Assert.assertEquals(panel.getTextBox("textField").getText(), "http://www.google.com");
				panel.getTextBox("textField").setText("http://cecs.pdx.edu/~xie");
				panel.getTextBox("textField").pressKey(Key.ENTER);
				Assert.assertTrue(panel.getButton("backButton").getAwtComponent().isEnabled());
				panel.getButton("backButton").click();
				Assert.assertEquals(panel.getTextBox("textField").getText(), "http://www.google.com");
				Assert.assertTrue(panel.getButton("forwardButton").getAwtComponent().isEnabled());
				panel.getButton("forwardButton").click();
				Assert.assertEquals(panel.getTextBox("textField").getText(), "http://cecs.pdx.edu/~xie");
				Assert.assertFalse(panel.getButton("forwardButton").getAwtComponent().isEnabled());
		}
	});
	}	
}

