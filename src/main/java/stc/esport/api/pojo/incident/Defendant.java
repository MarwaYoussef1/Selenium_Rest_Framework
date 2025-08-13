package stc.esport.api.pojo.incident;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Defendant {

	
	@JsonProperty
	private String userType;	
	@JsonProperty
	private String fullName;	
	@JsonProperty
	private int nationalityId;	
	@JsonProperty
	private String countryCode;	
	@JsonProperty
	private String mobileNumber;
	@JsonProperty
	private String email;	
	public int getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(int nationalityId) {
		this.nationalityId = nationalityId;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	@JsonProperty
	private String idType;	
	@JsonProperty
	private String nationalId;	
	@JsonProperty
	private NationalAddress nationalAddress;
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNationalId() {
		return nationalId;
	}
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	public NationalAddress getNationalAddress() {
		return nationalAddress;
	}
	public void setNationalAddress(NationalAddress nationalAddress) {
		this.nationalAddress = nationalAddress;
	}	
	
	
	
	
	
}
