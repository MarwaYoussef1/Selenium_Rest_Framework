package stc.esport.testcases;

	import java.io.IOException;

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

import stc.esport.api.services.LoginService;
import stc.esport.api.services.RequestsService;
import stc.esport.base.TestBase;
import stc.esport.pages.DashBoardPage;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;
import stc.esport.pages.SignUpPage;
import stc.esport.pages.fakeMailPage;
import stc.esport.pojo.Account;
import stc.esport.testdata.TestData;
import stc.esport.utils.Constants;



	public class SignUpTest extends TestBase{
		// Variables
			LoginPage loginPageObj;
			HomePage homePageObj;
			DashBoardPage dashBoardPageObj;
			SignUpPage signUpPageObj;
			fakeMailPage fakeMailPageObj;
			LoginService loginServiceObj;
		    RequestsService reqServiceObj;
			
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
				homePageObj= new HomePage(driver, properties);
				dashBoardPageObj = new DashBoardPage(driver, properties);
				signUpPageObj= new SignUpPage(driver,properties);
				loginServiceObj= new LoginService(properties);
				fakeMailPageObj= new fakeMailPage(driver,properties);

			}

			@BeforeTest
			public void BeforeTest() {
				ReportManager.log("BeforeTest");
			}
			
			@DataProvider(name = "otherRolesAccounts")
			public Object[][] otherRolesAccounts() throws InvalidFormatException, IOException {
				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testOtherRolesAccounts");
				return data;
			}
			
			
			@Test(dataProvider = "otherRolesAccounts")
			public void testOtherRolesSignUP(String roleType,String roleCategory){
				ReportManager.log("==============  Start Sign up Test with role = "+ roleType+" ==============");
				
				Account accountObj = new Account(properties);
				accountObj.setRoleType(roleType);
				accountObj.setUserType(roleCategory);
						
				homePageObj.clickLoginLink();
				loginPageObj.clickOnCreateNewAccBtn();
				
				Validations.assertThat().object(signUpPageObj.createAccountforOtherRoles(accountObj));
				ReportManager.log("Account created successfully");
				
				
				Validations.assertThat().object(loginPageObj.login(accountObj.getEmail(), accountObj.getCreatePassword()));
				ReportManager.log("User logged in with the new account successfully");
				
				Validations.assertThat().object(dashBoardPageObj.checkProfileName(roleType));
				ReportManager.log("Profile name displayed according to loggedin user role");
				
				dashBoardPageObj.logout();
				ReportManager.log("User logged out successfully");
				ReportManager.log("==============  End Sign up Test with role = "+ roleType+" ==============");
			}

			@Test()
			public void testClubOwnerSignUP() {
				ReportManager.log("============== Start club owner signup Test   ==============");
				String userCstAdmin = properties.getProperty("superAdmin");
				String userCstAdminPw = properties.getProperty("superAdminPW");
				
		    	Account accountObj = new Account(properties);
				accountObj.setRoleType(Constants.CLUBOWNERROLE);
				accountObj.setUserType(Constants.ORGANIZATION_USER_TYPE);
        			  	
                homePageObj.clickLoginLink();
                loginPageObj.clickOnCreateNewAccBtn();
			  	
			  	Validations.assertThat().object(signUpPageObj.createClubOwnerAccount(accountObj));
                ReportManager.log("User logged in with the new account successfully");
                
               
                Validations.assertThat().object(loginPageObj.login(accountObj.getEmail(), accountObj.getCreatePassword()));
				ReportManager.log("User logged in with the new account successfully");
				
				Validations.assertThat().object(dashBoardPageObj.checkProfileName(Constants.CLUBOWNERPENDING));
				ReportManager.log("Profile name displayed according to loggedin user role");
				
				dashBoardPageObj.logout();
				ReportManager.log("User logged out successfully");
				
			    String req=	fakeMailPageObj.getRequestIDFromMail(accountObj.getEmail(),properties.getProperty("clubOwnerReqSubject") );
			    
				String token = loginServiceObj.login(userCstAdmin,userCstAdminPw);
		    	reqServiceObj= new RequestsService (token,apiObj);
		    	Validations.assertThat().object(reqServiceObj.filterRequestsByRequestId(req)).isNotNull().perform();
		    	ReportManager.log("Request id for the club owner returned successfully");

		    	ReportManager.log("============== End club owner signup Test   ==============");
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
