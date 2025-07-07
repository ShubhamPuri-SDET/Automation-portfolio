package utility;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import drivers.DriverFactory;

public class BaseClass {
    protected WebDriver driver;
    protected Logger logger = LoggingUtil.getLogger(getClass());

    @BeforeMethod
    public void setUp(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        MDC.put("testName", className + "_" + methodName);
        logger = LoggerFactory.getLogger(className);

        logger.info("===== STARTING TEST: {}.{} =====", className, methodName);

        String browser = ConfigReader.getProperty("browser", "chrome").toLowerCase(Locale.ROOT);
        boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless", "false"));

        logger.info("Launching browser: {} | Headless: {}", browser, headless);

        driver = DriverFactory.initializeDriver(browser, headless);
    }
    @AfterMethod
    public void tearDown(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();

        if (driver != null) {
            driver.quit();
            logger.info("Closed browser and WebDriver.");
        }

        logger.info("===== ENDING TEST: {}.{} =====", className, methodName);
        MDC.clear();
    }

    public WebDriver getDriver() {
        return driver;
    }

    // Wait methods
    public WebElement waitForVisibility(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementClickable(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementClickable(By locator) {
        return waitForElementClickable(locator, 10);
    }
}
