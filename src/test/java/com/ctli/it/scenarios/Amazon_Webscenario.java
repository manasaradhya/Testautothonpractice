package com.ctli.it.scenarios;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ctli.it.lib.TestNgInitialization;
import com.ctli.it.webpage.Amazon_Page;
import com.ctli.it.webpage.Ghost_Homepage;
import com.ctli.it.webpage.Ghost_Loginpage;

public class Amazon_Webscenario extends TestNgInitialization{
	
	@BeforeMethod
	public WebDriver initializeDriver() throws IOException
	{
	                
	Properties prop= new Properties();
	FileInputStream fis=new FileInputStream("C:\\Users\\ab71711\\Automation_testathon\\Final_10Night\\Final_10Night\\CTLI_HAMPS\\Resources\\TestData.properties");

	prop.load(fis);
	String browserName=prop.getProperty("browser");
	System.out.println(browserName);

	if(browserName.equals("chrome"))
	{
	                System.setProperty("webdriver.chrome.driver", "C:\\Users\\AB71711\\chromedriver.exe");
	                driver= new ChromeDriver();
	                                //execute in chrome driver                
	}
	else if (browserName.equals("firefox"))
	{
	                driver= new FirefoxDriver();
                //firefox code
	}
	else if (browserName.equals("IE"))
	{
//	            IE code
	}
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	return driver;
	}
	
	@Test
	public void WebPage_Scenario() throws Throwable {
		//Amazon_Page webpage = new Amazon_Page(driver, testReport);

		//driver.findElement()
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//a[contains(text(),'Deals')]")).click();
		Select sortby = new Select(driver.findElement(By.xpath("//select[@name='sortOptions']")));
		sortby.selectByVisibleText("Price - Low to High");
		List<WebElement> items = driver.findElements(By.xpath("//div[contains(@id,'supple')]//div[@class='a-row priceBlock unitLineHeight']/span"));
		int count =0;
		for(int i = 1; i<=items.size();i++){	
			String prices= driver.findElement(By.xpath("(//div[contains(@id,'supple')]//div[@class='a-row priceBlock unitLineHeight'])["+i+"]/span")).getText();
			String prices1= prices.substring(1,prices.length());
			System.out.println(prices1);
			if(prices1.length()<=3){
			int actualprice = Integer.parseInt(prices1);
			if(actualprice<=100){
				count= count+1;
			}else{
				break;
			}
		  }	
		}
		System.out.println("Below or equal 100 rupess items are : "+count);
		
		driver.findElement(By.xpath("(//div[contains(@id,'supple')]//a[@id='dealTitle'])[2]")).click();
		Thread.sleep(2000);
		List <WebElement> row_count = driver.findElements(By.xpath("//div[@id='prodDetails']//div[@class='column col1 ']//tr"));	
		for(int i=0;i<=row_count.size()-1;i++){
			String parameter = driver.findElement(By.xpath("//div[@id='prodDetails']//div[@class='column col1 ']//tr[1]/td[1]")).getText();
			String value = driver.findElement(By.xpath("//div[@id='prodDetails']//div[@class='column col1 ']//tr[1]/td[2]")).getText();
			System.out.println(parameter+" - "+value);
		}
	}

}
