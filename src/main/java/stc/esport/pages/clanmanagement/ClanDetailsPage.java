package stc.esport.pages.clanmanagement;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;

public class ClanDetailsPage extends PageBase {

	WebDriver driver;
	Properties properties;
	
	// Constractor
	public ClanDetailsPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	

	}

	public boolean checkActionExist(String action) {
		waitForPageLoadWithoutSpinner(driver);
		By actionLocator =By.xpath("//a[contains(@class,'"+action+"')]");
		if(isElementExist(driver, actionLocator, action))
			return true;
		return false;
	}
	
	
}
