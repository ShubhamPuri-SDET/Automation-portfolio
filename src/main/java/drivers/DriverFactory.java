package drivers;

import java.time.Duration;

import utility.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static WebDriver initializeDriver() {
		if (driver.get() == null) {
			WebDriver newDriver = new ChromeDriver();
			newDriver.manage().window().maximize();
			newDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.set(newDriver);
		}
		return driver.get();
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}
	
	static {
		LoggingUtil.initializeLogsFolder();  // Ensures logs/ folder exists
	}
	

}
