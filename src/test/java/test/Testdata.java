package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.prof.utility.Utility;

import filloreader.FilloReader;
import screenshot.ScreenShot;

public class Testdata extends Utility{
	
	private WebDriver driver;

	public Testdata(){
		
	driver=new Utility().driver;
	
	}
	
public static final Logger log = Logger.getLogger(Test.class.getName());
	
	ScreenShot ss;
	List<Map<String, String>> pram = new ArrayList<Map<String, String>>();    
    String RunMode="";
	String filepath=System.getProperty("user.dir") + "\\src\\main\\java\\datamanager\\";
	String driverpath=System.getProperty("user.dir") + "\\Resources\\Drivers\\";
	FilloReader fillo;
	Utility util=new Utility();

	
	@BeforeTest
	public void setup() throws Exception {
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		System.setProperty("webdriver.chrome.driver", driverpath +"chromedriver.exe");
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			driver.get("http://www.testyou.in/Login.aspx");
			
			pram=new FilloReader().getTestDataInListMap(new File("C:\\Users\\naveen.kumar\\eclipse-workspace\\pfz\\src\\main\\java\\datamanager\\loginData.xlsx"),"login", "select * from %s where Runmode='Yes'"); 
			
			for (Map<String, String> data : pram) {
				
			String email_id=data.get("Email");
			driver.findElement(By.name("ctl00$CPHContainer$txtUserLogin")).sendKeys(email_id);
			log.info(email_id);
		
			String password=data.get("Password");
			driver.findElement(By.name("ctl00$CPHContainer$txtPassword")).sendKeys(password);
			log.info(password);
			
			driver.findElement(By.name("ctl00$CPHContainer$btnLoginn")).click();
			util.setUp();
			}
	}
	
	@Test
	public void UpdatingData() throws Exception {
		log.info("This is a Test");
		ss = new ScreenShot();
		pram=new FilloReader().getTestDataInListMap(new File("C:\\Users\\naveen.kumar\\eclipse-workspace\\pfz\\src\\main\\java\\datamanager\\loginData.xlsx"),"data", "select * from %s where Runmode='Yes'"); 
		for (Map<String, String> data : pram) {
			try {
			test=extent.createTest("Testing Testyou application");

			driver.findElement(By.xpath("//div[@class='edit_link']")).click();
			
			String first_name=data.get("Fname");
			if(first_name != null && !first_name.isEmpty()) {
			driver.findElement(By.name("ctl00$CPHContainer$txtFirstName")).clear();
			driver.findElement(By.name("ctl00$CPHContainer$txtFirstName")).sendKeys(first_name);
			log.info(first_name);
			test.pass("Entring the First Name");
			}
			
			String last_name=data.get("Lname");
			if(last_name != null && !last_name.isEmpty()) {
			driver.findElement(By.name("ctl00$CPHContainer$txtLastName")).clear();
			driver.findElement(By.name("ctl00$CPHContainer$txtLastName")).sendKeys(last_name);
			log.info(last_name);
			test.pass("Entring the Last Name");

			}
			
			String city=data.get("City");
			try {
			if(city != null && !city.isEmpty()) {
			driver.findElement(By.id("ctl00_CPHContainer_txtCity")).clear();
			driver.findElement(By.id("ctl00_CPHContainer_txtCity")).sendKeys(city);
			log.info(city);
			passFailScreenshot(driver,"FinFlowz data");
			test.pass("Entering the city name");

			}
			
			}catch(NoSuchElementException e) {
				passFailScreenshot(driver,"FinFlowz data");
				test.fail("Entering the city name failed");

			}
			
			String state=data.get("State");
			if(state != null && !state.isEmpty()) {
			driver.findElement(By.name("ctl00$CPHContainer$txtState")).clear();
			driver.findElement(By.name("ctl00$CPHContainer$txtState")).sendKeys(state);
			log.info(state);
			test.pass("Entering the state name");

			}
			
			
			String college=data.get("College");
			try {
			if(college != null && !college.isEmpty()) {
			driver.findElement(By.id("ctl00_CPHContainer_txtCollege1")).clear();
			driver.findElement(By.id("ctl00_CPHContainer_txtCollege")).sendKeys(college);
			log.info(college);
			test.pass("Entering the college name");
			}
			}catch(NoSuchElementException e) {
				test.fail("Entering the college name failed");

			}
			
			
			driver.findElement(By.name("ctl00$CPHContainer$btnUpdateProfile")).click();
			driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
			ss.CaptureScreenShot(driver, data.get("TestCase ID") + "-Testcase");
			
			passFailScreenshot(driver,"FinFlowz data");
			test.pass("Execution Pass");

			}
			catch(Exception e) {
				ss.CaptureScreenShot(driver, data.get("TestCase ID") + "-Testcase");
				WebElement ele=driver.findElement(By.xpath("//a[contains(text(),'Home')]"));
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
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_headerTopStudent_lnkbtnSignout")));
		extent.flush();

		driver.close();
	}
}