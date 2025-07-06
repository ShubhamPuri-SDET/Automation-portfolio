package utility;

import java.lang.reflect.Method;
import java.time.Duration;

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
    
    //******Webdriver Teardown methods********

    @BeforeMethod
    public void setUp(Method method) {

        // Get test class and method names
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();

        // Setup per-test logging context
        MDC.put("testName", className + "_" + methodName);
        logger = LoggerFactory.getLogger(className);

        logger.info("===== STARTING TEST: {}.{} =====", className, methodName);
        logger.info("Launching browser and initializing WebDriver...");

        // Initialize WebDriver from a central DriverFactory
        driver = DriverFactory.initializeDriver();
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

         // Clear MDC to avoid memory leaks or cross-test log contamination
         MDC.clear();
     }
  
    public WebDriver getDriver() {
        return driver;
    }

    
    //*****wait methods***********
    
    // Reusable Explicit Wait: Wait for visibility
    public WebElement waitForVisibility(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Reusable Explicit Wait: Wait for clickability
    public WebElement waitForElementClickable(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // You can also add a default timeout overload
    public WebElement waitForElementClickable(By locator) {
        return waitForElementClickable(locator, 10); // default 10 seconds
    }

}
