package stc.esport.pages.teamsclans;


import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;

public class TeamsDashboardPage extends PageBase{

		public WebDriver driver;
		public Properties properties;
		
		   By myTeamsTab= By.xpath("//a[contains(@href,'my-teams')]");
		   By membershipTab= By.xpath("//a[contains(@href,'subscribed-')]");
		   By archivedTab= By.xpath("//a[contains(@href,'archived-')]");
		   By discoverTeam= By.xpath("//a[contains(@href,'teams/discover-teams')]");

		// Constractor
				public TeamsDashboardPage(WebDriver driver, Properties properties) {
					this.driver = driver;
					this.properties = properties;
				}
				
				 public boolean isDiscoverTeamTabExist() {
			    	 waitForPageLoadWithoutSpinner(driver);
			    	 if(isElementExist(driver, discoverTeam, "Discover Teams"))
				 			return true;
				 		return false;
				 }
				 
				   public void selectMyTeamTab() {
						driver.element().click(myTeamsTab);
						waitForPageLoadWithoutSpinner(driver);
					}
				   
					public void selectMembershipTeamTab() {
						driver.element().click(membershipTab);
						waitForPageLoadWithoutSpinner(driver);
					}
					
					public void selectArchivedTeamTab() {
						driver.element().click( archivedTab);
						waitForPageLoadWithoutSpinner(driver);
					}
					
					public boolean isMyTeamsTabsExist(){
						 if(isElementExist(driver, myTeamsTab, "My Teams"))
							 return true;
						 return false;
					}
					
					public boolean isArchivedTeamTabExist() {
						 if(isElementExist(driver, archivedTab, "Archived Teams"))
							 return true;
						 return false;
					}
					
					public boolean isMembershipTeamTabExist() {
						 if(isElementExist(driver, membershipTab, "Membership Teams"))
							 return true;
						 return false;
					}
}
