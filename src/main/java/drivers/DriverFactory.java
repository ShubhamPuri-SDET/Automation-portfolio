package drivers;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import utility.*;

import java.time.Duration;

public class DriverFactory {
	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static WebDriver initializeDriver() {
		if (driver.get() == null) {

			ChromeOptions options = new ChromeOptions();

			String headless = ConfigReader.get("headless");
			if ("true".equalsIgnoreCase(headless)) {
				options.addArguments("--headless=new");
				options.addArguments("--disable-gpu");
				options.addArguments("--window-size=1920,1080");
				options.addArguments("--remote-allow-origins=*");
			}

			WebDriver newDriver = new ChromeDriver(options);
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
