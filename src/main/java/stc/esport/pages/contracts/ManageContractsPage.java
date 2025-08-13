package stc.esport.pages.contracts;

import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;


public class ManageContractsPage extends PageBase{

		public WebDriver driver;
		public Properties properties;
		
		
		// identifier
		By uploadContractBtn=By.xpath("//a[contains(@class,'upload-contract-btn')]");
		By contractStatus= By.xpath("//p[contains(@class,'status')]");
		By dropDownIcon= By.xpath("//button[@class='dropbtn']");
		By viewContract= By.xpath("//a[contains(@href,'/contracts/contract-details/')]");
		By backArrow= By.xpath("//a[contains(@href,'my-teams') and contains(@class,'title-icon')]");
		By popupMsg= By.xpath("//div[contains(@class,'cancel-toaster')]//div[contains(@id,'container')]");
		
		// Constractor
		public ManageContractsPage(WebDriver driver, Properties properties) {
			this.driver = driver;
			this.properties = properties;
		}
		
		public void pressOnBackArrow() {
			driver.element().click(backArrow);
		}
		private By getMemberLocator (String memberName) {
			By locator= By.xpath("//p[@title="+"'"+memberName+"'"+"]");
			return locator;
		}
		
		public void clickOnUploadContractBtn(String memberName) {
			By locator= By.xpath("(//p[@title="+"'"+memberName+"'"+"]//following::a[contains(@class,'upload-contract-btn')])[1]");
			driver.element().click(locator);

		}
		
		public boolean getContractStatus(String status) {
		return	isMsgExistandDisplayed(driver, contractStatus, status);
		}
		
		public void selectFromDropDownList(String optionName) {
			driver.element().click(dropDownIcon);
			driver.element().click(viewContract);
		}

		public boolean getClosedWindowMsg() {
			String closedWindowMsg= properties.getProperty("transferWindowClosedMsg");
			return isMsgExistandDisplayed(driver,popupMsg , closedWindowMsg);
		}
}
