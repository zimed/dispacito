package mehdi.learning.projects.dispacito.selenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;

public class BaseTest {

public static WebDriver app = null;
DesiredCapabilities capabilities = null;

@BeforeClass
@Parameters("browser")
public void testSetup(String browser)
{
	browser = "chrome";
	if(browser.equals("chrome")) {
		app = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
		
	}
	else if (browser.equals("IE")){
		String path = System.getProperty("user.dir") + "/DriverLocal/IEDriverServer.exe";
		System.setProperty("webdriver.ie.driver", path);
		capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		app = new InternetExplorerDriver(capabilities);
	}
	app.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	app.manage().window().maximize();

}





@BeforeMethod
public void openBrowser()
{
//	app.get("https://www.browserstack.com/");
//	app.findElement(By.id("signupModalButton")).click();
//	System.out.println("We are currently on the following URL" +app.getCurrentUrl());
}



@AfterMethod
public void postSignUp()
{
	System.out.println("");
}

@AfterClass
public void afterClass()
{
	app.quit();
}

}