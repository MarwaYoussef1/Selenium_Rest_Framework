package stc.esport.api.services;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApplyForPostService {
	
	private static String joinTeamUrl="team-club/v1/applicants";
	private RestActions apiObj;
	private String tokenHeader;
	
	public ApplyForPostService(String token,RestActions apiObj) {
		this.apiObj = apiObj;
		this.tokenHeader = "Bearer " + token;
	}
	
	
	
	public boolean joinTeam(String teamId)
	{
		  //Request paylaod sending along with post request
		  JSONObject requestParams=new JSONObject();
		  requestParams.put("teamId",teamId);
		Response resp=apiObj.buildNewRequest(joinTeamUrl, RequestType.POST)
				.addHeader("Authorization", tokenHeader).setContentType(ContentType.JSON).useRelaxedHTTPSValidation().setRequestBody(requestParams).performRequest();
		return resp.getStatusCode()==200;
		
		
	}
	
	
	
	

}
