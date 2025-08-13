package stc.esport.api.business;

import java.util.ArrayList;
import java.util.Properties;

import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT.Validations;

import stc.esport.api.pojo.userprofile.UserProfileRequestBody;
import stc.esport.api.pojo.userprofile.UserProfileResponseBody;
import stc.esport.api.services.LoginService;
import stc.esport.api.services.UserProfileService;

public class UserProfile {

	private ArrayList<String> authorities;
		
	public Properties properties;
	
	LoginService loginServiceObj;
	
	 UserProfileService userProfileServiceObj;
	
	public UserProfile(RestActions apiObj, Properties properties) {
		this.properties = properties;
	    loginServiceObj=new LoginService(properties);
	    userProfileServiceObj= new UserProfileService(apiObj);
	}

	
	public ArrayList<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(ArrayList<String> authorities) {
		this.authorities = authorities;
	}

  
	 public boolean prepareUserProfile(String profileName, ArrayList<String> newAuthorities, boolean changeProfileAuth ) {
	    	//login by admin user
		    String admin = properties.getProperty("superAdmin");
			String adminPw = properties.getProperty("superAdminPW");
			String token = loginServiceObj.login(admin,adminPw);
			
			UserProfileResponseBody profileResponseBody =null;
			UserProfileRequestBody profileRequestBody = new UserProfileRequestBody();
			    
			// get profile Details
			   profileResponseBody= userProfileServiceObj.getProfileDetails(token, profileName);
			   Validations.assertThat().object(profileResponseBody).isNotNull().perform();
			   
			  if(changeProfileAuth) {
				  //save old profile authorities
				 setAuthorities(profileResponseBody.getAuthorities());
			  }
			 
			   //create profile request body 
			   profileRequestBody.setId(profileResponseBody.getId());
			   profileRequestBody.setLevelId(profileResponseBody.getLevelId());			
			   profileRequestBody.setName(profileResponseBody.getName());
	           profileRequestBody.setNameAr(profileResponseBody.getNameAr());
	           profileRequestBody.setSignUp(profileResponseBody.isSignUp());
	           profileRequestBody.setInvitationsEnabled(profileResponseBody.isInvitationsEnabled());
			   
			 if(changeProfileAuth) {
			    profileRequestBody.setAuthorities(newAuthorities);
			 }else{
				 if(getAuthorities()!=null)
				  profileRequestBody.setAuthorities(getAuthorities());
				 else 
					 profileRequestBody.setAuthorities(profileResponseBody.getAuthorities());
				
			 }
			
			  //update profile details
			 return	userProfileServiceObj.updateProfileDetails(token,profileRequestBody);
		} 
	    
}
