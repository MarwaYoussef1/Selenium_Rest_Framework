package stc.esport.pages.crm;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;


import stc.esport.base.PageBase;
import stc.esport.pojo.ContractRequest;
import stc.esport.pojo.ContractRequest;
import stc.esport.utils.Constants;

public class ContractManagementListPage extends PageBase {

	public WebDriver driver;
	public Properties properties;
	public ContractDetailsPage contractDetailsPageObj;

	// identifier
	 By actionSubmit=By.xpath("//button[@type='submit']");
     By actionConfirmMsg=By.id("swal2-html-container");
     By actionConfirm=By.xpath("//button[@class='swal2-confirm']");
     By reassignUserListInput=By.xpath("//*[@id='usersList']//input[@aria-autocomplete='list']");
     By reassignUserList=By.xpath("//div[contains(@class,'ng-dropdown')]");
     By requestStatus = By.xpath("(//*[contains(@class,'card-header')]//following::span[contains(@class,'status')])[1]");
     By backToTeam=By.xpath("//i[contains(@class,'back-icon')]");
     //Contract Requests List details
     By teamMembersTab=By.xpath("//button[contains(@class,'member-tap') and contains(text(),'Team Members')]");
     By pastRequestsTab=By.xpath("//button[contains(@class,'member-tap') and contains(text(),'Past Requests')]");
     
     
     
