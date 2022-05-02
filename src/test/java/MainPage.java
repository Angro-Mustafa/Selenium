import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.concurrent.TimeUnit;

class MainPage extends PageBase {

    private By searchBarBy = By.xpath("//div/div[1]/form/div/input");
	private By searchButton=By.xpath("//section[1]/div/div/div[1]/form/button");
    private By about = By.xpath("//div[2]/a[@href='/about-us']");
    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://plainmath.net/");
	}

    public SearchResultPage search(String searchQuery, String selector) {
        this.waitAndReturnElement(searchBarBy).sendKeys(searchQuery);
        this.waitAndReturnElement(searchButton).click();
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector)));

        return new SearchResultPage(this.driver);
    }

    public AboutPage staticPage() {
        this.waitAndReturnElement(about).click();
        return new AboutPage(this.driver);
    }

    public GeneralPage multiplePage(String selector) {
        this.waitAndReturnElement(By.xpath(selector)).click();
        return new GeneralPage(this.driver);
    }

    public void hover() {
        WebElement ele = this.waitAndReturnElement(By.xpath("//div/a[@href='javascript:void(0)']"));
        Actions action = new Actions(this.driver);

        action.moveToElement(ele).perform();
    }

    public void back() {
        this.waitAndReturnElement(about).click();
        this.driver.navigate().back();
    }

}
