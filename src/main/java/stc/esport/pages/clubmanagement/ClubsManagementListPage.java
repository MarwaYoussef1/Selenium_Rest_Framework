package stc.esport.pages.clubmanagement;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pages.HomePage;
import stc.esport.utils.Constants;



public class ClubsManagementListPage extends PageBase {

	public WebDriver driver;
	public Properties properties;
	HomePage homePageObj;
	

	public ClubsManagementListPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}
	
	By searchInput = By.xpath("//input[@placeholder='Search']");
	By exportButton = By.id("export-dropdown");
	By allClubsTab = By.xpath("//a[contains(@href,'/club-management/admin/all-clubs')]");
	By blockedClubsTab = By.xpath("//a[contains(@href,'/club-management/admin/blocked-clubs')]");
	By clubTitle = By.xpath("(//div[contains(@class,'card')]//descendant::h4)[1]");
	By clubRow = By.xpath("//div[contains(@class,'card')]");
	
	

	public void waitForClubManagementPageLoad()
	{
		driver.element().waitToBeReady( allClubsTab);
	}

	public By searchOnClub(String tabName, String clubName,String expected) {
		waitForClubManagementPageLoad();
		if (tabName.equals(Constants.MANAGEMENTALLCLUBS))
			executeJavaScript(driver, allClubsTab, "arguments[0].click();");
		else if (tabName.equals(Constants.MANAGEMENTBLOCKEDCLUBS))
		executeJavaScript(driver, blockedClubsTab, "arguments[0].click();");
		return searchOnItemInList(driver, searchInput, clubRow, clubName, clubTitle, expected);
	
	}
	
	public void openClub(String tabName, String clubName,String expected) {
		waitForPageLoadWithoutSpinner(driver);
		By club = searchOnClub(tabName, clubName,expected);
		if (club != null) {
			driver.element().click(clubTitle);
			waitForPageLoadWithoutSpinner(driver);
		}
	}
	
	public boolean openFirstClub(String tabName)
	{
		waitForPageLoadWithoutSpinner(driver);
		if (tabName.equals(Constants.MANAGEMENTALLCLUBS))
			executeJavaScript(driver, allClubsTab, "arguments[0].click();");
		else if (tabName.equals(Constants.MANAGEMENTBLOCKEDCLUBS))
		executeJavaScript(driver, blockedClubsTab, "arguments[0].click();");
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
