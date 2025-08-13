package stc.esport.pages.invitations;

import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.utils.Constants;

public class InvitationsListPage extends PageBase {

	WebDriver driver;
	Properties properties;
	InvitationDetailsPage invitationDetailsObj;
	

	// identifier
	By searchInput = By.xpath("//input[@placeholder='Search']");
	By receivedInvitationsTab = By.xpath("//a[contains(@href,'invitations/received')]");
	By sentInvitationsTab = By.xpath("//a[contains(@href,'invitations/sent')]");
	By invitationTitle = By.xpath("(//div[contains(@class,'invitation-card')]//descendant::span)[1]");
	By invitationTitles = By.xpath("//img[@class='invitation-img']//following::span[contains(@class,'ng-star-inserted')][1]");
	By invitationStatus = By.xpath("(//span[contains(@class,'label-status')])[1]");
	
	By invitationRow = By.xpath("//div[contains(@class,'card invitation')]");
	
	By notFoundSearchMsg=By.xpath("//*[@class='notfound-content']");

	// Constractor
	public InvitationsListPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
		invitationDetailsObj = new InvitationDetailsPage(driver, properties);
	
	}
	public void waitForInvitationPageLoad()
	{
		driver.element().waitToBeReady( receivedInvitationsTab);
	}

	public By searchOnInvitation(String tabType, String teamName,String expected) {
		waitForInvitationPageLoad();
		
		if (tabType.equals(Constants.RECEIVEDINVITATION))
			executeJavaScript(driver, receivedInvitationsTab, "arguments[0].click();");
		else if (tabType.equals(Constants.SENTINVITATION))
		executeJavaScript(driver, sentInvitationsTab, "arguments[0].click();");
		return searchOnItemInList(driver, searchInput, invitationRow, teamName, invitationTitle, expected);
	
	}
	
	public WebElement searchOnInvitationByStatus(String tabType, String status,String expected) {
		waitForInvitationPageLoad();
		
		if (tabType.equals(Constants.RECEIVEDINVITATION))
			executeJavaScript(driver, receivedInvitationsTab, "arguments[0].click();");
		else if (tabType.equals(Constants.SENTINVITATION))
		executeJavaScript(driver, sentInvitationsTab, "arguments[0].click();");
		return searchOnItemInListByStatus(driver, searchInput, invitationRow, status, invitationTitles, expected);
	
	}

	public void openInvitationByName(String tabType,String teamName,String expected) {
		waitForPageLoadWithoutSpinner(driver);
		By invitation;
	     invitation = searchOnInvitation(tabType, teamName,expected);
		if (invitation != null) {
			driver.element().click(invitationTitle);
			waitForPageLoadWithoutSpinner(driver);
		}
	}
	
	public void openInvitationByStatus(String tabType,String status,String expected) {
		waitForPageLoadWithoutSpinner(driver);
		WebElement invitation;
	     invitation = searchOnInvitationByStatus(tabType, status,expected);
		if (invitation != null) {
			invitation.click();
			waitForPageLoadWithoutSpinner(driver);
		}
	}
	public  ArrayList<Boolean> acceptRejectInvitation(String action, String teamName)
     {
		ArrayList<Boolean> checks = new ArrayList<Boolean>();
		boolean acceptedRejectedListStatus = false;
		openInvitationByName(Constants.RECEIVEDINVITATION, teamName,teamName);
		checks.addAll(acceptRejectInvitationDetails( action)) ;
		if (action.equals(Constants.ACCEPTINVITATION))
			acceptedRejectedListStatus = checkInvitationListStatus(Constants.INVITATIONACCEPTED);
		else if (action.equals(Constants.REJECTINVITATION))
			acceptedRejectedListStatus = checkInvitationListStatus(Constants.INVITATIONREJECTED);
		checks.add(acceptedRejectedListStatus);
		return checks;
     }

     public  ArrayList<Boolean> acceptRejectInvitationByStatus(String action, String status,String invitationTitle)
     {
    	 ArrayList<Boolean> checks = new ArrayList<Boolean>();
 		 boolean acceptedRejectedListStatus = false;
    	 openInvitationByStatus(Constants.RECEIVEDINVITATION, status,invitationTitle);
    	 checks.addAll(acceptRejectInvitationDetails( action)) ;
    		if (action.equals(Constants.ACCEPTINVITATION) || action.equals(Constants.ACCEPT_REMOVE_INVITATION) )
    		{   
    			searchOnInvitationByStatus(action, Constants.INVITATIONACCEPTED, invitationTitle);
    			acceptedRejectedListStatus = checkInvitationListStatus(Constants.INVITATIONACCEPTED);
    		}
    		else if (action.equals(Constants.REJECTINVITATION)|| action.equals(Constants.REJECT_REMOVE_INVITATION))
    		{
    			 searchOnInvitationByStatus(action, Constants.INVITATIONREJECTED, invitationTitle);
    			acceptedRejectedListStatus = checkInvitationListStatus(Constants.INVITATIONREJECTED);   
    		}
    		checks.add(acceptedRejectedListStatus);	
    		return checks;
     }
	private ArrayList<Boolean> acceptRejectInvitationDetails(String action) {
		ArrayList<Boolean> checks = new ArrayList<Boolean>();
		boolean acceptedRejected;
		boolean buttonsHidden;
		acceptedRejected = invitationDetailsObj.acceptRejectInvitation(action);
		buttonsHidden = invitationDetailsObj.actionButtonsHidden();
		checks.add(acceptedRejected);
		checks.add(buttonsHidden);
		return checks;
	}
	
	public ArrayList<Boolean> viewProfiles(String teamName,String clubName)
	{
		openInvitationByName(Constants.RECEIVEDINVITATION, teamName,teamName);
	    return invitationDetailsObj.viewProfiles(teamName, clubName);
		
	}
	
	public boolean cancelInvitation(String teamName,String accountName) {
	    boolean invitationCancelled;
	    openInvitationByName(Constants.SENTINVITATION, teamName,accountName);
		invitationCancelled = invitationDetailsObj.cancelInvitation();
		return invitationCancelled;
	}

	private boolean checkInvitationListStatus(String expectedStatus) {
		String actualStatus;
		actualStatus = driver.getDriver().findElement(invitationStatus).getText();
		return actualStatus.trim().equals(expectedStatus);

	}
	
	public boolean openCheckInvitationStatus(String invitationType,String teamName,String expectedTitle,String expectedStatus) {
		String actualStatus;
		boolean invitationStatusList;
		boolean invitationStatusDetail;
		if(teamName==null)
		openInvitationByStatus(invitationType, expectedStatus, expectedTitle);	
		else
		openInvitationByName(invitationType, teamName,expectedTitle);
		actualStatus = driver.getDriver().findElement(invitationStatus).getText();
		invitationStatusList= actualStatus.trim().equals(expectedStatus);
		invitationStatusDetail=invitationDetailsObj.checkInvitationDetailStatus(expectedStatus);
		return invitationStatusList && invitationStatusDetail;

	}
	
	

}
