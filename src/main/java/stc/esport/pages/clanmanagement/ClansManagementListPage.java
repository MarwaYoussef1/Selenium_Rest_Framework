package stc.esport.pages.clanmanagement;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pages.HomePage;
import stc.esport.utils.Constants;



public class ClansManagementListPage extends PageBase {

	public WebDriver driver;
	public Properties properties;
	HomePage homePageObj;
	

	public ClansManagementListPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}
	By searchInput = By.xpath("//input[@placeholder='Search']");
	By exportButton = By.id("export-dropdown");
	By allClansTab = By.xpath("//a[contains(@href,'/admin-clans/all-clans')]");
	By archivedClansTab = By.xpath("//a[contains(@href,'/admin-clans/archived-clans')]");
	By clanTitle = By.xpath("(//div[contains(@class,'card')]//descendant::h4)[1]");
	By clanRow = By.xpath("//div[contains(@class,'card')]");
	
	

	public void waitForClanManagementPageLoad()
	{
		driver.element().waitToBeReady( allClansTab);
	}

	public By searchOnClan(String tabName, String clanName,String expected) {
		waitForClanManagementPageLoad();
		if (tabName.equals(Constants.MANAGEMENTALLCLANS))
			executeJavaScript(driver, allClansTab, "arguments[0].click();");
		else if (tabName.equals(Constants.MANAGEMENTARCHIVECLAN))
		executeJavaScript(driver, archivedClansTab, "arguments[0].click();");
		return searchOnItemInList(driver, searchInput, clanRow, clanName, clanTitle, expected);
	
	}
	
	public void openClan(String tabName, String clanName,String expected) {
		waitForPageLoadWithoutSpinner(driver);
		By club = searchOnClan(tabName, clanName,expected);
		if (club != null) {
			driver.element().click(clanTitle);
			waitForPageLoadWithoutSpinner(driver);
		}
	}
	
	public boolean openFirstClan(String tabName)
	{
		waitForPageLoadWithoutSpinner(driver);
		if (tabName.equals(Constants.MANAGEMENTALLCLANS))
			executeJavaScript(driver, allClansTab, "arguments[0].click();");
		else if (tabName.equals(Constants.MANAGEMENTARCHIVEDCLANS))
		executeJavaScript(driver, archivedClansTab, "arguments[0].click();");
		return openFirstItemInList(driver);
	}
	
	public boolean isExportBtnExist()
	{
		waitForPageLoadWithoutSpinner(driver);
		if(isElementExist(driver, exportButton, "Export Button"))
			return true;
		return false;
	}


}
