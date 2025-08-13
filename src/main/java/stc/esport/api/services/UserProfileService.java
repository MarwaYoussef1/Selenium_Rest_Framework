package stc.esport.api.services;

import java.util.ArrayList;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import stc.esport.api.pojo.filter.Filter;
import stc.esport.api.pojo.filter.FilterBody;
import stc.esport.api.pojo.filter.FilterSorting;
import stc.esport.api.pojo.userprofile.UserProfileRequestBody;
import stc.esport.api.pojo.userprofile.UserProfileResponseBody;

public class UserProfileService {

	private static String filterProfilesUrl="uaa/profile/filter";
	private static String profileUrl="uaa/profile/";
    private RestActions apiObj;
	private String tokenHeader;

	public UserProfileService(RestActions apiObj) {
		this.apiObj = apiObj;
		

	}

	
	public String filterByProfileName(String token,String profileName) {
		tokenHeader="Bearer "+token;
		String profileId = null;
		Filter filter= new Filter();
		filter.setColumnName("name");
		filter.setColumnValue(profileName);
		filter.setOperator("equals");
		ArrayList<Filter> filters= new ArrayList<Filter>();
		filters.add(filter);
		
		FilterSorting sorting = new FilterSorting();
		sorting.setSortName("id");
		sorting.setSortType("DESC");
		sorting.setSortColumnType("string");
		ArrayList<FilterSorting> sortingList= new ArrayList<FilterSorting>();
		sortingList.add(sorting);
		
		FilterBody filterBody= new FilterBody();
		filterBody.setFilters(filters);
		filterBody.setSorting(sortingList);
		filterBody.setPageLength(10);
		filterBody.setPageNo(0);
		
	
		Response response = apiObj.buildNewRequest(filterProfilesUrl, RequestType.POST).addHeader("Authorization", tokenHeader)
								.setContentType(ContentType.JSON).setRequestBody(filterBody).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is "+response.asPrettyString());
		if(response.getStatusCode()==200)
		{
		JsonPath jsonPathObj = new JsonPath(response.asString());
		profileId=jsonPathObj.get("content[0].id");		
		
		
		}
		return profileId;
	}
	
	public UserProfileResponseBody getProfileDetails(String token,String profileName) {
		String url = null;
		UserProfileResponseBody profileResponse =null;
		tokenHeader="Bearer "+token;
		String profileId= filterByProfileName(token, profileName);
		url = profileUrl + profileId;
		Response resp = apiObj.buildNewRequest(url, RequestType.GET).useRelaxedHTTPSValidation()
				.addHeader("Authorization", tokenHeader).setContentType(ContentType.JSON)
				.performRequest();
		System.out.println("Response is " + resp.asPrettyString());
		if(resp.getStatusCode()==200)
			profileResponse = resp.getBody().as(UserProfileResponseBody.class);
		return profileResponse;
	}
	
	public  boolean updateProfileDetails(String token,UserProfileRequestBody profileBody) {
		tokenHeader="Bearer "+token;
		//profileBody.setInvitationsEnabled(true);
		//profileBody.setSignUp(true);
		Response resp = apiObj.buildNewRequest(profileUrl, RequestType.PUT).useRelaxedHTTPSValidation()
				.addHeader("Authorization", tokenHeader).setContentType(ContentType.JSON).setRequestBody(profileBody)
				.performRequest();		
		return resp.getStatusCode()==200;
		
	}
	
	
}
