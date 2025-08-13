package stc.esport.pages.teamsclans;

import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pojo.Teams;
import stc.esport.utils.Constants;

public class MyTeamsListPage extends PageBase{

	public WebDriver driver;
	public Properties properties;
	public AddTeamPage addTeamPageObj;
	public TeamProfilePage teamProfilePageObj;
	
	// identifier
	   By myTeamsTab= By.xpath("//a[contains(@href,'my-teams')]");
	   By myClanTab= By.xpath("//a[contains(@href,'clans/my-clans')]");
	   By membershipTab= By.xpath("//a[contains(@href,'subscribed-')]");
	   By archivedTab= By.xpath("//a[contains(@href,'archived-')]");
	   By addTeamBtn= By.xpath("//a[contains(@href,'/add')]");
	   By search= By.xpath("//input[contains(@placeholder,'Search')]");
	   By dotsIcon= By.xpath("//button[@aria-haspopup='menu']");//cdk-overlay-container
	   By overlay=By.xpath("//div[@class='cdk-overlay-container']");
	   By editTeamBtn= By.xpath("//a[contains(@href,'edit')]");
	   By leaveTeamBtn= By.xpath("//span[contains(text(),'Leave')]//parent::button");
	   By archiveBtn= By.xpath("//span[contains(text(),'Archive')]//parent::button");
	   By manageContractBtn= By.xpath("//span[@class='manage-contracts']");
	   By unarchiveBtn= By.xpath("//span[contains(text(),'Unarchive')]//parent::button");
	   By confrimAction= By.xpath("//button[contains(@class,'confirm')]");
	   By cancelLeave= By.xpath("//button[contains(@class,'cancel')]");
	   By successToast= By.xpath("//div[contains(@id,'html-container')]");
	   By noRecordFound=By.xpath("//div[@class='notfound-content']//h5");
	   By errorPopupMsg= By.xpath("//div[contains(@id,'container')]//span");

	   
	
	// Constractor
		public MyTeamsListPage(WebDriver driver, Properties properties) {
			this.driver = driver;
			this.properties = properties;
			addTeamPageObj = new AddTeamPage(driver, properties);
			teamProfilePageObj = new TeamProfilePage(driver, properties);
		}
		
		public void selectMyTeamTab() {
			driver.element().click(myTeamsTab);
			waitForPageLoadWithoutSpinner(driver);
		}
		
		public void selectMyClanTab() {
			driver.element().click(myClanTab);
			waitForPageLoadWithoutSpinner(driver);
		}
		
		public void selectMembershipTab() {
			
			driver.element().click(membershipTab);
			waitForPageLoadWithoutSpinner(driver);
		}
		
		public void selectArchivedTab() {
			driver.element().click( archivedTab);
			waitForPageLoadWithoutSpinner(driver);
		}
		
		public void pressAddteamBtn() {
			driver.element().click( addTeamBtn);
			waitForPageLoadWithoutSpinner(driver);
		}
		
		public boolean teamAction(String team, String action) {
			boolean actionStatus= true;
			
            searchWithTeamName(team);
			
            waitForPageLoadWithoutSpinner(driver);
            driver.element().click( dotsIcon);
            
            if(action.equals(Constants.EDITTEAM)&&isElementExist(driver, editTeamBtn,"edit")) {
            	driver.element().click( editTeamBtn);	
            }
            else if(action.equals(Constants.LEAVETEAM)&&isElementExist(driver, leaveTeamBtn,"leave")) {
            	      driver.element().click( leaveTeamBtn);
            }
            else if (action.equals(Constants.ARCHIVETEAM)&&isElementExist(driver, archiveBtn,"archive")) {
            	      driver.element().click(archiveBtn);
            }
            else if(action.equals(Constants.UNARCHIVETEAM)&&isElementExist(driver, unarchiveBtn,"unArchive")){
            	driver.element().click(unarchiveBtn);
            }
            else if(action.equals(Constants.MANAGECONTRACTTEAM)&&isElementExist(driver, manageContractBtn,"manage contract")) {
                driver.element().click(manageContractBtn);

            }
            	else {
            	      driver.element().click(overlay);
                  	actionStatus= false;
            }
          return actionStatus;
		}
		
