package stc.esport.api.pojo.incident;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Issuer {

	
	@JsonProperty
	private String userType;	
	@JsonProperty
	private String fullName;	
	@JsonProperty
	private String nationalId;	
	@JsonProperty
	private String idType;	
	@JsonProperty
	private int nationalityId;	
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
	
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	public NationalAddress getNationalAddress() {
		return nationalAddress;
	}
	public void setNationalAddress(NationalAddress nationalAddress) {
		this.nationalAddress = nationalAddress;
	}
	public String getNationalId() {
		return nationalId;
	}
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	public int getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(int nationalityId) {
		this.nationalityId = nationalityId;
	}
	
	
	
	
	
	
	
}
