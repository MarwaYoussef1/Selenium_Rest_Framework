package stc.esport.pages.members;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pages.HomePage;

public class DiscoverMembersPage extends PageBase {


	public WebDriver driver;
	public Properties properties;
	HomePage homePageObj;

	public DiscoverMembersPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}

	By discoverMemberTab= By.xpath("//a[contains(@href,'/members/discover')]");
	
	public boolean isDiscoverMemberTabExist() {
		waitForPageLoadWithoutSpinner(driver);
		if(isElementExist(driver, discoverMemberTab, "Discover"))
			return true;
		return false;
	}
}
