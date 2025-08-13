package stc.esport.pages.incident;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class IncidentDetailsPage extends PageBase{

	WebDriver driver;
	Properties properties;
	
	// identifier
	 By reassignUserListInput=By.xpath("//*[@id='usersList']//input[@aria-autocomplete='list']");
     By reassignUserList=By.xpath("//div[contains(@class,'ng-dropdown')]");
     By actionSubmit=By.xpath("//button[@type='submit']");
     By actionConfirmMsg=By.id("swal2-html-container");
     By actionConfirm=By.xpath("//button[@class='swal2-confirm']");
     By actionRequiredNote=By.xpath("//textarea[@placeholder='Add Note']");
    // By blockToggle=By.xpath("//*[@formcontrolname='blockUser']//*[@role='switch']");
     By incidentStatus = By.xpath("//span[contains(@class,'custom-badge')]");
   
   
	// Constractor
	public IncidentDetailsPage(WebDriver driver, Properties properties) {
				this.driver = driver;
				this.properties = properties;
			}
	
	
	
	
	private By getActionBtnLocator(String action) {
		By actionBtnLocator;
		if (action.equals(Constants.INCIDENT_APPROVE))
			actionBtnLocator = By.xpath("//*[contains(text(),'" + Constants.INCIDENT_APPROVE + "')]//parent::button");
		else
			actionBtnLocator = By.xpath("//button[contains(text(),'" + action + "')]");
		return actionBtnLocator;
	}
	
	public boolean actionRequired() {
		By updateMsg=By.xpath("//*[text()='"+properties.getProperty("RequireActionSuccessMsg")+ "']");
		driver.element().click(getActionBtnLocator(Constants.CRMACTIONREQUIRED));
		waitForPageLoadWithoutSpinner(driver);
		driver.element().waitToBeReady(actionRequiredNote);
		sleep(1000);
		driver.element().type(actionRequiredNote,properties.getProperty("RequireAnActionNote") );
		driver.element().click(actionSubmit);
		//executeJavaScript(driver, actionSubmit, "arguments[0].click();");
		
	    if (isMsgExistandDisplayed(driver, actionConfirmMsg, properties.getProperty("RequireActionPopUpMsg"))) {
	    	executeJavaScript(driver, actionConfirm, "arguments[0].click();");
	    	waitUntilElementPresent(driver, updateMsg);
	    	waitForPageLoadWithoutSpinner(driver);
	    	if (isMsgExistandDisplayed(driver, incidentStatus,Constants.CRMREQUESTACTIONREQUIREDDETAILS)) {
				return true;
			}
			
		} 
			return false;

		
	}

	
	public boolean approve(String assignee) {
		reassign(assignee);
		By approveMsg=By.xpath("//*[text()='"+properties.getProperty("ApproveSuccessMsg")+ "']");
		driver.element().click(getActionBtnLocator(Constants.INCIDENT_APPROVE));
		waitForPageLoadWithoutSpinner(driver);
				
	    if (isMsgExistandDisplayed(driver, actionConfirmMsg, properties.getProperty("IncidentApprovePopUpMsg"))) {
	    	executeJavaScript(driver, actionConfirm, "arguments[0].click();");
	       	waitUntilElementPresent(driver, approveMsg);
	    	waitForPageLoadWithoutSpinner(driver);
	    	if (isMsgExistandDisplayed(driver, incidentStatus,Constants.INCIDENT_UNDER_REVIEW)) {
				return true;
			}
		} 
			return false;

		
	}
	
	/*public boolean reject(boolean block) {
		By rejectCRMMsg=By.xpath("//*[text()='"+properties.getProperty("RejectSuccessMsg")+ "']");
		driver.element().click(getActionBtnLocator(Constants.CRMREJECT));
		waitForPageLoadWithoutSpinner(driver);
		waitUntilElementPresent(driver, actionRequiredNote);
		driver.element().type(actionRequiredNote,properties.getProperty("RejectReason") );
		if(block)
		{
			executeJavaScript(driver, blockToggle, "arguments[0].click();");
			
		}
		
		executeJavaScript(driver, actionSubmit, "arguments[0].click();");
				
	    if (isMsgExistandDisplayed(driver, actionConfirmMsg, properties.getProperty("RejectPopUpMsg"))) {
	    	executeJavaScript(driver, actionConfirm, "arguments[0].click();");
	    	waitUntilElementPresent(driver, rejectCRMMsg);
	    	waitForPageLoadWithoutSpinner(driver);
	    	if (isMsgExistandDisplayed(driver, requestStatus,Constants.CRMREQUESTREJECTED)) {
				return true;
			}
		} 
			return false;

		
	}*/
	
	public boolean actionButtonsHidden()
	{
		By actionBtnsContainer=By.xpath("//div[contains(@class,'action-btns')]//child::button");
		int actionBtnsSize=driver.getDriver().findElements(actionBtnsContainer).size();
		
		if(actionBtnsSize==0)
		{
			return true;
		}
		
		return false;
	}
	
	
	public boolean reassign(String name)
	{
		By reassignMsg=By.xpath("//*[text()='"+properties.getProperty("ReassignActionSuccessMsg")+ "']");
		By reassignUserOption=By.xpath("//*[@role='option']//span[ text()=' "+name+" ']");
		By reAssignLocator=By.xpath("//button[contains(text(),'"+Constants.INCIDENT_REASSIGN+"')]");
		driver.element().click(reAssignLocator);
		waitForPageLoadWithoutSpinner(driver);
		driver.element().waitToBeReady(reassignUserListInput);
		driver.element().type(reassignUserListInput, name);
		waitUntilElementPresent(driver, reassignUserOption);
		driver.element().click(reassignUserOption);
    	executeJavaScript(driver, actionSubmit, "arguments[0].click();");
    	waitUntilElementPresent(driver, reassignMsg);
    	return true;
	}

	
	
	
}