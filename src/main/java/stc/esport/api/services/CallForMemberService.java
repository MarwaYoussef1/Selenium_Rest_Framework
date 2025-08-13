package stc.esport.api.services;

import java.util.Properties;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import stc.esport.api.pojo.CallForMember;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class CallForMemberService {

	private static String callForMemberUrl="team-club/v1/announcements";
	private RestActions apiObj;
	private Properties properties;
	private String tokenHeader;
	
	public CallForMemberService(RestActions apiObj, Properties properties) {
		this.apiObj = apiObj;
		this.properties = properties;

	}
	
	public boolean callForMemberPost (String token,String teamId) {
		tokenHeader="Bearer "+token;
		
		CallForMember body= new CallForMember();
	      body.setMessage("call for member post");
	      body.setTeamId(teamId);
	      body.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
	      body.setVacancies("4");
	      body.setEndAt(Utils.getTomorrowDateInMilisecond());
	      
	      System.out.println(body.getRole());
	      System.out.println(body.getEndAt());
	      
	  	Response response = apiObj.buildNewRequest(callForMemberUrl, RequestType.POST).addHeader("Authorization", tokenHeader)
				.setContentType(ContentType.JSON).setRequestBody(body).useRelaxedHTTPSValidation().setTargetStatusCode(201).perform();
	  	
           System.out.println("Response is "+response.asPrettyString());
           
           return response.getStatusCode()==201;
	}
}
