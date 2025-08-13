// login Page 
// Date: 10-01-2023
//-----------------------------------
package stc.esport.pages;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import com.shaft.tools.io.ReportManager;

import stc.esport.base.PageBase;

public class LoginPage extends PageBase {

	// Variables
	public WebDriver driver;
	
	public Properties properties;
	public DashBoardPage dashBoardPageObj;

	// Define locators
	By emailRadio = By.id("byMail");
	By emailInput = By.id("email");
	By pageTitle = By.xpath("//h1[@class='login-title']");
	By passwordInput = By.id("password");
	By loginBtn = By.id("kc-login");
	By createNewAccBtn = By.xpath("//button[@class='btn register-btn']");
	By invalidLoginMsg = By.xpath("//span[contains(@class,'feedback-text')]");
	By switcher = By.xpath("//a[@class='head-translate']");
	By switcherLang = By.xpath("//span[contains(text(),'English')]");
	// Functions
	public LoginPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
		dashBoardPageObj = new DashBoardPage(driver, properties);
	}

	// login with e-mail and password
	public boolean login(String us, String pass) {
		waitUntilElementPresent(driver, switcher);
		if(isElementExist(driver, switcherLang, "switcher in English"))
		{
			driver.element().click(switcherLang);
		}
		fillLoginForm(us, pass);
		dashBoardPageObj.waitForDashBoardPageLoaded();
		return true;
	}

	// Invalid login with e-mail and password
	public boolean invalidLogin(String us, String pass, String message) {
		fillLoginForm(us, pass);
		if (isMsgExistandDisplayed(driver, invalidLoginMsg, message))
			return true;
		else
			return false;
	}

	private void fillLoginForm(String us, String pass) {
		
		waitUntilElementPresent(driver, emailRadio);
		driver.element().click(emailRadio);
		ReportManager.log("UserName will be Entered : [" + us + "] Successfully");
		driver.element().type(emailInput, us);
		ReportManager.log("UserName Entered : [" + us + "] Successfully");
		driver.element().type(passwordInput, pass);
		ReportManager.log("Password Enter Successfully");
		driver.element().click(loginBtn);
	}

	public void clickOnCreateNewAccBtn() {
		// homePageObj.clickLoginLink();
		waitUntilElementPresent(driver, emailRadio);
		driver.element().click(createNewAccBtn);
		sleep(7000);
	}

	public String getPageTitle() {
		return driver.element().getText(pageTitle);
	}

}