		public boolean isAddTeamClanExist() {
			 if(isElementExist(driver, addTeamBtn, "Add"))
				 return true;
			 return false;
		}
		
		public boolean isActionExist(String teamClanName, String action) {
			
			 
            searchWithTeamName(teamClanName);

            waitForPageLoadWithoutSpinner(driver);
            driver.element().click( dotsIcon);
            
            if(action.equals(Constants.EDITTEAM)&& isElementExist(driver, editTeamBtn,"Edit"))
            	return true;
            
            if(action.equals(Constants.ARCHIVETEAM)&& isElementExist(driver, archiveBtn,"Archive"))
            	return true;
            
            if(action.equals(Constants.UNARCHIVETEAM)&& isElementExist(driver, unarchiveBtn,"Unarchive"))
            	return true; 
            
         return false;
		}
	
		
		public boolean addTeamWithoutMembers(Teams teams) {
			pressAddteamBtn();
			
			return addTeamPageObj.addEditTeamWithoutMembers(teams,Constants.ADDTEAM);
		}
		
		
		public boolean editTeamWithoutMembers(Teams team, Teams updateTeam) {

			teamAction(team.getTeamNameEn(),Constants.EDITTEAM);
			
		return	addTeamPageObj.addEditTeamWithoutMembers(updateTeam,Constants.EDITTEAM);
		}
		
		
		public boolean searchWithTeamName(String name) {		
			By cardTitle= By.xpath("//*[@title="+"'"+name+"'"+"]");
			driver.element().type(search, name);
			waitUntilElementPresent(driver, cardTitle);
		 return  isElementExist(driver, cardTitle, name);
		}
		
		
		public boolean searchWithInvalidValue(String name) {
			String	invalidSearchText= properties.getProperty("invalidSearch");
			driver.element().type(search, name);
			if(isElementExist(driver, noRecordFound, invalidSearchText)) {
				return true;
			}else {
				return false;
			}
		}
		
		
		public  ArrayList<Boolean> checkTeamCardDetails(Teams team) {
	 	      ArrayList<Boolean> status=new ArrayList<Boolean>(); 

			By teamLogo= By.xpath("//img[contains(@src,"+"'"+team.getTeamLogo()+"'"+")]");
			By teamGame= By.xpath("//span[@title="+"'"+team.getGame()+"'"+"]");
			By clubName= By.xpath("//span[@title="+"'"+properties.getProperty("clubName")+"'"+"]");
			
			searchWithTeamName(team.getTeamNameEn());
			status.add(isElementExist(driver,teamGame ,team.getGame()));

			if (team.getTeamType().equals(Constants.CLUBTEAM)) {
				By teamMember= By.xpath("//span[text()="+"'"+properties.getProperty("clubTeamMember")+"'"+"]");

				status.add(isElementExist(driver,teamLogo ,team.getTeamLogo()));
				status.add(isElementExist(driver,teamMember, properties.getProperty("clubTeamMember")));
				status.add(isElementExist(driver,clubName, team.getTeamNameEn()));
				
			}else {
				By teamMember= By.xpath("//span[text()="+"'"+properties.getProperty("clanTeamMember")+"'"+"]");

				status.add(isElementExist(driver,teamMember, properties.getProperty("clanTeamMember")));
			}
			
			return status;	
		}
		
		
		public boolean addTeamWithMembers(Teams team, String memberImage) {
			pressAddteamBtn();			
			return addTeamPageObj.addEditTeamWithMembers(team, memberImage,Constants.ADDTEAM);
		}
		
		public boolean editTeamWithMembers(Teams team, String memberImage) {
			teamAction(team.getTeamNameEn(),Constants.EDITTEAM);
			
			return addTeamPageObj.addEditTeamWithMembers(team, memberImage,Constants.EDITTEAM);
		}
		
		
		public boolean cancelAddTeamMember (Teams team, String memberImage) {
            pressAddteamBtn();
			return addTeamPageObj.cancelAddMember(team.getMemberWithRole(), memberImage);
		}
		
