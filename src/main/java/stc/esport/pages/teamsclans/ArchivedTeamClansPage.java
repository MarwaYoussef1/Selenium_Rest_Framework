package stc.esport.pages.teamsclans;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.utils.Constants;

public class ArchivedTeamClansPage extends PageBase {


		public WebDriver driver;
		public Properties properties;
        public TeamsDashboardPage teamsDashboardPageObj;
        public ClansDashboardPage clansDashboardPageObj;
		
		   By successToast= By.xpath("//div[contains(@id,'html-container')]");
		   By confrimAction= By.xpath("//button[contains(@class,'confirm')]");
		   By unarchiveBtn= By.xpath("//span[contains(text(),'Unarchive')]//parent::button");
		   By dotsIcon= By.xpath("//button[@aria-haspopup='menu']");//cdk-overlay-container
		   By errorPopupMsg= By.xpath("//div[contains(@id,'container')]//span");

		   
		public ArchivedTeamClansPage(WebDriver driver, Properties properties) {
			this.driver = driver;
			this.properties = properties;
			teamsDashboardPageObj= new TeamsDashboardPage(driver, properties);
			clansDashboardPageObj= new ClansDashboardPage(driver, properties);
		}
		
		public boolean selectUnarchiveAction(String name) {
			 searchWithName(driver,name);
				
	            waitForPageLoadWithoutSpinner(driver);
	            driver.element().click( dotsIcon);
	            
	            if(isElementVisible(driver, unarchiveBtn)){
	                 driver.element().click(unarchiveBtn);
	                 return true;
	            }
	        return false;
		}
		
		public boolean unarchiveTeam(String teamName, String teamType) {
			String unarchiveMsg;
			
			 if(teamType.equals(Constants.CLUBTEAM)) {
			      unarchiveMsg="Team "+properties.getProperty("successUnarchive");
				}else {
					unarchiveMsg= "Clan "+properties.getProperty("successUnarchive");
				}
			
			 if(teamType.equals(Constants.CLUBTEAM)) {
				 teamsDashboardPageObj.selectArchivedTeamTab();}
			 else {
				 clansDashboardPageObj.selectArchivedClanTab();
			 }
			 
			 selectUnarchiveAction(teamName);
			 driver.element().click(confrimAction);
             
			 return isMsgExistandDisplayed(driver, successToast, unarchiveMsg);
		}
		
		public boolean unachiveButReachedtheLimit(String teamName, String teamNumbers) {
			String unarchiveErrorMsg= properties.getProperty("unarchiveLimitError1")+" "+teamNumbers+" "+
					 properties.getProperty("unarchiveLimitError2");
			
			 clansDashboardPageObj.selectArchivedClanTab();
			 
			 selectUnarchiveAction(teamName);

			 driver.element().click(confrimAction);
			 
			 if(isMsgExistandDisplayed(driver, errorPopupMsg,unarchiveErrorMsg)) {
				 driver.element().click(confrimAction);
					return true;
			 }else {
				 return false;
			 }
		}
}
