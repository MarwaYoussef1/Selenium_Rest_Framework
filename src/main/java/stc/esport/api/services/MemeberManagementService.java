package stc.esport.api.services;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import stc.esport.api.pojo.filter.Filter;
import stc.esport.api.pojo.filter.FilterBody;
import stc.esport.api.pojo.filter.FilterSorting;

public class MemeberManagementService {

	private static String filterMemebersUrl="team-club/v1/members";
	private static String filterBlockedMemebersUrl="team-club/v1/members?blocked=true";
	private static String BlockUrl="/block";
	private static String unBlockUrl="/unblock/";
    private RestActions apiObj;
	private String tokenHeader;

	public MemeberManagementService(RestActions apiObj,String token) {
		this.apiObj = apiObj;
		this.tokenHeader="Bearer "+token;

	}

	
	public String filterMember(String memberEmail,boolean blocked) {
		String systemId = null;
		String filterUrl=null;
		if(blocked)
			filterUrl=filterBlockedMemebersUrl;
		else 
			filterUrl=filterMemebersUrl;
		
		Filter filter= new Filter();
		filter.setColumnName("query");
		filter.setColumnValue(memberEmail);
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
		
	
		Response response = apiObj.buildNewRequest(filterUrl, RequestType.POST).addHeader("Authorization", tokenHeader)
								.setContentType(ContentType.JSON).setRequestBody(filterBody).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is "+response.asPrettyString());
		if(response.getStatusCode()==200)
		{
		JsonPath jsonPathObj = new JsonPath(response.asString());
		systemId=jsonPathObj.get("content[0].userSystemId");		
		
		
		}
		return systemId;
	}
	
	public boolean unBlockMember(String memberEmail) {
		String url = null;
		String memberId= filterMember(memberEmail,true);
		url = filterMemebersUrl + unBlockUrl+memberId;
		Response resp = apiObj.buildNewRequest(url, RequestType.PUT).useRelaxedHTTPSValidation()
				.addHeader("Authorization", tokenHeader).setContentType(ContentType.JSON).performRequest();
		System.out.println("Response is " + resp.asPrettyString());
		return resp.getStatusCode()==200;
	}
	
	public boolean blockMember(String memberEmail) {
		String url = null;
		url = filterMemebersUrl + BlockUrl;
		String memberId= filterMember(memberEmail,false);
		//Request paylaod sending along with put request
		JSONObject requestParams=new JSONObject();
		requestParams.put("lockReason","Test block");
		requestParams.put("userId",memberId);
		Response resp = apiObj.buildNewRequest(url, RequestType.PUT).useRelaxedHTTPSValidation()
				.addHeader("Authorization", tokenHeader).setContentType(ContentType.JSON).setRequestBody(requestParams)
				.performRequest();
		System.out.println("Response is " + resp.asPrettyString());
		return resp.getStatusCode()==200;
	}
	
	
	
}
