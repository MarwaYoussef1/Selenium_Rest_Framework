package stc.esport.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

import stc.esport.api.pojo.Teams.TeamInvitation;
import stc.esport.api.services.CallForMemberService;
import stc.esport.api.services.InvitationService;
import stc.esport.api.services.LoginService;
import stc.esport.api.services.MemberPublicProfileService;
import stc.esport.api.services.TeamService;
import stc.esport.base.TestBase;
import stc.esport.pages.DashBoardPage;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;
import stc.esport.pages.fakeMailPage;
import stc.esport.pages.notification.NotificationPage;
import stc.esport.pages.settings.TeamAndClansManagement;
import stc.esport.pages.teamsclans.AddTeamPage;
import stc.esport.pages.teamsclans.ArchivedTeamClansPage;
import stc.esport.pages.teamsclans.ClansDashboardPage;
import stc.esport.pages.teamsclans.MyTeamsListPage;
import stc.esport.pages.teamsclans.TeamsDashboardPage;
import stc.esport.pages.users.profiles.ProfileManagementPage;
import stc.esport.pojo.Teams;
import stc.esport.testdata.TestData;
import stc.esport.utils.Constants;
import stc.esport.utils.Utils;

public class TeamsTest  extends TestBase{
	
	         // Variables
	  			LoginPage loginPageObj;
				HomePage homePageObj;
				DashBoardPage dashBoardPageObj;
                MyTeamsListPage myTeamsListPageObj;
                AddTeamPage addTeamPageObj;
                NotificationPage notificationPageObj;
                fakeMailPage fakeMailPageObj;
                ProfileManagementPage profileManagementPageObj;
        	    LoginService loginServiceObj;
        	    TeamAndClansManagement teamAndClansManagementObj;
        	    TeamsDashboardPage teamsDashboardPageObj;
                ClansDashboardPage clansDashboardPageObj;
                ArchivedTeamClansPage archivedTeamClansPageObj;
                
                
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
    				myTeamsListPageObj= new MyTeamsListPage(driver,properties);
    				addTeamPageObj= new AddTeamPage(driver,properties);
    				notificationPageObj= new NotificationPage(driver, properties);
    				fakeMailPageObj= new fakeMailPage(driver,properties);
    				profileManagementPageObj = new ProfileManagementPage(driver,properties);
    				loginServiceObj= new LoginService(properties);
    				teamAndClansManagementObj= new TeamAndClansManagement(driver,properties);
    				teamsDashboardPageObj= new TeamsDashboardPage(driver, properties);
    				clansDashboardPageObj= new ClansDashboardPage(driver, properties);
    				archivedTeamClansPageObj= new ArchivedTeamClansPage(driver, properties);
    			}

    			@BeforeTest
    			public void BeforeTest() {
    				ReportManager.log("BeforeTest");
    			}
    			
    			@DataProvider(name = "AddteamType")
    			public Object[][] testAddTeamData() throws InvalidFormatException, IOException {
    				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testAddTeam");
    				return data;
    			}
    			
    			@DataProvider(name = "AddTeamWithRegMembers")
    			public Object[][] testAddTeamWithRegMembersData() throws InvalidFormatException, IOException {
    				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testAddTeamWithRegMembers");
    				return data;
    			}
    			
    			@DataProvider(name = "AddTeamWithNonRegMembers")
    			public Object[][] testAddTeamWithNonRegMembers() throws InvalidFormatException, IOException {
    				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testAddTeamWithNonRegMembers");
    				return data;
    			}
    			
    			@DataProvider(name = "EditTeam")
    			public Object[][] testEditTeam() throws InvalidFormatException, IOException {
    				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testEditTeam");
    				return data;
    			}
    			
    			@DataProvider(name = "RemovingMembers")
    			public Object[][] testRemovingMembers() throws InvalidFormatException, IOException {
    				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testRemovingMembers");
    				return data;
    			}
    			
    			@DataProvider(name = "MemberStatus")
    			public Object[][] testMemberStatus() throws InvalidFormatException, IOException {
    				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testTeamMembeStatusInEdit");
    				return data;
    			}
    			
    			@DataProvider(name = "EditTeamWithRegMember")
    			public Object[][] testEditTeamWithRegMembers() throws InvalidFormatException, IOException {
    				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testEditTeamWithRegMembers");
    				return data;
    			}
    			
    			@DataProvider(name = "MemberLeaveTeam")
    			public Object[][] testMemberLeaveTeam() throws InvalidFormatException, IOException {
    				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testMemberLeaveTeam");
    				return data;
    			}
    			
    			@DataProvider(name = "archiveTeam")
    			public Object[][] testArchiveTeam() throws InvalidFormatException, IOException {
    				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testArchiveTeam");
    				return data;
    			}
    			@DataProvider(name = "unarchiveTeam")
    			public Object[][] testUnarchiveTeam() throws InvalidFormatException, IOException {
    				Object[][] data =TestData.fetchData(properties.getProperty("testDataPath"), "testUnarchiveTeam");
    				return data;
    			}
    			
    			
    			@Test(dataProvider = "AddteamType")
    			public void testAddTeam(String teamType){
    				ReportManager.log("==============  Start add team test ==============");
    				
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");
    				
    				String playerAcc=properties.getProperty("playerAcc");
    			    String playerPW=properties.getProperty("playerPW");
    			    
    			    
    				ArrayList<Boolean> status = new ArrayList<Boolean>();

    				Teams teamObj= new Teams(properties);
    				teamObj.setTeamType(teamType);

    				homePageObj.clickLoginLink();
    				
    				boolean login;
    				if (teamType.equals(Constants.CLUBTEAM)) {
        				 login= loginPageObj.login(clubOwnerAcc, clubOwnerPw);
    				}
    				else {
        				 login=loginPageObj.login(playerAcc, playerPW);
    				}
    				Validations.assertThat().object(login).isTrue().perform();
    				ReportManager.log("club onwer/player logged in successfully");

    				if (teamType.equals(Constants.CLUBTEAM)) {
    				dashBoardPageObj.navigateToTeams();
    				teamsDashboardPageObj.selectMyTeamTab();
    				}else {
    					dashBoardPageObj.navigateToClans();
    					clansDashboardPageObj.selectMyClanTab();
    				}
    				
    				Validations.assertThat().object(myTeamsListPageObj.addTeamWithoutMembers(teamObj)).isTrue().perform();
    				ReportManager.log("team created successfully");
    				
    				status = myTeamsListPageObj.checkTeamCardDetails(teamObj);
    				
    				Validations.assertThat().object(status.get(0)).isTrue().perform();
    				ReportManager.log("Game name displayed successfully");
    				
    				if(teamType.equals(Constants.CLUBTEAM)) {
    					Validations.assertThat().object(status.get(1)).isTrue().perform();
        				ReportManager.log("Team logo displayed successfully");
        				
        				Validations.assertThat().object(status.get(2)).isTrue().perform();
        				ReportManager.log("Team members displayed successfully");
        				
        				Validations.assertThat().object(status.get(3)).isTrue().perform();
        				ReportManager.log("club name displayed successfully");
    				}else {
    					Validations.assertThat().object(status.get(1)).isTrue().perform();
        				ReportManager.log("Team members displayed successfully");
    				}
    			
    				dashBoardPageObj.logout();
    				ReportManager.log("==============  End add team test ==============");
    				
    			}
    			
    			@Test(dataProvider = "EditTeam")
    			public void testEditTeam(String teamType) {
                      ReportManager.log("==============  Start edit team test ==============");
    				
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");
    				
    				String playerAcc=properties.getProperty("playerAcc");
    			    String playerPW=properties.getProperty("playerPW");
    			    
    			    HashMap<String,String> socialMedia = new HashMap<String,String>();;
    			    socialMedia.put(Constants.FACEBOOK, properties.getProperty("teamFB"));
    			    
    			    ArrayList<Boolean> status = new ArrayList<Boolean>();

    				Teams teamObj= new Teams(properties);
    				Teams teamUpdateObj= new Teams(properties);
    				teamObj.setTeamType(teamType);
    				teamUpdateObj.setTeamType(teamType);

    				teamObj.setTeamNameEn("Auto Team " +Utils.getRandomStringORNum(5,"String"));
    				teamUpdateObj.setTeamNameEn("Auto Edit Team " +Utils.getRandomStringORNum(5,"String"));
    				teamUpdateObj.setAbout(properties.getProperty("updateTeamAbout"));
    				teamUpdateObj.setGame(properties.getProperty("updateTeamGame"));
    				teamUpdateObj.setTeamLogo(properties.getProperty("updateTeamLogo"));
    				teamUpdateObj.setSocialMedia(socialMedia);
    				
    				
    				homePageObj.clickLoginLink();
    				
    				boolean login;
    				if (teamType.equals(Constants.CLUBTEAM)) {
    					login= loginPageObj.login(clubOwnerAcc, clubOwnerPw);
    				}
    				else {
    					login = loginPageObj.login(playerAcc, playerPW);
    				}
					Validations.assertThat().object(login).isTrue().perform();
    				ReportManager.log("club onwer/player logged in successfully");

    				
    				if (teamType.equals(Constants.CLUBTEAM)) {
        				dashBoardPageObj.navigateToTeams();
        				teamsDashboardPageObj.selectMyTeamTab();
        				}else {
        					dashBoardPageObj.navigateToClans();
        					clansDashboardPageObj.selectMyClanTab();
        				}
    				
    				Validations.assertThat().object(myTeamsListPageObj.addTeamWithoutMembers(teamObj)).isTrue().perform();
    				ReportManager.log("team created successfully");
    				
    				Validations.assertThat().object(myTeamsListPageObj.editTeamWithoutMembers(teamObj,teamUpdateObj)).isTrue().perform();
    				ReportManager.log("team created successfully");
    				
    				status = myTeamsListPageObj.checkTeamCardDetails(teamUpdateObj);
    				
    				Validations.assertThat().object(status.get(0)).isTrue().perform();
    				ReportManager.log("Game name displayed successfully");
    				
    				if(teamType.equals(Constants.CLUBTEAM)) {
    					Validations.assertThat().object(status.get(1)).isTrue().perform();
        				ReportManager.log("Team logo displayed successfully");
        				
        				Validations.assertThat().object(status.get(2)).isTrue().perform();
        				ReportManager.log("Team members displayed successfully");
        				
        				Validations.assertThat().object(status.get(3)).isTrue().perform();
        				ReportManager.log("club name displayed successfully");
    				}else {
    					Validations.assertThat().object(status.get(1)).isTrue().perform();
        				ReportManager.log("Team members displayed successfully");
    				}
    			
    				dashBoardPageObj.logout();
    				ReportManager.log("==============  End edit team test ==============");
    				
    			}
    			
    			@Test(dataProvider = "AddTeamWithRegMembers")
    			public void testAddTeamWithRegMembers(String teamType){
    				ReportManager.log("==============  Start add team with registered members test ==============");
    				
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");
    				
    				String playerAcc=properties.getProperty("playerAcc");
    			    String playerPW=properties.getProperty("playerPW");
    			    
    				String contentAcc=properties.getProperty("contentAcc");
    			    String contentPw=properties.getProperty("contentPW");
    			    
    			    String clubMemberImage=properties.getProperty("noMemberImage");
    			    String clanMemberImage=properties.getProperty("clubLogo");
    			    
    			    Teams teamObj= new Teams(properties);
    			    teamObj.setTeamType(teamType);
    			    
    				HashMap<String, ArrayList<String>> teamMember = new HashMap<String, ArrayList<String>>();
   				    ArrayList<String> userDetails= new ArrayList<String>();
   				    userDetails.add(Constants.CONTENTCREATORROLE);
   				    userDetails.add(properties.getProperty("contentAccName"));
   				     teamMember.put(properties.getProperty("contentAcc"),userDetails);
   				     teamObj.setMemberWithRole(teamMember);
   				
    				
    				String clubTeamInvitation=properties.getProperty("clubName")+" "+properties.getProperty("clubTeamInvitation")+" "+
    				teamObj.getTeamNameEn();
    				
    				String clanTeamInvitation=properties.getProperty("playerAccName")+" "+properties.getProperty("clanTeamInvitation")+" "+
    	    				teamObj.getTeamNameEn();

    				homePageObj.clickLoginLink();
    				
    				boolean login;
    				if (teamType.equals(Constants.CLUBTEAM)) {
    					login= loginPageObj.login(clubOwnerAcc, clubOwnerPw);
    				}
    				else {
    					login = loginPageObj.login(playerAcc, playerPW);
    				}
					Validations.assertThat().object(login).isTrue().perform();
    				ReportManager.log("club onwer/player logged in successfully");
    				
    				
    				if (teamType.equals(Constants.CLUBTEAM)) {
        				dashBoardPageObj.navigateToTeams();
        				teamsDashboardPageObj.selectMyTeamTab();
        				}else {
        					dashBoardPageObj.navigateToClans();
        					clansDashboardPageObj.selectMyClanTab();
        				}
    				boolean teamMemberStatus;
    				
    				if(teamType.equals(Constants.CLUBTEAM)) {
    					teamMemberStatus= myTeamsListPageObj.addTeamWithMembers(teamObj,clubMemberImage);
    				}else {
    					teamMemberStatus=myTeamsListPageObj.addTeamWithMembers(teamObj,clanMemberImage);
    				}
    				   Validations.assertThat().object(teamMemberStatus).isTrue().perform();
    				   ReportManager.log("clan/club team created with registered members");
    				   
    				dashBoardPageObj.logout();
    				homePageObj.clickLoginLink();
    				
    				Validations.assertThat().object(loginPageObj.login(contentAcc, contentPw)).isTrue().perform();
    				ReportManager.log("Login done successfully");
    				
    				boolean notification;
    				if (teamType.equals(Constants.CLUBTEAM)) {
    				notification=notificationPageObj.checkNotficationContent(clubTeamInvitation);
    				}else {
    					notification=notificationPageObj.checkNotficationContent(clanTeamInvitation);
    				}
    				Validations.assertThat().object(notification).isTrue().perform();
    				ReportManager.log("clan invitation notification sent to the member successfully");

    				dashBoardPageObj.logout();
    				
    				ReportManager.log("==============  End add team with registered members test ==============");
    			}
    			
    			
    			@Test(dataProvider = "AddTeamWithNonRegMembers")
    			public void testAddTeamWithNonRegMembers(String teamType){
    				ReportManager.log("==============  Start add team with non registered members test ==============");
    				
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");
    				
    				String playerAcc=properties.getProperty("playerAcc");
    			    String playerPW=properties.getProperty("playerPW");
    				
    			    String memberImage=properties.getProperty("noMemberImage");
    			    
    			    Teams teamObj= new Teams(properties);
    			    teamObj.setTeamType(teamType);    
    			    
    				String memberMail=Utils.generateRandomEmailAddress(properties.getProperty("fakedomain"));
    				HashMap<String, ArrayList<String>> teamMember = new HashMap<String, ArrayList<String>>();
    				 ArrayList<String> userDetails= new ArrayList<String>();
    				 userDetails.add(Constants.CONTENTCREATORROLE);
    	
    				 teamMember.put(memberMail,userDetails);
    				
    				teamObj.setMemberWithRole(teamMember);
    				
                     homePageObj.clickLoginLink();
    				
                     boolean login;
     				if (teamType.equals(Constants.CLUBTEAM)) {
     					login= loginPageObj.login(clubOwnerAcc, clubOwnerPw);
     				}
     				else {
     					login = loginPageObj.login(playerAcc, playerPW);
     				}
 					Validations.assertThat().object(login).isTrue().perform();
     				ReportManager.log("club onwer/player logged in successfully");
    				
     				if (teamType.equals(Constants.CLUBTEAM)) {
        				dashBoardPageObj.navigateToTeams();
        				teamsDashboardPageObj.selectMyTeamTab();
        				}else {
        					dashBoardPageObj.navigateToClans();
        					clansDashboardPageObj.selectMyClanTab();
        				}
     				
    				Validations.assertThat().object(myTeamsListPageObj.addTeamWithMembers(teamObj,memberImage)).isTrue().perform();
    				ReportManager.log("team created with non-registered members");
    				
    				dashBoardPageObj.logout();
    				
    				boolean memberInvitationSent = fakeMailPageObj.checkOnEmailWithSubject(true, memberMail.split("@")[0],
    						properties.getProperty("inviteEmailSubject"));
    				Validations.assertThat().object(memberInvitationSent).isTrue().perform();
    				
    			}
    			
    			@Test
    			public void testRemoveTeamMember(){
    				ReportManager.log("==============  Start test remove member ==============");
    				
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");
    				String memberImage= properties.getProperty("noMemberImage");
    				
    				Teams teamObj= new Teams(properties);
    				
                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPw)).isTrue().perform();
    				ReportManager.log("Login done successfully");
    				
    				dashBoardPageObj.navigateToTeams();
    				
    				myTeamsListPageObj.pressAddteamBtn();
    				
    				ArrayList<Boolean> status=addTeamPageObj.addMembers(teamObj.getMemberWithRole(), memberImage);
    				Validations.assertThat().object(status.stream().allMatch(val -> val == true)).isTrue().perform();
    				ReportManager.log("member added successfully");

    				Validations.assertThat().object(addTeamPageObj.removeMember(teamObj.getMemberWithRole(), Constants.ADDTEAM)).isTrue().perform();
    				ReportManager.log("member removed successfully");

    				dashBoardPageObj.logout();
    				ReportManager.log("==============  End test remove team member ==============");

    			}
    			
    			
    			@Test
    			public void  checkRoleNotMatched(){
    				ReportManager.log("==============  Start test error when adding reg mail not matched with role ==============");
    				
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");
    				String playerAcc= properties.getProperty("playerAcc");
    				String contentAccName=properties.getProperty("contentAccName");
    				String errorMsg= properties.getProperty("memberRoleNotMatched");

    				    Teams teamObj= new Teams(properties);        				

        				HashMap<String, ArrayList<String>> teamMember = new HashMap<String, ArrayList<String>>();
        				 ArrayList<String> userDetails= new ArrayList<String>();
        				 userDetails.add(Constants.CONTENTCREATORROLE);
        				 userDetails.add(contentAccName);
        				 teamMember.put(playerAcc, userDetails);
        				 
        				teamObj.setMemberWithRole(teamMember);

          				 homePageObj.clickLoginLink();
                         
                         Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPw)).isTrue().perform();
         				 ReportManager.log("Login done successfully");
         				
         				dashBoardPageObj.navigateToTeams();
         				myTeamsListPageObj.pressAddteamBtn();
         				
         				Validations.assertThat().object(addTeamPageObj.memberValidationCases(teamObj.getMemberWithRole(),errorMsg)).isTrue().perform();
        				
         				dashBoardPageObj.logout();
        				ReportManager.log("==============  End test error when adding reg mail not matched with role  ==============");
    			}
    			
    			
    			@Test
    			public void testAddMemberValidations() {
	               ReportManager.log("============== Start test check add member mail validations  ==============");
    				
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");
    				String playerAcc= properties.getProperty("playerAcc");
    				String playerPW=properties.getProperty("playerPW");
    				String contentAcc=properties.getProperty("contentAcc");

    				String approvedMemberMsg= properties.getProperty("approvedMember");
    				String pendingMemberMsg= properties.getProperty("pendingMember");
    				
					String clubOwnerToken = loginServiceObj.login(clubOwnerAcc,clubOwnerPw);
			    	String playerToken = loginServiceObj.login(playerAcc,playerPW);

			    	HashMap<String, ArrayList<String>> teamMember = new HashMap<String, ArrayList<String>>();
   				    ArrayList<String> contentDetails= new ArrayList<String>();
   				  

    				Teams teamObj= new Teams(properties);       
    				
    				TeamInvitation playerInvitation = new TeamInvitation();
    				playerInvitation.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
    				playerInvitation.setEmail(playerAcc);
    				
    				TeamInvitation contentInvitation = new TeamInvitation();
    				contentInvitation.setRole(Constants.CONTENTCREATORAPI.toUpperCase());
    				contentInvitation.setEmail(contentAcc);
    				
			    	ArrayList<TeamInvitation> invitations =new ArrayList<TeamInvitation>();
			    	invitations.add(playerInvitation);
			    	invitations.add(contentInvitation);

			    	TeamService teamService = new TeamService(apiObj,properties);
			    	String teamName=teamService.createTeamWithInvitation(clubOwnerToken,true,invitations).getName();
			    	Validations.assertThat().object(teamName).isNotNull().perform();  	
			    	teamObj.setTeamNameEn(teamName);
			    	
			    	InvitationService invitationService =new InvitationService(playerToken,apiObj);
			    	String invitationId= invitationService.getInvitationByTeam(teamName,Constants.RECEIVEDINVITATION);
			    	boolean acceptInvitation = invitationService.acceptRejectCancelInvitation(Constants.ACCEPTINVITATION, invitationId);
			    	Validations.assertThat().object(acceptInvitation).isTrue().perform();
			    	
			    	 homePageObj.clickLoginLink();
                     
                     Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPw)).isTrue().perform();
     				 ReportManager.log("Login done successfully");
     				
     				dashBoardPageObj.navigateToTeams();
     				teamsDashboardPageObj.selectMyTeamTab();
	 				myTeamsListPageObj.teamAction(teamObj.getTeamNameEn(),Constants.EDITTEAM);
			    	
	 				Validations.assertThat().object(addTeamPageObj.memberValidationCases(teamObj.getMemberWithRole(),approvedMemberMsg)).isTrue().perform();
    				 ReportManager.log("can't added user already accepted error displayed successfully");

    				contentDetails.add(Constants.CONTENTCREATORROLE);
  			    	teamMember.put(contentAcc, contentDetails);
    				teamObj.setMemberWithRole(teamMember);

  			    	Validations.assertThat().object(addTeamPageObj.memberValidationCases(teamObj.getMemberWithRole(),pendingMemberMsg)).isTrue().perform();
   				    ReportManager.log("can't added user already pending error displayed successfully");
  			    	
    				
     				dashBoardPageObj.logout();
    				ReportManager.log("==============  End test check add member mail validations  ==============");

    			}
    			
    			@Test
    			public void testCancelAddTeamMember() {
                           ReportManager.log("==============  Start test cancel add team member ==============");
    				
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");
    			    String clanMemberImage=properties.getProperty("clubTeamMemberImage");

				    Teams teamObj= new Teams(properties);


 				 homePageObj.clickLoginLink();
                 
                 Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPw)).isTrue().perform();
 				 ReportManager.log("Login done successfully");
 				
 				dashBoardPageObj.navigateToTeams();
 				
 				Validations.assertThat().object(myTeamsListPageObj.cancelAddTeamMember(teamObj, clanMemberImage)).isTrue().perform();
				
 				dashBoardPageObj.logout();
                ReportManager.log("==============  End test cancel add team member ==============");

    			}
    			
    			@Test
    			public void testRoleWhenToggleDisabled() {
    				ReportManager.log("==============  Start test role list incase add member toggle disabled ==============");
    				
    				String superAdmin = properties.getProperty("superAdmin");
    				String superAdminPw = properties.getProperty("superAdminPW");
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");    	
    						
    				String roleName= Constants.REFREEROLE;	 
    				
    				homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(superAdmin, superAdminPw)).isTrue().perform();
    				 ReportManager.log("SEF admin Login done successfully");
    				 
    				dashBoardPageObj.navigateToUsers();
    				
    				 Validations.assertThat().object(profileManagementPageObj.disableEnableTeamMemberToggle(roleName,"disable")).isTrue().perform();
    				 ReportManager.log("Referee toggle disabled successfully");
    				
    	 			dashBoardPageObj.logout();

                    homePageObj.clickLoginLink();
                    
                    Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPw)).isTrue().perform();
    				 ReportManager.log("Login with club owner account done successfully");
    				 
    	 			dashBoardPageObj.navigateToTeams();
    	 			myTeamsListPageObj.pressAddteamBtn();
    	 			
    	 			Validations.assertThat().object(addTeamPageObj.roleNotDisplayed(roleName)).isTrue().perform();
    				 ReportManager.log("Referee role not displayed in the add members roles");
    				 
    				 dashBoardPageObj.logout();
    				 
    				 homePageObj.clickLoginLink();
                     
                     Validations.assertThat().object(loginPageObj.login(superAdmin, superAdminPw)).isTrue().perform();
     				 ReportManager.log("SEF admin Login done successfully");
     				 
     				dashBoardPageObj.navigateToUsers();
     				
     				 Validations.assertThat().object(profileManagementPageObj.disableEnableTeamMemberToggle(roleName,"enable")).isTrue().perform();
     				 ReportManager.log("Referee toggle enabled again successfully");
     				
     	 			dashBoardPageObj.logout();

    			}
    			
    			
    			@Test(dataProvider = "MemberStatus")
    			public void testTeamMembeStatusInEdit(String actionType,String status) {
    				ReportManager.log("==============  Start test team members status in edit view ==============");
    				
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");    	
    				String playerAcc= properties.getProperty("playerAcc");
    				String playerPW=properties.getProperty("playerPW");
    				
					String clubOwnerToken = loginServiceObj.login(clubOwnerAcc,clubOwnerPw);
			    	String playerToken = loginServiceObj.login(playerAcc,playerPW);

				    Teams teamObj= new Teams(properties);
				    TeamInvitation playerInvitation = new TeamInvitation();
			    	TeamService teamService = new TeamService(apiObj,properties);
			    	
			    	playerInvitation.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
			    	playerInvitation.setEmail(playerAcc);
			    	
			    	
			    	ArrayList<TeamInvitation> invitations =new ArrayList<TeamInvitation>();
			    	invitations.add(playerInvitation);
			    	
      				teamObj.getMemberWithRole().get(playerAcc).add(status);
			    	
			    	String teamName=teamService.createTeamWithInvitation(clubOwnerToken,true,invitations).getName();
			    	Validations.assertThat().object(teamName).isNotNull().perform();  	
			    	 teamObj.setTeamNameEn(teamName);
			    	
			    	
			    	String invitationId;
			    	InvitationService invitationService;
			    	
			    	if (actionType.equals(Constants.CANCELINVITATION)) {
				    	 invitationService =new InvitationService(clubOwnerToken,apiObj);

				    	invitationId= invitationService.getInvitationByTeam(teamName,Constants.SENTINVITATION);
			    	}else {
				    	 invitationService =new InvitationService(playerToken,apiObj);
				    	 invitationId= invitationService.getInvitationByTeam(teamName,Constants.RECEIVEDINVITATION);
			    	}
			    	
			    	boolean InvitationStatus = invitationService.acceptRejectCancelInvitation(actionType, invitationId);
			    	Validations.assertThat().object(InvitationStatus).isTrue().perform();
			    	
    				homePageObj.clickLoginLink();
			    	
			    	 Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPw)).isTrue().perform();
	 				 ReportManager.log("Login done successfully");
	 				
	 				dashBoardPageObj.navigateToTeams();
	 				teamsDashboardPageObj.selectMyTeamTab();
	 				myTeamsListPageObj.teamAction(teamObj.getTeamNameEn(),Constants.EDITTEAM);
	 				
	 				 ArrayList<Boolean> memberCard =addTeamPageObj.memberCardDetials(teamObj.getMemberWithRole(), "null");
	 				 
	 				 if(actionType.equals(Constants.ACCEPTINVITATION)) {
	 				Validations.assertThat().object(memberCard.get(0)).isTrue().perform();
	 				 ReportManager.log("member name/mail displayed");
	 				 
	 				Validations.assertThat().object(memberCard.get(1)).isTrue().perform();
	 				 ReportManager.log("member role displayed");
	 				 
	 				Validations.assertThat().object(memberCard.get(3)).isTrue().perform();
	 				 ReportManager.log("member status displayed");
	 				 }else {
	 					Validations.assertThat().object(memberCard.get(0)).isFalse().perform();
		 				 ReportManager.log("member name/mail displayed");
	 				 }	
	 				 
	 				dashBoardPageObj.logout();
	                ReportManager.log("==============  End test team members status in edit view ==============");
    			}
    			
    			@Test
    			public void testClubOwnerNotMember() {
    				ReportManager.log("==============  Start test club owner not member in the team ==============");

    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");    	
    			
					String clubOwnerToken = loginServiceObj.login(clubOwnerAcc,clubOwnerPw);
				    Teams teamObj= new Teams(properties);
				    
			    	TeamService teamService = new TeamService(apiObj,properties);

			    	String teamName=teamService.createTeamWithInvitation(clubOwnerToken,true, null).getName();
			    	Validations.assertThat().object(teamName).isNotNull().perform();  
			    	
			    	teamObj.setTeamNameEn(teamName);
			    	teamObj.setTeamType(Constants.CLUBTEAM);
			    	
			    	homePageObj.clickLoginLink();
			    	
			    	 Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPw)).isTrue().perform();
	 				 ReportManager.log("Login done successfully");
	 				
	 				dashBoardPageObj.navigateToTeams();
	 				teamsDashboardPageObj.selectMyTeamTab();
	 				myTeamsListPageObj.teamAction(teamObj.getTeamNameEn(),Constants.EDITTEAM);

	 				 ArrayList<Boolean> memberCard =addTeamPageObj.memberCardDetials(teamObj.getMemberWithRole(), "null");

	 				Validations.assertThat().object(memberCard.get(0)).isFalse().perform();
	 				 ReportManager.log("Club owner account not displayed in member section");
	 				 
	 				dashBoardPageObj.logout();
	                ReportManager.log("==============  End test club owner not member in the team ==============");
    			}
    			
    			
    			@Test(dataProvider = "RemovingMembers")
    			public void testRemovingPendingAcceptedMembers(String actionType,String status) {
    				ReportManager.log("==============  Start test club owner not member in the team ==============");

    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");    	
    				String playerAcc= properties.getProperty("playerAcc");
    				String playerAccName=properties.getProperty("playerAccName");
    				String playerPW=properties.getProperty("playerPW");
    				
					String clubOwnerToken = loginServiceObj.login(clubOwnerAcc,clubOwnerPw);
			    	String playerToken = loginServiceObj.login(playerAcc,playerPW);

					Teams teamObj= new Teams(properties);
      				teamObj.getMemberWithRole().get(playerAcc).add(status);

	   				 teamObj.setTeamType(Constants.CLUBTEAM);
	   				 
				    TeamInvitation playerInvitation = new TeamInvitation();
					playerInvitation.setRole(Constants.CLUBPLAYERROLE.toUpperCase());
			    	playerInvitation.setEmail(playerAcc);
			    	ArrayList<TeamInvitation> invitations =new ArrayList<TeamInvitation>();
			    	invitations.add(playerInvitation);
			    	
			    	
			    	TeamService teamService = new TeamService(apiObj,properties);
			    	String teamName=teamService.createTeamWithInvitation(clubOwnerToken,true, invitations).getName();
			    	Validations.assertThat().object(teamName).isNotNull().perform();  
	   				teamObj.setTeamNameEn(teamName);
	    			String removeNotification= properties.getProperty("removeMemberNotification")+" "+teamObj.getTeamNameEn();


			    	if(actionType.equals(Constants.ACCEPTINVITATION)) {
			    		InvitationService invitationService =new InvitationService(playerToken,apiObj);
				    	String invitationId= invitationService.getInvitationByTeam(teamName,Constants.RECEIVEDINVITATION);
				    	boolean acceptInvitation = invitationService.acceptRejectCancelInvitation(Constants.ACCEPTINVITATION, invitationId);
				    	Validations.assertThat().object(acceptInvitation).isTrue().perform();
			    	}
			    	
			    	homePageObj.clickLoginLink();
			    	
			    	 Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPw)).isTrue().perform();
	 				 ReportManager.log("Login with club owner done successfully");
	 				
	 				dashBoardPageObj.navigateToTeams();
	 				teamsDashboardPageObj.selectMyTeamTab();
	 				myTeamsListPageObj.teamAction(teamObj.getTeamNameEn(),Constants.EDITTEAM);
	 				
	 				 Validations.assertThat().object(addTeamPageObj.removeMember(teamObj.getMemberWithRole(),Constants.EDITTEAM)).isTrue().perform();
	 				 ReportManager.log(playerAccName+" member removed successfully");
	 				 
	 				addTeamPageObj.submitAddEditTeam(Constants.EDITTEAM);
	 				
    				ArrayList<Boolean> listCardDetails = myTeamsListPageObj.checkTeamCardDetails(teamObj);
    				
    				Validations.assertThat().object(listCardDetails.get(2)).isTrue().perform();
    				ReportManager.log("Team members displayed zero");
    				dashBoardPageObj.logout();
    				
	 				 if(actionType.equals(Constants.ACCEPTINVITATION)) {
	 					homePageObj.clickLoginLink();
	 					
	 					 Validations.assertThat().object(loginPageObj.login(playerAcc, playerPW)).isTrue().perform();
		 				 ReportManager.log("Login with player done successfully");
		 				 
		 				 Validations.assertThat().object(notificationPageObj.checkNotficationContent(removeNotification)).isTrue().perform();
		 				 ReportManager.log("removed notification displayed successfully");
		 				 
		 				dashBoardPageObj.logout();
	 				 }
	 				 
	    				
	    				ReportManager.log("==============  End test club owner not member in the team ==============");
    			}
    			
    			
    			@Test(dataProvider="EditTeamWithRegMember")
    			public void testEditTeamByAddingRegMember(String teamType) {
    				ReportManager.log("============== Start test edit team by adding registered members ==============");

    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW"); 
    				
    				String playerAcc= properties.getProperty("playerAcc");
    				String playerPW=properties.getProperty("playerPW");
    				
    				String contentAcc=properties.getProperty("contentAcc");
    			    String contentPw=properties.getProperty("contentPW");
    			    String contentAccName= properties.getProperty("contentAccName");
    			    
    			    String teamMemberImage=properties.getProperty("noMemberImage");
    			    String clanMemberImage=properties.getProperty("clubLogo");
    			    
					String clubOwnerToken = loginServiceObj.login(clubOwnerAcc,clubOwnerPw);
					String playerToken= loginServiceObj.login(playerAcc, playerPW);

					Teams teamObj= new Teams(properties);
					HashMap<String, ArrayList<String>> teamMember = new HashMap<String, ArrayList<String>>();
   				     ArrayList<String> userDetails= new ArrayList<String>();
   				     userDetails.add(Constants.CONTENTCREATORROLE);
   				     userDetails.add(contentAccName);
   				     teamMember.put(contentAcc, userDetails);
					 teamObj.setMemberWithRole(teamMember);
					 teamObj.setTeamType(teamType);
			    	
			    	TeamService teamService = new TeamService(apiObj,properties);
			    	String teamName;
			    	if(teamType.equals(Constants.CLUBTEAM)) {
			    	 teamName=teamService.createTeamWithInvitation(clubOwnerToken,true, null).getName();
			    	}else {
				     teamName=teamService.createTeamWithInvitation(playerToken,false,null).getName();
			    	}
			    	Validations.assertThat().object(teamName).isNotNull().perform();  
	   				teamObj.setTeamNameEn(teamName);
	   				
	   				String clubTeamInvitation=properties.getProperty("clubName")+" "+properties.getProperty("clubTeamInvitation")+" "+
	   	    				teamObj.getTeamNameEn();
	   	    				
	   	    		String clanTeamInvitation=properties.getProperty("playerAccName")+" "+properties.getProperty("clanTeamInvitation")+" "+
	   	    	    				teamObj.getTeamNameEn();
	   	    		
	   				homePageObj.clickLoginLink();

                    boolean login;
    				if (teamType.equals(Constants.CLUBTEAM)) {
    					login= loginPageObj.login(clubOwnerAcc, clubOwnerPw);
    				}
    				else {
    					login = loginPageObj.login(playerAcc, playerPW);
    				}
					Validations.assertThat().object(login).isTrue().perform();
    				ReportManager.log("club onwer/player logged in successfully");
	 				
    				if (teamType.equals(Constants.CLUBTEAM)) {
        				dashBoardPageObj.navigateToTeams();
        				teamsDashboardPageObj.selectMyTeamTab();
        				}else {
        					dashBoardPageObj.navigateToClans();
        					clansDashboardPageObj.selectMyClanTab();
        				}

	 				boolean memberAdded;
	 				if (teamType.equals(Constants.CLUBTEAM)) {
	 			   memberAdded=	myTeamsListPageObj.editTeamWithMembers(teamObj,teamMemberImage);			
    			    }else {
 	 			   memberAdded=	myTeamsListPageObj.editTeamWithMembers(teamObj,clanMemberImage);			
    			      }
	 				Validations.assertThat().object(memberAdded).isTrue().perform();	
    				ReportManager.log("Registered member added successfully ");
    				
    				dashBoardPageObj.logout();
    				homePageObj.clickLoginLink();
    				
    				Validations.assertThat().object(loginPageObj.login(contentAcc, contentPw)).isTrue().perform();
    				ReportManager.log("Login done successfully");
    				
    				boolean notification;
    				if (teamType.equals(Constants.CLUBTEAM)) {
    				notification=notificationPageObj.checkNotficationContent(clubTeamInvitation);
    				}else {
    					notification=notificationPageObj.checkNotficationContent(clanTeamInvitation);
    				}
    				Validations.assertThat().object(notification).isTrue().perform();
    				ReportManager.log("clan invitation notification sent to the member successfully");

    				dashBoardPageObj.logout();
    				ReportManager.log("============== End test edit team by adding registered members ==============");
    			}

    			
    			@Test 
    			public void testTeamCreatorLeaveTeam() {
    				String playerAcc= properties.getProperty("playerAcc");
    				String playerPW=properties.getProperty("playerPW");
    				
    				String contentAcc=properties.getProperty("contentAcc");
    			    String contentPw=properties.getProperty("contentPW");
    			    String contentAccName= properties.getProperty("contentAccName");
    			    
					String playerToken= loginServiceObj.login(playerAcc, playerPW);
					String contentToken= loginServiceObj.login(contentAcc, contentPw);

					Teams teamObj= new Teams(properties);
					HashMap<String, ArrayList<String>> teamMember = new HashMap<String, ArrayList<String>>();
   				     ArrayList<String> userDetails= new ArrayList<String>();
   				     userDetails.add(Constants.CONTENTCREATORROLE);
   				     userDetails.add(contentAccName);
   				     teamMember.put(contentAcc, userDetails);
					 teamObj.setMemberWithRole(teamMember);
			    	
					 TeamInvitation contentInvitation = new TeamInvitation();
					 contentInvitation.setRole(Constants.CONTENTCREATORAPI.toUpperCase());
					 contentInvitation.setEmail(contentAcc);
				    	ArrayList<TeamInvitation> invitations =new ArrayList<TeamInvitation>();
				    	invitations.add(contentInvitation);
				    	
			    	TeamService teamService = new TeamService(apiObj,properties);
			    	String  teamName=teamService.createTeamWithInvitation(playerToken,false,invitations).getName();
			    	Validations.assertThat().object(teamName).isNotNull().perform();  
	   				teamObj.setTeamNameEn(teamName);
	   				String teamAdminNotification= properties.getProperty("adminLeavedminNotification")+" "+teamObj.getTeamNameEn();
	   						
	   				InvitationService invitationService =new InvitationService(contentToken,apiObj);
			    	String invitationId= invitationService.getInvitationByTeam(teamName,Constants.RECEIVEDINVITATION);
			    	boolean acceptInvitation = invitationService.acceptRejectCancelInvitation(Constants.ACCEPTINVITATION, invitationId);
			    	Validations.assertThat().object(acceptInvitation).isTrue().perform();
	   				
	   				homePageObj.clickLoginLink();

	   				Validations.assertThat().object(loginPageObj.login(playerAcc, playerPW)).isTrue().perform();
    				ReportManager.log("player logged in successfully");
    				
    				dashBoardPageObj.navigateToClans();
    				clansDashboardPageObj.selectMyClanTab();
    				
    				Validations.assertThat().object(myTeamsListPageObj.leaveTeam(teamObj.getTeamNameEn())).isTrue().perform();
    				ReportManager.log("team creator left the team successfully");

    				//Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamObj.getTeamNameEn())).isFalse().perform();
    				//ReportManager.log("team removed from my list");
    				
    				dashBoardPageObj.logout();

    				homePageObj.clickLoginLink();

	   				Validations.assertThat().object(loginPageObj.login(contentAcc, contentPw)).isTrue().perform();
    				ReportManager.log("player logged in successfully");
    				
    				Validations.assertThat().object(notificationPageObj.checkNotficationContent(teamAdminNotification)).isTrue().perform();
    				ReportManager.log("team admin notification displayed successfully");
    				
    				dashBoardPageObj.navigateToClans();;	 
    				clansDashboardPageObj.selectMyClanTab();

    				Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamObj.getTeamNameEn())).isTrue().perform();
    				ReportManager.log("team removed from my list");
    				
    				dashBoardPageObj.logout();
    			}
    			
    			
    			@Test(dataProvider="MemberLeaveTeam")
    			public void testMemberLeaveTeam(String teamType) {
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW"); 
    				
    				String playerAcc= properties.getProperty("playerAcc");
    				String playerPW=properties.getProperty("playerPW");
    				
    				String contentAcc=properties.getProperty("contentAcc");
    			    String contentPw=properties.getProperty("contentPW");
    			    String contentAccName= properties.getProperty("contentAccName");
    			    
					String playerToken= loginServiceObj.login(playerAcc, playerPW);
					String clubOwnerToken= loginServiceObj.login(clubOwnerAcc,clubOwnerPw );
					String contentToken= loginServiceObj.login(contentAcc, contentPw);

					Teams teamObj= new Teams(properties);
					HashMap<String, ArrayList<String>> teamMember = new HashMap<String, ArrayList<String>>();
   				     ArrayList<String> userDetails= new ArrayList<String>();
   				     userDetails.add(Constants.CONTENTCREATORROLE);
   				     userDetails.add(contentAccName);
   				     teamMember.put(contentAcc, userDetails);
					 teamObj.setMemberWithRole(teamMember);
			    	
					 TeamInvitation contentInvitation = new TeamInvitation();
					 contentInvitation.setRole(Constants.CONTENTCREATORAPI.toUpperCase());
					 contentInvitation.setEmail(contentAcc);
				    	ArrayList<TeamInvitation> invitations =new ArrayList<TeamInvitation>();
				    	invitations.add(contentInvitation);
				    	
			    	TeamService teamService = new TeamService(apiObj,properties);
			    	String teamName;
			    	if(teamType.equals(Constants.CLUBTEAM)) {
			    	 teamName=teamService.createTeamWithInvitation(clubOwnerToken,true, invitations).getName();
			    	}else {
				     teamName=teamService.createTeamWithInvitation(playerToken,false,invitations).getName();
			    	}
			    	Validations.assertThat().object(teamName).isNotNull().perform();  
	   				teamObj.setTeamNameEn(teamName);
	   				String memberLeaveNotification=contentAccName+" "+ properties.getProperty("memberLeaveNotification")+" "+teamObj.getTeamNameEn();
	   						
	   				InvitationService invitationService =new InvitationService(contentToken,apiObj);
			    	String invitationId= invitationService.getInvitationByTeam(teamName,Constants.RECEIVEDINVITATION);
			    	boolean acceptInvitation = invitationService.acceptRejectCancelInvitation(Constants.ACCEPTINVITATION, invitationId);
			    	Validations.assertThat().object(acceptInvitation).isTrue().perform();
	   				
	   				homePageObj.clickLoginLink();

	   				Validations.assertThat().object(loginPageObj.login(contentAcc, contentPw)).isTrue().perform();
    				ReportManager.log("player logged in successfully");
    				
    				if (teamType.equals(Constants.CLUBTEAM)) {
        				dashBoardPageObj.navigateToTeams();
        				teamsDashboardPageObj.selectMembershipTeamTab();
        				}else {
        					dashBoardPageObj.navigateToClans();
        					clansDashboardPageObj.selectMembershipClanTab();
        				}
    				    				
    				Validations.assertThat().object(myTeamsListPageObj.leaveTeam(teamObj.getTeamNameEn())).isTrue().perform();
    				ReportManager.log("team creator left the team successfully");

    				//Validations.assertThat().object(myTeamsListPageObj.searchWithName(driver,teamObj.getTeamNameEn())).isFalse().perform();
    				//ReportManager.log("team removed from my list");
    				
    				dashBoardPageObj.logout();

    				homePageObj.clickLoginLink();

    				 boolean login;
     				if (teamType.equals(Constants.CLUBTEAM)) {
     					login= loginPageObj.login(clubOwnerAcc, clubOwnerPw);
     				}
     				else {
     					login = loginPageObj.login(playerAcc, playerPW);
     				}
 					Validations.assertThat().object(login).isTrue().perform();
     				ReportManager.log("club onwer/player logged in successfully");
    				
    				Validations.assertThat().object(notificationPageObj.checkNotficationContent(memberLeaveNotification)).isTrue().perform();
    				ReportManager.log("team admin notification displayed successfully");
    				
    				dashBoardPageObj.logout();
    			}
    			
    			
    			@Test
    			public void testCancelLeaveTeam() {
    				String playerAcc= properties.getProperty("playerAcc");
    				String playerPW=properties.getProperty("playerPW");
    				
    				String contentAcc=properties.getProperty("contentAcc");
    			    String contentPw=properties.getProperty("contentPW");
    			    String contentAccName= properties.getProperty("contentAccName");
    			    
					String playerToken= loginServiceObj.login(playerAcc, playerPW);
					String contentToken= loginServiceObj.login(contentAcc, contentPw);

					Teams teamObj= new Teams(properties);
					HashMap<String, ArrayList<String>> teamMember = new HashMap<String, ArrayList<String>>();
   				     ArrayList<String> userDetails= new ArrayList<String>();
   				     userDetails.add(Constants.CONTENTCREATORROLE);
   				     userDetails.add(contentAccName);
   				     teamMember.put(contentAcc, userDetails);
					 teamObj.setMemberWithRole(teamMember);
			    	
					 TeamInvitation contentInvitation = new TeamInvitation();
					 contentInvitation.setRole(Constants.CONTENTCREATORAPI.toUpperCase());
					 contentInvitation.setEmail(contentAcc);
				    	ArrayList<TeamInvitation> invitations =new ArrayList<TeamInvitation>();
				    	invitations.add(contentInvitation);
				    	
			    	TeamService teamService = new TeamService(apiObj,properties);
			    	String  teamName=teamService.createTeamWithInvitation(playerToken,false,invitations).getName();
			    	Validations.assertThat().object(teamName).isNotNull().perform();  
	   				teamObj.setTeamNameEn(teamName);
	   						
	   				InvitationService invitationService =new InvitationService(contentToken,apiObj);
			    	String invitationId= invitationService.getInvitationByTeam(teamName,Constants.RECEIVEDINVITATION);
			    	boolean acceptInvitation = invitationService.acceptRejectCancelInvitation(Constants.ACCEPTINVITATION, invitationId);
			    	Validations.assertThat().object(acceptInvitation).isTrue().perform();
	   				
	   				homePageObj.clickLoginLink();

	   				Validations.assertThat().object(loginPageObj.login(playerAcc, playerPW)).isTrue().perform();
    				ReportManager.log("player logged in successfully");
    				
    				dashBoardPageObj.navigateToClans();
    				clansDashboardPageObj.selectMyClanTab();
    				
    				Validations.assertThat().object(myTeamsListPageObj.cancelLeaveTeam(teamObj.getTeamNameEn())).isTrue().perform();
    				ReportManager.log("leave team action canceled successfully");
    				
    				dashBoardPageObj.logout();
    			}
    			
    			@Test
    			public void testClubOwnerCannotLeaveTeam() {
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW"); 
    				
					String clubOwnerToken= loginServiceObj.login(clubOwnerAcc,clubOwnerPw );

					Teams teamObj= new Teams(properties);

			    	TeamService teamService = new TeamService(apiObj,properties);
			    	String teamName=teamService.createTeamWithInvitation(clubOwnerToken,true, null).getName();
			    	Validations.assertThat().object(teamName).isNotNull().perform();  
	   				teamObj.setTeamNameEn(teamName);
	   				
	   				homePageObj.clickLoginLink();

	   				Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPw)).isTrue().perform();
    				ReportManager.log("club owner logged in successfully");
    				
    				dashBoardPageObj.navigateToTeams();	
    				teamsDashboardPageObj.selectMyTeamTab();
    				
    				Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamObj.getTeamNameEn())).isTrue().perform();
    				ReportManager.log("team "+teamObj.getTeamNameEn()+"exist");

    				
    				Validations.assertThat().object(myTeamsListPageObj.teamAction(teamObj.getTeamNameEn(),Constants.LEAVETEAM)).isFalse().perform();
    				ReportManager.log("leave team action not displayed for club owner");

    				dashBoardPageObj.logout();
    			}
    			
    			@Test
    			public void testAdminCannotLeaveTeam() {
    				String playerAcc = properties.getProperty("playerAcc");
    				String playerPw = properties.getProperty("playerPW"); 
    				
					String playerToken= loginServiceObj.login(playerAcc,playerPw );

					Teams teamObj= new Teams(properties);

			    	TeamService teamService = new TeamService(apiObj,properties);
			    	String teamName=teamService.createTeamWithInvitation(playerToken,true, null).getName();
			    	Validations.assertThat().object(teamName).isNotNull().perform();  
	   				teamObj.setTeamNameEn(teamName);
	   				
	   				homePageObj.clickLoginLink();

	   				Validations.assertThat().object(loginPageObj.login(playerAcc, playerPw)).isTrue().perform();
    				ReportManager.log("club owner logged in successfully");
    				
    				dashBoardPageObj.navigateToClans();
    				clansDashboardPageObj.selectMyClanTab();
    				
    				Validations.assertThat().object(myTeamsListPageObj.teamAction(teamObj.getTeamNameEn(),Constants.LEAVETEAM)).isFalse().perform();
    				ReportManager.log("leave team action not displayed for club owner");
    				
    				dashBoardPageObj.logout();
    			}
    			
    			
    			@Test(dataProvider="archiveTeam")
    			public void testArchiveTeamClan (String teamType){
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW"); 
    				
    				String playerAcc= properties.getProperty("playerAcc");
    				String playerPW=properties.getProperty("playerPW");
    				
    				String contentAcc=properties.getProperty("contentAcc");
    			    String contentPw=properties.getProperty("contentPW");
    			    String contentAccName= properties.getProperty("contentAccName");
    				
    				String playerToken= loginServiceObj.login(playerAcc, playerPW);
					String clubOwnerToken= loginServiceObj.login(clubOwnerAcc,clubOwnerPw );
					String contentToken= loginServiceObj.login(contentAcc, contentPw);

					
					Teams teamObj= new Teams(properties);
                    teamObj.setTeamType(teamType);
				 	
					HashMap<String, ArrayList<String>> teamMember = new HashMap<String, ArrayList<String>>();
   				     ArrayList<String> userDetails= new ArrayList<String>();
   				     userDetails.add(Constants.CONTENTCREATORROLE);
   				     userDetails.add(contentAccName);
   				     teamMember.put(contentAcc, userDetails);
					 teamObj.setMemberWithRole(teamMember);
			    	
					 TeamInvitation contentInvitation = new TeamInvitation();
					 contentInvitation.setRole(Constants.CONTENTCREATORAPI.toUpperCase());
					 contentInvitation.setEmail(contentAcc);
				    ArrayList<TeamInvitation> invitations =new ArrayList<TeamInvitation>();
				    	invitations.add(contentInvitation);
				    	
			    	TeamService teamService = new TeamService(apiObj,properties);
			    	String teamName;
			    	if(teamObj.getTeamType().equals(Constants.CLUBTEAM)) {
			    	 teamName=teamService.createTeamWithInvitation(clubOwnerToken,true, invitations).getName();
			    	}else {
				     teamName=teamService.createTeamWithInvitation(playerToken,false,invitations).getName();
			    	}
			    	Validations.assertThat().object(teamName).isNotNull().perform();  
	   				teamObj.setTeamNameEn(teamName);
	   				
	   				InvitationService invitationService =new InvitationService(contentToken,apiObj);
			    	String invitationId= invitationService.getInvitationByTeam(teamName,Constants.RECEIVEDINVITATION);
			    	boolean acceptInvitation = invitationService.acceptRejectCancelInvitation(Constants.ACCEPTINVITATION, invitationId);
			    	Validations.assertThat().object(acceptInvitation).isTrue().perform();
			    	
			    	String archiveNotification= properties.getProperty("removeMemberNotification")+" "+ teamObj.getTeamNameEn();
			    			
	   				homePageObj.clickLoginLink();

	   				
    				boolean loginStatus;
    				if (teamObj.getTeamType().equals(Constants.CLUBTEAM)) {
    					loginStatus= loginPageObj.login(clubOwnerAcc, clubOwnerPw);
    					Validations.assertThat().object(loginStatus).isTrue().perform();
        				ReportManager.log("club owner logged in successfully");
        				
        				dashBoardPageObj.navigateToTeams();
        				teamsDashboardPageObj.selectMyTeamTab();

        				}else {
        				loginStatus= loginPageObj.login(playerAcc, playerPW);
        				Validations.assertThat().object(loginStatus).isTrue().perform();
        				ReportManager.log("player logged in successfully");
        				
        				dashBoardPageObj.navigateToClans();
        				clansDashboardPageObj.selectMyClanTab();
        				}
    				
    				Validations.assertThat().object(myTeamsListPageObj.archiveTeam(teamObj.getTeamNameEn(), teamObj.getTeamType(), false)).isTrue().perform();
    				ReportManager.log(teamObj.getTeamType()+" archived successfully");

    				Validations.assertThat().object(myTeamsListPageObj.searchWithInvalidValue(teamObj.getTeamNameEn())).isTrue().perform();
    				ReportManager.log(teamObj.getTeamType()+" removed from list");
    				
    				clansDashboardPageObj.selectArchivedClanTab();
    				
    				Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamObj.getTeamNameEn())).isTrue().perform();
    				ReportManager.log(teamObj.getTeamType()+" moved to archived list");
    				
    				dashBoardPageObj.logout();
    				ReportManager.log("Player/Club owner logged out successfully");
    				
	   				homePageObj.clickLoginLink();

    				Validations.assertThat().object(loginPageObj.login(contentAcc, contentPw)).isTrue().perform();
    				ReportManager.log("Content creator logged in successfully");
    				
    				Validations.assertThat().object(notificationPageObj.checkNotficationContent(archiveNotification)).isTrue().perform();
    				ReportManager.log("Content creator removed from the archived team");
    				
    				dashBoardPageObj.logout();
    				ReportManager.log("Content creator logged out successfully");
    			}
    			    		
    			
    			@Test
    			public void testArchiveTeamWithCallOfMember() {
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW"); 
    				
					String clubOwnerToken= loginServiceObj.login(clubOwnerAcc,clubOwnerPw );

					Teams teamObj= new Teams(properties);
                    teamObj.setTeamType(Constants.CLUBTEAM);
                    
                    TeamService teamService = new TeamService(apiObj,properties);
			         String teamName= teamService.createTeamWithInvitation(clubOwnerToken,true, null).getName();
			    		Validations.assertThat().object(teamName).isNotNull().perform();  
			    		ReportManager.log("Team created successfully");
	   				  teamObj.setTeamNameEn(teamName);
	   				  
	   				  String teamId= teamService.filterTeamByTeamEnName(teamObj.getTeamNameEn(), Constants.MYTEAMLIST, clubOwnerToken);
			    		Validations.assertThat().object(teamId).isNotNull().perform();  

	   				  CallForMemberService callForMemberService= new CallForMemberService(apiObj,properties);
	   				  Validations.assertThat().object(callForMemberService.callForMemberPost(clubOwnerToken, teamId)).isTrue().perform();
	   				  ReportManager.log("call for member added on team "+ teamObj.getTeamNameEn());
	   				  
	   				  homePageObj.clickLoginLink();

    					Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPw)).isTrue().perform();
        				ReportManager.log("club owner logged in successfully");
                    
        				dashBoardPageObj.navigateToTeams();
        				teamsDashboardPageObj.selectMyTeamTab();
        				
        				Validations.assertThat().object(myTeamsListPageObj.archiveTeam(teamObj.getTeamNameEn(), teamObj.getTeamType(), true)).isTrue().perform();
        				ReportManager.log(teamObj.getTeamType()+" can't archived");
        				
        				Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamObj.getTeamNameEn())).isTrue().perform();
        				ReportManager.log("Team still displayed in the my teams list");
        				
        				dashBoardPageObj.logout();
        				ReportManager.log("Club owner logged out successfully");
    			}
    			
    			@Test(dataProvider="unarchiveTeam")
    			public void testUnarchiveTeamClan(String teamType) { 
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW"); 
    				
    				String playerAcc= properties.getProperty("playerAcc");
    				String playerPW=properties.getProperty("playerPW");
    				
    				String playerToken= loginServiceObj.login(playerAcc, playerPW);
					String clubOwnerToken= loginServiceObj.login(clubOwnerAcc,clubOwnerPw );

					
					Teams teamObj= new Teams(properties);
                    teamObj.setTeamType(teamType);
                    
                    TeamService teamService = new TeamService(apiObj,properties);
			    	String teamName;
			    	if(teamObj.getTeamType().equals(Constants.CLUBTEAM)) {
			    	 teamName=teamService.createTeamWithInvitation(clubOwnerToken,true, null).getName();
			    	}else {
				     teamName=teamService.createTeamWithInvitation(playerToken,false,null).getName();
			    	}
			    	Validations.assertThat().object(teamName).isNotNull().perform();  
			    	ReportManager.log("Team/Clan created successfully");
	   				teamObj.setTeamNameEn(teamName);
	   				
	   				String teamId;
	   				if(teamObj.getTeamType().equals(Constants.CLUBTEAM)) {
	   			   teamId= teamService.filterTeamByTeamEnName(teamObj.getTeamNameEn(), Constants.MYTEAMLIST, clubOwnerToken);
	   				}else {
	 	   			   teamId= teamService.filterTeamByTeamEnName(teamObj.getTeamNameEn(), Constants.MYTEAMLIST, playerToken);

	   				}
		    		Validations.assertThat().object(teamId).isNotNull().perform(); 
		    		
		    		boolean teamArchived;
		    		if(teamObj.getTeamType().equals(Constants.CLUBTEAM)) {
		    		teamArchived= teamService.archiveTeam(clubOwnerToken, teamId);
		    		}else {
			    	teamArchived= teamService.archiveTeam(playerToken, teamId);
		    		}
		    		Validations.assertThat().object(teamArchived).isTrue().perform();
		    		ReportManager.log("Team/Clan archived successfully");
		    		
	   				homePageObj.clickLoginLink();

	   				boolean loginStatus;
    				if (teamObj.getTeamType().equals(Constants.CLUBTEAM)) {
    					loginStatus= loginPageObj.login(clubOwnerAcc, clubOwnerPw);
    					Validations.assertThat().object(loginStatus).isTrue().perform();
        				ReportManager.log("club owner logged in successfully");
        				
        				dashBoardPageObj.navigateToTeams();

        				}else {
        				loginStatus= loginPageObj.login(playerAcc, playerPW);
        				Validations.assertThat().object(loginStatus).isTrue().perform();
        				ReportManager.log("player logged in successfully");
        				
        				dashBoardPageObj.navigateToClans();
        				}
    				    				
    				Validations.assertThat().object(archivedTeamClansPageObj.unarchiveTeam(teamObj.getTeamNameEn(), teamObj.getTeamType())).isTrue().perform();
    				ReportManager.log(teamObj.getTeamNameEn()+ " unarchived successfully");
    				
    				Validations.assertThat().object(myTeamsListPageObj.searchWithInvalidValue(teamObj.getTeamNameEn())).isTrue().perform();
    				ReportManager.log("Team/clan removed from archive list");
    				
    				if (teamObj.getTeamType().equals(Constants.CLUBTEAM)) {
    					teamsDashboardPageObj.selectMyTeamTab();
    				}else {
    					clansDashboardPageObj.selectMyClanTab();
    				}
    				
    				Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamObj.getTeamNameEn())).isTrue().perform();
    				ReportManager.log("Team/clan moved to my team list");
    				
    				dashBoardPageObj.logout();
    				ReportManager.log("Club owner/Player logged out successfully");
    			}
    		
    			
    			@Test
    			public void testUnarchiveButReachedTheLimit() {
    				String adminAcc= properties.getProperty("superAdmin");
    				String adminPW= properties.getProperty("superAdminPW");
    				
    				String contentAcc= properties.getProperty("contentAcc");
    				String contentPW=properties.getProperty("contentPW");
    				String contentAccID= properties.getProperty("contentAccID");
    				
                    String contentToken= loginServiceObj.login(contentAcc,contentPW );

					
					Teams teamObj= new Teams(properties);
                    teamObj.setTeamType(Constants.CLANTEAM);
                    
                 TeamService teamService = new TeamService(apiObj,properties);
                  String  teamName=teamService.createTeamWithInvitation(contentToken,false,null).getName();
		    	  Validations.assertThat().object(teamName).isNotNull().perform();  
		    	   ReportManager.log("Clan created successfully");
   				   teamObj.setTeamNameEn(teamName);
                    
   				 String teamId= teamService.filterTeamByTeamEnName(teamObj.getTeamNameEn(), Constants.MYTEAMLIST, contentToken);
   				ReportManager.log("team id= "+ teamId); 
		    	 Validations.assertThat().object(teamId).isNotNull().perform(); 
		    	
		    	boolean	teamArchived= teamService.archiveTeam(contentToken, teamId);
		    		Validations.assertThat().object(teamArchived).isTrue().perform();
		    		ReportManager.log("Clan archived successfully"); 
		    	
		    	MemberPublicProfileService profileService= new MemberPublicProfileService(apiObj,properties);
		    	String numberOfTeams= profileService.getMemberProfileDetails(contentAccID);
		    	
	   			homePageObj.clickLoginLink();

				Validations.assertThat().object(loginPageObj.login(adminAcc, adminPW)).isTrue().perform();
				ReportManager.log("Super admin logged in successfully");

				dashBoardPageObj.naviagteToSettings();
				
				Validations.assertThat().object(teamAndClansManagementObj.changeActiveClanNum(Constants.CONTENTCREATORROLE, "1" )).isTrue().perform();
				ReportManager.log("Number of clans changed successfully");

				dashBoardPageObj.logout();
				ReportManager.log("Super admin logged out successfully");

				homePageObj.clickLoginLink();

				Validations.assertThat().object(loginPageObj.login(contentAcc, contentPW)).isTrue().perform();
				ReportManager.log("Content Creator account logged in successfully");
			
				dashBoardPageObj.navigateToClans();
				
				clansDashboardPageObj.selectArchivedClanTab();
				
				Validations.assertThat().object(archivedTeamClansPageObj.unachiveButReachedtheLimit(teamObj.getTeamNameEn(),numberOfTeams)).isTrue().perform();
				ReportManager.log(teamObj.getTeamNameEn()+ "not unarchived");
				
				Validations.assertThat().object(myTeamsListPageObj.searchWithTeamName(teamObj.getTeamNameEn())).isTrue().perform();
				ReportManager.log("Clan removed from archive list");
				
				dashBoardPageObj.logout();
				ReportManager.log("Content Creator account logged out successfully");
				
				homePageObj.clickLoginLink();

				Validations.assertThat().object(loginPageObj.login(adminAcc, adminPW)).isTrue().perform();
				ReportManager.log("Super admin logged in successfully");

				dashBoardPageObj.naviagteToSettings();
				
				Validations.assertThat().object(teamAndClansManagementObj.changeActiveClanNum(Constants.CONTENTCREATORROLE, "200" )).isTrue().perform();
				ReportManager.log("Number of clans changed successfully");

				dashBoardPageObj.logout();
				ReportManager.log("Super admin logged out successfully");

    			}
    			
    			@Test
    			public void testAddTeamWithNonRegMembersNewFakeMail(){
    				ReportManager.log("==============  Start add team with non registered members test ==============");
    				
    				String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
    				String clubOwnerPw = properties.getProperty("clubOwnerPW");
    				
    				String playerAcc=properties.getProperty("playerAcc");
    			    String playerPW=properties.getProperty("playerPW");
    				
    			    String memberImage=properties.getProperty("noMemberImage");
    			    
    			    Teams teamObj= new Teams(properties);
    			    teamObj.setTeamType("club team");    
    			    
    				String memberMail=Utils.generateRandomEmailAddress("armyspy");
    				HashMap<String, ArrayList<String>> teamMember = new HashMap<String, ArrayList<String>>();
    				 ArrayList<String> userDetails= new ArrayList<String>();
    				 userDetails.add(Constants.CONTENTCREATORROLE);
    	
    				 teamMember.put(memberMail,userDetails);
    				
    				teamObj.setMemberWithRole(teamMember);
    				
                     homePageObj.clickLoginLink();
    				
                     boolean login;
     				if (teamObj.getTeamType().equals(Constants.CLUBTEAM)) {
     					login= loginPageObj.login(clubOwnerAcc, clubOwnerPw);
     				}
     				else {
     					login = loginPageObj.login(playerAcc, playerPW);
     				}
 					Validations.assertThat().object(login).isTrue().perform();
     				ReportManager.log("club onwer/player logged in successfully");
    				
     				if (teamObj.getTeamType().equals(Constants.CLUBTEAM)) {
        				dashBoardPageObj.navigateToTeams();
        				teamsDashboardPageObj.selectMyTeamTab();
        				}else {
        					dashBoardPageObj.navigateToClans();
        					clansDashboardPageObj.selectMyClanTab();
        				}
     				
    				Validations.assertThat().object(myTeamsListPageObj.addTeamWithMembers(teamObj,memberImage)).isTrue().perform();
    				ReportManager.log("team created with non-registered members");
    				
    				dashBoardPageObj.logout();
    				
    				boolean memberInvitationSent = fakeMailPageObj.checkEmailWithSubjectNew(memberMail.split("@")[0],
    						properties.getProperty("inviteEmailSubject"));
    				Validations.assertThat().object(memberInvitationSent).isTrue().perform();
    				
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
