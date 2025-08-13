package stc.esport.api.services;

import java.util.Properties;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MemberPublicProfileService {
	
	private static String memberPublicProfileUrl="team-club/v1/public/users/profile/";
	private RestActions apiObj;
	private Properties properties;
	
	public MemberPublicProfileService(RestActions apiObj, Properties properties) {
		this.apiObj = apiObj;
		this.properties = properties;
	}
	
	public String  getMemberProfileDetails(String memberId) {
		String url = memberPublicProfileUrl+memberId;
		
		Response response = apiObj.buildNewRequest(url, RequestType.GET)
				.setContentType(ContentType.JSON).useRelaxedHTTPSValidation()
				.perform();
		System.out.println("Response status is " + response.statusCode());
		
		JsonPath jsonPathObj = new JsonPath(response.asString());
		
		String clanNum= jsonPathObj.getString("clansCount");
		System.out.println(clanNum);

        return clanNum;
        //return response.getStatusCode()==200;

	}

}
