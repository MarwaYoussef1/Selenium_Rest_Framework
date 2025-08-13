package stc.esport.pojo;

import java.util.Properties;

import stc.esport.utils.Utils;

public class User {

	 
    public Properties properties;
    private String name;
    private String email;
    private String mobileNumber;
    private String nationality;
    private String gender;
    private String type;
    private String profile;
    private String region;
    private String subRegion;
    
    public User( Properties properties) {
        this.properties = properties;
    }
    
  
    
    public String getName() {
    	if (name==null)
		  {
		  return properties.getProperty("userFullName")+Utils.getRandomStringORNum(5, "String");
		  }
		
		return name;	
		
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobileNumber() {
		if (mobileNumber==null)
		  {
		  return properties.getProperty("userMobileNo")+Utils.getRandomStringORNum(7,"num");
		  }
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}



	public String getNationality() {
		if (nationality==null)
		  {
		  return " "+ properties.getProperty("userNationality")+" ";
		  }
		return nationality;
	}



	public void setNationality(String nationality) {
		this.nationality = nationality;
	}



	public String getGender() {
		if (gender==null)
		  {
		  return properties.getProperty("userDefaultGender");
		  }
		return gender;
	}



	public void setGender(String gender) {
		
		this.gender = gender;
	}



	public String getType() {
		if (type==null)
		  {
		  return properties.getProperty("userDefaultType");
		  }
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getProfile() {
		if (profile==null)
		  {
		  return " "+ properties.getProperty("userProfile")+" ";
		  }
		return profile;
	}



	public void setProfile(String profile) {
		this.profile = profile;
	}



	public String getRegion() {
		if (region==null)
		  {
		  return " "+ properties.getProperty("userRegion")+" ";
		  }
		return region;
	}



	public void setRegion(String region) {
		this.region = region;
	}



	public String getSubRegion() {
		if (subRegion==null)
		  {
		  return " "+ properties.getProperty("userSubRegion")+" ";
		  }
		return subRegion;
	}



	public void setSubRegion(String subRegion) {
		this.subRegion = subRegion;
	}

	
}
