package stc.esport.pages.teamsclans;


import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;

public class ClansDashboardPage extends PageBase{

	public WebDriver driver;
	public Properties properties;
	
	   By membershipTab= By.xpath("//a[contains(@href,'subscribed-')]");
	   By archivedTab= By.xpath("//a[contains(@href,'archived-')]");
	   By discoverClan= By.xpath("//a[contains(@href,'clans/discover-clans')]");
	   By myClanTab= By.xpath("//a[contains(@href,'clans/my-clans')]");

	// Constractor
			public ClansDashboardPage(WebDriver driver, Properties properties) {
				this.driver = driver;
				this.properties = properties;
			}
			
			 public boolean isDiscoverClanTabExist() {
		    	 waitForPageLoadWithoutSpinner(driver);
		    	 if(isElementExist(driver, discoverClan, "Discover Clans"))
		 			return true;
		 		return false;
		     }
			 
			 public void selectMyClanTab() {
					driver.element().click(myClanTab);
					waitForPageLoadWithoutSpinner(driver);
				}
			 
			 public void selectMembershipClanTab() {
					driver.element().click(membershipTab);
					waitForPageLoadWithoutSpinner(driver);
				}
				
				public void selectArchivedClanTab() {
					driver.element().click( archivedTab);
					waitForPageLoadWithoutSpinner(driver);
				}
			 
			 public boolean isMyClansTabsExist(){	             
				if(isElementExist(driver, myClanTab, "My Clans"))
				    return true;
				return false;
			 }
			 
			 public boolean isMembershipClanTanExist() {
				if(isElementExist(driver, membershipTab, "Membership"))
                  return true;
			  return false;
			 }
			 
			 public boolean isArchivedClanTabExist() {
				if(isElementExist(driver, archivedTab, "Archived Clans"))
                   return true;				
			   return false;	
			}
			 

}
