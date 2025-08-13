package stc.esport.testcases;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.shaft.tools.io.ReportManager;
import com.shaft.validation.Validations;

import stc.esport.api.services.LoginService;
import stc.esport.api.services.RequestsService;
import stc.esport.base.TestBase;
import stc.esport.pages.DashBoardPage;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;




public class LoginTest extends TestBase{
	// Variables
		LoginPage loginPageObj;
		HomePage homePageObj;
		DashBoardPage dashBoardPageObj;
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
			loginPageObj = new LoginPage(driver, properties);
			homePageObj = new HomePage(driver, properties);
			dashBoardPageObj = new DashBoardPage(driver, properties);
			
			
		}

		@BeforeTest
		public void BeforeTest() {
			ReportManager.log("BeforeTest");
		}
		
		

	    @Test()
		public void testLogin() {
			ReportManager.log("============== Valid Login Test start  ==============");
			ReportManager.logDiscrete("============== Valid Login Test start  ==============");
			String superUser = properties.getProperty("superAdmin");
			String superPass = properties.getProperty("superAdminPW");
			ReportManager.log("Login with acpUser & Pass : " + superUser );
			homePageObj.clickLoginLink();
			boolean loggedIn = loginPageObj.login(superUser, superPass);
			Validations.assertThat().object(loggedIn).isTrue().perform();
			dashBoardPageObj.logout();
			ReportManager.log("==============  Valid Login Test End ==============");
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
