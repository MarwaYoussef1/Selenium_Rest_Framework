//PageBase 
// This Class is used for all common Functions of the business 
// 10-01-2023
//-------------------------------------------

package stc.esport.base;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.shaft.cli.FileActions;
import com.shaft.driver.SHAFT.GUI.WebDriver;
import com.shaft.tools.io.ReportManager;

public class PageBase {

	// methods
	
	By socialURL= By.xpath("//div[contains(@class,'d-none')]//input[@id='basic-url']");
	By submitSocial= By.xpath("//div[contains(@class,'d-none')]//button[contains(@class,'outline-primary')]");
	By search= By.xpath("//input[contains(@placeholder,'Search')]");

	
	public boolean isElementExist(WebDriver driver, By elementLocator, String ElementName) {
		int foundElementsCount = 0;

		foundElementsCount = driver.getDriver().findElements(elementLocator).size();
		// boolean HEADLESS_EXECUTION =
		// Boolean.valueOf(System.getProperty("headlessExecution").trim());
		ReportManager.log("ElementName : [" + ElementName + "], locator: [" + elementLocator + "] and count -->  ["
				+ foundElementsCount + "]");

		if (foundElementsCount > 0) {
			/*
			 * if(HEADLESS_EXECUTION) { ReportManager.log("Element [" + ElementName +
			 * "] is exist"); return true; }
			 */
			if (isElementVisible(driver, elementLocator)) {
				ReportManager.log("Element [" + ElementName + "] is exist and visible");
				return true;
			} else {
				ReportManager.log("Element [" + ElementName + "] is exist but not visible!!");
				return false;
			}
		}
		return false;
	}

	public boolean isElementFound(WebDriver driver, By elementLocator, String ElementName) {
		int foundElementsCount = 0;
		sleep(2000);

		foundElementsCount = driver.getDriver().findElements(elementLocator).size();
		ReportManager.log("ElementName : [" + ElementName + "], locator: [" + elementLocator + "] and count -->  ["
				+ foundElementsCount + "]");

		if (foundElementsCount > 0) {
			return true;
		}
		return false;
	}

	public boolean waitUntilElementClickable(WebDriver driver, By elementLocator) {
		boolean flag = false;
		try {
			ReportManager.log("Start clicking on element." + elementLocator);
			(new WebDriverWait(driver.getDriver(), Duration.ofSeconds(200)))
					.until(ExpectedConditions.elementToBeClickable(elementLocator));
			ReportManager.log("Element matching this locator [" + elementLocator + "] found and clickable.");
			flag = true;
		} catch (TimeoutException e) {
			ReportManager.log(e.getMessage());
			ReportManager.log("Element matching this locator [" + elementLocator + "] not clickable.");
			flag = false;
		}
		return flag;
	}

	public boolean click(WebDriver driver, By elementLocator,boolean checkOnElementClickable) {

		ReportManager.log(" Start clicking using native selenium ");
		if (checkOnElementClickable && !waitUntilElementClickable(driver, elementLocator)) {
			return false;
		}

		driver.getDriver().findElement(elementLocator).click();
		/*if (!isElementExist(driver, elementLocator, "button")) {
			ReportManager.log(" Clicking using native selenium on  " + elementLocator + " Done successfully");
			return true;

		}*/
		ReportManager.log(" End clicking using native selenium ");
		return true;
	}

	/*public boolean isMsgExistandDispalyed(WebDriver driver, By MsgID, String ValidatedMsgTitle) {
		return isMsgExistandDisplayed(driver, MsgID, ValidatedMsgTitle, 50);
	}*/

