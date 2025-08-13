package stc.esport.pages.invitations;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.utils.Constants;

public class RejectInvitationPage extends PageBase {

	WebDriver driver;

	// identifier
	By invitationRejectTitle = By.xpath("//h2[@class='form-title']");
	By goHome = By.xpath("//button[@class='continue']");

	// Constractor
	public RejectInvitationPage(WebDriver driver) {
		this.driver = driver;

	}

	public boolean checkInvitationMsg() {
         waitForPageLoadWithoutSpinner(driver);
         waitUntilElementPresent(driver, goHome);
		if (isElementExist(driver, invitationRejectTitle, Constants.INVITATIONREJECTED)) {
			waitUntilElementClickable(driver, goHome);
			driver.element().click(goHome);
			switchingBetweenTabs(driver, 0);
			
			return true;

		}
		return false;
	}

}
