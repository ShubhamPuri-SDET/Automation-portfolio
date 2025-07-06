package utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import drivers.DriverFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

	public static String takeScreenshot(WebDriver driver, String className) {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String screenshotName = className + "_" + timestamp + ".png";

		String basePath = System.getProperty("user.dir") + "/src/test/resources/TestReports/Screenshots/";

		new File(basePath).mkdirs();

		String screenshotPath = basePath + screenshotName;

		File src = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(screenshotPath));
			System.out.println("✅ Screenshot saved at: " + screenshotPath);
		} catch (IOException e) {
			System.err.println("❌ Failed to save screenshot: " + e.getMessage());
		}

		return screenshotPath;
	}
}
