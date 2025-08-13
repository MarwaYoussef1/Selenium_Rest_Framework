package stc.esport.pages;

import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pojo.Account;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class SignUpPage extends PageBase {

	public WebDriver driver;
	public Properties properties;

	// identifier
	String ownerMailXpath = "(//app-club-owner-sign-up//input[@id='email'])[1]";
	String ownerMobileXpath = "(//app-club-owner-sign-up//input[@id='mobileNumber'])[1]";
	String otherRoleMailXpath = "//input[@id='pcrEmail']";
	String otherRoleMobileXpath = "//input[@id='pcrMobileNumber']";
	String ownerNationalIdXpath = "//*[@id='new-nationalId']";
	String crNumberXpath = "//*[@id='crNumber']";
	String ibanXpath = "//*[@id='new-clubIban']";
	By individualRadio=By.xpath("//input[@value='INDIVIDUAL']");
	By orgRadio=By.xpath("//input[@value='ORGANIZATION']");
	By roleList = By.id("role");
	By clubOwnerName = By.xpath("(//app-club-owner-sign-up//input[@id='name'])[1]");
	By otherRolesName = By.id("pcrName");
	By clubOwnerMail = By.xpath(ownerMailXpath);
	By otherRolesMail = By.xpath(otherRoleMailXpath);
	By clubOwnerCountryCode = By.xpath("(//app-club-owner-sign-up//ng-select[@id='countryCode']//input)[1]");
	By otherRolesCountryCode = By.xpath("//app-player-coach-ref-signup//ng-select[@id='countryCode']//input");
	By clubOwnerMobile = By.xpath(ownerMobileXpath);
	By otherRolesMobile = By.xpath(otherRoleMobileXpath);
	By otherRoleMaleGender = By.xpath("//app-player-coach-ref-signup//i[@class='icon-male']");
	By clubOwnerFemaleGender = By.xpath("(//app-club-owner-sign-up//i[@class='icon-female'])[1]");
	By otherRoleContinueBtn = By.xpath("//app-player-coach-ref-signup//button[@class='continue']");
	By clubOwnerContinueBtn = By.xpath("(//app-club-owner-sign-up//button[@class='continue'])[1]");
	By nationalityList = By.xpath("//ng-select[contains(@id,'countryId')]//input");
	By birthDay = By.xpath("//input[@placeholder='Choose a date']");
	/*
	 * By calendaryear=By.xpath("//span[contains(@id,'mat-calendar-button')]"); By
	 * previousYearsArrow= By.xpath("//button[@aria-label='Previous 24 years']");
	 */
	By clubOwnerNationalID = By.id("new-nationalId");
	By clubOwnerNationalIDFile = By.id("nationalIdFile");
	By clubOwnerEN = By.id("clubName");
	By clubOwnerAR = By.id("clubNameAr");
	By clubLogo = By.id("clubLogo");
	By crNumber = By.id("crNumber");
	By crEndDate = By.xpath("//app-hijri-datepicker//input");
	By crEndDateLabel=By.xpath("(//label[contains(text(),'CR End Date')]//parent::div[@class='form-group'])[2]");
	By cRFile = By.id("crFile");
	By clubIban = By.id("new-clubIban");
	By clubIbanFile = By.id("clubIBANFile");
	By password = By.id("new-password");
	By confirmPassword = By.id("new-confirmPassword");
	By confirmConditions = By.xpath("//mat-checkbox[@formcontrolname='agreeTerms']//descendant::input");
	By signupBtn = By.xpath("//button[text()='Sign up ']");
	By loginBtn = By.xpath("//app-default-signup-form//button[@class='login']");
	By submitClubOwner = By.xpath("//app-club-owner-summary//following::button[@class='continue']");
	By submitOtherRole = By.xpath("//button[@class='continue']");
	By verifyBtn = By.xpath("//button[@type='submit']");
	By getStartedBtn = By.xpath("//button[contains(@class,'ok')]");
	By subTitleOtherRole = By.xpath("//app-signup-success//*[contains(@class,'form-sub-title')]");
	By subTitleClubOwner = By.xpath("//app-check-your-mail//span[@class='form-sub-title']");

	// Constractor
	public SignUpPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}

	private void selectRole(String roleName) {
		By roleNameLocator = By.xpath("//div[@role='option']//span[text()=" + "' " + roleName + " '" + "]");
		driver.element().waitToBeReady(roleList);
		driver.element().click(roleList);
		waitUntilElementPresent(driver, roleNameLocator);
		driver.element().click(roleNameLocator);
	}

	private void addMobileNumber(boolean clubOwner, String mobileCode, String mobileNumber) {
		waitForDropDownWithoutSpinner(driver);
		if (clubOwner) {
			driver.element().type(clubOwnerCountryCode, mobileCode);
			driver.element().keyPress(clubOwnerCountryCode, Keys.ENTER);
			driver.element().type(clubOwnerMobile, mobileNumber);

		} else {
			driver.element().type(otherRolesCountryCode, mobileCode);
			driver.element().keyPress(otherRolesCountryCode, Keys.ENTER);
			driver.element().type(otherRolesMobile, mobileNumber);

		}
	}

	private void selectNationality(String nationality) {
		// driver.element().click(nationalityList);
		driver.element().type(nationalityList, nationality);
		driver.element().keyPress(nationalityList, Keys.ENTER);

	}

	/*
	 * private void selectBirthOfDate(String DOB){ driver.element().click(driver,
	 * birthDay); driver.element().click(calendaryear);
	 * 
	 * String dateParts[] = (DOB).split("/"); String year =dateParts[2]; int
	 * month=Integer.parseInt(dateParts[1]); String day=dateParts[0]; String
	 * monthName= Utils.getMonth(month);
	 * 
	 * 
	 * By yearLocator= By.xpath("//button[@aria-label="+"'"+year+"'"+"]"); By
	 * monthLocator=
	 * By.xpath("//button[contains(@aria-label,"+"'"+monthName+"'"+")]"); By
	 * dayLocator= By.xpath("//button[contains(@aria-label,"+"'"+day+"'"+")]");
	 * 
	 * int i=1; while(!isElementVisible(driver, yearLocator)) {
	 * driver.element().click(previousYearsArrow); i++; }
	 * driver.element().click(yearLocator); driver.element().click(driver,
	 * monthLocator); driver.element().click(dayLocator); }
	 */

	private void selectBirthDay() {
		driver.element().click(birthDay);

		By dayLocator = By.xpath("(//button[contains(@aria-label," + "'" + Utils.getTodayDay() + "'" + ")])[1]");
		driver.element().click(dayLocator);
	}

	private void selectCrEndDate() {
		driver.element().click(crEndDate);

		By dayLocator = By
				.xpath("(//div[@class='ngb-dp-day']//div[text()=" + "'" + Utils.getTodayHijriDay() + "'" + "])[1]");
		driver.element().click(dayLocator);
	}

	private void addOTP(String otp) {
		for (int i = 0; i < otp.length(); i++) {
			By otpLocator = By.xpath("(//input[@type='text'])[" + (i + 1) + "]");
			String input = otp.charAt(i) + "";
			driver.element().type(otpLocator, input);
		}
	}
	
	private boolean fillOtherRoleAccountInfo(Account accountObj)
	{
		driver.element().type(otherRolesName, accountObj.getName());
		addMobileNumber(false, accountObj.getMobileCode(), accountObj.getMobile());
		driver.element().click(otherRoleMaleGender);
		driver.element().click(otherRoleContinueBtn);
		selectBirthDay();
		selectNationality(accountObj.getNationality());
		driver.element().type(password, accountObj.getCreatePassword());
		driver.element().type(confirmPassword, accountObj.getConfirmPassword());
		driver.element().click(confirmConditions);
		driver.element().click(signupBtn);
		driver.element().click(submitOtherRole);
		addOTP(accountObj.getOtp());
		driver.element().click(verifyBtn);
		waitUntilElementPresent(driver, subTitleOtherRole);
		if (isMsgExistandDisplayed(driver, subTitleOtherRole, properties.getProperty("successSignupMsg"))) {
			driver.element().click(getStartedBtn);
			return true;
			}
		
			return false;

		
	}

	public boolean createAccountforOtherRoles(Account accountObj) {
		waitUntilElementClickable(driver, loginBtn);
		if(accountObj.getUserType().equals(Constants.ORGANIZATION_USER_TYPE))
			driver.element().click(orgRadio);
		else
		driver.element().click(individualRadio);
		selectRole(accountObj.getRoleType());
		
		driver.element().type(otherRolesMail, accountObj.getEmail());
		
		return fillOtherRoleAccountInfo(accountObj);
	}
	
	public boolean completeSignUpForm(Account accountObj) {
		//waitUntilElementClickable(driver, loginBtn);
		waitForPageLoadWithoutSpinner(driver);
		return fillOtherRoleAccountInfo(accountObj);
	}

	private void uploadFiles(String fileName, By uploadLocator) {
		uploadFile(driver,fileName, uploadLocator);
		waitForPageLoadWithoutSpinner(driver);
	}

	public boolean createClubOwnerAccount(Account accountObj) {
		driver.element().click(orgRadio);
		selectRole(accountObj.getRoleType());
		driver.element().type(clubOwnerName, accountObj.getName());
		driver.element().type(clubOwnerMail, accountObj.getEmail());
		addMobileNumber(true, accountObj.getMobileCode(), accountObj.getMobile());
		driver.element().click(clubOwnerFemaleGender);
		driver.element().click(clubOwnerContinueBtn);
		selectNationality(accountObj.getNationality());
		driver.element().type(clubOwnerNationalID, accountObj.getClubOwnerNationalID());
		uploadFiles(accountObj.getClubOwnerNationalIDFile(), clubOwnerNationalIDFile);
		uploadFiles(accountObj.getClubLogo(), clubLogo);
		uploadFiles(accountObj.getCrFile(), cRFile);
		uploadFiles(accountObj.getClubIBANFile(), clubIbanFile);
		driver.element().type(clubOwnerEN, accountObj.getClubOwnerEn());
		driver.element().type(clubOwnerAR, accountObj.getClubOwnerAr());
		driver.element().type(crNumber, accountObj.getCrNumber());
		selectCrEndDate();
		waitForPageLoadWithoutSpinner(driver);
		String crEndDate=driver.element().getText(crEndDateLabel).trim().replace("CR End Date","").replace("/", "-").replace(" ", "");
		accountObj.setCrDate(crEndDate);
		driver.element().type(clubIban, accountObj.getClubIBAN());
		driver.element().type(password, accountObj.getCreatePassword());
		driver.element().type(confirmPassword, accountObj.getConfirmPassword());
		sleep(3000);
		driver.element().click(confirmConditions);
		sleep(3000);
		driver.element().click(signupBtn);
		executeJavaScript(driver, submitClubOwner, "arguments[0].click();");
		addOTP(accountObj.getOtp());
		driver.element().click(verifyBtn);
		waitUntilElementPresent(driver, subTitleClubOwner);
		if (isMsgExistandDisplayed(driver, subTitleClubOwner, properties.getProperty("successClubOwnerMsg"))) {
			driver.element().click(getStartedBtn);
			return true;
		} else {
			return false;

		}
	}

	public boolean checkBlockSignUpMsgs(Account accountObj) {
		String expectedOwnerBlockMsg = properties.getProperty("RejectOwnerBlockMsg");
		String expectedRoleBlockMsg = properties.getProperty("RejectRoleBlockMsg");
		ArrayList<Boolean> checks = new ArrayList<Boolean>();
	
	
		if (accountObj.getRoleType().equals(Constants.CLUBOWNERROLE)) {
			driver.element().click(orgRadio);
			selectRole(accountObj.getRoleType());
			driver.element().type(clubOwnerName, accountObj.getName());
			driver.element().type(clubOwnerMail, accountObj.getEmail());
			driver.element().keyPress(clubOwnerMail, Keys.ENTER);
			addMobileNumber(true, accountObj.getMobileCode(), accountObj.getMobile());
			checks.add(checkBlockMsg(ownerMailXpath, expectedOwnerBlockMsg));
			checks.add(checkBlockMsg(ownerMobileXpath, expectedOwnerBlockMsg));

			accountObj.setEmail(null);
			driver.element().type(clubOwnerMail, accountObj.getEmail());
			accountObj.setMobile(null);
			addMobileNumber(true, accountObj.getMobileCode(), accountObj.getMobile());
			driver.element().click(clubOwnerFemaleGender);

			driver.element().click(clubOwnerContinueBtn);
			selectNationality(accountObj.getNationality());
			driver.element().type(clubOwnerNationalID, accountObj.getClubOwnerNationalID());
			driver.element().keyPress(clubOwnerNationalID, Keys.ENTER);
			driver.element().type(crNumber, accountObj.getCrNumber());
			driver.element().type(clubIban, accountObj.getClubIBAN());

			checks.add(checkBlockMsg(ownerNationalIdXpath, expectedOwnerBlockMsg));
			checks.add(checkBlockMsg(crNumberXpath, expectedOwnerBlockMsg));
			checks.add(checkBlockMsg(ibanXpath, expectedOwnerBlockMsg));

			driver.element().click(confirmConditions);
			driver.element().click(signupBtn);
		}
		
		else 
		{
			driver.element().click(individualRadio);
			selectRole(accountObj.getRoleType());
			driver.element().type(otherRolesName, accountObj.getName());
			driver.element().type(otherRolesMail, accountObj.getEmail());
			driver.element().keyPress(otherRolesMail, Keys.ENTER);
			addMobileNumber(false, accountObj.getMobileCode(), accountObj.getMobile());
			checks.add(checkBlockMsg(otherRoleMailXpath, expectedRoleBlockMsg));
			checks.add(checkBlockMsg(otherRoleMobileXpath, expectedRoleBlockMsg));
			
		}

		boolean isAllTrue = checks.stream().allMatch(val -> val == Boolean.TRUE);
		return isAllTrue;

	}

	private boolean checkBlockMsg(String blockedfieldXpath, String expectedmsg) {
		By invalidMsg = By.xpath("(" + blockedfieldXpath + "//following::div[contains(@class,'invalid-feedback')]//div)[1]");
		if (isMsgExistandDisplayed(driver, invalidMsg, expectedmsg)) {
			return true;
		}
		return false;
	}
	
	public boolean checkAutoFilledFields()
	{
		waitForPageLoadWithoutSpinner(driver);
		sleep(2000);
		driver.element().waitToBeReady(otherRoleContinueBtn);
		boolean roleAutoFilled=driver.element().getAttribute(roleList, "class").contains("ng-select-disabled");
		boolean emailAutoFilled=driver.element().getAttribute(otherRolesMail, "disabled") !=null;
		if(roleAutoFilled && emailAutoFilled)
			return true;
		return false;
	}
}
