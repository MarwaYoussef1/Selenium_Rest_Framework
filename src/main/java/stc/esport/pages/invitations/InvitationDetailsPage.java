package stc.esport.pages.invitations;

import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import com.shaft.tools.io.ReportManager;

import stc.esport.base.PageBase;
import stc.esport.pages.teamsclans.ClubProfilePage;
import stc.esport.pages.teamsclans.TeamProfilePage;
import stc.esport.utils.Constants;

public class InvitationDetailsPage extends PageBase {

	WebDriver driver;
	Properties properties;
	TeamProfilePage teamProfileObj;
	ClubProfilePage clubProfileObj;
	

	// identifier
	By acceptInvitation = By.xpath("//a[contains(@class,'accept-btn')]");
	By rejectInvitation = By.xpath("//a[contains(@class,'reject-btn')]");
	By confirmAcceptRejectInvitation = By.xpath("//button[@type='submit']");
	By acceptRejectReason=By.xpath("//*[@formcontrolname='reason']");
	By cancelInvitation = By.xpath("//a[contains(text(),'Cancel')]");
	By actionConfirmMsg = By.id("swal2-html-container");
	By actionViewTeamProfile= By.xpath("//a[contains(@href,'profile/team')]");
	By actionViewClubProfile= By.xpath("//a[contains(@href,'profile/club')]");
	By cancelInvitationPopUpBtn= By.xpath("//button[contains(@class,'swal2-confirm')]");
	By invitationDetailStatus = By.xpath("//div[contains(@class,'actions')]//span[contains(@class,'status')]");

	// Constractor
	public InvitationDetailsPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
		teamProfileObj = new TeamProfilePage(driver, properties);
		clubProfileObj=new ClubProfilePage(driver, properties);

	}

	public boolean acceptRejectInvitation(String action) {
		String invitationMsg = null;
		String invitationStatusMsg;
		if (action.equals(Constants.ACCEPTINVITATION)) {
			invitationMsg = properties.getProperty("AcceptInvitationMsg");
			invitationStatusMsg = Constants.INVITATIONACCEPTED;
			driver.element().click(acceptInvitation);
		}
		else if (action.equals(Constants.ACCEPT_REMOVE_INVITATION)) {
			invitationMsg = properties.getProperty("AcceptInvitationMsg");
			invitationStatusMsg = Constants.INVITATIONACCEPTED;
			driver.element().click(acceptInvitation);
			waitUntilElementPresent(driver, confirmAcceptRejectInvitation);
			if(isElementExist(driver, acceptRejectReason, "rejection reason"))
			driver.element().type(acceptRejectReason, "reason");
			driver.element().click(confirmAcceptRejectInvitation);
			
		}

		else {
			invitationMsg = properties.getProperty("RejectInvitationMsg");
			invitationStatusMsg = Constants.INVITATIONREJECTED;
			driver.element().click(rejectInvitation);
			waitUntilElementPresent(driver, confirmAcceptRejectInvitation);
			if(isElementExist(driver, acceptRejectReason, "rejection reason"))
			driver.element().type(acceptRejectReason, "reason");
			driver.element().click(confirmAcceptRejectInvitation);
		}
		if (isMsgExistandDisplayed(driver, actionConfirmMsg, invitationMsg) && checkInvitationDetailStatus(invitationStatusMsg)) {
			
				return true;
			
		}
		return false;
	}
	
	public boolean cancelInvitation() {
		String cancelInvitationMsg = properties.getProperty("CancelInvitationMsg");;
		String canclePopUpMsg = properties.getProperty("CancelMsgPopup");
		driver.element().click(cancelInvitation);
		if (isMsgExistandDisplayed(driver, actionConfirmMsg, canclePopUpMsg)) {
			driver.element().waitToBeReady(cancelInvitationPopUpBtn);
			driver.element().click(cancelInvitationPopUpBtn);
			if (isMsgExistandDisplayed(driver, actionConfirmMsg, cancelInvitationMsg) 
					&& checkInvitationDetailStatus(Constants.INVITATIONCANCELLED)) {
				return true;
			}
		}
		return false;
	}

	public boolean actionButtonsHidden() {
		By actionBtnsContainer = By.xpath("//div[contains(@class,'action')]//child::a");
		int actionBtnsSize = driver.getDriver().findElements(actionBtnsContainer).size();

		if (actionBtnsSize == 0) {
			return true;
		}

		return false;
	}
	
	public boolean checkInvitationDetailStatus(String expectedStatus)
	{
		if (isMsgExistandDisplayed(driver, invitationDetailStatus, expectedStatus)) {
			return true;
		}
		
		return false;
	}
	
	public ArrayList<Boolean> viewProfiles(String teamName,String clubName)
	{
		ArrayList<Boolean> checks = new ArrayList<Boolean>();
		driver.element().click(actionViewTeamProfile);
		ReportManager.log("view team profile");
		switchingBetweenTabs(driver, 1);
		sleep(3000);
	    checks.add(teamProfileObj.getProfileTitle().equals(teamName.toUpperCase()));
	    ReportManager.log("view Club profile");
	    driver.element().click(actionViewClubProfile);
	    switchingBetweenTabs(driver, 1);
	    sleep(3000);
	    checks.add(clubProfileObj.getProfileTitle().equals(clubName.toUpperCase()));
	    switchingBetweenTabs(driver, 0);
	 
		return checks;
	}
}
