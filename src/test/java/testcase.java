import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class testcase {

    WebDriver driver;

    @BeforeSuite
    public void setUp() throws IOException {

        try {
            FileUtils.deleteDirectory(new File("./Reports"));
        }catch (Exception e){
            System.out.println("Unable to remove reports");
        }
        new File("./Reports").mkdir();
        WebDriverManager.chromedriver().win().setup();

    }

    @BeforeMethod
    public void beforeMethod(){
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().window().maximize();
    }

    @Test
    public void login() throws IOException {

        wait(By.name("username"));
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        wait(By.className("oxd-topbar-header-title"));
        takeScreenshot("login");
    }

    @Test
    public void applyLeave() throws Exception {

        wait(By.name("username"));
        driver.findElement(By.name("username")).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        wait(By.className("oxd-topbar-header-title"));
        wait(By.xpath("//span[text()='Leave']"));
        driver.findElement(By.xpath("//span[text()='Leave']")).click();
        wait(By.xpath("(//div[@class='oxd-checkbox-wrapper'])[2]"));
        driver.findElement(By.xpath("(//div[@class='oxd-checkbox-wrapper'])[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/div[2]/div[1]/div/div[9]/div/button[1]")).click();
        takeScreenshot("Apply_leave");
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    public void wait(By ele){

        try{
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(Long.parseLong("30"))).pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
        }catch (Exception e){
            System.out.println("Error in Wait....");
        }
    }

    public void takeScreenshot(String message) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File("./Reports/"+message+".png"));
    }
}
