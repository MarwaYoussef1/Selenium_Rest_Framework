package stc.esport.pages.crm;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pojo.Account;
import stc.esport.pojo.ContractRequest;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class CRMListPage extends PageBase {

	 WebDriver driver;
	 Properties properties;
	 CRMDetailsPage crmDetailsObj;
	 ContractManagementListPage ContractManagementListPageObj;

	// identifier
	By searchInput = By.xpath("//input[@type='text']");
	By RequestId = By.xpath("//span[@class='request-id'][1]");
	By listRow= By.xpath("//div[contains(@class,'table-list-item')]");
	By requestsStatus=By.xpath("//div[contains(@class,'table-list')]//span[contains(@class,'request-status')]");
	By RequestIdStatus = By.xpath("//span[contains(@class,'request-status')][1]");

	// Constractor
	public CRMListPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
		crmDetailsObj= new CRMDetailsPage(driver,properties);
		ContractManagementListPageObj=new ContractManagementListPage(driver,properties);
	}

	private By SearchOnCRMByRequestId(String requestId) {
		
		int size= 0;
		int i =5;
		
		do {
			driver.element().type(searchInput, requestId);
			driver.element().keyPress(searchInput, Keys.ENTER);
			waitForPageLoadWithoutSpinner(driver);
			sleep(2000);
			
			size=Integer.valueOf(driver.getDriver().findElements(listRow).size());
			i=i-1;
		}
		while(size>1 && i > 0);
		if (driver.getDriver().findElement(RequestId).getText().equals(requestId))
			return RequestIdStatus;
		return null;
	}
	
	private boolean SearchOnCRMByEmail(String emailInput) {
		driver.element().type(searchInput, emailInput);
		waitForPageLoadWithoutSpinner(driver);
		sleep(2000);
		int size=Integer.valueOf(driver.getDriver().findElements(listRow).size());
		if (size==1)
		{
			driver.element().click(RequestId);
			waitForPageLoadWithoutSpinner(driver);
			return crmDetailsObj.checkEmailText(emailInput);
			
		}
		return false;
	}
	
	/*private boolean SearchOnCRMByStatus(String status) {
		By email= By.xpath("//*[@title='"+emailInput+"']");
		driver.element().type(driver, searchInput, status);
		//driver.findElement(searchInput).sendKeys(requestId);
		driver.element().keyPress(driver, searchInput, Keys.ENTER);
		driver.element().type(driver, searchInput, status);
		driver.element().keyPress(driver, searchInput, Keys.ENTER);
		waitForPageLoadWithoutSpinner(driver);
		String size= driver.element().getSize(driver, listRow);
		sleep(2000);
		if (isElementExist(driver, email, emailInput) && size.equals("1"))
			return true;
		return false;
	}*/

	private void openCRM(String requestId) {
		waitForPageLoadWithoutSpinner(driver);
		By crmRequest = SearchOnCRMByRequestId(requestId);
		if (crmRequest != null)
		{
			driver.element().click(crmRequest);
			
		waitForPageLoadWithoutSpinner(driver);
		}
	}
	
	public boolean requireAnActionOnCRMRequest(String requestId,String name)
	{
		boolean actionRequiredDone;
		boolean actionRequiredStatus;
		openCRM(requestId);
		crmDetailsObj.reassignIfNeeded(name);
		actionRequiredDone=crmDetailsObj.actionRequired();
		actionRequiredStatus=checkCRMRequestStatus(requestId,Constants.CRMREQUESTACTIONREQUIRED);
		return actionRequiredDone  && actionRequiredStatus;
	}
	
	public boolean approveCRMRequest(String requestId,String name)
	{
		boolean approveDone;
		boolean approveStatus;
		boolean buttonsHidden;
		openCRM(requestId);
		crmDetailsObj.reassignIfNeeded(name);
		approveDone=crmDetailsObj.approve();
		buttonsHidden=crmDetailsObj.actionButtonsHidden();
		approveStatus=checkCRMRequestStatus(requestId,Constants.CRMREQUESTAPPROVED);
		return approveDone  && buttonsHidden && approveStatus ;
	}
	
	public boolean rejectCRMRequest(boolean block,String requestId,String name)
	{
		boolean rejectDone;
		boolean rejectStatus;
		boolean buttonsHidden;
		openCRM(requestId);
		crmDetailsObj.reassignIfNeeded(name);
		rejectDone=crmDetailsObj.reject(block);
		buttonsHidden=crmDetailsObj.actionButtonsHidden();
		rejectStatus=checkCRMRequestStatus(requestId,Constants.CRMREQUESTREJECTED);
		return rejectDone  && buttonsHidden && rejectStatus ;
	}
	
	
	public boolean checkCRMRequestStatus(String requestId,String expectedStatus)
	{
		String actualStatus;
		By crmRequestStatus = SearchOnCRMByRequestId(requestId);
		if (crmRequestStatus != null)
		{
			waitForPageLoadWithoutSpinner(driver);
			//driver.element().getText(driver, crmRequestStatus)
			actualStatus=driver.getDriver().findElement(crmRequestStatus).getText();
			return actualStatus.trim().equals(expectedStatus);
		}
		return false;
	}
	
	public boolean CRMRequestDetails(String requestId,Account accountObj)
	{
		boolean detailsViewed;
		openCRM(requestId);
		detailsViewed=crmDetailsObj.checkRequestDetails(accountObj);
		return detailsViewed ;
	}
	
	public boolean searchOnCRM(String type,String value)
	{
		boolean requestFound=false;
		if(type.equals(Constants.CRMSEARCHBYID))
		{
			requestFound= SearchOnCRMByRequestId(value)!=null;
		}
		else if(type.equals(Constants.CRMSEARCHBYEMAIL))
		{
			requestFound= SearchOnCRMByEmail(value);
		}
		return requestFound;
	}
	
	public boolean approveContractRequest(String requestId,String accountName,boolean expired)
	{
		boolean approveDone;
		String assigneeName = properties.getProperty("superAdminName");
		String parentRequestId = Utils.removeLastChar(requestId);
		openCRM(parentRequestId);		
		ContractManagementListPageObj.reassignIfNeeded(assigneeName);
		approveDone=ContractManagementListPageObj.approveContractRequest(accountName, expired);
		return approveDone ;
	}

	
	public boolean rejectContractRequest(String requestId,String accountName,String expectedStatus)
	{
		boolean rejectDone;
		String assigneeName = properties.getProperty("superAdminName");
		//filter to get parent request id 
		//int lastCharIndex = requestId.lastIndexOf(requestId.charAt(requestId.length() - 1));
		//String parentRequestId = requestId.substring(0, lastCharIndex);
		String parentRequestId = Utils.removeLastChar(requestId);
		openCRM(parentRequestId);		
		ContractManagementListPageObj.reassignIfNeeded(assigneeName);
		rejectDone=ContractManagementListPageObj.rejectContractRequest(accountName,expectedStatus);
		return rejectDone ;
	}
	
	public boolean requireAnActionOnContractRequest(String requestId,String accountName)
	{
		boolean requireAnActionDone;
		String assigneeName = properties.getProperty("superAdminName");
		//filter to get parent request id 
		//int lastCharIndex = requestId.lastIndexOf(requestId.charAt(requestId.length() - 1));
		//String parentRequestId = requestId.substring(0, lastCharIndex);
		String parentRequestId = Utils.removeLastChar(requestId);
		openCRM(parentRequestId);		
		ContractManagementListPageObj.reassignIfNeeded(assigneeName);
		requireAnActionDone=ContractManagementListPageObj.requireAnActionOnContractRequest(accountName);
		return requireAnActionDone ;
	}
	
	public boolean reSubmitContractRequest(String requestId,String accountName,ContractRequest contract)
	{
		boolean resubmitDone;
		String parentRequestId = Utils.removeLastChar(requestId);
		openCRM(parentRequestId);		
		resubmitDone=ContractManagementListPageObj.reSubmitContract(accountName,contract);
		return resubmitDone ;
	}
	
	public boolean checkContractRequestStatus(String requestId,String expectedStatus)
	{
		String parentRequestId = Utils.removeLastChar(requestId);
		openCRM(parentRequestId);
		return ContractManagementListPageObj.checkParentRequestStatus(expectedStatus);
		
	}
	
	public boolean checkRequestStatusById(String tabName,String requestId,String expectedStatus)
	{
		return ContractManagementListPageObj.checkRequestStatusById(tabName, requestId, expectedStatus);
		
	}
	
	public boolean checkContractDetails(String accountName,ContractRequest contract)
	{
		return ContractManagementListPageObj.checkUpdatedContractDetails(accountName,contract);
		
	}
	
	public boolean checkNoContractRequests(String tabName)
	{
		return ContractManagementListPageObj.checkNoRequests(tabName);
	}


}
