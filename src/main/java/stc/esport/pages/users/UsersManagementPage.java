package stc.esport.pages.users;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.shaft.driver.SHAFT.GUI.WebDriver;

import stc.esport.base.PageBase;
import stc.esport.pages.HomePage;
import stc.esport.pojo.User;
import stc.esport.utils.Constants;



public class UsersManagementPage extends PageBase {

	public WebDriver driver;
	public Properties properties;
	HomePage homePageObj;
	

	public UsersManagementPage(WebDriver driver, Properties properties) {
		this.driver = driver;
		this.properties = properties;
	}

	// Add new user profile button
	By addNewUserButton = By.xpath("//a[contains(@href,'users/add')]");
	// user email id in List grid
	By userEmailIdInListGrid = By.xpath("//td[contains(@class,'email')]");
	
	//Add grid
	By fullName= By.id("firstName");
	By emailAddress=By.id("email");
	By mobileNumber=By.id("mobileNo");
	By nationality=By.xpath("//*[@id='nationality']//descendant::input");
	By maleRadioButton=By.xpath("//input[@value='MALE']");
	By femaleRadioButton=By.xpath("//input[@value='FEMALE']");
	By employeeRadioButton=By.xpath("//input[@value='EMPLOYEE']");
	By vendorRadioButton=By.xpath("//input[@value='VENDOR']");
	By profile=By.xpath("//*[@id='profileId']//descendant::input"); 
	By region=By.xpath("//*[@id='level_1']//descendant::input"); 
	By subLevel=By.xpath("//*[@id='level_2']//descendant::input");
	By saveBtn=By.xpath("//button[@type='submit']");
	By userAddedMsg=By.id("swal2-html-container");



	// Click on add new user button
	public void clickOnAddNewUser() {
		waitUntilElementClickable(driver,addNewUserButton);
		driver.element().click(addNewUserButton);
		
	}
	

	// Add new user function
	public String addNewUser(User user) {
		String emailAddress = user.getName()+properties.getProperty("FakeDomain");
		clickOnAddNewUser();
		user.setEmail(emailAddress);
		fillUserData(user);
		driver.element().click(saveBtn);
		waitUntilElementPresent(driver, userAddedMsg);
		return emailAddress;
	}
	
	private void fillUserData(User user)
	{
		driver.element().type(fullName, user.getName());
		driver.element().type(emailAddress, user.getEmail());
		driver.element().type(mobileNumber, user.getMobileNumber());
		selectByAutoComplete(nationality,user.getNationality());
		if(user.getGender().equals(Constants.USERFEMALEGENDER))
		driver.element().click(femaleRadioButton);
		if(user.getType().equals(Constants.USERVENDORTYPE))
		driver.element().click(vendorRadioButton);
		selectByAutoComplete(profile,user.getProfile());
		selectByAutoComplete(region,user.getRegion());
		if(isElementFound(driver, subLevel, "Sub level"))
		selectByAutoComplete(subLevel,user.getSubRegion());	
	}

	private void selectByAutoComplete(By autoCompleteInput,String value)
	{
		driver.element().click(autoCompleteInput);
		waitForDropDownWithoutSpinner(driver);
		driver.element().type(autoCompleteInput, value);
		driver.element().keyPress(autoCompleteInput, Keys.ENTER);
	}
	


}
