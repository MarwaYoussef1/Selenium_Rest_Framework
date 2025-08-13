package stc.esport.pages.teamsclans;


import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;

public class TeamProfilePage extends PageBase {

	public WebDriver driver;
	public Properties properties;
	
	By profileTitle= By.xpath("//*[@class='profile-title']//*[@class='name']");

	// Constructor
	public TeamProfilePage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}
	public String getProfileTitle()
	{
		sleep(2000);
		waitForPageLoadWithoutSpinner(driver);
		return driver.element().getText(profileTitle);
	}

	
	public boolean checkIfMemberExist(String role,String accountName) {
		
		List<WebElement> rolesList = driver.getDriver().findElements(By.xpath("//app-all-members//div[contains(@class,'role')]"));
		List<WebElement> nameList = driver.getDriver().findElements(By.xpath("//app-all-members//div[contains(@class,'name')]"));
		for (int i = 0; i < rolesList.size(); i++) {
			for (int j = 0; j < nameList.size(); j++) {
				if ((rolesList.get(i).getText().equals(role)) && ( nameList.get(i).getText().equals(accountName)) )
				{
					return true;
				} 
			}
		}
			
	
     return false;
	
}
}
