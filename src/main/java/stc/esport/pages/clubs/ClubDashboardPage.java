package stc.esport.pages.clubs;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;

public class ClubDashboardPage extends PageBase{
	
	public WebDriver driver;
	public Properties properties;
	
	public ClubDashboardPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}
	
	   By adminTab= By.xpath("//a[contains(@href,'club-management/member/co-admin')]");
	   By discoverTab= By.xpath("//a[contains(@href,'/club-management/member/discover-clubs')]");
	   By myClubInfoTab= By.xpath("//a[contains(@href,'club-management/member/view-my-club')]");

	   public boolean isAdminTabExist() {
		   waitForPageLoadWithoutSpinner(driver);
			if(isElementExist(driver, adminTab, "admin"))
				return true;
			return false;
	   }
	   
	   public void selectAdminTab() {
		   driver.element().click(adminTab);
	   }
	   
	   public boolean isDisoverTabExist() {
		   waitForPageLoadWithoutSpinner(driver);
			if(isElementExist(driver, discoverTab, "Discover Clubs"))
				return true;
			return false;
	   }
	
	   public void selectMyClubInfo() {
    	   driver.element().click(myClubInfoTab);
       }
       
       public boolean isMyClubInfoTabExist() {
		   waitForPageLoadWithoutSpinner(driver);
			if(isElementExist(driver, myClubInfoTab, "My Club info"))
				return true;
			return false;
	   }
	
}
