package androidApp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    // Locators
    private By usernameField = By.xpath("//android.widget.EditText[@content-desc='test-Username']");
    private By passwordField = By.xpath("//android.widget.EditText[@content-desc='test-Password']");
    private By loginButton = By.xpath("//android.view.ViewGroup[@content-desc='test-LOGIN']");
    private By errorMessage = By.xpath("//android.view.ViewGroup[@content-desc='test-Error message']//android.widget.TextView");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElement(errorMessage).isDisplayed();
    }
    
    public String getErrorMessageText() {
        return driver.findElement(errorMessage).getText();
    }
}
