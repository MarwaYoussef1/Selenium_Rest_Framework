// DashBoard Page 
// Version: 1.0 : initial creation
// Date: 30-03-2023
//-----------------------------------

package stc.esport.pages;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import com.shaft.tools.io.ReportManager;

import stc.esport.base.PageBase;
import stc.esport.pojo.Account;
import stc.esport.utils.Utils;

public class DashBoardPage extends PageBase {

	// Variables
	public WebDriver driver;
	public Properties properties;
	

	// Functions
	public DashBoardPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;

	} 

	// Elements Locators
	By accountDropDown = By.xpath("//div[@class='name-wrapper']//*[@class='name']");
	By logoutBtn = By.xpath("//*[contains(@class,'sign-out')]");
	By usersNavSide = By.xpath("//span[contains(@class,'menu-item-text') and contains(text(),'Users')]");
	By crmNavSide=By.xpath("//span[contains(@class,'menu-item-text') and contains(text(),'CRM')]");
	By clubsManagementNavSide= By.xpath("//span[contains(@class,'menu-item-text') and text()=' Clubs Management ']");
	By clubsNavSide= By.xpath("//span[contains(@class,'menu-item-text') and text()=' Clubs ']");
	By teamsManagementNavSide= By.xpath("//span[contains(@class,'menu-item-text') and text()=' Teams Management ']");
	By teamsNavSide= By.xpath("//span[contains(@class,'menu-item-text') and text()=' Teams ']");
	By clansManagementNavSide= By.xpath("//span[contains(@class,'menu-item-text') and text()=' Clans Management ']");
	By clansNavSide= By.xpath("//span[contains(@class,'menu-item-text') and text()=' Clans ']");
	By membersManagementNavSide= By.xpath("//span[contains(@class,'menu-item-text') and text()=' Members Management ']");
	By membersNavSide= By.xpath("//span[contains(@class,'menu-item-text') and text()=' Members ']");
	By invitationNavSide= By.xpath("//span[contains(@class,'menu-item-text') and contains(text(),'Invitations')]");
	By profileName = By.xpath("//div[@class='profile']");
	By clubOwnerStatus = By.xpath("//app-club-owner-status//*[@class='ng-star-inserted']");
	By reSubmitApp=By.xpath("//button[contains(@class,'require-action')]");
	By settingNavside= By.xpath("//span[contains(@class,'menu-item-text') and text()=' Settings ']");
	By myClubNavSide= By.xpath("//span[contains(@class,'menu-item-text') and text()=' My Club ']");
	By incidnetNavSide= By.xpath("//span[contains(@class,'menu-item-text') and text()=' Incidents ']");
	
	// Resubmit Application Elements
	By status=By.xpath("//*[@class='req-status']");
	By nationalityList = By.xpath("//ng-select[@id='countryId']//input");
	By clubOwnerNationalID = By.id("nationalId");
	By clubOwnerNationalIDFile = By.id("nationalIdFile");
	By clubOwnerEN = By.id("clubName");
	By clubOwnerAR = By.id("clubNameAr");
	By clubLogo = By.id("clubLogo");
	By crNumber = By.id("crNumber");
	By crEndDate = By.xpath("//app-hijri-datepicker//input");
	By cRFile = By.id("crFile");
	By clubIban = By.id("clubIban");
	By clubIbanFile = By.id("clubIBANFile");
	By reSubmitAppPopUp=By.xpath("//button[contains(@class,'btn-primary')]");
	
	//teams counter 
	By teamCounter= By.xpath("//span[@class='counter']");

	//////////////////////////////////////////////////////////
	////////////////////// Actions //////////////////////////
	public void waitForDashBoardPageLoaded() {
		driver.element().waitToBeReady(accountDropDown);
		waitForPageLoadWithoutSpinner(driver);
		
	}

	public void clickOnAccountIcon() {
		
		driver.element().waitToBeReady(accountDropDown);
		driver.element().clickAndHold(accountDropDown);
		  
	}

	public void logout() {

		waitForPageLoadWithoutSpinner(driver);
		clickOnAccountIcon();
		waitUntilElementPresent(driver, logoutBtn);
	    driver.element().click(logoutBtn);
		ReportManager.log("logout from website");

	}

	public void navigateToUsers() {
		driver.element().waitToBeReady(usersNavSide);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(usersNavSide);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on Users from the side Menu");
	}
	
	public void navigateToCRM() {
		driver.element().waitToBeReady(crmNavSide);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(crmNavSide);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on CRM from the side Menu");
	}
	
	public void navigateToMembersManagement() {
		driver.element().waitToBeReady(membersManagementNavSide);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(membersManagementNavSide);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on Members Management from the side Menu");
	}
	
	public void navigateToMembers() {
		driver.element().waitToBeReady(membersNavSide);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(membersNavSide);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on Members from the side Menu");
	}	
	
	public void navigateToClubsManagement() {
		driver.element().waitToBeReady(clubsManagementNavSide);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(clubsManagementNavSide);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on Clubs Management from the side Menu");
	}
	
	public void navigateToClubs() {
		driver.element().waitToBeReady(clubsNavSide);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(clubsNavSide);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on Clubs from the side Menu");
	}	
	
	public void navigateToTeamsManagement() {
		driver.element().waitToBeReady(teamsManagementNavSide);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(teamsManagementNavSide);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on Teams Management from the side Menu");
	}
	
	public void navigateToTeams() {
		driver.element().waitToBeReady(teamsNavSide);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(teamsNavSide);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on Teams from the side Menu");
	}
	
     public void navigateToClansManagement() {
		
		driver.element().waitToBeReady(clansManagementNavSide);
		driver.element().click(clansManagementNavSide);
		waitForPageLoadWithoutSpinner(driver);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on clans management from the side Menu");
	}
	
	public void navigateToClans() {
		
		driver.element().waitToBeReady(clansNavSide);
		driver.element().click(clansNavSide);
		waitForPageLoadWithoutSpinner(driver);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on clans from the side Menu");
	}
	
	public void navigateToInvitations() {
		driver.element().waitToBeReady(invitationNavSide);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(invitationNavSide);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on invitations from the side Menu");
	}
	
	public void naviagteToSettings() {
		driver.element().waitToBeReady(settingNavside);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(settingNavside);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on settings from the side Menu");
	}

	
	public void navigateToMyClub() {
		driver.element().waitToBeReady(myClubNavSide);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(myClubNavSide);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on my club from the side Menu");
	}
	
	public void navigateToIncidents() {
		driver.element().waitToBeReady(incidnetNavSide);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(incidnetNavSide);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on Incidents from the side Menu");
	}
	
	public boolean checkProfileName(String name) {
		waitUntilElementPresent( driver, profileName);
		return isMsgExistandDisplayed(driver, profileName, name);
	}

	public boolean checkStatusMsg(String msg) {
		return isMsgExistandDisplayed(driver, clubOwnerStatus, msg);
	}

	public boolean resubmitApp(Account clubOwnerAccount) {
	
		By dayLocator = By.xpath("(//div[@class='ngb-dp-day ng-star-inserted']//div[text()=" + "'" + Utils.getTodayHijriDay() + "'" + "])[1]");
		By userUpdatedMsg=By.xpath("//*[text()='"+properties.getProperty("RequireActionSuccessMsg")+ "']");
		driver.element().click(reSubmitApp);
		waitForPageLoadWithoutSpinner(driver);
		boolean statusFound= isMsgExistandDisplayed(driver, status, properties.getProperty("RequireActionStatus"));
		if(statusFound)
		{
			
		driver.element().type(nationalityList, clubOwnerAccount.getNationality());
		driver.element().keyPress(nationalityList, Keys.ENTER);
		driver.element().type(clubOwnerNationalID, clubOwnerAccount.getClubOwnerNationalID());
		uploadFile(driver,clubOwnerAccount.getClubOwnerNationalIDFile(), clubOwnerNationalIDFile);
		driver.element().type(clubOwnerEN, clubOwnerAccount.getClubOwnerEn());
		driver.element().type(clubOwnerAR, clubOwnerAccount.getClubOwnerAr());
		uploadFile(driver,clubOwnerAccount.getClubLogo(), clubLogo);
		driver.element().type(crNumber, clubOwnerAccount.getCrNumber());
		driver.element().click(crEndDate);
		//sleep(1000);
		//waitUntilElementPresent(driver, dayLocator);
		driver.element().click(dayLocator);
		//executeJavaScript(driver, dayLocator, "arguments[0].click();");
		uploadFile(driver,clubOwnerAccount.getCrFile(), cRFile);
		driver.element().type(clubIban, clubOwnerAccount.getClubIBAN());
		uploadFile(driver,clubOwnerAccount.getClubIBANFile(), clubIbanFile);
		sleep(2000);
		driver.element().click(reSubmitAppPopUp);
		waitUntilElementPresent(driver, userUpdatedMsg);
		return true;
		}
		return false;
	}
	
	public boolean isSideMenuItemExist(String tabName) {
		By sideMenuName= By.xpath("//span[@class='menu-item-text' and text()="+"' "+tabName+" '"+"]");

		waitForPageLoadWithoutSpinner(driver);
		if(isElementExist(driver, sideMenuName, ""))
			return true;
		return false;
	}
	
	public boolean isPageNotAuthorized(String expectedTitle) {
		String currenTitle= driver.browser().getCurrentWindowTitle();
		driver.browser().navigateBack();

		if(expectedTitle.equals(currenTitle))
			return true;
		return false;
	}
	
	public int getCurrentTeamCounter() {
		int currentCounter;
		if( !isElementExist(driver, teamCounter, "")) {
			currentCounter=0;
		}else {
			currentCounter= Integer.parseInt(driver.element().getText(teamCounter));
		}
		return currentCounter;
	}
	
	public boolean checkTeamCounterAfterContractRequest(int counterBeforeRequest) {
		int currentCounter= Integer.parseInt(driver.element().getText(teamCounter));
		if (currentCounter==counterBeforeRequest+1)
			return true;
		return false;
	}

	
	public boolean checkTeamCounterAfterSubmitAllReq(int counterAfterReq) {
		int counterAfterSubmit= Integer.parseInt(driver.element().getText(teamCounter));
		if(counterAfterSubmit==counterAfterReq)
			return true;
		return false;

	}

}
