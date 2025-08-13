package stc.esport.api.services;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AssginRequestService {

	private static String assignRequestsUrlPart1 = "team-club/v1/assigned_request/";
	private static String assignRequestsUrlPart2 = "/assigned_admin";
	private static String reAssignRequestsUrl = "/reassign";
	private RestActions apiObj;
	private String tokenHeader;

	public AssginRequestService(String token, RestActions apiObj) {
		this.apiObj = apiObj;
		this.tokenHeader = "Bearer " + token;
	}
	
	public boolean reAssignUser(String requestSystemId, String userEmail) {
		// get userId of admin using reqSystemId		
		String userId = getUserIdForAssignedUserOnRequest(requestSystemId, userEmail);
		return reAssignUserApi(requestSystemId,  userId);
		
	}

	public String getUserIdForAssignedUserOnRequest(String requestSystemId, String email) {
		String userId = null;
		String url = assignRequestsUrlPart1 + requestSystemId + assignRequestsUrlPart2;
		Response response = apiObj.buildNewRequest(url, RequestType.GET).addHeader("Authorization", tokenHeader)
				.setContentType(ContentType.JSON).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is " + response.asPrettyString());
		if (response.getStatusCode() == 200) {
			JsonPath jsonPathObj = new JsonPath(response.asString());
			userId = jsonPathObj.get("content.find{it.user.email =='" + email + "'}.userId");
		}
		return userId;

	}

	public boolean reAssignUserApi(String requestSystemId, String userId) {
		// Request paylaod sending along with post request
		JSONObject requestParams = new JSONObject();
		requestParams.put("userId", userId);
		String url = assignRequestsUrlPart1 + requestSystemId + reAssignRequestsUrl;
		Response response = apiObj.buildNewRequest(url, RequestType.PATCH).addHeader("Authorization", tokenHeader)
				.setContentType(ContentType.JSON).setRequestBody(requestParams).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is " + response.asPrettyString());
		return response.getStatusCode() == 200;

	}

}
