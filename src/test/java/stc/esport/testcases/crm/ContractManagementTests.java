package stc.esport.testcases.crm;

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

import stc.esport.api.business.ContractManagement;
import stc.esport.api.pojo.Teams.TeamInvitation;
import stc.esport.api.services.InvitationService;
import stc.esport.api.services.LoginService;
import stc.esport.api.services.TeamService;
import stc.esport.base.TestBase;
import stc.esport.pages.DashBoardPage;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;
import stc.esport.pages.fakeMailPage;
import stc.esport.pages.Teammanagement.TeamDetailsPage;
import stc.esport.pages.Teammanagement.TeamsManagementListPage;
import stc.esport.pages.contracts.ManageContractsPage;
import stc.esport.pages.contracts.UploadContractPage;
import stc.esport.pages.contracts.ViewContractDetailsPage;
import stc.esport.pages.crm.CRMListPage;
import stc.esport.pages.invitations.InvitationDetailsPage;
import stc.esport.pages.invitations.InvitationsListPage;
import stc.esport.pages.notification.NotificationPage;
import stc.esport.pages.teamsclans.AddTeamPage;
import stc.esport.pages.teamsclans.MyTeamsListPage;
import stc.esport.pages.teamsclans.TeamContractsPage;
import stc.esport.pages.teamsclans.TeamsDashboardPage;
import stc.esport.pojo.ContractRequest;
import stc.esport.pojo.ContractRequest;
import stc.esport.pojo.Teams;
import stc.esport.testdata.TestData;
import stc.esport.utils.Constants;

