package stc.esport.pages.myClub;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import com.shaft.tools.io.ReportManager;

import stc.esport.base.PageBase;
import stc.esport.pages.publicprofiles.ClubPublicProfilePage;

public class MyClubPage extends PageBase {


	public WebDriver driver;
	public Properties properties;
	public ClubPublicProfilePage clubPublicProfilePageObj;
	

	public MyClubPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
		clubPublicProfilePageObj = new ClubPublicProfilePage(driver, properties);
	}
	
	By callForMemberBtn= By.xpath("//a[contains(@class,'call-members')]//span");
	By achievementsTab= By.xpath("//i[contains(@class,'icon-achievment')]");
	By contentCreators= By.xpath("//i[contains(@class,'icon-eye')]//parent::span");
	By viewAsPublicProfile= By.xpath("//a[contains(@class,'view-as profile')]//span");
	
	public boolean isCallForMemberBtnExist() {
		waitForPageLoadWithoutSpinner(driver);
		if(isElementExist(driver, callForMemberBtn, "Call For Member"))
			return true;
		return false;
	}
	
	public boolean isAchievementsTabExist() {
		waitForPageLoadWithoutSpinner(driver);
		if(isElementExist(driver, achievementsTab, "Achievements"))
			return true;
		return false;
		
	}
	
	public void navigateToContentCreators() {
		waitForPageLoadWithoutSpinner(driver);
		driver.element().waitToBeReady(contentCreators);
		driver.element().click(contentCreators);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on ContentCreators from the side Menu");
	}
	
	public void navigateToPublicProfile() {
		waitForPageLoadWithoutSpinner(driver);
		driver.element().waitToBeReady(viewAsPublicProfile);
		driver.element().click(viewAsPublicProfile);
		waitForPageLoadWithoutSpinner(driver);
		ReportManager.log("Click on View As public profile");
	}
	
	public boolean checkIfContentCreatorInProfile(String name)
	{
		boolean found;
		navigateToPublicProfile();
		found=clubPublicProfilePageObj.searchOnContentCreator(name);
		if(found)
		{
			clubPublicProfilePageObj.exitPublicProfile();
			return true;
		}
		return false;
			
	}
	



}
