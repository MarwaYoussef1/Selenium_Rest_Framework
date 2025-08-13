package stc.esport.pages.publicprofiles;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import com.shaft.tools.io.ReportManager;

import stc.esport.base.PageBase;

public class ClubPublicProfilePage extends PageBase{
	public WebDriver driver;
	public Properties properties;
	

	public ClubPublicProfilePage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}

	
	By exitViewAsPublicProfile= By.xpath("//a[contains(@class,'view-as')]");
	By contentCreatorsTab=By.xpath("//a[contains(@href,'content-creators')]");
	   
	private void navigateToContentCreatorTab() {
		waitForPageLoadWithoutSpinner(driver);
		driver.element().waitToBeReady(contentCreatorsTab);
		driver.element().click(contentCreatorsTab);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on ContentCreators tab from the menu");
	   }
	
	public void exitPublicProfile()
	{
		driver.element().click(exitViewAsPublicProfile);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Exit from public profile");
	}
	
	public boolean searchOnContentCreator(String name)
	{
		navigateToContentCreatorTab();
		By cardName = By.xpath("//div[contains(@class,'card')]//following::div[@class='name' and contains(text(),'"+name+"')]");
		if(isElementExist(driver, cardName, name))
			return true;
		return false;
	}

}
