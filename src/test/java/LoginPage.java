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
import java.util.concurrent.TimeUnit;

class LoginPage extends PageBase {
    private By loginButton = By.xpath("//div/a[@href='/login']");
    private By usernameInput = By.name("email");
    private By passwordInput = By.name("password");
    private By loginSubmitButton = By.xpath("//div/form/table/tbody/tr[5]/td/input");
	
	

    public LoginPage(WebDriver driver) {
        super(driver);
	   //open URL
this.driver.get("https://plainmath.net/");
//Move to login page
this.waitAndReturnElement(loginButton).click();
    }    

    public MainPage login(String username, String password) {
        this.waitAndReturnElement(usernameInput).sendKeys(username);
        this.waitAndReturnElement(passwordInput).sendKeys(password);
        this.waitAndReturnElement(loginSubmitButton).click();
        // wait to login
       // this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[1]/div/div/div[2]/div/a[1]")));
	   WebDriverWait wait = new WebDriverWait(this.driver,30);
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='qa-header']")));
	   return new MainPage(this.driver);
    }

}
