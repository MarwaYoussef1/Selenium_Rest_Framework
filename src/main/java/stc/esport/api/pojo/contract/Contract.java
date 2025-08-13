package stc.esport.api.pojo.contract;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contract {

	
	@JsonProperty
	private String teamSystemId;	
	@JsonProperty
	private String memberSystemId;	
	@JsonProperty
	private String name;	
	@JsonProperty
	private String nameAr;	
	@JsonProperty
	private String gender;
	@JsonProperty
	private String memberStatusCode;	
	@JsonProperty
	private int countryId;	
	@JsonProperty
	private String birthDate;	
	@JsonProperty
	private String parentIdTypeCode;	
	@JsonProperty
	private String parentNationalId;
	@JsonProperty
	private String memberContractParentIdFile;	
	@JsonProperty
	private String memberContractParentApprovalFile;
	@JsonProperty
	private String idTypeCode;	
	@JsonProperty
	private String nationalId;	
	@JsonProperty
	private String memberContractIdFile;	
	@JsonProperty
	private boolean isSaudiEleague;	
	@JsonProperty
	private String fromDate;	
	@JsonProperty
	private String  toDate;
	@JsonProperty
	private String salary;	
	@JsonProperty
	private String  memberContractFile;	
	@JsonProperty
	private boolean  termsAccepted;
	@JsonProperty
	private String  reason;
	
	
	public String getTeamSystemId() {
		return teamSystemId;
	}
	public void setTeamSystemId(String teamSystemId) {
		this.teamSystemId = teamSystemId;
	}
	public String getMemberSystemId() {
		return memberSystemId;
	}
	public void setMemberSystemId(String memberSystemId) {
		this.memberSystemId = memberSystemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameAr() {
		return nameAr;
	}
	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMemberStatusCode() {
		return memberStatusCode;
	}
	public void setMemberStatusCode(String memberStatusCode) {
		this.memberStatusCode = memberStatusCode;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getIdTypeCode() {
		return idTypeCode;
	}
	public void setIdTypeCode(String idTypeCode) {
		this.idTypeCode = idTypeCode;
	}
	public String getNationalId() {
		return nationalId;
	}
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	
	public String getMemberContractParentIdFile() {
		return memberContractParentIdFile;
	}
	public void setMemberContractParentIdFile(String memberContractParentIdFile) {
		this.memberContractParentIdFile = memberContractParentIdFile;
	}
	public String getMemberContractParentApprovalFile() {
		return memberContractParentApprovalFile;
	}
	public void setMemberContractParentApprovalFile(String memberContractParentApprovalFile) {
		this.memberContractParentApprovalFile = memberContractParentApprovalFile;
	}
	
	public String getParentIdTypeCode() {
		return parentIdTypeCode;
	}
	public void setParentIdTypeCode(String parentIdTypeCode) {
		this.parentIdTypeCode = parentIdTypeCode;
	}
	public String getParentNationalId() {
		return parentNationalId;
	}
	public void setParentNationalId(String parentNationalId) {
		this.parentNationalId = parentNationalId;
	}
	
	public String getMemberContractIdFile() {
		return memberContractIdFile;
	}
	public void setMemberContractIdFile(String memberContractIdFile) {
		this.memberContractIdFile = memberContractIdFile;
	}
	public boolean isSaudiEleague() {
		return isSaudiEleague;
	}
	public void setSaudiEleague(boolean isSaudiEleague) {
		this.isSaudiEleague = isSaudiEleague;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getMemberContractFile() {
		return memberContractFile;
	}
	public void setMemberContractFile(String memberContractFile) {
		this.memberContractFile = memberContractFile;
	}
	public boolean isTermsAccepted() {
		return termsAccepted;
	}
	public void setTermsAccepted(boolean termsAccepted) {
		this.termsAccepted = termsAccepted;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
}
