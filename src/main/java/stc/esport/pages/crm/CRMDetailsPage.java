package stc.esport.pages.crm;

import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pojo.Account;
import stc.esport.utils.Constants;

public class CRMDetailsPage extends PageBase {

	public WebDriver driver;
	public Properties properties;

	// identifier
	 By actionRequiredListInput= By.xpath("//input[@aria-autocomplete='list']");
     By actionRequiredNote=By.xpath("//textarea[@placeholder='Add Note']");
     By actionSubmit=By.xpath("//button[@type='submit']");
     By actionConfirmMsg=By.id("swal2-html-container");
     By actionConfirm=By.xpath("//button[@class='swal2-confirm']");
     By reassignUserListInput=By.xpath("//*[@id='usersList']//input[@aria-autocomplete='list']");
     By reassignUserList=By.xpath("//div[contains(@class,'ng-dropdown')]");
     By blockToggle=By.xpath("//*[@formcontrolname='blockUser']//*[@role='switch']");
     By requestStatus = By.xpath("//app-request-details//span[contains(@class,'request-status')]");
     
     //Details locators
     
 
    
     
	// Constractor
	public CRMDetailsPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}
	
	public void waitForCRMDetailsPageLoaded() {
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
	
	
	private String getDetailText(String detailName)
	{
		By detailLocator=By.xpath("//*[@class='label-details' and text()='"+detailName+"']//following::*[contains(@class,'text-details')][1]");
		return driver.element().getText(detailLocator);
	}
	
	private String getAttachementSrc(String attachementName)
	{
		By attachementImage= By.xpath("//p[text()='"+attachementName+"']//following::img[1]");
		String src=driver.element().getAttribute(attachementImage, "src");
		return src;
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
		selectActionRequiredField(Constants.NATIONALITY);
		selectActionRequiredField(Constants.NATIONALID);
		selectActionRequiredField(Constants.NATIONALIDFILE);
		selectActionRequiredField(Constants.CLUBIBANFILE);
		selectActionRequiredField(Constants.CLUBIBAN);
		selectActionRequiredField(Constants.CLUBLOGO);
		selectActionRequiredField(Constants.CLUBNAMEEN);
		selectActionRequiredField(Constants.CLUBNAMRAR);
		selectActionRequiredField(Constants.CRENDDATE);
		selectActionRequiredField(Constants.CRFILE);
		selectActionRequiredField(Constants.CRNUMBER);
		//selectActionRequiredField(Constants.CLUBEMAIL);
		//selectActionRequiredField(Constants.CLUBMOBILENUMBER);
		driver.element().type(actionRequiredNote,properties.getProperty("RequireAnActionNote") );
		driver.element().click(actionSubmit);
		//executeJavaScript(driver, actionSubmit, "arguments[0].click();");
		
	    if (isMsgExistandDisplayed(driver, actionConfirmMsg, properties.getProperty("RequireActionPopUpMsg"))) {
	    	executeJavaScript(driver, actionConfirm, "arguments[0].click();");
	    	waitUntilElementPresent(driver, updateCRMMsg);
	    	waitForPageLoadWithoutSpinner(driver);
	    	if (isMsgExistandDisplayed(driver, requestStatus,Constants.CRMREQUESTACTIONREQUIREDDETAILS)) {
				return true;
			}
			
		} 
			return false;

		
	}

	
	public boolean approve() {
		By approveCRMMsg=By.xpath("//*[text()='"+properties.getProperty("ApproveSuccessMsg")+ "']");
		driver.element().click(getActionBtnLocator(Constants.CRMAPPROVE));
		waitForPageLoadWithoutSpinner(driver);
				
	    if (isMsgExistandDisplayed(driver, actionConfirmMsg, properties.getProperty("ApprovePopUpMsg"))) {
	    	executeJavaScript(driver, actionConfirm, "arguments[0].click();");
	    	waitUntilElementPresent(driver, approveCRMMsg);
	    	waitForPageLoadWithoutSpinner(driver);
	    	if (isMsgExistandDisplayed(driver, requestStatus,Constants.CRMREQUESTAPPROVED)) {
				return true;
			}
		} 
			return false;

		
	}
	
	public boolean reject(boolean block) {
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

		
	}
	
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
	
	public void reassignIfNeeded(String name)
	{
		sleep(2000);
		if(!isElementExist(driver, getActionBtnLocator(Constants.CRMAPPROVE), "Approve action"))
    	{
			 reassign(name);
    	}
		
	}
	
	public boolean reassign(String name)
	{
		By reassignCRMMsg=By.xpath("//*[text()='"+properties.getProperty("ReassignActionSuccessMsg")+ "']");
		By reassignUserOption=By.xpath("//*[@role='option']//span[ text()=' "+name+" ']");
		driver.element().click(getActionBtnLocator(Constants.CRMREASSIGN));
		waitForPageLoadWithoutSpinner(driver);
		driver.element().waitToBeReady(reassignUserListInput);
		driver.element().type(reassignUserListInput, name);
		waitUntilElementPresent(driver, reassignUserOption);
		driver.element().click(reassignUserOption);
    	executeJavaScript(driver, actionSubmit, "arguments[0].click();");
    	waitUntilElementPresent(driver, reassignCRMMsg);
    	return true;
	}
	
	public boolean checkRequestDetails(Account accountObj)
	{
		ArrayList<Boolean> checks = new ArrayList<Boolean>();
		checks.add(getDetailText(Constants.CLUBNAMEENDETAILLABEL).equals(accountObj.getClubOwnerEn()));
		checks.add(getDetailText(Constants.CLUBNAMRARDETAILLABEL).equals(accountObj.getClubOwnerAr()));
		checks.add(getDetailText(Constants.CLUBEMAIL).equals(accountObj.getEmail()));
		checks.add(getDetailText(Constants.CLUBMOBILENUMBER).replace("\n","").equals(accountObj.getMobileCode()+accountObj.getMobile()));
		checks.add(getDetailText(Constants.CLUBOWNERROLE).equals(accountObj.getName()));
		checks.add(getDetailText(Constants.CLUBNATIONALIDDETAILLABEL).equals(accountObj.getClubOwnerNationalID()));
		checks.add(getDetailText(Constants.NATIONALITY).toLowerCase().equals(accountObj.getNationality()));
		checks.add(getDetailText(Constants.CRNUMBERDETAILLABEL).equals(accountObj.getCrNumber()));
		//checks.add(getDetailText(Constants.CRENDDATEDETAILLABEL).equals(accountObj.getCrDate()));
		checks.add(getDetailText(Constants.IBANDETAILLABEL).equals(accountObj.getClubIBAN()));
		
		checks.add(getAttachementSrc(Constants.CLUBNATIONALIDDETAILLABEL).contains(accountObj.getClubOwnerNationalIDFile()));
		checks.add(getAttachementSrc(Constants.CRFILEDETAILLABEL).contains("pdf"));
		checks.add(getAttachementSrc(Constants.IBANDETAILFILELABEL).contains("pdf"));
		checks.add(getAttachementSrc(Constants.CLUBLOGODETAILLABEL).contains(accountObj.getClubLogo()));
		
		boolean isAllTrue = checks.stream().allMatch(val -> val == Boolean.TRUE);
		return isAllTrue;
	}
	
	public boolean checkEmailText(String expectedEmail)
	{
		return getDetailText(Constants.CLUBEMAIL).equals(expectedEmail);
	}
	
	
	


}
