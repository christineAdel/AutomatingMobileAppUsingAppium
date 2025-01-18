package androidApp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    WebDriver driver;

    // Locators
    private By testCartElement = By.xpath("//android.view.ViewGroup[@content-desc='test-Cart drop zone']");
    private By productsContainerElement = By.xpath("//android.widget.ScrollView[@content-desc='test-PRODUCTS']");
    private By sideMenuElement = By.xpath("//android.view.ViewGroup[@content-desc='test-Menu']");
    		// Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public boolean isTestCartDisplayed() {
        return driver.findElement(testCartElement).isDisplayed();
    }
    
    public boolean isProductsDisplayed() {
        return driver.findElement(productsContainerElement).isDisplayed();
    }
    
    public boolean isSideMenuDisplayed() {
        return driver.findElement(sideMenuElement).isDisplayed();
    }
}
