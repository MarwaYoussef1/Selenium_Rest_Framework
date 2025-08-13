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
import stc.esport.pages.clubmanagement.ClubDetailsPage;
import stc.esport.pages.clubmanagement.ClubsManagementListPage;
import stc.esport.pages.clubs.AdminsPage;
import stc.esport.pages.clubs.ClubDashboardPage;
import stc.esport.pages.clubs.MyClubInfoPage;
import stc.esport.pages.myClub.MyClubPage;
import stc.esport.pages.users.profiles.ProfileManagementPage;
import stc.esport.utils.Constants;

public class clubsAuthorizationTest extends TestBase{
	
	// Variables
		LoginPage loginPageObj;
		HomePage homePageObj;
		DashBoardPage dashBoardPageObj;
	    ClubsManagementListPage  clubsManagementListPageObj;
	    ClubDetailsPage   clubDetailsPageObj;
	    ProfileManagementPage profileManagementPageObj;
	    AdminsPage adminsPageObj;
	    ClubDashboardPage clubDashboardPageObj;
	    MyClubInfoPage myClubInfoPageObj;
	    MyClubPage myClubPageObj;
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
			clubDetailsPageObj= new ClubDetailsPage(driver,properties);
			profileManagementPageObj= new ProfileManagementPage(driver, properties);
			clubsManagementListPageObj= new ClubsManagementListPage(driver, properties);
			adminsPageObj= new AdminsPage(driver,properties);
			clubDashboardPageObj= new ClubDashboardPage(driver,properties);
			myClubInfoPageObj= new MyClubInfoPage(driver,properties);
			myClubPageObj= new MyClubPage(driver,properties);
			userProfileBusinessObj=new UserProfile(apiObj,properties);
			
		}

		@BeforeTest
		public void BeforeTest() {
			ReportManager.log("BeforeTest");
		}
		
		@Test
	    public void testDiscoverclubsAuth() {
  	    	ReportManager.log("============== Start test disover club Auth  ==============");

			// club owner account cred
			String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
			String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
			
			boolean callForMember;
			boolean manageAchivements;
			boolean discoverClubsTab;
			boolean AssignAdminTab;
			boolean myClubInfoTab;
			boolean clubManagement;
			
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.DISCOVER_CLUBS);
			 
			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
			 ReportManager.log("Club owner profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
			ReportManager.log("Club ower account logged in successfully");
			
			//Verify authorities 
			dashBoardPageObj.navigateToClubs();
			discoverClubsTab=clubDashboardPageObj.isDisoverTabExist();
			Validations.verifyThat().object(discoverClubsTab).isTrue().perform();
			ReportManager.log("Disover club page exist");
			
			AssignAdminTab=clubDashboardPageObj.isAdminTabExist();
			Validations.verifyThat().object(AssignAdminTab).isFalse().perform();
			ReportManager.log("Admin tab not exist");
			
			myClubInfoTab=clubDashboardPageObj.isMyClubInfoTabExist();
			Validations.verifyThat().object(myClubInfoTab).isFalse().perform();
			ReportManager.log("My club info tab not exist");
			
			clubManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS_MANAGEMENT);
			Validations.verifyThat().object(clubManagement).isFalse().perform();
			ReportManager.log("Club management not exist in the side menu");
			
			dashBoardPageObj.navigateToMyClub();
			
			callForMember=myClubPageObj.isCallForMemberBtnExist();
			Validations.verifyThat().object(callForMember).isFalse().perform();
			ReportManager.log("call for member button not exist");
			
			manageAchivements=myClubPageObj.isAchievementsTabExist();
			Validations.verifyThat().object(manageAchivements).isFalse().perform();
			ReportManager.log("Manage achivements tab not exist");
			 
			dashBoardPageObj.logout();
		    ReportManager.log("============== End test discover club Auth   ==============");
	    }   
	    
	    @Test
	    public void testMyClubInfoAuth() {
  	    	ReportManager.log("============== Start test my club info Auth  ==============");

  	        //club owner account cred
  	    	String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
			String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
			
			boolean callForMember;
			boolean manageAchivements;
			boolean discoverClubsTab;
			boolean AssignAdminTab;
			boolean myClubInfoTab;
			boolean clubManagement;
			boolean editMyClubInfo;
			
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.MY_CLUB_INFO);
			 
			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
			 ReportManager.log("Club owner profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
			ReportManager.log("Club ower account logged in successfully");
			
			//Verify authorities 
			dashBoardPageObj.navigateToClubs();
			myClubInfoTab=clubDashboardPageObj.isMyClubInfoTabExist();
			Validations.verifyThat().object(myClubInfoTab).isTrue().perform();
			ReportManager.log("My club info tab exist");
			
			discoverClubsTab=clubDashboardPageObj.isDisoverTabExist();
			Validations.verifyThat().object(discoverClubsTab).isFalse().perform();
			ReportManager.log("Disover club page not exist");

			AssignAdminTab=clubDashboardPageObj.isAdminTabExist();
			Validations.verifyThat().object(AssignAdminTab).isFalse().perform();
			ReportManager.log("Admin tab not exist");
			
			clubDashboardPageObj.selectMyClubInfo();
			editMyClubInfo=myClubInfoPageObj.isEditBtnExist();
			Validations.verifyThat().object(editMyClubInfo).isFalse().perform();
			ReportManager.log("Edit my club info button not exist");
			
			dashBoardPageObj.navigateToMyClub();
			callForMember=myClubPageObj.isCallForMemberBtnExist();
			Validations.verifyThat().object(callForMember).isFalse().perform();
			ReportManager.log("call for member button not exist");
			
			manageAchivements=myClubPageObj.isAchievementsTabExist();
			Validations.verifyThat().object(manageAchivements).isFalse().perform();
			ReportManager.log("manage achivements tab not exist");
			
			clubManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS_MANAGEMENT);
			Validations.verifyThat().object(clubManagement).isFalse().perform();
			ReportManager.log("Club management not exist in the side menu");
			
			dashBoardPageObj.logout();
		    
		    ReportManager.log("============== End test my club info Auth   ==============");
	    }   
	    
	    @Test
	    public void testEditMyClubInfoAuth() {
  	    	ReportManager.log("============== Start test edit my club info Auth  ==============");

  	        // club owner account cred
			String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
			String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
			
			boolean callForMember;
			boolean manageAchivements;
			boolean discoverClubsTab;
			boolean AssignAdminTab;
			boolean clubManagement;
			boolean editMyClubInfo;
			
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.MY_CLUB_INFO);
			 newAuthorities.add(Constants.EDIT_MY_CLUB_INFO);

			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
			 ReportManager.log("Club owner profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
			ReportManager.log("Club ower account logged in successfully");
			
			//Verify authorities 
			dashBoardPageObj.navigateToClubs();
			
			clubDashboardPageObj.selectMyClubInfo();
			editMyClubInfo=myClubInfoPageObj.isEditBtnExist();
			Validations.verifyThat().object(editMyClubInfo).isTrue().perform();
			ReportManager.log("Edit my club info button exist");
			
			discoverClubsTab=clubDashboardPageObj.isDisoverTabExist();
			Validations.verifyThat().object(discoverClubsTab).isFalse().perform();
			ReportManager.log("Disover club page not exist");

			AssignAdminTab=clubDashboardPageObj.isAdminTabExist();
			Validations.verifyThat().object(AssignAdminTab).isFalse().perform();
			ReportManager.log("Admin tab not exist");
			
			dashBoardPageObj.navigateToMyClub();
			callForMember=myClubPageObj.isCallForMemberBtnExist();
			Validations.verifyThat().object(callForMember).isFalse().perform();
			ReportManager.log("call for member button not exist");
			
			manageAchivements=myClubPageObj.isAchievementsTabExist();
			Validations.verifyThat().object(manageAchivements).isFalse().perform();
			ReportManager.log("manage achivements tab not exist");
			 
			clubManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS_MANAGEMENT);
			Validations.verifyThat().object(clubManagement).isFalse().perform();
			ReportManager.log("Club management not exist in the side menu");
			
			dashBoardPageObj.logout();
			ReportManager.log("============== End test edit my club info Auth   ==============");
	    }   
	    
	    @Test
	    public void testAssignAdminsAuth() {
  	    	ReportManager.log("============== Start test assign admin Auth  ==============");

  	        // club owner account cred
  	    	String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
			String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
			
			boolean callForMember;
			boolean manageAchivements;
			boolean discoverClubsTab;
			boolean assignAdminTab;
			boolean addAdminBtn;
			boolean myClubInfoTab;
			boolean clubManagement;
			
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.ASSIGN_ADMINS);
			 
			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
				 ReportManager.log("Club owner profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
			ReportManager.log("Club ower account logged in successfully");
			
			//Verify authorities 
			dashBoardPageObj.navigateToClubs();
			assignAdminTab=clubDashboardPageObj.isAdminTabExist();
			Validations.verifyThat().object(assignAdminTab).isTrue().perform();
			ReportManager.log("Admin tab exist");
			
			clubDashboardPageObj.selectAdminTab();
			addAdminBtn=adminsPageObj.isAddAdminCardExist();
			Validations.verifyThat().object(addAdminBtn).isTrue().perform();
			ReportManager.log("Assign admin button exist");
			
			discoverClubsTab=clubDashboardPageObj.isDisoverTabExist();
			Validations.verifyThat().object(discoverClubsTab).isFalse().perform();
			ReportManager.log("Disover club page not exist");
			
			myClubInfoTab=clubDashboardPageObj.isMyClubInfoTabExist();
			Validations.verifyThat().object(myClubInfoTab).isFalse().perform();
			ReportManager.log("My club info tab not exist");
			
			dashBoardPageObj.navigateToMyClub();
			callForMember=myClubPageObj.isCallForMemberBtnExist();
			Validations.verifyThat().object(callForMember).isFalse().perform();
			ReportManager.log("call for member button not exist");
			
			manageAchivements=myClubPageObj.isAchievementsTabExist();
			Validations.verifyThat().object(manageAchivements).isFalse().perform();
			ReportManager.log("manage achivements tab not exist");
			 
			clubManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS_MANAGEMENT);
			Validations.verifyThat().object(clubManagement).isFalse().perform();
			ReportManager.log("Club management not exist in the side menu");
			
			dashBoardPageObj.logout();
			
	        ReportManager.log("============== End test assign admin Auth   ==============");
	    }   
	    
	    @Test
	    public void testCallForMemberAuth() {
  	    	ReportManager.log("============== Start test call for member Auth  ==============");

  	        // club owner account cred
			String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
			String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
			
			boolean callForMember;
			boolean manageAchivements;
			boolean clubs;
			boolean clubManagement;
			
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.CALL_FOR_MEMBERS);
			 
			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
			 ReportManager.log("Club owner profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
			ReportManager.log("Club ower account logged in successfully");
			
			//Verify authorities 
			callForMember=myClubPageObj.isCallForMemberBtnExist();
			Validations.verifyThat().object(callForMember).isTrue().perform();
			ReportManager.log("call for member button exist");
			
			manageAchivements=myClubPageObj.isAchievementsTabExist();
			Validations.verifyThat().object(manageAchivements).isFalse().perform();
			ReportManager.log("manage achievements tab not exist in the club profile");

			clubManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS_MANAGEMENT);
			Validations.verifyThat().object(clubManagement).isFalse().perform();
			ReportManager.log("Club management not exist in the side menu");

			clubs=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS);
			Validations.verifyThat().object(clubs).isFalse().perform();
			ReportManager.log("Clubs not exist in the side menu");
			
			dashBoardPageObj.logout();
			
	        ReportManager.log("============== End test call for member Auth   ==============");
	    }   
	    
	    @Test
	    public void testClubManageAchievementsClubOwnerAuth() {
  	    	ReportManager.log("============== Start test manage achivements from club owner side Auth  ==============");

  	        // club owner account cred
			String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
			String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
			
			  boolean callForMember;
			  boolean manageAchivements;
			  boolean clubs;
			  boolean clubManagement;
			
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.CLUB_MANAGE_ACHIVEMENTS);

			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
			   ReportManager.log("Club owner profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
			ReportManager.log("Club ower account logged in successfully");
			
			//Verify authorities 
			manageAchivements=myClubPageObj.isAchievementsTabExist();
			Validations.verifyThat().object(manageAchivements).isTrue().perform();
			ReportManager.log("manage achievements tab exist in the club profile");

			callForMember=myClubPageObj.isCallForMemberBtnExist();
			Validations.verifyThat().object(callForMember).isFalse().perform();
			ReportManager.log("call for member button not exist");
			
			clubs=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS_MANAGEMENT);
			Validations.verifyThat().object(clubs).isFalse().perform();
			ReportManager.log("Club management not exist in the side menu");

			clubManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS);
			Validations.verifyThat().object(clubManagement).isFalse().perform();
			ReportManager.log("Clubs not exist in the side menu");
			
			dashBoardPageObj.logout();
			ReportManager.log("============== End test manage achivements from club owner side Auth   ==============");
	    }   
	    
	    @Test
	    public void testAllClubListsAuth() {
  	    	ReportManager.log("============== Start test all clubs list Auth  ==============");

			//SEF user account cred
			String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
			String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
			
			    boolean myClub;
			    boolean clubs;
			    boolean export;
			    boolean blockClub;
			    boolean unblockClub;
			    boolean editAllClubs;
			    boolean manageClubPublicProfile;
			    boolean manageAchievements;
			    
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.LIST_ALL_CLUBS);

			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
				 ReportManager.log("AutoAuthorizationProfile profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
			ReportManager.log("SEF admin account logged in successfully");

			//Verify authorities 
			dashBoardPageObj.navigateToClubsManagement();
			export=clubsManagementListPageObj.isExportBtnExist();
			Validations.verifyThat().object(export).isFalse().perform();
			ReportManager.log("Export button not exist");

			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTALLCLUBS); 
			manageClubPublicProfile=clubDetailsPageObj.isManageProfileExist();
			Validations.verifyThat().object(manageClubPublicProfile).isFalse().perform();
			ReportManager.log("Manage club Profile action not found");
			
			editAllClubs=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLUB);
			Validations.verifyThat().object(editAllClubs).isFalse().perform();
			ReportManager.log("Edit club action not found");
			
			manageAchievements=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGECLUBACHIEVEMENT);
			Validations.verifyThat().object(manageAchievements).isFalse().perform();
			ReportManager.log("Manage Club achivement action not found");
			
			blockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKCLUB);
			Validations.verifyThat().object(blockClub).isFalse().perform();
			ReportManager.log("Block club action not found");
			
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTBLOCKEDCLUBS);
			unblockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKCLUB);
			Validations.verifyThat().object(unblockClub).isFalse().perform();
			ReportManager.log("Mangement unblock club action not found");
			
			clubs=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS);
			Validations.verifyThat().object(clubs).isFalse().perform();
			ReportManager.log("Clubs not exist in the side menu");
			
			myClub=dashBoardPageObj.isSideMenuItemExist(Constants.MYCLUB);
			Validations.verifyThat().object(myClub).isFalse().perform();
			ReportManager.log("My club not exist in the side menu");
			
			dashBoardPageObj.logout();
			ReportManager.log("============== End test all list club Auth   ==============");
	    }   
	    
	    @Test
	    public void testEditAllClubsInfoAuth() {
  	    	ReportManager.log("============== Start test edit all clubs info Auth  ==============");

			//SEF user account cred
			String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
			String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
			
			    boolean myClub;
			    boolean clubs;
			    boolean export;
			    boolean blockClub;
			    boolean unblockClub;
			    boolean editAllClubs;
			    boolean manageClubPublicProfile;
			    boolean manageAchievements;
			    
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.LIST_ALL_CLUBS);
			 newAuthorities.add(Constants.EDIT_ALL_CLUB_INFO);

			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
			  ReportManager.log("AutoAuthorizationProfile profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
			ReportManager.log("SEF admin account logged in successfully");

			//Verify authorities 
			dashBoardPageObj.navigateToClubsManagement();	    				
		
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTALLCLUBS); 
			editAllClubs=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLUB);
			Validations.verifyThat().object(editAllClubs).isTrue().perform();
			ReportManager.log("Edit club action found");
			
			export=clubsManagementListPageObj.isExportBtnExist();
			Validations.verifyThat().object(export).isFalse().perform();
			ReportManager.log("Export button not exist");
			
			manageClubPublicProfile=clubDetailsPageObj.isManageProfileExist();
			Validations.verifyThat().object(manageClubPublicProfile).isFalse().perform();
			ReportManager.log("Manage club Profile action not found");
			
			manageAchievements=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGECLUBACHIEVEMENT);
			Validations.verifyThat().object(manageAchievements).isFalse().perform();
			ReportManager.log("Manage Club achivement action not found");
			
			blockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKCLUB);
			Validations.verifyThat().object(blockClub).isFalse().perform();
			ReportManager.log("Block club action not found");
			
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTBLOCKEDCLUBS);
			unblockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKCLUB);
			Validations.verifyThat().object(unblockClub).isFalse().perform();
			ReportManager.log("Mangement unblock club action not found");
			
			clubs=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS);
			Validations.verifyThat().object(clubs).isFalse().perform();
			ReportManager.log("Clubs not exist in the side menu");
			
			myClub=dashBoardPageObj.isSideMenuItemExist(Constants.MYCLUB);
			Validations.verifyThat().object(myClub).isFalse().perform();
			ReportManager.log("My club not exist in the side menu");
			
			dashBoardPageObj.logout();
		
	    }   
	    
	    @Test
	    public void testManageClubsProfilesAuth() {
  	    	ReportManager.log("============== Start test manage clubs profiles Auth  ==============");

			//SEF user account cred
			String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
			String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
			
			   boolean myClub;
		       boolean clubs;
		       boolean export;
		       boolean blockClub;
		       boolean unblockClub;
		       boolean editAllClubs;
		       boolean manageClubPublicProfile;
		       boolean manageAchievements;
		       
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.LIST_ALL_CLUBS);
			 newAuthorities.add(Constants.MANAGE_CLUB_PROFILES);

			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
				 ReportManager.log("AutoAuthorizationProfile profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
			ReportManager.log("SEF admin account logged in successfully");

			//Verify authorities 
			dashBoardPageObj.navigateToClubsManagement();	    				
		
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTALLCLUBS); 
			manageClubPublicProfile=clubDetailsPageObj.isManageProfileExist();
			Validations.verifyThat().object(manageClubPublicProfile).isTrue().perform();
			ReportManager.log("Manage club Profile action found");
			
			editAllClubs=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLUB);
			Validations.verifyThat().object(editAllClubs).isFalse().perform();
			ReportManager.log("Edit club action not found");
			
			export=clubsManagementListPageObj.isExportBtnExist();
			Validations.verifyThat().object(export).isFalse().perform();
			ReportManager.log("Export button not exist");
			
			manageAchievements=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGECLUBACHIEVEMENT);
			Validations.verifyThat().object(manageAchievements).isFalse().perform();
			ReportManager.log("Manage Club achivement action not found");
			
			blockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKCLUB);
			Validations.verifyThat().object(blockClub).isFalse().perform();
			ReportManager.log("Block club action not found");
			
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTBLOCKEDCLUBS);
			unblockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKCLUB);
			Validations.verifyThat().object(unblockClub).isFalse().perform();
			ReportManager.log("Mangement unblock club action not found");
			
			clubs=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS);
			Validations.verifyThat().object(clubs).isFalse().perform();
			ReportManager.log("Clubs not exist in the side menu");
			
			myClub=dashBoardPageObj.isSideMenuItemExist(Constants.MYCLUB);
			Validations.verifyThat().object(myClub).isFalse().perform();
			ReportManager.log("My club not exist in the side menu");
			
			dashBoardPageObj.logout();
			
	      ReportManager.log("============== End test manage clubs profiles Auth   ==============");
	    }   
	    
	    @Test
	    public void testBlockClubsAuth() {
  	    	ReportManager.log("============== Start test block clubs Auth  ==============");

			//SEF user account cred
			String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
			String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
			
			   boolean myClub;
		       boolean clubs;
		       boolean export;
		       boolean blockClub;
		       boolean unblockClub;
		       boolean editAllClubs;
		       boolean manageClubPublicProfile;
		       boolean manageAchievements;
		       
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.LIST_ALL_CLUBS);
			 newAuthorities.add(Constants.BLOCK_CLUBS);

			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
				 ReportManager.log("AutoAuthorizationProfile profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
			ReportManager.log("SEF admin account logged in successfully");

			//Verify authorities 
			dashBoardPageObj.navigateToClubsManagement();	    				
		
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTALLCLUBS); 
			blockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKCLUB);
			Validations.verifyThat().object(blockClub).isTrue().perform();
			ReportManager.log("Block club action found");
			
			manageClubPublicProfile=clubDetailsPageObj.isManageProfileExist();
			Validations.verifyThat().object(manageClubPublicProfile).isFalse().perform();
			ReportManager.log("Manage club Profile action not found");
			
			editAllClubs=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLUB);
			Validations.verifyThat().object(editAllClubs).isFalse().perform();
			ReportManager.log("Edit club action not found");
			
			export=clubsManagementListPageObj.isExportBtnExist();
			Validations.verifyThat().object(export).isFalse().perform();
			ReportManager.log("Export button not exist");
			
			manageAchievements=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGECLUBACHIEVEMENT);
			Validations.verifyThat().object(manageAchievements).isFalse().perform();
			ReportManager.log("Manage Club achivement action not found");
			
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTBLOCKEDCLUBS);
			unblockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKCLUB);
			Validations.verifyThat().object(unblockClub).isFalse().perform();
			ReportManager.log("Mangement unblock club action not found");
			
			clubs=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS);
			Validations.verifyThat().object(clubs).isFalse().perform();
			ReportManager.log("Clubs not exist in the side menu");
			
			myClub=dashBoardPageObj.isSideMenuItemExist(Constants.MYCLUB);
			Validations.verifyThat().object(myClub).isFalse().perform();
			ReportManager.log("My club not exist in the side menu");
			
			dashBoardPageObj.logout();
			 ReportManager.log("============== End test block clubs Auth   ==============");
	    }   
	    
	    @Test
	    public void testClubManageAchievementsSEFAdminAuth() {
  	    	ReportManager.log("============== Start test club manage achievements from SEF admin side Auth  ==============");

			//SEF user account cred
			String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
			String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
			
			   boolean myClub;
		       boolean clubs;
		       boolean export;
		       boolean blockClub;
		       boolean unblockClub;
		       boolean editAllClubs;
		       boolean manageClubPublicProfile;
		       boolean manageAchievements;
		       
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.LIST_ALL_CLUBS);
			 newAuthorities.add(Constants.CLUB_MANAGE_ACHIVEMENTS);

			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
				 ReportManager.log("AutoAuthorizationProfile profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
			ReportManager.log("SEF admin account logged in successfully");

			//Verify authorities 
			dashBoardPageObj.navigateToClubsManagement();	    				
		
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTALLCLUBS); 
			manageAchievements=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGECLUBACHIEVEMENT);
			Validations.verifyThat().object(manageAchievements).isTrue().perform();
			ReportManager.log("Manage Club achivement action found");
			
			blockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKCLUB);
			Validations.verifyThat().object(blockClub).isFalse().perform();
			ReportManager.log("Block club action not found");
			
			manageClubPublicProfile=clubDetailsPageObj.isManageProfileExist();
			Validations.verifyThat().object(manageClubPublicProfile).isFalse().perform();
			ReportManager.log("Manage club Profile action not found");
			
			editAllClubs=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLUB);
			Validations.verifyThat().object(editAllClubs).isFalse().perform();
			ReportManager.log("Edit club action not found");
			
			export=clubsManagementListPageObj.isExportBtnExist();
			Validations.verifyThat().object(export).isFalse().perform();
			ReportManager.log("Export button not exist");
			
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTBLOCKEDCLUBS);
			unblockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKCLUB);
			Validations.verifyThat().object(unblockClub).isFalse().perform();
			ReportManager.log("Mangement unblock club action not found");
			
			clubs=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS);
			Validations.verifyThat().object(clubs).isFalse().perform();
			ReportManager.log("Clubs not exist in the side menu");
			
			myClub=dashBoardPageObj.isSideMenuItemExist(Constants.MYCLUB);
			Validations.verifyThat().object(myClub).isFalse().perform();
			ReportManager.log("My club not exist in the side menu");
			
			dashBoardPageObj.logout();
			
	      ReportManager.log("============== End test club Manage achievement from SEF admin side Auth   ==============");
	    }   
	    
	    @Test
	    public void testUnblockClubsAuth() {
  	    	ReportManager.log("============== Start test unblock clubs Auth  ==============");

			//SEF user account cred
			String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
			String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
			
			   boolean myClub;
		       boolean clubs;
		       boolean export;
		       boolean blockClub;
		       boolean unblockClub;
		       boolean editAllClubs;
		       boolean manageClubPublicProfile;
		       boolean manageAchievements;
		       
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.LIST_ALL_CLUBS);
			 newAuthorities.add(Constants.UNBLOCK_CLUBS);

			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
				 ReportManager.log("AutoAuthorizationProfile profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
			ReportManager.log("SEF admin account logged in successfully");

			//Verify authorities 
			dashBoardPageObj.navigateToClubsManagement();	    				
		
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTBLOCKEDCLUBS);
			unblockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKCLUB);
			Validations.verifyThat().object(unblockClub).isTrue().perform();
			ReportManager.log("Mangement unblock club action found");
			
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTALLCLUBS); 
			manageAchievements=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGECLUBACHIEVEMENT);
			Validations.verifyThat().object(manageAchievements).isFalse().perform();
			ReportManager.log("Manage Club achivement action not found");
			
			blockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKCLUB);
			Validations.verifyThat().object(blockClub).isFalse().perform();
			ReportManager.log("Block club action not found");
			
			manageClubPublicProfile=clubDetailsPageObj.isManageProfileExist();
			Validations.verifyThat().object(manageClubPublicProfile).isFalse().perform();
			ReportManager.log("Manage club Profile action not found");
			
			editAllClubs=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLUB);
			Validations.verifyThat().object(editAllClubs).isFalse().perform();
			ReportManager.log("Edit club action not found");
			
			export=clubsManagementListPageObj.isExportBtnExist();
			Validations.verifyThat().object(export).isFalse().perform();
			ReportManager.log("Export button not exist");
			
			clubs=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS);
			Validations.verifyThat().object(clubs).isFalse().perform();
			ReportManager.log("Clubs not exist in the side menu");
			
			myClub=dashBoardPageObj.isSideMenuItemExist(Constants.MYCLUB);
			Validations.verifyThat().object(myClub).isFalse().perform();
			ReportManager.log("My club not exist in the side menu");
			
			dashBoardPageObj.logout();
			ReportManager.log("============== End test unblock clubs Auth   ==============");
	    }   
	    
	    @Test
	    public void testExportClubAuth() {
  	    	ReportManager.log("============== Start test export club Auth  ==============");

			//SEF user account cred
			String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
			String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
			
			   boolean myClub;
		       boolean clubs;
		       boolean export;
		       boolean blockClub;
		       boolean unblockClub;
		       boolean editAllClubs;
		       boolean manageClubPublicProfile;
		       boolean manageAchievements;
		       
			 ArrayList<String> newAuthorities =new ArrayList<String>();
			 newAuthorities.add(Constants.LIST_ALL_CLUBS);
			 newAuthorities.add(Constants.CLUBS_EXPORT);

			 //prepare profile
			 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
				 ReportManager.log("AutoAuthorizationProfile profile updated");

            homePageObj.clickLoginLink();
            
            Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
			ReportManager.log("SEF admin account logged in successfully");

			//Verify authorities 
			dashBoardPageObj.navigateToClubsManagement();	    				
		
			export=clubsManagementListPageObj.isExportBtnExist();
			Validations.verifyThat().object(export).isTrue().perform();
			ReportManager.log("Export button exist");
			
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTBLOCKEDCLUBS);
			unblockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNBLOCKCLUB);
			Validations.verifyThat().object(unblockClub).isFalse().perform();
			ReportManager.log("Mangement unblock club action not found");
			
			clubsManagementListPageObj.openFirstClub(Constants.MANAGEMENTALLCLUBS); 
			manageAchievements=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTMANAGECLUBACHIEVEMENT);
			Validations.verifyThat().object(manageAchievements).isFalse().perform();
			ReportManager.log("Manage Club achivement action not found");
			
			blockClub=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTBLOCKCLUB);
			Validations.verifyThat().object(blockClub).isFalse().perform();
			ReportManager.log("Block club action not found");
			
			manageClubPublicProfile=clubDetailsPageObj.isManageProfileExist();
			Validations.verifyThat().object(manageClubPublicProfile).isFalse().perform();
			ReportManager.log("Manage club Profile action not found");
			
			editAllClubs=clubDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLUB);
			Validations.verifyThat().object(editAllClubs).isFalse().perform();
			ReportManager.log("Edit club action not found");
			
			clubs=dashBoardPageObj.isSideMenuItemExist(Constants.CLUBS);
			Validations.verifyThat().object(clubs).isFalse().perform();
			ReportManager.log("Clubs not exist in the side menu");
			
			myClub=dashBoardPageObj.isSideMenuItemExist(Constants.MYCLUB);
			Validations.verifyThat().object(myClub).isFalse().perform();
			ReportManager.log("My club not exist in the side menu");
			
			dashBoardPageObj.logout();
			 ReportManager.log("============== End test export club Auth   ==============");
	    }   
	
			
		@AfterMethod
		public void AfterMethod(ITestResult result) {
			ReportManager.log("AfterMethod");
			Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,null,false)).isTrue().perform();
			Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,null,false)).isTrue().perform();
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
