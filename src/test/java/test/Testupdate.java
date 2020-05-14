package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.prof.utility.Utility;

import filloreader.FilloReader;
import screenshot.ScreenShot;
import testbase.TestBase;

public class Testupdate extends Utility{
public static final Logger log = Logger.getLogger(Test3.class.getName());
	
	ScreenShot ss;
	List<Map<String, String>> pram = new ArrayList<Map<String, String>>();    
    String RunMode="";
	String filepath=System.getProperty("user.dir") + "\\src\\main\\java\\datamanager\\";
	FilloReader fillo;
	Utility util=new Utility();
	//String email_id;
	public String email_id;
	public String logTestCaseID;
	public String account;
	@Test
	public void setup() throws Exception {
		// System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chrome\\chromedriver.exe");
		// System.setProperty("webdriver.chrome.driver", "C:\\Users\\naveen.kumar\\eclipse-workspace\\pfz\\Resources\\Drivers\\chromedriver.exe");
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		System.setProperty("webdriver.chrome.driver","C:\\Users\\naveen.kumar\\Desktop\\chrome81\\chromedriver.exe");
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			
			pram=new FilloReader().getTestDataInListMap(new File("C:\\Users\\naveen.kumar\\eclipse-workspace\\profinchFinflowz\\src\\main\\java\\datamanager\\loginData.xlsx"),"login", "select * from %s"); 

		//	int i=0;
			for (Map<String, String> data : pram) {
				driver.get("http://www.testyou.in/Login.aspx");

			//	logTestCaseID=data.get("TCNumber");
				
				/*if(i>=1)
					UpdatingData();
					
				else {*/
							
			//	if (email_id != null && !email_id.isEmpty()) {
				email_id=data.get("Email");

			driver.findElement(By.name("ctl00$CPHContainer$txtUserLogin")).sendKeys(email_id);
			log.info(email_id);
			
		
			String password=data.get("Password");
			driver.findElement(By.name("ctl00$CPHContainer$txtPassword")).sendKeys(password);
			log.info(password);
			

			Thread.sleep(5000);
			driver.findElement(By.xpath("//input[@id='ctl00_CPHContainer_btnLoginn']")).click();
			util.setUp();
			
				//}
				
				//else 
				
					UpdatingData();
				//	i=i+1;
					email_id=data.get("Email");
				//}	
			}
			
	}
	
	public void UpdatingData() throws Exception {
		log.info("This is a Test");
		
		String TestCaseID = null;
		ss = new ScreenShot();
		pram=new FilloReader().getTestDataInListMap(new File("C:\\Users\\naveen.kumar\\eclipse-workspace\\profinchFinflowz\\src\\main\\java\\datamanager\\loginData.xlsx"),"data", "select * from %s where Runmode='Yes'"); 
		for (Map<String, String> data : pram) {
			try {
			test=extent.createTest("Testing Testyou application");
			int i=0;
			
			if(data.get("Action").equalsIgnoreCase("New")) {
			
			driver.findElement(By.xpath("//div[@class='edit_link']")).click();
			
			TestCaseID=data.get("TestCaseID");
			
			//if(i<=1) {

			
			String first_name=data.get("Fname");
			if(first_name != null && !first_name.isEmpty()) {
			driver.findElement(By.name("ctl00$CPHContainer$txtFirstName")).clear();
			driver.findElement(By.name("ctl00$CPHContainer$txtFirstName")).sendKeys(first_name);
			log.info(first_name);
			test.pass("Entring the First Name");
			}
			
			account = driver.findElement(By.name("ctl00$CPHContainer$txtFirstName")).getAttribute("value");

			
			String last_name=data.get("Lname");
			if(last_name != null && !last_name.isEmpty()) {
			driver.findElement(By.name("ctl00$CPHContainer$txtLastName")).clear();
			driver.findElement(By.name("ctl00$CPHContainer$txtLastName")).sendKeys(last_name);
			log.info(last_name);
			test.pass("Entring the Last Name");

			}
			
			
			//if(TestCaseID.equalsIgnoreCase(logTestCaseID)) {
			//	System.setProperty("ROW", "1");//Table start row
		//		System.setProperty("COLUMN", "1");//Table start column
				Fillo fillo=new Fillo();
				Connection connection=fillo.getConnection("C:\\Users\\naveen.kumar\\eclipse-workspace\\profinchFinflowz\\src\\main\\java\\datamanager\\loginData.xlsx");
				
				String strQuery="Update data Set City="+"'"+account+"'"+" where TestCaseID="+"'"+TestCaseID+"'";
				
				connection.executeUpdate(strQuery);
				connection.close();
				
				pram=new FilloReader().getTestDataInListMap(new File("C:\\Users\\naveen.kumar\\eclipse-workspace\\profinchFinflowz\\src\\main\\java\\datamanager\\loginData.xlsx"),"data", "select * from %s where Runmode='Yes'"); 

		//	}
			
			
				
				String city=pram.get(0).get("City");
				
				if (city != null && !city.isEmpty())
				{
					
				driver.findElement(By.id("ctl00_CPHContainer_txtCity")).clear();
				driver.findElement(By.id("ctl00_CPHContainer_txtCity")).sendKeys(city);
				log.info(city);
				test.pass("Entering the state name");
				}
				
				else
				{
					driver.findElement(By.id("ctl00_CPHContainer_txtCity")).sendKeys(account);

				}
			
			
			
			String state=data.get("State");
			if(state != null && !state.isEmpty()) {
			driver.findElement(By.name("ctl00$CPHContainer$txtState")).clear();
			driver.findElement(By.name("ctl00$CPHContainer$txtState")).sendKeys(state);
		

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
			ss.CaptureScreenShot(driver, data.get("TestCaseID") + "-Testcase");
			
			passFailScreenshot(driver,"Testing you");
			test.pass("Execution Pass");
			//}
			i=i+1;

			}
			
			else 	if(data.get("Action").equalsIgnoreCase("Delete")) {
				
				
				driver.findElement(By.xpath("//div[@class='edit_link']")).click();
				
				TestCaseID=data.get("TestCaseID");
				
				//if(i<=1) {

				
				String first_name=data.get("Fname");
				if(first_name != null && !first_name.isEmpty()) {
				driver.findElement(By.name("ctl00$CPHContainer$txtFirstName")).clear();
				driver.findElement(By.name("ctl00$CPHContainer$txtFirstName")).sendKeys(first_name);
				log.info(first_name);
				test.pass("Entring the First Name");
				}
				
				driver.findElement(By.name("ctl00$CPHContainer$btnUpdateProfile")).click();
				driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
			}

		}
			catch(Exception e) {
				ss.CaptureScreenShot(driver, data.get("TestCase ID") + "-Testcase");
				WebElement ele=driver.findElement(By.xpath("//a[contains(text(),'Home')]"));
				ele.click();
				passFailScreenshot(driver,"Testing you");
				test.fail("Execution failed");
			    extent.flush();
			continue;

			}
		
		
		}
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_headerTopStudent_lnkbtnSignout"))).click();
		extent.flush();


	}
	
	@AfterTest
	public void cleanup() throws Exception {
	
		// Xl_Reader("");
		driver.close();
	}
}