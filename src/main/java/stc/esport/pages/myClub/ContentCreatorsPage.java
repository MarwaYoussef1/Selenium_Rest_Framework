package stc.esport.pages.myClub;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pages.HomePage;
import stc.esport.utils.Constants;

public class ContentCreatorsPage extends PageBase {


	public WebDriver driver;
	public Properties properties;
	HomePage homePageObj;

	public ContentCreatorsPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}
	
	By addBtn= By.xpath("//i[contains(@class,'add-icon')]");
	By memberMail = By.id("email");
	By addMemberMail = By.xpath("//button[contains(@class,'add-mail-btn')]");
	By uploadPhoto=By.id("file");
	By removePhotoBtn=By.xpath("//button[contains(@class,'remove')]");
	By saveEditChangesBtn=By.xpath("//button[@type='submit']");
	By showInProfileToggle=By.id("showInClubProfile-input");
	By sendInvitation = By.xpath("//button[contains(@class,'save-btn')]");
	By cancelInvitation= By.xpath("//span[contains(text(),'Cancel Invitation')]//parent::button");
	By delete= By.xpath("//span[contains(text(),'Delete')]//parent::button");
	By edit= By.xpath("//span[contains(text(),'Edit')]//parent::button[@mat-menu-item]");
	By invitationPopUpMsg= By.id("swal2-html-container") ;
	By proceedBtn= By.xpath("//button[@class='swal2-confirm']");
	By successToast = By.id("swal2-html-container");
	
	public boolean addContentCreator(String mailId,String expected) {
		By invitationMail=By.xpath("//*[contains(@class,'chip-content')]//span[contains(text(),'"+mailId+"')]");
		String addSuccessMsg = properties.getProperty("contentCreatorAddMsg");
		driver.element().click(addBtn);
		driver.element().type(memberMail,mailId);
		driver.element().click(addMemberMail);
		waitForPageLoadWithoutSpinner(driver);
		waitUntilElementPresent(driver, invitationMail);
		driver.element().click(sendInvitation);
		if(isMsgExistandDisplayed(driver, successToast, addSuccessMsg))
		{
			return checkCardDetails(expected, Constants.INVITATIONPENDING);
		}
         
		return false;
		
	}
	
	private String getCardNameLocator(String name)
	{
		String cardName= "//div[contains(@class,'content-creator-name')  and contains (text(),'"+name+"')]";
		return cardName;
	}
	
	private void clickCardAction(String name,String actionType)
	{
		By icon= By.xpath(getCardNameLocator(name)+"//preceding::button[contains(@class,'content-actions')][1]");
		driver.element().click(icon);
		if(actionType.equals(Constants.CANCELINVITATION))
		{
			waitUntilElementPresent(driver, cancelInvitation);
			driver.element().click(cancelInvitation);
		}
		else if(actionType.equals(Constants.DELETE))
		{
			waitUntilElementPresent(driver, delete);
			driver.element().click(delete);
		}
		else if(actionType.equals(Constants.Edit))
		{
			waitUntilElementPresent(driver, edit);
			driver.element().click(edit);
		}
		
			
		
	}

	public boolean checkCardDetails(String cardName,String expectedStatus) {
		
	   By cardText = By.xpath(getCardNameLocator(cardName));
	   By status= By.xpath(getCardNameLocator(cardName)+"//following::div[1]");
	   String actualStatus = driver.element().getText(status);
	  
	   if(isElementExist(driver, cardText, cardName) && expectedStatus.equals(actualStatus))
                 return true;
       return false;
		
	}
	public boolean checkCardPhoto(String cardName,String expectedImage)
	{
		 By cardImage = By.xpath(getCardNameLocator(cardName)+"//preceding::img[contains(@src,'"+expectedImage+"')][1]");
		 if(isElementExist(driver, cardImage, expectedImage))
	                 return true;
	       return false;
	}
	
	public boolean removeCancelInvitation(String name,String actionType)
	{
		By cardText = By.xpath(getCardNameLocator(name));
		String successMsg = null;
		clickCardAction(name,actionType);
		waitUntilElementPresent(driver, proceedBtn);
		if(actionType.equals(Constants.CANCELINVITATION))
			successMsg =properties.getProperty("contentCreatorCancel");
		else
			successMsg =properties.getProperty("contentCreatorDelete");
		if(isMsgExistandDisplayed(driver, invitationPopUpMsg, successMsg))
		{
			driver.element().click(proceedBtn);
			waitUntilElementPresent(driver, successToast);
			waitForPageLoadWithoutSpinner(driver);
		}
		 if(!isElementExist(driver, cardText, name))
			 return true;
		return false;
	}
	
	public boolean editCardDetails(String cardName,String uploadfile,boolean removePhoto,boolean toggle)
	{
		clickCardAction(cardName,Constants.Edit);
		waitUntilElementPresent(driver, showInProfileToggle);
		if(toggle)
		driver.element().click(showInProfileToggle);
		
		if(removePhoto)
		{
			waitUntilElementPresent(driver, removePhotoBtn);
			driver.element().click(removePhotoBtn);
		}
		else {
			waitUntilElementPresent(driver, uploadPhoto);
			uploadFile(driver, uploadfile, uploadPhoto);
		}
		driver.element().click(saveEditChangesBtn);
		waitUntilElementPresent(driver, successToast);
		waitForPageLoadWithoutSpinner(driver);
		return true;
	}
	
	
	


}
