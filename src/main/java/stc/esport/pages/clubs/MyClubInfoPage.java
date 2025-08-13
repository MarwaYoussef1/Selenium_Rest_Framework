package stc.esport.pages.clubs;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pages.HomePage;

public class MyClubInfoPage extends PageBase{
	public WebDriver driver;
	public Properties properties;
	HomePage homePageObj;

	public MyClubInfoPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}
	
	   By myClubInfoTab= By.xpath("//a[contains(@href,'club-management/member/view-my-club')]");
       By editClubBtn= By.xpath("//button[contains(@class,'btn-primary')]");
       
       

       public boolean isEditBtnExist() {
    	   waitForPageLoadWithoutSpinner(driver);
			if(isElementExist(driver, editClubBtn, "Edit"))
				return true;
			return false;
       }
}
