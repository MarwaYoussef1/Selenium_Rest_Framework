package stc.esport.pages.notification;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;

public class NotificationPage extends PageBase{
	
	public WebDriver driver;
	public Properties properties;

	
	
	// identifier
	By notificationIcon= By.xpath("//fa-icon[@icon='bell']");
	By firstNotificationText= By.xpath("(//h4[contains(@class,'notification')])[1]");
	
	
	
	
	// Constractor
	  public NotificationPage(WebDriver driver, Properties properties) {
					this.driver = driver;
					this.properties = properties;
				}
	  
	  public boolean checkNotficationContent(String notificationText) {
		  
		  waitForPageLoadWithoutSpinner(driver);
		  waitUntilElementClickable(driver, notificationIcon);
		  driver.element().click(notificationIcon);
		  driver.element().waitToBeReady(firstNotificationText);
		  By notification=By.xpath("//h4[contains(text(),'"+notificationText+"')]");
		  if (isElementExist(driver, notification, notificationText))
			  return true;
			return false;
	  }
	  
	  public boolean checkNotficationContentAndSelectIt(String notificationText) {
		  if (checkNotficationContent(notificationText)) {
					  driver.element().click(firstNotificationText);
				      return true;
	                }else {
	                  return false;
	                }
				
	  }		
	  
	  
				
				
				
}
