package stc.esport.pages.Teammanagement;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pages.HomePage;
import stc.esport.utils.Constants;



public class TeamsManagementListPage extends PageBase {

	public WebDriver driver;
	public Properties properties;
	HomePage homePageObj;
	TeamDetailsPage teamDetailsPageObj;
	

	public TeamsManagementListPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
		teamDetailsPageObj=new TeamDetailsPage(driver, properties);
	}
	By searchInput = By.xpath("//input[@placeholder='Search']");
	By exportButton = By.id("export-dropdown");
	By allTeamsTab = By.xpath("//a[contains(@href,'/admin-teams/all-teams')]");
	By archivedTeamsTab = By.xpath("//a[contains(@href,'/admin-teams/archived-teams')]");
	By teamTitle = By.xpath("(//div[contains(@class,'card')]//descendant::h4)[1]");
	By teamRow = By.xpath("//div[contains(@class,'card')]");
	
	

	public void waitForTeamManagementPageLoad()
	{
		driver.element().waitToBeReady( allTeamsTab);
	}

	public By searchOnTeam(String tabName, String teamName,String expected) {
		waitForTeamManagementPageLoad();
		
		if (tabName.equals(Constants.MANAGEMENTALLTEAMS))
			executeJavaScript(driver, allTeamsTab, "arguments[0].click();");
		else if (tabName.equals(Constants.MANAGEMENTARCHIVEDTEAMS))
		executeJavaScript(driver, archivedTeamsTab, "arguments[0].click();");
		return searchOnItemInList(driver, searchInput, teamRow, teamName, teamTitle, expected);
	
	}
	
	public void openTeam(String tabName, String teamName,String expected) {
		waitForPageLoadWithoutSpinner(driver);
		By team = searchOnTeam(tabName, teamName,expected);
		if (team != null) {
			driver.element().click(teamTitle);
			waitForPageLoadWithoutSpinner(driver);
		}
	}
	
	public boolean openFirstTeam(String tabName)
	{
		waitForPageLoadWithoutSpinner(driver);
		if (tabName.equals(Constants.MANAGEMENTALLTEAMS))
			executeJavaScript(driver, allTeamsTab, "arguments[0].click();");
		else if (tabName.equals(Constants.MANAGEMENTARCHIVEDTEAMS))
		executeJavaScript(driver, archivedTeamsTab, "arguments[0].click();");
		return openFirstItemInList(driver);
	}
	
	public boolean isExportBtnExist()
	{
		waitForPageLoadWithoutSpinner(driver);
		if(isElementExist(driver, exportButton, "Export Button"))
			return true;
		return false;
	}
	
	public boolean checkTeamMemberContractStatus(String teamName,String memberName,String expectedStatus)
	{
		waitForPageLoadWithoutSpinner(driver);
		openTeam(Constants.MANAGEMENTALLTEAMS, teamName,teamName);
		return teamDetailsPageObj.checkContractStatus(memberName, expectedStatus);
		
	}
	
	public boolean requestContract(String teamName) {
		openTeam(Constants.MANAGEMENTALLTEAMS, teamName,teamName);
		return teamDetailsPageObj.requestContractForTeam(teamName);
	}
	
	public boolean removeTeamMember(String teamName,String memberName) {
		waitForPageLoadWithoutSpinner(driver);
		openTeam(Constants.MANAGEMENTALLTEAMS, teamName,teamName);
		return teamDetailsPageObj.removeTeamMember(memberName);
	}
	
	public boolean archiveTeam(String teamName,boolean withActiveMember) {
		waitForPageLoadWithoutSpinner(driver);
		openTeam(Constants.MANAGEMENTALLTEAMS, teamName,teamName);
		return teamDetailsPageObj.ArchiveTeam(withActiveMember);
	}



}
