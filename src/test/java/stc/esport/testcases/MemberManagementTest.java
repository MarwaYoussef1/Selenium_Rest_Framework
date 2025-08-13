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

import com.shaft.driver.SHAFT.Validations;
import com.shaft.tools.io.ReportManager;

import stc.esport.base.TestBase;
import stc.esport.pages.DashBoardPage;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;
import stc.esport.pages.membersmanagement.MemberDetailsPage;
import stc.esport.pages.membersmanagement.MembersManagementListPage;
import stc.esport.utils.Constants;

public class MemberManagementTest extends TestBase{
	// Variables
	LoginPage loginPageObj;
	HomePage homePageObj;
	DashBoardPage dashBoardPageObj;
	MembersManagementListPage memberManagementListPageObj;
	MemberDetailsPage  	memberDetailsPageObj;
	
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
		memberManagementListPageObj = new MembersManagementListPage(driver, properties);
		memberDetailsPageObj = new MemberDetailsPage(driver, properties);
	}
	
	@BeforeTest
	public void BeforeTest() {
		ReportManager.log("BeforeTest");
	}
	
	@Test
	public void testBlockMember() {
		ReportManager.log("==============  Start block member test ==============");
		//superAdmin
		String superAdmin = properties.getProperty("superAdmin");
		String superAdminPw = properties.getProperty("superAdminPW");
		String blockedMemberAcc = properties.getProperty("blockedMemberAcc");
		String blockedMemberPW = properties.getProperty("blockedMemberPW");
		String blockMemberName = properties.getProperty("BlockMemberName");
		String invalidBlockedMemberLoginMsg = properties.getProperty("invalidBlockedMemberLoginMsg");
		homePageObj.clickLoginLink();
		boolean memberBlocked;
				
		Validations.assertThat().object(loginPageObj.login(superAdmin,superAdminPw)).isTrue().perform();
		ReportManager.log("Login done successfully by super admin =" + superAdmin);
		dashBoardPageObj.navigateToMembersManagement();
		ReportManager.log("Members Management is displayed");
		memberManagementListPageObj.openMember(Constants.MANAGEMENTALLMEMBERS, blockMemberName , blockMemberName);
		memberBlocked = memberDetailsPageObj.blockMember();
		Validations.assertThat().object(memberBlocked).isTrue().perform();
		ReportManager.log("Member blocked successfully ");	
		/// ** end on block member action
		
		memberManagementListPageObj.searchWithInvalidValue(blockMemberName);
		ReportManager.log("Member disappeared from All Members List");	
		
		ReportManager.log("Searching that blocked member displayed in Blocked Members List");	
		memberManagementListPageObj.openMember(Constants.MANAGEMENTBLOCKEDMEMBERS , blockMemberName , blockMemberName);
		Validations.assertThat().object(memberDetailsPageObj.checkActionExist("Unblock")).isTrue();
		ReportManager.log("Unblock action is existing successfully.");
		dashBoardPageObj.logout();
		ReportManager.log("Sef Admin user logs out successfully.");
		//login with blocked member
		homePageObj.clickLoginLink();
		memberBlocked = loginPageObj.invalidLogin(blockedMemberAcc, blockedMemberPW, invalidBlockedMemberLoginMsg);
		Validations.assertThat().object(memberBlocked).isTrue().perform();			
		
		ReportManager.log("Unable to login with blocked member");
		ReportManager.log("==============  End block member test ==============");
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
