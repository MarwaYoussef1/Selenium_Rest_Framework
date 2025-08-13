package stc.esport.api.services;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.response.Response;

public class CustomersService {
	
	private static String customer="customer/v1/customer/";
	private RestActions apiObj;
	private String tokenHeader;
	
	public CustomersService(String token,RestActions apiObj) {
		this.apiObj = apiObj;
		this.tokenHeader="Bearer " + token;
	}

	public String getCustomerId(String primaryContactEmail)
	{
       	Response resp=apiObj.buildNewRequest(customer, RequestType.GET).addHeader("Authorization", tokenHeader).useRelaxedHTTPSValidation().performRequest();
		String systemId= resp.jsonPath().getString("findAll { it.primaryContactEmail=='"+primaryContactEmail+"' }.systemId").replaceAll("[\\[\\]\\(\\)]", "");//$..book[?(@.author=='J.R.R. Tolkien')].title
		return systemId;
	}
	
	public int deleteCustomerId(String primaryContactEmail)
	{
		
		String systemId =getCustomerId(primaryContactEmail);
       	Response resp=apiObj.buildNewRequest(customer+"/"+systemId+"/", RequestType.DELETE).useRelaxedHTTPSValidation().addHeader("Authorization", tokenHeader).performRequest();
		int status = resp.getStatusCode();
		return status;
	}
	
	
}
