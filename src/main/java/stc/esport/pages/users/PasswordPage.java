package stc.esport.pages.users;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;



public class PasswordPage extends PageBase {

	// Variables
	public WebDriver driver;
	public Properties properties;
	

	By proceed=By.xpath("//a[text()='» Click here to proceed']");
	By password = By.id("password-new");
	By confirmPassword=By.id("password-confirm");
	By submit=By.xpath("//input[@type='submit']");
	By successMsg=By.xpath("//p[@class='instruction']");

	// functions
	public PasswordPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
		
	}

	// Add user password

	public  boolean setPassword(String updatePasswordLink, String ps) {
		driver.browser().navigateToURL(updatePasswordLink);
		driver.element().click(proceed);
		driver.element().waitToBeReady(submit);
		driver.element().type(password, ps);
		driver.element().type(confirmPassword, ps);
		driver.element().click(submit);
		if (isMsgExistandDisplayed(driver, successMsg, properties.getProperty("userAccountUpdateMsg"))) {
			switchingBetweenTabs(driver, 0);
			return true;
			
		}
		return false;
	}


}