public class ContractManagementTests extends TestBase {
	// Variables
	LoginPage loginPageObj;
	HomePage homePageObj;
	DashBoardPage dashBoardPageObj;
	CRMListPage crmListPageObj;
	fakeMailPage fakeMailPageObj;
	TeamsManagementListPage teamsManagementListPageObj;
	TeamDetailsPage teamDetailsPageObj;
	TeamsDashboardPage teamsDashboardPageObj;
	MyTeamsListPage myTeamsListPageObj;
	TeamContractsPage teamContractsPageObj;
	NotificationPage notificationPageObj;
	LoginService loginServiceObj;
	UploadContractPage uploadContractPageObj;
	ManageContractsPage manageContractsPageObj;
	InvitationsListPage invitationListPageObj;
	ViewContractDetailsPage viewContractDetailsPageObj;
	InvitationDetailsPage invitationDetailsPageObj;
	AddTeamPage addTeamPageObj;

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
		crmListPageObj = new CRMListPage(driver, properties);
		fakeMailPageObj = new fakeMailPage(driver, properties);
		teamsManagementListPageObj = new TeamsManagementListPage(driver, properties);
		teamsDashboardPageObj = new TeamsDashboardPage(driver, properties);
		myTeamsListPageObj = new MyTeamsListPage(driver, properties);
		teamContractsPageObj = new TeamContractsPage(driver, properties);
		notificationPageObj = new NotificationPage(driver, properties);
		loginServiceObj = new LoginService(properties);
		uploadContractPageObj = new UploadContractPage(driver, properties);
		manageContractsPageObj = new ManageContractsPage(driver, properties);
		invitationListPageObj = new InvitationsListPage(driver, properties);
		viewContractDetailsPageObj = new ViewContractDetailsPage(driver, properties);
		invitationDetailsPageObj = new InvitationDetailsPage(driver, properties);
		addTeamPageObj = new AddTeamPage(driver, properties);
		teamDetailsPageObj = new TeamDetailsPage(driver, properties);

	}

	@BeforeTest
	public void BeforeTest() {
		ReportManager.log("BeforeTest");
	}

	@DataProvider(name = "UploadContract")
	public Object[][] testAddTeamData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"), "testUploadContract");
		return data;
	}

	@DataProvider(name = "contractInvitation")
	public Object[][] testContractInvitation() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"), "testContractInvitation");
		return data;
	}

	@DataProvider(name = "testContractRequestApproveData")
	public Object[][] testContractRequestApproveData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"), "testContractRequestApprove");
		return data;
	}

	@DataProvider(name = "testRemoveActiveRequestData")
	public Object[][] testRemoveActiveContractRequest() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"), "testRemoveActiveContractRequest");
		return data;
	}
	
	@DataProvider(name = "testArchiveTeamWithActiveRequestData")
	public Object[][] testArchiveTeamWithActiveRequestData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"), "testArchiveTeamWithActiveRequest");
		return data;
	}

	@Test(dataProvider = "UploadContract")
	public void testUploadContract(boolean memberAgeMoreThan16) {
		ReportManager.log("==============  Start upload contracts for member test ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;

		String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String clubName = properties.getProperty("clubNameEn");
		String playerAcc = properties.getProperty("playerAcc");
		// String playerPW=properties.getProperty("playerPW");
		String playerAccName = properties.getProperty("playerAccName");
		String playerMobileNum = properties.getProperty("playerAccMobile");

		String pendingContractStatus = Constants.PENDINGMEMBERAPPROVAL;

		ContractRequest contractObj = new ContractRequest(properties);
		if (memberAgeMoreThan16) {
			contractObj.setDateOfBirth(properties.getProperty("DOBMoreThan16"));
		} else {
			contractObj.setDateOfBirth(properties.getProperty("DOBLessThan16"));
		}

		Teams teamObj = new Teams(properties);
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.createTeamWithMember();
		Validations.assertThat().object(data).isNotNull().perform();
		teamName = data.get(2);
		teamObj.setTeamNameEn(teamName);

		homePageObj.clickLoginLink();
		boolean login = loginPageObj.login(clubOwnerAcc, clubOwnerPw);
		Validations.assertThat().object(login).isTrue().perform();
		ReportManager.log("club onwer/player logged in successfully");

		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.teamAction(teamObj.getTeamNameEn(), Constants.MANAGECONTRACTTEAM);
		manageContractsPageObj.clickOnUploadContractBtn(playerAccName);

		Validations.assertThat().object(uploadContractPageObj.uploadContractForMember(contractObj, memberAgeMoreThan16))
				.isTrue().perform();
		ReportManager.log("Contract uploaded for the member successfully");

		Validations.assertThat().object(manageContractsPageObj.getContractStatus(Constants.PENDINGMEMBERAPPROVAL))
				.isTrue().perform();
		ReportManager.log("Member contract status became pending for member approval");

		manageContractsPageObj.selectFromDropDownList(Constants.VIEWCONTRACT);

		ReportManager.log("check contract details after submission");
		Validations.assertThat().object(viewContractDetailsPageObj.checkContractDetails(contractObj, teamObj,
				memberAgeMoreThan16, pendingContractStatus, clubName, playerAcc, playerMobileNum, playerAccName))
				.isTrue().perform();

		dashBoardPageObj.logout();
		ReportManager.log("Club owner account loggedout");

		ReportManager.log("==============  End upload contracts for member test ==============");
	}

	@Test(dataProvider = "contractInvitation")
	public void testApproveAndRejectContractInvitation(String actionType) {
		ReportManager.log("============== End Approve and reject contract Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String clubName = properties.getProperty("clubNameEn");
		String playerAccMail = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String notificationMsg = properties.getProperty("contractInvitationNotification");

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.NOACTIONINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		teamName = data.get(1);
		notificationMsg = clubName + " " + notificationMsg;

		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(playerAccMail, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  player account");

		notificationPageObj.checkNotficationContentAndSelectIt(notificationMsg);
		ReportManager.log("Contract invitation sent to the player");

		invitationDetailsPageObj.acceptRejectInvitation(actionType);
		ReportManager.log("Contract invitation " + actionType);

		dashBoardPageObj.logout();
		ReportManager.log("Player account logout");

		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  club owner account");

		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.teamAction(teamName, Constants.MANAGECONTRACTTEAM);

		if (actionType.equals(Constants.ACCEPTINVITATION)) {
			Validations.assertThat().object(manageContractsPageObj.getContractStatus(Constants.CONTRACTSTATUSNEW))
					.isTrue().perform();
			ReportManager.log("Member contract status became new");
		} else {
			Validations.assertThat().object(manageContractsPageObj.getContractStatus(Constants.NOCONTRACTSUBMITTED))
					.isTrue().perform();
			ReportManager.log("Member contract status became no contract submitted again");
		}

		dashBoardPageObj.logout();
		ReportManager.log("club owner user logout");

		ReportManager.log("============== End Approve and reject contract Test   ==============");
	}

	@Test
	public void testContractRequestedByAdmin() {
		ReportManager.log("============== Start contract requested by admin Test   ==============");

		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		int currentReqCounter;
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String superAdmin = properties.getProperty("superAdmin");
		String superAdminPw = properties.getProperty("superAdminPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String playerAccMail = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String notificationMsg;

		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.createTeamWithMember();
		Validations.assertThat().object(data).isNotNull().perform();
		teamName = data.get(2);
		notificationMsg = properties.getProperty("contractReqNotificationP1") + " " + teamName + " "
				+ properties.getProperty("contractReqNotificationP2");

		ContractRequest contractObj = new ContractRequest(properties);

		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner account");

		currentReqCounter = dashBoardPageObj.getCurrentTeamCounter();
		ReportManager.log("Current teams request contract counter = " + currentReqCounter);

		dashBoardPageObj.logout();
		ReportManager.log("Club owner loggedout");

		homePageObj.clickLoginLink();

		Validations.assertThat().object(loginPageObj.login(superAdmin, superAdminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by super admin account");

		dashBoardPageObj.navigateToTeamsManagement();

		Validations.assertThat().object(teamsManagementListPageObj.requestContract(teamName)).isTrue().perform();
		ReportManager.log("Contract requested by admin");

		dashBoardPageObj.logout();
		ReportManager.log("Super admin loggedout");

		homePageObj.clickLoginLink();

		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner account");

		Validations.assertThat().object(dashBoardPageObj.checkTeamCounterAfterContractRequest(currentReqCounter))
				.isTrue().perform();
		ReportManager.log("Team request contract increased by one after request");

		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();

		Validations.assertThat().object(myTeamsListPageObj.checkContractReqStatus(teamName)).isTrue().perform();
		ReportManager.log("Contract requested label added to the team " + teamName);

		notificationPageObj.checkNotficationContentAndSelectIt(notificationMsg);
		ReportManager.log("Contract request notification sent to club owner successfully");

		Validations.assertThat().object(manageContractsPageObj.getContractStatus(Constants.CONTRACTREQUESTED)).isTrue()
				.perform();
		ReportManager.log("Member contract status became contract requested");

		manageContractsPageObj.clickOnUploadContractBtn(playerAccountName);

		Validations.assertThat().object(uploadContractPageObj.uploadContractForMember(contractObj, true)).isTrue()
				.perform();
		ReportManager.log("Contract uploaded for the member successfully");

		Validations.assertThat().object(manageContractsPageObj.getContractStatus(Constants.PENDINGMEMBERAPPROVAL))
				.isTrue().perform();
		ReportManager.log("Member contract status became pending member approval");

		// approve contract invitation from member
		String memberToken = loginServiceObj.login(playerAccMail, playerPw);
		InvitationService invitationService = new InvitationService(memberToken, apiObj);
		String invitationId = invitationService.getInvitationByTeam(teamName, Constants.RECEIVEDINVITATION);
		contractManagementObj.approveRejectContractByMember(Constants.ACCEPTINVITATION, invitationId);

		manageContractsPageObj.pressOnBackArrow();

		Validations.assertThat().object(myTeamsListPageObj.checkContractReqStatus(teamName)).isFalse().perform();
		ReportManager.log("Contract requested label removed from the team " + teamName);

		Validations.assertThat().object(dashBoardPageObj.checkTeamCounterAfterSubmitAllReq(currentReqCounter)).isTrue()
				.perform();
		ReportManager.log("Team contract requested number decreased by one");

		dashBoardPageObj.logout();
		ReportManager.log("Club owner loggedout");

		ReportManager.log("============== End contract requested by admin Test   ==============");
	}

	@Test(dataProvider = "testContractRequestApproveData")
	public void testContractRequestApprove(boolean contractExpired) {
		ReportManager.log("============== Start  Approve contract Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String requestId;
		String teamName;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String expectedStatus = Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS;
		String notificationMsg = properties.getProperty("ContractNotificationMsg");
		boolean requestApproved;

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, contractExpired);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		notificationMsg = notificationMsg + " " + requestId + " " + properties.getProperty("ApproveNotificationMsg");

		// Step2 SFE login to approve request
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToCRM();
		ReportManager.log("SEF admin start approve request ");
		requestApproved = crmListPageObj.approveContractRequest(requestId, playerAccountName, contractExpired);
		Validations.assertThat().object(requestApproved).isTrue().perform();
		ReportManager.log("Request approved successfully by SFE admin");
		ReportManager.log("Check if  contract status changed in Team managment List for SFE ");
		dashBoardPageObj.navigateToTeamsManagement();
		if (contractExpired)
			expectedStatus = Constants.CONTRACT_MANAGEMENT_EXPIRED_STATUS;
		Validations.assertThat().object(
				teamsManagementListPageObj.checkTeamMemberContractStatus(teamName, playerAccountName, expectedStatus))
				.isTrue().perform();
		ReportManager.log("Contract status changed in Team managment details for SFE ");
		dashBoardPageObj.logout();
		ReportManager.log("SFE admin log out ");

		// Step3 Login by Club owner to check contract status & notifications
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner");
		boolean notificatioDisplayed = notificationPageObj.checkNotficationContent(notificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Notification message displayed successfully");
		ReportManager.log("club owner navigate to team contracts to check on contract status");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.checkContractStatus(playerAccountName, expectedStatus))
				.isTrue().perform();
		ReportManager.log("Contract status updated successfully.");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		// Step 4 check notifications & email for club owner
		/*
		 * String approveSubject=properties.getProperty("ContractSubject")+ " "+
		 * requestId + properties.getProperty("ApprovedContractSubject"); boolean
		 * requestApprovedSent = fakeMailPageObj.checkOnEmailWithSubject(true,
		 * clubOwner.split("@")[0],approveSubject);
		 * Validations.assertThat().object(requestApprovedSent).isTrue().perform();
		 * ReportManager.log("Approved request email sent successfully to Club owner ");
		 */

		ReportManager.log("============== End  Approve contract Test   ==============");
	}

	@Test
	public void testContractRequestReject() {
		ReportManager.log("============== Start  Reject contract Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String requestId;
		String teamName;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String expectedStatus = Constants.CONTRACT_MANAGEMENT_NO_CONTRACT_STATUS;
		String notificationMsg = properties.getProperty("ContractNotificationMsg");
		boolean requestRejected;

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		notificationMsg = notificationMsg + " " + requestId + " "
				+ properties.getProperty("RejectContractNotificationMsg");

		// Step2 SFE login to Reject request
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToCRM();
		ReportManager.log("SEF admin start reject request ");
		requestRejected = crmListPageObj.rejectContractRequest(requestId, playerAccountName, expectedStatus);
		Validations.assertThat().object(requestRejected).isTrue().perform();
		ReportManager.log("Request rejected successfully by SFE admin");
		ReportManager.log("Check if  contract status changed in Team managment List for SFE ");
		dashBoardPageObj.navigateToTeamsManagement();
		Validations.assertThat().object(
				teamsManagementListPageObj.checkTeamMemberContractStatus(teamName, playerAccountName, expectedStatus))
				.isTrue().perform();
		ReportManager.log("Contract status changed in Team managment details for SFE ");
		dashBoardPageObj.logout();
		ReportManager.log("SFE admin log out ");

		// Step3 Login by Club owner to check contract status & notifications
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner");
		boolean notificatioDisplayed = notificationPageObj.checkNotficationContent(notificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Notification message displayed successfully");
		ReportManager.log("club owner navigate to team contracts to check on contract status");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.checkContractStatus(playerAccountName, expectedStatus))
				.isTrue().perform();
		ReportManager.log("Contract status updated successfully.");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		// Step 4 check notifications & email for club owner
		/*
		 * String rejectSubject=properties.getProperty("ContractSubject")+ " "+
		 * requestId + properties.getProperty("RejectedContractSubject"); boolean
		 * requestRejectedSent = fakeMailPageObj.checkOnEmailWithSubject(true,
		 * clubOwner.split("@")[0],rejectSubject);
		 * Validations.assertThat().object(requestRejectedSent).isTrue().perform();
		 * ReportManager.log("Rejected request email sent successfully to Club owner ");
		 */

		ReportManager.log("============== End  Reject contract Test   ==============");
	}

	@Test
	public void testContractRequestRequireAnAction() {
		ReportManager.log("============== Start  Require an action contract Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String requestId;
		String teamName;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String expectedStatus = Constants.CONTRACT_MANAGEMENT_REQUIRED_ACTION_STATUS_CLUB_OWNER;

		boolean requireAnActionSent;
		boolean reSubmitActionDone;

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		String notificationMsg = " " + requestId + " " + properties.getProperty("ActionRequiredNotificationMsg");

		// Step2 SFE login to Require An action on request
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToCRM();
		ReportManager.log("SEF admin start require an action on request ");
		requireAnActionSent = crmListPageObj.requireAnActionOnContractRequest(requestId, playerAccountName);
		Validations.assertThat().object(requireAnActionSent).isTrue().perform();
		ReportManager.log("SEF admin require an action successfully.");
		ReportManager.log("Check if  contract status changed in Team managment List for SFE ");
		dashBoardPageObj.navigateToTeamsManagement();
		Validations.assertThat().object(
				teamsManagementListPageObj.checkTeamMemberContractStatus(teamName, playerAccountName, expectedStatus))
				.isTrue().perform();
		ReportManager.log("Contract status changed in Team managment details for SFE ");
		dashBoardPageObj.logout();
		ReportManager.log("SFE admin log out ");

		// Step3 Login by Club owner to check contract status & notifications
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner");
		boolean notificatioDisplayed = notificationPageObj.checkNotficationContent(notificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Notification message displayed successfully");
		ReportManager.log("club owner navigate to team contracts to check on contract status");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.checkContractStatus(playerAccountName,
				Constants.CONTRACT_MANAGEMENT_REQUIRED_ACTION_STATUS_CLUB_OWNER)).isTrue().perform();
		ReportManager.log("Contract status updated successfully.");

		ReportManager.log("Club owner navigate to CRM to resubmit the contract");
		dashBoardPageObj.navigateToCRM();
		ContractRequest contractRequestObj = new ContractRequest(properties);
		ReportManager.log("club owner  user log out ");
		reSubmitActionDone = crmListPageObj.reSubmitContractRequest(requestId, playerAccountName, contractRequestObj);
		Validations.assertThat().object(reSubmitActionDone).isTrue().perform();
		ReportManager.log("Club owner resubmit contract successfully.");
		ReportManager.log("club owner navigate to team contracts to check on contract status");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.checkContractStatus(playerAccountName,
				Constants.CONTRACT_MANAGEMENT_ACTION_TAKEN_STATUS_CLUB_OWNER)).isTrue().perform();
		ReportManager.log("Contract status updated successfully.");
		dashBoardPageObj.logout();

		ReportManager.log("============== End  Require an action contract Test   ==============");
	}

	@Test()
	public void testCancelContractInvitation() {
		ReportManager.log("============== Start  cancel contract invitation Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String clubName = properties.getProperty("clubName");
		boolean invitationStatus;
		String expectedInvitationStatus = Constants.INVITATIONCANCELLED;

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.NOACTIONINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		teamName = data.get(1);

		// Step2 Login by Club owner to cancelInvitation
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts to cancel invitation");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.cancelContractInvitationRequest(playerAccountName,
				Constants.CONTRACT_MANAGEMENT_CANCEL_INVITATION)).isTrue().perform();
		ReportManager.log("Contract status updated successfully and invitation cancelled.");
		dashBoardPageObj.navigateToInvitations();
		invitationStatus = invitationListPageObj.openCheckInvitationStatus(Constants.SENTINVITATION, null,
				playerAccountName, expectedInvitationStatus);
		Validations.assertThat().object(invitationStatus).isTrue().perform();
		ReportManager.log("Invitation Status changed in list and detail page");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		// Step3 Login by player account to check on invitation status
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		dashBoardPageObj.navigateToInvitations();
		invitationStatus = invitationListPageObj.openCheckInvitationStatus(Constants.RECEIVEDINVITATION, null, clubName,
				expectedInvitationStatus);
		Validations.assertThat().object(invitationStatus).isTrue().perform();
		ReportManager.log("Invitation Status changed in list and detail page");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		ReportManager.log("============== End  cancel contract  Inviation Test   ==============");
	}

	@Test()
	public void testCancelContractRequest() {
		ReportManager.log("============== Start  cancel contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerAccountName = properties.getProperty("playerAccName");

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);

		// Step2 Login by Club owner to cancel Request
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts to cancel invitation");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.cancelContractInvitationRequest(playerAccountName,
				Constants.CONTRACT_MANAGEMENT_CANCEL_CONTRACT)).isTrue().perform();
		ReportManager.log("Contract status updated successfully and contract cancelled.");
		// Step3 Check on CRM
		dashBoardPageObj.navigateToCRM();
		Validations.assertThat().object(
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS))
				.isTrue().perform();
		ReportManager.log("Parent Request with status completed");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_CANCELLED_STATUS)).isTrue().perform();
		ReportManager.log(" Request Cancelled successfully");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		ReportManager.log("============== End  cancel contract  Request Test   ==============");
	}

	@Test
	public void testClosedTransferWindow() {
		ReportManager.log("============== Start closed transfer window Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;

		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String superAdmin = properties.getProperty("superAdmin");
		String superAdminPw = properties.getProperty("superAdminPW");
		String playerAccountName = properties.getProperty("playerAccName");

		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.createTeamWithMember();
		Validations.assertThat().object(data).isNotNull().perform();
		teamName = data.get(2);
		homePageObj.clickLoginLink();

		/*
		 * Validations.assertThat().object(loginPageObj.login(superAdmin,
		 * superAdminPw)).isTrue().perform();
		 * ReportManager.log("Login done  successfully by super admin account");
		 * 
		 * dashBoardPageObj.naviagteToSettings();
		 * 
		 * Validations.assertThat().object(transferWindowPageObj.editWindowTime()).
		 * isTrue().perform();
		 */

		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by club owner account");

		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.teamAction(teamName, Constants.MANAGECONTRACTTEAM);

		manageContractsPageObj.clickOnUploadContractBtn(playerAccountName);

		Validations.assertThat().object(manageContractsPageObj.getClosedWindowMsg()).isTrue().perform();
		ReportManager.log("Transfer window error message checked successfully");

		dashBoardPageObj.logout();
		ReportManager.log("Club owner loggedout");

		ReportManager.log("============== End closed transfer window Test   ==============");

	}

	@Test()
	public void testRemoveContractPendingInvitation() {
		ReportManager
				.log("============== Start  Remove memeber with pending contract invitation Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String clubOwnerNotificationMsg;
		String playerNotificationMsg;
		boolean notificatioDisplayed;
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String clubName = properties.getProperty("clubName");
		boolean invitationStatus;
		String expectedInvitationStatus = Constants.INVITATIONCANCELLED;

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.NOACTIONINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		teamName = data.get(1);

		// Step2 Login by Club owner to remove member
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts to cancel invitation");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.teamAction(teamName, Constants.EDITTEAM);
		Validations.assertThat().object(addTeamPageObj.removeContractMemberByAccountName(playerAccountName)).isTrue()
				.perform();
		ReportManager.log(playerAccountName + " member removed successfully");

		// Step3 check if player removed from contracts & removal notification appeared
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.checkIfMemberExist(playerAccountName)).isFalse().perform();
		ReportManager.log("Player removed from contract");
		clubOwnerNotificationMsg = properties.getProperty("clubOwnerRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(clubOwnerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal club owner Notification message displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		// Step3 Login by player account to check on invitation status & notification
		// msg
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		playerNotificationMsg = properties.getProperty("memberRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(playerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal player Notification message displayed successfully");
		dashBoardPageObj.navigateToInvitations();
		invitationStatus = invitationListPageObj.openCheckInvitationStatus(Constants.RECEIVEDINVITATION, teamName,
				clubName, expectedInvitationStatus);
		Validations.assertThat().object(invitationStatus).isTrue().perform();
		ReportManager.log("Invitation Status changed in list and detail page");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		ReportManager.log("==============End  Remove memeber with pending contract invitation Test  ==============");
	}

	@Test()
	public void testRemoveContractPendingRequest() {
		ReportManager.log(
				"============== Start  Remove member By Club owner with pending contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String clubOwnerNotificationMsg;
		String playerNotificationMsg;
		boolean notificatioDisplayed;
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String playerAccountName = properties.getProperty("playerAccName");

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);

		// Step2 Login by Club owner to remove member
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts to cancel invitation");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.teamAction(teamName, Constants.EDITTEAM);
		Validations.assertThat().object(addTeamPageObj.removeContractMemberByAccountName(playerAccountName)).isTrue()
				.perform();
		ReportManager.log(playerAccountName + " member removed successfully");

		// Step3 check if member removed & notification appeared
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.checkIfMemberExist(playerAccountName)).isFalse().perform();
		ReportManager.log("Player removed from contract");
		clubOwnerNotificationMsg = properties.getProperty("clubOwnerRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(clubOwnerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal club owner Notification message displayed successfully");
		// Step4 Check on CRM
		dashBoardPageObj.navigateToCRM();
		Validations.assertThat().object(
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS))
				.isTrue().perform();
		ReportManager.log("Parent Request with status completed");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_CANCELLED_STATUS)).isTrue().perform();
		ReportManager.log(" Request Cancelled successfully");
		Validations.assertThat().object(crmListPageObj.checkNoContractRequests(Constants.CONTRACT_TEAM_MEMBERS_TAB))
				.isTrue().perform();
		ReportManager.log(" New Request not displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		// Step5 Login by player account to check on invitation status & notification
		// msg
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		playerNotificationMsg = properties.getProperty("memberRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(playerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal player Notification message displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		ReportManager.log(
				"============== End  Remove member By Club owner with pending contract Request Test   ==============");
	}

	@Test(dataProvider = "testRemoveActiveRequestData")
	public void testRemoveActiveContractRequest(String actionType) {
		ReportManager.log(
				"============== Start  Remove member By Club owner with Active contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String clubOwnerNotificationMsg;
		String playerNotificationRequestMsg;
		String invitationTitle;
		boolean notificationDisplayed;
		boolean invitationStatusUpdated;
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String playerAccountName = properties.getProperty("playerAccName");

		// Step1 Prepare Active contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(requestId);
		Validations.assertThat().object(contractApproved).isTrue().perform();

		// Step2 Login by Club owner to remove member
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts to cancel invitation");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.teamAction(teamName, Constants.EDITTEAM);
		Validations.assertThat().object(addTeamPageObj.removeContractMemberByAccountName(playerAccountName)).isTrue()
				.perform();
		ReportManager.log(" Invitation sent to member to be  removed ");
		dashBoardPageObj.logout();

		// Step 3 login by player and accept /reject invitation and check notification
		// and logout
		invitationTitle = properties.getProperty("memberRemoveNotificationRequest") + " " + teamName.substring(0, 11);
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		playerNotificationRequestMsg = properties.getProperty("clubNameEn") + " "
				+ properties.getProperty("memberRemoveNotificationRequest") + " " + teamName;
		notificationDisplayed = notificationPageObj.checkNotficationContent(playerNotificationRequestMsg);
		Validations.assertThat().object(notificationDisplayed).isTrue().perform();
		ReportManager.log("The Removal player Notification  Request message displayed successfully");
		dashBoardPageObj.navigateToInvitations();
		if (actionType.equals(Constants.ACCEPT_REMOVE_INVITATION))
			invitationStatusUpdated = invitationListPageObj.acceptRejectInvitationByStatus(
					Constants.ACCEPT_REMOVE_INVITATION, Constants.INVITATIONPENDING, invitationTitle).get(2);
		else
			invitationStatusUpdated = invitationListPageObj.acceptRejectInvitationByStatus(
					Constants.REJECT_REMOVE_INVITATION, Constants.INVITATIONPENDING, invitationTitle).get(2);
		Validations.assertThat().object(invitationStatusUpdated).isTrue().perform();
		ReportManager.log("The invitation status in list is changed to accepted/rejected after click on Accept/Reject");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		// Step 4 login by club owner to check on memeber removed in case of acceptance
		// and check on CRM & notfication
		if (actionType.equals(Constants.ACCEPT_REMOVE_INVITATION))
			clubOwnerNotificationMsg = properties.getProperty("clubOwnerAgreedRemoveNotification") + " " + teamName;
		else
			clubOwnerNotificationMsg = properties.getProperty("clubOwnerRejectedRemoveNotification") + " " + teamName;
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts to cancel invitation");
		notificationDisplayed = notificationPageObj.checkNotficationContent(clubOwnerNotificationMsg);
		Validations.assertThat().object(notificationDisplayed).isTrue().perform();
		ReportManager.log("The Removal club owner Notification message displayed successfully");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		if (actionType.equals(Constants.ACCEPT_REMOVE_INVITATION)) {
			Validations.assertThat().object(teamContractsPageObj.checkIfMemberExist(playerAccountName)).isFalse()
					.perform();
			ReportManager.log("Player removed from contract");
		} else {
			Validations.assertThat().object(teamContractsPageObj.checkIfMemberExist(playerAccountName)).isTrue()
					.perform();
			ReportManager.log("Player  Not removed from contract");
		}

		// Step4 Check on CRM
		dashBoardPageObj.navigateToCRM();
		Validations.assertThat().object(
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS))
				.isTrue().perform();
		ReportManager.log("Parent Request with status completed");
		if (actionType.equals(Constants.ACCEPT_REMOVE_INVITATION)) {
			Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
					requestId, Constants.CONTRACT_MANAGEMENT_APPROVED_STATUS)).isTrue().perform();
			ReportManager.log(" Request Approved successfully in past requests tab");
			Validations.assertThat().object(crmListPageObj.checkNoContractRequests(Constants.CONTRACT_TEAM_MEMBERS_TAB))
					.isTrue().perform();
			ReportManager.log(" New Request not displayed successfully");
		} else {
			Validations.assertThat()
					.object(crmListPageObj.checkNoContractRequests(Constants.CONTRACT_PAST_REQUESTS_TAB)).isTrue()
					.perform();
			ReportManager.log("No Requests in past requests tab");
			Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_TEAM_MEMBERS_TAB,
					requestId, Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS)).isTrue().perform();
			ReportManager.log(" Active Request still displayed successfully in Team Requests Tab");
		}

		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		ReportManager.log(
				"============== End  Remove member By Club owner with active contract Request Test   ==============");
	}

	@Test()
	public void testRemoveContractPendingInvitationSFE() {
		ReportManager.log(
				"============== Start  Remove memeber By SFE with pending contract invitation Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String clubOwnerNotificationMsg;
		String playerNotificationMsg;
		boolean notificatioDisplayed;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String clubName = properties.getProperty("clubName");
		boolean invitationStatus;
		String expectedInvitationStatus = Constants.INVITATIONCANCELLED;

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.NOACTIONINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		teamName = data.get(1);

		// Step2 Login by SFE admin to remove member
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToTeamsManagement();
		Validations.assertThat().object(teamsManagementListPageObj.removeTeamMember(teamName, playerAccountName))
				.isTrue().perform();
		ReportManager.log("Team member Removed succesffully ");
		dashBoardPageObj.logout();
		ReportManager.log("SFE admin  log out ");

		// Step 3 Login by club owner to check if player removed from contracts &
		// removal notification appeared
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts to cancel invitation");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.checkIfMemberExist(playerAccountName)).isFalse().perform();
		ReportManager.log("Player removed from contract");
		clubOwnerNotificationMsg = properties.getProperty("clubOwnerRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(clubOwnerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal club owner Notification message displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		// Step3 Login by player account to check on invitation status & notification
		// msg
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		playerNotificationMsg = properties.getProperty("memberRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(playerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal player Notification message displayed successfully");
		dashBoardPageObj.navigateToInvitations();
		invitationStatus = invitationListPageObj.openCheckInvitationStatus(Constants.RECEIVEDINVITATION, teamName,
				clubName, expectedInvitationStatus);
		Validations.assertThat().object(invitationStatus).isTrue().perform();
		ReportManager.log("Invitation Status changed in list and detail page");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		ReportManager.log(
				"============== End  Remove memeber By SFE with pending contract invitation Test   ==============");
	}

	@Test()
	public void testRemoveContractPendingRequestSFE() {
		ReportManager.log(
				"============== Start  Remove member By SFE admin with pending contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String clubOwnerNotificationMsg;
		String playerNotificationMsg;
		boolean notificatioDisplayed;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String playerAccountName = properties.getProperty("playerAccName");

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);

		// Step2 Login by SFE admin to remove member
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToTeamsManagement();
		Validations.assertThat().object(teamsManagementListPageObj.removeTeamMember(teamName, playerAccountName))
				.isTrue().perform();
		ReportManager.log("Team member Removed succesffully ");
		dashBoardPageObj.logout();
		ReportManager.log("SFE admin  log out ");

		// Step2 Login by Club owner to check if member removed & notification appeared

		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.checkIfMemberExist(playerAccountName)).isFalse().perform();
		ReportManager.log("Player removed from contract");
		clubOwnerNotificationMsg = properties.getProperty("clubOwnerRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(clubOwnerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal club owner Notification message displayed successfully");
		// Step3 Check on CRM
		dashBoardPageObj.navigateToCRM();
		Validations.assertThat().object(
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS))
				.isTrue().perform();
		ReportManager.log("Parent Request with status completed");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_REJECTED_STATUS)).isTrue().perform();
		ReportManager.log(" Request Rejected successfully");
		Validations.assertThat().object(crmListPageObj.checkNoContractRequests(Constants.CONTRACT_TEAM_MEMBERS_TAB))
				.isTrue().perform();
		ReportManager.log(" New Request not displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		// Step4 Login by player account to check on invitation status & notification
		// msg
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		playerNotificationMsg = properties.getProperty("memberRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(playerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal player Notification message displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		ReportManager.log(
				"============== End  Remove member By Club owner with pending contract Request Test   ==============");
	}

	@Test()
	public void testRemoveActiveContractRequestSFE() {
		ReportManager.log(
				"============== Start  Remove member By SFE admin with Active contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String clubOwnerNotificationMsg;
		String playerNotificationMsg;
		boolean notificatioDisplayed;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String playerAccountName = properties.getProperty("playerAccName");

		// Step1 Prepare Active contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(requestId);
		Validations.assertThat().object(contractApproved).isTrue().perform();

		// Step2 Login by SFE admin to remove member
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToTeamsManagement();
		Validations.assertThat().object(teamsManagementListPageObj.removeTeamMember(teamName, playerAccountName))
				.isTrue().perform();
		ReportManager.log("Team member Removed succesffully ");
		dashBoardPageObj.logout();
		ReportManager.log("SFE admin  log out ");

		// Step2 Login by Club owner to check if member removed & notification appeared

		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.checkIfMemberExist(playerAccountName)).isFalse().perform();
		ReportManager.log("Player removed from contract");
		clubOwnerNotificationMsg = properties.getProperty("clubOwnerRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(clubOwnerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal club owner Notification message displayed successfully");
		// Step3 Check on CRM
		dashBoardPageObj.navigateToCRM();
		Validations.assertThat().object(
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS))
				.isTrue().perform();
		ReportManager.log("Parent Request with status completed");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_APPROVED_STATUS)).isTrue().perform();
		ReportManager.log(" Request Approved successfully");
		Validations.assertThat().object(crmListPageObj.checkNoContractRequests(Constants.CONTRACT_TEAM_MEMBERS_TAB))
				.isTrue().perform();
		ReportManager.log(" No Requests in Team members Tab");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		// Step4 Login by player account to check on invitation status & notification
		// msg
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		playerNotificationMsg = properties.getProperty("memberRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(playerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal player Notification message displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		ReportManager.log(
				"============== End  Remove member By SFE admin with Active contract Request Test   ==============");
	}

	@Test()
	public void testArchiveTeamWithPendingInvitation() {
		ReportManager.log("============== Start  archive team with pending contract invitation Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String playerNotificationMsg;
		boolean notificatioDisplayed;
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String clubName = properties.getProperty("clubName");
		boolean invitationStatus;
		String expectedInvitationStatus = Constants.INVITATIONCANCELLED;

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.NOACTIONINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		teamName = data.get(1);

		// Step2 Login by Club owner to archive team
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts to cancel invitation");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		Validations.assertThat().object(myTeamsListPageObj.archiveTeamWithContracts(teamName)).isTrue().perform();
		teamsDashboardPageObj.selectArchivedTeamTab();
		Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamName)).isTrue().perform();
		ReportManager.log(teamName + " moved to archived list");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		// Step3 Login by player account to check on invitation status & notification
		// msg
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		playerNotificationMsg = properties.getProperty("memberRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(playerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal player Notification message displayed successfully");
		dashBoardPageObj.navigateToInvitations();
		invitationStatus = invitationListPageObj.openCheckInvitationStatus(Constants.RECEIVEDINVITATION, teamName,
				clubName, expectedInvitationStatus);
		Validations.assertThat().object(invitationStatus).isTrue().perform();
		ReportManager.log("Invitation Status changed in list and detail page");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		ReportManager.log("============== End archive team with pending contract invitation Test   ==============");
	}

	@Test()
	public void testArciveTeamWithPendingRequest() {
		ReportManager.log(
				"============== Start Archive Team By Club owner with pending contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String playerNotificationMsg;
		boolean notificatioDisplayed;
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);

		// Step2 Login by Club owner to archive team
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts to cancel invitation");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		Validations.assertThat().object(myTeamsListPageObj.archiveTeamWithContracts(teamName)).isTrue().perform();
		teamsDashboardPageObj.selectArchivedTeamTab();
		Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamName)).isTrue().perform();
		ReportManager.log(teamName + " moved to archived list");

		// Step4 Check on CRM
		dashBoardPageObj.navigateToCRM();
		Validations.assertThat().object(
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS))
				.isTrue().perform();
		ReportManager.log("Parent Request with status completed");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_CANCELLED_STATUS)).isTrue().perform();
		ReportManager.log(" Request Cancelled successfully");
		Validations.assertThat().object(crmListPageObj.checkNoContractRequests(Constants.CONTRACT_TEAM_MEMBERS_TAB))
				.isTrue().perform();
		ReportManager.log(" New Request not displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		// Step5 Login by player account to check on notification
		// msg
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		playerNotificationMsg = properties.getProperty("memberRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(playerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal player Notification message displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		ReportManager.log(
				"============== End Archive Team By Club owner with pending contract Request Test   ==============");
	}
	
	@Test(dataProvider = "testArchiveTeamWithActiveRequestData")
	public void testArchiveTeamWithActiveRequest(String actionType) {
		ReportManager.log(
				"============== Start  Archive Team By Club owner with Active contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String clubOwnerNotificationMsg;
		String playerNotificationRequestMsg;
		String invitationTitle;
		boolean notificationDisplayed;
		boolean invitationStatusUpdated;
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String playerAccountName = properties.getProperty("playerAccName");

		// Step1 Prepare Active contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(requestId);
		Validations.assertThat().object(contractApproved).isTrue().perform();

		// Step2 Login by Club owner to archive team
				homePageObj.clickLoginLink();
				Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
				ReportManager.log("club owner navigate to team contracts to cancel invitation");
				dashBoardPageObj.navigateToTeams();
				teamsDashboardPageObj.selectMyTeamTab();
				Validations.assertThat().object(myTeamsListPageObj.archiveTeamWithContracts(teamName)).isTrue().perform();
				ReportManager.log(" Invitation sent to member to be  removed ");
		dashBoardPageObj.logout();

		// Step 3 login by player and accept /reject invitation and check notification
		// and logout
		invitationTitle = properties.getProperty("memberRemoveNotificationRequest") + " " + teamName.substring(0, 11);
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		playerNotificationRequestMsg = properties.getProperty("clubNameEn") + " "
				+ properties.getProperty("memberRemoveNotificationRequest") + " " + teamName;
		notificationDisplayed = notificationPageObj.checkNotficationContent(playerNotificationRequestMsg);
		Validations.assertThat().object(notificationDisplayed).isTrue().perform();
		ReportManager.log("The Removal player Notification  Request message displayed successfully");
		dashBoardPageObj.navigateToInvitations();
		if (actionType.equals(Constants.ACCEPT_REMOVE_INVITATION))
			invitationStatusUpdated = invitationListPageObj.acceptRejectInvitationByStatus(
					Constants.ACCEPT_REMOVE_INVITATION, Constants.INVITATIONPENDING, invitationTitle).get(2);
		else
			invitationStatusUpdated = invitationListPageObj.acceptRejectInvitationByStatus(
					Constants.REJECT_REMOVE_INVITATION, Constants.INVITATIONPENDING, invitationTitle).get(2);
		Validations.assertThat().object(invitationStatusUpdated).isTrue().perform();
		ReportManager.log("The invitation status in list is changed to accepted/rejected after click on Accept/Reject");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		// Step 4 login by club owner to check on memeber removed in case of acceptance
		// and check on CRM & notfication
		if (actionType.equals(Constants.ACCEPT_REMOVE_INVITATION))
			clubOwnerNotificationMsg = properties.getProperty("clubOwnerAgreedRemoveNotification") + " " + teamName;
		else
			clubOwnerNotificationMsg = properties.getProperty("clubOwnerRejectedRemoveNotification") + " " + teamName;
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts to cancel invitation");
		notificationDisplayed = notificationPageObj.checkNotficationContent(clubOwnerNotificationMsg);
		Validations.assertThat().object(notificationDisplayed).isTrue().perform();
		ReportManager.log("The Removal club owner Notification message displayed successfully");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		if (actionType.equals(Constants.ACCEPT_REMOVE_INVITATION)) {
			Validations.assertThat().object(teamContractsPageObj.checkIfMemberExist(playerAccountName)).isFalse()
					.perform();
			ReportManager.log("Player removed from contract");
		} else {
			Validations.assertThat().object(teamContractsPageObj.checkIfMemberExist(playerAccountName)).isTrue()
					.perform();
			ReportManager.log("Player  Not removed from contract");
		}

		// Step4 Check on CRM
		dashBoardPageObj.navigateToCRM();
		Validations.assertThat().object(
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS))
				.isTrue().perform();
		ReportManager.log("Parent Request with status completed");
		if (actionType.equals(Constants.ACCEPT_REMOVE_INVITATION)) {
			Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
					requestId, Constants.CONTRACT_MANAGEMENT_APPROVED_STATUS)).isTrue().perform();
			ReportManager.log(" Request Approved successfully in past requests tab");
			Validations.assertThat().object(crmListPageObj.checkNoContractRequests(Constants.CONTRACT_TEAM_MEMBERS_TAB))
					.isTrue().perform();
			ReportManager.log(" New Request not displayed successfully");
		} else {
			Validations.assertThat()
					.object(crmListPageObj.checkNoContractRequests(Constants.CONTRACT_PAST_REQUESTS_TAB)).isTrue()
					.perform();
			ReportManager.log("No Requests in past requests tab");
			Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_TEAM_MEMBERS_TAB,
					requestId, Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS)).isTrue().perform();
			ReportManager.log(" Active Request still displayed successfully in Team Requests Tab");
		}
		
		//Step 5 clubowner can archive team
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		Validations.assertThat().object(myTeamsListPageObj.archiveTeamWithContracts(teamName)).isTrue().perform();
		
		if (actionType.equals(Constants.ACCEPT_REMOVE_INVITATION)) {
		teamsDashboardPageObj.selectArchivedTeamTab();
		Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamName)).isTrue().perform();
		ReportManager.log(teamName + " moved to archived list");
		}
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		ReportManager.log(
				"==============End Archive Team By Club owner with Active contract Request Test   ==============");
	}


	@Test()
	public void testArchiveTeamWithPendingInvitationSFE() {
		ReportManager.log(
				"============== Start  Archive Team By SFE with pending contract invitation Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String clubOwnerNotificationMsg;
		String playerNotificationMsg;
		boolean notificatioDisplayed;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String clubName = properties.getProperty("clubName");
		boolean invitationStatus;
		String expectedInvitationStatus = Constants.INVITATIONCANCELLED;

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.NOACTIONINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		teamName = data.get(1);

		// Step2 Login by SFE admin to Archive Team
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToTeamsManagement();
		Validations.assertThat().object(teamsManagementListPageObj.archiveTeam(teamName, false)).isTrue().perform();
		ReportManager.log("Team   archived  successfully.");
		dashBoardPageObj.logout();
		ReportManager.log("SFE admin  log out ");

		// Step2 Login by Club owner to check if team archived & notification appeared
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to teams ");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectArchivedTeamTab();
		Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamName)).isTrue().perform();
		ReportManager.log(teamName + " moved to archived list");
		clubOwnerNotificationMsg = teamName + " " + properties.getProperty("archiveNotificationMsg");
		notificatioDisplayed = notificationPageObj.checkNotficationContent(clubOwnerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Archive Notification message displayed successfully");
		dashBoardPageObj.logout();

		// Step3 Login by player account to check on invitation status & notification
		// msg
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		playerNotificationMsg = properties.getProperty("memberRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(playerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal player Notification message displayed successfully");
		dashBoardPageObj.navigateToInvitations();
		invitationStatus = invitationListPageObj.openCheckInvitationStatus(Constants.RECEIVEDINVITATION, teamName,
				clubName, expectedInvitationStatus);
		Validations.assertThat().object(invitationStatus).isTrue().perform();
		ReportManager.log("Invitation Status changed in list and detail page");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		ReportManager
				.log("============== End  Archive Team By SFE with pending contract invitation Test  ==============");
	}

	@Test()
	public void testArchiveTeamWithPendingRequestSFE() {
		ReportManager.log(
				"============== Start  Archive team By SFE admin with pending contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String clubOwnerNotificationMsg;
		String playerNotificationMsg;
		boolean notificatioDisplayed;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String player = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);

		// Step2 Login by SFE admin to Archive Team
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToTeamsManagement();
		Validations.assertThat().object(teamsManagementListPageObj.archiveTeam(teamName, false)).isTrue().perform();
		ReportManager.log("Team   archived successfully.");
		dashBoardPageObj.logout();
		ReportManager.log("SFE admin  log out ");

		// Step2 Login by Club owner to check if team archived & notification appeared
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectArchivedTeamTab();
		Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamName)).isTrue().perform();
		ReportManager.log(teamName + " moved to archived list");
		clubOwnerNotificationMsg = teamName + " " + properties.getProperty("archiveNotificationMsg");
		notificatioDisplayed = notificationPageObj.checkNotficationContent(clubOwnerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Archive Notification message displayed successfully");
		// Step3 Check on CRM
		dashBoardPageObj.navigateToCRM();
		Validations.assertThat().object(
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS))
				.isTrue().perform();
		ReportManager.log("Parent Request with status completed");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_REJECTED_STATUS)).isTrue().perform();
		ReportManager.log(" Request Rejected successfully");
		Validations.assertThat().object(crmListPageObj.checkNoContractRequests(Constants.CONTRACT_TEAM_MEMBERS_TAB))
				.isTrue().perform();
		ReportManager.log(" New Request not displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		// Step4 Login by player account to check on notification
		// msg
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(player, playerPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  Player ");
		playerNotificationMsg = properties.getProperty("memberRemoveNotification") + " " + teamName;
		notificatioDisplayed = notificationPageObj.checkNotficationContent(playerNotificationMsg);
		Validations.assertThat().object(notificatioDisplayed).isTrue().perform();
		ReportManager.log("The Removal player Notification message displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("Player  user log out ");

		ReportManager.log(
				"============== End Archive team By SFE admin with pending contract Request Test   ==============");
	}

	@Test()
	public void testArchiveTeamWithContractSFE() {
		ReportManager.log(
				"============== Start  Archive Team By SFE admin with Active contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerAccountName = properties.getProperty("playerAccName");

		// Step1 Prepare Active contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(requestId);
		Validations.assertThat().object(contractApproved).isTrue().perform();

		// Step2 Login by SFE admin to Archive Team
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToTeamsManagement();
		Validations.assertThat().object(teamsManagementListPageObj.archiveTeam(teamName, true)).isTrue().perform();
		ReportManager.log("Team  with contracts couldn't be archived .");
		dashBoardPageObj.logout();
		ReportManager.log("SFE admin  log out ");

		// Step2 Login by Club owner to check if member and team still exists

		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(clubOwner, clubOwnerPw)).isTrue().perform();
		ReportManager.log("club owner navigate to team contracts");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.openTeamContracts(teamName);
		Validations.assertThat().object(teamContractsPageObj.checkIfMemberExist(playerAccountName)).isTrue().perform();
		ReportManager.log("Team still exists and Player  not removed from contract ");
		// Step3 Check on CRM
		dashBoardPageObj.navigateToCRM();
		Validations.assertThat().object(
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS))
				.isTrue().perform();
		ReportManager.log("Parent Request with status completed");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_TEAM_MEMBERS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS)).isTrue().perform();
		ReportManager.log(" Request Still Active ");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		ReportManager
				.log("==============End  Archive Team By SFE admin with Active contract Request Test   ==============");
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
