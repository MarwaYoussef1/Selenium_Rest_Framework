package stc.esport.pages.teamsclans;


import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.utils.Constants;

public class TeamContractsPage extends PageBase {

	public WebDriver driver;
	public Properties properties;
	
	
    By cancelRequestPopUpBtn=By.xpath("//*[contains(@class,'confirm-action')]");
	// Constructor
	public TeamContractsPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}
	
	private By getUploadCancelContractLocator(String accountName)
	{
		By contractUploadCancel=By.xpath("//*[@title='"+accountName+"']//following::a[contains(@class,'upload-contract-btn')]");
		return contractUploadCancel;
	}
	public boolean checkContractStatus(String accountName,String expectedStatus)
	{   
		By contractStatus=By.xpath("(//*[@title='"+accountName+"']//following::*[contains(@class,'status')])[1]");
		waitForPageLoadWithoutSpinner(driver);
		return isMsgExistandDisplayed(driver, contractStatus,expectedStatus);
		
	}
	
	public boolean cancelContractInvitationRequest(String accountName,String cancelAction)
	{
		By contractUploadCancel=getUploadCancelContractLocator(accountName);
		driver.element().click(contractUploadCancel);
		waitForPageLoadWithoutSpinner(driver);
		if(cancelAction.equals(Constants.CONTRACT_MANAGEMENT_CANCEL_CONTRACT))
		{
		waitUntilElementPresent(driver, cancelRequestPopUpBtn);
		driver.element().click(cancelRequestPopUpBtn);
		}
		return checkContractStatus(accountName,Constants.CONTRACT_MANAGEMENT_NO_CONTRACT_STATUS);
	}
	
	public boolean checkIfMemberExist(String accountName)
	{
		waitForPageLoadWithoutSpinner(driver);
		By account=By.xpath("//*[@title='"+accountName+"']");
		return isElementExist(driver, account, accountName);
	}

	

}
