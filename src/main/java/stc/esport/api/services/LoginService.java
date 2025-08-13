package stc.esport.api.services;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.ParametersType;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class LoginService {
	
	private static String authService="/token";
	private Properties properties;
	public LoginService(Properties properties) {
		this.properties = properties;
		
	}
	
	
	public String login(String userName,String password)
	{
		String baseAuthUrl;
		baseAuthUrl=properties.getProperty("keycloackUrl");
		List<List<Object>> parameters = Arrays.asList(Arrays.asList("username", userName),Arrays.asList("password", password),
				Arrays.asList("grant_type", "password"),Arrays.asList("client_id", "WEB"));
		  	 
		RestActions apiObj= new RestActions(baseAuthUrl);//DriverFactory.getAPIDriver(baseAuthUrl);
		Response resp=apiObj.buildNewRequest(authService, RequestType.POST).useRelaxedHTTPSValidation().setParameters(parameters, ParametersType.FORM)
		.setContentType(ContentType.URLENC).performRequest();
		String token= resp.jsonPath().getString("access_token");
		System.out.println("token is =" +token);
		
		return token ;
		
	}
	
	
	
	

}