	public boolean isMsgExistandDisplayed(WebDriver driver, By MsgID, String ValidatedMsgTitle) {
		boolean flag = false;
		driver.element().waitToBeReady(MsgID);
		boolean isMsgDisplayed = isElementExist(driver, MsgID, ValidatedMsgTitle);

		if (isMsgDisplayed) {
			String ScreenMsg = "";
			ScreenMsg = driver.element().getText(MsgID);

			if (ScreenMsg.contains(ValidatedMsgTitle)) {
				ReportManager.log("Message: [" + ValidatedMsgTitle
						+ "] is Displayed Successfully as it is part of the screen Msg [" + ScreenMsg + "]");
				flag = true;
			} else {
				ReportManager.log("Message: [" + ValidatedMsgTitle + "] is failed to be Validated with the screen Msg ["
						+ ScreenMsg + "]");
				flag = false;
			}
		} else {
			ReportManager.log("Msg [" + ValidatedMsgTitle + "] Doesn't be displayed ");
			flag = false;
		}
		return flag;
	}

	
	public boolean isElementVisible(WebDriver driver, By elementLocator) {
		boolean flag = false;
		try {
			(new WebDriverWait(driver.getDriver(), Duration.ofSeconds(20)))
					.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
			System.out.println("Element matching this locator [" + elementLocator + "] is visible.");
			flag = true;
		} catch (TimeoutException e) {
			System.out.println("Element matching this locator [" + elementLocator + "] is Not visible.");
			flag = false;
		}

		return flag;
	}
	
	public void waitUntilElementPresent(WebDriver driver, By elementLocator) {
	
		try {
			sleep(2000);
			(new WebDriverWait(driver.getDriver(), Duration.ofSeconds(100)))
					.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
			System.out.println("Element matching this locator [" + elementLocator + "] present.");
			
		} catch (TimeoutException e) {
			System.out.println("Element matching this locator [" + elementLocator + "]  Not present.");
		
		}

	
	}

	// ====================================================================

	public static void sleep(Integer milliSeconds) {
		long secondsLong = (long) milliSeconds;
		try {
			Thread.sleep(secondsLong);
		} catch (Exception e) {
			ReportManager.log(e.getMessage());
		}
	}

	// public void waitforPageLoad(WebDriver driver, int timeout) {
	// driver.getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS); }

	public void executeJavaScript(WebDriver driver, By elementLocator, String JavaScript) {
		if(isElementExist(driver, elementLocator, JavaScript))
		{
		ReportManager.log("<-- Handle this element " + elementLocator + " By JavaScript --> " + JavaScript);
		((JavascriptExecutor) driver.getDriver()).executeScript(JavaScript, driver.getDriver().findElement(elementLocator));
		ReportManager.log("<-- Handle this element " + elementLocator + " By JavaScript -->  Done ");
		}
		
	}

	public List<String> getSelectOptions(WebDriver driver, By selectLocator) {
		WebElement select = driver.getDriver().findElement(selectLocator);
		List<WebElement> options = select.findElements(By.tagName("option"));
		List<String> List = new ArrayList<>();

		for (WebElement ele : options) {
			List.add(ele.getText());
			ReportManager.log("Item: [" + ele.getText() + "]");
		}
		ReportManager.log("select Items: " + List);
		return List;
	}

	public boolean selectItemByValueInOptionList(WebDriver driver, By listLocator, String optionValue) {

		WebElement ulListItems = driver.getDriver().findElement(listLocator);
		List<WebElement> listItems = ulListItems.findElements(By.tagName("option"));
		boolean itemSelected = false;

		for (WebElement item : listItems) {
			if (item.getText().trim().equalsIgnoreCase(optionValue)) {
				ReportManager.log("Item: [" + item.getText().trim() + "] will be selected");
				item.click();
				if (item.getAttribute("Class").contains("selected")) {
					ReportManager.log("Item: [" + item.getText().trim() + "]  selected");
					itemSelected = true;
				}
			}

		}
		return itemSelected;
	}

	public boolean isCheckBoxChecked(WebElement checkBoxLocator) {
		String checked = checkBoxLocator.getAttribute("checked");

		if (checked != null && checked.equals("true")) {
			return true;
		}
		return false;
	}