		public boolean leaveTeam(String teamName) { 
			String successMsg= properties.getProperty("successLeave");
			
			teamAction(teamName,Constants.LEAVETEAM);
			driver.element().click(confrimAction);
			
		    return isMsgExistandDisplayed(driver, successToast, successMsg);
		}
		
		public boolean cancelLeaveTeam(String teamName) {
			teamAction(teamName,Constants.LEAVETEAM);
			driver.element().click(cancelLeave);
			return searchWithTeamName(teamName);
		}
		
		public boolean archiveTeam(String teamName, String teamType, boolean callForMemberActive) {
			String archiveMsg = null;
		    String archiveTeamCallForMember=properties.getProperty("archiveTeamCallForMember");
		    
	
		     if(teamType.equals(Constants.CLUBTEAM)) {
		      archiveMsg=properties.getProperty("TeamSuccessArchive");
			}else {
			  archiveMsg=properties.getProperty("ClanSuccessArchive");
			}
			
			teamAction(teamName, Constants.ARCHIVETEAM);
			driver.element().click(confrimAction);
			
			if(callForMemberActive) {
			boolean status= isMsgExistandDisplayed(driver, errorPopupMsg,archiveTeamCallForMember);
				driver.element().click(confrimAction);
				return status;
			}else {
			 return isMsgExistandDisplayed(driver, successToast,archiveMsg);
			 }
		}
		
		public boolean archiveTeamWithContracts(String teamName) {
			teamAction(teamName, Constants.ARCHIVETEAM);
			driver.element().click(confrimAction);
			waitUntilElementPresent(driver, successToast);
			waitForPageLoadWithoutSpinner(driver);
			return true;
		
		}
		
		public boolean unarchiveTeam(String teamName, String teamType) {
			String unarchiveMsg;
			
			 if(teamType.equals(Constants.CLUBTEAM)) {
			      unarchiveMsg="Team "+properties.getProperty("successUnarchive");
				}else {
					unarchiveMsg= "Clan "+properties.getProperty("successUnarchive");
				}
			 
			 selectArchivedTab();
			 teamAction(teamName, Constants.UNARCHIVETEAM);
			 driver.element().click(confrimAction);
             
			 return isMsgExistandDisplayed(driver, successToast, unarchiveMsg);
		}
		
		public boolean unachiveButReachedtheLimit(String teamName, String teamNumbers) {
			String unarchiveErrorMsg= properties.getProperty("unarchiveLimitError1")+" "+teamNumbers+" "+
					 properties.getProperty("unarchiveLimitError2");
			
			 selectArchivedTab();
			 teamAction(teamName, Constants.UNARCHIVETEAM);
			 driver.element().click(confrimAction);
			 
			 if(isMsgExistandDisplayed(driver, errorPopupMsg,unarchiveErrorMsg)) {
				 driver.element().click(confrimAction);
					return true;
			 }else {
				 return false;
			 }
		}
	
		private boolean openTeamProfile(String teamName)
		{
			By cardTitle= By.xpath("//h4[@title="+"'"+teamName+"'"+"]");
			selectMyTeamTab();
		    if(searchWithTeamName(teamName))
			driver.element().click(cardTitle);
			return teamProfilePageObj.getProfileTitle().equals(teamName.toUpperCase());
						
		}
		
		
		public boolean checkTeamMemeberAddedInProfile(String teamName,String role,String accountName)
		{
			if(openTeamProfile(teamName))
			{
				return teamProfilePageObj.checkIfMemberExist(role, accountName);
				
			}
			return false;
		}
		
		public void openTeamContracts(String teamName)
		{
			teamAction(teamName,Constants.MANAGE_CONTRACTS);
			
		}
		
		public boolean checkContractReqStatus(String teamName) {
			String status= properties.getProperty("contractRequestedStatus");
			By contractReqLocator= By.xpath("//div[text()="+"' "+status+" '"+"]");
			
	        searchWithTeamName(teamName);

			if(isElementExist(driver, contractReqLocator, ""))
				return true;
			return false;
			
			}
}
