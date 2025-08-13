package stc.esport.testcases;

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

import stc.esport.api.business.IncidentManagement;
import stc.esport.base.TestBase;
import stc.esport.pages.DashBoardPage;
import stc.esport.pages.HomePage;
import stc.esport.pages.LoginPage;
import stc.esport.pages.incident.IncidentDetailsPage;
import stc.esport.pages.incident.IncidentListPage;
import stc.esport.pages.incident.SubmitIncidentPage;
import stc.esport.pages.notification.NotificationPage;
import stc.esport.pojo.Incidents.Defendants;
import stc.esport.pojo.Incidents.Incident;
import stc.esport.utils.Constants;

public class IncidentTest extends TestBase{

	 //Variables
		LoginPage loginPageObj;
		HomePage homePageObj;
		DashBoardPage dashBoardPageObj;
		IncidentListPage incidnetListPageObj;
		SubmitIncidentPage submitIncidentPage;
		IncidentDetailsPage incidentDetailsPageObj;
		NotificationPage notificationPageObj;
		
		
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
			incidnetListPageObj= new IncidentListPage(driver, properties);
			submitIncidentPage= new SubmitIncidentPage(driver, properties);
			incidentDetailsPageObj=new IncidentDetailsPage(driver, properties);
			notificationPageObj = new NotificationPage(driver, properties);
		}
		
		@BeforeTest
		public void BeforeTest() {
			ReportManager.log("BeforeTest");
		}
		
		@Test
		public void testAddClaimWithPlayerAccount() {
			ReportManager.log("==============  Start add claim test ==============");
			
			String playerAcc=properties.getProperty("playerAcc");
		    String playerPW=properties.getProperty("playerPW");
		    
		    Incident incidentObj= new Incident(properties);
		    Defendants defendantOneObj= new Defendants(properties);
		    Defendants defendantTwoObj= new Defendants(properties);
		    
			ArrayList<Defendants> defendantLists=new ArrayList<Defendants>(); 
		    defendantLists.add(defendantTwoObj);
		    defendantLists.add(defendantOneObj);

		    incidentObj.setPlantiffIdNumber("555666555");//Marwa: This should be in Property file 
		    incidentObj.setDefendant(defendantLists);
		    incidentObj.setIncidentType(Constants.CLAIMINCIDENT);

			homePageObj.clickLoginLink();

		    boolean login = loginPageObj.login(playerAcc, playerPW);
		    Validations.assertThat().object(login).isTrue().perform();
			ReportManager.log("player logged in successfully");
			
			dashBoardPageObj.navigateToIncidents();
			incidnetListPageObj.clickOnAddIncidentButton();
			 
			Validations.assertThat().object(submitIncidentPage.submitIncident(incidentObj, Constants.PLAYERINCIDENT)).isTrue().perform();
			ReportManager.log("Claim submitted successfully");
			
			Validations.assertThat().object(incidnetListPageObj.checkIncidentAdded(incidentObj.getPlantiffName())).isTrue().perform();
			ReportManager.log("Claim added to the list successfully");


			dashBoardPageObj.logout();
			ReportManager.log("Player logged out successfully");

			ReportManager.log("==============  End add claim test ==============");
		}
		
		
		@Test
		public void testAddComplainWithClubOwnerAccount() {
			ReportManager.log("==============  Start add complain test ==============");
			
			String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
			String clubOwnerPw = properties.getProperty("clubOwnerPW");
		    
		    Incident incidentObj= new Incident(properties);
		    Defendants defendantOneObj= new Defendants(properties);
		    Defendants defendantTwoObj= new Defendants(properties);
		    
			ArrayList<Defendants> defendantLists=new ArrayList<Defendants>(); 
		    defendantOneObj.setDefendantIdNumber(properties.getProperty("playerNationalID"));
		    defendantOneObj.setDefendantMai(properties.getProperty("playerAcc"));
		    defendantOneObj.setDefendantName(properties.getProperty("playerAccName"));
		    defendantLists.add(defendantTwoObj);
		    defendantLists.add(defendantOneObj);

		    incidentObj.setDefendant(defendantLists);
		    incidentObj.setIncidentType(Constants.COMPLAININCIDENT);
		    incidentObj.setOnBehalf(Constants.CLUB_ONBEHALF);
		    
			homePageObj.clickLoginLink();

		    boolean login = loginPageObj.login(clubOwnerAcc, clubOwnerPw);
		    Validations.assertThat().object(login).isTrue().perform();
			ReportManager.log("club owner logged in successfully");
			
			dashBoardPageObj.navigateToIncidents();
			incidnetListPageObj.clickOnAddIncidentButton();
			 
			Validations.assertThat().object(submitIncidentPage.submitIncident(incidentObj,Constants.CLUBOWNERINCIDENT)).isTrue().perform();
			ReportManager.log("Complain submitted successfully");
			
			dashBoardPageObj.logout();
			ReportManager.log("Club owner logged out successfully");

			ReportManager.log("==============  End add complain test ==============");
		}
		
		@Test
		public void testAddComplainWithSEFAccount() {
			ReportManager.log("==============  Start add complain test ==============");
			
			String adminAcc= properties.getProperty("superAdmin");
			String adminPW= properties.getProperty("superAdminPW");
		    
		    Incident incidentObj= new Incident(properties);
		    Defendants defendantOneObj= new Defendants(properties);
		    Defendants defendantTwoObj= new Defendants(properties);
		    
			ArrayList<Defendants> defendantLists=new ArrayList<Defendants>(); 
			defendantOneObj.setDefendantIdNumber(properties.getProperty("playerNationalID"));
			defendantOneObj.setDefendantMai(properties.getProperty("playerAcc"));
			defendantOneObj.setDefendantName(properties.getProperty("playerAccName"));
		    defendantLists.add(defendantTwoObj);
		    defendantLists.add(defendantOneObj);

		    incidentObj.setPlantiffIdNumber("555666555");
		    incidentObj.setDefendant(defendantLists);
		    incidentObj.setIncidentType(Constants.COMPLAININCIDENT);
		    incidentObj.setOnBehalf(Constants.MYSELF_ONBEHALF);
		    
			homePageObj.clickLoginLink();

		    boolean login = loginPageObj.login(adminAcc, adminPW);
		    Validations.assertThat().object(login).isTrue().perform();
			ReportManager.log("SEF admin logged in successfully");
			
			dashBoardPageObj.navigateToIncidents();
			incidnetListPageObj.clickOnAddIncidentButton();
			 
			Validations.assertThat().object(submitIncidentPage.submitIncident(incidentObj,Constants.ADMININCIDENT)).isTrue().perform();
			ReportManager.log("Complain submitted successfully");
			
			dashBoardPageObj.logout();
			ReportManager.log("SEF Admin logged out successfully");

			ReportManager.log("==============  End add complain test ==============");
		}
		
		@Test
		public void testSEFAdminrOnBehalfOptions() {
            ReportManager.log("==============  Start SEF admin onbehalf options test ==============");
			
			String adminAcc= properties.getProperty("superAdmin");
			String adminPW= properties.getProperty("superAdminPW");
			
			homePageObj.clickLoginLink();

		    boolean login = loginPageObj.login(adminAcc, adminPW);
		    Validations.assertThat().object(login).isTrue().perform();
			ReportManager.log("SEF admin logged in successfully");
			
			dashBoardPageObj.navigateToIncidents();
			incidnetListPageObj.clickOnAddIncidentButton();
			
			Validations.assertThat().object(submitIncidentPage.checkSEFOnbehalf()).isTrue().perform();
			ReportManager.log("Onbehalf validations done successfully");
			
			dashBoardPageObj.logout();
			ReportManager.log("SEF admin logged out successfully");

			ReportManager.log("==============  End SEF admin onbehalf options test ==============");
			
		}
		
		@Test
		public void testClubOwnerOnBehalfOptions() {
            ReportManager.log("==============  Start club owner onbehalf options test ==============");
			
            String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
			String clubOwnerPw = properties.getProperty("clubOwnerPW");
			
			homePageObj.clickLoginLink();

		    boolean login = loginPageObj.login(clubOwnerAcc, clubOwnerPw);
		    Validations.assertThat().object(login).isTrue().perform();
			ReportManager.log("Club owner logged in successfully");
			
			dashBoardPageObj.navigateToIncidents();
			incidnetListPageObj.clickOnAddIncidentButton();
			
			Validations.assertThat().object(submitIncidentPage.checkclubOwnerOnbehalf()).isTrue().perform();
			ReportManager.log("Onbehalf validations done successfully");
						
			dashBoardPageObj.logout();
			ReportManager.log("Club owner logged out successfully");

			ReportManager.log("==============  End club owner onbehalf options test ==============");
			
		}
		
		@Test
		public void testApproveIncident() {
			ReportManager.log("==============  Start Approve Incident by Admin test ==============");
			
			IncidentManagement incidentManagementObj = new IncidentManagement(apiObj, properties);
			String incidentId = incidentManagementObj.prepareIncident().get(1);
			String adminAcc= properties.getProperty("superAdmin");
			String adminPW= properties.getProperty("superAdminPW");
			String adminName= properties.getProperty("superAdminName");
			String clubOwnerAcc = properties.getProperty("clubOwnerAcc");
			String clubOwnerPw = properties.getProperty("clubOwnerPW");
			String clubOwnerNotificationMsg= properties.getProperty("IncidentNotificationMsgPart1") + " "
			+incidentId+" "+properties.getProperty("IncidentNotificationMsgPart2");
			boolean notificationDisplayed;
			homePageObj.clickLoginLink();
			
		    boolean login = loginPageObj.login(adminAcc, adminPW);
		    Validations.assertThat().object(login).isTrue().perform();
			ReportManager.log("SEF admin logged in successfully");
			dashBoardPageObj.navigateToIncidents();
			incidnetListPageObj.openIncidentByName(incidentId);
			Validations.assertThat().object(incidentDetailsPageObj.approve(adminName)).isTrue().perform();
			ReportManager.log("Incident approved successfully");
			dashBoardPageObj.logout();
			ReportManager.log("Admin logged out successfully");
			
			homePageObj.clickLoginLink();
			Validations.assertThat().object(loginPageObj.login(clubOwnerAcc, clubOwnerPw)).isTrue().perform();
			notificationDisplayed = notificationPageObj.checkNotficationContent(clubOwnerNotificationMsg);
			Validations.assertThat().object(notificationDisplayed).isTrue().perform();
			ReportManager.log("The Approved Notification message displayed successfully");
			dashBoardPageObj.logout();
			ReportManager.log("club owner logged out successfully");

			ReportManager.log("==============  End Approve Incident by Admin test ==============");
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
