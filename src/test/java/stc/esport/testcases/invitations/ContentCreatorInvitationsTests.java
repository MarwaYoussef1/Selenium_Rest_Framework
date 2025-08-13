package stc.esport.testcases.invitations;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shaft.driver.SHAFT.Validations;
import com.shaft.tools.io.ReportManager;

import stc.esport.api.services.InvitationService;
import stc.esport.api.services.LoginService;
import stc.esport.base.TestBase;
import stc.esport.pages.DashBoardPage;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;
import stc.esport.pages.SignUpPage;
import stc.esport.pages.fakeMailPage;
import stc.esport.pages.invitations.InvitationsListPage;
import stc.esport.pages.invitations.RejectInvitationPage;
import stc.esport.pages.myClub.ContentCreatorsPage;
import stc.esport.pages.myClub.MyClubPage;
import stc.esport.pages.notification.NotificationPage;
import stc.esport.pojo.Account;
import stc.esport.testdata.TestData;
import stc.esport.utils.Constants;

public class ContentCreatorInvitationsTests extends TestBase {
	// Variables
	LoginPage loginPageObj;
	HomePage homePageObj;
	SignUpPage signUpPageObj;
	fakeMailPage fakeMailPageObj;
	DashBoardPage dashBoardPageObj;
	InvitationsListPage invitationListPageObj;
	NotificationPage notificationPageObj;
	RejectInvitationPage RejectInvitationPageObj;
	ContentCreatorsPage contentCreatorsPageObj;
	MyClubPage myClubPageObj;
	
	LoginService loginServiceObj;

	@BeforeSuite
	public void BeforeSuite() {
		ReportManager.log("BeforeSuite");
		ReportManager.log("");
	}

	@BeforeClass
	public void BeforeClass() {
		ReportManager.log("BeforeClass");
	}

	@BeforeMethod
	public void BeforeMethod() {
		ReportManager.log("BeforeMethod");
		openBrowser();
		getBaseApiObj();
		loginPageObj = new LoginPage(driver, properties);
		homePageObj = new HomePage(driver, properties);
		dashBoardPageObj = new DashBoardPage(driver, properties);
		invitationListPageObj = new InvitationsListPage(driver, properties);
		notificationPageObj = new NotificationPage(driver, properties);
		signUpPageObj = new SignUpPage(driver, properties);
		fakeMailPageObj = new fakeMailPage(driver, properties);
		RejectInvitationPageObj = new RejectInvitationPage(driver);
		contentCreatorsPageObj = new ContentCreatorsPage(driver, properties);
		myClubPageObj = new MyClubPage(driver, properties);
		

	}

	@BeforeTest
	public void BeforeTest() {
		ReportManager.log("BeforeTest");
	}

