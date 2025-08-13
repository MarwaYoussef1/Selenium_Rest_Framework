package stc.esport.pages.Teammanagement;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.utils.Constants;

public class TeamDetailsPage extends PageBase {

	WebDriver driver;
	Properties properties;
	

	// identifier
	By requestContractBtn= By.xpath("//a[contains(@class,'req-contracts-btn')]");
	By requestSent= By.xpath("//p[contains(@class,'req-contract-sent')]");
	
	By confirmRemoveBtn = By.xpath("//button[contains(@class,'confirm-action')]");
	By saveButton=By.xpath("//button[@type='submit']");
	
	//Archive popup with reason
	
	By archiveReason=By.xpath("//textarea[@formcontrolname='reason']");
	By confirmArchiveBtn=By.xpath("//*[normalize-space(text())='Archive']//parent::button");
	By cancelArchiveBtn=By.xpath("//*[normalize-space(text())='Cancel']//parent::button");
	By archiveErrorMsg=By.id("swal2-html-container");
	By archiveErrorCloseBtn=By.xpath("//button[@class='swal2-confirm confirm-action']");
	By archiveYesBtn=By.xpath("//button[@class='swal2-confirm']");
	By successToast= By.xpath("//div[contains(@id,'html-container')]");
	
	// Constractor
	public TeamDetailsPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	

	}
	
	private By getActionLocator(String action)
	{
		waitForPageLoadWithoutSpinner(driver);
		By actionLocator =By.xpath("//a[contains(@class,'"+action+"')]");
		return actionLocator;
	}
	
	public boolean removeTeamMember(String accountName)
	{
		By removeBtn= By.xpath("//button[normalize-space()='"+accountName+"']//following::button[contains(@class,'close-btn')]");
		By accountTitle= By.xpath("//*[@title='"+accountName+"']");
		driver.element().click(getActionLocator(Constants.MANAGEMENTEDITTEAM));
		waitUntilElementPresent(driver, removeBtn);
		driver.element().click(removeBtn);
		waitUntilElementPresent(driver, confirmRemoveBtn);
		driver.element().click(confirmRemoveBtn);
		waitForElementDisappeared(driver, removeBtn);
		driver.element().click(saveButton);
		waitForPageLoadWithoutSpinner(driver);
		return !isElementExist(driver, accountTitle, accountName);
	}
	
	public boolean ArchiveTeam(boolean withActiveMember)
	{
		String errorMsg;
		driver.element().click(getActionLocator(Constants.MANAGEMENTARCHIVETEAM));
		waitUntilElementPresent(driver, confirmArchiveBtn);
		driver.element().type(archiveReason, "Archive");
		driver.element().click(confirmArchiveBtn);
		if(withActiveMember)
		{   
			errorMsg=properties.getProperty("archiveTeamErrorMsg");
			if(isMsgExistandDisplayed(driver, archiveErrorMsg, errorMsg))
			{
				driver.element().click(archiveErrorCloseBtn);
				waitUntilElementPresent(driver, cancelArchiveBtn);
				driver.element().click(cancelArchiveBtn);
				return true;
			}
		}
		else {
			if(isElementExist(driver, archiveYesBtn, "yes archive"))
			driver.element().click(archiveYesBtn);
			waitUntilElementPresent(driver, successToast);
			return true;
			
		}
		return false;
	}

	public boolean checkActionExist(String action) {
		By actionLocator =getActionLocator(action);
		if(isElementExist(driver, actionLocator, action))
			return true;
		return false;
	}
	
	public boolean checkContractStatus(String accountName,String expectedStatus)
	{
		By requestStatus=By.xpath("(//*[@title='"+accountName+"']//following::span[contains(@class,'contract-status')])[1]");
		return isMsgExistandDisplayed(driver, requestStatus,expectedStatus);
	}
	
	public boolean requestContractForTeam(String teamName) {
		String expectedStatus= properties.getProperty("contractRequestSent");
		driver.element().click(requestContractBtn);
		return isMsgExistandDisplayed(driver, requestSent,expectedStatus);
	}
	
	
	
	
}
