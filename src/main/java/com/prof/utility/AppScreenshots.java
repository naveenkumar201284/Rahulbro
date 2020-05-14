package com.prof.utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AppScreenshots extends Utility {
	
	public static String getScreenshot(WebDriver driver,String screenshotName) throws IOException {
		File sourceFile= ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String imagPath = "./reports/Screenshot/" +screenshotName+ getcurrentdateandtime()+".png";

	//	String imagPath ="C:/Users/naveen.kumar/Desktop/Data/screenshot/" + getcurrentdateandtime() + ".png";
		File path=new File(imagPath);
		FileUtils.copyFile(sourceFile, path);
		return  imagPath;
	}

}
