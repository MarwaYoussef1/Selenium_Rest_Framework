package stc.esport.pages.membersmanagement;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pages.HomePage;
import stc.esport.utils.Constants;



public class MembersManagementListPage extends PageBase {

	public WebDriver driver;
	public Properties properties;
	HomePage homePageObj;
	

	public MembersManagementListPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}
	By searchInput = By.xpath("//input[@placeholder='Search']");
	By exportButton = By.id("export-dropdown");
	By allMembersTab = By.xpath("//a[contains(@href,'/admin-members/all-members')]");
	By blockedMembersTab = By.xpath("//a[contains(@href,'/admin-members/blocked-members')]");
	By memberTitle = By.xpath("(//div[contains(@class,'card')]//descendant::h4)[1]");
	By memberRow = By.xpath("//div[contains(@class,'card')]");
	By noRecordFound=By.xpath("//div[@class='notfound-content']//h5");
	
	

	public void waitForMembersManagementPageLoad()
	{
		driver.element().waitToBeReady( allMembersTab);
	}

	public By searchOnMember(String tabName, String memberName,String expected) {
		waitForMembersManagementPageLoad();
		if (tabName.equals(Constants.MANAGEMENTALLMEMBERS))
			executeJavaScript(driver, allMembersTab, "arguments[0].click();");
		else if (tabName.equals(Constants.MANAGEMENTBLOCKEDMEMBERS))
		executeJavaScript(driver, blockedMembersTab, "arguments[0].click();");
		return searchOnItemInList(driver, searchInput, memberRow, memberName, memberTitle, expected);
	
	}
	
	public void openMember(String tabName, String memberName,String expected) {
		waitForPageLoadWithoutSpinner(driver);
		By member = searchOnMember(tabName, memberName,expected);
		if (member != null) {
			driver.element().click(memberTitle);
			waitForPageLoadWithoutSpinner(driver);
		}
	}
	
	public boolean openFirstMember(String tabName)
	{
		waitForPageLoadWithoutSpinner(driver);
		if (tabName.equals(Constants.MANAGEMENTALLMEMBERS))
			executeJavaScript(driver, allMembersTab, "arguments[0].click();");
		else if (tabName.equals(Constants.MANAGEMENTBLOCKEDMEMBERS))
		executeJavaScript(driver, blockedMembersTab, "arguments[0].click();");
		return openFirstItemInList(driver);
	}
	
	public boolean isExportBtnExist()
	{
		waitForPageLoadWithoutSpinner(driver);
		if(isElementExist(driver, exportButton, "Export Button"))
			return true;
		return false;
	}
		public boolean searchWithInvalidValue(String name) {
		String	invalidSearchText= properties.getProperty("invalidSearch");
		driver.element().type(searchInput, name);
		if(isElementExist(driver, noRecordFound, invalidSearchText)) {
			return true;
		}else {
			return false;
		}
	}
											 


}
