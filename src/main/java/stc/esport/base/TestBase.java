// TestBase
// This Class is used for all common Functions of the tests
//10-01-2023

package stc.esport.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;

import com.shaft.api.RestActions;
import com.shaft.cli.FileActions;
import com.shaft.driver.DriverFactory.DriverType;
import com.shaft.properties.internal.PropertyFileManager;
import com.shaft.driver.SHAFT;
import com.shaft.tools.io.ReportManager;
import com.shaft.tools.io.internal.ReportHelper;



public class TestBase {
 
	public static Properties properties;
	public SHAFT.GUI.WebDriver driver;
	//public WebDriver driver;
	public static RestActions apiObj;
   
	
	
	  
	
	static {
		     properties = ReadProperties("./src/test/resources/ESportProperties/ESport.properties");
	       } 
	
	

	public static Properties ReadProperties(String FilePath) {

		try {
			FileInputStream testProperties = new FileInputStream(FilePath);
			properties = new Properties();
			properties.load(new InputStreamReader(testProperties,Charset.forName("ISO-8859-1")));
			return properties;
		} catch (FileNotFoundException e) {
			System.out.println("property file not found " + e.getMessage());
		} catch (IOException e) {
			System.out.println("property file couldn't be loaded " + e.getMessage());
		}
		return properties;
	}
	
	public  void openBrowser() {
		System.out.println(" i am here to create log4j again before each test");
		 String fileName = "log4j.log";
		 String folderName="logs/";
		 String folderPath = (new File(folderName )).getAbsolutePath();
		// String file = FileActions.getInstance().getAbsolutePath(FilePath, "log4j.log");
		 System.out.println("Log file path is = "+ folderPath);
		 if(!(FileActions.getInstance().doesFileExist(folderPath+"/"+fileName)))
		 {
			 System.out.println(" i am here to create log4j again as it is deleted");
		 FileActions.getInstance().createFile(folderPath, fileName);
		 }
		  driver = new SHAFT.GUI.WebDriver(DriverType.CHROME,createCustomChOPs());
		  navigateHome();

	}
	
	
	public  void navigateHome() 
	{
		String url = properties.getProperty("homeUrl");
		
		ReportManager.log("URL:" + url );
  	   	driver.browser().navigateToURL(url);
		
		
		
	}
	public static void getBaseApiObj()
	{
		 String baseUrl=properties.getProperty("BaseUrl")+"api/";
		 apiObj=new RestActions(baseUrl);}
	
	
	public  void closeBrowser() {
		ReportManager.log("Closing Browser ... ");
		
		driver.quit();
	}

	private static ChromeOptions createCustomChOPs()
	{
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		ops.setCapability("webSocketUrl", false);
		//ops.addArguments("--incognito");
		/*boolean HEADLESS_EXECUTION = Boolean.valueOf(System.getProperty("headlessExecution").trim());
		boolean AUTO_MAXIMIZE      = Boolean.valueOf(System.getProperty("autoMaximizeBrowserWindow").trim());
		if (Boolean.TRUE.equals(HEADLESS_EXECUTION) ) 
		{ops.addArguments("window-size=1920,1080");}
		else
		{
			if (Boolean.TRUE.equals(AUTO_MAXIMIZE) ) 
			{ops.addArguments("--start-maximized");}
		}
		ops.addArguments("--enable-automation");
		ops.addArguments("--disable-logging");
		ops.addArguments("--no-sandbox");             
		ops.setHeadless(HEADLESS_EXECUTION);
		//ops.addArguments("--ignore-certificate-errors");
		 Map<String, Object> chromePreferences = new HashMap<>();
	        chromePreferences.put("profile.default_content_settings.popups", 0);
	        chromePreferences.put("download.prompt_for_download", "false");
	       
	        ops.setExperimentalOption("prefs", chromePreferences);*/
		 //String fileName = System.getProperty("appender.file.fileName");
		 
		
		
		return ops;
	}
   

	//@BeforeTest(description = "test Setup", alwaysRun = true)
    /*public void testSetup(ITestContext testContext) {
		System.out.println(" i am here to create log4j again before each test");
		 String FilePath = "logs/";
		 String file = FileActions.getInstance().getAbsolutePath(FilePath, "log4j.log");
		 System.out.println("Log file path is = "+ file);
		 if(!(FileActions.getInstance().doesFileExist(file)))
		 {
			 System.out.println(" i am here to create log4j again as it is deleted");
		 FileActions.getInstance().createFile(FilePath, file);
		 }
		 
    }*/

}