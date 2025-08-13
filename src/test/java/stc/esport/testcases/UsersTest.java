package stc.esport.testcases;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.shaft.tools.io.ReportManager;
import com.shaft.validation.Validations;

import stc.esport.base.TestBase;
import stc.esport.pages.DashBoardPage;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;
import stc.esport.pages.fakeMailPage;
import stc.esport.pages.users.PasswordPage;
import stc.esport.pages.users.UsersManagementPage;
import stc.esport.pojo.User;



public class UsersTest extends TestBase {

	LoginPage LoginPage;
	HomePage HomePage;
	DashBoardPage dashBoardPageObj;
	UsersManagementPage usersManagementPageObj;
	PasswordPage passwordPageObj;
	fakeMailPage FakeMailPage;
	

	@BeforeMethod
	public void BeforeMethod() {
		openBrowser();
		LoginPage = new LoginPage(driver, properties);
		HomePage = new HomePage(driver, properties);
		dashBoardPageObj = new DashBoardPage(driver, properties);
		usersManagementPageObj = new UsersManagementPage(driver, properties);
		FakeMailPage = new fakeMailPage(driver, properties);
		passwordPageObj = new PasswordPage(driver, properties);
	
	}

	//
	// Add new user test
	@Test
	public void addNewUser() {
		ReportManager.log("Start test Add new User");
		String superAdmin = properties.getProperty("superAdmin");
		String superAdminPw = properties.getProperty("superAdminPW");
		String newUserPw = properties.getProperty("userPassword");
		Validations.assertThat().object(LoginPage.login(superAdmin,superAdminPw)).isTrue().perform();
		ReportManager.log("Login done  successfully by super admin =" + superAdmin);
		
		User newUser=new User(properties);
				
		dashBoardPageObj.navigateToUsers();
		// Registration with new  user
		String userEmail = usersManagementPageObj.addNewUser(newUser);
		Validations.assertThat().object(userEmail).isNotNull().perform();
		ReportManager.log("user added successfully with email= " + userEmail);

		dashBoardPageObj.logout();
		ReportManager.log("Log off super admin user");

		// Set password by new user added
		String passwordLink = FakeMailPage.getPasswordLink(userEmail);
		Validations.assertThat().object(passwordPageObj.setPassword(passwordLink,newUserPw )).isTrue().perform();
		ReportManager.log("user activated successfully");

		// Assert that user able to login successfully
		Validations.assertThat().object(LoginPage.login(userEmail, newUserPw)).isTrue().perform();
		ReportManager.log("user logged in successfully");
		dashBoardPageObj.logout();
		ReportManager.log("Log off new user");
		
		ReportManager.log("End test Add new User");
		
		
	}

	

	@AfterMethod
	public void AfterMethod(ITestResult result) {
		closeBrowser();
	}

}
