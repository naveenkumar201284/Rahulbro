package com.prof.utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class Utility {
	
	public  WebDriver driver;
	
	public static ExtentReports extent;
	public static ExtentHtmlReporter reporter;
	public static ExtentTest test;
	public static ExtentTest parentTest;

	String concatenate =".";
	
	
	public void setUp() {
		reporter = new ExtentHtmlReporter("C:\\Users\\naveen.kumar\\eclipse-workspace\\profinchFinflowz\\reports\\Extent_"+ getcurrentdateandtime() + ".html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		reporter.config().setDocumentTitle("Finflowz");
		reporter.config().setReportName("Naveen kumar");
		reporter.config().setTestViewChartLocation(ChartLocation.TOP);
		reporter.config().setTheme(Theme.STANDARD);
		
		extent.setSystemInfo("Environment", "AutomationTesting");
		extent.setSystemInfo("HostAddress", "TestYou");
		extent.setSystemInfo("Browser", "GoogleChrome");
		extent.setSystemInfo("Application_URL", "http://www.testyou.in/Login.aspx");
	}
	
	
	public static String getcurrentdateandtime() {
		String str = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
			Date date = new Date();
			str = dateFormat.format(date);
			str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
		} catch (Exception e) {
		}
		return str;
	}
	
	
	public void passFailScreenshot(WebDriver driver,String name) throws IOException {
		String screenshotName=concatenate + AppScreenshots.getScreenshot(driver,name);
	//	String scenario_name=concatenate + ScreenShot.CaptureScreenShot(driver, scenario_name);
		screenCapture("TestYou",screenshotName);
	}

	
	public static Object screenCapture(String logdetails, String imagepath) throws IOException {
		test.log(Status.INFO, logdetails,MediaEntityBuilder.createScreenCaptureFromPath(imagepath).build());
		return test;
	}
		
}
