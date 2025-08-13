package stc.esport.testcases.authorization;

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

import stc.esport.api.business.UserProfile;
import stc.esport.api.services.LoginService;
import stc.esport.api.services.TeamService;
import stc.esport.base.TestBase;
import stc.esport.pages.DashBoardPage;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;
import stc.esport.pages.Teammanagement.TeamDetailsPage;
import stc.esport.pages.Teammanagement.TeamsManagementListPage;
import stc.esport.pages.clanmanagement.ClanDetailsPage;
import stc.esport.pages.clanmanagement.ClansManagementListPage;
import stc.esport.pages.teamsclans.ClansDashboardPage;
import stc.esport.pages.teamsclans.DiscoverTeamsClansPage;
import stc.esport.pages.teamsclans.MyTeamsListPage;
import stc.esport.pages.teamsclans.TeamsDashboardPage;
import stc.esport.testdata.TestData;
import stc.esport.utils.Constants;

public class TeamsAuthroizationTests  extends TestBase{
	
	         // Variables
	  			LoginPage loginPageObj;
				HomePage homePageObj;
				DashBoardPage dashBoardPageObj;
                ClansManagementListPage  clansManagementListPageObj;
                TeamsManagementListPage  teamsManagementListPageObj;
                ClanDetailsPage          clanDetailsPageObj;
                TeamDetailsPage          teamDetailsPageObj;
				DiscoverTeamsClansPage   discoverTeamsClansPageObj;
				MyTeamsListPage myTeamsListPageObj;
				LoginService loginServiceObj;
			    ClansDashboardPage clansDashboardPageObj;
			    TeamsDashboardPage teamsDashboardPageObj;
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
    				clansManagementListPageObj= new ClansManagementListPage(driver,properties);
    				teamsManagementListPageObj= new TeamsManagementListPage(driver,properties);
    				clanDetailsPageObj= new ClanDetailsPage(driver,properties);
    				teamDetailsPageObj= new TeamDetailsPage(driver,properties);
    				discoverTeamsClansPageObj = new DiscoverTeamsClansPage(driver,properties);
    				myTeamsListPageObj= new MyTeamsListPage(driver,properties);
    				clansDashboardPageObj= new ClansDashboardPage(driver,properties);
    				teamsDashboardPageObj= new TeamsDashboardPage(driver,properties);
    				userProfileBusinessObj=new UserProfile(apiObj,properties);		
    				//services
    				loginServiceObj= new LoginService(properties);
    				
    			}

    			@BeforeTest
    			public void BeforeTest() {
    				ReportManager.log("BeforeTest");
    			}
    			
    			@DataProvider(name = "DiscoverTeamsAndClans")
    			public Object[][] testDiscoverTeamsAndClansData() throws InvalidFormatException, IOException {
    				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testDiscoverTeamsAndClans");
    				return data;
    			}
    		   
    		    @Test
    		    public void testAllTeamsAndClansListAuth() {
    	  	    	ReportManager.log("============== Start test all teams and clans list Auth  ==============");
    
    	  	        //SEF user account cred
    				String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
    				String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
    				
    				   boolean teams;
    				   boolean clans;
    				   boolean exportTeams;
    				   boolean editAllTeams;
    				   boolean archiveTeam;
    				   boolean unarchiveTeam;
    				   boolean exportClans;
    				   boolean editAllClans;
    				   boolean archiveClan;
    				   boolean unarchiveClan;
    				   
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.LIST_TEAMS_CLANS);

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
     				 ReportManager.log("AutoAuthorizationProfile profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
    				ReportManager.log("SEF admin account logged in successfully");

    				//Verify authorities 
    				
    				teams=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMS);
    				Validations.verifyThat().object(teams).isFalse().perform();
    				ReportManager.log("Teams not exist in the side menu");
    				
    				clans=dashBoardPageObj.isSideMenuItemExist(Constants.CLANS);
    				Validations.verifyThat().object(clans).isFalse().perform();
    				ReportManager.log("clans not exist in the side menu");
    				
    				//Team Management
    				dashBoardPageObj.navigateToTeamsManagement();	
    				exportTeams=teamsManagementListPageObj.isExportBtnExist();
    				Validations.assertThat().object(exportTeams).isFalse().perform();
    				ReportManager.log("Export button not exist");

    				teamsManagementListPageObj.openFirstTeam(Constants.MANAGEMENTALLTEAMS);
    				editAllTeams=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITTEAM);
    				Validations.assertThat().object(editAllTeams).isFalse().perform();
    				ReportManager.log("Edit Team action not found");
    				
    				archiveTeam= teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTARCHIVETEAM);  				
    				Validations.assertThat().object(archiveTeam).isFalse().perform();
    				ReportManager.log("Archive Team action not found");
    				
    				teamsManagementListPageObj.openFirstTeam(Constants.MANAGEMENTARCHIVEDTEAMS);
    				unarchiveTeam=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNARCHIVETEAM);
    				Validations.assertThat().object(unarchiveTeam).isFalse().perform();
    				ReportManager.log("UnArchive Team action not found");
    				
    				//Clan Management
    				dashBoardPageObj.navigateToClansManagement();
    				exportClans=clansManagementListPageObj.isExportBtnExist();
    				Validations.assertThat().object(exportClans).isFalse().perform();
    				ReportManager.log("Export button not exist");

    				clansManagementListPageObj.openFirstClan(Constants.MANAGEMENTALLCLANS);
    				editAllClans=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLAN);
    				Validations.assertThat().object(editAllClans).isFalse().perform();
    				ReportManager.log("Edit CLan action not found");
    				
    				archiveClan=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTARCHIVECLAN);   				
    				Validations.assertThat().object(archiveClan).isFalse().perform();
    				ReportManager.log("Archive CLan action not found");
    				
    				clansManagementListPageObj.openFirstClan(Constants.MANAGEMENTARCHIVEDCLANS);
    				unarchiveClan=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNARCHIVECLAN);
    				Validations.assertThat().object(unarchiveClan).isFalse().perform();
    				ReportManager.log("UnArchive CLan action not found");
    				
    				dashBoardPageObj.logout();
    			    ReportManager.log("============== End test all teams and clans list Auth   ==============");
    		    }   
    		    
    		    @Test
    		    public void testEditAllTeamsClansAuth() {
    	  	    	ReportManager.log("============== Start test Edit all teams & clans Auth  ==============");
    
    	  	         //SEF user account cred
    				String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
    				String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
    				
    				       boolean teams;
  				           boolean clans;
  				           boolean exportTeams;
  				           boolean editAllTeams;
  				           boolean archiveTeam;
  				           boolean unarchiveTeam;
  				           boolean exportClans;
  				           boolean editAllClans;
  				           boolean archiveClan;
  				           boolean unarchiveClan;
  				           
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.LIST_TEAMS_CLANS);
    				 newAuthorities.add(Constants.EDIT_ALL_TEAMS_CLANS);

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
     				 ReportManager.log("AutoAuthorizationProfile profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
    				ReportManager.log("SEF admin account logged in successfully");

    				//Verify authorities 
    				
    				//Team Management
    				dashBoardPageObj.navigateToTeamsManagement();	
    				teamsManagementListPageObj.openFirstTeam(Constants.MANAGEMENTALLTEAMS);
    				editAllTeams=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITTEAM);
    				Validations.assertThat().object(editAllTeams).isTrue().perform();
    				ReportManager.log("Edit Team action found");
    				
    				exportTeams=teamsManagementListPageObj.isExportBtnExist();
    				Validations.assertThat().object(exportTeams).isFalse().perform();
    				ReportManager.log("Export button not exist");

    				archiveTeam=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTARCHIVETEAM);
    				Validations.assertThat().object(archiveTeam).isFalse().perform();
    				ReportManager.log("Archive Team action not found");
    				
    				teamsManagementListPageObj.openFirstTeam(Constants.MANAGEMENTARCHIVEDTEAMS);
    				unarchiveTeam=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNARCHIVETEAM);
    				Validations.assertThat().object(unarchiveTeam).isFalse().perform();
    				ReportManager.log("UnArchive Team action not found");
    				
    				//Clan Management
    				dashBoardPageObj.navigateToClansManagement();
    				clansManagementListPageObj.openFirstClan(Constants.MANAGEMENTALLCLANS);
    				editAllClans=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLAN);
    				Validations.assertThat().object(editAllClans).isTrue().perform();
    				ReportManager.log("Edit CLan action found");
    				
    				exportClans=clansManagementListPageObj.isExportBtnExist();
    				Validations.assertThat().object(exportClans).isFalse().perform();
    				ReportManager.log("Export button not exist");
    				   				
    				archiveClan=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTARCHIVECLAN);
    				Validations.assertThat().object(archiveClan).isFalse().perform();
    				ReportManager.log("Archive CLan action not found");
    				
    				clansManagementListPageObj.openFirstClan(Constants.MANAGEMENTARCHIVEDCLANS);
    				unarchiveClan=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNARCHIVECLAN);
    				Validations.assertThat().object(unarchiveClan).isFalse().perform();
    				ReportManager.log("UnArchive CLan action not found");
    				
    				teams=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMS);
    				Validations.verifyThat().object(teams).isFalse().perform();
    				ReportManager.log("Teams not exist in the side menu");
    				
    				clans=dashBoardPageObj.isSideMenuItemExist(Constants.CLANS);
    				Validations.verifyThat().object(clans).isFalse().perform();
    				ReportManager.log("clans not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    			    ReportManager.log("============== End test Edit all teams & clans Auth  ==============");
    		    }   
    		    
    		    @Test
    		    public void testArchiveAllTeamsAndClansAuth() {
    	  	    	ReportManager.log("============== Start test  Archive all teams & clans Auth  ==============");
    
    	  	         //SEF user account cred
    				String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
    				String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
    				
    				   boolean teams;
			           boolean clans;
			           boolean exportTeams;
			           boolean editAllTeams;
			           boolean archiveTeam;
			           boolean unarchiveTeam;
			           boolean exportClans;
			           boolean editAllClans;
			           boolean archiveClan;
			           boolean unarchiveClan;
			           
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.LIST_TEAMS_CLANS);
    				 newAuthorities.add(Constants.ARCHIVE_ALL_TEAMS_CALNS);

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
     				 ReportManager.log("AutoAuthorizationProfile profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
    				ReportManager.log("SEF admin account logged in successfully");

                    //Verify authorities 
    				
    				//Team Management
    				dashBoardPageObj.navigateToTeamsManagement();	
    				teamsManagementListPageObj.openFirstTeam(Constants.MANAGEMENTALLTEAMS);
    				archiveTeam=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTARCHIVETEAM);
    				Validations.assertThat().object(archiveTeam).isTrue().perform();
    				ReportManager.log("Archive Team action found");
    				
    				editAllTeams=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITTEAM);
    				Validations.assertThat().object(editAllTeams).isFalse().perform();
    				ReportManager.log("Edit Team action not found");
    				
    				exportTeams=teamsManagementListPageObj.isExportBtnExist();
    				Validations.assertThat().object(exportTeams).isFalse().perform();
    				ReportManager.log("Export button not exist");

    				teamsManagementListPageObj.openFirstTeam(Constants.MANAGEMENTARCHIVEDTEAMS);
    				unarchiveTeam=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNARCHIVETEAM);
    				Validations.assertThat().object(unarchiveTeam).isFalse().perform();
    				ReportManager.log("UnArchive Team action not found");
    				
    				//Clan Management
    				dashBoardPageObj.navigateToClansManagement();
    				clansManagementListPageObj.openFirstClan(Constants.MANAGEMENTALLCLANS);
    				archiveClan=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTARCHIVECLAN);
    				Validations.assertThat().object(archiveClan).isTrue().perform();
    				ReportManager.log("Archive CLan action found");
    				
    				editAllClans=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLAN);
    				Validations.assertThat().object(editAllClans).isFalse().perform();
    				ReportManager.log("Edit CLan action not found");
    				
    				exportClans=clansManagementListPageObj.isExportBtnExist();
    				Validations.assertThat().object(exportClans).isFalse().perform();
    				ReportManager.log("Export button not exist");
    				
    				clansManagementListPageObj.openFirstClan(Constants.MANAGEMENTARCHIVEDCLANS);
    				unarchiveClan=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNARCHIVECLAN);
    				Validations.assertThat().object(unarchiveClan).isFalse().perform();
    				ReportManager.log("UnArchive CLan action not found");
    				
    				teams=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMS);
    				Validations.verifyThat().object(teams).isFalse().perform();
    				ReportManager.log("Teams not exist in the side menu");
    				
    				clans=dashBoardPageObj.isSideMenuItemExist(Constants.CLANS);
    				Validations.verifyThat().object(clans).isFalse().perform();
    				ReportManager.log("clans not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    			    ReportManager.log("============== End test Archive all teams & clans Auth  ==============");
    		    }   
    		    
    		    @Test
    		    public void testUnarchiveAllTeamsClansAuth() {
    	  	    	ReportManager.log("============== Start test   unarchive all teams & clans Auth  ==============");
    
    	  	         //SEF user account cred
    				String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
    				String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
    				
    				   boolean teams;
			           boolean clans;
			           boolean exportTeams;
			           boolean editAllTeams;
			           boolean archiveTeam;
			           boolean unarchiveTeam;
			           boolean exportClans;
			           boolean editAllClans;
			           boolean archiveClan;
			           boolean unarchiveClan;
			           
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.LIST_TEAMS_CLANS);
    				 newAuthorities.add(Constants.UNARCHIVE_ALL_TEAMS_CLANS);

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
     				 ReportManager.log("AutoAuthorizationProfile profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
    				ReportManager.log("SEF admin account logged in successfully");

                    //Verify authorities 
    				
    				//Team Management
    				dashBoardPageObj.navigateToTeamsManagement();	
    				teamsManagementListPageObj.openFirstTeam(Constants.MANAGEMENTARCHIVEDTEAMS);
    				unarchiveTeam=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNARCHIVETEAM);
    				Validations.assertThat().object(unarchiveTeam).isTrue().perform();
    				ReportManager.log("UnArchive Team action found");
    				
    				teamsManagementListPageObj.openFirstTeam(Constants.MANAGEMENTALLTEAMS);
    				archiveTeam=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTARCHIVETEAM);
    				Validations.assertThat().object(archiveTeam).isFalse().perform();
    				ReportManager.log("Archive Team action not found");
    				
    				editAllTeams=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITTEAM);
    				Validations.assertThat().object(editAllTeams).isFalse().perform();
    				ReportManager.log("Edit Team action not found");
    				
    				exportTeams=teamsManagementListPageObj.isExportBtnExist();
    				Validations.assertThat().object(exportTeams).isFalse().perform();
    				ReportManager.log("Export button not exist");

    				
    				//Clan Management
    				dashBoardPageObj.navigateToClansManagement();
    				clansManagementListPageObj.openFirstClan(Constants.MANAGEMENTARCHIVEDCLANS);
    				unarchiveClan=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNARCHIVECLAN);
    				Validations.assertThat().object(unarchiveClan).isTrue().perform();
    				ReportManager.log("UnArchive CLan action found");
    				
    				clansManagementListPageObj.openFirstClan(Constants.MANAGEMENTALLCLANS);
    				archiveClan=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTARCHIVECLAN);
    				Validations.assertThat().object(archiveClan).isFalse().perform();
    				ReportManager.log("Archive CLan action not found");
    				
    				editAllClans=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLAN);
    				Validations.assertThat().object(editAllClans).isFalse().perform();
    				ReportManager.log("Edit CLan action not found");
    				
    				exportClans=clansManagementListPageObj.isExportBtnExist();
    				Validations.assertThat().object(exportClans).isFalse().perform();
    				ReportManager.log("Export button not exist");
    				
    				teams=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMS);
    				Validations.verifyThat().object(teams).isFalse().perform();
    				ReportManager.log("Teams not exist in the side menu");
    				
    				clans=dashBoardPageObj.isSideMenuItemExist(Constants.CLANS);
    				Validations.verifyThat().object(clans).isFalse().perform();
    				ReportManager.log("clans not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    			    ReportManager.log("============== End test Archive all teams & clans Auth  ==============");
    		    }   
    		    
    		    @Test
    		    public void testExportTeamsAndClansAuth() {
    	  	    	ReportManager.log("============== Start test export teams and clans Auth  ==============");
    
    	  	         //SEF user account cred
    				String AuthSEFAdmin = properties.getProperty("AuthSEFAdmin");
    				String AuthSEFAdminPW = properties.getProperty("AuthSEFAdminPW"); 
    				
    				   boolean teams;
			           boolean clans;
			           boolean exportTeams;
			           boolean editAllTeams;
			           boolean archiveTeam;
			           boolean unarchiveTeam;
			           boolean exportClans;
			           boolean editAllClans;
			           boolean archiveClan;
			           boolean unarchiveClan;
			           
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.LIST_TEAMS_CLANS);
    				 newAuthorities.add(Constants.TEAMS_EXPORT);
 
    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.SEFADMINPROFILE,newAuthorities,true)).isTrue().perform();
     				 ReportManager.log("AutoAuthorizationProfile profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(AuthSEFAdmin, AuthSEFAdminPW)).isTrue().perform();
    				ReportManager.log("SEF admin account logged in successfully");

                    //Verify authorities 
    				
    				//Team Management
    				dashBoardPageObj.navigateToTeamsManagement();	
    				teamsManagementListPageObj.openFirstTeam(Constants.MANAGEMENTALLTEAMS);
    				exportTeams=teamsManagementListPageObj.isExportBtnExist();
    				Validations.assertThat().object(exportTeams).isTrue().perform();
    				ReportManager.log("Export button exist");
    				
    				archiveTeam=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTARCHIVETEAM);
    				Validations.assertThat().object(archiveTeam).isFalse().perform();
    				ReportManager.log("Archive Team action not found");
    				
    				editAllTeams=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITTEAM);
    				Validations.assertThat().object(editAllTeams).isFalse().perform();
    				ReportManager.log("Edit Team action not found");
    				
    				teamsManagementListPageObj.openFirstTeam(Constants.MANAGEMENTARCHIVEDTEAMS);
    				unarchiveTeam=teamDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNARCHIVETEAM);
    				Validations.assertThat().object(unarchiveTeam).isFalse().perform();
    				ReportManager.log("UnArchive Team action not found");

    	
    				//Clan Management
    				dashBoardPageObj.navigateToClansManagement();
    				clansManagementListPageObj.openFirstClan(Constants.MANAGEMENTALLCLANS);
    				exportClans=clansManagementListPageObj.isExportBtnExist();
    				Validations.assertThat().object(exportClans).isTrue().perform();
    				ReportManager.log("Export button exist");
    				
    				archiveClan=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTARCHIVECLAN);
    				Validations.assertThat().object(archiveClan).isFalse().perform();
    				ReportManager.log("Archive CLan action not found");
    				
    				editAllClans=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTEDITCLAN);
    				Validations.assertThat().object(editAllClans).isFalse().perform();
    				ReportManager.log("Edit CLan action not found");
    				
    				clansManagementListPageObj.openFirstClan(Constants.MANAGEMENTARCHIVEDCLANS);
    				unarchiveClan=clanDetailsPageObj.checkActionExist(Constants.MANAGEMENTUNARCHIVECLAN);
    				Validations.assertThat().object(unarchiveClan).isFalse().perform();
    				ReportManager.log("UnArchive CLan action not found");
    				
    				teams=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMS);
    				Validations.verifyThat().object(teams).isFalse().perform();
    				ReportManager.log("Teams not exist in the side menu");
    				
    				clans=dashBoardPageObj.isSideMenuItemExist(Constants.CLANS);
    				Validations.verifyThat().object(clans).isFalse().perform();
    				ReportManager.log("clans not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    			    ReportManager.log("============== End test export teams and clans Auth  ==============");
    		    }    
    		    
    		    @Test(dataProvider = "DiscoverTeamsAndClans")
    		    public void testDiscoverTeamsClansAuth(String userRole) {
    	  	    	ReportManager.log("============== Start test Discover teams & clans Auth  ==============");
    
    				//club owner account cred
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
    				
    				//media outlet account cred
    				String mediaOutletAcc = properties.getProperty("mediaOutletAcc");
    				String mediaOutletPw = properties.getProperty("mediaOutletPw"); 
    				     
    				    boolean teamsManagement;
    				    boolean clansManagement;
    				    boolean discoverClans;
    				    boolean discoverTeams;
    				    boolean myTeamsList;
    				    boolean myClansList;
    				    boolean archivedTeamList;
    				    boolean archivedClanList;
    				    boolean membershipClansList;
    				    boolean membershipTeamsList;
    				
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.DISCOVER_TEAMS_CLANS);

    				//prepare profile
    				 if(userRole.equals(Constants.CLUBOWNERROLE)) {
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
     				 ReportManager.log("Club owner profile updated");
    				 }else {
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.MEDIAOUTLETROLE,newAuthorities,true)).isTrue().perform();
         		     ReportManager.log("Media outlet profile updated");
    				 }

                    homePageObj.clickLoginLink();
                    
                    if(userRole.equals(Constants.CLUBOWNERROLE)) {
                    Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
    				ReportManager.log("Club owner account logged in successfully");
                    }else {
                    Validations.assertThat().object(loginPageObj.login(mediaOutletAcc, mediaOutletPw)).isTrue().perform();
        			ReportManager.log("Media Outlet account logged in successfully");
                    }
                    
                    //Verify authorities
    				
    				//clans 
    				dashBoardPageObj.navigateToClans();
    				discoverClans=clansDashboardPageObj.isDiscoverClanTabExist();
    				Validations.verifyThat().object(discoverClans).isTrue().perform();
    				ReportManager.log("Discover clans tab exist");
    				
    				myClansList=clansDashboardPageObj.isMyClansTabsExist();
    				Validations.verifyThat().object(myClansList).isFalse().perform();
    				ReportManager.log("My clans tab not exist");
    				
    				membershipClansList=clansDashboardPageObj.isMembershipClanTanExist();
    				Validations.verifyThat().object(membershipClansList).isFalse().perform();
    				ReportManager.log("Membership clans tab not exist");
    				
    				archivedClanList=clansDashboardPageObj.isArchivedClanTabExist();
    				Validations.verifyThat().object(archivedClanList).isFalse().perform();
    				ReportManager.log("Archived clans tab not exist");
    				
    				//teams 
    				dashBoardPageObj.navigateToTeams();
    				discoverTeams=teamsDashboardPageObj.isDiscoverTeamTabExist();
    				Validations.verifyThat().object(discoverTeams).isTrue().perform();
    				ReportManager.log("Discover teams tab exist");
    				
    				myTeamsList=teamsDashboardPageObj.isMyTeamsTabsExist();
    				Validations.verifyThat().object(myTeamsList).isFalse().perform();
    				ReportManager.log("My teams tab not exist");
    				
    				membershipTeamsList=teamsDashboardPageObj.isMembershipTeamTabExist();
    				Validations.verifyThat().object(membershipTeamsList).isFalse().perform();
    				ReportManager.log("Membership teams tab not exist");
    				
    				archivedTeamList=teamsDashboardPageObj.isArchivedTeamTabExist();
    				Validations.verifyThat().object(archivedTeamList).isFalse().perform();
    				ReportManager.log("Archived teams tab not exist");
    				
    				teamsManagement=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMSMANAGEMENT);
     				Validations.verifyThat().object(teamsManagement).isFalse().perform();
     				ReportManager.log("Teams management not exist in the side menu");
     				
     				clansManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLANSMANAGEMENT);
     				Validations.verifyThat().object(clansManagement).isFalse().perform();
     				ReportManager.log("clans management not exist in the side menu  ");
    				
    				dashBoardPageObj.logout();
    		        ReportManager.log("============== End test Discover teams & clans Auth  ==============");
    		    }
    		    
    		    @Test
    		    public void testListMyClansAndArchivedAuth() {
    	  	    	ReportManager.log("============== Start test list my clans and archived clans Auth  ==============");
    				
    				//media outlet account cred
    				String mediaOutletAcc = properties.getProperty("mediaOutletAcc");
    				String mediaOutletPw = properties.getProperty("mediaOutletPw"); 
    				     
    				    boolean teamsManagement;
    				    boolean clansManagement;
    				    boolean discoverClans;
    				    boolean discoverTeams;
    				    boolean myTeamsList;
    				    boolean myClansList;
    				    boolean archivedTeamList;
    				    boolean archivedClanList;
    				    boolean membershipClansList;
    				    boolean membershipTeamsList;
    				
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.TEAMS_CLANS_ARCHIVE_TEAMS_CLANS);

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.MEDIAOUTLETROLE,newAuthorities,true)).isTrue().perform();
         		     ReportManager.log("Media outlet profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(mediaOutletAcc, mediaOutletPw)).isTrue().perform();
        			ReportManager.log("Media Outlet account logged in successfully");
                   
                    
                    //Verify authorities
    				
    				//clans 
    				dashBoardPageObj.navigateToClans();
    				myClansList=clansDashboardPageObj.isMyClansTabsExist();
    				Validations.verifyThat().object(myClansList).isTrue().perform();
    				ReportManager.log("My clans tab exist");
    				
    				membershipClansList=clansDashboardPageObj.isMembershipClanTanExist();
    				Validations.verifyThat().object(membershipClansList).isTrue().perform();
    				ReportManager.log("Membership clans tab exist");
    				
    				archivedClanList=clansDashboardPageObj.isArchivedClanTabExist();
    				Validations.verifyThat().object(archivedClanList).isTrue().perform();
    				ReportManager.log("Archived clans tab exist");
    				
    				discoverClans=clansDashboardPageObj.isDiscoverClanTabExist();
    				Validations.verifyThat().object(discoverClans).isFalse().perform();
    				ReportManager.log("Discover clans tab not exist");
    				
    				//teams 
    				dashBoardPageObj.navigateToTeams();
    				membershipTeamsList=teamsDashboardPageObj.isMembershipTeamTabExist();
    				Validations.verifyThat().object(membershipTeamsList).isTrue().perform();
    				ReportManager.log("Membership teams tab exist");
    				
    				discoverTeams=teamsDashboardPageObj.isDiscoverTeamTabExist();
    				Validations.verifyThat().object(discoverTeams).isFalse().perform();
    				ReportManager.log("Discover teams tab not exist");
    				
    				myTeamsList=teamsDashboardPageObj.isMyTeamsTabsExist();
    				Validations.verifyThat().object(myTeamsList).isFalse().perform();
    				ReportManager.log("My teams tab not exist");
    				
    				archivedTeamList=teamsDashboardPageObj.isArchivedTeamTabExist();
    				Validations.verifyThat().object(archivedTeamList).isFalse().perform();
    				ReportManager.log("Archived teams tab not exist");
    				
    				teamsManagement=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMSMANAGEMENT);
     				Validations.verifyThat().object(teamsManagement).isFalse().perform();
     				ReportManager.log("Teams management not exist in the side menu");
     				
     				clansManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLANSMANAGEMENT);
     				Validations.verifyThat().object(clansManagement).isFalse().perform();
     				ReportManager.log("clans management not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    			    ReportManager.log("============== End test list my clans and archived clans Auth  ==============");
    		    } 
    		    
    		    @Test
    		    public void testListMyTeamsAndArchivedAuth() {
    	  	    	ReportManager.log("============== Start test list my teams and archived teams Auth  ==============");
    				
    	  	          //club owner account cred
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
    				
    				String pageNotAuth=properties.getProperty("notAuthorizedUrl"); 
    				     
    				    boolean teamsManagement;
    				    boolean clansManagement;
    				    boolean clans;
    				    boolean discoverTeams;
    				    boolean myTeamsList;
    				    boolean archivedTeamList;
    				    boolean membershipTeamsList;
    				
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.TEAMS_CLANS_ARCHIVE_TEAMS_CLANS);

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
         		     ReportManager.log("Media outlet profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
        			ReportManager.log("Media Outlet account logged in successfully");
                   
                    
                    //Verify authorities 
        			
        			//teams 
    				dashBoardPageObj.navigateToTeams();
    				myTeamsList=teamsDashboardPageObj.isMyTeamsTabsExist();
    				Validations.verifyThat().object(myTeamsList).isTrue().perform();
    				ReportManager.log("My teams tab exist");
    				
    				archivedTeamList=teamsDashboardPageObj.isArchivedTeamTabExist();
    				Validations.verifyThat().object(archivedTeamList).isTrue().perform();
    				ReportManager.log("Archived teams tab exist");
    				
    				membershipTeamsList=teamsDashboardPageObj.isMembershipTeamTabExist();
    				Validations.verifyThat().object(membershipTeamsList).isFalse().perform();
    				ReportManager.log("Membership teams tab not exist");
    				
    				discoverTeams=teamsDashboardPageObj.isDiscoverTeamTabExist();
    				Validations.verifyThat().object(discoverTeams).isFalse().perform();
    				ReportManager.log("Discover teams tab not exist");
    				
    				//clans 
    				dashBoardPageObj.navigateToClans();
    				clans=dashBoardPageObj.isPageNotAuthorized(pageNotAuth);
    				Validations.verifyThat().object(clans).isFalse().perform();
     				ReportManager.log("Clans not exist in the side menu");
     				
    				teamsManagement=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMSMANAGEMENT);
     				Validations.verifyThat().object(teamsManagement).isFalse().perform();
     				ReportManager.log("Teams management not exist in the side menu");
     				
     				clansManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLANSMANAGEMENT);
     				Validations.verifyThat().object(clansManagement).isFalse().perform();
     				ReportManager.log("clans management not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    				
    			    ReportManager.log("============== End test list my teams and archived teams Auth  ==============");
    		    } 
    		    
    		    @Test
    		    public void testAddClansAuth() {
    	  	    	ReportManager.log("============== Start test add clans Auth  ==============");
    				
    				//media outlet account cred
    				String mediaOutletAcc = properties.getProperty("mediaOutletAcc");
    				String mediaOutletPw = properties.getProperty("mediaOutletPw"); 
    				     
    				    boolean teamsManagement;
    				    boolean clansManagement;
    				    boolean discoverClans;
    				    boolean discoverTeams;
    				    boolean myTeamsList;
    				    boolean myClansList;
    				    boolean archivedTeamList;
    				    boolean archivedClanList;
    				    boolean membershipClansList;
    				    boolean membershipTeamsList;
    				    boolean addTeams;
    				    boolean addClans;
    				    
    				    
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.TEAMS_CLANS_ARCHIVE_TEAMS_CLANS);
    				 newAuthorities.add(Constants.ADD_TEAM_CLAN);


    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.MEDIAOUTLETROLE,newAuthorities,true)).isTrue().perform();
         		     ReportManager.log("Media outlet profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(mediaOutletAcc, mediaOutletPw)).isTrue().perform();
        			ReportManager.log("Media Outlet account logged in successfully");
                   
                    
                    //Verify authorities
    				
    				//clans 
    				dashBoardPageObj.navigateToClans();
    				addClans=myTeamsListPageObj.isAddTeamClanExist();
    				Validations.verifyThat().object(addClans).isTrue().perform();
    				ReportManager.log("Add clans button exist");
    				
    				myClansList=clansDashboardPageObj.isMyClansTabsExist();
    				Validations.verifyThat().object(myClansList).isTrue().perform();
    				ReportManager.log("My clans tab exist");
    				
    				membershipClansList=clansDashboardPageObj.isMembershipClanTanExist();
    				Validations.verifyThat().object(membershipClansList).isTrue().perform();
    				ReportManager.log("Membership clans tab exist");
    				
    				archivedClanList=clansDashboardPageObj.isArchivedClanTabExist();
    				Validations.verifyThat().object(archivedClanList).isTrue().perform();
    				ReportManager.log("Archived clans tab exist");
    				
    				discoverClans=clansDashboardPageObj.isDiscoverClanTabExist();
    				Validations.verifyThat().object(discoverClans).isFalse().perform();
    				ReportManager.log("Discover clans tab not exist");
    				
    				//teams 
    				dashBoardPageObj.navigateToTeams();
    				addTeams=myTeamsListPageObj.isAddTeamClanExist();
    				Validations.verifyThat().object(addTeams).isFalse().perform();
    				ReportManager.log("Add teams button not exist");
    				
    				membershipTeamsList=teamsDashboardPageObj.isMembershipTeamTabExist();
    				Validations.verifyThat().object(membershipTeamsList).isTrue().perform();
    				ReportManager.log("Membership teams tab exist");
    				
    				discoverTeams=teamsDashboardPageObj.isDiscoverTeamTabExist();
    				Validations.verifyThat().object(discoverTeams).isFalse().perform();
    				ReportManager.log("Discover teams tab not exist");
    				
    				myTeamsList=teamsDashboardPageObj.isMyTeamsTabsExist();
    				Validations.verifyThat().object(myTeamsList).isFalse().perform();
    				ReportManager.log("My teams tab not exist");
    				
    				archivedTeamList=teamsDashboardPageObj.isArchivedTeamTabExist();
    				Validations.verifyThat().object(archivedTeamList).isFalse().perform();
    				ReportManager.log("Archived teams tab not exist");
    				
    				teamsManagement=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMSMANAGEMENT);
     				Validations.verifyThat().object(teamsManagement).isFalse().perform();
     				ReportManager.log("Teams management not exist in the side menu");
     				
     				clansManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLANSMANAGEMENT);
     				Validations.verifyThat().object(clansManagement).isFalse().perform();
     				ReportManager.log("clans management not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    			    ReportManager.log("============== End test add clans Auth  ==============");
    		    } 
    		    
    		    @Test
    		    public void testAddTeamsAuth() {
    	  	    	ReportManager.log("============== Start test add teams Auth  ==============");
    				
    	  	        //club owner account cred
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
    				
    				String pageNotAuth=properties.getProperty("notAuthorizedUrl"); 

    				    boolean teamsManagement;
    				    boolean clansManagement;
    				    boolean discoverTeams;
    				    boolean myTeamsList;
    				    boolean archivedTeamList;
    				    boolean membershipTeamsList;
    				    boolean addTeams;
    				    boolean clans;
    				    
    				    
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.TEAMS_CLANS_ARCHIVE_TEAMS_CLANS);
    				 newAuthorities.add(Constants.ADD_TEAM_CLAN);


    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
         		     ReportManager.log("Club owner profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
        			ReportManager.log("Club owner account logged in successfully");
                   
                    
                    //Verify authorities
        			//teams 
    				dashBoardPageObj.navigateToTeams();
    				addTeams=myTeamsListPageObj.isAddTeamClanExist();
    				Validations.verifyThat().object(addTeams).isTrue().perform();
    				ReportManager.log("Add teams button exist");
    				
    				myTeamsList=teamsDashboardPageObj.isMyTeamsTabsExist();
    				Validations.verifyThat().object(myTeamsList).isTrue().perform();
    				ReportManager.log("My teams tab exist");
    				
    				archivedTeamList=teamsDashboardPageObj.isArchivedTeamTabExist();
    				Validations.verifyThat().object(archivedTeamList).isTrue().perform();
    				ReportManager.log("Archived teams tab exist");
    				
    				membershipTeamsList=teamsDashboardPageObj.isMembershipTeamTabExist();
    				Validations.verifyThat().object(membershipTeamsList).isFalse().perform();
    				ReportManager.log("Membership teams tab not exist");
    				
    				discoverTeams=teamsDashboardPageObj.isDiscoverTeamTabExist();
    				Validations.verifyThat().object(discoverTeams).isFalse().perform();
    				ReportManager.log("Discover teams tab not exist");
    				
    				//clans 
    				dashBoardPageObj.navigateToClans();
    				clans=dashBoardPageObj.isPageNotAuthorized(pageNotAuth);
    				Validations.verifyThat().object(clans).isFalse().perform();
     				ReportManager.log("Clans not exist in the side menu");
    				
    				teamsManagement=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMSMANAGEMENT);
     				Validations.verifyThat().object(teamsManagement).isFalse().perform();
     				ReportManager.log("Teams management not exist in the side menu");
     				
     				clansManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLANSMANAGEMENT);
     				Validations.verifyThat().object(clansManagement).isFalse().perform();
     				ReportManager.log("clans management not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    			    ReportManager.log("============== End test add teams Auth ==============");
    		    } 
    		    
    		    @Test
    		    public void testEditTeamAuth() {
    	  	    	ReportManager.log("============== Start test edit teams Auth  ==============");
    				
    	  	        //club owner account cred
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
    				     
    			   String pageNotAuth=properties.getProperty("notAuthorizedUrl"); 

    				    boolean teamsManagement;
    				    boolean clansManagement;
    				    boolean discoverTeams;
    				    boolean myTeamsList;
    				    boolean archivedTeamList;
    				    boolean membershipTeamsList;
    				    boolean addTeams;
    				    boolean editTeams;
    				    boolean clans;
    				    
    				    
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.TEAMS_CLANS_ARCHIVE_TEAMS_CLANS);
    				 newAuthorities.add(Constants.EDIT_TEAM_CLAN);

    				 //Create Team
         		     String teamName= createTeamORClan(Constants.CLUBTEAM);

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
         		     ReportManager.log("Media outlet profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
        			ReportManager.log("Media Outlet account logged in successfully");
                   
                    
                    //Verify authorities
        			//teams 
    				dashBoardPageObj.navigateToTeams();
    				editTeams=myTeamsListPageObj.isActionExist(teamName, Constants.EDITTEAM);
    				Validations.verifyThat().object(editTeams).isTrue().perform();
    				ReportManager.log("Edit teams button not exist");
    				
    				addTeams=myTeamsListPageObj.isAddTeamClanExist();
    				Validations.verifyThat().object(addTeams).isFalse().perform();
    				ReportManager.log("Add teams button not exist");
    				
    				myTeamsList=teamsDashboardPageObj.isMyTeamsTabsExist();
    				Validations.verifyThat().object(myTeamsList).isTrue().perform();
    				ReportManager.log("My teams tab exist");
    				
    				archivedTeamList=teamsDashboardPageObj.isArchivedTeamTabExist();
    				Validations.verifyThat().object(archivedTeamList).isTrue().perform();
    				ReportManager.log("Archived teams tab exist");
    				
    				membershipTeamsList=teamsDashboardPageObj.isMembershipTeamTabExist();
    				Validations.verifyThat().object(membershipTeamsList).isFalse().perform();
    				ReportManager.log("Membership teams tab not exist");
    				
    				discoverTeams=teamsDashboardPageObj.isDiscoverTeamTabExist();
    				Validations.verifyThat().object(discoverTeams).isFalse().perform();
    				ReportManager.log("Discover teams tab not exist");
    				
    				//clans 
    				dashBoardPageObj.navigateToClans();
    				clans=dashBoardPageObj.isPageNotAuthorized(pageNotAuth);
    				Validations.verifyThat().object(clans).isFalse().perform();
     				ReportManager.log("Clans not exist in the side menu");
    				
    				teamsManagement=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMSMANAGEMENT);
     				Validations.verifyThat().object(teamsManagement).isFalse().perform();
     				ReportManager.log("Teams management not exist in the side menu");
     				
     				clansManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLANSMANAGEMENT);
     				Validations.verifyThat().object(clansManagement).isFalse().perform();
     				ReportManager.log("clans management not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    			     ReportManager.log("============== End test edit teams Auth ==============");
    		    } 
    		    
    		    @Test
    		    public void testEditClanAuth() {
    	  	    	ReportManager.log("============== Start test edit clans Auth  ==============");
    				
    	  	    	 //media outlet account cred
    				String mediaOutletAcc = properties.getProperty("mediaOutletAcc");
    				String mediaOutletPw = properties.getProperty("mediaOutletPw"); 
    				     
    				    boolean teamsManagement;
    				    boolean clansManagement;
    				    boolean myClansList;
    				    boolean membershipTeamsList;
    				    boolean membershipClansList;
    				    boolean archivedClanList;
    				    boolean discoverClans;
    				    boolean addClans;
    				    boolean editClans; 
    				    
    				    
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.TEAMS_CLANS_ARCHIVE_TEAMS_CLANS);
    				 newAuthorities.add(Constants.EDIT_TEAM_CLAN);

    				 //Create Team
         		     String clanName= createTeamORClan(Constants.CLANTEAM);
         		     

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.MEDIAOUTLETROLE,newAuthorities,true)).isTrue().perform();
         		     ReportManager.log("Media outlet profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(mediaOutletAcc, mediaOutletPw)).isTrue().perform();
        			ReportManager.log("Media Outlet account logged in successfully");
                   
                    
                    //Verify authorities
        
        			//clans 
    				dashBoardPageObj.navigateToClans();
    				editClans=myTeamsListPageObj.isActionExist(clanName, Constants.EDITTEAM);
    				Validations.verifyThat().object(editClans).isTrue().perform();
    				ReportManager.log("Edit Clan button exist");

    				myClansList=clansDashboardPageObj.isMyClansTabsExist();
    				Validations.verifyThat().object(myClansList).isTrue().perform();
    				ReportManager.log("My clans tab exist");
    				
    				membershipClansList=clansDashboardPageObj.isMembershipClanTanExist();
    				Validations.verifyThat().object(membershipClansList).isTrue().perform();
    				ReportManager.log("Membership clans tab exist");
    				
    				archivedClanList=clansDashboardPageObj.isArchivedClanTabExist();
    				Validations.verifyThat().object(archivedClanList).isTrue().perform();
    				ReportManager.log("Archived clans tab exist");
    				
    				discoverClans=clansDashboardPageObj.isDiscoverClanTabExist();
    				Validations.verifyThat().object(discoverClans).isFalse().perform();
    				ReportManager.log("Discover clans tab not exist");
    				
    				addClans=myTeamsListPageObj.isAddTeamClanExist();
    				Validations.verifyThat().object(addClans).isFalse().perform();
    				ReportManager.log("Add clans button not exist");
     				
        			//teams 
    				dashBoardPageObj.navigateToTeams();
    				membershipTeamsList=teamsDashboardPageObj.isMembershipTeamTabExist();
    				Validations.verifyThat().object(membershipTeamsList).isTrue().perform();
    				ReportManager.log("Membership teams tab exist");
    			    				
    				teamsManagement=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMSMANAGEMENT);
     				Validations.verifyThat().object(teamsManagement).isFalse().perform();
     				ReportManager.log("Teams management not exist in the side menu");
     				
     				clansManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLANSMANAGEMENT);
     				Validations.verifyThat().object(clansManagement).isFalse().perform();
     				ReportManager.log("clans management not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    				
    			   ReportManager.log("============== End test edit clans Auth ==============");
    		    } 
    		    
    		    @Test
    		    public void testArchiveTeamAuth() {
    	  	    	ReportManager.log("============== Start test archive teams Auth  ==============");
    				
    	  	        //club owner account cred
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
    				     
    			   String pageNotAuth=properties.getProperty("notAuthorizedUrl"); 

    				    boolean teamsManagement;
    				    boolean clansManagement;
    				    boolean discoverTeams;
    				    boolean myTeamsList;
    				    boolean archivedTeamList;
    				    boolean membershipTeamsList;
    				    boolean addTeams;
    				    boolean editTeams;
    				    boolean archiveTeam;
    				    boolean clans;
    				    
    				    
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.TEAMS_CLANS_ARCHIVE_TEAMS_CLANS);
    				 newAuthorities.add(Constants.ARCHIVE_TEAM_CLAN);

    				 //Create Team
         		     String teamName= createTeamORClan(Constants.CLUBTEAM);

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
         		     ReportManager.log("Club owner profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
        			ReportManager.log("Media Outlet account logged in successfully");
                   
                    
                    //Verify authorities
        			//teams 
    				dashBoardPageObj.navigateToTeams();
    				archiveTeam= myTeamsListPageObj.isActionExist(teamName, Constants.ARCHIVETEAM);
    				Validations.verifyThat().object(archiveTeam).isTrue().perform();
    				ReportManager.log("archive teams button exist");
    				
    				editTeams=myTeamsListPageObj.isActionExist(teamName, Constants.EDITTEAM);
    				Validations.verifyThat().object(editTeams).isFalse().perform();
    				ReportManager.log("Edit teams button not exist");
    				
    				addTeams=myTeamsListPageObj.isAddTeamClanExist();
    				Validations.verifyThat().object(addTeams).isFalse().perform();
    				ReportManager.log("Add teams button not exist");
    				
    				myTeamsList=teamsDashboardPageObj.isMyTeamsTabsExist();
    				Validations.verifyThat().object(myTeamsList).isTrue().perform();
    				ReportManager.log("My teams tab exist");
    				
    				archivedTeamList=teamsDashboardPageObj.isArchivedTeamTabExist();
    				Validations.verifyThat().object(archivedTeamList).isTrue().perform();
    				ReportManager.log("Archived teams tab exist");
    				
    				membershipTeamsList=teamsDashboardPageObj.isMembershipTeamTabExist();
    				Validations.verifyThat().object(membershipTeamsList).isFalse().perform();
    				ReportManager.log("Membership teams tab not exist");
    				
    				discoverTeams=teamsDashboardPageObj.isDiscoverTeamTabExist();
    				Validations.verifyThat().object(discoverTeams).isFalse().perform();
    				ReportManager.log("Discover teams tab not exist");
    				
    				//clans 
    				dashBoardPageObj.navigateToClans();
    				clans=dashBoardPageObj.isPageNotAuthorized(pageNotAuth);
    				Validations.verifyThat().object(clans).isFalse().perform();
     				ReportManager.log("Clans not exist in the side menu");
    				
    				teamsManagement=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMSMANAGEMENT);
     				Validations.verifyThat().object(teamsManagement).isFalse().perform();
     				ReportManager.log("Teams management not exist in the side menu");
     				
     				clansManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLANSMANAGEMENT);
     				Validations.verifyThat().object(clansManagement).isFalse().perform();
     				ReportManager.log("clans management not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    			    ReportManager.log("============== End test archive teams Auth ==============");
    		    } 
    		    
    		    @Test
    		    public void testArchiveClanAuth() {
    	  	    	ReportManager.log("============== Start test archive clans Auth  ==============");
    				
    	  	    	 //media outlet account cred
    				String mediaOutletAcc = properties.getProperty("mediaOutletAcc");
    				String mediaOutletPw = properties.getProperty("mediaOutletPw"); 
    				     
    				    boolean teamsManagement;
    				    boolean clansManagement;
    				    boolean myClansList;
    				    boolean membershipTeamsList;
    				    boolean membershipClansList;
    				    boolean archivedClanList;
    				    boolean discoverClans;
    				    boolean addClans;
    				    boolean editClans; 
    				    boolean archiveClans;
    				   
    				    
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.TEAMS_CLANS_ARCHIVE_TEAMS_CLANS);
    				 newAuthorities.add(Constants.ARCHIVE_TEAM_CLAN);

    				 //Create Team
         		     String clanName= createTeamORClan(Constants.CLANTEAM);
         		     

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.MEDIAOUTLETROLE,newAuthorities,true)).isTrue().perform();
         		     ReportManager.log("Media outlet profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(mediaOutletAcc, mediaOutletPw)).isTrue().perform();
        			ReportManager.log("Media Outlet account logged in successfully");
                   
                    
                    //Verify authorities
        
        			//clans 
    				dashBoardPageObj.navigateToClans();
    				archiveClans=myTeamsListPageObj.isActionExist(clanName, Constants.ARCHIVETEAM);
    				Validations.verifyThat().object(archiveClans).isTrue().perform();
    				ReportManager.log("Archive Clan button exist");
    				
    				editClans=myTeamsListPageObj.isActionExist(clanName, Constants.EDITTEAM);
    				Validations.verifyThat().object(editClans).isFalse().perform();
    				ReportManager.log("Edit Clan button exist");

    				myClansList=clansDashboardPageObj.isMyClansTabsExist();
    				Validations.verifyThat().object(myClansList).isTrue().perform();
    				ReportManager.log("My clans tab exist");
    				
    				membershipClansList=clansDashboardPageObj.isMembershipClanTanExist();
    				Validations.verifyThat().object(membershipClansList).isTrue().perform();
    				ReportManager.log("Membership clans tab exist");
    				
    				archivedClanList=clansDashboardPageObj.isArchivedClanTabExist();
    				Validations.verifyThat().object(archivedClanList).isTrue().perform();
    				ReportManager.log("Archived clans tab exist");
    				
    				discoverClans=clansDashboardPageObj.isDiscoverClanTabExist();
    				Validations.verifyThat().object(discoverClans).isFalse().perform();
    				ReportManager.log("Discover clans tab not exist");
    				
    				addClans=myTeamsListPageObj.isAddTeamClanExist();
    				Validations.verifyThat().object(addClans).isFalse().perform();
    				ReportManager.log("Add clans button not exist");
     				
        			//teams 
    				dashBoardPageObj.navigateToTeams();
    				membershipTeamsList=teamsDashboardPageObj.isMembershipTeamTabExist();
    				Validations.verifyThat().object(membershipTeamsList).isTrue().perform();
    				ReportManager.log("Membership teams tab exist");
    			    				
    				teamsManagement=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMSMANAGEMENT);
     				Validations.verifyThat().object(teamsManagement).isFalse().perform();
     				ReportManager.log("Teams management not exist in the side menu");
     				
     				clansManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLANSMANAGEMENT);
     				Validations.verifyThat().object(clansManagement).isFalse().perform();
     				ReportManager.log("clans management not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    				
    			    ReportManager.log("============== End test edit clans Auth ==============");
    		    } 
    		    
    		    @Test
    		    public void testUnarchiveTeamAuth() {
    	  	    	ReportManager.log("============== Start test unarchive teams Auth  ==============");
    				
    	  	        //club owner account cred
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
    				     
    			   String pageNotAuth=properties.getProperty("notAuthorizedUrl"); 

    				    boolean teamsManagement;
    				    boolean clansManagement;
    				    boolean discoverTeams;
    				    boolean membershipTeamsList;
    				    boolean addTeams;
    				    boolean editTeams;
    				    boolean archiveTeam;
    				    boolean unarchiveTeam;
    				    boolean clans;
    				    
    				    
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.TEAMS_CLANS_ARCHIVE_TEAMS_CLANS);
    				 newAuthorities.add(Constants.UNARCHIVE_TEAM_CLAN);

    				 //Create Team
         		     String teamName= createTeamORClan(Constants.CLUBTEAM);
         		     //archive Team 
         		    Validations.assertThat().object(archiveTeamOrClan(Constants.CLUBTEAM, teamName)).isTrue().perform();
         		     

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.CLUBOWNERROLE,newAuthorities,true)).isTrue().perform();
         		     ReportManager.log("Club owner profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPW)).isTrue().perform();
        			ReportManager.log("Media Outlet account logged in successfully");
                   
                    
                    //Verify authorities
        			//teams 
    				dashBoardPageObj.navigateToTeams();
    				
    				teamsDashboardPageObj.selectMyTeamTab();
    				addTeams=myTeamsListPageObj.isAddTeamClanExist();
    				Validations.verifyThat().object(addTeams).isFalse().perform();
    				ReportManager.log("Add teams button not exist");
    				
    				
    				teamsDashboardPageObj.selectArchivedTeamTab();
    				unarchiveTeam=myTeamsListPageObj.isActionExist(teamName, Constants.UNARCHIVETEAM);
    				//myTeamsListPageObj.unarchiveTeam(teamName, Constants.CLUBTEAM);
    				Validations.verifyThat().object(unarchiveTeam).isTrue().perform();
    				ReportManager.log("unarchive teams button exist");
    				
    				
    				archiveTeam= myTeamsListPageObj.isActionExist(teamName, Constants.ARCHIVETEAM);
    				Validations.verifyThat().object(archiveTeam).isFalse().perform();
    				ReportManager.log("archive teams button not exist");
    				
    				editTeams=myTeamsListPageObj.isActionExist(teamName, Constants.EDITTEAM);
    				Validations.verifyThat().object(editTeams).isFalse().perform();
    				ReportManager.log("Edit teams button not exist");
    				
    				membershipTeamsList=teamsDashboardPageObj.isMembershipTeamTabExist();
    				Validations.verifyThat().object(membershipTeamsList).isFalse().perform();
    				ReportManager.log("Membership teams tab not exist");
    				
    				discoverTeams=teamsDashboardPageObj.isDiscoverTeamTabExist();
    				Validations.verifyThat().object(discoverTeams).isFalse().perform();
    				ReportManager.log("Discover teams tab not exist");
    				
    				//clans 
    				dashBoardPageObj.navigateToClans();
    				clans=dashBoardPageObj.isPageNotAuthorized(pageNotAuth);
    				Validations.verifyThat().object(clans).isFalse().perform();
     				ReportManager.log("Clans not exist in the side menu");
    				
    				teamsManagement=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMSMANAGEMENT);
     				Validations.verifyThat().object(teamsManagement).isFalse().perform();
     				ReportManager.log("Teams management not exist in the side menu");
     				
     				clansManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLANSMANAGEMENT);
     				Validations.verifyThat().object(clansManagement).isFalse().perform();
     				ReportManager.log("clans management not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    			    ReportManager.log("============== End test unarchive teams Auth ==============");
    		    } 
    		    
    		    @Test
    		    public void testUnarchiveClanAuth() {
    	  	    	ReportManager.log("============== Start test unarchive clans Auth  ==============");
    				
    	  	    	 //media outlet account cred
    				String mediaOutletAcc = properties.getProperty("mediaOutletAcc");
    				String mediaOutletPw = properties.getProperty("mediaOutletPw"); 
    				     
    				    boolean teamsManagement;
    				    boolean clansManagement;
    				    boolean membershipTeamsList;
    				    boolean membershipClansList;
    				    boolean discoverClans;
    				    boolean addClans;
    				    boolean editClans; 
    				    boolean archiveClans;
    				    boolean unarchiveClans;

    				    
    				 ArrayList<String> newAuthorities =new ArrayList<String>();
    				 newAuthorities.add(Constants.TEAMS_CLANS_ARCHIVE_TEAMS_CLANS);
    				 newAuthorities.add(Constants.UNARCHIVE_TEAM_CLAN);

    				 //Create Team
         		     String clanName= createTeamORClan(Constants.CLANTEAM);
         		    //archive Team 
          		    Validations.assertThat().object(archiveTeamOrClan(Constants.CLANTEAM, clanName)).isTrue().perform();

    				//prepare profile
    				 Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.MEDIAOUTLETROLE,newAuthorities,true)).isTrue().perform();
         		     ReportManager.log("Media outlet profile updated");

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(mediaOutletAcc, mediaOutletPw)).isTrue().perform();
        			ReportManager.log("Media Outlet account logged in successfully");
                   
                    
                    //Verify authorities
        
        			//clans 
    				dashBoardPageObj.navigateToClans();
    				
    				clansDashboardPageObj.selectMyClanTab();
    				addClans=myTeamsListPageObj.isAddTeamClanExist();
    				Validations.verifyThat().object(addClans).isFalse().perform();
    				ReportManager.log("Add clans button not exist");
    				
    				
    				clansDashboardPageObj.selectArchivedClanTab();
    				unarchiveClans=myTeamsListPageObj.isActionExist(clanName, Constants.UNARCHIVETEAM);
    				//myTeamsListPageObj.unarchiveTeam(clanName, Constants.CLANTEAM);
    				Validations.verifyThat().object(unarchiveClans).isTrue().perform();
    				ReportManager.log("un-Archive Clan button exist");
    				
    				
    				
    				archiveClans=myTeamsListPageObj.isActionExist(clanName, Constants.ARCHIVETEAM);
    				Validations.verifyThat().object(archiveClans).isFalse().perform();
    				ReportManager.log("Archive Clan button not exist");
    				
    				editClans=myTeamsListPageObj.isActionExist(clanName, Constants.EDITTEAM);
    				Validations.verifyThat().object(editClans).isFalse().perform();
    				ReportManager.log("Edit Clan button not exist");
    				
    				membershipClansList=clansDashboardPageObj.isMembershipClanTanExist();
    				Validations.verifyThat().object(membershipClansList).isTrue().perform();
    				ReportManager.log("Membership clans tab exist");
    				
    				discoverClans=clansDashboardPageObj.isDiscoverClanTabExist();
    				Validations.verifyThat().object(discoverClans).isFalse().perform();
    				ReportManager.log("Discover clans tab not exist");
     				
        			//teams 
    				dashBoardPageObj.navigateToTeams();
    				membershipTeamsList=teamsDashboardPageObj.isMembershipTeamTabExist();
    				Validations.verifyThat().object(membershipTeamsList).isTrue().perform();
    				ReportManager.log("Membership teams tab exist");
    			    				
    				teamsManagement=dashBoardPageObj.isSideMenuItemExist(Constants.TEAMSMANAGEMENT);
     				Validations.verifyThat().object(teamsManagement).isFalse().perform();
     				ReportManager.log("Teams management not exist in the side menu");
     				
     				clansManagement=dashBoardPageObj.isSideMenuItemExist(Constants.CLANSMANAGEMENT);
     				Validations.verifyThat().object(clansManagement).isFalse().perform();
     				ReportManager.log("clans management not exist in the side menu");
    				
    				dashBoardPageObj.logout();
    			    ReportManager.log("============== End test unarchive clans Auth ==============");
    		    }
    		    
    	    		    
    		    
    		   private String createTeamORClan(String teamType) {
    			   
    			   //club owner account cred
   				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
   				String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
    			
   			       //media outlet account cred
				String mediaOutletAcc = properties.getProperty("mediaOutletAcc");
				String mediaOutletPw = properties.getProperty("mediaOutletPw"); 
   				
				String token;
				String teamName;
				
				if(teamType.equals(Constants.CLUBTEAM)) {
    			  token = loginServiceObj.login(clubOwnerAcc,clubOwnerPW);
				}else {
    			  token = loginServiceObj.login(mediaOutletAcc,mediaOutletPw);
				}
				
				System.out.println("token equal: "+token);
   		    	TeamService teamService = new TeamService(apiObj,properties);
   		    	
   		    	if(teamType.equals(Constants.CLUBTEAM)){
   		         teamName=teamService.createTeamWithInvitation(token,true,null).getName();
   		    	}else {
		    	 teamName=teamService.createTeamWithInvitation(token,false,null).getName();
		    	}
   		    	
		    	Validations.assertThat().object(teamName).isNotNull().perform();  
		    	return teamName;

    		   }
   		    	
    		   private boolean archiveTeamOrClan(String teamType, String teamName) {
    			   //club owner account cred
      				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
      				String clubOwnerPW = properties.getProperty("clubOwnerPW"); 
       			
      			       //media outlet account cred
   				String mediaOutletAcc = properties.getProperty("mediaOutletAcc");
   				String mediaOutletPw = properties.getProperty("mediaOutletPw"); 
      				
   				String token;
   				
   				if(teamType.equals(Constants.CLUBTEAM)) {
       			  token = loginServiceObj.login(clubOwnerAcc,clubOwnerPW);
   				}else {
       			  token = loginServiceObj.login(mediaOutletAcc,mediaOutletPw);
   				}
   				
      		    	 TeamService teamService = new TeamService(apiObj,properties);
      		    	 String teamId= teamService.filterTeamByTeamEnName(teamName,Constants.MYTEAMLIST,token);
      		    	return teamService.archiveTeam(token,teamId);
    		   }
   		    	
    		   
    		   
    			@AfterMethod
    			public void AfterMethod(ITestResult result) {
    				ReportManager.log("AfterMethod");
    				//Return profiles to the original authorities 
    				Validations.assertThat().object(userProfileBusinessObj.prepareUserProfile(Constants.MEDIAOUTLETROLE,null,false)).isTrue().perform();
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
