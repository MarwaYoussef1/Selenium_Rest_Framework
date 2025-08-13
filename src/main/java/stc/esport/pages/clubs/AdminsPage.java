package stc.esport.pages.clubs;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pages.HomePage;

public class AdminsPage extends PageBase{
	public WebDriver driver;
	public Properties properties;
	HomePage homePageObj;

	public AdminsPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}

	
	   By addAdminCard= By.xpath("//button[contains(@class,'add-btn-card')]");
	   
	  
	   
	   public boolean isAddAdminCardExist() {
		   waitForPageLoadWithoutSpinner(driver);
			if(isElementExist(driver, addAdminCard, "Add Admin"))
				return true;
			return false;
	   }

}
