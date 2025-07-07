package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import drivers.DriverFactory;
import utility.*;

public class TestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestStart(result);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {

		WebDriver driver = DriverFactory.getDriver();

		if (driver == null) {
			System.err.println("Could not get WebDriver from test class");
			return;
		}

		System.out.println("❌ Test Failed: " + result.getName());

		// Check if it's the final retry
		Object retryCountObj = result.getAttribute("retryCount");
		Object maxRetryObj = result.getAttribute("maxRetry");

		int retryCount = retryCountObj instanceof Integer ? (int) retryCountObj : 0;
		int maxRetry = maxRetryObj instanceof Integer ? (int) maxRetryObj : 0;

		if (retryCount >= maxRetry) {
			// Take screenshot only on final failure
			String className = result.getTestClass().getRealClass().getSimpleName();
			ScreenshotUtil.takeScreenshot(driver, className);
			System.out.println("📸 Screenshot taken on final failure.");
		} else {
			System.out.println("🔁 Skipping screenshot – retrying test again.");
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
	}

}
