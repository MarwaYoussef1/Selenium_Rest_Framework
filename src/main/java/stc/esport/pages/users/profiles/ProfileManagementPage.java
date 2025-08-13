package stc.esport.pages.users.profiles;

import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;

public class ProfileManagementPage extends PageBase{
	
	public WebDriver driver;
	public Properties properties;
	
	// identifier
	By profileTab= By.xpath("//a[contains(@href,'profiles')]");
	By searchBar= By.xpath("//input[@placeholder='Search']");
	By profileCard= By.xpath("//div[contains(@class,'profile-list')]");
	By editBtn= By.xpath("//button[@type='submit']");
	By toggleStatus= By.xpath("(//mat-slide-toggle//input[@type='checkbox'])[2]");
	By teamMemberToggle= By.xpath("//label[contains(text(),'Allow team & clan member')]//following::mat-slide-toggle");
	By addProfileBtn= By.xpath("//a[contains(@href,'profiles/add')]");
	
	
	
	// Constractor
			public ProfileManagementPage(WebDriver driver, Properties properties) {
				this.driver = driver;
				this.properties = properties;
			}
			
			
			
	//methods
			
	public void selectProfilesTab() {
		driver.element().click( profileTab);
	}
	
	
	public boolean searchWithProfileName(String name) {
		By nameOnCard= By.xpath("//div[@class='profile-content']//div[text()="+"'"+name+"'"+"]");
		driver.element().type(searchBar, name);
		 
		return isElementExist(driver, nameOnCard, name);
	}
	
	    public void clickOnAddProfile() {
		driver.element().click(addProfileBtn);
	    }
	
	    public void editProfile(String name) {
	    	selectProfilesTab();
			sleep(2000);
			searchWithProfileName(name);
			
			driver.element().click( profileCard);
			driver.element().click( editBtn);
			sleep(2000);
	    }
	    
	public boolean disableEnableTeamMemberToggle(String name,String status) {
		selectProfilesTab();
		sleep(2000);
		searchWithProfileName(name);
		
		driver.element().click( profileCard);
		driver.element().click( editBtn);
		sleep(2000);
		driver.element().click( teamMemberToggle);
		
	String checked=	driver.element().getAttribute(toggleStatus, "aria-checked");
	
		if(status.equals("enable")&&checked.equals("true")) {
			executeJavaScript(driver, editBtn, "arguments[0].click();");
			waitForPageLoadWithoutSpinner(driver);
			return true; 
		}
		else if(status.equals("disable")&&checked.equals("false")){
			executeJavaScript(driver, editBtn, "arguments[0].click();");
			waitForPageLoadWithoutSpinner(driver);
			return true;
		}
		else {
			return false; 
		}
	}
	
	public void expandProfileAuthorities(String name) {
		By tabName= By.xpath("//span[text()="+"' "+name+" '"+"]");

		//By tabExpanded= By.xpath("//span[text()="+"' "+name+" '"+"]//parent::div//parent::span//parent::mat-expansion-panel-header");
	   driver.element().click(tabName);
	}
	
	public void selectAuthority(String moduleName,ArrayList<String> authoritiesNames){
		expandProfileAuthorities(moduleName);
		
		for(int i=0; i<authoritiesNames.size();i++) { 
			By authorityLocator= By.xpath("//span[text()=' Club Management ']//following::span[text()="+"' "+authoritiesNames.get(i)+" '"+"][1]");
			driver.element().click(authorityLocator);
		}
	}
		

}


