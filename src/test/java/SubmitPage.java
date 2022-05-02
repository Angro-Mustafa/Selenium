import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import com.github.javafaker.Faker;

class SubmitPage extends PageBase {

    private By chooseCategory_DropDown = By.xpath("//table/tbody/tr[1]/td/select[1]");
	private By SecondaryChoice = By.xpath("//td/select[1]/option[2]");
    private By titleBy = By.xpath("//div[@class='_2wyvfFW3oNcCs5GVkmcJ8z']/textarea[@placeholder='Title']");
    private By postdescriptionBy = By.xpath("//table/tbody/tr[3]/td/div[1]/div[1]/div[2]/div");
    private By postButtonBy = By.xpath("//button[@id='ask_form_submit']");
    private By checkbox = By.xpath("//input[@id='ask_form_notify']");
    
    public SubmitPage(WebDriver driver) {
        super(driver);
    }    

    public GeneralPage submitPost() {
        Faker faker = new Faker(); 
        String Question = faker.lorem().paragraph();

        //choose category and post a question 
        this.waitAndReturnElement(chooseCategory_DropDown).click();
        this.waitAndReturnElement(SecondaryChoice).click();
        this.waitAndReturnElement(postdescriptionBy).sendKeys(Question+"?");
        this.waitAndReturnElement(checkbox).click();
        this.waitAndReturnElement(postButtonBy).click();
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[4]")));

        return new GeneralPage(this.driver);
    }
      /*
    public GeneralPage submitPostWithUpload() {
        Faker faker = new Faker(); 
        String Question = faker.lorem().paragraph();
        File file = new File("src/test/java/resources/plainmath.png");
        this.waitAndReturnElement(chooseCategory_DropDown).click();
        this.waitAndReturnElement(SecondaryChoice).click();
        this.waitAndReturnElement(imageButtonBy).click();
 
        // fill drop-down
        //is.waitAndReturnElement(selectBy).sendKeys("u/JaafarTest");
       //his.waitAndReturnElement(titleBy).sendKeys(title);

        // display the input file cuz it's hidden in the website
       //avascriptExecutor js = (JavascriptExecutor) this.driver;
       //executeScript("return document.getElementsByClassName('sU2P34us34ODfjtvAFHEh')[0].style.display = 'block';");
        
        this.waitAndReturnElement(postdescriptionBy).sendKeys(file.getAbsolutePath());

       //his.wait.until(ExpectedConditions.elementToBeClickable(postButtonBy));	
        this.waitAndReturnElement(postButtonBy).click();

        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div[4]")));

        return new GeneralPage(this.driver);
    }
	*/
}
