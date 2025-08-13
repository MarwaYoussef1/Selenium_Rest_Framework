package stc.esport.pages.clubmanagement;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;

public class ClubDetailsPage extends PageBase {

	WebDriver driver;
	Properties properties;
	

	// identifier
	
	By manageProfile = By.xpath("//a[contains(@class,'manage-profile-btn')]");
	
	
	// Constractor
	public ClubDetailsPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	

	}

	public boolean checkActionExist(String action) {
		waitForPageLoadWithoutSpinner(driver);
		By actionLocator =By.xpath("//button[contains(text(),'"+action+"')]");
		if(isElementExist(driver, actionLocator, action))
			return true;
		return false;
	}
	
	public boolean isManageProfileExist() {
		waitForPageLoadWithoutSpinner(driver);
		if(isElementExist(driver, manageProfile, "Manage profile"))
			return true;
		return false;
	}
	
	
	
}
