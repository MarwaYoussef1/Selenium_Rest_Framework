package stc.esport.pages.teamsclans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pojo.Teams;
import stc.esport.utils.Constants;

public class AddTeamPage extends PageBase {

	public WebDriver driver;
	public Properties properties;
	public Map<String, String> socialMediaLinks;

	// identifier
	By teamNameEn = By.id("teamNameEn");
	By clanNameEn = By.id("clanNameEn");
	By teamNameAr = By.id("teamNameAr");
	By clanNameAr = By.id("clanNameAr");
	By about = By.id("about");
	By teamLogo = By.xpath("//input[@type='file']");
	By game = By.xpath("//ng-select//input[@type='text']");
	By addGame = By.xpath("//div[@class='ng-value-container']");
	By addMembers = By.xpath("//div[contains(@class,'-members')]//button[contains(@class,'add-btn')]");
	By selectRole = By.xpath("(//div[@class='ng-input']//input)[2]");
	By memberMail = By.id("email");
	By addMemberMail = By.xpath("//button[contains(@class,'add-mail-')]");
	By mailsList = By.xpath("//span[@class='mail']");
	By rolesList = By.xpath("//span[@class='role badge']");
	By saveMembersMails = By.xpath("//button[contains(@class,'save-btn')]");
	By addSocialMedia = By.xpath("//div[contains(@class,'social-medi')]//button[contains(@class,'add-btn')]");
	By closeSocialMedia = By.xpath("(//app-social-media-form//span[contains(@class,'icon-close')])[1]");
	By submitBtn = By.xpath("//div[@class='action-btns']//button[@type='submit']");
	By successToast = By.xpath("//div[contains(@id,'html-container')]");
	By mailError = By.xpath("//div[contains(@class,'invalid-feedback')]//div");
	By cancelAddMemberBtn = By.xpath("//button[@class='btn btn-secondary']"); 
	By confirmRemoveBtn = By.xpath("//button[contains(@class,'confirm-action')]");
	By roleNotFound = By.xpath("//div[contains(@class,'ng-option-disabled')]");  
	// identifiers for remove member with contract
	By removeReason=By.id("lockReason");
	By confirmContractMemberRemoveBtn=By.xpath("//div[contains(@class,'mat-dialog-actions')]//button[@type='submit']");
	

