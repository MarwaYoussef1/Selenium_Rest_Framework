package stc.esport.testcases.crm;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shaft.driver.SHAFT.Validations;
import com.shaft.tools.io.ReportManager;

import stc.esport.api.pojo.SignUpData;
import stc.esport.api.services.SignUpService;
import stc.esport.base.TestBase;
import stc.esport.pages.DashBoardPage;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;
import stc.esport.pages.SignUpPage;
import stc.esport.pages.fakeMailPage;
import stc.esport.pages.crm.CRMListPage;
import stc.esport.pojo.Account;
import stc.esport.testdata.TestData;
import stc.esport.utils.Constants;

public class CRMTests extends TestBase {
	// Variables
	LoginPage loginPageObj;
	HomePage homePageObj;
	DashBoardPage dashBoardPageObj;
	CRMListPage crmListPageObj;
	fakeMailPage fakeMailPageObj;
	SignUpPage signUpPageObj;

	@BeforeSuite
	public void BeforeSuite() {
		ReportManager.log("BeforeSuite");
		ReportManager.log("");
	}

	@BeforeClass
	public void BeforeClass() {
		ReportManager.log("BeforeClass");
	}

	@BeforeMethod
	public void BeforeMethod() {
		ReportManager.log("BeforeMethod");
		openBrowser();
		getBaseApiObj();
		loginPageObj = new LoginPage(driver, properties);
		homePageObj = new HomePage(driver, properties);
		dashBoardPageObj = new DashBoardPage(driver, properties);
		crmListPageObj = new CRMListPage(driver, properties);
		fakeMailPageObj = new fakeMailPage(driver, properties);
		signUpPageObj = new SignUpPage(driver, properties);

	}

	@BeforeTest
	public void BeforeTest() {
		ReportManager.log("BeforeTest");
	}
	
	@DataProvider(name = "testCRMSearchData")
	public Object[][] otherRolesAccounts() throws InvalidFormatException, IOException {
		Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testCRMSearch");
		return data;
	}

	@Test()
	public void testCRMApprove() {
		ReportManager.log("============== Start CRM Approve Test   ==============");
		String crmUserEmail = properties.getProperty("crmuseremail");
		String crmUserPw = properties.getProperty("crmuserpassword");
		String crmUserName = properties.getProperty("crmUserName");
		boolean requestApproved;

		// Step1 SignUp by new user through APIs and get request ID
		SignUpService signupService = new SignUpService(apiObj, properties);
		ArrayList<Object> data = signupService.defaultSignUp();
		SignUpData SignUp = (SignUpData) data.get(0);
		String userEmail = SignUp.getEmail();
		String userPassword = SignUp.getPassword();
		String requestId = data.get(1).toString();
		Validations.assertThat().object(requestId).isNotNull().perform();

		// Step2 club owner login to check on pending status
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(userEmail, userPassword)).isTrue().perform();
		ReportManager.log("Login done  successfully by User for checking on pending status");
		boolean statusMsgDisplayed = dashBoardPageObj.checkStatusMsg(properties.getProperty("RequestPendingMsg"));
		Validations.assertThat().object(statusMsgDisplayed).isTrue().perform();
		ReportManager.log("'Your Request still pending' message displayed successfully. ");
		dashBoardPageObj.logout();
		ReportManager.log("Club Owner user log out ");

		// Step3 Login by CRM User to approve
		homePageObj.clickLoginLink();
		
