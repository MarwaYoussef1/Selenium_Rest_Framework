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


public class RequestsService {

	private static String filterRequestsUrl="team-club/v1/requests/club-owner-request";
	private RestActions apiObj;
	private String tokenHeader;
	
	public RequestsService(String token,RestActions apiObj) {
		this.apiObj = apiObj;
		this.tokenHeader="Bearer " + token;
	}
	
	public String filterRequestsByRequestId(String requestId)
	{
		
		Filter filter= new Filter();
		filter.setColumnName("name");
		filter.setColumnValue(requestId);
		filter.setOperator("equals");
		ArrayList<Filter> filters= new ArrayList<Filter>();
		String requestSystemId = null;
		filters.add(filter);
		
		FilterSorting sorting = new FilterSorting();
		sorting.setSortName("createdAt");
		sorting.setSortType("DESC");
		sorting.setSortColumnType("date");
		ArrayList<FilterSorting> sortingList= new ArrayList<FilterSorting>();
		sortingList.add(sorting);
		
		FilterBody RequestFilterbody= new FilterBody();
		RequestFilterbody.setFilters(filters);
		RequestFilterbody.setSorting(sortingList);
		RequestFilterbody.setPageLength(10);
		RequestFilterbody.setPageNo(0);
		
		Response response = apiObj.buildNewRequest(filterRequestsUrl, RequestType.POST).addHeader("Authorization", tokenHeader)
								.setContentType(ContentType.JSON).setRequestBody(RequestFilterbody).useRelaxedHTTPSValidation().perform();
		System.out.println("Response is "+response.asPrettyString());
		if(response.getStatusCode()==200)
		{
		JsonPath jsonPathObj = new JsonPath(response.asString());
		requestSystemId = jsonPathObj.get("content.find{it.requestId =='" + requestId + "'}.systemId");
		}
		return requestSystemId;
		
	}
	
	
}
