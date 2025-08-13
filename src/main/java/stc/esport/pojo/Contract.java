package stc.esport.pojo;

import java.util.Properties;

import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class Contract {
	
	 //variables 
    public Properties properties;
    public String fullNameInAR;
    public String fullNameInEN;
    public String gender;
    public String status;
    public String nationality;
    public String city;
    public String dateOfBirth;
    public String idType;
    public String idAttachement;
    public String idNumber;
    public String parentApproval;
    public String saudiEleague;
    public String contractStartDate;
    public String contractEndDate;
    public String salary;
    public String uploadContract;
    public String creationDate;
    public String memberAge;
    public String contractPeriod;
   
    
    
    public Contract(Properties properties) {
        this.properties = properties;
    }



	public String getFullNameInAR() {
		if(fullNameInAR==null) {
			this.fullNameInAR= "عقد" + " "+Utils.getRandomArabicString(5);
	    	}
			return fullNameInAR;
	}
	public void setFullNameInAR(String fullNameInAR) {
		this.fullNameInAR = fullNameInAR;
	}



	public String getFullNameInEN() {
		if(fullNameInEN==null) {	
			this.fullNameInEN= "Contract " +Utils.getRandomStringORNum(5,"String");

			}
		return fullNameInEN;
	}
	public void setFullNameInEN(String fullNameInEN) {
		this.fullNameInEN = fullNameInEN;
	}



	public String getGender() {
		if(gender==null) {
			 return Constants.FEMALEGENDER;
		}
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}



	public String getStatus() {
		if(status==null) {
			 return properties.getProperty("memberStatus");
		}
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}



	public String getNationality() {
		if(nationality==null) {
			 return properties.getProperty("contractNationality");
		}
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	
	
	public String getCity() {
		if(city==null) {
			 return properties.getProperty("contractCity");
		}
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}


	public String getDateOfBirth() {
		if(dateOfBirth==null) {
			 return properties.getProperty("DOBMoreThan16");
		}
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}



	public String getIdType() {
		if(idType==null) {
			 return properties.getProperty("contractIdType");
		}
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}



	public String getIdAttachement() {
		if(idAttachement==null) {
			 return properties.getProperty("contractIdAttachmenet");
		}
		return idAttachement;
	}
	public void setIdAttachement(String idAttachement) {
		this.idAttachement = idAttachement;
	}



	public String getParentApproval() {
		if(parentApproval==null) {
			 return properties.getProperty("contractParentApproval");
		}
		return parentApproval;
	}
	public void setParentApproval(String parentApproval) {
		this.parentApproval = parentApproval;
	}



	public String getIdNumber() {
		if(idNumber==null) {
			 return properties.getProperty("contractIdNumber");
		}
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}



	public String getSaudiEleague() {
		if(saudiEleague==null) {
			 return properties.getProperty("saudiEleague");
		}
		return saudiEleague;
	}
	public void setSaudiEleague(String saudiEleague) {
		this.saudiEleague = saudiEleague;
	}



	public String getContractStartDate() {
		if(contractStartDate==null) {
			 return Utils.getCurrentDate("dd/MM/yyyy");
		}
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}



	public String getContractEndDate() {
		if(contractEndDate==null) {
		 return Utils.getTomorrowDate();
		}
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}



	public String getSalary() {
		if(salary==null) {
			 return properties.getProperty("salary");
		}
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}



	public String getUploadContract() {
		if(uploadContract==null) {
			 return properties.getProperty("uploadContract");
		}
		return uploadContract;
	}
	public void setUploadContract(String uploadContract) {
		this.uploadContract = uploadContract;
	}



	public String getCreationDate() {
		if(creationDate==null) {
			 return Utils.getCurrentDate("dd/MM/yyyy");
		}
		   return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}



	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}



	public String getMemberAge() {
		
		return memberAge;
	}
	public void setMemberAge(String memberAge) {
		this.memberAge = memberAge;
	}
    
    

}
