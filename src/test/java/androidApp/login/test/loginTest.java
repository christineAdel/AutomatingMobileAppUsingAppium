package androidApp.login.test;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import anroid.zain.test.AndroidBase;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class loginTest extends AndroidBase {

	 @DataProvider(name = "loginTestData")
	    public Object[][] getTestData() throws Exception {
	        String filePath = System.getProperty("user.dir") + File.separator + "src/main/resources/data/testData.csv";
	        BufferedReader reader = new BufferedReader(new FileReader(filePath));
	        List<Object[]> testData = new ArrayList<>();

	        String line;
	        reader.readLine(); // To Skip the header
	        while ((line = reader.readLine()) != null) {
	            String[] data = line.split(",");
	            testData.add(new Object[]{
	                data[0], // username
	                data[1], // password
	                Boolean.parseBoolean(data[2]), // isLoginSuccessful
	                data.length > 3 ? data[3] : "", // errorMsg
	                data.length > 4 ? data[4] : ""  // testCaseDescription
	            });
	        }
	        reader.close();

	        return testData.toArray(new Object[0][0]);
	    }

    @Test(enabled = true, description = "Positive and negative login Test scenarios for the android application " ,dataProvider = "loginTestData")
    public void testLogin(String username, String password, boolean isLoginSuccessful, String errorMsg, String caseDescription) {
		logger = extent.startTest("Test Case " + caseDescription);
    	logger.setDescription("Positive and negative login Test scenarios for the android application");
    	loginPage.enterUsername(username);
		logger.log(LogStatus.INFO,"Enter user name: " + username);
        loginPage.enterPassword(password);
		logger.log(LogStatus.INFO,"Enter password: " + username);
        loginPage.clickLoginButton();
		logger.log(LogStatus.INFO,"Click on Login button : " );

		logger.log(LogStatus.INFO,"Check if the login success : " +isLoginSuccessful );
        // Assert based on expected result
        if (isLoginSuccessful) {
        	
        	boolean isTestCartDisplayed = homePage.isTestCartDisplayed();
            logger.log(LogStatus.INFO,"Check if the test cart displayed : " +isTestCartDisplayed );
            Assert.assertTrue(isTestCartDisplayed, "Home screen should be displayed at the Home page");
            

        	boolean isProductsDisplayed = homePage.isProductsDisplayed();
            logger.log(LogStatus.INFO,"Check if the products displayed at the home page : " +isProductsDisplayed );
            Assert.assertTrue(isProductsDisplayed, "Products Container should be displayed at the Home Page");
          

        	boolean isSideMenuDisplayed = homePage.isSideMenuDisplayed();
            logger.log(LogStatus.INFO,"Check if the side menu displayed at the home page : " +isSideMenuDisplayed );
            Assert.assertTrue(isSideMenuDisplayed, "side Menu should be displayed at the Home Page");
        } else {
            logger.log(LogStatus.INFO,"make sure that the error message displayed at the login page : ");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
           System.out.println(loginPage.getErrorMessageText());
           logger.log(LogStatus.INFO,"make sure that the error message context displayed correctly at the login page : ");
            Assert.assertTrue(loginPage.getErrorMessageText().contains(errorMsg), "Error message mismatch"); 
        }
    }
}

