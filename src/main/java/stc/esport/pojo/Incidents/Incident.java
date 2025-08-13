package stc.esport.pojo.Incidents;

import java.util.ArrayList;
import java.util.Properties;

import stc.esport.utils.Utils;

public class Incident {

	//variables 
    public Properties properties;
    private String incidentType;
    private String onBehalf;
    private String plantiffName;
    private String plantiffNationality;
    private String plantiffIdType;
    private String plantiffIdNumber;
    private String plantiffCity;
    private String plantiffDistrict;
    private String lawyerName;
    private String lawyerNationality;
    private String lawyerEntity;
    private String lawyerMobileNum;
    private String lawyerMail;
    private String lawyerIdType;
    private String lawyerIdNumber;
    private String lawyerAttorneyNumber;
    private String lawyerAttorneyDate;
    private String lawyerAttorneyExpiryDate;
    private String lawyerAttorneyIssuer;
    private String lawyerCity;
    private String lawyerDistrict;
    private String ComplaintSummary;
    private String LegalEvidenceDoc;
    private String LegalEvidenceDesc;
    private String requests;
    private String GeneralAttachmentsDoc;
    private String GeneralAttachmentsDesc;
    
	private ArrayList<Defendants> defendant;
    
    public Incident(Properties properties) {
        this.properties = properties;
    }

   
	public String getIncidentType() {
		return incidentType;
	}
	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}


	public String getOnBehalf() {
		return onBehalf;
	}
	public void setOnBehalf(String onBehalf) {
		this.onBehalf = onBehalf;
	}


	public String getPlantiffName() {
		if(plantiffName==null) {	
			this.plantiffName= "Plantiff " +Utils.getRandomStringORNum(5,"String");

			}
		return plantiffName;
	}
	public void setPlantiffName(String plantiffName) {
		this.plantiffName = plantiffName;
	}


	public String getPlantiffNationality() {
		if(plantiffNationality==null) {
			 return properties.getProperty("incidentNationality");
		}
		return plantiffNationality;
	}
	public void setPlantiffNationality(String plantiffNationality) {
		this.plantiffNationality = plantiffNationality;
	}


	public String getPlantiffIdType() {
		if(plantiffIdType==null) {
			 return properties.getProperty("incidentIdType");
		}
		return plantiffIdType;
	}
	public void setPlantiffIdType(String plantiffIdType) {
		this.plantiffIdType = plantiffIdType;
	}


	public String getPlantiffIdNumber() {
		return plantiffIdNumber;
	}
	public void setPlantiffIdNumber(String plantiffIdNumber) {
		this.plantiffIdNumber = plantiffIdNumber;
	}


	public String getPlantiffCity() {
		if(plantiffCity==null) {
			 return properties.getProperty("incidentCity");
		}
		return plantiffCity;
	}
	public void setPlantiffCity(String plantiffCity) {
		this.plantiffCity = plantiffCity;
	}


	public String getPlantiffDistrict() {
		if(plantiffDistrict==null) {
			 return properties.getProperty("incidentDistrict");
		}
		return plantiffDistrict;
	}
	public void setPlantiffDistrict(String plantiffDistrict) {
		this.plantiffDistrict = plantiffDistrict;
	}


	public String getLawyerName() {
		if(lawyerName==null) {	
			this.lawyerName= "Lawyer " +Utils.getRandomStringORNum(5,"String");

			}
		return lawyerName;
	}
	public void setLawyerName(String lawyerName) {
		this.lawyerName = lawyerName;
	}


	public String getLawyerNationality() {
		if(lawyerNationality==null) {
			 return properties.getProperty("incidentNationality");
		}
		return lawyerNationality;
	}
	public void setLawyerNationality(String lawyerNationality) {
		this.lawyerNationality = lawyerNationality;
	}


	public String getLawyerEntity() {
		if(lawyerEntity==null) {
			 return properties.getProperty("incidentLawyerEntity");
		}
		return lawyerEntity;
	}
	public void setLawyerEntity(String lawyerEntity) {
		this.lawyerEntity = lawyerEntity;
	}


	public String getLawyerMobileNum() {
		if(lawyerMobileNum==null) {
			 return this.lawyerMobileNum=properties.getProperty("mobileStartWith")+Utils.getRandomStringORNum(7,"num");
		}
		return lawyerMobileNum;
	}
	public void setLawyerMobileNum(String lawyerMobileNum) {
		this.lawyerMobileNum = lawyerMobileNum;
	}


	public String getLawyerMail() {
		if(lawyerMail==null) {
			lawyerMail= Utils.generateRandomEmailAddress(properties.getProperty("fakedomain"));
    	}
		return lawyerMail;
	}
	public void setLawyerMail(String lawyerMail) {
		this.lawyerMail = lawyerMail;
	}


	public String getLawyerIdType() {
		if(lawyerIdType==null) {
			 return properties.getProperty("incidentIdType");
		}
		return lawyerIdType;
	}
	public void setLawyerIdType(String lawyerIdType) {
		this.lawyerIdType = lawyerIdType;
	}


	public String getLawyerIdNumber() {
		if(lawyerIdNumber==null) {
			 return this.lawyerIdNumber=Utils.getRandomStringORNum(5,"num");
		}
		return lawyerIdNumber;
	}
	public void setLawyerIdNumber(String lawyerIdNumber) {
		this.lawyerIdNumber = lawyerIdNumber;
	}


	public String getLawyerAttorneyNumber() {
		if(lawyerAttorneyNumber==null) {
			 return this.lawyerAttorneyNumber=Utils.getRandomStringORNum(5,"num");
		}
		return lawyerAttorneyNumber;
	}
	public void setLawyerAttorneyNumber(String lawyerAttorneyNumber) {
		this.lawyerAttorneyNumber = lawyerAttorneyNumber;
	}


	public String getLawyerAttorneyDate() {
		return lawyerAttorneyDate;
	}
	public void setLawyerAttorneyDate(String lawyerAttorneyDate) {
		this.lawyerAttorneyDate = lawyerAttorneyDate;
	}


	public String getLawyerAttorneyExpiryDate() {
		return lawyerAttorneyExpiryDate;
	}
	public void setLawyerAttorneyExpiryDate(String lawyerAttorneyExpiryDate) {
		this.lawyerAttorneyExpiryDate = lawyerAttorneyExpiryDate;
	}


	public String getLawyerAttorneyIssuer() {
		if(lawyerAttorneyIssuer==null) {
			 return this.lawyerAttorneyIssuer=Utils.getRandomStringORNum(5,"String");
		}
		return lawyerAttorneyIssuer;
	}
	public void setLawyerAttorneyIssuer(String lawyerAttorneyIssuer) {
		this.lawyerAttorneyIssuer = lawyerAttorneyIssuer;
	}


	public String getLawyerCity() {
		if(lawyerCity==null) {
			 return properties.getProperty("incidentCity");
		}
		return lawyerCity;
	}
	public void setLawyerCity(String lawyerCity) {
		this.lawyerCity = lawyerCity;
	}


	public String getLawyerDistrict() {
		if(lawyerDistrict==null) {
			 return properties.getProperty("incidentDistrict");
		}
		return lawyerDistrict;
	}
	public void setLawyerDistrict(String lawyerDistrict) {
		this.lawyerDistrict = lawyerDistrict;
	}


	public String getComplaintSummary() {
		if(ComplaintSummary==null) {
			 return properties.getProperty("incidentCompliansummary");
		}
		return ComplaintSummary;
	}
	public void setComplaintSummary(String complaintSummary) {
		ComplaintSummary = complaintSummary;
	}


	public String getLegalEvidenceDoc() {
		if(LegalEvidenceDoc==null) {
			 return properties.getProperty("incidentAttachments");
		}
		return LegalEvidenceDoc;
	}
	public void setLegalEvidenceDoc(String legalEvidenceDoc) {
		LegalEvidenceDoc = legalEvidenceDoc;
	}


	public String getLegalEvidenceDesc() {
		if(LegalEvidenceDesc==null) {
			 return properties.getProperty("incidentDocDescription");
		}
		return LegalEvidenceDesc;
	}
	public void setLegalEvidenceDesc(String legalEvidenceDesc) {
		LegalEvidenceDesc = legalEvidenceDesc;
	}


	public String getRequests() {
		if(requests==null) {
			 return properties.getProperty("incidentRequest");
		}
		return requests;
	}
	public void setRequests(String requests) {
		this.requests = requests;
	}


	public String getGeneralAttachmentsDoc() {
		if(GeneralAttachmentsDoc==null) {
			 return properties.getProperty("incidentAttachments");
		}
		return GeneralAttachmentsDoc;
	}
	public void setGeneralAttachmentsDoc(String generalAttachmentsDoc) {
		GeneralAttachmentsDoc = generalAttachmentsDoc;
	}


	public String getGeneralAttachmentsDesc() {
		if(GeneralAttachmentsDesc==null) {
			 return properties.getProperty("incidentDocDescription");
		}
		return GeneralAttachmentsDesc;
	}
	public void setGeneralAttachmentsDesc(String generalAttachmentsDesc) {
		GeneralAttachmentsDesc = generalAttachmentsDesc;
	}


	public ArrayList<Defendants> getDefendant() {
		return defendant;
	}
	public void setDefendant(ArrayList<Defendants> defendant) {
		this.defendant = defendant;
	}

	
	

    
   
}
