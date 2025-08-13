
//26.3.2023
package stc.esport.testcases;

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

import com.shaft.tools.io.ReportManager;
import com.shaft.validation.Validations;

import stc.esport.api.business.ContractManagement;
import stc.esport.api.business.IncidentManagement;
import stc.esport.api.pojo.SignUpData;
import stc.esport.api.pojo.Teams.TeamInvitation;
import stc.esport.api.pojo.userprofile.UserProfileRequestBody;
import stc.esport.api.pojo.userprofile.UserProfileResponseBody;
import stc.esport.api.services.ApplyForPostService;
import stc.esport.api.services.CallForMemberService;
import stc.esport.api.services.InvitationService;
import stc.esport.api.services.LoginService;
import stc.esport.api.services.MemberPublicProfileService;
import stc.esport.api.services.MemeberManagementService;
import stc.esport.api.services.RequestsService;
import stc.esport.api.services.SignUpService;
import stc.esport.api.services.TeamService;
import stc.esport.api.services.UserProfileService;
import stc.esport.base.TestBase;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;
import stc.esport.testdata.TestData;
import stc.esport.utils.Constants;

public class APITests extends TestBase {
	// Variables
	LoginPage loginPageObj;
	HomePage homePageObj;
	LoginService loginServiceObj;
	RequestsService reqServiceObj;
	UserProfileService userProfileServiceObj;

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
		getBaseApiObj();
		loginServiceObj = new LoginService(properties);

	}

	@BeforeTest
	public void BeforeTest() {
		ReportManager.log("BeforeTest");
	}

	@Test()
	public void testAPIFilterRequest() {
		ReportManager.log("============== Test API   ==============");
		String userCstAdmin = properties.getProperty("superAdmin");
		String userCstAdminPw = properties.getProperty("superAdminPW");
		String token = loginServiceObj.login(userCstAdmin, userCstAdminPw);
		reqServiceObj = new RequestsService(token, apiObj);
		Validations.assertThat().object(reqServiceObj.filterRequestsByRequestId("RQ000000130")).isNotNull().perform();
		ReportManager.log("==============  Test API End ==============");
	}

	@Test()
	public void testAPISignUpRequest() {
		ReportManager.log("============== Test API Sign Up request   ==============");
		SignUpService signupService = new SignUpService(apiObj, properties);
		ArrayList<Object> data = signupService.defaultSignUp();
		SignUpData SignUp = (SignUpData) data.get(0);
		String requestId = data.get(1).toString();
		Validations.assertThat().object(SignUp).isNotNull().perform();
		Validations.assertThat().object(requestId).isNotNull().perform();
		ReportManager.log("==============  Test API End ==============");
	}
	
	@DataProvider(name = "testAPISignUpUserRequestData")
	public Object[][] testAPISignUpUserRequestData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"),
				"testAPISignUpUserRequest");
		return data;
	}

	//@Test(dataProvider = "testAPISignUpUserRequestData")
	/*public void testAPISignUpUserRequest(String roleType) {
		
		ReportManager.log("============== Test API Sign Up request   ==============");
		SignUpService signupService = new SignUpService(apiObj, properties);
		boolean signedUp = signupService.signUpUser(roleType);
		Validations.assertThat().object(signedUp).isTrue().perform();
		ReportManager.log("==============  Test API End ==============");
	}*/

	@Test()
	public void testAPICreateTeamRequest() {
		ReportManager.log("============== Test API Create Team request   ==============");

		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerEmail = properties.getProperty("playerAcc");
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);

		TeamInvitation playerinvitation = new TeamInvitation();
		TeamService teamService = new TeamService(apiObj, properties);
		playerinvitation.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
		playerinvitation.setEmail(playerEmail);
		ArrayList<TeamInvitation> invitations = new ArrayList<TeamInvitation>();
		invitations.add(playerinvitation);
		String teamName = teamService.createTeamWithInvitation(clubOwnerToken, true, invitations).getName();
		Validations.assertThat().object(teamName).isNotNull().perform();
		ReportManager.log("==============  Test API End ==============");
	}

	@Test()
	public void testAPICreateClanRequest() {
		ReportManager.log("============== Test API Create Clan request   ==============");

		String playerEmail = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String playerToken = loginServiceObj.login(playerEmail, playerPw);

		String otherPlayerEmail = properties.getProperty("otherPlayerAcc");
		TeamInvitation playerinvitation = new TeamInvitation();
		TeamService teamService = new TeamService(apiObj, properties);
		playerinvitation.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
		playerinvitation.setEmail(otherPlayerEmail);
		ArrayList<TeamInvitation> invitations = new ArrayList<TeamInvitation>();
		invitations.add(playerinvitation);
		String teamName = teamService.createTeamWithInvitation(playerToken, false, invitations).getName();
		Validations.assertThat().object(teamName).isNotNull().perform();
		ReportManager.log("==============  Test API End ==============");
	}

	@Test()
	public void testApiAcceptInvitation() {
		ReportManager.log("============== Test API Accept invitation   ==============");

		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerEmail = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);

		TeamInvitation playerinvitation = new TeamInvitation();
		TeamService teamService = new TeamService(apiObj, properties);
		playerinvitation.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
		playerinvitation.setEmail(playerEmail);
		ArrayList<TeamInvitation> invitations = new ArrayList<TeamInvitation>();
		invitations.add(playerinvitation);
		String teamName = teamService.createTeamWithInvitation(clubOwnerToken, true, invitations).getName();
		String playerToken = loginServiceObj.login(playerEmail, playerPw);
		InvitationService invitationService = new InvitationService(playerToken, apiObj);
		String invitationId = invitationService.getInvitationByTeam(teamName, Constants.RECEIVEDINVITATION);
		boolean acceptInvitation = invitationService.acceptRejectCancelInvitation(Constants.ACCEPTINVITATION,
				invitationId);
		Validations.assertThat().object(acceptInvitation).isTrue().perform();
		ReportManager.log("invitation ID= " + invitationId);
		ReportManager.log("==============  Test API End ==============");
	}

	@Test()
	public void testApiRejectInvitation() {
		ReportManager.log("============== Test API Reject invitation   ==============");

		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerEmail = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);

		TeamInvitation playerinvitation = new TeamInvitation();
		TeamService teamService = new TeamService(apiObj, properties);
		playerinvitation.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
		playerinvitation.setEmail(playerEmail);
		ArrayList<TeamInvitation> invitations = new ArrayList<TeamInvitation>();
		invitations.add(playerinvitation);
		String teamName = teamService.createTeamWithInvitation(clubOwnerToken, true, invitations).getName();
		String playerToken = loginServiceObj.login(playerEmail, playerPw);
		InvitationService invitationService = new InvitationService(playerToken, apiObj);
		String invitationId = invitationService.getInvitationByTeam(teamName, Constants.RECEIVEDINVITATION);
		boolean rejectInvitation = invitationService.acceptRejectCancelInvitation(Constants.REJECTINVITATION,
				invitationId);
		Validations.assertThat().object(rejectInvitation).isTrue().perform();
		ReportManager.log("invitation ID= " + invitationId);
		ReportManager.log("==============  Test API End ==============");
	}

	@Test()
	public void testApiCancelInvitation() {
		ReportManager.log("============== Test API Reject invitation   ==============");

		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerEmail = properties.getProperty("playerAcc");
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);

		TeamInvitation playerinvitation = new TeamInvitation();
		TeamService teamService = new TeamService(apiObj, properties);
		playerinvitation.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
		playerinvitation.setEmail(playerEmail);
		ArrayList<TeamInvitation> invitations = new ArrayList<TeamInvitation>();
		invitations.add(playerinvitation);
		String teamName = teamService.createTeamWithInvitation(clubOwnerToken, true, invitations).getName();
		InvitationService invitationService = new InvitationService(clubOwnerToken, apiObj);
		String invitationId = invitationService.getInvitationByTeam(teamName, Constants.SENTINVITATION);
		boolean cancelInvitation = invitationService.acceptRejectCancelInvitation(Constants.CANCELINVITATION,
				invitationId);
		Validations.assertThat().object(cancelInvitation).isTrue().perform();
		ReportManager.log("invitation ID= " + invitationId);
		ReportManager.log("==============  Test API End ==============");
	}

	@Test()
	public void testAPIFilterTeam() {
		ReportManager.log("============== Test API   ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String token = loginServiceObj.login(clubOwner, clubOwnerPw);
		TeamService teamService = new TeamService(apiObj, properties);
		teamService.filterTeamByTeamEnName("AUTOOZZZYGFBNNM", Constants.MYTEAMLIST, token);
		ReportManager.log("==============  Test API End ==============");
	}

	@Test()
	public void testAPICallForMember() {
		ReportManager.log("============== Test API   ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String token = loginServiceObj.login(clubOwner, clubOwnerPw);
		TeamService teamService = new TeamService(apiObj, properties);
		String teamId = teamService.filterTeamByTeamEnName("AUTOOKCHLAUWSTO", Constants.MYTEAMLIST, token);
		CallForMemberService callForMemberService = new CallForMemberService(apiObj, properties);
		callForMemberService.callForMemberPost(token, teamId);
	}

	@Test()
	public void testAPIJoinTeam() {
		ReportManager.log("============== Test API   ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerEmail = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		TeamService teamService = new TeamService(apiObj, properties);
		String teamName = teamService.createTeamWithInvitation(clubOwnerToken, true, null).getName();
		String teamId = teamService.filterTeamByTeamEnName(teamName, Constants.MYTEAMLIST, clubOwnerToken);
		CallForMemberService callForMemberService = new CallForMemberService(apiObj, properties);
		callForMemberService.callForMemberPost(clubOwnerToken, teamId);
		String playerToken = loginServiceObj.login(playerEmail, playerPw);
		ApplyForPostService applyForMemberService = new ApplyForPostService(playerToken, apiObj);
		applyForMemberService.joinTeam(teamId);

	}

	@Test()
	public void testAPIArchiveTeam() {
		ReportManager.log("============== Test API   ==============");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String token = loginServiceObj.login(clubOwner, clubOwnerPw);
		TeamService teamService = new TeamService(apiObj, properties);
		String teamId = teamService.filterTeamByTeamEnName("AUTOOKHWYWAOJWB", Constants.MYTEAMLIST, token);
		teamService.archiveTeam(token, teamId);
	}

	@Test()
	public void testAPIUpdateUserProfile() {
		ReportManager.log("============== Test API Update user Profile request   ==============");
		userProfileServiceObj = new UserProfileService(apiObj);
		UserProfileResponseBody profileResponseBody = null;
		UserProfileRequestBody profileRequestBody = new UserProfileRequestBody();
		// login by admin user
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String token = loginServiceObj.login(admin, adminPw);
		// filter to get any profile details
		String profileId = userProfileServiceObj.filterByProfileName(token, Constants.MEDIAOUTLETROLE);
		Validations.assertThat().object(profileId).isNotNull().perform();
		// get profile Details
		profileResponseBody = userProfileServiceObj.getProfileDetails(token, profileId);
		Validations.assertThat().object(profileResponseBody).isNotNull().perform();
		// create profile request body
		ArrayList<String> authorities = new ArrayList<String>();
		profileRequestBody.setId(profileResponseBody.getId());
		profileRequestBody.setLevelId(profileResponseBody.getLevelId());
		profileRequestBody.setName(profileResponseBody.getName());
		profileRequestBody.setNameAr(profileResponseBody.getNameAr());
		authorities = profileResponseBody.getAuthorities();
		authorities.add(Constants.ADD_TEAM_CLAN);
		profileRequestBody.setAuthorities(authorities);
		// update profile details
		Validations.assertThat().object(userProfileServiceObj.updateProfileDetails(token, profileRequestBody)).isTrue()
				.perform();
		ReportManager.log("==============  Test API End ==============");
	}

	@Test()
	public void testAPIMemberPublicProfile() {
		ReportManager.log("============== Test API   ==============");
		MemberPublicProfileService service = new MemberPublicProfileService(apiObj, properties);
		service.getMemberProfileDetails("CTC_31578358");
	}

	@Test()
	public void testApiBlockUnblockMember() {
		ReportManager.log("============== Test API Block member   ==============");

		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String blockedPlayerEmail = properties.getProperty("BlockedPlayer");
		String blockedPlayerPw = properties.getProperty("BlockedPlayerPW");
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);

		TeamInvitation playerinvitation = new TeamInvitation();
		TeamService teamService = new TeamService(apiObj, properties);
		playerinvitation.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
		playerinvitation.setEmail(blockedPlayerEmail);
		ArrayList<TeamInvitation> invitations = new ArrayList<TeamInvitation>();
		invitations.add(playerinvitation);
		// Create team with player
		String teamName = teamService.createTeamWithInvitation(clubOwnerToken, true, invitations).getName();
		String playerToken = loginServiceObj.login(blockedPlayerEmail, blockedPlayerPw);
		InvitationService invitationService = new InvitationService(playerToken, apiObj);
		String invitationId = invitationService.getInvitationByTeam(teamName, Constants.RECEIVEDINVITATION);
		boolean acceptInvitation = invitationService.acceptRejectCancelInvitation(Constants.ACCEPTINVITATION,
				invitationId);
		Validations.assertThat().object(acceptInvitation).isTrue().perform();
		ReportManager.log("invitation ID= " + invitationId);
		// Block member
		String adminToken = loginServiceObj.login(admin, adminPw);
		MemeberManagementService memeberManagementService = new MemeberManagementService(apiObj, adminToken);
		boolean memberBlocked = memeberManagementService.blockMember(blockedPlayerEmail);
		Validations.assertThat().object(memberBlocked).isTrue().perform();
		// unblock member
		boolean memberUnBlocked = memeberManagementService.unBlockMember(blockedPlayerEmail);
		Validations.assertThat().object(memberUnBlocked).isTrue().perform();
		ReportManager.log("==============  Test API End ==============");
	}

	@Test()
	public void testApiApproveContract() {
		ReportManager.log("============== Test API Approve contract   ==============");
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		String requestId = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false).get(0);
		Validations.assertThat().object(requestId).isNotNull().perform();
		ReportManager.log("==============  Test API End ==============");
	}

	@Test()
	public void testApiApproveContractByAdmin() {
		ReportManager.log("============== Test API Approve contract by admin  ==============");
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		String requestId = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false).get(0);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(requestId);
		Validations.assertThat().object(contractApproved).isTrue().perform();
		ReportManager.log("==============  Test API End ==============");
	}
	
	@Test()
	public void testApiCreateContract() {
		ReportManager.log("============== Test API create contract  ==============");
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		String requestId = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false).get(0);
		Validations.assertThat().object(requestId).isNotNull().perform();
		ReportManager.log("==============  Test API End ==============");
	}
	
	@Test()
	public void testApiUpdateContractAcceptInvitation() {
		ReportManager.log("============== Test API Update contract  ==============");
		ArrayList<String> data = new ArrayList<String>();
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(data.get(0));//requestId
		Validations.assertThat().object(contractApproved).isTrue().perform();
		Validations.assertThat().object(contractManagementObj.updateContract(data.get(2), data.get(3), clubOwnerToken,Constants.ACCEPTINVITATION)).isNotNull().perform();
		ReportManager.log("==============  Test API End ==============");
	}
	
	@Test()
	public void testApiUpdateContractCancelInvitation() {
		ReportManager.log("============== Test API Update contract  ==============");
		ArrayList<String> data = new ArrayList<String>();
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		data = contractManagementObj.prepareContract(Constants.ACCEPTINVITATION, false);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(data.get(0));//requestId
		Validations.assertThat().object(contractApproved).isTrue().perform();
		Validations.assertThat().object(contractManagementObj.updateContract(data.get(2), data.get(3), clubOwnerToken,Constants.NOACTIONINVITATION)).isNotNull().perform();
		ReportManager.log("==============  Test API End ==============");
	}

	@DataProvider(name = "testApiApproveMemberContractByAdminData")
	public Object[][] testApiApproveMemberContractByAdminData() throws InvalidFormatException, IOException {
		Object[][] data = TestData.fetchData(properties.getProperty("testDataPath"),
				"testApiApproveMemberContractByAdmin");
		return data;
	}

	@Test(dataProvider = "testApiApproveMemberContractByAdminData")
	public void testApiApproveMemberContractByAdmin(String roleType, String email) {
		ReportManager.log("============== Test API Approve contract by admin  ==============");
		ContractManagement contractManagementObj = new ContractManagement(apiObj, properties);
		String requestId = contractManagementObj.prepareContractByRole(Constants.ACCEPTINVITATION, false,roleType,email).get(0);
		// Approve member contract by admin
		boolean contractApproved = contractManagementObj.approveContractByAdmin(requestId);
		Validations.assertThat().object(contractApproved).isTrue().perform();
		ReportManager.log("==============  Test API End ==============");
	}
	@Test()
	public void testApiCreateIncident() {
		ReportManager.log("============== Test API create Incident  ==============");
		IncidentManagement incidentManagementObj = new IncidentManagement(apiObj, properties);
		ArrayList<String> incidentValues= incidentManagementObj.prepareIncident();
		String incidentSysId = incidentValues.get(0);
		String incidentId = incidentValues.get(1);
		Validations.assertThat().object(incidentSysId).isNotNull().perform();
		Validations.assertThat().object(incidentId).isNotNull().perform();
		ReportManager.log("==============  Test API End ==============");
	}
	
	@Test()
	public void testApiApproveIncident() {
		ReportManager.log("============== Test API create Incident  ==============");
		IncidentManagement incidentManagementObj = new IncidentManagement(apiObj, properties);
		String incidentId = incidentManagementObj.prepareIncident().get(0);
		Validations.assertThat().object(incidentId).isNotNull().perform();
		Validations.assertThat().object(incidentManagementObj.approveIncident(incidentId)).isTrue().perform();
		ReportManager.log("==============  Test API End ==============");
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		ReportManager.log("AfterMethod");

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