	// Constractor
	public ContractManagementListPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
		contractDetailsPageObj=new ContractDetailsPage( driver,  properties);
	}
	
	public void waitForContractListPageLoaded() {
		waitForPageLoadWithoutSpinner(driver);
	}
	private By getNewDetailInfoLocator(String newInfo)
	{
		By newDetailInfo = By.xpath("//th[normalize-space(text())='New']//following::*[normalize-space(text())='"+newInfo+"']");
		return newDetailInfo;
	}

	private void clickOnActionInDropDown(String contractName,String action) {
		
		By dotsDropDown= By.xpath("//*[@title='"+contractName+"']//following::button[@class='dropbtn'][1]");
		By actionBtnLocator=By.xpath("//*[@title='"+contractName+"']//following::a[contains(text(),'"+action+"')][1]");
		driver.element().clickAndHold(dotsDropDown);
		waitUntilElementPresent(driver, actionBtnLocator);
		driver.element().click(actionBtnLocator);
		waitForPageLoadWithoutSpinner(driver);
	}
	
	
	public void reassignIfNeeded(String name)
	{
		sleep(2000);
		By assigneeLocator= By.xpath("//span[text()='Assignee : ']//following::span[@title='"+name+"']");
		if(!isElementExist(driver, assigneeLocator, "Assignee user"))
    	{
			 reassign(name);
    	}
		
	}
	
	public boolean reassign(String name)
	{
		By reassignCRMMsg=By.xpath("//*[text()='"+properties.getProperty("ReassignActionSuccessMsg")+ "']");
		By reassignUserOption=By.xpath("//*[@role='option']//span[ text()=' "+name+" ']");
		By reAssignLocator=By.xpath("//button[contains(text(),'"+Constants.CRMREASSIGN+"')]");
		driver.element().click(reAssignLocator);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().waitToBeReady(reassignUserListInput);
		driver.element().type(reassignUserListInput, name);
		waitUntilElementPresent(driver, reassignUserOption);
		driver.element().click(reassignUserOption);
    	executeJavaScript(driver, actionSubmit, "arguments[0].click();");
    	waitUntilElementPresent(driver, reassignCRMMsg);
    	return true;
	}
	
	
	private void viewContractDetails(String accountName)
	{
		clickOnActionInDropDown(accountName,Constants.CONTRACT_MANAGEMENT_VIEW_DETAILS);
	}
	
	public boolean approveContractRequest(String accountName,boolean expired) {
		
		boolean approveDone;
		boolean approveStatus;
		boolean buttonsHidden;
		boolean contractStatusUpdated;
		viewContractDetails(accountName);
		approveDone=contractDetailsPageObj.approve(expired);
		contractStatusUpdated=checkParentRequestStatus(Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS);
		if(expired)
		approveStatus=checkRequestStatus(accountName,Constants.CONTRACT_MANAGEMENT_EXPIRED_STATUS);
		else
		approveStatus=checkRequestStatus(accountName,Constants.CONTRACT_MANAGEMENT_ACTIVE_STATUS);	
		buttonsHidden=contractDetailsPageObj.actionButtonsHidden();
		return approveDone && buttonsHidden && approveStatus &&contractStatusUpdated ;
		
	}
	
      public boolean rejectContractRequest(String accountName,String expectedStatus) {
		
		boolean rejectDone;
		boolean rejectStatus;
		boolean buttonsHidden;
		boolean contractStatusUpdated;
		viewContractDetails(accountName);
		rejectDone=contractDetailsPageObj.reject();
		rejectStatus=checkRequestStatus(accountName,expectedStatus);
		buttonsHidden=contractDetailsPageObj.actionButtonsHidden();
		contractStatusUpdated=checkParentRequestStatus(Constants.CONTRACT_MANAGEMENT_COMPLETED_STATUS);
		return rejectDone && buttonsHidden && rejectStatus && contractStatusUpdated ;
		
	}
           
      public boolean requireAnActionOnContractRequest(String accountName)
  	{
  		boolean actionRequiredDone;
  		boolean actionRequiredStatus;
  		viewContractDetails(accountName);
  		actionRequiredDone=contractDetailsPageObj.actionRequired();
  		actionRequiredStatus=checkRequestStatus(accountName,Constants.CONTRACT_MANAGEMENT_REQUIRED_ACTION_STATUS);
  		return actionRequiredDone  && actionRequiredStatus;
  	}
      
    public boolean reSubmitContract(String accountName,ContractRequest contract)
    	{
    		boolean resubmitDone;
    		boolean status;
    		viewContractDetails(accountName);
    		resubmitDone=contractDetailsPageObj.reSubmitContract(contract);
    		status=checkRequestStatus(accountName,Constants.CONTRACT_MANAGEMENT_ACTION_TAKEN_STATUS);
    		return resubmitDone  && status;
    	}
	
	private boolean checkRequestStatus(String accountName,String expectedStatus)
	{
		By requestStatus=By.xpath("(//*[@title='"+accountName+"']//following::span[contains(@class,'request-status')])[1]");
		return isMsgExistandDisplayed(driver, requestStatus,expectedStatus);
	}
	
	public boolean checkParentRequestStatus(String expectedStatus)
	{
		return isMsgExistandDisplayed(driver, requestStatus,expectedStatus);
	}
	
	public boolean checkRequestStatusById(String tabName,String requestId,String expectedStatus)
	{    
		openTab (tabName);
		By requestStatus=By.xpath("(//*[text()=' "+requestId+" ']//following::span[contains(@class,'request-status')])[1]");
		return isMsgExistandDisplayed(driver, requestStatus,expectedStatus);
	}
	
	public boolean checkUpdatedContractDetails(String accountName,ContractRequest contractObj)
	{
		viewContractDetails(accountName);
		boolean contractUpdated
		 = isElementExist(driver, getNewDetailInfoLocator(contractObj.getUploadContract()), "ContractFile") &&
		   isElementExist(driver, getNewDetailInfoLocator(contractObj.getStatus()), "Member status") && 
		   isElementExist(driver, getNewDetailInfoLocator(contractObj.getSaudiEleague()), "Saudi league status") ;
		driver.element().click(backToTeam);
		return contractUpdated;
	}
	
	public boolean checkNoRequests(String tabName)
	{
		openTab (tabName);
		By noRequestsMsg=By.xpath("//div[@class='notfound-content']");
		return isElementExist(driver, noRequestsMsg, "No Requests");
	}
	
	private void openTab(String tabName)
	{
		if(tabName.equals(Constants.CONTRACT_PAST_REQUESTS_TAB))
			driver.element().click(pastRequestsTab);
		else
			driver.element().click(teamMembersTab);
	}
	
	
	


}
