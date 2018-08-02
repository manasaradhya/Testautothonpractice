package com.ctli.it.webpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.ctli.it.lib.BaseClass;
import com.relevantcodes.extentreports.ExtentTest;

public class Amazon_Page extends BaseClass{
	
	public Amazon_Page(WebDriver driver, ExtentTest testReport) {
		super(driver, testReport);
		PageFactory.initElements(driver, this);
	}

}
