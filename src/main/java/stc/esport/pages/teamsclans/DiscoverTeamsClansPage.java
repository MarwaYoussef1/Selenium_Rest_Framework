package stc.esport.pages.teamsclans;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pages.HomePage;
import stc.esport.utils.Constants;

public class DiscoverTeamsClansPage extends PageBase {


	public WebDriver driver;
	public Properties properties;
	HomePage homePageObj;

	public DiscoverTeamsClansPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}
	
	     By discoverTeam= By.xpath("//a[contains(@href,'teams/discover-teams')]");
	     By discoverClan= By.xpath("//a[contains(@href,'clans/discover-clans')]");
	     
	     public boolean isDiscoverTabExist(String type) {
	    	 waitForPageLoadWithoutSpinner(driver);
	    	 if(type.equals(Constants.CLANTEAM)) {
				return isElementExist(driver, discoverClan, "Discover Clans");
	    	 }else {
	    		return isElementExist(driver,discoverTeam , "Discover Clubs");
	    	 }
	     }

}
