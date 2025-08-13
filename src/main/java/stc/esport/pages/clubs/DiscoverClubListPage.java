package stc.esport.pages.clubs;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pages.HomePage;

public class DiscoverClubListPage extends PageBase {


		public WebDriver driver;
		public Properties properties;
		HomePage homePageObj;

		public DiscoverClubListPage(WebDriver driver, Properties properties) {
			this.driver = driver;
			this.properties = properties;
		}
	
	   By searchBar= By.xpath("//input[@placeholder='Search By Club Name']");
	   By clubNameOnCard= By.xpath("//div[@class='card']//div[@class='name']");
	   
	  
	   
	   public boolean searchWithClubName(String clubName) {
		   
		   driver.element().type(searchBar, clubName);
		   waitForPageLoadWithoutSpinner(driver);

		   if (driver.element().getText(clubNameOnCard).equals(clubName))
			   return true;
		   return false;
	   }
	
	
}
