package cachetestcases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class LinkedinRequest {

    @Test
    public void sentinvitation() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        options.addArguments("--window-size=1920,1080"); // Optional: to handle screen-based rendering
        options.addArguments("--disable-gpu"); // Recommended for headless
        options.addArguments("--no-sandbox");  // Helps avoid errors in some environments

        // Initialize WebDriver with options
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.linkedin.com/login");
        driver.manage().window().maximize();

        String usernmae = System.getenv("Linkdin_Email");
        String password = System.getenv("Linkdin_Password");
        driver.findElement(By.xpath("//input[@id=\"username\"]")).sendKeys(usernmae);
        driver.findElement(By.xpath("//input[@id=\"password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//button[contains(@class, 'btn__primary--large') and contains(@class, 'from__button--floating')]")).click();

        Thread.sleep(Duration.ofSeconds(5));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'global-nav__primary-link') and .//span[normalize-space()='My Network']]")
        )).click();
        WebElement showAllSpan = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p[contains(normalize-space(), 'People you may know')]/following::button[.//span[contains(text(), 'Show all')]]")
        ));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", showAllSpan);
        js.executeScript("arguments[0].click();", showAllSpan);

        List<WebElement> connectButtons = driver.findElements(By.xpath("//span[normalize-space()='Connect']"));

        for (WebElement connectButton : connectButtons) {
            try {
                // Scroll to each button and click it using JS
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", connectButton);
                js.executeScript("arguments[0].click();", connectButton);

                // Optional: wait a bit between clicks if needed (avoid UI glitches)
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println("Could not click a connect button: " + e.getMessage());
            }
        }

    }

}
