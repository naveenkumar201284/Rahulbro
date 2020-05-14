package com.prof.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ProfinchCommon {
	
	private WebDriver driver;
	private WebElement element;
	private Actions actions;
	private WebDriverWait waitDriver;
	private JavascriptExecutor jsExecutor;
	
	public void postConstruct() {
		actions = new Actions(driver);
		waitDriver = new WebDriverWait(driver, 5);
		jsExecutor = (JavascriptExecutor) driver;
	}
	
	public void focusToElement(WebElement element) {
		actions.moveToElement(element).perform();
	}
	
	private boolean checkElementNValue(String elementId, String value) {
		if ((value != null && !value.equalsIgnoreCase("")) && (elementId != null && !elementId.equalsIgnoreCase(""))) {
			return true;
		}
		return false;
	}
	
	public boolean validInput(String value) {
		if (value != null && !value.equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}
	
	
	public void populateTextById(String elementId, String text) {
		if (checkElementNValue(elementId, text)) {
			element = driver.findElement(By.id(elementId));
			focusToElement(element);
			if (validInput(element.getAttribute("value"))) {
				element.click();
				element.clear();
				element.sendKeys(text);
				element.sendKeys(Keys.TAB);
			} else {
				element.click();
				element.sendKeys(text);
				element.sendKeys(Keys.TAB);
			}
		}
	}
	
	
	public void populateTextByname(String elementname, String text) {
		if (checkElementNValue(elementname, text)) {
			element = driver.findElement(By.name(elementname));
			focusToElement(element);
			if (validInput(element.getAttribute("value"))) {
				element.click();
				element.clear();
				element.sendKeys(text);
				element.sendKeys(Keys.TAB);
			} else {
				element.click();
				element.sendKeys(text);
				element.sendKeys(Keys.TAB);
			}
		}
	}

}
