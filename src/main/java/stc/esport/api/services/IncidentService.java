package stc.esport.api.services;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import stc.esport.api.pojo.contract.Contract;
import stc.esport.api.pojo.filter.Filter;
import stc.esport.api.pojo.filter.FilterBody;
import stc.esport.api.pojo.filter.FilterSorting;
import stc.esport.api.pojo.incident.Incident;

public class IncidentService {

	private static String incidentUrl = "incident/v1/incident";
	private static String approveIncidentUrl = "incident/v1/incident/action/approve";
	private static String reassignIncidentUrl = "/reassign";
	private static String assignedIncidentsUrl = "/assigned_admin";
	private static String filterIncidentsUrl= "incident/v1/incident/filter";
	
	private RestActions apiObj;
	private String tokenHeader;
	
	public IncidentService(RestActions apiObj,String token) {
		this.apiObj = apiObj;
		this.tokenHeader="Bearer " + token;
		
	}
	
	public String filterIncidentsByIncidentId(String incidentSystemId)
	{
		String incidentId = null;

		Filter filter= new Filter();
		filter.setColumnName("query");
		filter.setColumnValue("");
		filter.setOperator("equals");
		ArrayList<Filter> filters= new ArrayList<Filter>();
		filters.add(filter);
		
		FilterSorting sorting = new FilterSorting();
		sorting.setSortName("createdAt");
		sorting.setSortType("DESC");
		sorting.setSortColumnType("date");
		ArrayList<FilterSorting> sortingList= new ArrayList<FilterSorting>();
		sortingList.add(sorting);
		
		FilterBody IncidentFilterbody= new FilterBody();
		IncidentFilterbody.setFilters(filters);
		IncidentFilterbody.setSorting(sortingList);
		IncidentFilterbody.setPageLength(10);
		IncidentFilterbody.setPageNo(0);
		
		Response response = apiObj.buildNewRequest(filterIncidentsUrl, RequestType.POST).addHeader("Authorization", tokenHeader)
								.setContentType(ContentType.JSON).setRequestBody(IncidentFilterbody).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is "+response.asPrettyString());
		if(response.getStatusCode()==200)
		{
		JsonPath jsonPathObj = new JsonPath(response.asString());
		incidentId = jsonPathObj.get("content.find{it.systemId =='" + incidentSystemId + "'}.incidentId");
		}
		return incidentId;
		
	}

	public String createIncident(Incident incidentBody) {
		
		String incidentId = null;
		Response response = apiObj.buildNewRequest(incidentUrl, RequestType.POST)
				.addHeader("Authorization", tokenHeader).setContentType(ContentType.JSON).useRelaxedHTTPSValidation()
				.setRequestBody(incidentBody).perform();
		System.out.println("Response is " + response.asPrettyString());

		if (response.getStatusCode() == 200) {
			JsonPath jsonPathObj = new JsonPath(response.asString());
			incidentId = jsonPathObj.get("systemId");

			return incidentId;
		}

		return incidentId;
	}

	public boolean approveIncident(String token,String incidentId) {
		tokenHeader="Bearer " + token;
		// Request paylaod sending along with post request
		JSONObject requestParams = new JSONObject();
		requestParams.put("forceApprove", false);
		requestParams.put("systemId", incidentId);
		Response resp = apiObj.buildNewRequest(approveIncidentUrl, RequestType.PUT)
				.addHeader("Authorization", tokenHeader).setContentType(ContentType.JSON).useRelaxedHTTPSValidation()
				.setRequestBody(requestParams).performRequest();
		System.out.println("Response is " + resp.asPrettyString());
		return resp.getStatusCode() == 200;

	}
	
	public boolean reAssignUser(String token,String incidentId, String userEmail) {
		tokenHeader="Bearer " + token;
		// get userId of admin using reqSystemId		
		String userId = getUserIdForAssignedUserOnIncident(token,incidentId, userEmail);
		return reAssignUserApi(token,incidentId,  userId);
		
	}

	public String getUserIdForAssignedUserOnIncident(String token,String incidentId, String email) {
		String userId = null;
		String url = incidentUrl + "/"+incidentId + assignedIncidentsUrl;
		Response response = apiObj.buildNewRequest(url, RequestType.GET).addHeader("Authorization", tokenHeader)
				.setContentType(ContentType.JSON).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is " + response.asPrettyString());
		if (response.getStatusCode() == 200) {
			JsonPath jsonPathObj = new JsonPath(response.asString());
			userId = jsonPathObj.get("content.find{it.user.email =='" + email + "'}.userId");
		}
		return userId;

	}

	public boolean reAssignUserApi(String token,String incidentId, String userId) {
		// Request paylaod sending along with post request
		JSONObject requestParams = new JSONObject();
		requestParams.put("userId", userId);
		String url = incidentUrl + "/"+ incidentId + reassignIncidentUrl;
		Response response = apiObj.buildNewRequest(url, RequestType.PATCH).addHeader("Authorization", tokenHeader)
				.setContentType(ContentType.JSON).setRequestBody(requestParams).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is " + response.asPrettyString());
		return response.getStatusCode() == 200;

	}

}
