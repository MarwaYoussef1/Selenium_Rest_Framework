 package stc.esport.pages;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import com.shaft.tools.io.ReportManager;

import stc.esport.base.PageBase;
import stc.esport.utils.Constants;

public class fakeMailPage extends PageBase {

	// Variables
	public WebDriver driver;
	public Properties properties;
	public String emailAddress;
	// Define locators
    By inputName= By.id("field_a1xtj");
    By checkButton=By.xpath("//button[@type='submit']");
    // Locator for click here button in e-mail part
	By clickhereBtn = By.xpath("(//p//a[@target='_other'])[1]");
	//By emailFrame = By.id("html_msg_body");
	//By emailFrame = By.id("ifmail");
	By requestId = By.xpath("//p[contains(text(),'ID')]//span");

	// functions
	public fakeMailPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}

	// get password link from e-mail
	public String getPasswordLink(String name) {
		openMailBySubject(true, name, "Set your password");
		//driver.element().waitToBeReady(emailFrame);

		//driver.getDriver().switchTo().frame(driver.getDriver().findElement(emailFrame));
		String passwordLink = driver.getDriver().findElement(clickhereBtn).getAttribute("href");
		//driver.element().switchToDefaultContent();
		return passwordLink;
	}

	// open inbox for specific user
	public void openInbox(String name) {
		//openUrlInNewTab(driver, properties.getProperty("fakeMailUrl") + name.split("@")[0], 1);
		sleep(120000);
		String username=name.split("@")[0];
		openUrlInNewTab(driver, properties.getProperty("fakeMailUrlPart1")+username+properties.getProperty("fakeMailUrlPart2"), 1);
	
	
	}

	public void openMailBySubject(boolean openInbox, String name, String subject) {
		if (openInbox) {
			openInbox(name);
		}
		//By subjectName = By.xpath("//td[contains(text(),'" + subject + "')]");
		ReportManager.log("Email openend");
		By subjectName = By.xpath("//div[contains(@class,'inbox-subject') and contains(text(),'" + subject + "')]");
		waitUntilElementPresent(driver, subjectName);
		executeJavaScript(driver, subjectName, "arguments[0].click();");
	}

	public String getRequestIDFromMail(String name, String subject) {
		openMailBySubject(true, name, subject);
		//driver.element().waitToBeReady(emailFrame);

		//driver.getDriver().switchTo().frame(driver.getDriver().findElement(emailFrame));
		String requestID = driver.element().getText(requestId);

		//driver.element().switchToDefaultContent();
		switchingBetweenTabs(driver, 0);
		ReportManager.log("requestId is "+requestID);
		return requestID;

	}
	
	public boolean checkOnEmailWithSubject(boolean openInbox, String name, String subject)
	{
		if (openInbox) {
			openInbox(name);
		}
		ReportManager.log("Email openend");
		//By subjectName = By.xpath("//td[contains(text(),'" + subject + "')]");
		By subjectName = By.xpath("//div[contains(@class,'inbox-subject') and contains(text(),'" + subject + "')]");
	    waitUntilElementPresent(driver, subjectName);
		if(isMsgExistandDisplayed(driver, subjectName, subject))
		{
			//driver.element().switchToDefaultContent();
			switchingBetweenTabs(driver, 0);
			return true;
		}
			
		return false;
	}
	
	public void submitInvitationAction(String actionType,String mail,String invitationTitle)
	{
		openMailBySubject(true, mail, invitationTitle); 
		String actionXpath="";
		//driver.element().waitToBeReady(emailFrame);
		//driver.getDriver().switchTo().frame(driver.getDriver().findElement(emailFrame));
		By unblocklinks=By.xpath("//a[contains(text(),'Unblock links and images')]");
		By userName=By.name("username");
		By password=By.name("password");
		By signIn=By.xpath("//button[@type='submit']");
		driver.element().click(unblocklinks);
		switchingBetweenTabs(driver, 2);
		waitUntilElementPresent(driver, signIn);
		driver.element().type(userName,"marwaYoussef");
		driver.element().type(password,"MarwaYoussef1");
		driver.element().click(signIn);
		waitForElementDisappeared(driver, signIn);
		//sleep(3000);
		if(actionType.equals(Constants.ACCEPTINVITATIONMAIL))
			actionXpath="//a[contains(text(),'"+Constants.ACCEPTINVITATIONMAIL+"')]";
		else if (actionType.equals(Constants.REJECTJOININVITATIONMAIL))
			actionXpath="//a[contains(text(),'"+Constants.REJECTJOININVITATIONMAIL+"')]";
		else if (actionType.equals(Constants.REJECTINVITATIONMAIL))
			actionXpath="(//a[contains(text(),'Reject ')])[2]";
		waitUntilElementPresent(driver, By.xpath(actionXpath));
		executeJavaScript(driver, By.xpath(actionXpath), "arguments[0].click();");
		switchingBetweenTabs(driver, 3);
		waitForPageLoadWithoutSpinner(driver);
	}
	
	public boolean checkEmailWithSubjectNew(String name,String subject) {
		openUrlInNewTab(driver,"https://www.fakemailgenerator.com/inbox/armyspy.com/" + name.split("@")[0], 1);
		By subjectName = By.xpath("//p[contains(text(),'"+ subject +"')]");
	    waitUntilElementPresent(driver, subjectName);
		if(isMsgExistandDisplayed(driver, subjectName, subject))
		{
			driver.element().switchToDefaultContent();
			switchingBetweenTabs(driver, 0);
			return true;
		}
			
		return false;
	}

}