		Validations.assertThat().object(loginPageObj.login(crmUserEmail, crmUserPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by User for manage CRM requests =" + crmUserName);
		dashBoardPageObj.navigateToCRM();
		requestApproved = crmListPageObj.approveCRMRequest(requestId,crmUserName);
		Validations.assertThat().object(requestApproved).isTrue().perform();
		ReportManager.log("CRM Approve action  done  successfully by User for manage CRM requests ");

		dashBoardPageObj.navigateToCRM();
		dashBoardPageObj.logout();
		ReportManager.log("CRM user log out ");

		// Step4 check that club owner received an email
		boolean requestApprovedSent = fakeMailPageObj.checkOnEmailWithSubject(true, userEmail.split("@")[0],
				properties.getProperty("ApproveEmailSubject"));
		Validations.assertThat().object(requestApprovedSent).isTrue().perform();
		ReportManager.log("Approved request email sent successfully to Club owner ");

		ReportManager.log("============== End CRM Approve Test   ==============");
	}

	@Test()
	public void testCRMReject() {
		ReportManager.log("============== Start CRM Reject Test   ==============");
		String crmUserEmail = properties.getProperty("crmuseremail");
		String crmUserPw = properties.getProperty("crmuserpassword");
		String crmUserName = properties.getProperty("crmUserName");
		String errorMsg = properties.getProperty("InvalidLoginMsg");
		boolean requestRejected;

		// Step1 SignUp by new user through APIs and get request ID
		SignUpService signupService = new SignUpService(apiObj, properties);
		ArrayList<Object> data = signupService.defaultSignUp();
		SignUpData SignUp = (SignUpData) data.get(0);
		String userEmail = SignUp.getEmail();
		String userPassword = SignUp.getPassword();
		String requestId = data.get(1).toString();
		Validations.assertThat().object(requestId).isNotNull().perform();

		// Step2 Login by CRM User to Reject
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(crmUserEmail, crmUserPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by User for manage CRM requests =" + crmUserName);
		dashBoardPageObj.navigateToCRM();
		requestRejected = crmListPageObj.rejectCRMRequest(false, requestId,crmUserName);
		Validations.assertThat().object(requestRejected).isTrue().perform();
		ReportManager.log("CRM Reject action  done  successfully by User for manage CRM requests ");
		dashBoardPageObj.navigateToCRM();
		dashBoardPageObj.logout();
		ReportManager.log("CRM user log out ");

		// Step3 Login by club owner to check on invalid login msg
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.invalidLogin(userEmail, userPassword, errorMsg)).isTrue().perform();
		ReportManager.log("Invalid login message displayed  successfully for club owner ");

		// Step3 check that club owner received an email
		boolean requestRejectSent = fakeMailPageObj.checkOnEmailWithSubject(true, userEmail.split("@")[0],
				properties.getProperty("RejectEmailSubject"));
		Validations.assertThat().object(requestRejectSent).isTrue().perform();
		ReportManager.log("Rejected request  email sent successfully to Club owner ");

		// Step 4 club owner can use same data to sign up
		loginPageObj.clickOnCreateNewAccBtn();
		Account accountObj = new Account(properties);
		accountObj.setRoleType(Constants.CLUBOWNERROLE);
		accountObj.setEmail(userEmail);
		accountObj.setMobile(SignUp.getMobileNumber());
		accountObj.setClubOwnerNationalID(SignUp.getNationalId());
		accountObj.setCrNumber(SignUp.getCrNumber());
		accountObj.setClubIBAN(SignUp.getClubIBAN());
		Validations.assertThat().object(signUpPageObj.createClubOwnerAccount(accountObj));
		ReportManager.log("User Sign up with the new account successfully");

		ReportManager.log("============== End CRM Reject Test   ==============");
	}

	@Test()
	public void testCRMRejectBlock() {
		ReportManager.log("============== Start CRM RejectBlock Test   ==============");
		String crmUserEmail = properties.getProperty("crmuseremail");
		String crmUserPw = properties.getProperty("crmuserpassword");
		String crmUserName = properties.getProperty("crmUserName");
		String errorMsg = properties.getProperty("InvalidLoginMsg");
		boolean requestRejected;

		// Step1 SignUp by new user through APIs and get request ID
		SignUpService signupService = new SignUpService(apiObj, properties);
		ArrayList<Object> data = signupService.defaultSignUp();
		SignUpData SignUp = (SignUpData) data.get(0);
		String userEmail = SignUp.getEmail();
		String userPassword = SignUp.getPassword();
		String requestId = data.get(1).toString();
		Validations.assertThat().object(requestId).isNotNull().perform();

		Account accountObj = new Account(properties);
		accountObj.setRoleType(Constants.CLUBOWNERROLE);
		accountObj.setEmail(userEmail);
		accountObj.setMobile(SignUp.getMobileNumber());
		accountObj.setClubOwnerNationalID(SignUp.getNationalId());
		accountObj.setCrNumber(SignUp.getCrNumber());
		accountObj.setClubIBAN(SignUp.getClubIBAN());
		Account playerAccountObj = accountObj;
		playerAccountObj.setRoleType(Constants.CLUBPLAYERROLE);

		// Step2 Login by CRM User to Reject
		homePageObj.clickLoginLink();
		//BrowserActions.refreshCurrentPage(driver);
		Validations.assertThat().object(loginPageObj.login(crmUserEmail, crmUserPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by User for manage CRM requests =" + crmUserName);
		dashBoardPageObj.navigateToCRM();
		requestRejected = crmListPageObj.rejectCRMRequest(true, requestId,crmUserName);
		Validations.assertThat().object(requestRejected).isTrue().perform();
		ReportManager.log("CRM Reject action  done  successfully by User for manage CRM requests ");
		dashBoardPageObj.navigateToCRM();
		dashBoardPageObj.logout();
		ReportManager.log("CRM user log out ");

		// Step3 Login by club owner to check on invalid login msg
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.invalidLogin(userEmail, userPassword, errorMsg)).isTrue().perform();
		ReportManager.log("Invalid login message displayed  successfully for club owner ");

		// Step 4 Player couldn't sign up by same data again
		loginPageObj.clickOnCreateNewAccBtn();
		boolean playerUserBlocked = signUpPageObj.checkBlockSignUpMsgs(playerAccountObj);
		Validations.assertThat().object(playerUserBlocked).isTrue().perform();
		ReportManager.log("Rejected block message displayed successfully for same email,mobile number for player");

		// Step 5 Club owner couldn't sign up by same data again
		accountObj.setRoleType(Constants.CLUBOWNERROLE);
		boolean userBlocked = signUpPageObj.checkBlockSignUpMsgs(accountObj);
		Validations.assertThat().object(userBlocked).isTrue().perform();
		ReportManager.log(
				"Rejected block message displayed successfully for same email,mobile number,National Id,Crnumber,Iban ");

		// Step6 check that club owner received an email
		boolean requestRejectSent = fakeMailPageObj.checkOnEmailWithSubject(true, userEmail.split("@")[0],
				properties.getProperty("RejectEmailSubject"));
		Validations.assertThat().object(requestRejectSent).isTrue().perform();
		ReportManager.log("Rejected request  email sent successfully to Club owner ");

		ReportManager.log("============== End CRM Reject Test   ==============");
	}

	@Test()
	public void testCRMRequireAnAction() {
		ReportManager.log("============== Start CRM Require An Action Test   ==============");
		String crmUserEmail = properties.getProperty("crmuseremail");
		String crmUserPw = properties.getProperty("crmuserpassword");
		String crmUserName = properties.getProperty("crmUserName");
		boolean requireAnActionSent;

		Account accountObj = new Account(properties);
		accountObj.setRoleType(Constants.CLUBOWNERROLE);

		// Step1 SignUp by new user through APIs and get request ID
		SignUpService signupService = new SignUpService(apiObj, properties);
		ArrayList<Object> data = signupService.defaultSignUp();
		SignUpData SignUp = (SignUpData) data.get(0);
		String userEmail = SignUp.getEmail();
		String userPassword = SignUp.getPassword();
		String requestId = data.get(1).toString();
		Validations.assertThat().object(requestId).isNotNull().perform();

		// Step2 Login by User to perform require an action
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(crmUserEmail, crmUserPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by User for manage CRM requests =" + crmUserName);
		dashBoardPageObj.navigateToCRM();
		requireAnActionSent = crmListPageObj.requireAnActionOnCRMRequest(requestId,crmUserName);
		Validations.assertThat().object(requireAnActionSent).isTrue().perform();
		ReportManager.log("CRM require an action  done  successfully by User for manage CRM requests ");
		dashBoardPageObj.navigateToCRM();
		dashBoardPageObj.logout();
		ReportManager.log("CRM user log out ");

		// Step3 check that club owner received an email
		boolean requestSent = fakeMailPageObj.checkOnEmailWithSubject(true, userEmail.split("@")[0],
				properties.getProperty("RequireActionStatus"));
		Validations.assertThat().object(requestSent).isTrue().perform();
		ReportManager.log("Require an Action Sent successfully to Club owner ");

		// Step4 club owner login to perform resubmit
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(userEmail, userPassword)).isTrue().perform();
		ReportManager.log("Login done  successfully by User for resubmit application =" + userEmail);
		boolean statusMsgDisplayed = dashBoardPageObj.checkStatusMsg(properties.getProperty("RequireAnActionMsg"));
		Validations.assertThat().object(statusMsgDisplayed).isTrue().perform();
		ReportManager.log("'Your Request Requires Actions' message displayed successfully. ");
		boolean applicationSubmitted = dashBoardPageObj.resubmitApp(accountObj);
		Validations.assertThat().object(applicationSubmitted).isTrue().perform();
		ReportManager.log("Apllication re-submit done  successfully by User ");
		dashBoardPageObj.logout();
		ReportManager.log("Club Owner user log out ");

		// Step 5 check on CRM request status
		homePageObj.clickLoginLink();
		Validations.assertThat().object(loginPageObj.login(crmUserEmail, crmUserPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by User for manage CRM requests =" + crmUserName);
		dashBoardPageObj.navigateToCRM();
		boolean actionTakenStatusAppeared = crmListPageObj.checkCRMRequestStatus(requestId,
				Constants.CRMREQUESTACTIONTAKEN);
		Validations.assertThat().object(actionTakenStatusAppeared).isTrue().perform();
		ReportManager.log("Action taken Status displayed successfully");
		dashBoardPageObj.logout();
		ReportManager.log("CRM user log out ");

		ReportManager.log("============== End CRM Require An Action Test   ==============");
	}
	
	@Test()
	public void testCRMDDetails() {
		ReportManager.log("============== Start CRM Details Test   ==============");
		String crmUserEmail = properties.getProperty("crmuseremail");
		String crmUserPw = properties.getProperty("crmuserpassword");
		String crmUserName = properties.getProperty("crmUserName");
		boolean requestDetailsDisplayed;

		Account accountObj = new Account(properties);
		accountObj.setRoleType(Constants.CLUBOWNERROLE);
			  	
        homePageObj.clickLoginLink();
       loginPageObj.clickOnCreateNewAccBtn();
	  	
	 	Validations.assertThat().object(signUpPageObj.createClubOwnerAccount(accountObj));
        ReportManager.log("User logged in with the new account successfully");

        String req=	fakeMailPageObj.getRequestIDFromMail(accountObj.getEmail(),properties.getProperty("clubOwnerReqSubject") );
        
		// Step2 Login by CRM User to check Request Details
		Validations.assertThat().object(loginPageObj.login(crmUserEmail, crmUserPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by User for manage CRM requests =" + crmUserName);
		dashBoardPageObj.navigateToCRM();
		requestDetailsDisplayed = crmListPageObj.CRMRequestDetails(req, accountObj);
		Validations.assertThat().object(requestDetailsDisplayed).isTrue().perform();
		ReportManager.log("CRM Request details displayed  successfully by User for manage CRM requests ");

		dashBoardPageObj.navigateToCRM();
		dashBoardPageObj.logout();
		ReportManager.log("CRM user log out ");

		ReportManager.log("============== End CRM Approve Test   ==============");
	}
	
	@Test(dataProvider = "testCRMSearchData")
	public void testCRMSearch(String searchType,String searchValue) {
		ReportManager.log("============== Start CRM Search Test   ==============");
		String adminEmail = properties.getProperty("superAdmin");
		String adminPw = properties.getProperty("superAdminPW");
		boolean requestFound;

		 homePageObj.clickLoginLink();
	
		Validations.assertThat().object(loginPageObj.login(adminEmail, adminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by admin User for search on CRM requests =" + adminEmail);
		dashBoardPageObj.navigateToCRM();
		requestFound = crmListPageObj.searchOnCRM(searchType, searchValue);
		Validations.assertThat().object(requestFound).isTrue().perform();
		ReportManager.log("CRM Request search  done  successfully ");

		dashBoardPageObj.navigateToCRM();
		dashBoardPageObj.logout();
		ReportManager.log("CRM user log out ");

		

		ReportManager.log("============== End CRM Approve Test   ==============");
	}

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		ReportManager.log("AfterMethod");
		closeBrowser();
	}

	@AfterTest
	public void AfterTest() {
		ReportManager.log("AfterTest");
	}

	@AfterClass
	public void AfterClass() {
		ReportManager.log("After Class");
	}

	@AfterSuite
	public void AfterSuite() {
		ReportManager.log("After Suite");
	}

}
