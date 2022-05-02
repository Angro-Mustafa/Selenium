import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

class PageBase {
    protected WebDriver driver;
    protected WebDriverWait wait;

	//private By menuBarBy  =  By.xpath("/html/body/div[1]/div/header/div[3]/a[4]");
    private By logoutButton = By.xpath("//div/a[@href='/logout']");

    
    public PageBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }
    
    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    } 
    
    public String getBodyText() {
        WebElement bodyElement = this.waitAndReturnElement(By.tagName("body"));
        return bodyElement.getText();
    }

    public String getUrl() {
        return this.driver.getCurrentUrl(); 
    }
 
    public SubmitPage postClick() {
        By postButton = By.xpath("//div/a[@href='javascript:void(0)']");
		
        this.waitAndReturnElement(postButton).click();

        return new SubmitPage(this.driver);
    }

    public MainPage logout() { 
        this.waitAndReturnElement(logoutButton).click();
        return new MainPage(this.driver);
    }
   
}
