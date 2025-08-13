package stc.esport.pages.incident;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pojo.Incidents.Incident;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class SubmitIncidentPage extends PageBase{

	WebDriver driver;
	Properties properties;
	
	// identifier
	  
	   By incidentType= By.xpath("//ng-select[@id='type']//div[@class='ng-input']");
	   By onBehalf= By.id("onBehalf");
	   
	   //plaintiff fields
	   By plaintiffName= By.xpath("//app-issuer-form//input[@formcontrolname='fullName']");
	   By plaintiffIdType=By.xpath("//app-issuer-form//ng-select[@id='idType']");
	   By plaintiffIdNum= By.xpath("//app-issuer-form//input[@id='nationalId']");
	   By plaintiffLabelIdNum= By.xpath("//label[@for='nationalId']//following::div[contains(@class,'labeled-field')]");
	   By plaintiffNationality= By.xpath("//app-issuer-form//ng-select[@id='nationalityId']//input");
	   By plaintiffLabelNationality=By.xpath("//app-issuer-form//ng-select[@id='nationalityId']");
	   By plaintiffCity= By.xpath("//app-issuer-form//ng-select[@id='cityId']//input");
	   By plaintiffDistrict= By.xpath("//app-issuer-form//ng-select[@id='districtId']//input");
	   
	   //lawyer and representive fields
	   By lawyerToggle= By.xpath("//input[@role='switch']");
	   By lawyerName= By.xpath("//app-representative-form//input[@formcontrolname='fullName']");
	   By lawyerNationality= By.xpath("//app-representative-form//ng-select[@id='nationalityId']//input");
	   By lawyerEntity= By.xpath("//app-representative-form//ng-select[@id='userType']//input");
	   By lawyerMobileNum= By.xpath("//app-representative-form//input[@id='mobile']");
	   By lawyerMail= By.xpath("//app-representative-form//input[@id='email']");
	   By lawyerIdType= By.xpath("//app-representative-form//ng-select[@id='idType']//div[@class='ng-input']");
	   By lawyerIdNum= By.xpath("//app-representative-form//input[@id='nationalId']");
	   By lawyerAttornyNum= By.id("attorneyNumber");
	   By lawyerAttornyDate= By.id("attorneyDate");
	   By lawyerAttornyExpireDate= By.id("attorneyExpiryDate");
	   By lawyerAttornyIssuer= By.xpath("//input[@formcontrolname='attorneyIssuer']");
	   By lawyerCity= By.xpath("//app-representative-form//ng-select[@id='cityId']//input");
	   By lawyerDistrict= By.xpath("//app-representative-form//ng-select[@id='districtId']//input");
	   
	   //defendant fields
	   By defendantEntity= By.xpath("//div[contains(@class,'defendant')]//ng-select[@id='userType' and contains(@class,'ng-invalid')]//input");
	   By defendantClubOwner= By.xpath("//app-select-club[@formcontrolname='clubSystemId']");
	   By defendantName= By.xpath("//div[contains(@class,'defendant')]//input[@formcontrolname='fullName']");
	   By defendantMail= By.xpath("//div[contains(@class,'defendant')]//input[@id='email']");
	   By defendantNationality= By.xpath("//div[contains(@class,'defendant')]//ng-select[@id='nationalityId' and contains(@class,'ng-invalid')]//input");
	   By defendantMobile= By.xpath("//div[contains(@class,'defendant')]//input[@id='mobile']");
	   By defendantIdType=By.xpath("//div[contains(@class,'defendant')]//ng-select[@id='idType' and contains(@class,'ng-invalid')]//div[@class='ng-input']");
	   By defendantIdNum= By.xpath("//div[contains(@class,'defendant')]//input[@id='nationalId']");
	   By defendantCity= By.xpath("//div[contains(@class,'defendant')]//ng-select[@id='cityId'and contains(@class,'ng-invalid')]//input");
	   By defendantDistrict= By.xpath("//div[contains(@class,'defendant')]//ng-select[@id='districtId'and contains(@class,'ng-invalid') ]//input");
	   By addMoreDefendant= By.xpath("(//button[contains(@class,'add-item-btn')])[1]");
	   
	   //incident details
	   By complainSummary= By.id("summary");
	   By incidentAttachment=By.xpath("//app-details-form//input[@id='incidentAttachment']");
	   By attachmentDesc= By.xpath("//app-details-form//input[@placeholder='Description']");
	   By requests= By.id("i");
	   By incidentGeneralAttachment= By.xpath("//app-general-attachments-form//input[@id='incidentAttachment']");
	   By incidentGeneralAttachmentDesc= By.xpath("//app-general-attachments-form//input[@formcontrolname='description']");
	   
	   By acceptTerms= By.xpath("//mat-checkbox[@formcontrolname='acceptTerms']");
	   By submitBtn= By.xpath("//button[contains(@class,'btn-info')]");
	   
	   By successToast = By.xpath("//div[contains(@id,'html-container')]");
	
	
	
	// Constractor
		public SubmitIncidentPage(WebDriver driver, Properties properties) {
			this.driver = driver;
			this.properties = properties;
		}
	
		private void selectNationality(String nationality, String role) {
			if (role.equals(Constants.PLANTIFF)) {
			   driver.element().type(plaintiffNationality, nationality);
			   driver.element().keyPress(plaintiffNationality, Keys.ENTER);
			}
		    else if(role.equals(Constants.LAWYER)) {
				driver.element().type(lawyerNationality, nationality);
				driver.element().keyPress(lawyerNationality, Keys.ENTER);
			}
		    else if((role.equals(Constants.DEFENDANT))) {
		    	driver.element().type(defendantNationality, nationality);
				driver.element().keyPress(defendantNationality, Keys.ENTER);
		    }
		}
		

		private void provideIdType(String type, String role) {
			 By optionLocator= By.xpath("//div[@role='option' and text()="+"' "+type+" '"+"]");

			if (role.equals(Constants.PLANTIFF)) {
			driver.element().click(plaintiffIdType);
            driver.element().click(optionLocator);
         //   driver.element().type(plaintiffIdNum, idNumber);
			}
			  else if(role.equals(Constants.LAWYER)) {
				  driver.element().click(lawyerIdType);
		          driver.element().click(optionLocator);
		    //      driver.element().type(lawyerIdNum, idNumber);
			  }
			  else if((role.equals(Constants.DEFENDANT))) {
				  driver.element().click(defendantIdType);
		          driver.element().click(optionLocator);
		      //    driver.element().type(defendantIdNum, idNumber);
			  }
		}
		
		private void selectOnBehalf(String option) {
			 By optionLocator= By.xpath("//div[@role='option' and text()="+"' "+option+" '"+"]");
			    driver.element().click(onBehalf);
	            driver.element().click(optionLocator);
		}
		
		private void selectCityAndDistrict(String city, String district, String role) {
			if (role.equals(Constants.PLANTIFF)) {
				driver.element().type(plaintiffCity, city);
				driver.element().keyPress(plaintiffCity, Keys.ENTER);
				
				driver.element().type(plaintiffDistrict, district);
				driver.element().keyPress(plaintiffDistrict, Keys.ENTER);
			}
			 else if(role.equals(Constants.LAWYER)) {
				    driver.element().type(lawyerCity, city);
					driver.element().keyPress(lawyerCity, Keys.ENTER);
					
					driver.element().type(lawyerDistrict, district);
					driver.element().keyPress(lawyerDistrict, Keys.ENTER); 
			 }
			 else if(role.equals(Constants.DEFENDANT)) {
				    driver.element().type(defendantCity, city);
					driver.element().keyPress(defendantCity, Keys.ENTER);
					
					driver.element().type(defendantDistrict, district);
					driver.element().keyPress(defendantDistrict, Keys.ENTER); 
			 }
		}
		
		public void selectIncidnetType(String type) {
			 By optionLocator= By.xpath("//div[@role='option' and text()="+"' "+type+" '"+"]");

			 driver.element().click(incidentType);
	         driver.element().click(optionLocator);;
		}
		
		private void selectDefendantEntity(String entityName) {
			driver.element().type(defendantEntity, entityName);
			driver.element().keyPress(defendantEntity,Keys.ENTER);
		}
		
		private void selectLawyerEntity(String entityName) {
			driver.element().type(lawyerEntity, entityName);
			driver.element().keyPress(lawyerEntity,Keys.ENTER);
		}
		
		private void selectAttornyExpiryDate() {
			By dayLocator= By.xpath("//div[contains(@class,'calendar-body') and text()="+"' "+Utils.getTodayDay()+" '"+"]");
			driver.element().click(lawyerAttornyExpireDate);
			driver.element().click(dayLocator);
		}
		
		private void selectAttornyDate() {
			By dayLocator= By.xpath("//div[contains(@class,'calendar-body') and text()="+"' "+Utils.getTodayDay()+" '"+"]");
			driver.element().click(lawyerAttornyDate);
			driver.element().click(dayLocator);
		}
		
		private void uploadFiles(String fileName, By uploadLocator) {
			uploadFile(driver,fileName, uploadLocator);
			waitForPageLoadWithoutSpinner(driver);
		}
		
		private boolean checkFieldIFDisabled(By locator) {
			String attributeValue= driver.element().getAttribute(locator, "class");
			if (attributeValue.contains("disabled")) {
				return false;
			}
			return true;
		}
		
		public void addDefendantInfo(Incident incident) {

		        for(int i=0; i<incident.getDefendant().size(); i++) {
		        	selectDefendantEntity(incident.getDefendant().get(i).getDefendantEntity());
		        	
		        	List<WebElement> DefendantNames = driver.getDriver().findElements(defendantName);
		        	List<WebElement> DefendantMobiles = driver.getDriver().findElements(defendantMobile);
		        	List<WebElement> DefendantMails = driver.getDriver().findElements( defendantMail);

		        	DefendantNames.get(i).sendKeys(incident.getDefendant().get(i).getDefendantName());
		        	DefendantMobiles.get(i).sendKeys(incident.getDefendant().get(i).getDefendantMobileNumber());
		        	DefendantMails.get(i).sendKeys(incident.getDefendant().get(i).getdefendantMail());
		        	
					provideIdType(incident.getDefendant().get(i).getDefendantIdType(),Constants.DEFENDANT);
		        	List<WebElement> DefendantIDNumbers = driver.getDriver().findElements( defendantIdNum);
					DefendantIDNumbers.get(i).sendKeys(incident.getDefendant().get(i).getDefendantIdNumber());
					 
			selectNationality(incident.getDefendant().get(i).getDefendantNationality(), Constants.DEFENDANT);
			selectCityAndDistrict(incident.getDefendant().get(i).getDefendantCity(), incident.getDefendant().get(i).getDefendantDistrict(), Constants.DEFENDANT);
			       
			if(i+1 <incident.getDefendant().size()) {
		            	driver.element().click(addMoreDefendant);
		            }
		        }
		}
		
		
		private boolean isCheckBoxChecked(By locator) {
			String attributeValue= driver.element().getAttribute(locator, "aria-checked");
			if (attributeValue.contains("true")) {
				return false;
			}
			return true;
		}
		
		private void addLawyer(Incident incident) {
			if(isCheckBoxChecked(lawyerToggle)) {
			driver.element().click(lawyerToggle);
			}
			
			driver.element().type(lawyerName, incident.getLawyerName());
			selectNationality(incident.getLawyerNationality(), Constants.LAWYER);
			selectLawyerEntity(incident.getLawyerEntity());
			driver.element().type(lawyerMobileNum, incident.getLawyerMobileNum());
			driver.element().type(lawyerMail, incident.getLawyerMail());
			provideIdType(incident.getLawyerIdType(),Constants.LAWYER);
			driver.element().type(lawyerIdNum, incident.getLawyerIdNumber());
			driver.element().type(lawyerAttornyNum, incident.getLawyerAttorneyNumber());
			driver.element().type(lawyerAttornyIssuer, incident.getLawyerAttorneyIssuer());
			selectAttornyExpiryDate();
			selectAttornyDate();
			selectCityAndDistrict(incident.getLawyerCity(), incident.getLawyerDistrict(), Constants.LAWYER);
		}
		
		private void addIncidentDetails(Incident incident) {
			driver.element().type(complainSummary, incident.getComplaintSummary());
			uploadFiles(incident.getLegalEvidenceDoc(),incidentAttachment);
			driver.element().type(attachmentDesc,incident.getLegalEvidenceDesc() );
			driver.element().type(requests, incident.getRequests());
			uploadFiles(incident.getGeneralAttachmentsDoc(),incidentGeneralAttachment);
			driver.element().type(incidentGeneralAttachmentDesc,incident.getGeneralAttachmentsDesc() );
		}
		
		private void addPlaintiff(Incident incident, String role) {
			if(!role.equals(Constants.CLUBOWNERINCIDENT)) {
				driver.element().type(plaintiffName, incident.getPlantiffName());
				
				if(checkFieldIFDisabled(plaintiffIdType)) {
					provideIdType(incident.getPlantiffIdType(),Constants.PLANTIFF);
				}
				
				if(!isElementExist(driver, plaintiffLabelIdNum, "plaintiff id num")) {
				driver.element().type(plaintiffIdNum, incident.getPlantiffIdNumber());
				}
			}
			
		    if(checkFieldIFDisabled(plaintiffLabelNationality)) {
				selectNationality(incident.getPlantiffNationality(), Constants.PLANTIFF);
			}
			selectCityAndDistrict(incident.getLawyerCity(), incident.getLawyerDistrict(), Constants.PLANTIFF);	
		}
		
		public boolean submitIncident(Incident incident, String role) {
		String incidentSubmitSuccessMsg = properties.getProperty("incidnetSuccessMsg");
		selectIncidnetType(incident.getIncidentType());

		if(!role.equals(Constants.PLAYERINCIDENT)) {
			selectOnBehalf(incident.getOnBehalf());
		}
		addPlaintiff(incident, role);
		addLawyer(incident);
		addDefendantInfo(incident);
		addIncidentDetails(incident);
		
		driver.element().click(acceptTerms);
		driver.element().click(submitBtn);
		
		 return isMsgExistandDisplayed(driver, successToast,incidentSubmitSuccessMsg );
		}
		
		
		private boolean checkPlantiffNameWithOnbehalfOptions(String name, String onbehalfOption) {
			By nameLocator= By.xpath("//div[contains(@class,'labeled') and text()="+"' "+name+" '"+"]");
			selectOnBehalf(onbehalfOption);
			if(isElementExist(driver, nameLocator, name)) {
				return true;
			}
		return false;
		}
		
		public boolean checkSEFOnbehalf() {
			selectIncidnetType(Constants.COMPLAININCIDENT);

			String SEFplanitiffName = properties.getProperty("SEFplanitiffName");
			String CommitteePlaintiffName= properties.getProperty("CommitteePlaintiffName");
			
			if(
			checkPlantiffNameWithOnbehalfOptions(SEFplanitiffName, Constants.SEF_ONBEHALF)&&
			checkPlantiffNameWithOnbehalfOptions(CommitteePlaintiffName, Constants.DISCIPLINARYCOMMITTEE_ONBEHALF)) {
				return true;
			}
			return false;
		}
		
		public boolean checkclubOwnerOnbehalf() {
			selectIncidnetType(Constants.COMPLAININCIDENT);
			
			String clubNameEn = properties.getProperty("clubNameEn");
			String clubOwnerName= properties.getProperty("clubOwnerName");
			
			if(
			checkPlantiffNameWithOnbehalfOptions(clubOwnerName, Constants.MYSELF_ONBEHALF)&&
			checkPlantiffNameWithOnbehalfOptions(clubNameEn, Constants.CLUB_ONBEHALF)) {
				return true;
			}
			return false;

		}
		
	
}
