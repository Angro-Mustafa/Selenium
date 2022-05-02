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
import java.util.*;  
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PlainmathTest {
    public WebDriver driver;
    
    @BeforeTest 
    public void setup() {
        WebDriverManager.chromedriver().setup();
        
        // to block notifications
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions(); 
        options.setExperimentalOption("prefs", prefs);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("os", "Windows");
        capabilities.setCapability("os_version", "18");
        capabilities.setCapability("name", "Bstack-[Java] Sample file download");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
    }

   @Test(groups = {"login"})
    public void testLogin() {
        LoginPage loginPage = new LoginPage(this.driver);     
        MainPage mainPage = loginPage.login("mustafamadira94@gmail.com","123456789+");
        String bodyText = mainPage.getBodyText();
		Assert.assertTrue(bodyText.contains("Logout"));
        Assert.assertEquals(this.driver.getTitle(), "Math Homework Help and Answers| Students ask, students answer. Easy as that - PlainMath");
    }
    
    @Test(dependsOnMethods={"testLogin"})
    public void testLogout() {
               
        MainPage mainPage = new MainPage(this.driver); 
        mainPage = mainPage.logout();
        String bodyText = mainPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Sign In"));	
	}
	
	
    @Test
    public void testStaticPage() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.staticPage();
        String bodyText = mainPage.getBodyText();
        Assert.assertTrue(bodyText.contains("About us"));
    }
	
    @Test
    public void testSearch() {
        MainPage mainPage = new MainPage(this.driver); 

        String[] searchQueries={"Math","???????????????"};  
        for(String searchQuery : searchQueries) {  
            String bodyText;
            if(searchQuery == "Math")
            {
                SearchResultPage searchResultPage = mainPage.search(searchQuery,"//div[3]/div[1]/div[1]");
                bodyText = searchResultPage.getBodyText();
                Assert.assertTrue(bodyText.contains("Jaya Legge"));
            } else {
                mainPage.waitAndReturnElement(By.xpath("//div[1]/a/img")).click();
                SearchResultPage searchResultPage = mainPage.search(searchQuery,"//div[3]/div[1]/div");
                bodyText = searchResultPage.getBodyText();
                Assert.assertTrue(bodyText.contains("Сan’t find what you need?"));
				
            }
        }  
    }
	

    @Test
    public void testMultiplePage() {
        String[] pages ={"Privacy","Term of Service"}; 
        String bodyText, URL;
        GeneralPage generalPage;
        for(String page : pages) {  
            MainPage mainPage = new MainPage(this.driver);
            if(page == "Privacy") {
                generalPage = mainPage.multiplePage("//div[3]/div[2]/a[3]");
                bodyText = generalPage.getBodyText();
                URL = generalPage.getUrl();
                Assert.assertEquals(URL,"https://plainmath.net/privacy-policy");
                Assert.assertTrue(bodyText.contains("Our Privacy Policy"));
            } 
			else {
                generalPage = mainPage.multiplePage("//div[3]/div[2]/a[2]");
                bodyText = generalPage.getBodyText();
                URL = generalPage.getUrl();
                Assert.assertEquals(URL, "https://plainmath.net/terms-of-use");
                Assert.assertTrue(bodyText.contains("Terms of Use"));
            }
        } 
    }
	
	@Test
    public void testReading_Textarea_Content() {
	   MainPage mainPage = new MainPage(this.driver);
       mainPage.waitAndReturnElement(By.xpath("//div[2]/div/a[1]")).click();
        String bodyText = mainPage.getBodyText();
        Assert.assertTrue(bodyText.contains("User mustafamadira94"));		
    }

    @Test(groups = { "login" })
    public void testPost() {
        MainPage mainPage = new MainPage(this.driver); 

        SubmitPage submitPage = mainPage.postClick();
        GeneralPage generalPage = submitPage.submitPost();
        String bodyText = generalPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Your question will be checked and approved shortly."));
    }

  
   @Test(dependsOnMethods={"testLogin"})	
    public void hoverTest()					
    {		
        MainPage mainPage = new MainPage(this.driver);
        mainPage.hover();
	}

    @Test(dependsOnMethods={"testLogout"})		
    public void dragDropTest()					
    {		
        GeneralPage generalPage = new GeneralPage(this.driver);
        generalPage.dragDrop();
		String bodyText = generalPage.getBodyText();
		Assert.assertTrue(bodyText.contains("You did great!"));
	}
	

   @Test(dependsOnMethods={"testLogin"})	
    public void historyTest() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.back();
        Assert.assertEquals(mainPage.getUrl(), "https://plainmath.net/");
    }

    // Cookies Test
    @Test
	public void addCookie()
	{
        MainPage mainPage = new MainPage(this.driver);

		Cookie name = new Cookie("mycookie", "123456789");
		this.driver.manage().addCookie(name);
		
		Set<Cookie> cookiesList =  this.driver.manage().getCookies();
		for(Cookie getcookies :cookiesList) {
		    System.out.println(getcookies );
		}
	}

    @Test
	public void deleteCookie()
	{
		MainPage mainPage = new MainPage(this.driver);

        Cookie name = new Cookie("mycookie", "123456789");
		this.driver.manage().addCookie(name);
        this.driver.manage().deleteCookieNamed("mycookie");
	}
    // End of Cookies Test

    @AfterTest 
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
