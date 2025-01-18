package anroid.zain.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import androidApp.pages.HomePage;
import androidApp.pages.LoginPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;


public class AndroidBase {
	public AndroidDriver driver;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public HomePage homePage;
	public LoginPage loginPage;
	public SoftAssert softAssert;

	// @BeforeSuite
	@BeforeClass
	public void setup() throws Exception {
		ConfigLoader.loadConfig("src/main/resources/config/configFile.properties");
		UiAutomator2Options options = new UiAutomator2Options();
		options.setCapability("appium:automationName", ConfigLoader.getProperty("automationName"));
		options.setCapability("platformName", ConfigLoader.getProperty("platform"));
		options.setCapability("appium:deviceName", ConfigLoader.getProperty("deviceName"));
		options.setCapability("appium:platformVersion", ConfigLoader.getProperty("version"));
		options.setCapability("appium:app", System.getProperty("user.dir") + File.separator + ConfigLoader.getProperty("path"));
		options.setCapability("appium:appPackage", ConfigLoader.getProperty("appPackage"));
		options.setCapability("appium:appActivity", ConfigLoader.getProperty("appActivity"));
		options.setCapability("appium:FULL_RESET", true);
		options.setCapability("appium:noReset", false);
		System.out.println("Loaded Capabilities: " + options.asMap());
		driver = new AndroidDriver(new URL(ConfigLoader.getProperty("ipAddress")), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
	}

	@BeforeSuite
	public void beforeSuit() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		extent = new ExtentReports("Reports/" + "Extent-Report-" + currentDateTime + ".html", true);
		extent.addSystemInfo("Environment", "Android");
		extent.addSystemInfo("Author", "Automation Team");
		extent.addSystemInfo("App", "Mobile sample app");
	}

	@AfterMethod
	public void afterMethod(Method method, ITestResult result) {

		File snapshot = driver.getScreenshotAs(OutputType.FILE);
		Instant now = Instant.now();
		long epochMillis = now.toEpochMilli();
		try {
			FileUtils.copyFile(snapshot, new File(System.getProperty("user.dir") + File.separator + "images/"
					+ method.getName() + epochMillis + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fullPath = System.getProperty("user.dir") + File.separator + "images/" + method.getName() + epochMillis
				+ ".png";
		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, "Test is Passed");
			logger.log(LogStatus.PASS, logger.addScreenCapture(fullPath));
		} else if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test is failed");
			logger.log(LogStatus.FAIL, result.getThrowable());
			logger.log(LogStatus.FAIL, logger.addScreenCapture(fullPath));
		} else {
			logger.log(LogStatus.SKIP, "Test is Skipped");
		}
	}

	@AfterSuite
	public void tearDown() {
		extent.flush();
		if (driver != null) {
			driver.quit();
		}
	}
}
