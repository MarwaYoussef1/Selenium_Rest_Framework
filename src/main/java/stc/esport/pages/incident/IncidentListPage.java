package stc.esport.pages.incident;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.utils.Utils;

public class IncidentListPage extends PageBase{

	WebDriver driver;
	Properties properties;
	
	// identifier
	 By addIncident=By.xpath("(//a[@href='/authnticate/incidents/add'])[1]");
	 By search= By.xpath("//input[contains(@placeholder,'Search')]");
   
   
	// Constractor
	public IncidentListPage(WebDriver driver, Properties properties) {
				this.driver = driver;
				this.properties = properties;
			}
	
	public void clickOnAddIncidentButton() {
		driver.element().click(addIncident);
	}
	
	private By searchWithIncidentName(String name) {		
		By incidentTitle= By.xpath("(//div[contains(@class,'card-title')]//span[text()='"+name+"'])[1]");
		driver.element().type(search, name);
		waitUntilElementPresent(driver, incidentTitle);
	    if(isElementExist(driver, incidentTitle, name))
	    {
	    	return incidentTitle;
	    }
	    return null;
	}
	
	public boolean checkIncidentAdded(String plainTiff) {
		By plainTiffLocator= By.xpath("//p[@class='value' and text()="+"'"+plainTiff+" '"+"]");
		
		 return isElementExist(driver, plainTiffLocator, plainTiff);
	}
	
	public void openIncidentByName(String name) {
		waitForPageLoadWithoutSpinner(driver);
		By incidentTitle = searchWithIncidentName(name);
		if (incidentTitle != null) {
			driver.element().click(incidentTitle);
			waitForPageLoadWithoutSpinner(driver);
		}
	}
	

	
	
	
}