package stc.esport.api.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.ParametersType;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import stc.esport.api.pojo.invitations.InvitationBody;
import stc.esport.api.pojo.invitations.Member;
import stc.esport.utils.Constants;

public class InvitationService {

	private static String getInvitationUrl = "team-club/v1/invitation";
	private static String acceptInvitationUrl = "team-club/v1/invitation/approve/";
	private static String rejectInvitationUrl = "team-club/v1/invitation/reject/";
	private static String cancelInvitationUrl = "team-club/v1/invitation/cancel/";
	private static String acceptContractInvitationUrl = "team-club/v1/invitation/approve/contract/";
	private RestActions apiObj;
	private String tokenHeader;

	public InvitationService(String token, RestActions apiObj) {
		this.apiObj = apiObj;
		this.tokenHeader = "Bearer " + token;
	}

	public String getInvitationByTeam(String teamName, String invitationType) {
		List<List<Object>> parameters = Arrays.asList(Arrays.asList("page", 0), Arrays.asList("size", 10),
				Arrays.asList("sort", "creationDate,desc"), Arrays.asList("type", invitationType));
		Response response = apiObj.buildNewRequest(getInvitationUrl, RequestType.GET)
				.addHeader("Authorization", tokenHeader).setParameters(parameters, ParametersType.QUERY)
				.setContentType(ContentType.JSON).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is " + response.asPrettyString());
		JsonPath jsonPathObj = new JsonPath(response.asString());
		String invitationId = jsonPathObj.get("content.find{it.team.name =='" + teamName + "'}.systemId");
		System.out.print("invitationID " + invitationId);
		return invitationId;

	}
	
	public String getInvitationByStatusAndTeam(String status,String teamName, String invitationType) {
		String invitationId ;
		List<List<Object>> parameters = Arrays.asList(Arrays.asList("page", 0), Arrays.asList("size", 10),
				Arrays.asList("sort", "creationDate,desc"), Arrays.asList("type", invitationType),Arrays.asList("searchableValue", status));
		Response response = apiObj.buildNewRequest(getInvitationUrl, RequestType.GET)
				.addHeader("Authorization", tokenHeader).setParameters(parameters, ParametersType.QUERY)
				.setContentType(ContentType.JSON).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is " + response.asPrettyString());
		JsonPath jsonPathObj = new JsonPath(response.asString());
		if(teamName==null)
		invitationId = jsonPathObj.get("content.find{it.team== null}.systemId");
		else
	    invitationId = jsonPathObj.get("content.find{it.team.name =='"+ teamName + "'}.systemId");
		System.out.print("invitationID " + invitationId);
		return invitationId;

	}
	
	public boolean inviteSpecialMember(String token ,String invitationType,String email)
	{
		
		InvitationBody invitationBody = new InvitationBody();
		ArrayList<Member> members =new ArrayList<Member>();
		Member member =new Member();
		member.setMemberId(null);
		member.setEmail(email);
		
		if(invitationType.equals(Constants.INVITATION_CONTENT_CREATOR_TYPE_API))
		{
			member.setRole(Constants.CONTENTCREATORAPI);
			invitationBody.setInvType(invitationType);
			invitationBody.setInvitationFrom(Constants.INVITATION_FROM_CLUB_API);
			invitationBody.setFromType(Constants.INVITATION_CLUB_TYPE_API);
		}
		members.add(member);
		invitationBody.setMembers(members);
		return sendInvitation(token,invitationBody);
		
	}
	
	public boolean sendInvitation(String token,InvitationBody body) {
		Response response = apiObj.buildNewRequest(getInvitationUrl, RequestType.POST).addHeader("Authorization", "Bearer " + token)
				.setContentType(ContentType.JSON).useRelaxedHTTPSValidation().setRequestBody(body)
				.setTargetStatusCode(200).perform();
		System.out.println("Response status is " + response.statusCode());
		return response.statusCode()==200;
		

	}

	public boolean acceptRejectCancelInvitation(String action, String invitationId) {
		String url = null;
		// Request paylaod sending along with post request
		HashMap<String,String> requestDetails = new HashMap<String,String>();
		requestDetails.put("status", "APPROVED");
		JSONObject requestBody = new JSONObject(requestDetails);
		if (action.equals(Constants.ACCEPTINVITATION))
			url = acceptInvitationUrl + invitationId;
		else if (action.equals(Constants.REJECTINVITATION))
			url = rejectInvitationUrl + invitationId;
		else if (action.equals(Constants.CANCELINVITATION))
			url = cancelInvitationUrl + invitationId;
		Response resp = apiObj.buildNewRequest(url, RequestType.POST).useRelaxedHTTPSValidation()
				.addHeader("Authorization", tokenHeader).setContentType(ContentType.JSON).setRequestBody(requestBody)
				.performRequest();
		System.out.println("Response is " + resp.asPrettyString());
		return true;
	}
	
	public boolean acceptRejectContractInvitation(String action, String invitationId) {
		String url = null;
		
		if (action.equals(Constants.ACCEPTINVITATION))
			url = acceptContractInvitationUrl + invitationId;
		Response resp = apiObj.buildNewRequest(url, RequestType.POST).useRelaxedHTTPSValidation()
				.addHeader("Authorization", tokenHeader).setContentType(ContentType.TEXT).setRequestBody(invitationId)
				.performRequest();
		System.out.println("Response is " + resp.asPrettyString());
		return resp.statusCode()==200;
	}
	
	

}
