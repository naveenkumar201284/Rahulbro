package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.prof.utility.Utility;
import filloreader.FilloReader;
import screenshot.ScreenShot;

public class Test3 extends Utility{
	private Properties props;
	String propertyFilePath=System.getProperty("user.dir") + "\\Resources\\ConfigFiles\\TestYouLogin.properties";
	FileReader reader=null;
	private WebDriver driver;
	
	public Test3(){
		driver=new Utility().driver;
		try {
			reader = new FileReader(propertyFilePath);
			props = new Properties();
			try {
				props.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}		
	}
	
	
	public static final Logger log = Logger.getLogger(Test3.class.getName());
	
	ScreenShot ss;
	List<Map<String, String>> pram = new ArrayList<Map<String, String>>();    
	String filepath=System.getProperty("user.dir") + "\\src\\main\\java\\datamanager\\";
	String driverpath=System.getProperty("user.dir") + "\\Resources\\Drivers\\";
	FilloReader fillo;
	Utility util=new Utility();
	
	
	
	@BeforeTest
	@Parameters("myBrowser")
	public void getBrowser(String myBrowser) throws Exception {
		if (System.getProperty("os.name").contains("Window")) {
			if (myBrowser.equalsIgnoreCase("firefox")) {
				// https://github.com/mozilla/geckodriver/releases
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.gecko.driver","C:\\Users\\naveen.kumar\\Desktop\\Data\\geckodriver.exe");
				driver = new FirefoxDriver();
							
		
			} else if (myBrowser.equalsIgnoreCase("chrome")) {
				System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
				System.setProperty("webdriver.chrome.driver", driverpath +"chromedriver.exe");
				driver = new ChromeDriver();
			
				
			}
		
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.get(props.getProperty("URL"));
			
			pram=new FilloReader().getTestDataInListMap(new File("C:\\Users\\naveen.kumar\\eclipse-workspace\\profinchFinflowz\\src\\main\\java\\datamanager\\loginData.xlsx"),"login", "select * from %s"); 



			for (Map<String, String> data : pram) {
				
			String email_id=data.get("Email");
			driver.findElement(By.name(props.getProperty("Emailid"))).sendKeys(email_id);
			log.info(email_id);
			
			
			String password=data.get("Password");
			driver.findElement(By.name(props.getProperty("Password"))).sendKeys(password);
			log.info(password);
			driver.findElement(By.name(props.getProperty("ClickOnLogin"))).click();
						
			util.setUp();
			}
		}
	}
	@Test
	public void UpdatingData1() throws Exception {
		log.info("This is a test. Don't worry.");
		ss = new ScreenShot();
		//pram=new FilloReader().getTestDataInListMap(new File(filepath + "loginData.xlsx"),"data", "select * from %s where Runmode='Yes'"); 
		//pram=new FilloReader().getTestDataInListMap(new File(props.getProperty("ExcelPath")),"data", "select * from %s where Runmode='Yes'"); 
		pram=new FilloReader().getTestDataInListMap(new File("C:\\Users\\naveen.kumar\\eclipse-workspace\\profinchFinflowz\\src\\main\\java\\datamanager\\loginData.xlsx"),"data", "select * from %s where Runmode='Yes'"); 

		for (Map<String, String> data : pram) {
			try {
			test=extent.createTest("Testing TestYou Application");

			driver.findElement(By.xpath(props.getProperty("EditUser"))).click();
			
			String first_name=data.get("Fname");
			if(first_name != null && !first_name.isEmpty()) {
			driver.findElement(By.name(props.getProperty("FirstName"))).clear();
			driver.findElement(By.name(props.getProperty("FirstName"))).sendKeys(first_name);
			//driver.findElement(By.xpath("//input[contains(@id,'test_')]"));
			log.info(first_name);
			passFailScreenshot(driver,"FinFlowz data");
			test.pass("Entring the First Name");
			}
			
			String last_name=data.get("Lname");
			if(last_name != null && !last_name.isEmpty()) {
			driver.findElement(By.name(props.getProperty("LastName"))).clear();
			driver.findElement(By.name(props.getProperty("LastName"))).sendKeys(last_name);
			log.info(last_name);
			passFailScreenshot(driver,"FinFlowz data");
			test.pass("Entring the Last Name");
			}
			
			String city=data.get("City");
			if(city != null && !city.isEmpty()) {
			driver.findElement(By.id(props.getProperty("City"))).clear();
			driver.findElement(By.id(props.getProperty("City"))).sendKeys(city);
			log.info(city);
			passFailScreenshot(driver,"FinFlowz data");
			test.pass("Entring the City");
			
			}
			
			String state=data.get("State");
			if(state != null && !state.isEmpty()) {
			driver.findElement(By.name(props.getProperty("State"))).clear();
			driver.findElement(By.name(props.getProperty("State"))).sendKeys(state);
			log.info(state);
			passFailScreenshot(driver,"FinFlowz data");
			test.pass("Entring the State");

			}			
			
			driver.findElement(By.name(props.getProperty("Update"))).click();
			test.pass("Click on the Update");
			
			driver.findElement(By.xpath(props.getProperty("HomeButton"))).click();
			test.pass("Click on the HomeButton");

			ss.CaptureScreenShot(driver, data.get("TestCase ID"));
			
			passFailScreenshot(driver,"FinFlowz data");
			test.pass("Execution Pass");
		    extent.flush();
	
			}
			catch(Exception e) {
				ss.CaptureScreenShot(driver, data.get("TestCase ID"));
				WebElement ele=driver.findElement(By.xpath(props.getProperty("HomeButton")));
				ele.click();
				passFailScreenshot(driver,"FinFlowz data");
				test.fail("Execution failed");
			    extent.flush();
			continue;

			}
		}
	}
	
	@AfterTest
	public void cleanup() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(props.getProperty("Logout"))));
		extent.flush();
		driver.close();
	}
}