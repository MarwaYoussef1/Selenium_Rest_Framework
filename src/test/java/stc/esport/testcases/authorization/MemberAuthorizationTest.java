package stc.esport.testcases.authorization;
import java.util.ArrayList;

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

import stc.esport.api.business.UserProfile;
import stc.esport.base.TestBase;
import stc.esport.pages.DashBoardPage;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;
import stc.esport.pages.members.DiscoverMembersPage;
import stc.esport.pages.membersmanagement.MemberDetailsPage;
import stc.esport.pages.membersmanagement.MembersManagementListPage;
import stc.esport.pages.users.profiles.ProfileManagementPage;
import stc.esport.utils.Constants;

public class MemberAuthorizationTest extends TestBase{
	
	// Variables
	LoginPage loginPageObj;
	HomePage homePageObj;
	DashBoardPage dashBoardPageObj;
    MembersManagementListPage memberManagementListPageObj;
    MemberDetailsPage        memberDetailsPageObj;
    ProfileManagementPage profileManagementPageObj;
    DiscoverMembersPage  discoverMembersPageObj;
	UserProfile userProfileBusinessObj;
   
    
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
		memberManagementListPageObj= new MembersManagementListPage(driver,properties);
		memberDetailsPageObj= new MemberDetailsPage(driver,properties);
		profileManagementPageObj= new ProfileManagementPage(driver, properties);
		discoverMembersPageObj= new DiscoverMembersPage(driver,properties);
		userProfileBusinessObj=new UserProfile(apiObj,properties);
		
	}

	@BeforeTest
	public void BeforeTest() {
		ReportManager.log("BeforeTest");
	}
	
	
	@Test
    public void testDiscoverMemberAuth() {
	    	ReportManager.log("============== Start test Discover Member Auth   ==============");

		//login with media outlet account
		String mediaOutletAcc = properties.getProperty("mediaOutletAcc");
		String mediaOutletPw = properties.getProperty("mediaOutletPw"); 
		
		 ArrayList<String> newAuthorities =new ArrayList<String>();
		 
		 boolean discoverMember;
		 boolean memberManagementSideMenu;
		 
		 newAuthorities.add(Constants.DISCOVER_MEMBER);
		 
		 //prepare member profile
		 Validations.verifyThat().object(userProfileBusinessObj.prepareUserProfile(Constants.MEDIAOUTLETROLE,newAuthorities,true)).isTrue().perform();
		 ReportManager.log("MediaOutlet profile updated");

        homePageObj.clickLoginLink();
		
		Validations.assertThat().object(loginPageObj.login(mediaOutletAcc, mediaOutletPw)).isTrue().perform();
		ReportManager.log("MediaOutlet account logged in successfully");
		
		//Verify authorities 
		dashBoardPageObj.navigateToMembers();
		discoverMember=discoverMembersPageObj.isDiscoverMemberTabExist();
		Validations.verifyThat().object(discoverMember).isTrue().perform();
		ReportManager.log("Discover member page is exist");
		
		memberManagementSideMenu=dashBoardPageObj.isSideMenuItemExist(Constants.MEMBERMANAGEMENT);
		Validations.verifyThat().object(memberManagementSideMenu).isFalse().perform();
		ReportManager.log("Member management item not displayed in the side menu");
		
		dashBoardPageObj.logout();			
	    ReportManager.log("============== End test Discover Member Auth   ==============");
    }
    
    @Test
    public void testAllMemberListAuth() {
	    	ReportManager.log("============== Start test all Member list Auth   ==============");

		//login with SEF user account
		String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
		String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
		
		boolean memberPublicProfile;
		boolean manageMemberAchivements;
		boolean blockMember;
		boolean unblockMember;
		boolean exportMember;
		
		 ArrayList<String> newAuthorities =new ArrayList<String>();
		 newAuthorities.add(Constants.ALL_MEMBERS_LIST);
		 
		 //perpare profile
		 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
	     ReportManager.log("AutoAuthorizationProfile profile updated");

        homePageObj.clickLoginLink();
        
        Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
		ReportManager.log("SEF account logged in successfully");
		
		dashBoardPageObj.navigateToMembersManagement();

		//Verify authorities 
		exportMember=memberManagementListPageObj.isExportBtnExist();
		Validations.verifyThat().object(exportMember).isFalse().perform();
		ReportManager.log("Export button not exist");

		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTALLMEMBERS);
		memberPublicProfile=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMEMBERPUBLICPROFILE);
		Validations.verifyThat().object(memberPublicProfile).isFalse().perform();
		ReportManager.log("Manage Public Profile action not found");
		
		manageMemberAchivements=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGEMEMBERACHIEVEMENT);
		Validations.verifyThat().object(manageMemberAchivements).isFalse().perform();
		ReportManager.log("Manage member achivement action not found");
		
		blockMember=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKMEMBER);
		Validations.verifyThat().object(blockMember).isFalse().perform();
		ReportManager.log("Block member action not found");
		
		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTBLOCKEDMEMBERS);
		unblockMember=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKMEMBER);
		Validations.verifyThat().object(unblockMember).isFalse().perform();
		ReportManager.log("unblock member action not found");
		
		dashBoardPageObj.logout();
	    ReportManager.log("============== End test all Member list Auth   ==============");
    }
    
    @Test
    public void testBlockMemberAuth() {
	    	ReportManager.log("============== Start test block a member Auth   ==============");

		//login with SEF user account
		String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
		String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
		
		boolean memberPublicProfile;
		boolean manageMemberAchivements;
		boolean blockMember;
		boolean unblockMember;
		boolean exportMember;
		
		 ArrayList<String> newAuthorities =new ArrayList<String>();
		 newAuthorities.add(Constants.ALL_MEMBERS_LIST);
		 newAuthorities.add(Constants.BLOCK_MEMBER);
		 
		 //prepare profile
		 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
			 ReportManager.log("AutoAuthorizationProfile profile updated");

        homePageObj.clickLoginLink();
        
        Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
		ReportManager.log("SEF account logged in successfully");
		
		dashBoardPageObj.navigateToMembersManagement();

		//Verify authorities
		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTALLMEMBERS); 
		blockMember=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKMEMBER);
		Validations.verifyThat().object(blockMember).isTrue().perform();
		ReportManager.log("Block member action found");
		
		exportMember=memberManagementListPageObj.isExportBtnExist();
		Validations.verifyThat().object(exportMember).isFalse().perform();
		ReportManager.log("Export button not exist");

		memberPublicProfile=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMEMBERPUBLICPROFILE);
		Validations.verifyThat().object(memberPublicProfile).isFalse().perform();
		ReportManager.log("Manage Public Profile action not found");
		
		manageMemberAchivements=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGEMEMBERACHIEVEMENT);
		Validations.verifyThat().object(manageMemberAchivements).isFalse().perform();
		ReportManager.log("Manage member achivement action not found");
		
		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTBLOCKEDMEMBERS);
		unblockMember= memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKMEMBER);
		Validations.verifyThat().object(unblockMember).isFalse().perform();
		ReportManager.log("unblock member action not found");
		
		dashBoardPageObj.logout();
		ReportManager.log("============== End test block a member Auth   ==============");
    }
    
    @Test
    public void testUnblockMemberAuth() {
	    	ReportManager.log("============== Start test unblock member Auth   ==============");

		//login with SEF user account
		String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
		String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
		
		boolean memberPublicProfile;
		boolean manageMemberAchivements;
		boolean blockMember;
		boolean unblockMember;
		boolean exportMember;
		
		 ArrayList<String> newAuthorities =new ArrayList<String>();
		 newAuthorities.add(Constants.ALL_MEMBERS_LIST);
		 newAuthorities.add(Constants.UNBLOCK_MEMBER);
		 
		 //prepare profile
		 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
		 ReportManager.log("AutoAuthorizationProfile profile updated");

        homePageObj.clickLoginLink();
        
        Validations.verifyThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
		ReportManager.log("SEF account logged in successfully");
		
		dashBoardPageObj.navigateToMembersManagement();

		//Verify authorities 
		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTBLOCKEDMEMBERS);
		unblockMember=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKMEMBER);
		Validations.verifyThat().object(unblockMember).isTrue().perform();
		ReportManager.log("unblock member action found");
		
		exportMember= memberManagementListPageObj.isExportBtnExist();
		Validations.verifyThat().object(exportMember).isFalse().perform();
		ReportManager.log("Export button not exist");

		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTALLMEMBERS); 
		memberPublicProfile= memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMEMBERPUBLICPROFILE);
		Validations.verifyThat().object(memberPublicProfile).isFalse().perform();
		ReportManager.log("Manage Public Profile action not found");
		
		manageMemberAchivements=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGEMEMBERACHIEVEMENT);
		Validations.verifyThat().object(manageMemberAchivements).isFalse().perform();
		ReportManager.log("Manage member achivement action not found");
		
		blockMember=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKMEMBER);
		Validations.verifyThat().object(blockMember).isFalse().perform();
		ReportManager.log("Block member action not found");
		
		dashBoardPageObj.logout();
		ReportManager.log("============== End test unblock member Auth   ==============");
    }
    
    @Test
    public void testEditAllMembersProfilesAuth() {
	    	ReportManager.log("============== Start test Edit all members profiles Auth  ==============");

		//login with SEF user account
		String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
		String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
		
		boolean memberPublicProfile;
		boolean manageMemberAchivements;
		boolean blockMember;
		boolean unblockMember;
		boolean exportMember;
		
		 ArrayList<String> newAuthorities =new ArrayList<String>();
		 newAuthorities.add(Constants.ALL_MEMBERS_LIST);
		 newAuthorities.add(Constants.EDIT_ALL_MEMBER_PROFILES);
		 
		 //prepare profile
		 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
			 ReportManager.log("AutoAuthorizationProfile profile updated");

        homePageObj.clickLoginLink();
        
        Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
		ReportManager.log("SEF account logged in successfully");
		
		dashBoardPageObj.navigateToMembersManagement();

		//Verify authorities 
		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTALLMEMBERS); 
		memberPublicProfile=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMEMBERPUBLICPROFILE);
		Validations.verifyThat().object(memberPublicProfile).isTrue().perform();
		ReportManager.log("Manage Public Profile action found");
		
		exportMember=memberManagementListPageObj.isExportBtnExist();
		Validations.verifyThat().object(exportMember).isFalse().perform();
		ReportManager.log("Export button not exist");
	
		manageMemberAchivements=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGEMEMBERACHIEVEMENT);
		Validations.verifyThat().object(manageMemberAchivements).isFalse().perform();
		ReportManager.log("Manage member achivement action not found");
		
		blockMember=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKMEMBER);
		Validations.verifyThat().object(blockMember).isFalse().perform();
		ReportManager.log("Block member action not found");
		
		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTBLOCKEDMEMBERS);
		unblockMember=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKMEMBER);
		Validations.verifyThat().object(unblockMember).isFalse().perform();
		ReportManager.log("unblock member action not found");
		
		dashBoardPageObj.logout();
        ReportManager.log("============== End test Edit all members profiles Auth   ==============");
    }
    
    @Test
    public void testManageAchievementsMemberAuth() {
	    	ReportManager.log("============== Start test  Manage achievements  Auth  ==============");

		//login with SEF user account
		String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
		String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
		 
		boolean memberPublicProfile;
		boolean manageMemberAchivements;
		boolean blockMember;
		boolean unblockMember;
		boolean exportMember; 
		
		 ArrayList<String> newAuthorities =new ArrayList<String>();
		 newAuthorities.add(Constants.ALL_MEMBERS_LIST);
		 newAuthorities.add(Constants.MEMBER_MANAGE_ACHIVEMENTS);
		 
		 //prepare profile
		 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
			 ReportManager.log("AutoAuthorizationProfile profile updated");

        homePageObj.clickLoginLink();
        
        Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
		ReportManager.log("SEF account logged in successfully");
		
		dashBoardPageObj.navigateToMembersManagement();

		//Verify authorities 
		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTALLMEMBERS); 
		manageMemberAchivements=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGEMEMBERACHIEVEMENT);
		Validations.verifyThat().object(manageMemberAchivements).isTrue().perform();
		ReportManager.log("Manage member achivement action found");
		
		exportMember=memberManagementListPageObj.isExportBtnExist();
		Validations.verifyThat().object(exportMember).isFalse().perform();
		ReportManager.log("Export button not exist");

		memberPublicProfile=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMEMBERPUBLICPROFILE);
		Validations.verifyThat().object(memberPublicProfile).isFalse().perform();
		ReportManager.log("Manage Public Profile action not found");
		
		blockMember=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKMEMBER);
		Validations.verifyThat().object(blockMember).isFalse().perform();
		ReportManager.log("Block member action not found");
		
		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTBLOCKEDMEMBERS);
		unblockMember=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKMEMBER);
		Validations.verifyThat().object(unblockMember).isFalse().perform();
		ReportManager.log("unblock member action not found");
		
		dashBoardPageObj.logout();
		
	    ReportManager.log("============== End test Manage achievements Auth   ==============");
    }
    
    @Test
    public void testExportMemberAuth() {
	    	ReportManager.log("============== Start test export member Auth  ==============");

		//login with SEF user account
		String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
		String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
		
		boolean ManageMemberPublicProfile;
		boolean manageMemberAchivements;
		boolean blockMember;
		boolean unblockMember;
		boolean exportMember; 
		
		 ArrayList<String> newAuthorities =new ArrayList<String>();
		 newAuthorities.add(Constants.ALL_MEMBERS_LIST);
		 newAuthorities.add(Constants.MEMBER_Export);
		 
		 //prepare profile
		 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
			 ReportManager.log("AutoAuthorizationProfile profile updated");

        homePageObj.clickLoginLink();
        
        Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
		ReportManager.log("SEF account logged in successfully");
		
		dashBoardPageObj.navigateToMembersManagement();
		
		//Verify authorities 
		exportMember=memberManagementListPageObj.isExportBtnExist();
		Validations.verifyThat().object(exportMember).isTrue().perform();
		ReportManager.log("Export button exist");

		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTALLMEMBERS); 
		ManageMemberPublicProfile=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMEMBERPUBLICPROFILE);
		Validations.verifyThat().object(ManageMemberPublicProfile).isFalse().perform();
		ReportManager.log("Manage Public Profile action not found");
		
		manageMemberAchivements=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGEMEMBERACHIEVEMENT);
		Validations.verifyThat().object(manageMemberAchivements).isFalse().perform();
		ReportManager.log("Manage member achivement action not found");
		
		blockMember=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKMEMBER);
		Validations.verifyThat().object(blockMember).isFalse().perform();
		ReportManager.log("Block member action not found");
		
		memberManagementListPageObj.openFirstMember(Constants.MANAGEMENTBLOCKEDMEMBERS);
		unblockMember=memberDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKMEMBER);
		Validations.verifyThat().object(unblockMember).isFalse().perform();
		ReportManager.log("unblock member action not found");
		
		dashBoardPageObj.logout();
		
		ReportManager.log("============== End test export member Auth   ==============");
    }  
    
    
   
   
	@AfterMethod
	public void AfterMethod(ITestResult result) {
		ReportManager.log("AfterMethod");
		//Return profiles to the original authorities 
		Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.MEDIAOUTLETROLE,null,false)).isTrue().perform();
		 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,null,false)).isTrue().perform();
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
