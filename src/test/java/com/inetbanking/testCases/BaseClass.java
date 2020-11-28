package com.inetbanking.testCases;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.inetbanking.utilities.ReadConfig;

public class BaseClass {

	ReadConfig readconfig=new ReadConfig();
	
	public String baseURL=readconfig.getApplicationURL();
	public String username=readconfig.getUsername();
	public String password=readconfig.getPassword();
	public static RemoteWebDriver driver;
	
	public static Logger logger;
	
	@Parameters("browser")
	@BeforeTest
	public void setup(String br) throws MalformedURLException
	{			
		logger = Logger.getLogger("ebanking");
		PropertyConfigurator.configure("Log4j.properties");
		
		
		
		DesiredCapabilities cap = new DesiredCapabilities();
		
		
		if(br.equals("chrome"))
		{
			cap.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
			cap.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
		}
		else if(br.equals("firefox"))
		{
			cap.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
			cap.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);
		}
		else if(br.equals("edge"))
		{
			cap.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
			cap.setCapability(CapabilityType.BROWSER_NAME, BrowserType.EDGE);
		}
		URL url = new URL( "http://localhost:4444/wd/hub");
		driver = new RemoteWebDriver(url,cap);
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.get(baseURL);
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
	
	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}
	
	public String randomestring()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(8);
		return generatedstring;
	}
	
	public static String randomeNum() {
		String generatedString2 = RandomStringUtils.randomNumeric(4);
		return generatedString2;
	}
	
	
}
