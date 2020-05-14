package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import filloreader.FilloReader;
import screenshot.ScreenShot;

public class Teller {
	
	public static final Logger log = Logger.getLogger(Test1.class.getName());
	
	ScreenShot ss;
	List<Map<String, String>> pram = new ArrayList<Map<String, String>>();    
	String filepath=System.getProperty("user.dir") + "\\src\\main\\java\\datamanager\\";
	String driverpath=System.getProperty("user.dir") + "\\Resources\\Drivers\\";
	FilloReader fillo;
	static WebDriver driver;
	
	@BeforeTest
	public void setup() throws Exception {
		System.setProperty("webdriver.chrome.driver", driverpath +"chromedriver.exe");
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

			driver.findElement(By.xpath("//div[@class='apptitle'][contains(text(),'Teller')]']")).click();
			log.info("Clicked on teller button icon");
			
			driver.findElement(By.xpath("//mat-icon[contains(text(),'menu')]")).click();
			log.info("Clicked on Menu icon");
			
			
			if(data.get("Action").equalsIgnoreCase("BillPaymentByCash")) {
			
			driver.findElement(By.xpath("//div[contains(text(),'Bill payment by cash')]")).click();
			log.info("Clicked on Bill Payment By Cash");
			
			
			//Bill Details
			String billcat=data.get("BillCategory");
			if(billcat != null && !billcat.isEmpty()) {
			driver.findElement(By.name("billerCategory")).sendKeys(billcat);
			//driver.findElement(By.xpath("//input[contains(@id,'test_')]"));
			log.info(billcat);
			}
			
			driver.findElement(By.name("relationshipNumber")).sendKeys("12355");
			driver.findElement(By.name("billNumber")).sendKeys("");
			driver.findElement(By.name("billCurrencyCode")).sendKeys("");
			driver.findElement(By.name("billAmount")).sendKeys("");
			driver.findElement(By.name("billAmountPaid")).sendKeys("");
			driver.findElement(By.name("remarks")).sendKeys("");
			
			driver.findElement(By.name("NextBtn")).click();

			//Charges
			
			driver.findElement(By.xpath("")).click();
			driver.findElement(By.name("NextBtn")).click();

			//cash
			driver.findElement(By.name("cashCurrencyCode")).sendKeys("USD");
			driver.findElement(By.name("remarks")).sendKeys("ok");
			driver.findElement(By.name("NextBtn")).click();
			log.info("Click on NextButton");
			
			//Document
			driver.findElement(By.name("NextBtn")).click();
			
			//summary
			
			driver.findElement(By.xpath("//mat-icon[contains(text(),'save')]")).click();//save
			//or
			driver.findElement(By.xpath("//mat-icon[contains(text(),'drafts')]")).click();//draft
			//or
			driver.findElement(By.xpath("//mat-icon[contains(text(),'delete_outline')]")).click();
			
			}
			
			else  if(data.get("Action").equalsIgnoreCase("Bookoverage")) {
				
				driver.findElement(By.xpath("//div[contains(text(),'Book overage')]")).click();
				log.info("Clicked on Book Overage");
				
				//Detail screen
				driver.findElement(By.name("cashCurrencyCode")).sendKeys("");
				driver.findElement(By.xpath("//input[@id='control1395']")).sendKeys("");//amount field
				
				driver.findElement(By.name("NextBtn")).click();
				//summary
				
				driver.findElement(By.xpath("//mat-icon[contains(text(),'save')]")).click();//save
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'drafts')]")).click();//draft
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'delete_outline')]")).click();
				

			}
			
			
			else  if(data.get("Action").equalsIgnoreCase("Bookshortage")) {

				driver.findElement(By.xpath("//div[contains(text(),'Cash deposit')]")).click();
				log.info("Clicked on Cash Deposit");
				
				//Detail screen
				driver.findElement(By.name("cashCurrencyCode")).sendKeys("");
				driver.findElement(By.xpath("//input[@id='control1529']")).sendKeys("");//amount field
				
				driver.findElement(By.name("NextBtn")).click();
				//summary
				
				driver.findElement(By.xpath("//mat-icon[contains(text(),'save')]")).click();//save
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'drafts')]")).click();//draft
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'delete_outline')]")).click();
				
				
			}

			else  if(data.get("Action").equalsIgnoreCase("CashDeposit")) {
				driver.findElement(By.xpath("//div[contains(text(),'Cash deposit')]")).click();
				log.info("Clicked on Cash Deposit");
				//Account detail
				driver.findElement(By.name("accountNumber")).sendKeys("");
				driver.findElement(By.xpath("//input[@id='control2186']")).sendKeys("");//amount field
				driver.findElement(By.name("slipNumber")).sendKeys("");
				driver.findElement(By.name("remarks")).sendKeys("");
				driver.findElement(By.xpath("//mat-select[@name='signatureVerified']")).sendKeys("Yes"); //Signature Verify
				
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//Charges 
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//cash
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//Document Upload
				
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//Summary

				driver.findElement(By.xpath("//mat-icon[contains(text(),'save')]")).click();//save
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'drafts')]")).click();//draft
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'delete_outline')]")).click();
				
				
			}
			
			else  if(data.get("Action").equalsIgnoreCase("CashWithDrawal")) {
				
				driver.findElement(By.xpath("//div[contains(text(),'Cash deposit')]")).click();
				log.info("Clicked on Cash Deposit");
				//Deposit Account
				driver.findElement(By.name("accountNumber")).sendKeys("");
				driver.findElement(By.xpath("//input[@id='control2789']")).sendKeys("");//amount field
				driver.findElement(By.name("slipNumber")).sendKeys("");
				driver.findElement(By.name("remarks")).sendKeys("");
				driver.findElement(By.xpath("//mat-select[@name='signatureVerified']")).sendKeys("Yes"); //Signature Verify
				
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//Charges 
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//cash
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				driver.findElement(By.name("remarks")).sendKeys("");
				
				//Document upload
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//Summary
				driver.findElement(By.name("remarks")).sendKeys("");
				driver.findElement(By.xpath("//mat-icon[contains(text(),'save')]")).click();//save
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'drafts')]")).click();//draft
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'delete_outline')]")).click();

			}

			else  if(data.get("Action").equalsIgnoreCase("ChequeStatusEnquiry")) {
				driver.findElement(By.xpath("//div[contains(text(),'Cheque status enquiry')]")).click();
				log.info("Clicked on Cash Deposit");
				
				driver.findElement(By.name("accountNumber")).sendKeys("");
				driver.findElement(By.xpath("//input[@id='control3155']")).sendKeys("");
				driver.findElement(By.xpath("//span[contains(text(),'Search')]")).click();
			}

			else  if(data.get("Action").equalsIgnoreCase("ChequeWithDrawal")) {
				driver.findElement(By.xpath("//div[contains(text(),'Cheque withdrawal')]")).click();
				log.info("Clicked on Chaque Withdrawl");
				driver.findElement(By.name("accountNumber")).sendKeys("");
				driver.findElement(By.xpath("//input[@id='control3339']")).sendKeys("");
				
			}
			
			else  if(data.get("Action").equalsIgnoreCase("FXPurchaseByAccount")) {
				driver.findElement(By.xpath("//div[contains(text(),'FX Purchase By Account')]")).click();
				log.info("Clicked on FXPurchaseByAccount");
				driver.findElement(By.name("accountNumber")).sendKeys("");
				driver.findElement(By.xpath("//input[@id='control4096']")).sendKeys("");
				driver.findElement(By.name("beneficiaryName")).sendKeys("");
				driver.findElement(By.xpath("//mat-select[@name='residentType']")).sendKeys("Resident");
				driver.findElement(By.name("beneficiaryAddress")).sendKeys("");
				driver.findElement(By.name("passportNumber")).sendKeys("");
				driver.findElement(By.xpath("//mat-select[@name='signatureVerified']")).sendKeys("Yes"); //Signature Verify
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//charges
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//Cash
				driver.findElement(By.name("cashCurrencyCode")).sendKeys("");
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//Document upload
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//Summary

				driver.findElement(By.xpath("//mat-icon[contains(text(),'save')]")).click();//save
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'drafts')]")).click();//draft
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'delete_outline')]")).click();
								
			}

			else  if(data.get("Action").equalsIgnoreCase("GLtoGLTransfer")) {
				
				driver.findElement(By.xpath("//div[contains(text(),'GL to GL transfer')]")).click();
				log.info("Clicked on GL to GL Transfer");
				driver.findElement(By.name("glNumber")).sendKeys("");
				driver.findElement(By.name("glCurrencyCode")).sendKeys("");
				driver.findElement(By.xpath("//input[@id='control5235']")).sendKeys("");
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//Credit GL
				driver.findElement(By.name("glNumber")).sendKeys("");
				driver.findElement(By.name("glCurrencyCode")).sendKeys("");
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//Document Upload
				driver.findElement(By.name("NextBtn")).click();
				log.info("Click on NextButton");
				
				//summary 
				driver.findElement(By.xpath("//mat-icon[contains(text(),'save')]")).click();//save
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'drafts')]")).click();//draft
				//or
				driver.findElement(By.xpath("//mat-icon[contains(text(),'delete_outline')]")).click();
				
			}

				

			
			
			driver.findElement(By.xpath("//mat-select[@name='companyType']")).sendKeys("Public");//company type
			driver.findElement(By.name("companyName")).sendKeys("kumar"); //name of company
			driver.findElement(By.xpath("//mat-select[@name='typeOfOwnership']")).sendKeys("CP of legal person-ownership"); //Type Of Owernship
			driver.findElement(By.name("idType")).sendKeys("112111"); //ID type
			driver.findElement(By.name("idNumber")).sendKeys("23223");//ID number
			driver.findElement(By.name("registrationNumber")).sendKeys("kk1w2323");//Registration number
			driver.findElement(By.name("natureOfBusiness")).sendKeys("natureofbusiness");//nature of business
			driver.findElement(By.name("detailsOfActivity")).sendKeys("Banking");// Details of activity
			driver.findElement(By.name("capital")).sendKeys("profinch");//Capital
			driver.findElement(By.name("netWorth")).sendKeys("19888000"); //networth
			driver.findElement(By.name("trackLimit")).sendKeys("2343"); //TrackLimit
			driver.findElement(By.name("modeOfOperation")).sendKeys("");//Mode of operation
			driver.findElement(By.name("operationInstruction")).sendKeys("");//Operation Instruction
			driver.findElement(By.name("stateOfIncorporation")).sendKeys("");//state of incorporation
			driver.findElement(By.name("registrationCountry")).sendKeys("CD");//Country
			driver.findElement(By.name("currencyOfCountry")).sendKeys("USD");//Currencty of Country
			driver.findElement(By.name("nationalIdNumber")).sendKeys("Bang124343"); //National ID number
			driver.findElement(By.name("licenceNumber")).sendKeys("r345444"); //Licence number
			driver.findElement(By.name("licenceIssuedPlace")).sendKeys("Bangalore"); //Licence Issued Place
			driver.findElement(By.name("importExportLicenceNumber")).sendKeys("2342333"); //import/export lincence number
			driver.findElement(By.name("notarizedPlace")).sendKeys("bangalore");//notarizedPlace
			driver.findElement(By.name("detailsOfRegistrar")).sendKeys(""); //Details Of Registrar
			
			driver.findElement(By.name("NextBtn")).click();
			log.info("Click on NextButton");
			//Address 
			
			driver.findElement(By.name("correspondenceAddress1")).sendKeys("add1"); //Corrpondance address1
			driver.findElement(By.name("correspondenceAddress2")).sendKeys("add2");//Corrpondance address2
			driver.findElement(By.name("correspondenceAddress3")).sendKeys("add3");//Corrpondance address3
			driver.findElement(By.name("correspondenceState")).sendKeys("Karnataka"); //Corrpondance state
			driver.findElement(By.name("correspondenceCountry")).sendKeys("INDIA");//Corrpondance country
			driver.findElement(By.name("correspondenceCity")).sendKeys("Banglore");//Corrpondance city
			driver.findElement(By.name("correspondencePinCode")).sendKeys("560022");//Corrpondance pincode
			driver.findElement(By.name("correspondencePOBox")).sendKeys("JJR nagar");//Corrpondance PO BOX
			driver.findElement(By.xpath("//div[@class='mat-checkbox-inner-container']")).click();
			
			driver.findElement(By.name("registrationAddress1")).sendKeys("Regadd1"); //RegistrationAddress1
			driver.findElement(By.name("registrationAddress2")).sendKeys("Regadd2");
			driver.findElement(By.name("registrationAddress3")).sendKeys("Regadd3");
			driver.findElement(By.name("registrationState")).sendKeys("karnataka");
			driver.findElement(By.name("registrationCity")).sendKeys("Bangalore");
			driver.findElement(By.name("registrationPinCode")).sendKeys("560012");
			driver.findElement(By.name("registrationPOBox")).sendKeys("");
			driver.findElement(By.name("emailId")).sendKeys("naveenkumar@gamil.com");
			driver.findElement(By.name("mobileNo")).sendKeys("9866557566");
			driver.findElement(By.name("alternateMobileNo")).sendKeys("74151515232");
			
			//error pop up "//notifier-container/ul/li"  or "//p[contains(text(),'E_FORM_ERROR - Form has errors!')]"
			
			
			
			
			
			ss.CaptureScreenShot(driver, data.get("TestCase ID"));

			}
			catch(Exception e) {
				ss.CaptureScreenShot(driver, data.get("TestCase ID"));


			continue;

			}
		}
	}

}
