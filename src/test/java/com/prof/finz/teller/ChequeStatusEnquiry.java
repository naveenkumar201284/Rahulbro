package com.prof.finz.teller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.prof.utility.Utility;

import filloreader.FilloReader;
import screenshot.ScreenShot;

public class ChequeStatusEnquiry extends Utility{
	
	private WebDriver driver;
	
	public ChequeStatusEnquiry(){
		driver=new Utility().driver;
	}
public static final Logger log = Logger.getLogger(BillPaymentByCash.class.getName());
	
	ScreenShot ss;
	List<Map<String, String>> pram = new ArrayList<Map<String, String>>();    
	String filepath=System.getProperty("user.dir") + "\\src\\main\\java\\datamanager\\";
	String driverpath=System.getProperty("user.dir") + "\\Resources\\Drivers\\";
	FilloReader fillo;
	
	@BeforeTest
	public void setup() throws Exception {
		
		
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		System.setProperty("webdriver.chrome.driver","C:\\Users\\naveen.kumar\\Desktop\\chrome81\\chromedriver.exe");
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	
			driver.get("http://192.168.4.84:8099/FinFlowz/#/portal/landing-page");
			
			pram=new FilloReader().getTestDataInListMap(new File(filepath + "loginData.xlsx"),"FinFlowLogin", "select * from %s"); 

			for (Map<String, String> data : pram) {
				
			String username=data.get("Username");
			driver.findElement(By.name("username")).sendKeys(username);
			log.info(username);
			
			
			String password=data.get("Password");
			driver.findElement(By.name("password")).sendKeys(password);
			log.info(password);
			driver.findElement(By.xpath("//span[contains(text(),'Login')]")).click();
		}
	}
	
	@Test
	public void UpdatingData() throws Exception {
		log.info("This is a test. Don't worry.");
		ss = new ScreenShot();
		pram=new FilloReader().getTestDataInListMap(new File(filepath + "loginData.xlsx"),"FinFlowData", "select * from %s where Runmode='Yes'"); 
		for (Map<String, String> data : pram) {
			try {
			test=extent.createTest("Testing Testyou application");

			driver.findElement(By.xpath("//div[@class='apptitle'][contains(text(),'Teller')]']")).click();
			log.info("Clicked on teller button icon");
			
			driver.findElement(By.xpath("//mat-icon[contains(text(),'menu')]")).click();
			log.info("Clicked on Menu icon");
			
			 if(data.get("Action").equalsIgnoreCase("ChequeStatusEnquiry")) {
					driver.findElement(By.xpath("//div[contains(text(),'Cheque status enquiry')]")).click();
					log.info("Clicked on Cheque status enquiry");
					
					driver.findElement(By.name("accountNumber")).sendKeys("");
					driver.findElement(By.xpath("//input[@id='control3155']")).sendKeys("");
					driver.findElement(By.xpath("//span[contains(text(),'Search')]")).click();
				}
			//error pop up "//notifier-container/ul/li"  or "//p[contains(text(),'E_FORM_ERROR - Form has errors!')]"
		
			ss.CaptureScreenShot(driver, data.get("TestCase ID"));

			}
			catch(Exception e) {
				ss.CaptureScreenShot(driver, data.get("TestCase ID"));
				passFailScreenshot(driver,"Testing you");
				test.fail("Execution failed");
			    extent.flush();

			continue;

			}
		}
	}

	
	public void teardown() {
		driver.close();
	}

}

