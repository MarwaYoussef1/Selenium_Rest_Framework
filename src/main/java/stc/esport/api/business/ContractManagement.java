package stc.esport.api.business;

import java.util.ArrayList;
import java.util.Properties;

import com.shaft.api.RestActions;

import stc.esport.api.pojo.Teams.TeamInvitation;
import stc.esport.api.pojo.contract.Contract;
import stc.esport.api.services.AssginRequestService;
import stc.esport.api.services.InvitationService;
import stc.esport.api.services.LoginService;
import stc.esport.api.services.MemberContractService;
import stc.esport.api.services.MemeberManagementService;
import stc.esport.api.services.RequestsService;
import stc.esport.api.services.TeamService;
import stc.esport.api.services.UploadService;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class ContractManagement {

	public Properties properties;

	private RestActions apiObj;

	LoginService loginServiceObj;

	MemberContractService memberContractServiceObj;

	InvitationService invitationServiceObj;

	UploadService uploadServiceObj;

	public ContractManagement(RestActions apiObj, Properties properties) {
		this.properties = properties;
		this.apiObj = apiObj;
		loginServiceObj = new LoginService(properties);

	}

	public ArrayList<String> createTeamWithMember() {
		// login by Club owner user
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String playerEmail = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		ArrayList<String> data = new ArrayList<String>();
		// create Team with invitation
		TeamInvitation playerinvitation = new TeamInvitation();
		TeamService teamService = new TeamService(apiObj, properties);
		playerinvitation.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
		playerinvitation.setEmail(playerEmail);
		ArrayList<TeamInvitation> invitations = new ArrayList<TeamInvitation>();
		invitations.add(playerinvitation);
		String teamName = teamService.createTeamWithInvitation(clubOwnerToken, true, invitations).getName();
		String playerToken = loginServiceObj.login(playerEmail, playerPw);

		// Accept invitation
		InvitationService invitationService = new InvitationService(playerToken, apiObj);
		String invitationId = invitationService.getInvitationByTeam(teamName, Constants.RECEIVEDINVITATION);
		boolean acceptInvitation = invitationService.acceptRejectCancelInvitation(Constants.ACCEPTINVITATION,
				invitationId);
		if (acceptInvitation) {

			String adminToken = loginServiceObj.login(admin, adminPw);
			MemeberManagementService memberManagementService = new MemeberManagementService(apiObj, adminToken);
			// get team Id & member Id
			String teamId = teamService.filterTeamByTeamEnName(teamName, Constants.MYTEAMLIST, clubOwnerToken);
			String memberId = memberManagementService.filterMember(playerEmail, false);
			data.add(clubOwnerToken);
			data.add(teamId);
			data.add(teamName);
			data.add(memberId);
		}
		return data;

	}

	public ArrayList<String> prepareContract(String approveRejectContract, boolean expired) {

		ArrayList<String> invitationdata = createTeamWithMember();
		if (invitationdata.isEmpty()) {
			return null;
		}
		String clubOwnerToken = invitationdata.get(0);
		String teamId = invitationdata.get(1);
		String teamName = invitationdata.get(2);
		String memberId = invitationdata.get(3);
		ArrayList<String> contractData = new ArrayList<String>();
		ArrayList<String> data = new ArrayList<String>();
		String requestId = null;
		Contract contractUploadedObj = null;
		boolean approveRejectContractInvitation = false;
		boolean NoInvitationContractAction = approveRejectContract.equals(Constants.NOACTIONINVITATION);
		// upload contract
		memberContractServiceObj = new MemberContractService(apiObj, clubOwnerToken);
		contractUploadedObj = uploadContract(expired, teamId, memberId, clubOwnerToken);
		if (!NoInvitationContractAction)// upload contract done & club owner cancel request
		{
			if (contractUploadedObj != null) {// approve reject contract

				contractData = memberContractServiceObj.filterMemberContract(memberId, teamId);
				approveRejectContractInvitation = approveRejectContractByMember(approveRejectContract,
						contractData.get(0));
			}
			if (approveRejectContractInvitation) {
				contractData = memberContractServiceObj.filterMemberContract(memberId, teamId);
				requestId = contractData.get(1);
				data.add(requestId);
				data.add(teamName);
				data.add(teamId);
				data.add(memberId);
			}
		} else // in case of cancel invitation
		{
			data.add(teamId);
			data.add(teamName);
			data.add(memberId);
		}

		return data;

	}

	public ArrayList<String> prepareContractByRole(String approveRejectContract, boolean expired, String roleType,
			String email) {
		// login by Club owner user
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		String pw = properties.getProperty("playerPW");
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		ArrayList<String> contractData = new ArrayList<String>();
		ArrayList<String> data = new ArrayList<String>();
		String requestId = null;
		boolean contractUploaded = false;
		boolean approveRejectContractInvitation = false;

		// create Team with invitation
		TeamInvitation memberinvitation = new TeamInvitation();
		TeamService teamService = new TeamService(apiObj, properties);
		memberinvitation.setRole(roleType.toUpperCase());
		memberinvitation.setEmail(email);
		ArrayList<TeamInvitation> invitations = new ArrayList<TeamInvitation>();
		invitations.add(memberinvitation);
		String teamName = teamService.createTeamWithInvitation(clubOwnerToken, true, invitations).getName();
		String memberToken = loginServiceObj.login(email, pw);

		// Accept invitation
		InvitationService invitationService = new InvitationService(memberToken, apiObj);
		String invitationId = invitationService.getInvitationByTeam(teamName, Constants.RECEIVEDINVITATION);
		boolean acceptInvitation = invitationService.acceptRejectCancelInvitation(Constants.ACCEPTINVITATION,
				invitationId);

		if (acceptInvitation) {
			String adminToken = loginServiceObj.login(admin, adminPw);
			MemeberManagementService memberManagementService = new MemeberManagementService(apiObj, adminToken);
			// get team Id & member Id
			String teamId = teamService.filterTeamByTeamEnName(teamName, Constants.MYTEAMLIST, clubOwnerToken);
			String memberId = memberManagementService.filterMember(email, false);
			// upload contract
			memberContractServiceObj = new MemberContractService(apiObj, clubOwnerToken);
			contractUploaded = uploadContractUnderAge(expired, teamId, memberId, clubOwnerToken);
			if (contractUploaded) {// approve reject contract

				contractData = memberContractServiceObj.filterMemberContract(memberId, teamId);
				approveRejectContractInvitation = approveRejectContractByMemberType(email, approveRejectContract,
						contractData.get(0));
			}
			if (approveRejectContractInvitation) {
				contractData = memberContractServiceObj.filterMemberContract(memberId, teamId);
				requestId = contractData.get(1);
				data.add(requestId);
				data.add(teamName);

			}

		}

		return data;

	}

	public Contract uploadContract(boolean expired, String teamId, String memberId, String clubOwnerToken) {
		// upload contract
		boolean contractUploaded = false;
		UploadService uploadservice = new UploadService(apiObj);
		String memberFile = uploadservice.uploadFile(properties.getProperty("MemberFile"),
				Constants.MEMBER_CONTRACT_FILE);
		String memberContractIdFile = uploadservice.uploadFile(properties.getProperty("MemberIdFile"),
				Constants.MEMBER_CONTRACT_ID_FILE);

		Contract contractBody = new Contract();
		contractBody.setTeamSystemId(teamId);
		contractBody.setMemberSystemId(memberId);
		contractBody.setName(Utils.getRandomStringORNum(10, "String"));
		contractBody.setNameAr("عقد" + " " + Utils.getRandomArabicString(7));
		contractBody.setGender("MALE");
		contractBody.setMemberStatusCode("EMPLOYEE");
		contractBody.setCountryId(6);
		contractBody.setBirthDate(properties.getProperty("BirthDateUnderAge"));
		contractBody.setBirthDate(properties.getProperty("BirthDate"));
		contractBody.setIdTypeCode("NATIONAL_ID");
		contractBody.setNationalId(properties.getProperty("NationalId"));
		contractBody.setMemberContractIdFile(memberContractIdFile);
		contractBody.setMemberContractFile(memberFile);
		contractBody.setSaudiEleague(true);

		if (expired) {
			contractBody.setFromDate(properties.getProperty("ExpiredContractStartDate"));
			contractBody.setToDate(properties.getProperty("ExpiredContractEndDate"));
		} else {
			contractBody.setFromDate(Utils.getCurrentDate("yyyy-MM-dd"));
			contractBody.setToDate(properties.getProperty("ContractEndDate"));
		}
		contractBody.setSalary(properties.getProperty("Salary"));
		contractBody.setTermsAccepted(true);
		memberContractServiceObj = new MemberContractService(apiObj, clubOwnerToken);
		contractUploaded = memberContractServiceObj.uploadContract(contractBody);
		if (contractUploaded)
			return contractBody;

		return null;
	}

	public boolean uploadContractUnderAge(boolean expired, String teamId, String memberId, String clubOwnerToken) {
		// upload contract
		boolean contractUploaded = false;
		UploadService uploadservice = new UploadService(apiObj);
		String memberFile = uploadservice.uploadFile(properties.getProperty("MemberFile"),
				Constants.MEMBER_CONTRACT_FILE);
		String memberContractIdFile = uploadservice.uploadFile(properties.getProperty("MemberIdFile"),
				Constants.MEMBER_CONTRACT_ID_FILE);
		String memberContractParentFile = uploadservice.uploadFile(properties.getProperty("MemberFile"),
				Constants.MEMBER_CONTRACT_PARENT_ID_FILE);
		String memberContractApprovalFile = uploadservice.uploadFile(properties.getProperty("MemberIdFile"),
				Constants.MEMBER_CONTRACT_PARENT_APPROVAL_FILE);

		Contract contractBody = new Contract();
		contractBody.setTeamSystemId(teamId);
		contractBody.setMemberSystemId(memberId);
		contractBody.setName(Utils.getRandomStringORNum(10, "String"));
		contractBody.setNameAr("عقد" + " " + Utils.getRandomArabicString(7));
		contractBody.setGender("MALE");
		contractBody.setMemberStatusCode("EMPLOYEE");
		contractBody.setCountryId(6);
		contractBody.setBirthDate(properties.getProperty("BirthDateUnderAge"));
		// contractBody.setBirthDate(properties.getProperty("BirthDate"));
		// contractBody.setIdTypeCode("NATIONAL_ID");
		// contractBody.setNationalId(properties.getProperty("NationalId"));
		contractBody.setParentIdTypeCode("NATIONAL_ID");
		contractBody.setParentNationalId(properties.getProperty("NationalId"));
		contractBody.setMemberContractParentIdFile(memberContractParentFile);
		contractBody.setMemberContractParentApprovalFile(memberContractApprovalFile);
		;
		contractBody.setMemberContractIdFile(memberContractIdFile);
		contractBody.setMemberContractFile(memberFile);
		contractBody.setSaudiEleague(false);

		if (expired) {
			contractBody.setFromDate(properties.getProperty("ExpiredContractStartDate"));
			contractBody.setToDate(properties.getProperty("ExpiredContractEndDate"));
		} else {
			contractBody.setFromDate(Utils.getCurrentDate("yyyy-MM-dd"));
			contractBody.setToDate(properties.getProperty("ContractEndDate"));
		}
		contractBody.setSalary(properties.getProperty("Salary"));
		contractBody.setTermsAccepted(true);
		memberContractServiceObj = new MemberContractService(apiObj, clubOwnerToken);
		contractUploaded = memberContractServiceObj.uploadContract(contractBody);
		return contractUploaded;
	}

	public boolean approveRejectContractByMember(String action, String contractInvitationId) {
		boolean acceptContractInvitation = false;
		String playerEmail = properties.getProperty("playerAcc");
		String playerPw = properties.getProperty("playerPW");
		String playerToken = loginServiceObj.login(playerEmail, playerPw);
		InvitationService invitationService = new InvitationService(playerToken, apiObj);
		acceptContractInvitation = invitationService.acceptRejectContractInvitation(action, contractInvitationId);
		return acceptContractInvitation;
	}

	public boolean approveRejectContractByMemberType(String email, String action, String contractInvitationId) {
		boolean acceptContractInvitation = false;
		String pw = properties.getProperty("playerPW");
		String memberToken = loginServiceObj.login(email, pw);
		InvitationService invitationService = new InvitationService(memberToken, apiObj);
		acceptContractInvitation = invitationService.acceptRejectContractInvitation(action, contractInvitationId);
		return acceptContractInvitation;
	}

	public boolean approveContractByAdmin(String requestId) {

		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String adminToken = loginServiceObj.login(admin, adminPw);
		boolean requestReassigned = false;
		boolean contractApproved = false;
		String contractSystemId = null;
		AssginRequestService assginRequestServiceObj;
		MemberContractService memberContractServiceObj;
		RequestsService reqServiceObj = new RequestsService(adminToken, apiObj);
		assginRequestServiceObj = new AssginRequestService(adminToken, apiObj);
		memberContractServiceObj = new MemberContractService(apiObj, adminToken);
		int lastCharIndex = requestId.lastIndexOf(requestId.charAt(requestId.length() - 1));
		String parentRequestId = requestId.substring(0, lastCharIndex);

		// filter by request id to get reqSystemId
		String reqSystemId = reqServiceObj.filterRequestsByRequestId(parentRequestId);
		// Reassign request to admin
		if (reqSystemId != null) {
			requestReassigned = assginRequestServiceObj.reAssignUser(reqSystemId, admin);
			// Filter to get specific member contract id
			if (requestReassigned)
				contractSystemId = memberContractServiceObj.searchOnSpecificContractRequest(reqSystemId, requestId);
			if (reqSystemId != null)
				contractApproved = memberContractServiceObj.approveContractRequest(contractSystemId);
		}
		// Approve member contract
		return contractApproved;

	}
	
	public String updateContract(String teamId, String memberId, String clubOwnerToken,String approveRejectInvitation) {
		// upload contract
		
		boolean contractUpdated = false;
		boolean contractAccepted=false;
		ArrayList<String> contractData = new ArrayList<String>();
		UploadService uploadservice = new UploadService(apiObj);
		String memberFile = uploadservice.uploadFile(properties.getProperty("MemberFile"),
				Constants.MEMBER_CONTRACT_FILE);
		Contract contractBody = new Contract();
		contractBody.setMemberStatusCode("SCHOOL_STUDENT");
		contractBody.setMemberContractFile(memberFile);
		contractBody.setSaudiEleague(true);
    	contractBody.setFromDate(Utils.getCurrentDate("yyyy-MM-dd"));
		contractBody.setToDate(properties.getProperty("ContractEndDate"));
		contractBody.setSalary(properties.getProperty("Salary"));
		contractBody.setTermsAccepted(true);
		contractBody.setReason("Reason");		
		memberContractServiceObj = new MemberContractService(apiObj, clubOwnerToken);
		contractData = memberContractServiceObj.filterMemberContract(memberId, teamId);
		contractUpdated = memberContractServiceObj.updateContract(contractBody, contractData.get(2));
		if (contractUpdated)
		{
			if(approveRejectInvitation.equals(Constants.ACCEPTINVITATION))
			{
			contractData = memberContractServiceObj.filterMemberContract(memberId, teamId);
			contractAccepted = approveRejectContractByMember(approveRejectInvitation,contractData.get(0));
			contractData = memberContractServiceObj.filterMemberContract(memberId, teamId);
			}
			return contractData.get(1);	
		}
		
		
		
		return null;
	}

}
