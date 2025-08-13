package stc.esport.pojo;

import java.util.Properties;

import stc.esport.utils.Utils;

public class Account {
	
	  //variables 
    public Properties properties;
    private String userType;
    private String roleType;
    private String name;
    private String email;
    private String mobileCode;
    private String mobile;
    private String genderType;
    private String nationality;
    private String DOB;
    private String clubOwnerNationalID;
    private String clubOwnerNationalIDFile;
    private String clubOwnerEn;
    private String clubOwnerAr;
    private String clubLogo;
    private String crNumber;
    private String crDate;
    private String crFile;
    private String clubIBAN;
    private String clubIBANFile;
    private String createPassword;
    private String confirmPassword;
    private String otp;
    
    
    public Account(Properties properties) {
        this.properties = properties;
    }

    

	public String getUserType() {
		return userType;
	}



	public void setUserType(String userType) {
		this.userType = userType;
	}



	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	
	public String getName() {
		if(name==null) {
			name= Utils.getRandomStringORNum(5,"String");
    	}
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		if(email==null) {
			email= Utils.generateRandomEmailAddress(properties.getProperty("fakedomain"));
    	}
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getMobileCode() {
		if (mobileCode==null) {
			return properties.getProperty("saudiaMobileCode");
		}
		return mobileCode;
	}
	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}


	public String getMobile() {
		if(mobile==null) {
			mobile= properties.getProperty("mobileStartWith")+Utils.getRandomStringORNum(7,"num");
    	}
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getGenderType() {
		return genderType;
	}
	public void setGenderType(String genderType) {
		this.genderType = genderType;
	}


	public String getNationality() {
		if(nationality==null) {
			nationality= properties.getProperty("nationality");
    	}
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}


	public String getDOB() {
		if(DOB==null) {
			DOB= properties.getProperty("birthDay");
    	}
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}


	public String getClubOwnerNationalID() {
		if(clubOwnerNationalID==null) {
			clubOwnerNationalID= Utils.getRandomStringORNum(5,"int");
    	}
		return clubOwnerNationalID;
	}
	public void setClubOwnerNationalID(String clubOwnerNationalID) {
		this.clubOwnerNationalID = clubOwnerNationalID;
	}


	public String getClubOwnerNationalIDFile() {
		if (clubOwnerNationalIDFile==null) {
			clubOwnerNationalIDFile= properties.getProperty("clubnationalIDFile");
		}
		return clubOwnerNationalIDFile;
	}
	public void setClubOwnerNationalIDFile(String clubOwnerNationalIDFile) {
		this.clubOwnerNationalIDFile = clubOwnerNationalIDFile;
	}


	public String getClubOwnerEn() {
		if(clubOwnerEn==null) {
			clubOwnerEn= Utils.getRandomStringORNum(5,"String");
    	}
		return clubOwnerEn;
	}
	public void setClubOwnerEn(String clubOwnerEn) {
		this.clubOwnerEn = clubOwnerEn;
	}


	public String getClubOwnerAr(){
		if(clubOwnerAr==null) {	
		 clubOwnerAr="نادى" + " "+Utils.getRandomArabicString(8);
		}
		return clubOwnerAr;}
	public void setClubOwnerAr(String clubOwnerAr) {
		this.clubOwnerAr = clubOwnerAr;
	}


	public String getClubLogo() {
		if(clubLogo==null) {
			clubLogo= properties.getProperty("clubLogo");
		}
		return clubLogo;
	}
	public void setClubLogo(String clubLogo) {
		this.clubLogo = clubLogo;
	}


	public String getCrNumber() {
		if(crNumber==null) {
			crNumber= Utils.getRandomStringORNum(10,"num");
    	}
		return crNumber;
	}
	public void setCrNumber(String crNumber) {
		this.crNumber = crNumber;
	}


	public String getCrDate() {
		if(crDate==null) {
			crDate= properties.getProperty("crDate");
    	}
		return crDate;
	}
	public void setCrDate(String crDate) {
		this.crDate = crDate;
	}


	public String getCrFile() {
		if(crFile==null) {
			crFile=  properties.getProperty("clubCrFile");
		}
		return crFile;
	}
	public void setCrFile(String crFile) {
		this.crFile = crFile;
	}


	public String getClubIBAN() {
		if(clubIBAN==null) {
			clubIBAN= "SA"+Utils.getRandomStringORNum(22, "num");
    	}
		return clubIBAN;
	}
	public void setClubIBAN(String clubIBAN) {
		this.clubIBAN = clubIBAN;
	}


	public String getClubIBANFile() {
		if(clubIBANFile==null) {
			clubIBANFile=  properties.getProperty("clubIbanFile");
		}
		return clubIBANFile;
	}
	public void setClubIBANFile(String clubIBANFile) {
		this.clubIBANFile = clubIBANFile;
	}


	public String getCreatePassword() {
		if(createPassword==null) {
			createPassword= properties.getProperty("password");
		}
		return createPassword;
	}
	public void setCreatePassword(String createPassword) {
		this.createPassword = createPassword;
	}


	public String getConfirmPassword() {
		if(confirmPassword==null) {
			confirmPassword= properties.getProperty("password");
		}
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}



	public String getOtp() {
		if(otp==null) {
			otp= properties.getProperty("otp");
		}
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
    
    
	

}
