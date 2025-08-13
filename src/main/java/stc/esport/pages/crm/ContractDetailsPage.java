package stc.esport.pages.crm;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pojo.ContractRequest;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class ContractDetailsPage extends PageBase {

	public WebDriver driver;
	public Properties properties;

	// identifier
	By actionRequiredListInput = By.xpath("//input[@aria-autocomplete='list']");
	By actionRequiredNote = By.xpath("//textarea[@placeholder='Add Note']");
	By rejectReason = By.xpath("//textarea[@formcontrolname='reason']");
	By actionSubmit = By.xpath("//*[contains(text(),'Submit')]//parent::button");
	By actionConfirmMsg = By.id("swal2-html-container");
	By actionConfirm = By.xpath("//button[@class='swal2-confirm']");
	By reassignUserListInput = By.xpath("//*[@id='usersList']//input[@aria-autocomplete='list']");
	By reassignUserList = By.xpath("//div[contains(@class,'ng-dropdown')]");
	By requestStatus = By.xpath("//*[contains(@class,'card-title')]//following::span[contains(@class,'status')]");
	By requestTitle = By.xpath("//*[contains(@class,'card-title')]//a");
	By requireActionSubmit=By.xpath("//button[@type='submit']");
	By reSubmitAppPopUp=By.xpath("//button[contains(@class,'btn-primary')]");
	// resubmit details locators
	
	By nameAr=By.id("nameAr");
	By nameEn=By.id("name");
	By genderRadio=By.xpath("//input[@value='MALE']");
	By statusList=By.xpath("//ng-select[@id='memberStatusCode']//div[@role='combobox']");
	By nationalityList = By.xpath("//ng-select[@id='countryId']//div[@role='combobox']"); 
	By cityList=By.xpath("//ng-select[@id='cityId']//div[@role='combobox']");
	By idTypeList=By.xpath("//ng-select[@id='idTypeCode']//div[@role='combobox']");
	By idAttachmentUpload=By.id("memberContractIdFile");
	By nationalId=By.id("nationalId");
	By contractStartDate= By.id("fromDate");
	By contractEndDate=By.id("toDate");
	By salary=By.id("salary");
	By contractFileUpload=By.id("memberContractFile");

	// Constractor
	public ContractDetailsPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}

	public void waitForContractDetailsPageLoaded() {
		waitForPageLoadWithoutSpinner(driver);
	}

	private By getActionBtnLocator(String action) {
		By actionBtnLocator;
		if (action.equals(Constants.CRMAPPROVE))
			actionBtnLocator = By.xpath("//*[contains(text(),'" + Constants.CRMAPPROVE + "')]//parent::button");
		else
			actionBtnLocator = By.xpath("//button[contains(text(),'" + action + "')]");
		return actionBtnLocator;
	}
	
	private void selectNationalityCity(String nationality,String city) {
		By nameLocator = By.xpath("//div[@role='option' and text()=' "+nationality+" ']");
		By cityNameLocator = By.xpath("//div[@role='option' and text()=' "+city+" ']");
		waitUntilElementPresent(driver, nationalityList);
		driver.element().click(nationalityList);
		waitUntilElementPresent(driver, nameLocator);
		driver.element().click(nameLocator);
		waitUntilElementPresent(driver, cityList);
		driver.element().click(cityList);
		waitUntilElementPresent(driver, cityNameLocator);
		driver.element().click(cityNameLocator);
		
	}

	private void selectStatus(String status) {
		By nameLocator = By.xpath("//div[@role='option' and text()=' "+status+" ']");
		waitUntilElementPresent(driver, statusList);
		driver.element().click(statusList);
		waitUntilElementPresent(driver, nameLocator);
		driver.element().click(nameLocator);
	}
	
	private void selectIdType(String type) {
		By nameLocator = By.xpath("//div[@role='option' and text()=' "+type+" ']");
		waitUntilElementPresent(driver, idTypeList);
		driver.element().click(idTypeList);
		waitUntilElementPresent(driver, nameLocator);
		driver.element().click(nameLocator);
	}
	
	/*private void selectNationality(String nationality) {
		driver.element().type(nationalityList, nationality);
		driver.element().keyPress(nationalityList, Keys.ENTER);

	}

	private void selectStatus(String status) {
		driver.element().type(statusList, status);
		driver.element().keyPress(statusList, Keys.ENTER);

	}*/

	
	private void selectContractStartDate() {
		driver.element().click(contractStartDate);

		By dayLocator = By.xpath("(//button[contains(@aria-label," + "'" + Utils.getTodayDay() + "'" + ")])[1]");
		driver.element().click(dayLocator);
	}
	
	private void selectContractEndDate() {
		driver.element().click(contractEndDate);

		By dayLocator = By.xpath("(//button[contains(@aria-label," + "'" + Utils.getTodayDay() + "'" + ")])[1]");
		driver.element().click(dayLocator);
	}

	public boolean approve(boolean expired) {
		By approveMsg = By.xpath("//*[text()='" + properties.getProperty("ApproveSuccessMsg") + "']");
		boolean statusChanged = false;
		driver.element().click(getActionBtnLocator(Constants.CRMAPPROVE));
		waitForPageLoadWithoutSpinner(driver);

		if (isMsgExistandDisplayed(driver, actionConfirmMsg, properties.getProperty("ApprovePopUpMsg"))) {
			executeJavaScript(driver, actionConfirm, "arguments[0].click();");
			waitUntilElementPresent(driver, approveMsg);
			waitForPageLoadWithoutSpinner(driver);
			if (expired)
				statusChanged = isMsgExistandDisplayed(driver, requestStatus,
						Constants.CONTRACT_MANAGEMENT_EXPIRED_STATUS);
			else
				statusChanged = isMsgExistandDisplayed(driver, requestStatus,
						Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS);

		}
		driver.element().click(requestTitle);
		return statusChanged;

	}
	
	 private void selectActionRequiredField(String field) {
	    	driver.element().type(actionRequiredListInput, field);
	    	driver.element().keyPress(actionRequiredListInput, Keys.ENTER);
	    	
	    }

	
	public boolean actionRequired() {
		By updateCRMMsg=By.xpath("//*[text()='"+properties.getProperty("RequireActionSuccessMsg")+ "']");
		driver.element().click(getActionBtnLocator(Constants.CRMACTIONREQUIRED));
		waitForPageLoadWithoutSpinner(driver);
		driver.element().waitToBeReady(actionRequiredListInput);
		sleep(1000);
		selectActionRequiredField(Constants.CONTRACT_FULL_NAME_ARABIC);
		selectActionRequiredField(Constants.CONTRACT_FULL_NAME_ENGLISH);
		selectActionRequiredField(Constants.CONTRACT_GENDER);
		selectActionRequiredField(Constants.CONTRACT_DATE_OF_BIRTH);
		selectActionRequiredField(Constants.CONTRACT_NATIONALITY);
		selectActionRequiredField(Constants.CONTRACT_SALARY);
		selectActionRequiredField(Constants.CONTRACT_FILE);
		selectActionRequiredField(Constants.CONTRACT_MEMBER_STATUS);
		selectActionRequiredField(Constants.CONTRACT_SAUDI_ELEAGUE);
		selectActionRequiredField(Constants.CONTRACT_DURATION);
		selectActionRequiredField(Constants.CONTRACT_ID_ATTACHMENT);
		selectActionRequiredField(Constants.CONTRACT_ID_TYPE);
		selectActionRequiredField(Constants.CONTRACT_ID_NUMBER);
		driver.element().type(actionRequiredNote,properties.getProperty("RequireAnActionNote") );
		driver.element().click(requireActionSubmit);
		//executeJavaScript(driver, requireActionSubmit, "arguments[0].click();");
		
	    if (isMsgExistandDisplayed(driver, actionConfirmMsg, properties.getProperty("RequireActionPopUpMsg"))) {
	    	executeJavaScript(driver, actionConfirm, "arguments[0].click();");
	    	waitUntilElementPresent(driver, updateCRMMsg);
	    	waitForPageLoadWithoutSpinner(driver);
	    	if (isMsgExistandDisplayed(driver, requestStatus,Constants.CONTRACT_MANAGEMENT_REQUIRED_ACTION_STATUS)) {
	    		driver.element().click(requestTitle);
				return true;
			}
			
		} 
			return false;

		
	}
	
	public boolean reSubmitContract(ContractRequest contract) {
		By updateCRMRequestMsg=By.xpath("//*[text()='"+properties.getProperty("ContractResubmittedMsg")+ "']");
		driver.element().click(getActionBtnLocator(Constants.CRMREQUESTACTIONRESUBMIT));
		waitForPageLoadWithoutSpinner(driver);
		driver.element().type(nameAr, contract.getFullNameInAR());
		driver.element().type(nameEn, contract.getFullNameInEN());
		driver.element().click(genderRadio);
		selectStatus(contract.getStatus());
		selectNationalityCity(contract.getNationality(),contract.getCity());
		selectIdType(contract.getIdType());
		uploadFile(driver,contract.getIdAttachement(), idAttachmentUpload);
		driver.element().type(nationalId, contract.getIdNumber());
		selectContractStartDate();
		selectContractEndDate();
		driver.element().type(salary, contract.getSalary());
		uploadFile(driver,contract.getUploadContract(), contractFileUpload);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(reSubmitAppPopUp);
		waitUntilElementPresent(driver, updateCRMRequestMsg);
		driver.element().click(requestTitle);
		return true;
	}
	
	public boolean reject() {
		By rejectCRMMsg=By.xpath("//*[text()='"+properties.getProperty("RejectContractSuccessMsg")+ "']");
		driver.element().click(getActionBtnLocator(Constants.CRMREJECT));
		waitForPageLoadWithoutSpinner(driver);
		waitUntilElementPresent(driver, rejectReason);
		driver.element().type(rejectReason,properties.getProperty("RejectReason") );
		executeJavaScript(driver, actionSubmit, "arguments[0].click();");
				
	    if (isMsgExistandDisplayed(driver, actionConfirmMsg, properties.getProperty("RejectPopUpMsg"))) {
	    	executeJavaScript(driver, actionConfirm, "arguments[0].click();");
	    	waitUntilElementPresent(driver, rejectCRMMsg);
	    	waitForPageLoadWithoutSpinner(driver);
	    	if (isMsgExistandDisplayed(driver, requestStatus,Constants.CONTRACT_MANAGEMENT_REJECTED_STATUS)) {
	    		driver.element().click(requestTitle);
				return true;
			}
		} 
			return false;

		
	}


	public boolean actionButtonsHidden() {
		By actionBtnsContainer = By.xpath("//div[contains(@class,'action-btns')]//child::button");
		int actionBtnsSize = driver.getDriver().findElements(actionBtnsContainer).size();

		if (actionBtnsSize == 0) {
			return true;
		}

		return false;
	}

}
