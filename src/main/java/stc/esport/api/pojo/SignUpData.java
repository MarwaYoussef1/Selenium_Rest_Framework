package stc.esport.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpData {

	
	@JsonProperty
	private String countryCode;
	
	@JsonProperty
	private String mobileNumber;
	
    @JsonProperty
	private String email;
	
	@JsonProperty
	private String clubIBAN;
	
	@JsonProperty
	private String nationalId;
	
	@JsonProperty
	private String crNumber;
	
	@JsonProperty
	private String clubName;
	
	@JsonProperty
	private String clubNameAr;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String password;
	
	@JsonProperty
	private String gender;
	
	@JsonProperty
	private int countryId;
	
	@JsonProperty
	private String nationalIdFile;
	
	@JsonProperty
	private String clubLogo;
	
	@JsonProperty
	private String crFile;
	
	@JsonProperty
	private String crEndDate;
	
	@JsonProperty
	private String clubIBANFile;
	
	@JsonProperty
	private boolean agreeTerms;

	public String getCountryCode() {
		return countryCode;
		//return "+966";
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

	public String getClubIBAN() {
		return clubIBAN;
		//return "SA1100911713081235771543";
	}

	public void setClubIBAN(String clubIBAN) {
		this.clubIBAN = clubIBAN;
		
	}

	public String getNationalId() {
		return nationalId;
		//return "197111310";
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
		
	}

	public String getCrNumber() {
		return crNumber;
		//return "2214511156";
	}

	public void setCrNumber(String crNumber) {
		this.crNumber = crNumber;
		
	}

	public String getClubName() {
		return clubName;
		//return "club name automationnbb";
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
		
	}

	public String getClubNameAr() {
		return clubNameAr;
		//return "منتالببيي";
	}

	public void setClubNameAr(String clubNameAr) {
	this.clubNameAr = clubNameAr;
		
	}

	public String getName() {
		return name;
		//return "club autooxxx";
	}

	public void setName(String name) {
		this.name = name;
		
	}

	public String getPassword() {
		return password;
		//return "test!$1234";
	}

	public void setPassword(String password) {
		this.password = password;
		
	}

	public String getGender() {
		return gender;
		//return "MALE";
	}

	public void setGender(String gender) {
		this.gender = gender;
		
	}

	public int getCountryId() {
		return countryId;
		//return 58;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
		
	}

	public String getNationalIdFile() {
		return nationalIdFile;
		//return "esports/120a28e7-58ee-44ac-a368-4fabebdbe68c__SignUpPdf.pdf";
	}

	public void setNationalIdFile(String nationalIdFile) {
		this.nationalIdFile = nationalIdFile;
		
	}

	public String getClubLogo() {
		return clubLogo;
		//return "esports/4c5aa599-19d9-473b-ae6f-fa3fedf86c56__SignUpPng.png";
	}

	public void setClubLogo(String clubLogo) {
		this.clubLogo = clubLogo;
		
	}

	public String getCrFile() {
		return crFile;
		//return "esports/f16e07aa-0bc2-4737-9e2d-6fc352b1b9d9__SignUpPdf.pdf";
	}

	public void setCrFile(String crFile) {
		this.crFile = crFile;
		
	}

	public String getCrEndDate() {
		return crEndDate;
		//return "1704012080000";
	}

	public void setCrEndDate(String crEndDate) {
		this.crEndDate = crEndDate;
		
	}

	public String getClubIBANFile() {
		return clubIBANFile;
		//return "esports/84083cdd-26e0-4a87-b53b-388f71b69833__SignUpPdf.pdf";
	}

	public void setClubIBANFile(String clubIBANFile) {
		this.clubIBANFile = clubIBANFile;
		
	}

	public boolean isAgreeTerms() {
	return agreeTerms;
		
	}

	public void setAgreeTerms(boolean agreeTerms) {
		this.agreeTerms = agreeTerms;
	
	}
	
	

}
