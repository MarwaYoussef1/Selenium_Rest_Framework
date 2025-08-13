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

import stc.esport.api.pojo.Teams.TeamInvitation;
import stc.esport.api.services.ApplyForPostService;
import stc.esport.api.services.CallForMemberService;
import stc.esport.api.services.LoginService;
import stc.esport.api.services.TeamService;
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
import stc.esport.pages.teamsclans.MyTeamsListPage;
import stc.esport.pojo.Account;
import stc.esport.testdata.TestData;
import stc.esport.utils.Constants;

public class InvitationsTests extends TestBase {
	// Variables
	LoginPage loginPageObj;
	HomePage homePageObj;
	SignUpPage signUpPageObj;
	fakeMailPage fakeMailPageObj;
	DashBoardPage dashBoardPageObj;
	InvitationsListPage invitationListPageObj;
	NotificationPage notificationPageObj;
	RejectInvitationPage RejectInvitationPageObj;
	MyTeamsListPage myTeamsListPageObj;
	ContentCreatorsPage contentCreatorsPageObj;
	MyClubPage myClubPageObj;

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
		myTeamsListPageObj= new MyTeamsListPage(driver,properties);
		contentCreatorsPageObj= new ContentCreatorsPage(driver,properties);
		myClubPageObj=new MyClubPage(driver,properties);

	}

	@BeforeTest
	public void BeforeTest() {
		ReportManager.log("BeforeTest");
	}

	@DataProvider(name = "testSubmitActionByTeamMemberData")
	public Object[][] testSubmitActionByTeamMemberData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"), "testSubmitActionByTeamMember");
		return data;
	}

	@DataProvider(name = "testSubmitActionByNonRegMemberData")
	public Object[][] testSubmitActionByNonRegMemberData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"), "testSubmitActionByNonRegMember");
		return data;
	}
	
	@DataProvider(name = "testSubmitActionByClubOwnerData")
	public Object[][] testSubmitActionByClubOwnerData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"), "testSubmitActionByClubOwner");
		return data;
	}
	@DataProvider(name = "testCheckAutoFilledFieldsData")
	public Object[][] testCheckAutoFilledFieldsData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"), "testCheckAutoFilledFields");
		return data;
	}

	@Test(dataProvider = "testSubmitActionByTeamMemberData")
	public void testSubmitActionByTeamMember(String actionType) {
		ReportManager.log("============== Start  Registered team member Accept/Reject invitation Test   ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerEmail = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String notificationMsg = null;
		if (actionType.equals(Constants.ACCEPTINVITATION))
			notificationMsg = properties.getProperty("AcceptNotificationMsg");
		else
			notificationMsg = properties.getProperty("RejectNotificationMsg");

		ArrayList<Boolean> actionTakenChecks = new ArrayList<Boolean>();

		// Step1 Login by club owner to create team and send invitation to player
		String teamName = prepareInvitationByClubOwner(clubOwner, clubOwnerPw, playerEmail);
		Validations.assertThat().object(teamName).isNotNull().perform();

		// Step2 Player login to accept/Reject invitation
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(playerEmail, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by player");
		dashBoardPageObj.navigateToInvitations();
		if (actionType.equals(Constants.ACCEPTINVITATION))
			actionTakenChecks = invitationListPageObj.acceptRejectInvitation(Constants.ACCEPTINVITATION, teamName);
		else
			actionTakenChecks = invitationListPageObj.acceptRejectInvitation(Constants.REJECTINVITATION, teamName);

		Validations.assertThat().object(actionTakenChecks.get(0)).isTrue().perform();
		ReportManager.log("The invitation status is changed to accepted/rejected after click on Accept/Reject ");

		Validations.assertThat().object(actionTakenChecks.get(1)).isTrue().perform();
		ReportManager.log("All buttons hidden after accept/reject action");

		Validations.assertThat().object(actionTakenChecks.get(2)).isTrue().perform();
		ReportManager.log("The invitation status in list is changed to accepted/rejected after click on Accept/Reject");

		dashBoardPageObj.logout();
		ReportManager.log("Player log out ");

		// Step3 login by Club owner to check on notification
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by Club owner");
		String notificationfullMsg = properties.getProperty("playerAccName") + " " + notificationMsg + " " + teamName;
		boolean notificatioDisplayed = notificationPageObj.checkNotficationContent(notificationfullMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Notification message displayed successfully");

		dashBoardPageObj.logout();
		ReportManager.log("Club owner log out ");

		ReportManager.log("==============End Registered team member Accept/Reject invitation Test  ==============");
	}
	
	@Test(dataProvider = "testSubmitActionByClubOwnerData")
	public void testSubmitActionByClubOwner(String actionType) {
		ReportManager.log("============== Start Club owner Accept/Reject invitation Test   ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerEmail = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String playerAccountName=properties.getProperty("playerAccName");
		String notificationMsg = null;
		boolean memberAddedToProfile=false;
		
		if (actionType.equals(Constants.ACCEPTINVITATION))
			notificationMsg = properties.getProperty("AcceptRequestNotificationMsg");
		else
			notificationMsg = properties.getProperty("RejectRequestNotificationMsg1");

		ArrayList<Boolean> actionTakenChecks = new ArrayList<Boolean>();

		// Step1 Login by club owner to create team ,create post & player request to join team
	    String teamName= prepareJoinReqByTeamMember(clubOwner, clubOwnerPw,  playerEmail, playerPw);

		// Step2 Club owner login to accept/Reject invitation
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner");
		dashBoardPageObj.navigateToInvitations();
		if (actionType.equals(Constants.ACCEPTINVITATION))
			actionTakenChecks = invitationListPageObj.acceptRejectInvitation(Constants.ACCEPTINVITATION, teamName);
		else
			actionTakenChecks = invitationListPageObj.acceptRejectInvitation(Constants.REJECTINVITATION, teamName);

		Validations.assertThat().object(actionTakenChecks.get(0)).isTrue().perform();
		ReportManager.log("The invitation status is changed to accepted/rejected after click on Accept/Reject ");

		Validations.assertThat().object(actionTakenChecks.get(1)).isTrue().perform();
		ReportManager.log("All buttons hidden after accept/reject action");

		Validations.assertThat().object(actionTakenChecks.get(2)).isTrue().perform();
		ReportManager.log("The invitation status in list is changed to accepted/rejected after click on Accept/Reject");

		// Step3  in case accept check on team profile if player added
		if(actionType.equals(Constants.ACCEPTINVITATION))
		{
			dashBoardPageObj.navigateToTeams();
			memberAddedToProfile=myTeamsListPageObj.checkTeamMemeberAddedInProfile(teamName, Constants.CLUBPLAYERROLE, playerAccountName);	
			Validations.assertThat().object(memberAddedToProfile).isTrue().perform();
			ReportManager.log("Player added to team successfully. ");
		}
		
		// Step4 club owner logout 
		dashBoardPageObj.navigateToInvitations();
		dashBoardPageObj.logout();
		ReportManager.log("Club owner log out ");

		// Step5 login by Player to check on notification
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(playerEmail, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by player");
		String notificationfullMsg =" " + notificationMsg + " " + teamName;
		if(actionType.equals(Constants.REJECTINVITATION))
			notificationfullMsg=notificationfullMsg + " " + properties.getProperty("RejectRequestNotificationMsg2");
		boolean notificatioDisplayed = notificationPageObj.checkNotficationContent(notificationfullMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Notification message displayed successfully");
		
		//Step 6 in case of rejection check on sent invitation list the invitation not found 
		/*if(actionType.equals(Constants.REJECTINVITATION))
		{
			dashBoardPageObj.navigateToInvitations();
			Validations.assertThat().object(invitationListPageObj.searchOnInvitationByTeam(Constants.SENTINVITATION, teamName, null)).isNull().perform();
			ReportManager.log("Rejected invitation disappeared from player sent invitation list");
		}*/
		dashBoardPageObj.logout();
		ReportManager.log("player log out ");

		ReportManager.log("============== End Club owner Accept/Reject invitation Test  ==============");
	}

	@Test(dataProvider = "testSubmitActionByNonRegMemberData")
	public void testSubmitActionByNonRegMember(String actionType) {
		ReportManager.log("============== Start Accept/reject invitation by non Registered user Test   ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String playerPw = properties.getProperty("password");
		boolean invitationStatus;
		String expectedInvitationStatus;
		String notificationMsg = null;

		Account accountObj = new Account(properties);
		accountObj.setRoleType(Constants.CLUBPLAYERROLE);
		String playerEmail = accountObj.getEmail();

		if (actionType.equals(Constants.ACCEPTINVITATIONMAIL))
			notificationMsg = properties.getProperty("AcceptNotificationMsg");
		else
			notificationMsg = properties.getProperty("RejectNotificationMsg");

		// Step1 Login by club owner to create team and send invitation to player
		String teamName = prepareInvitationByClubOwner(clubOwner, clubOwnerPw, playerEmail);
		Validations.assertThat().object(teamName).isNotNull().perform();

		// Step2 Player open invitation email to accept/Reject invitation
		fakeMailPageObj.submitInvitationAction(actionType, playerEmail,Constants.INVITATION_MAIL_SUBJECT);
		ReportManager.log("Player submit action from invitation mail.");
		if (actionType.equals(Constants.ACCEPTINVITATIONMAIL))
		{       
			    playerAccountName =  accountObj.getName();
				expectedInvitationStatus=Constants.INVITATIONACCEPTED;
		}
		else
		{
			playerAccountName = playerEmail;
			expectedInvitationStatus=Constants.INVITATIONREJECTED;
		}

		if (actionType.equals(Constants.ACCEPTINVITATIONMAIL) || actionType.equals(Constants.REJECTJOININVITATIONMAIL)) {
			// Step3 Player complete sign up form in case of accept or reject and join
			Validations.assertThat().object(signUpPageObj.completeSignUpForm(accountObj)).isTrue().perform();;
			ReportManager.log("Account created successfully");
			SignUpPage.switchingBetweenTabs(driver, 0);
			homePageObj.clickLoginLink();
			// Step4 Player login to check on invitation status
			Validations.assertThat().object(loginPageObj.login(playerEmail, playerPw)).isTrue().perform();
			ReportManager.log("Login done  successfully by player");
			dashBoardPageObj.navigateToInvitations();
			invitationStatus = invitationListPageObj.openCheckInvitationStatus(Constants.RECEIVEDINVITATION, teamName,
					teamName, expectedInvitationStatus);
			Validations.assertThat().object(invitationStatus).isTrue().perform();
			ReportManager.log("Invitation Status changed in list and detail page");
			dashBoardPageObj.logout();
			ReportManager.log("Player log out ");
		} else {
			// Reject without join
			// Step3 Player reject without join the team
			Validations.assertThat().object(RejectInvitationPageObj.checkInvitationMsg()).isTrue().perform();
			ReportManager.log("Reject invitation message displayed to the user and user click on 'Go Home' button");

		}

		// Step3 login by Club owner to check on notification & invitation status
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by Club owner");
		String notificationfullMsg = playerAccountName + " " + notificationMsg + " " + teamName;
		boolean notificatioDisplayed = notificationPageObj.checkNotficationContent(notificationfullMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Notification message displayed successfully");

		if (actionType.equals(Constants.REJECTJOININVITATIONMAIL))
		{       
			    playerAccountName =  accountObj.getName();
		}
		dashBoardPageObj.navigateToInvitations();
		invitationStatus = invitationListPageObj.openCheckInvitationStatus(Constants.SENTINVITATION, teamName,
				playerAccountName, expectedInvitationStatus);
		Validations.assertThat().object(invitationStatus).isTrue().perform();
		ReportManager.log("Invitation Status changed in club owner list and detail page");
		
		dashBoardPageObj.logout();
		ReportManager.log("Club owner log out ");

		ReportManager.log("==============End Accept/reject invitation by non Registered user Test============ ");
	}

	@Test(dataProvider = "testCheckAutoFilledFieldsData")
	public void testCheckAutoFilledFields(String actionType) {
		ReportManager.log("============== Start check autofilled role & email Test to complete sign up  ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		boolean autofilled;
		Account accountObj = new Account(properties);
		accountObj.setRoleType(Constants.CLUBPLAYERROLE);
		String playerEmail = accountObj.getEmail();

		// Step1 Login by club owner to create team and send invitation to player
		String teamName = prepareInvitationByClubOwner(clubOwner, clubOwnerPw, playerEmail);
		Validations.assertThat().object(teamName).isNotNull().perform();

		// Step2 Player open invitation email to accept/Reject invitation
		fakeMailPageObj.submitInvitationAction(actionType, playerEmail,Constants.INVITATION_MAIL_SUBJECT);
		ReportManager.log("Player submit action from invitation mail.");
		//Step3 check on autofilled fields 
		autofilled=signUpPageObj.checkAutoFilledFields();
		if (actionType.equals(Constants.ACCEPTINVITATIONMAIL))
		{       
			Validations.assertThat().object(autofilled).isTrue().perform();
		}
		else
		{
			Validations.assertThat().object(autofilled).isFalse().perform();
		}

		ReportManager.log("============== End check autofilled Role & email Test to complete sign up   ==============");
	}

	@Test()
	public void testCancelInvitationByClubOwner() {
		ReportManager.log("============== Start Cancel invitation by Club owner Test   ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerEmail = properties.getProperty("playerAcc");

		boolean invitationCanceled;

		// Step1 Login by club owner to create team and send invitation to player
		String teamName = prepareInvitationByClubOwner(clubOwner, clubOwnerPw, playerEmail);
		Validations.assertThat().object(teamName).isNotNull().perform();

		// Step2 Club owner login to cancel invitation
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by Club owner");
		dashBoardPageObj.navigateToInvitations();
		invitationCanceled = invitationListPageObj.cancelInvitation(teamName, properties.getProperty("playerAccName"));
		Validations.assertThat().object(invitationCanceled).isTrue().perform();

		dashBoardPageObj.logout();
		ReportManager.log("Club owner log out ");

		ReportManager.log("============== End  Cancel invitation by Club owner Test   ==============");
	}
	
	@Test
	public void testViewProfile() {
		ReportManager.log("============== Start  View Team/club profile from invitation Test   ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerEmail = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String clubName= properties.getProperty("clubName");
		ArrayList<Boolean> actionTakenChecks = new ArrayList<Boolean>();
		// Step1 Login by club owner to create team and send invitation to player
		String teamName = prepareInvitationByClubOwner(clubOwner, clubOwnerPw, playerEmail);
		Validations.assertThat().object(teamName).isNotNull().perform();

		// Step2 Player login to open invitation
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(playerEmail, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by player");
		dashBoardPageObj.navigateToInvitations();
		ReportManager.log("Start View Club & Team Profile.");
		actionTakenChecks = invitationListPageObj.viewProfiles(teamName, clubName);
		Validations.assertThat().object(actionTakenChecks.get(0)).isTrue().perform();
		ReportManager.log("Redirection to team profile done successfully.");

		Validations.assertThat().object(actionTakenChecks.get(1)).isTrue().perform();
		ReportManager.log("Redirection to club profile done successfully.");

		ReportManager.log("End View Club & Team Profile.");
		dashBoardPageObj.logout();
		ReportManager.log("Player log out ");

		ReportManager.log("==============End  view Team/club profile from invitation Test  ==============");
	}
	
	

	private String prepareInvitationByClubOwner(String clubOwnerEmail, String clubOwnerPw, String email) {
		LoginService loginServiceObj = new LoginService(properties);
		String clubOwnerToken = loginServiceObj.login(clubOwnerEmail, clubOwnerPw);
		TeamInvitation playerinvitation = new TeamInvitation();
		TeamService teamService = new TeamService(apiObj, properties);
		playerinvitation.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
		playerinvitation.setEmail(email);
		ArrayList<TeamInvitation> invitations = new ArrayList<TeamInvitation>();
		invitations.add(playerinvitation);
		return teamService.createTeamWithInvitation(clubOwnerToken, true, invitations).getName();
	}

	private String prepareJoinReqByTeamMember(String clubOwnerEmail, String clubOwnerPw, String playerEmail,String playerPw) {
		LoginService loginServiceObj = new LoginService(properties);
		String clubOwnerToken = loginServiceObj.login(clubOwnerEmail,clubOwnerPw);
    	TeamService teamService = new TeamService(apiObj,properties);
    	String teamName=teamService.createTeamWithInvitation(clubOwnerToken,true,null).getName();
    	String teamId= teamService.filterTeamByTeamEnName(teamName,Constants.MYTEAMLIST,clubOwnerToken);
    	CallForMemberService callForMemberService = new CallForMemberService(apiObj,properties);
    	callForMemberService.callForMemberPost(clubOwnerToken,teamId);
    	String playerToken = loginServiceObj.login(playerEmail,playerPw);
    	ApplyForPostService applyForMemberService = new ApplyForPostService(playerToken,apiObj);
    	applyForMemberService.joinTeam(teamId);
    	return teamName;
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
