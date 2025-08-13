package stc.esport.api.services;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import stc.esport.api.pojo.SignUpBody;
import stc.esport.api.pojo.SignUpUserBody;
import stc.esport.utils.Constants;

public class SendOtpService {
	
	private static String sendOTPUrl="team-club/v1/public/otp/send";
	private RestActions apiObj;
	
	public SendOtpService(RestActions apiObj) {
		this.apiObj = apiObj;
		
	}
	
	
	
	public boolean sendOtp(SignUpBody body)
	{
		String phoneNumber=body.getSignupData().getCountryCode()+body.getSignupData().getMobileNumber();
		  //Request paylaod sending along with post request
		  JSONObject requestParams=new JSONObject();
		  requestParams.put("mobileNumber",phoneNumber);
		  requestParams.put("profileType",Constants.CLUB_OWNER_ROLE_API);
		  requestParams.put("name",body.getSignupData().getName());
		  requestParams.put("email",body.getSignupData().getEmail());
		Response resp=apiObj.buildNewRequest(sendOTPUrl, RequestType.POST)
		 .setContentType(ContentType.JSON).useRelaxedHTTPSValidation().setRequestBody(requestParams).performRequest();
		 /* Response resp=RestAssured.given().relaxedHTTPSValidation()
		            .contentType("application/json")
		           .body(requestParams).when().post("https://stage.saudiesports.sa/api/team-club/v1/public/otp/send");*/
		
		
		return resp.getStatusCode()==200;
		
		
	}
	
	
	
	

}
