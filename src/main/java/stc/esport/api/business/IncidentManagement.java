package stc.esport.api.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.shaft.api.RestActions;

import stc.esport.api.pojo.Teams.TeamInvitation;
import stc.esport.api.pojo.contract.Contract;
import stc.esport.api.pojo.incident.*;
import stc.esport.api.services.IncidentService;
import stc.esport.api.services.InvitationService;
import stc.esport.api.services.LoginService;
import stc.esport.api.services.UploadService;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class IncidentManagement {

	public Properties properties;

	private RestActions apiObj;

	LoginService loginServiceObj;

	
	IncidentService incidentServiceObj;

	UploadService uploadServiceObj;
	

	public IncidentManagement(RestActions apiObj, Properties properties) {
		this.properties = properties;
		this.apiObj = apiObj;
		loginServiceObj = new LoginService(properties);
		
	}
	
	public ArrayList<String> prepareIncident()
	{
		String clubOwner = properties.getProperty("clubOwnerAcc");
		String clubOwnerPw = properties.getProperty("clubOwnerPW");
		ArrayList<String> incidentValues= new ArrayList<String>();
		String incidentSysId;
		String incidentId;
		String clubOwnerToken = loginServiceObj.login(clubOwner, clubOwnerPw);
		incidentServiceObj = new IncidentService(apiObj,clubOwnerToken);
		Incident incidentBody= fillIncidentBody();
		incidentSysId=incidentServiceObj.createIncident(incidentBody);
		incidentId=incidentServiceObj.filterIncidentsByIncidentId(incidentSysId);
		incidentValues.add(incidentSysId);
		incidentValues.add(incidentId);
		return incidentValues;
	}
	public Incident fillIncidentBody()
	{
		UploadService uploadservice = new UploadService(apiObj);
		ArrayList<EvidenceAttachment> evidenceAttachments = new ArrayList<EvidenceAttachment>();
		ArrayList<Defendant> defendants = new ArrayList<Defendant>();
		ArrayList<String> generalAttachments = new ArrayList<String>();
		ArrayList<String> requests = new ArrayList<String>();
		Incident incident= new Incident();
		String incidentAttachmentFile = uploadservice.uploadFile(properties.getProperty("IncidentUploadFile"),Constants.INCIDENT_UPLOAD_TYPE_API);
		EvidenceAttachment evidenceAttachmentsObj= new EvidenceAttachment();
		evidenceAttachmentsObj.setDescription("Evidence");
		evidenceAttachmentsObj.setFileId(incidentAttachmentFile);
		evidenceAttachments.add(evidenceAttachmentsObj);
		
		NationalAddress nationalAddressObj =new NationalAddress();
		nationalAddressObj.setCityId(26);
		nationalAddressObj.setDistrictId(917);
		
		Issuer issure= new Issuer();
		issure.setFullName("Automation Club owner account");
		issure.setNationalityId(6);
		issure.setUserType(Constants.CLUB_OWNER_ROLE_API);
		issure.setIdType(Constants.NATIONAL_ID);
		issure.setNationalId(properties.getProperty("clubOwnerNationalId"));
		issure.setNationalAddress(nationalAddressObj);
		
		Defendant  defendantPlayer = new Defendant();
		defendantPlayer.setUserType(Constants.CLUBPLAYERROLE.toUpperCase());
		defendantPlayer.setFullName(properties.getProperty("playerAccName"));
		defendantPlayer.setNationalityId(168);
		defendantPlayer.setCountryCode(properties.getProperty("saudiaMobileCode"));
		defendantPlayer.setMobileNumber("567890432");
		defendantPlayer.setEmail("Auto_player@mailsac.com");
		defendantPlayer.setIdType(Constants.NATIONAL_ID);
		defendantPlayer.setNationalId(properties.getProperty("NationalId"));
		defendantPlayer.setNationalAddress(nationalAddressObj);
		
		
		Defendant  defendantPlayerTemp = new Defendant();
		defendantPlayerTemp.setUserType(Constants.CLUBPLAYERROLE.toUpperCase());
		defendantPlayerTemp.setFullName("automation player temp");//properties.getProperty("BirthDateUnderAge")
		defendantPlayerTemp.setNationalityId(168);
		defendantPlayerTemp.setCountryCode(properties.getProperty("saudiaMobileCode"));
		defendantPlayerTemp.setMobileNumber(Utils.getRandomStringORNum(7,"num"));
		defendantPlayerTemp.setEmail(Utils.generateRandomEmailAddress("mailsac"));
		defendantPlayerTemp.setIdType(Constants.NATIONAL_ID);
		defendantPlayerTemp.setNationalId(Utils.getRandomStringORNum(9, "num"));
		defendantPlayerTemp.setNationalAddress(nationalAddressObj);
		
		defendants.add(defendantPlayer);
		defendants.add(defendantPlayerTemp);
		
		requests.add("Request");
		
		incident.setHaveRepresentative(null);
		incident.setHaveDefendants(true);
		incident.setType(Constants.INCIDENT_COMPLAIN_TYPE_API);
		incident.setOnBehalf(Constants.INCIDENT_MYSELF_API);
		incident.setAcceptTerms(true);
		incident.setIssuer(issure);
		incident.setDefendants(defendants);
		incident.setGeneralAttachments(generalAttachments);
		incident.setSummary("summary");
		incident.setEvidenceAttachments(evidenceAttachments);
		incident.setRequests(requests);
		
		return incident;
	}
	
	public boolean approveIncident(String incidentSysId)
	{
		boolean incidentReassigned;
		String admin = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		String adminToken = loginServiceObj.login(admin, adminPw);
		incidentReassigned = incidentServiceObj.reAssignUser(adminToken, incidentSysId, admin);
		if(incidentReassigned)
		return incidentServiceObj.approveIncident(adminToken, incidentSysId);
		else
			return false;
	}
	
	

	
}