	// switching between tabs in the same window
	public static void switchingBetweenTabs(WebDriver driver, int tabNum) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getDriver().getWindowHandles());

		for (int i = 0; i <= tabs.size(); i++) {
			if (i == tabNum)
			 driver.browser().switchToWindow(tabs.get(i));
			
		}
	
	}

	public static void openUrlInNewTab(WebDriver driver, String Url, int tabNum) {
		((JavascriptExecutor) driver.getDriver()).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getDriver().getWindowHandles());
		driver.browser().switchToWindow(tabs.get(tabNum));
		driver.getDriver().navigate().to(Url);
		
	}

	public static void waitForPageLoadWithoutSpinner(WebDriver driver) {
		By spinner = By.xpath("//app-custome-spinner//*[local-name()='svg']");
		
		try {
			new WebDriverWait(driver.getDriver(), Duration.ofSeconds(500))
					.until(ExpectedConditions.numberOfElementsToBe(spinner, 0));
			sleep(1000);
			ReportManager.log("Element matching this locator [" + spinner + "] disappeared.");
		} catch (TimeoutException e) {
			ReportManager.log(e.getMessage());
			ReportManager.log("Element matching this locator [" + spinner + "] still found");
		} catch (Exception e) {
			ReportManager.log(e.getMessage());
			ReportManager.log("Element matching this locator [" + spinner + "] still found");
		}

	}
	
	public static void waitForDropDownWithoutSpinner(WebDriver driver) {
		By spinner = By.xpath("//app-custome-spinner//*[local-name()='svg']");
		// WebElement spinnerOverlay= driver.getDriver().findElement(spinner);
		try {
			new WebDriverWait(driver.getDriver(), Duration.ofSeconds(5000))
					.until(ExpectedConditions.numberOfElementsToBe(spinner, 0));
			sleep(500);
			ReportManager.log("Element matching this locator [" + spinner + "] disappeared.");
		} catch (TimeoutException e) {
			ReportManager.log(e.getMessage());
			ReportManager.log("Element matching this locator [" + spinner + "] still found");
		} catch (Exception e) {
			ReportManager.log(e.getMessage());
			ReportManager.log("Element matching this locator [" + spinner + "] still found");
		}

	}
	
	public static void waitForSuccessMessageDisappeared(WebDriver driver) {
		By successMsg = By.xpath("//*[contains(@class,'ant-notification-notice-message')]");
		// WebElement spinnerOverlay= driver.getDriver().findElement(spinner);
		try {

			new WebDriverWait(driver.getDriver(), Duration.ofSeconds(500))
					.until(ExpectedConditions.invisibilityOfElementLocated(successMsg));
			sleep(1000);
			ReportManager.log("Element matching this locator [" + successMsg + "] disappeared.");
		} catch (TimeoutException e) {
			ReportManager.log(e.getMessage());
			ReportManager.log("Element matching this locator [" + successMsg + "] still found");
		} catch (Exception e) {
			ReportManager.log(e.getMessage());
			ReportManager.log("Element matching this locator [" + successMsg + "] still found");
		}

	}
	
	public static void waitForElementDisappeared(WebDriver driver,By element) {
		
		try {

			new WebDriverWait(driver.getDriver(), Duration.ofSeconds(500))
					.until(ExpectedConditions.numberOfElementsToBe(element,0));
			ReportManager.log("Element matching this locator [" + element + "] disappeared.");
		} catch (TimeoutException e) {
			ReportManager.log(e.getMessage());
			ReportManager.log("Element matching this locator [" + element + "] still found");
		} catch (Exception e) {
			ReportManager.log(e.getMessage());
			ReportManager.log("Element matching this locator [" + element + "] still found");
		}

	}
	
	public boolean checkElementsWithSameText(WebDriver driver,By elementsLocator,String text)
	{
		List<WebElement> statuses = driver.getDriver().findElements(elementsLocator);
		for (WebElement status : statuses) {
			if(!status.getText().contains(text))
			{
				return false;
			}
			
		}
		return true;
	}
	
	private By getFilterLocator(String status)
	{
		
		By filterLocator = By.xpath("//*[contains(@class,'ant-select-item-option') and @title='"+status+"']");
		return filterLocator;
	}

	public boolean filterByStatus(WebDriver driver ,String status,By filtersDiv,By pagination,By next,By statusLocator,int EndPageCount)
	{
		String nextDisabled;
		boolean allItemsWithSameStatus = false;
		waitForPageLoadWithoutSpinner(driver);
		driver.element().click(filtersDiv);
		driver.element().click(getFilterLocator(status));
		waitForPageLoadWithoutSpinner(driver);
		
		if(isElementExist(driver,pagination,"pagination"))
		{
			 nextDisabled = driver.element().getAttribute( next, "aria-disabled");
			 int counter =1;
						
				while(nextDisabled.equals("false") && counter <= EndPageCount)
				{
					driver.element().click(next);
					waitForPageLoadWithoutSpinner(driver);
					allItemsWithSameStatus=checkElementsWithSameText(driver,statusLocator,status);
					if(!allItemsWithSameStatus)
					{
						ReportManager.log("Not all the Items  in pages with status"+status);
						return allItemsWithSameStatus;
					}
					nextDisabled = driver.element().getAttribute(next, "aria-disabled");
					counter = counter +1;
				}
		}
		else {
			allItemsWithSameStatus= checkElementsWithSameText(driver,statusLocator,status);
		}
		return allItemsWithSameStatus;
		
	}
	
	
	
	 public void uploadFile(WebDriver driver,String fileName, By uploadLocator) {   
			String FilePath = System.getProperty("testUploadFilesPath");
			String file = FileActions.getInstance().getAbsolutePath(FilePath, fileName);
			driver.element().typeFileLocationForUpload(uploadLocator,file);
	    }
	 
	 public void fillSocialhMap(WebDriver driver,Map<String, String> socialValues) {
		// using for-each loop for iteration over Map.entrySet()
	        for (Map.Entry<String,String> entry : socialValues.entrySet()) {
	        	  
	        By locator = By.xpath("(//p[text()="+"'" + entry.getKey()+"'"+"]//following::button)[1]");
			String EnteredValue = entry.getValue();
			driver.element().click( locator);
			driver.element().type(socialURL, EnteredValue);
			driver.element().click( submitSocial);
	    }
		 
		}
	 
	 
	 public boolean checkAddedSocialMedia(WebDriver driver, Map<String, String> socialValues) {
		   int i=0;
		 boolean status= false;
		 for (Map.Entry<String,String> entry : socialValues.entrySet()){
			 By addedMedia= By.xpath("(//h5[text()="+"'" + entry.getKey()+"'"+"]//following::a)[1]");
				ReportManager.log( entry.getKey()+ " is exist and visible");

			  driver.element().click(addedMedia);
			  switchingBetweenTabs(driver,i+1);
			 if( driver.getDriver().getTitle().contains(entry.getValue())){
				 status= true;
			 }else {
				 status= false;
			 }
			 switchingBetweenTabs(driver,0);
		   }
		 return status;
	 }
	 
	 public By searchOnItemInList(WebDriver driver,By searchInput,By itemRow,String ItemName,By itemTitleLocator,String expected) {
			int size= 0;
			int i =5;
			waitForPageLoadWithoutSpinner(driver);
			do {
				sleep(1000);
				driver.element().type(searchInput, ItemName);
				waitForPageLoadWithoutSpinner(driver);
				sleep(2000);
				size=Integer.valueOf(driver.getDriver().findElements(itemRow).size());
				i=i-1;
			}
			while(size>1 && i > 0);
			if (isMsgExistandDisplayed(driver, itemTitleLocator, expected))
				return itemTitleLocator;
			return null;
		
		}
	 
	 public WebElement searchOnItemInListByStatus(WebDriver driver,By searchInput,By itemRow,String ItemName,By itemTitleLocator,String expected) {
			List<WebElement> itemTitles;
			waitForPageLoadWithoutSpinner(driver);
			sleep(5000);
				driver.element().type(searchInput, ItemName);
				waitForPageLoadWithoutSpinner(driver);
				sleep(5000);
				itemTitles=driver.getDriver().findElements(itemTitleLocator);
				for(WebElement itemTitle:itemTitles)
				{
			       if (itemTitle.getText().contains(expected))
				return itemTitle;
				}
			return null;
		
		}
	 
	 public boolean openFirstItemInList(WebDriver driver)
	 {   
		 By itemRow =By.xpath("(//div[contains(@class,'card')])[1]");
		 By headerLocatorExpected =By.xpath("(//div[contains(@class,'card-header')])[1]");
		 driver.element().click(itemRow);
		 waitUntilElementPresent(driver, headerLocatorExpected);
		 return true;
	 }
	 
	 public boolean searchWithName(WebDriver driver,String name) {		
			By cardTitle= By.xpath("//*[@title="+"'"+name+"'"+"]");
			driver.element().type(search, name);
			waitUntilElementPresent(driver, cardTitle);
		 return  isElementExist(driver, cardTitle, name);
		}
	
}
