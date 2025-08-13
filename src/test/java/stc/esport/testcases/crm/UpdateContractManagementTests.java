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
import stc.esport.pages.Teammanagement.TeamsManagementListPage;
import stc.esport.pages.contracts.ManageContractsPage;
import stc.esport.pages.contracts.UploadContractPage;
import stc.esport.pages.crm.CRMListPage;
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

public class UpdateContractManagementTests extends TestBase {
	// Variables
	LoginPage loginPageObj;
	HomePage homePageObj;
	DashBoardPage dashBoardPageObj;
	CRMListPage crmListPageObj;
	fakeMailPage fakeMailPageObj;
	TeamsManagementListPage teamsManagementListPageObj;
	TeamsDashboardPage teamsDashboardPageObj;
	MyTeamsListPage myTeamsListPageObj;
	TeamContractsPage teamContractsPageObj;
	NotificationPage notificationPageObj;
	LoginService loginServiceObj;
	UploadContractPage uploadContractPageObj;
	ManageContractsPage manageContractsPageObj;
	InvitationsListPage invitationListPageObj;
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
		teamsDashboardPageObj = new TeamsDashboardPage(driver, properties);
		uploadContractPageObj = new UploadContractPage(driver, properties);
		manageContractsPageObj = new ManageContractsPage(driver, properties);
		invitationListPageObj = new InvitationsListPage(driver, properties);
		addTeamPageObj = new AddTeamPage(driver, properties);
	}

	@BeforeTest
	public void BeforeTest() {
		ReportManager.log("BeforeTest");
	}

	@DataProvider(name = "testRemovePendingUpdateRequestData")
	public Object[][] testRemovePendingUpdateRequestData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"),
				"testRemoveContractPendingUpdateRequest");
		return data;
	}

	@DataProvider(name = "testArchivePendingUpdateRequestData")
	public Object[][] testArchivePendingUpdateRequestData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"), "testArchivePendingUpdateRequest");
		return data;
	}

	@DataProvider(name = "testArchiveWithUpdatedContractSFEData")
	public Object[][] testArchiveWithUpdatedContractSFEData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"),
				"testArchiveTeamWithUpdatedContractSFE");
		return data;
	}

	@Test
	public void testUpdateContract() {
		ReportManager.log("==============  Start Update contracts for member test ==============");
		ArrayList<String> data = new ArrayList<String>();
		String requestId;
		String teamName;
		boolean login;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String player = properties.getProperty("playerAcc");
		String playerPW = properties.getProperty("playerPW");
		String updateInvitationTitle = properties.getProperty("UpdatePlayerInvitationTitle");
		ContractRequest contractObj = new ContractRequest(properties);
		contractObj.setStatus(properties.getProperty("memberUpdateStatus"));
		contractObj.setSalary(properties.getProperty("Salary"));
		contractObj.setSaudiEleague("No");
		contractObj.setUploadContract(properties.getProperty("uploadContractUpdated"));

		boolean invitationAccepted;
		// Step 1 create contract approved by admin
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		boolean contractApproved = contractManagementObj.approveContractByAdmin(requestId);
		Validations.assertThat().object(contractApproved).isTrue().perform();

		contractObj.setRequestId(requestId);

		// Step 2 club owner update contract
		homePageObj.clickLoginLink();
		login = loginPageObj.login(clubOwner, clubOwnerPw);
		Validations.assertThat().object(login).isTrue().perform();
		ReportManager.log("club onwer/player logged in successfully");
		dashBoardPageObj.navigateToTeams();
		teamsDashboardPageObj.selectMyTeamTab();
		myTeamsListPageObj.teamAction(teamName, Constants.MANAGECONTRACTTEAM);
		manageContractsPageObj.clickOnUploadContractBtn(playerAccountName);
		Validations.assertThat().object(uploadContractPageObj.updateContract(contractObj)).isTrue().perform();
		ReportManager.log("Contract updated for the member successfully");
		dashBoardPageObj.navigateToTeams();
		dashBoardPageObj.logout();
		ReportManager.log("club owner log out ");

		// Step3 player accept update invitation
		homePageObj.clickLoginLink();
		login = loginPageObj.login(player, playerPW);
		Validations.assertThat().object(login).isTrue().perform();
		ReportManager.log("player logged in successfully");
		dashBoardPageObj.navigateToInvitations();
		invitationAccepted = invitationListPageObj.acceptRejectInvitationByStatus(Constants.ACCEPTINVITATION,
				Constants.INVITATIONPENDING, updateInvitationTitle).get(0);
		Validations.assertThat().object(invitationAccepted).isTrue().perform();
		ReportManager.log("Contract updated for the member successfully");
		dashBoardPageObj.logout();
		ReportManager.log("player log out ");

		// Step4 SFE Admin to check on Contract request status and contract list status
		homePageObj.clickLoginLink();
		login = loginPageObj.login(admin, adminPw);
		Validations.assertThat().object(login).isTrue().perform();
		ReportManager.log("Admin logged in successfully");
		dashBoardPageObj.navigateToCRM();
		Validations.assertThat().object(
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_INPROGRESS_STATUS))
				.isTrue().perform();
		ReportManager.log("Contract request is in progress");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_APPROVED_STATUS)).isTrue().perform();
		ReportManager.log("Old Request approved successfully by SFE admin");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_TEAM_MEMBERS_TAB,
				contractObj.getRequestId(), Constants.CONTRACT_MANAGEMENT_STATUS_NEW)).isTrue().perform();
		ReportManager.log("New Request added successfully to Team members list");
		Validations.assertThat().object(crmListPageObj.checkContractDetails(playerAccountName, contractObj)).isTrue()
				.perform();
		ReportManager.log("Contract request Details updated successfully");
		ReportManager.log("New Request added successfully to Team members list");
		dashBoardPageObj.logout();
		ReportManager.log("admin log out ");

	}

	@Test
	public void testApproveUpdatedContract() {
		ReportManager.log("============== Start Test Approve updated contract  ==============");
		ArrayList<String> data = new ArrayList<String>();
		String requestId;
		String newRequestId;
		String teamName;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String expectedStatus = Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS;
		String notificationMsg = properties.getProperty("ContractNotificationMsg");
		boolean requestApproved;

		// Step1 Prepare contract Updated for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		requestId = data.get(0);
		teamName = data.get(1);

		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(data.get(0));// requestId
		Validations.assertThat().object(contractApproved).isTrue().perform();
		newRequestId = contractManagementObj.updateContract(data.get(2), data.get(3), clubOwnerToken,
				Constants.ACCEPTINVITATION);
		Validations.assertThat().object(newRequestId).isNotNull().perform();

		notificationMsg = notificationMsg + " " + newRequestId + " " + properties.getProperty("ApproveNotificationMsg");

		// Step2 SFE login to approve request
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToCRM();
		ReportManager.log("SEF admin start approve request ");
		requestApproved = crmListPageObj.approveContractRequest(newRequestId, playerAccountName, false);
		Validations.assertThat().object(requestApproved).isTrue().perform();
		ReportManager.log("Request approved successfully by SFE admin");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_APPROVED_STATUS)).isTrue().perform();
		ReportManager.log("Old Request approved successfully by SFE admin");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_TEAM_MEMBERS_TAB,
				newRequestId, Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS)).isTrue().perform();
		ReportManager.log("New Request activated successfully to Team members list");

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

		ReportManager.log("============== End  Approve  updated contract Test   ==============");
	}

	@Test
	public void testRejectUpdatedContract() {
		ReportManager.log("============== Start  Reject  Updated contract Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String requestId;
		String newRequestId;
		String teamName;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String expectedStatus = Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS;
		String notificationMsg = properties.getProperty("ContractNotificationMsg");
		boolean requestRejected;

		// Step1 Prepare contract Updated for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		requestId = data.get(0);
		teamName = data.get(1);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(data.get(0));// requestId
		Validations.assertThat().object(contractApproved).isTrue().perform();
		newRequestId = contractManagementObj.updateContract(data.get(2), data.get(3), clubOwnerToken,
				Constants.ACCEPTINVITATION);
		Validations.assertThat().object(newRequestId).isNotNull().perform();

		notificationMsg = notificationMsg + " " + newRequestId + " "
				+ properties.getProperty("RejectContractNotificationMsg");

		// Step2 SFE login to Reject request
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToCRM();
		ReportManager.log("SEF admin start reject request ");
		requestRejected = crmListPageObj.rejectContractRequest(newRequestId, playerAccountName,
				Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS);
		Validations.assertThat().object(requestRejected).isTrue().perform();
		ReportManager.log("Request rejected successfully by SFE admin");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				newRequestId, Constants.CONTRACT_MANAGEMENT_REJECTED_STATUS)).isTrue().perform();
		ReportManager.log("Old Request approved successfully by SFE admin");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_TEAM_MEMBERS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS)).isTrue().perform();
		ReportManager.log("New Request activated successfully to Team members list");
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
	public void testRequireActionUpdatedContract() {
		ReportManager.log("============== Start  Require an action on  Updated contract Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String requestId;
		String newRequestId;
		String teamName;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerAccountName = properties.getProperty("playerAccName");
		String expectedStatus = Constants.CONTRACT_MANAGEMENT_REQUIRED_ACTION_STATUS_CLUB_OWNER;
		String notificationMsg = properties.getProperty("ContractNotificationMsg");
		boolean requireAnActionSent;
		boolean reSubmitActionDone;

		// Step1 Prepare contract Updated for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		requestId = data.get(0);
		teamName = data.get(1);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(data.get(0));
		Validations.assertThat().object(contractApproved).isTrue().perform();
		newRequestId = contractManagementObj.updateContract(data.get(2), data.get(3), clubOwnerToken,
				Constants.ACCEPTINVITATION);
		Validations.assertThat().object(newRequestId).isNotNull().perform();

		notificationMsg = " " + newRequestId + " " + properties.getProperty("ActionRequiredNotificationMsg");

		// Step2 SFE login to Require An action on request
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(admin, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by  SFE ");
		dashBoardPageObj.navigateToCRM();
		ReportManager.log("SEF admin start require an action on request ");
		requireAnActionSent = crmListPageObj.requireAnActionOnContractRequest(newRequestId, playerAccountName);
		Validations.assertThat().object(requireAnActionSent).isTrue().perform();
		ReportManager.log("SEF admin require an action successfully.");
		ReportManager.log("Check if  contract status changed in Team managment List for SFE ");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_APPROVED_STATUS)).isTrue().perform();
		ReportManager.log("Old Request approved successfully by SFE admin");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_TEAM_MEMBERS_TAB,
				newRequestId, Constants.CONTRACT_MANAGEMENT_REQUIRED_ACTION_STATUS)).isTrue().perform();
		ReportManager.log("New Request added successfully to Team members list");
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
		reSubmitActionDone = crmListPageObj.reSubmitContractRequest(newRequestId, playerAccountName,
				contractRequestObj);
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

		ReportManager.log("============== End  Reject contract Test   ==============");
	}

	@Test(dataProvider = "testRemovePendingUpdateRequestData")
	public void testRemoveContractPendingUpdateRequest(String actionType) {
		ReportManager.log(
				"============== Start  Remove member By Club owner with pending  Update contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String newRequestId;
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

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(data.get(0));// requestId
		Validations.assertThat().object(contractApproved).isTrue().perform();
		newRequestId = contractManagementObj.updateContract(data.get(2), data.get(3), clubOwnerToken,
				Constants.ACCEPTINVITATION);
		Validations.assertThat().object(newRequestId).isNotNull().perform();

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
		// go to CRM
		dashBoardPageObj.navigateToCRM();
		Validations.assertThat().object(
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS))
				.isTrue().perform();
		ReportManager.log("Parent Request with status completed");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				newRequestId, Constants.CONTRACT_MANAGEMENT_CANCELLED_STATUS)).isTrue().perform();
		ReportManager.log(" Request B Cancelled successfully in past requests tab");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_TEAM_MEMBERS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS)).isTrue().perform();
		ReportManager.log(" Request A is Active in Team Members tab ");
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
					newRequestId, Constants.CONTRACT_MANAGEMENT_CANCELLED_STATUS)).isTrue().perform();
			ReportManager.log(" Request B Cancelled successfully in past requests tab");
			Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
					requestId, Constants.CONTRACT_MANAGEMENT_APPROVED_STATUS)).isTrue().perform();
			ReportManager.log(" Request A is Approved successfully in past requests tab ");
			Validations.assertThat().object(crmListPageObj.checkNoContractRequests(Constants.CONTRACT_TEAM_MEMBERS_TAB))
					.isTrue().perform();
			ReportManager.log("No Requests in Team members requests tab");

		}

		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		ReportManager.log(
				"============== End  Remove member By Club owner with pending  Update contract Request Test   ==============");
	}

	@Test()
	public void testRemoveContractPendingUpdateInvitationSFE() {
		ReportManager.log(
				"============== Start  Remove member By SFE admin with pending update Invitation Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String newRequestId;
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
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(data.get(0));// requestId
		Validations.assertThat().object(contractApproved).isTrue().perform();
		newRequestId = contractManagementObj.updateContract(data.get(2), data.get(3), clubOwnerToken,
				Constants.NOACTIONINVITATION);
		Validations.assertThat().object(newRequestId).isNotNull().perform();

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
		ReportManager.log(" Request A Approved successfully");
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
				"==============End Remove member By SFE admin with pending update Invitation Test  ==============");
	}

	@Test()
	public void testRemoveContractPendingUpdateRequestSFE() {
		ReportManager.log(
				"============== Start  Remove member By SFE admin with pending update contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String newRequestId;
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
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(data.get(0));// requestId
		Validations.assertThat().object(contractApproved).isTrue().perform();
		newRequestId = contractManagementObj.updateContract(data.get(2), data.get(3), clubOwnerToken,
				Constants.ACCEPTINVITATION);
		Validations.assertThat().object(newRequestId).isNotNull().perform();

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
		ReportManager.log(" Request A Approved successfully");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				newRequestId, Constants.CONTRACT_MANAGEMENT_REJECTED_STATUS)).isTrue().perform();
		ReportManager.log(" Request B Rejected successfully");
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
				"==============End Remove member By SFE admin with pending update contract Request Test   ==============");
	}

	@Test(dataProvider = "testArchivePendingUpdateRequestData")
	public void testArchivePendingUpdateRequest(String actionType) {
		ReportManager.log(
				"============== Start  Archive team  By Club owner with pending  Update contract Request Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String newRequestId;
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

		// Step1 Prepare contract for player
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(data.get(0));// requestId
		Validations.assertThat().object(contractApproved).isTrue().perform();
		newRequestId = contractManagementObj.updateContract(data.get(2), data.get(3), clubOwnerToken,
				Constants.ACCEPTINVITATION);
		Validations.assertThat().object(newRequestId).isNotNull().perform();

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
					newRequestId, Constants.CONTRACT_MANAGEMENT_CANCELLED_STATUS)).isTrue().perform();
			ReportManager.log(" Request B Cancelled successfully in past requests tab");
			Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
					requestId, Constants.CONTRACT_MANAGEMENT_APPROVED_STATUS)).isTrue().perform();
			ReportManager.log(" Request A is Approved successfully in past requests tab ");
			Validations.assertThat().object(crmListPageObj.checkNoContractRequests(Constants.CONTRACT_TEAM_MEMBERS_TAB))
					.isTrue().perform();
			ReportManager.log("No Requests in Team members requests tab");

		}

		// Step 5 clubowner can archive team
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
				"============== End Archive team  By Club owner with pending  Update contract Request Test   ==============");
	}

	@Test(dataProvider = "testArchiveWithUpdatedContractSFEData")
	public void testArchiveTeamWithUpdatedContractSFE(String contractType) {
		ReportManager.log(
				"============== Start  Archive Team By SFE admin with Active  Updated contracts Test   ==============");
		ArrayList<String> data = new ArrayList<String>();
		String teamName;
		String requestId;
		String newRequestId;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerAccountName = properties.getProperty("playerAccName");

		// Step1 Prepare Pending update/invitation update contract for player

		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		Validations.assertThat().object(data).isNotNull().perform();
		requestId = data.get(0);
		teamName = data.get(1);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(data.get(0));// requestId
		Validations.assertThat().object(contractApproved).isTrue().perform();
		if (contractType.equals(Constants.CONTRACT_PEDNING_UPDATE_INVITATION))
			newRequestId = contractManagementObj.updateContract(data.get(2), data.get(3), clubOwnerToken,
					Constants.NOACTIONINVITATION);
		else
			newRequestId = contractManagementObj.updateContract(data.get(2), data.get(3), clubOwnerToken,
					Constants.ACCEPTINVITATION);
		Validations.assertThat().object(newRequestId).isNotNull().perform();

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
				crmListPageObj.checkContractRequestStatus(requestId, Constants.CONTRACT_MANAGEMENT_INPROGRESS_STATUS))
				.isTrue().perform();
		ReportManager.log("Parent Request with status completed");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_TEAM_MEMBERS_TAB,
				newRequestId, Constants.CONTRACT_MANAGEMENT_STATUS_NEW)).isTrue().perform();
		ReportManager.log(" Request B Still new ");
		Validations.assertThat().object(crmListPageObj.checkRequestStatusById(Constants.CONTRACT_PAST_REQUESTS_TAB,
				requestId, Constants.CONTRACT_MANAGEMENT_APPROVED_STATUS)).isTrue().perform();
		ReportManager.log(" Request A Still Approved ");
		dashBoardPageObj.logout();
		ReportManager.log("club owner  user log out ");

		ReportManager.log(
				"==============End  Archive Team By SFE admin with Active  Updated contracts Test   ==============");
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
