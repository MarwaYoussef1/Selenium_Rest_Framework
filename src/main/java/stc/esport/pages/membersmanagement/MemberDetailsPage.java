package stc.esport.pages.membersmanagement;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.utils.Constants;

public class MemberDetailsPage extends PageBase {

	WebDriver driver;
	Properties properties;
	

	// identifier
	By blockMemberNote=By.xpath("//textarea[@placeholder='Block reason']");
	By actionSubmit=By.xpath("//button[@type='submit']");
	
	// Constractor
	public MemberDetailsPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	

	}

	public boolean checkActionExist(String action) {
		waitForPageLoadWithoutSpinner(driver);
		By actionLocator =By.xpath("//button[contains(text(),'"+action+"')]");
		if(isElementExist(driver, actionLocator, action))
			return true;
		return false;
	}
	
	private By getActionBtnLocator(String action) {
		By actionBtnLocator= By.xpath("//button[contains(text(),'" + action + "')]");
		return actionBtnLocator;
	}
	
	public boolean blockMember() {
		By blockMemberMsg=By.xpath("//*[text()='"+properties.getProperty("BlockMemberSuccessMsg")+ "']");
		driver.element().click(getActionBtnLocator(Constants.MANAGEMENTBLOCKMEMBER));
		waitForPageLoadWithoutSpinner(driver);
		waitUntilElementPresent(driver, blockMemberNote);
		driver.element().type(blockMemberNote, properties.getProperty("BlockMemberReason"));
		executeJavaScript(driver, actionSubmit,  "arguments[0].click();");
		waitUntilElementPresent(driver, blockMemberMsg);
		return true;				
	}
	
	public boolean checkContractStatus(String accountName,String expectedStatus)
	{
		By requestStatus=By.xpath("(//*[@title='"+accountName+"']//following::span[contains(@class,'contract-status')])[1]");
		return isMsgExistandDisplayed(driver, requestStatus,expectedStatus);
	}
	
	
}
