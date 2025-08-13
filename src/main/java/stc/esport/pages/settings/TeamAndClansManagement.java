package stc.esport.pages.settings;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;

public class TeamAndClansManagement extends PageBase{
	
	public WebDriver driver;
	public Properties properties;
	
	// identifier
	 By teamClanSettings= By.xpath("//div[text()='Teams, Clan and Clubs Management']");
     By clubNum= By.id("concurrentClubs");
	 By successToast= By.xpath("//div[contains(@id,'html-container')]");

	
	// Constractor
			public TeamAndClansManagement(WebDriver driver, Properties properties) {
				this.driver = driver;
				this.properties = properties;
			}

	       public void navigateToTeamsClansSetting() {
	    	   driver.element().click(teamClanSettings);
	       }
	       
	       private void selectRole(String roleName) {
	    	   By rolelocator= By.xpath("//span[text()="+"' "+roleName+" '"+"]");
	    	   driver.element().click(rolelocator);
	       }
		 
	       
	       private By saveButton(String roleName) {
	    	   By saveLocator= By.xpath("(//span[text()="+"' "+roleName+" '"+"]//following::button[contains(@class,'save-btn')])[1]");
	    	   return saveLocator;
	       }
	       
	       
	       private By clanNumLocator(String roleName) {
	    	   By locator=  By.xpath("(//span[text()="+"' "+roleName+" '"+"]//following::input[@id='concurrentTeams'])[1]");
	    	   return locator;
	       }
	       
	       
			public boolean changeActiveClanNum(String roleName, String number) {
				String successUpdateMsg= properties.getProperty("updateclanActiveNum");
				
				navigateToTeamsClansSetting();
				waitForPageLoadWithoutSpinner(driver);
				
				selectRole(roleName);
				sleep(1000);
				driver.element().clear(clanNumLocator(roleName));
				sleep(1000);
				driver.element().type(clanNumLocator(roleName), number);
				driver.element().click(saveButton(roleName));
				waitForPageLoadWithoutSpinner(driver);
				
				return isElementExist(driver, successToast, successUpdateMsg);
			}
}
