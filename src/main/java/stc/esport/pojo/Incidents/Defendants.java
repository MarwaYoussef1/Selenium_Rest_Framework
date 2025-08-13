package stc.esport.pojo.Incidents;

import java.util.Properties;

import stc.esport.utils.Utils;

public class Defendants {
	
	//variables 
        public Properties properties;
	    private String defendantEntity;
	    private String defendantName;
	    private String defendantMail;
	    private String defendantNationality;
	    private String defendantMobileNumber;
	    private String defendantIdType;
	    private String defendantIdNumber;
	    private String defendantCity;
	    private String defendantDistrict;
	    
	    public Defendants(Properties properties) {
	        this.properties = properties;
	    }
	    
	    public String getDefendantEntity() {
			if(defendantEntity==null) {
				 return properties.getProperty("incidentDefendantType");
			}
			return defendantEntity;
		}
		public void setDefendantEntity(String defendantEntity) {
			this.defendantEntity = defendantEntity;
		}


		public String getDefendantName() {
			if(defendantName==null) {	
				this.defendantName= "Defendant " +Utils.getRandomStringORNum(5,"String");
				}
			return defendantName;
		}
		public void setDefendantName(String defendantName) {
			this.defendantName = defendantName;
		}


		public String getdefendantMail() {
			if(defendantMail==null) {
				defendantMail= Utils.generateRandomEmailAddress(properties.getProperty("fakedomain"));
	    	}
			return defendantMail;
		}
		public void setDefendantMai(String defendantMail) {
			this.defendantMail = defendantMail;
		}


		public String getDefendantNationality() {
			if(defendantNationality==null) {
				 return properties.getProperty("incidentNationality");
			}
			return defendantNationality;
		}
		public void setDefendantNationality(String defendantNationality) {
			this.defendantNationality = defendantNationality;
		}


		public String getDefendantMobileNumber() {
			if(defendantMobileNumber==null) {
				 return this.defendantMobileNumber= properties.getProperty("mobileStartWith")+Utils.getRandomStringORNum(7,"num");
			}
			return defendantMobileNumber;
		}
		public void setDefendantMobileNumber(String defendantMobileNumber) {
			this.defendantMobileNumber = defendantMobileNumber;
		}


		public String getDefendantIdType() {
			if(defendantIdType==null) {
				 return properties.getProperty("incidentIdType");
			}
			return defendantIdType;
		}
		public void setDefendantIdType(String defendantIdType) {
			this.defendantIdType = defendantIdType;
		}


		public String getDefendantIdNumber() {
			if(defendantIdNumber==null) {
				 return Utils.getRandomStringORNum(7,"num");
			}
			return defendantIdNumber;
		}
		public void setDefendantIdNumber(String defendantIdNumber) {
			this.defendantIdNumber = defendantIdNumber;
		}


		public String getDefendantCity() {
			if(defendantCity==null) {
				 return properties.getProperty("incidentCity");
			}
			return defendantCity;
		}
		public void setDefendantCity(String defendantCity) {
			this.defendantCity = defendantCity;
		}


		public String getDefendantDistrict() {
			if(defendantDistrict==null) {
				 return properties.getProperty("incidentDistrict");
			}
			return defendantDistrict;
		}
		public void setDefendantDistrict(String defendantDistrict) {
			this.defendantDistrict = defendantDistrict;
		}

}