	@DataProvider(name = "testContentCreatorInvitationData")
	public Object[][] testContentCreatorInvitationData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"), "testContentCreatorInvitation");
		return data;
	}

	@DataProvider(name = "testNonRegContentCreatorInvitationData")
	public Object[][] testNonRegContentCreatorInvitationData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"),
				"testNonRegContentCreatorInvitation");
		return data;
	}

	@Test(dataProvider = "testContentCreatorInvitationData")
	public void testContentCreatorInvitation(String actionType) {
		ReportManager.log("============== Start  Content creator invitation test  ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String contentEmail = properties.getProperty("contentAccInviteEmail");
		String contentPw = properties.getProperty("contentPW");
		String contentAccountName = properties.getProperty("contentAccInviteName");
		String invitationTitle = properties.getProperty("InvitationTitle");
		boolean contentCreatorInvited;
		boolean contentCreatorAccepted;
		boolean contentCreatorRemoved;
		String notificationMsg = null;
		ArrayList<Boolean> actionTakenChecks = new ArrayList<Boolean>();

		// Step1 Club owner login to invite content creator
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner");
		myClubPageObj.navigateToContentCreators();
		contentCreatorInvited = contentCreatorsPageObj.addContentCreator(contentEmail, contentAccountName);
		Validations.assertThat().object(contentCreatorInvited).isTrue().perform();
		dashBoardPageObj.logout();
		ReportManager.log("Club owner log out ");

		// Step2 Content Creator login to accept invitation
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(contentEmail, contentPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by content creator");
		dashBoardPageObj.navigateToInvitations();
		if (actionType.equals(Constants.ACCEPTINVITATION))
			actionTakenChecks = invitationListPageObj.acceptRejectInvitationByStatus(Constants.ACCEPTINVITATION,
					Constants.INVITATIONPENDING, invitationTitle);
		else
			actionTakenChecks = invitationListPageObj.acceptRejectInvitationByStatus(Constants.REJECTINVITATION,
					Constants.INVITATIONPENDING, invitationTitle);

		Validations.assertThat().object(actionTakenChecks.get(0)).isTrue().perform();
		ReportManager.log("The invitation status is changed to accepted/rejected after click on Accept/Reject ");
		Validations.assertThat().object(actionTakenChecks.get(1)).isTrue().perform();
		ReportManager.log("All buttons hidden after accept/reject action");
		Validations.assertThat().object(actionTakenChecks.get(2)).isTrue().perform();
		ReportManager.log("The invitation status in list is changed to accepted/rejected after click on Accept/Reject");
		dashBoardPageObj.logout();
		ReportManager.log("Content Creator log out ");

		// Step3 Login with club owner to check on notifications
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner");
		if (actionType.equals(Constants.ACCEPTINVITATION))
			notificationMsg = properties.getProperty("AcceptNotificationMsg");
		else
			notificationMsg = properties.getProperty("RejectNotificationMsg");

		String notificationfullMsg = contentAccountName + " " + notificationMsg;
		boolean notificatioDisplayed = notificationPageObj.checkNotficationContent(notificationfullMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Notification message displayed successfully");

		// Step4 club owner check on content creator status
		if (actionType.equals(Constants.ACCEPTINVITATION)) {
			myClubPageObj.navigateToContentCreators();
			contentCreatorAccepted = contentCreatorsPageObj.checkCardDetails(contentAccountName,
					Constants.INVITATIONACCEPTED);
			Validations.assertThat().object(contentCreatorAccepted).isTrue().perform();
			ReportManager.log("The content creator accepted successfully.");
			// Step5: remove the content creator
			contentCreatorRemoved = contentCreatorsPageObj.removeCancelInvitation(contentAccountName, Constants.DELETE);
			Validations.assertThat().object(contentCreatorRemoved).isTrue().perform();
			ReportManager.log("The content creator removed successfully.");

		}

		// Step6: club owner logout
		dashBoardPageObj.logout();
		ReportManager.log("Club owner log out ");

		ReportManager.log("============== End Club owner Content creator invitation test  ==============");
	}

	@Test(dataProvider = "testNonRegContentCreatorInvitationData")
	public void testNonRegContentCreatorInvitation(String actionType) {
		ReportManager.log(
				"============== Start Accept/reject invitation by non Registered ContentCreator Test   ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String contentPw = properties.getProperty("password");
		String contentAccountName;
		String invitationTitle = properties.getProperty("InvitationTitle");
		boolean invitationStatus;
		boolean contentCreatorInvited;
		boolean contentCreatorAccepted;
		boolean contentCreatorRemoved;
		String expectedInvitationStatus;
		String notificationMsg = null;

		Account accountObj = new Account(properties);
		accountObj.setRoleType(Constants.CONTENTCREATORROLE);
		String contentEmail = accountObj.getEmail();

		if (actionType.equals(Constants.ACCEPTINVITATIONMAIL))
			notificationMsg = properties.getProperty("AcceptNotificationMsg");
		else
			notificationMsg = properties.getProperty("RejectNotificationMsg");

		// Step1 Club owner login to invite content creator
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner");
		myClubPageObj.navigateToContentCreators();
		contentCreatorInvited = contentCreatorsPageObj.addContentCreator(contentEmail, contentEmail);
		Validations.assertThat().object(contentCreatorInvited).isTrue().perform();
		dashBoardPageObj.logout();
		ReportManager.log("Club owner log out ");

		// Step2 Content creator open invitation email to accept/Reject invitation
		fakeMailPageObj.submitInvitationAction(actionType, contentEmail, Constants.INVITATION_JOIN_CLUB_SUBJECT);
		ReportManager.log("Content creator submit action from invitation mail.");
		if (actionType.equals(Constants.ACCEPTINVITATIONMAIL)) {
			contentAccountName = accountObj.getName();
			expectedInvitationStatus = Constants.INVITATIONACCEPTED;
		} else {
			contentAccountName = contentEmail;
			expectedInvitationStatus = Constants.INVITATIONREJECTED;
		}

		if (actionType.equals(Constants.ACCEPTINVITATIONMAIL)
				|| actionType.equals(Constants.REJECTJOININVITATIONMAIL)) {
			// Step3 Content creator complete sign up form in case of accept or reject and
			// join
			Validations.assertThat().object(signUpPageObj.completeSignUpForm(accountObj)).isTrue().perform();
			;
			ReportManager.log("Account created successfully");
			SignUpPage.switchingBetweenTabs(driver, 0);
			homePageObj.clickLoginLink();
			// Step4 Content creator login to check on invitation status
			Validations.assertThat().object(loginPageObj.login(contentEmail, contentPw)).isTrue().perform();
			ReportManager.log("Login done  successfully by player");
			dashBoardPageObj.navigateToInvitations();
			invitationStatus = invitationListPageObj.openCheckInvitationStatus(Constants.RECEIVEDINVITATION, null,
					invitationTitle, expectedInvitationStatus);
			Validations.assertThat().object(invitationStatus).isTrue().perform();
			ReportManager.log("Invitation Status changed in list and detail page");
			dashBoardPageObj.logout();
			ReportManager.log("Content creator log out ");
		} else {
			// Reject without join
			// Step3 Content creator reject without join the team
			Validations.assertThat().object(RejectInvitationPageObj.checkInvitationMsg()).isTrue().perform();
			ReportManager.log("Reject invitation message displayed to the user and user click on 'Go Home' button");

		}

		// Step3 login by Club owner to check on notification & Card status & invitation
		// status
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by Club owner");
		String notificationfullMsg = contentAccountName + " " + notificationMsg;
		boolean notificatioDisplayed = notificationPageObj.checkNotficationContent(notificationfullMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Notification message displayed successfully");

		//Step 4: Check on card status
		myClubPageObj.navigateToContentCreators();
		if (actionType.equals(Constants.ACCEPTINVITATIONMAIL)) {
			contentCreatorAccepted = contentCreatorsPageObj.checkCardDetails(contentAccountName,
					Constants.INVITATIONACCEPTED);
			Validations.assertThat().object(contentCreatorAccepted).isTrue().perform();
			ReportManager.log("The content creator accepted successfully.");
			// Step5: remove the content creator
			contentCreatorRemoved = contentCreatorsPageObj.removeCancelInvitation(contentAccountName, Constants.DELETE);
			Validations.assertThat().object(contentCreatorRemoved).isTrue().perform();
			ReportManager.log("The content creator removed successfully.");
		} else if (actionType.equals(Constants.REJECTJOININVITATIONMAIL)) {
			contentAccountName = accountObj.getName();
			

		} else
		{
			
		}
		// Step5: check on invitation status
			dashBoardPageObj.navigateToInvitations();
		invitationStatus = invitationListPageObj.openCheckInvitationStatus(Constants.SENTINVITATION, null,
				contentAccountName, expectedInvitationStatus);
		Validations.assertThat().object(invitationStatus).isTrue().perform();
		ReportManager.log("Invitation Status changed in club owner list and detail page");

		dashBoardPageObj.logout();
		ReportManager.log("Club owner log out ");

		ReportManager.log("==============End Accept/reject invitation by non Registered user Test============ ");
	}
	
	@Test
	public void testEditApprovedInvitation() {
		ReportManager.log("============== Start  Edit Approved Content creator test  ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String contentAccountName = properties.getProperty("contentAccInviteName");
		String contentImage = properties.getProperty("ContentCreatorImage");
		boolean contentCreatorEdited;
		boolean contentCreatorApproved;
		boolean contentCreatorAdded;

		// Step1 Content Creator created by club owner
		prepareApprovedContentCreator();

		// Step2 Login with club owner to edit the content creator
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner");
		myClubPageObj.navigateToContentCreators();
		contentCreatorApproved = contentCreatorsPageObj.checkCardDetails(contentAccountName,Constants.INVITATIONACCEPTED);
		Validations.assertThat().object(contentCreatorApproved).isTrue().perform();
		ReportManager.log("The content creator is Approved.");
		contentCreatorEdited = contentCreatorsPageObj.editCardDetails(contentAccountName,contentImage,false,true);
		Validations.verifyThat().object(contentCreatorEdited).isTrue().perform();
		ReportManager.log("The content creator Edited successfully.");
		
		//Step 3 check card Photo
		Validations.verifyThat().object(contentCreatorsPageObj.checkCardPhoto(contentAccountName, contentImage)).isTrue().perform();
		ReportManager.log("The content creator  Photo uploaded successfully.");
		
		//Step4 check on content creator in public profile
		contentCreatorAdded=myClubPageObj.checkIfContentCreatorInProfile(contentAccountName);
		Validations.verifyThat().object(contentCreatorAdded).isTrue().perform();
		ReportManager.log("The content creator is Added to profile.");
		
		//Step5 remove content creator
		myClubPageObj.navigateToContentCreators();
		Validations.assertThat().object(contentCreatorsPageObj.removeCancelInvitation(contentAccountName, Constants.DELETE)).isTrue().perform();
		ReportManager.log("The content creator deleted successfully.");
		
		dashBoardPageObj.logout();
		ReportManager.log("Club owner log out ");

		ReportManager.log("============== End  Edit Approved Content creator test   ==============");
	}


	@Test
	public void testCancelPendingInvitation() {
		ReportManager.log("============== Start  Cancel pending Content creator test  ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String contentEmail = properties.getProperty("contentAccInviteEmail");
		String contentAccountName = properties.getProperty("contentAccInviteName");
		
		boolean contentCreatorInvited;
		boolean contentCreatorCancelled;
		boolean contentCreatorPending;

		// Step1 Club owner login to invite content creator
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner");
		myClubPageObj.navigateToContentCreators();
		contentCreatorInvited = contentCreatorsPageObj.addContentCreator(contentEmail, contentAccountName);
		Validations.assertThat().object(contentCreatorInvited).isTrue().perform();
		// Step2 Cancel pending invitation
		myClubPageObj.navigateToContentCreators();
		contentCreatorPending = contentCreatorsPageObj.checkCardDetails(contentAccountName,
				Constants.INVITATIONPENDING);
		Validations.assertThat().object(contentCreatorPending).isTrue().perform();
		ReportManager.log("The content creator is pending.");
		contentCreatorCancelled = contentCreatorsPageObj.removeCancelInvitation(contentAccountName,
				Constants.CANCELINVITATION);
		Validations.assertThat().object(contentCreatorCancelled).isTrue().perform();
		ReportManager.log("The content creator invitation cancelled successfully.");
		// Step3 check on invitation status in invitation list
		dashBoardPageObj.navigateToInvitations();
		Validations.assertThat().object(invitationListPageObj.openCheckInvitationStatus(Constants.SENTINVITATION, null,contentAccountName,Constants.CANCELEDINVITATION)).isTrue().perform();
		ReportManager.log("The content creator invitation status is cancelled.");
		dashBoardPageObj.logout();
		ReportManager.log("Club owner log out ");
		ReportManager.log("============== End  Cancel pending Content creator test  ==============");
	}

	@Test
	public void testRemoveApprovedInvitation() {
		ReportManager.log("============== Start  Remove Approved Content creator test  ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String contentEmail = properties.getProperty("contentAccInviteEmail");
		String contentPw = properties.getProperty("contentPW");
		String contentAccountName = properties.getProperty("contentAccInviteName");
		String notificationMsg = properties.getProperty("RemoveNotifcationMsg");
		boolean contentCreatorRemoved;
		boolean contentCreatorApproved;

		// Step1 Content Creator created by club owner
		prepareApprovedContentCreator();

		// Step2 Login with club owner to remove the content creator
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner");
		myClubPageObj.navigateToContentCreators();
		contentCreatorApproved = contentCreatorsPageObj.checkCardDetails(contentAccountName,
				Constants.INVITATIONACCEPTED);
		Validations.assertThat().object(contentCreatorApproved).isTrue().perform();
		ReportManager.log("The content creator is Approved.");
		contentCreatorRemoved = contentCreatorsPageObj.removeCancelInvitation(contentAccountName, Constants.DELETE);
		Validations.assertThat().object(contentCreatorRemoved).isTrue().perform();
		ReportManager.log("The content creator removed successfully.");
		dashBoardPageObj.logout();
		ReportManager.log("Club owner log out ");

		// Step3 Content Creator login to check notification
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(contentEmail, contentPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner");
		boolean notificatioDisplayed = notificationPageObj.checkNotficationContent(notificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Notification message displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("Content creator log out ");
		ReportManager.log("============== End Club owner Content creator invitation test  ==============");
	}

	private void prepareApprovedContentCreator() {
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String contentEmail = properties.getProperty("contentAccInviteEmail");
		String contentPw = properties.getProperty("contentPW");
		LoginService loginServiceObj = new LoginService(properties);
		String contentToken = loginServiceObj.login(contentEmail, contentPw);
		InvitationService invitationServiceObj = new InvitationService(contentToken, apiObj);
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		boolean contentCreatorInvited = invitationServiceObj.inviteSpecialMember(clubOwnerToken,
				Constants.INVITATION_CONTENT_CREATOR_TYPE_API, contentEmail);
		Validations.assertThat().object(contentCreatorInvited).isTrue().perform();
		String invitationId = invitationServiceObj.getInvitationByStatusAndTeam(Constants.INVITATIONPENDING, null,
				Constants.RECEIVEDINVITATION);
		boolean contentCreatorAccepted = invitationServiceObj.acceptRejectCancelInvitation(Constants.ACCEPTINVITATION,
				invitationId);
		Validations.assertThat().object(contentCreatorAccepted).isTrue().perform();

	}

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		ReportManager.log("AfterMethod");
		closeBrowser();
	}

	@AfterTest
	public void AfterTest() {
		ReportManager.log("AfterTest");
	}

	@AfterClass
	public void AfterClass() {
		ReportManager.log("After Class");
	}

	@AfterSuite
	public void AfterSuite() {
		ReportManager.log("After Suite");
	}

}
