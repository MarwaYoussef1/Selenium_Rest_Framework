package stc.esport.api.services;

import java.util.ArrayList;
import java.util.Properties;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import stc.esport.api.pojo.SignUpBody;
import stc.esport.api.pojo.SignUpData;
import stc.esport.api.pojo.SignUpUserBody;
import stc.esport.api.pojo.SignUpUserData;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class SignUpService {

	private static String signUpUrl="team-club/v1/public/club-owner-request/signup";
	private static String signUpUserUrl="team-club/v1/public/users/sign-up";
	private RestActions apiObj;
	private Properties properties;
	
	
	public SignUpService(RestActions apiObj2,Properties properties) {
		this.apiObj = apiObj2;
		this.properties=properties;
	
	}
	
	public ArrayList<Object> defaultSignUp()
	{
		String mobileNumber= properties.getProperty("mobileStartWith")+Utils.getRandomStringORNum(7,"num");
		
		SignUpBody body=new SignUpBody();
		SignUpData data=new SignUpData();
		UploadService uploadservice=new UploadService(apiObj);
		String crFileId=uploadservice.uploadFile(properties.getProperty("clubCrFile"), Constants.SIGNUPCRFILE);
		String clubLogoId=uploadservice.uploadFile(properties.getProperty("clubLogo"), Constants.SIGNUPCLUBLOGO);
		String ibanFileId=uploadservice.uploadFile(properties.getProperty("clubCrFile"), Constants.SIGNUPCLUBIBANFile);
		String nationalIdFile=uploadservice.uploadFile(properties.getProperty("clubCrFile"), Constants.SIGNUPCLUBNATIONALIDFile);
		
		
		data.setCountryCode(properties.getProperty("saudiaMobileCode"));
		data.setMobileNumber(mobileNumber);
		data.setEmail(Utils.generateRandomEmailAddress(properties.getProperty("fakedomain")));
		data.setClubIBAN("SA"+Utils.getRandomStringORNum(22,"num"));
		data.setNationalId(Utils.getRandomStringORNum(9,"num"));
		data.setCrNumber(Utils.getRandomStringORNum(10,"num"));
		data.setClubName(Utils.getRandomStringORNum(10,"String"));
		data.setClubNameAr("نادى" + " "+Utils.getRandomArabicString(7));
		data.setName(Utils.getRandomStringORNum(5,"String"));
		data.setPassword(properties.getProperty("password"));
		data.setGender("MALE");
		data.setCountryId(58);
		data.setNationalIdFile(nationalIdFile);
        data.setClubLogo(clubLogoId);
        data.setCrFile(crFileId);
        data.setClubIBANFile(ibanFileId);
        data.setCrEndDate(properties.getProperty("crEndDate"));
        data.setAgreeTerms(true);
      
		
		body.setOtpCode(properties.getProperty("otp"));
		body.setSignupData(data);
		return signUp(body);
	}
	
	/*public boolean signUpUser(String role)
	{
		String mobileNumber= properties.getProperty("mobileStartWith")+Utils.getRandomStringORNum(7,"num");
		
		SignUpUserBody userBody=new SignUpUserBody();
		SignUpUserData data=new SignUpUserData();
		
		data.setCountryCode(properties.getProperty("saudiaMobileCode"));
		data.setMobileNumber(properties.getProperty("saudiaMobileCode")+mobileNumber);
		data.setEmail(Utils.generateRandomEmailAddress(properties.getProperty("fakedomain")));
		data.setName(Utils.getRandomStringORNum(5,"String"));
		data.setPassword(properties.getProperty("password"));
		data.setGender("MALE");
		data.setCountryId(168);
		data.setDateOfBirth(properties.getProperty("dateOfBirth"));
        data.setProfileName("");
        if(role.equals(Constants.CONTENTCREATORROLE))
        	 data.setProfileType(Constants.CONTENTCREATORAPI);
        else
        data.setProfileType(role.toUpperCase());
      
		
        userBody.setOtpCode(properties.getProperty("otp"));
        userBody.setUserSignUpDto(data);
		return signUpUser(userBody);
	}*/
	
	
	public ArrayList<Object> signUp(SignUpBody body)
	{
		ArrayList<Object> data= new ArrayList<Object>();
		SendOtpService sendOtpService = new SendOtpService(apiObj);
		boolean otp= sendOtpService.sendOtp(body);
		if(otp)
		{
		Response response = apiObj.buildNewRequest(signUpUrl, RequestType.POST).setContentType(ContentType.JSON).useRelaxedHTTPSValidation().setRequestBody(body).perform();
			 /*Response response=RestAssured.given().relaxedHTTPSValidation()
			            .contentType("application/json")
			           .body(body).when().post("https://stage.saudiesports.sa/api/team-club/v1/public/club-owner-request/signup");*/
		System.out.println("Response is "+response.asPrettyString());
		if(response.getStatusCode()==200)
		{
			data.add(body.getSignupData());
			data.add(response.jsonPath().getString("requestId"));
			return data;
		}
		
		}
		return null;
		
	}
	
	/*public boolean signUpUser(SignUpUserBody body)
	{
		ArrayList<Object> data= new ArrayList<Object>();
		SendOtpService sendOtpService = new SendOtpService(apiObj);
		boolean otp= sendOtpService.sendOtp(body);
		if(otp)
		{
		Response response = apiObj.buildNewRequest(signUpUserUrl, RequestType.POST).setContentType(ContentType.JSON).useRelaxedHTTPSValidation().setRequestBody(body).perform();
			 /*Response response=RestAssured.given().relaxedHTTPSValidation()
			            .contentType("application/json")
			           .body(body).when().post("https://stage.saudiesports.sa/api/team-club/v1/public/club-owner-request/signup");*/
		/*System.out.println("Response is "+response.asPrettyString());
		return response.getStatusCode()==200;
		}
		
		return false;
	}*/
	
	
}
