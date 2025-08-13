// Home Page 
// Version: 1.0 : initial creation
// Date: 10-01-2023
//-----------------------------------

package stc.esport.pages;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import com.shaft.tools.io.ReportManager;

import stc.esport.base.PageBase;

public class HomePage extends PageBase {

	// Variables
	public WebDriver driver;
	public Properties properties;

	// Functions
	public HomePage(WebDriver driver,Properties properties) {
		this.driver = driver;
		this.properties = properties;

	}

	// Elements Locators
	By loginBtn = By.xpath("//button[@class='btn login-btn']");
	By switcher = By.xpath("//div[@class='lang-switcher']//*[text()='E']");
	By switcherLang = By.xpath("//div[@class='lang-switcher']//*[text()='E']");
	
	//////////////////////////////////////////////////////////
	////////////////////// Actions //////////////////////////

	public void clickLoginLink() {
		
		waitUntilElementPresent(driver, loginBtn);
		if(isElementExist(driver, switcher, "switcher in English"))
		{
			driver.element().click(switcher);
		}
		waitUntilElementClickable(driver, loginBtn);
		driver.element().click(loginBtn);
		//executeJavaScript(driver, loginBtn, "arguments[0].click();");
		ReportManager.log("Click on Login link from the Main Menu");
		waitForPageLoadWithoutSpinner(driver);
	}

}