	// Constractor
	public AddTeamPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}

	private void FillTeamMandatoryData(Teams team) {
		if (team.getTeamType().equals(Constants.CLUBTEAM)) {
			uploadFile(driver,team.getTeamLogo(), teamLogo);
			driver.element().type(teamNameEn, team.getTeamNameEn());
			driver.element().type(teamNameAr, team.getTeamNameAr());
		} else {

			driver.element().type(clanNameEn, team.getTeamNameEn());
			driver.element().type(clanNameAr, team.getTeamNameAr());
		}

		driver.element().type(game, team.getGame());
		sleep(2000);
		driver.element().keyPress(game, Keys.ENTER);
	}

	public boolean submitAddEditTeam(String teamAction) {
		String addTeamMsg = properties.getProperty("teamSuccessMsg");
		String editTeamMsg = properties.getProperty("editTeamSuccessMsg");

		driver.element().click(submitBtn);

		if (teamAction.equals(Constants.ADDTEAM)) {
			//waitUntilElementPresent(driver, successToast);
		   return isMsgExistandDisplayed(driver, successToast, addTeamMsg);
		} else {
			//waitUntilElementPresent(driver, successToast);
			return isMsgExistandDisplayed(driver, successToast, editTeamMsg);
		}
	}

	public boolean submitAddEditClan(String teamAction) {
		String addClanMsg = properties.getProperty("clanSuccessMsg");
		String editClanMsg = properties.getProperty("editClanSuccessMsg");

		driver.element().click(submitBtn);

				if(teamAction.equals(Constants.ADDTEAM)) {
					return isMsgExistandDisplayed(driver,successToast,addClanMsg);
				}else {
					return isMsgExistandDisplayed(driver,successToast,editClanMsg);
				}
		}
		
		public boolean addEditTeamWithoutMembers(Teams team,String action) {
			FillTeamMandatoryData(team);
			driver.element().type(about, team.getAbout());
			
			if(action.equals(Constants.ADDTEAM)) {
			    addSocialMedia(team.getSocialMedia());
			}else {
				removeSocialMedia(team.getSocialMedia());
			}
			if(team.getTeamType().equals(Constants.CLUBTEAM)) {
			return submitAddEditTeam(action);
			}else{
			return submitAddEditClan(action);
			}
		}
		
			
		private boolean addSocialMedia(Map<String, String> socialMediaLinks) {
			    driver.element().click( addSocialMedia);
				
				fillSocialhMap(driver, socialMediaLinks);
				driver.element().click( closeSocialMedia);
				
				return checkAddedSocialMedia(driver, socialMediaLinks);
		}
			
	
		public boolean addEditTeamWithMembers(Teams team, String memberImage, String action ) {
			boolean successSubmit= false;
			
			if(action.equals(Constants.ADDTEAM)){
			FillTeamMandatoryData(team);
			}
			
			ArrayList<Boolean> status= addMembers(team.getMemberWithRole(),memberImage);
			
			if(status.stream().allMatch(val -> val == true)){
				if(team.getTeamType().equals(Constants.CLUBTEAM)) {
					successSubmit= submitAddEditTeam(action);
				}else {
					successSubmit= submitAddEditClan(action);
				}
			  }
			return successSubmit;
		}
		
		
		public void membersMapIteration(HashMap<String, ArrayList<String>> membersWithRoles) {
			for (Map.Entry<String, ArrayList<String>> entry : membersWithRoles.entrySet()) {
				
				driver.element().type( selectRole, entry.getValue().get(0));
				driver.element().keyPress(selectRole, Keys.ENTER);
									
				driver.element().type(memberMail,entry.getKey() );
				driver.element().click(addMemberMail);
			}
		}
		
		public boolean roleNotDisplayed (String roleName) {
			driver.element().click( addMembers);

			driver.element().type(selectRole, roleName);
			 if( isElementVisible( driver,roleNotFound)) {
				 driver.element().click( cancelAddMemberBtn);
					return true;
			 }else {
				 return false;
			 }
		}

	public ArrayList<Boolean> addMembers(HashMap<String, ArrayList<String>> membersWithRoles, String memberImage) {
		ArrayList<Boolean> status = new ArrayList<Boolean>();
		status.add(0, false);
		driver.element().click(addMembers);

		membersMapIteration(membersWithRoles);

		driver.element().click(saveMembersMails);
		waitForPageLoadWithoutSpinner(driver);

		return memberCardDetials(membersWithRoles, memberImage);
	}

	public ArrayList<Boolean> memberCardDetials(HashMap<String, ArrayList<String>> membersWithRoles,
			String memberImage) {
		sleep(6000);
		List<WebElement> rolesList = driver.getDriver().findElements(By.xpath("//button[contains(@class,'role')]"));
		List<WebElement> contentList = driver.getDriver().findElements(By.xpath("//button[contains(@class,' content')]"));
		List<WebElement> MemberImagesList = driver.getDriver().findElements(By.xpath("//img[contains(@class,'rounded-circle')]"));
		List<WebElement> memberStatusList = driver.getDriver().findElements(By.xpath("//div[contains(@class,'status')]"));

		ArrayList<Boolean> status = new ArrayList<Boolean>();
		status.add(0, false);

		for (int i = 0; i < rolesList.size(); i++) {

			for (Map.Entry<String, ArrayList<String>> entry : membersWithRoles.entrySet()) {
				status.clear();

				if (rolesList.get(i).getText().equalsIgnoreCase(entry.getValue().get(0))) {
					status.add(true);
				} else {
					status.add(false);
				}

				sleep(2000);
				if (contentList.get(i).getText().equals(entry.getKey())
						|| contentList.get(i).getText().equals(entry.getValue().get(1))) {
					status.add(true);
				} else {
					status.add(false);
				}

				if (MemberImagesList.get(i).getAttribute("src").contains(memberImage)) {
					status.add(true);
				} else {
					status.add(false);
				}

				if (entry.getValue().size() > 2) {
					if (memberStatusList.get(i).getText().equals(entry.getValue().get(2))) {
						status.add(true);
					} else {
						status.add(false);
					}
				}
			}
		}
		return status;
	}

	public boolean memberValidationCases(HashMap<String, ArrayList<String>> membersWithRoles, String errorMsg) {
		boolean status = false;

		driver.element().click(addMembers);
        sleep(2000);
		membersMapIteration(membersWithRoles);

		if (isMsgExistandDisplayed(driver, mailError, errorMsg)) {
			driver.element().click(cancelAddMemberBtn);
			status = true;
		}
		return status;
	}

	public boolean cancelAddMember(HashMap<String, ArrayList<String>> membersWithRoles, String memberImage) {
		boolean status = false;
		List<WebElement> memberCards = driver.getDriver().findElements(By.xpath("//div[contains(@class,'member-card')]"));

		driver.element().click(addMembers);
		membersMapIteration(membersWithRoles);
		driver.element().click(cancelAddMemberBtn);

		if (memberCards.size() == 0) {
			status = true;
		}
		return status;
	}

	public boolean removeMember(HashMap<String, ArrayList<String>> membersWithRoles, String action) {
		Entry<String, ArrayList<String>> firstEntry = membersWithRoles.entrySet().iterator().next();
		String firstValue = firstEntry.getValue().get(1);

		By removeBtn = By.xpath(
				"//button[text()=" + "'" + firstValue + " '" + "]//following::button[contains(@class,'close-btn')]");
		
		waitUntilElementClickable(driver, removeBtn);
		driver.element().click(removeBtn);

		if (action.equals(Constants.EDITTEAM)) {
			driver.element().click(confirmRemoveBtn);
		}
		
		waitForElementDisappeared(driver, removeBtn);
		if(isElementExist(driver, removeBtn,"removeBtn")) {
		     return false;
		}else {
			return true;
		}
	}
	
	public boolean removeContractMemberByAccountName(String accountName) {
		
		By removeBtn = By.xpath(
				"//button[text()=" + "'" + accountName + " '" + "]//following::button[contains(@class,'close-btn')]");
		
		waitUntilElementPresent(driver, removeBtn);
		driver.element().click(removeBtn);
		waitUntilElementPresent(driver, confirmContractMemberRemoveBtn);
		if(isElementExist(driver, removeReason, "Remove reason test area"))
			driver.element().type(removeReason, "Remove member");
		driver.element().click(confirmContractMemberRemoveBtn);
		if(isElementExist(driver, removeReason, "Remove reason test area"))
			 return submitAddEditTeam(Constants.EDITTEAM);
		else
		{
		waitForElementDisappeared(driver, removeBtn);
		submitAddEditTeam(Constants.EDITTEAM);
		return true;
		}
	}

	private boolean removeSocialMedia(HashMap<String, String> hashMap) {
		 Entry<String,String> entry = hashMap.entrySet().iterator().next();
		 String key= entry.getKey();
		 
		By removeBtn= By.xpath("//h5[text()="+"'"+key+"'"+"]//following::button[1]");
		By addAccount = By.xpath("(//p[text()="+"'" +key+"'"+"]//following::button)[1]");
		boolean status=false;
		
		driver.element().click( removeBtn);
		driver.element().click(addSocialMedia);
		
	if(	isElementVisible(driver, addAccount)) {
		driver.element().click( closeSocialMedia);

		status= true;
	      }
	return status;

	}

}
