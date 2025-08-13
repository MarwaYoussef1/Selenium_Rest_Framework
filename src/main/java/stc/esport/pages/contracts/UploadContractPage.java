package stc.esport.pages.contracts;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.shaft.driver.SHAFT.GUI.WebDriver;


import stc.esport.base.PageBase;
import stc.esport.pojo.ContractRequest;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class UploadContractPage extends PageBase{

		public WebDriver driver;
		public Properties properties;
		
		
		// identifier
		By contractArName= By.id("nameAr");
		By contractEnName= By.id("name");
		By memberStatusList= By.xpath("//ng-select[@id='memberStatusCode']//input");
		By nationalityList= By.xpath("//ng-select[@id='countryId']//input");
		By cityList= By.xpath("//ng-select[@id='cityId']//input");
		By idTypeList= By.xpath("//ng-select[@id='idTypeCode']//input");
		By nationalId= By.id("nationalId");
		By iqamaNum= By.id("iqamaNumber");
		By passportNum= By.id("passportNumber");
		// check the below id
		By uploadIdAttachement= By.xpath("//input[@id='memberContractIdFile']");
		By parentIdType= By.xpath("//ng-select[@id='parentIdTypeCode']//input");
		By parentIqamaNum= By.id("parentIqamaNumber");
		By parentNationalId= By.id("parentNationalId");
		By parentPassportNum= By.id("parentPassportNumber");
		// check the below id's
		By parentIdAttachement= By.xpath("//input[@id='memberContractParentIdFile']");
		By parentApproval= By.xpath("//input[@id='memberContractParentApprovalFile']");
		By uploadContractFile= By.xpath("//input[@id='memberContractFile']");
		
		By femaleGender= By.xpath("//input[@value='FEMALE']");
		By maleGender= By.xpath("//input[@value='MALE']");
		By trueSaudiEleague= By.xpath("//input[@value='true']");
		By falseSaudiEleague= By.xpath("//input[@value='false']");
		By fromContractDay= By.xpath("//input[@id='fromDate']");
		By endContractDay= By.xpath("//input[@id='toDate']");
		By contractSalary= By.id("salary");
		By birthDate= By.id("birthDate");
	    By calendaryear=By.xpath("//span[contains(@id,'mat-calendar-button')]");
	    By previousYearsArrow=By.xpath("//button[contains(@aria-label,'Previous')]");
	    By nextYearArrow= By.xpath("//button[contains(@aria-label,'Next')]");
		By termsCheckBox= By.xpath("//mat-checkbox[@formcontrolname='termsAccepted']//input");
		By reason=By.xpath("//textarea[@formcontrolname='reason']");
		By saveBtn= By.xpath("//button[@type='submit']");
		By successToast = By.xpath("//div[contains(@id,'html-container')]");
		By updateNote=By.xpath("//*[contains(@class,'update-note')]");
		By status=By.xpath("//*[contains(@class,'status')]");

		
		// Constractor
		public UploadContractPage(WebDriver driver, Properties properties) {
			this.driver = driver;
			this.properties = properties;
		}
		
		
		private void selectGender(String genderType) {
			if(genderType.equals("MALEGENDER"))
			    driver.element().click(maleGender);
			else {
			    driver.element().click(femaleGender);
			}
		}
		
		private void selectMemberStatus(String status) {
			driver.element().click(memberStatusList);
		 By optionLocator= By.xpath("//div[@role='option' and text()="+"' "+status+" '"+"]");
            driver.element().click(optionLocator);
		}
		
		private void selectNationality(String nationality, String city) {
			driver.element().type(nationalityList, nationality);
			driver.element().keyPress(nationalityList, Keys.ENTER);

			if(nationality.contains(Constants.SAUDINATIONALITY)) {
				driver.element().type(cityList, city);
				driver.element().keyPress(cityList, Keys.ENTER);
			}
		}
		
		private void selectCity(String city) {
			driver.element().type(cityList, city);
			driver.element().keyPress(cityList, Keys.ENTER);

		}
		
		private void selectIdType(String type) {
			driver.element().click(idTypeList);
		 By optionLocator= By.xpath("//div[@role='option' and text()="+"' "+type+" '"+"]");
            driver.element().click(optionLocator);
		}
		
		private void uploadFiles(String fileName, By uploadLocator) {
			uploadFile(driver,fileName, uploadLocator);
			waitForPageLoadWithoutSpinner(driver);
		}
		
		private void selectFromContractDate() {
			String todayDay=Utils.getTodayDay();
			driver.element().click(fromContractDay);
			By dayLocator = By
					.xpath("(//button[contains(@aria-label,"+"'"+todayDay+" '"+")])[1]");
			driver.element().click(dayLocator);
		}
		
		private void selectToContractDate() {
			String tomorrowDay=Utils.getTomorrowDay();
			driver.element().click(endContractDay);
			By dayLocator = By
					.xpath("(//button[contains(@aria-label,"+"'"+tomorrowDay+" '"+")])[1]");
			driver.element().click(dayLocator);
		}
		
		 private void selectBirthOfDate(String DOB){ 
			  driver.element().click(birthDate);
			  driver.element().click(calendaryear);
			 
			 String dateParts[] = (DOB).split("/");
			 String year =dateParts[2]; 
			 int month=Integer.parseInt(dateParts[1]);
			 String day=dateParts[0];
			 String	 monthName= Utils.getMonth(month);
		
			  By yearLocator= By.xpath("//button[@aria-label="+"'"+year+"'"+"]");
			  By monthLocator= By.xpath("//button[contains(@aria-label,"+"'"+monthName+"'"+")]");
			  By dayLocator= By.xpath("//button[contains(@aria-label,"+"'"+day+"'"+")]");
		
			  int j=0;
			  //if cond
			  while(driver.element().getAttribute(nextYearArrow, "disabled")==null) {
				 driver.element().click(nextYearArrow);  
			 j++;
			 }
			  
			  int i=0;
			  while(!isElementExist(driver, yearLocator, year)) {
				 driver.element().click(previousYearsArrow);  
			 i++;
			 }
			 
			     driver.element().click(yearLocator); 
				 driver.element().click(monthLocator);
				 driver.element().click(dayLocator);
		 }
		
		
		 private void memberAgeMoreThan16(ContractRequest contract) {
			 driver.element().click(idTypeList);
			 By optionLocator= By.xpath("//div[@role='option' and text()="+"' "+contract.getIdType()+" '"+"]");
	         driver.element().click(optionLocator);
	         
	         driver.element().type(nationalId, contract.getIdNumber());
		     uploadFiles(contract.getIdAttachement(),uploadIdAttachement);
		 }
		 
		 private void memberAgeLessThan16(ContractRequest contract) {
			 driver.element().click(parentIdType);
			 By optionLocator= By.xpath("//div[@role='option' and text()="+"' "+contract.getIdType()+" '"+"]");
	         driver.element().click(optionLocator);
	         
	         driver.element().type(parentNationalId, contract.getIdNumber());
		     uploadFiles(contract.getIdAttachement(),parentIdAttachement);
		     uploadFiles(contract.getParentApproval(),parentApproval);
		 }
		 
		public boolean uploadContractForMember(ContractRequest contract, boolean AgeMore16) {
			String contractSuccessMsg = properties.getProperty("contractSuccessMsg");

			driver.element().type(contractArName, contract.getFullNameInAR());
			driver.element().type(contractEnName, contract.getFullNameInEN());
			
			selectGender(contract.getGender());
			selectMemberStatus(contract.getStatus());
			selectNationality(contract.getNationality(), contract.getCity());
			
			driver.element().click(trueSaudiEleague);
			selectBirthOfDate(contract.getDateOfBirth());

			if(AgeMore16) {
			memberAgeMoreThan16(contract);
			}else {
			memberAgeLessThan16(contract);
			}
			
			selectFromContractDate();
			selectToContractDate();
			
			driver.element().type(contractSalary, contract.getSalary());
			uploadFiles(contract.getUploadContract(), uploadContractFile);
			driver.element().click(termsCheckBox);
			
			driver.element().click(saveBtn);
		 return isMsgExistandDisplayed(driver, successToast,contractSuccessMsg );

		}
		
		public boolean updateContract(ContractRequest contract) {
			String contractUpdateNoteMsg = properties.getProperty("contractSuccessUpdateMsg");
			selectMemberStatus(contract.getStatus());
			driver.element().click(falseSaudiEleague);
			//selectFromContractDate();
			//selectToContractDate();
			driver.element().type(contractSalary, contract.getSalary());
			uploadFiles(contract.getUploadContract(), uploadContractFile);
			driver.element().type(reason, properties.getProperty("Reason"));
			driver.element().click(termsCheckBox);
			driver.element().click(saveBtn);
			String newRequestId= Utils.replaceLastCharByNextChar(contract.getRequestId());
			contract.setRequestId(newRequestId);
		 return isMsgExistandDisplayed(driver, updateNote,contractUpdateNoteMsg ) && isMsgExistandDisplayed(driver, status,Constants.CONTRACT_UPDATE_STATUS );

		}
}
