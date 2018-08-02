package com.ctli.it.scenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.ctli.it.lib.BaseClass;
import com.ctli.it.lib.TestNgInitialization;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Scenario extends BaseClass{
	
	
	public Scenario(WebDriver driver, ExtentTest testReport) {
		super(driver, testReport);
		PageFactory.initElements(driver, this);
		
	}
	@Test
	public void testcase() throws InterruptedException{
		
	}
	

}
