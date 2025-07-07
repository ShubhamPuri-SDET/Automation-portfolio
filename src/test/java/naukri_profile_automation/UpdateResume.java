package naukri_profile_automation;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import utility.BaseClass;

public class UpdateResume extends BaseClass {

	@Test
	public void uploadResumeToNaukri() throws InterruptedException, URISyntaxException {

		driver.get("https://www.naukri.com/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		try {

			WebElement gotItButton = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//div[@class='acceptance-btn']//span[normalize-space()='Got it']")));

			// Scroll into view and click using JavaScript
			js.executeScript("arguments[0].scrollIntoView({block: 'center'});", gotItButton);
			js.executeScript("arguments[0].click();", gotItButton);

			System.out.println("Cookie banner dismissed.");

		} catch (TimeoutException e) {
			System.out.println("Cookie banner not found or already dismissed.");
		}

		WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[contains(@class,'nI-gNb-log-reg')]//a[@id='login_Layer' or @title='Jobseeker Login' or contains(@href, 'nLogin/Login') or normalize-space()='Login']")));

		loginBtn.click();

		String username = "spuri4867@gmail.com"; //System.getenv("NAUKRI_EMAIL"); // "spuri4867@gmail.com";
		String password = "Shubham@29051"; //System.getenv("NAUKRI_PASSWORD"); // "Shubham@29051";

		WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//input[@type='text' and contains(@placeholder, 'Enter your active Email ID / Username')]")));
		emailInput.sendKeys(username);

// driver.findElement(By.xpath("//input[@placeholder='Enter your active Email ID / Username']")).sendKeys("spuri4867@gmail.com");
		driver.findElement(By.xpath("//input[@placeholder='Enter your password']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='View profile']")))
					.click();
			System.out.println("✔️ 'View profile' link clicked.");
		} catch (TimeoutException e) {
			System.out.println("⚠️ 'View profile' not found, trying 'Complete profile'...");

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Complete')]")))
						.click();
				System.out.println("✔️ 'Complete profile' link clicked.");
			} catch (TimeoutException ex) {
				System.out.println("❌ Neither 'View profile' nor 'Complete profile' link found.");
				driver.quit(); // Optionally exit if both fail
				return;
			}
		}

		WebElement updateResume = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@type='button' and @value='Update resume']")));

		js.executeScript("arguments[0].scrollIntoView({block: 'center'});", updateResume);
		js.executeScript("arguments[0].click();", updateResume);

		Thread.sleep(1000);

		WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("attachCV")));

//		URL resourceUrl = UpdateResume.class.getClassLoader()
//				.getResource("InputTestData/Shubham_Puri_TestAutomationEngineer_Resume.pdf");
//
//		if (resourceUrl == null) {
//			throw new RuntimeException("Resume file not found in test resources.");
//		}
//
//		File resumeFile = new File(resourceUrl.toURI());
		
		
		String filePath = System.getProperty("user.dir") + "/src/test/resources/InputTestData/Shubham_Puri_TestAutomationEngineer_Resume.pdf";
		File resumeFile = new File(filePath);

		

		// String filePath = System.getProperty("user.home") +
		// "\\Desktop\\Shubham_Puri_TestAutomationEngineer_Resume.pdf";
		// fileInput.sendKeys(filePath);
		System.out.println("Uploading file: " + resumeFile.getAbsolutePath());
		fileInput.sendKeys(resumeFile.getAbsolutePath());
		System.out.println("✅ sendKeys called on file input.");
		System.out.println("✅ Resume uploaded successfully to Naukri.");

		// driver.quit();
	}
}
