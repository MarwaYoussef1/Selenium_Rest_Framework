package stc.esport.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpUserData {

	
	@JsonProperty
	private String countryCode;
	
	@JsonProperty
	private String mobileNumber;
	
    @JsonProperty
	private String email;
	
		
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String password;
	
	@JsonProperty
	private String gender;
	
	@JsonProperty
	private int countryId;
	
	@JsonProperty
	private String dateOfBirth;
	
	@JsonProperty
	private String profileType;
	
	@JsonProperty
	private String profileName;
	
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getCountryCode() {
		return countryCode;
		
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
		
	}

	public String getMobileNumber() {
		return mobileNumber;
		//return "545698764";
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
		
	}

	public String getEmail() {
		return email;
		//return "yasmine345@clubowner.com";
	}

	public void setEmail(String email) {
		this.email = email;
		
	}

	
	public String getName() {
		return name;
		
	}

	public void setName(String name) {
		this.name = name;
		
	}

	public String getPassword() {
		return password;
		
	}

	public void setPassword(String password) {
		this.password = password;
		
	}

	public String getGender() {
		return gender;
		
	}

	public void setGender(String gender) {
		this.gender = gender;
		
	}

	public int getCountryId() {
		return countryId;
		
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
		
	}


	
	

}
