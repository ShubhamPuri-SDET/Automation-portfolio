package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class AdvancedRetryAnalyzer implements IRetryAnalyzer {
	
	//Retry logic - if want to use need to assign listener in TestNG
	//Automatically apply retry to all tests

	  private int retryCount = 0;
	    private final int maxRetryCount = 2;

	    @Override
	    public boolean retry(ITestResult result) {
	        if (retryCount < maxRetryCount) {
	            retryCount++;
	            result.setAttribute("retryCount", retryCount);
	            result.setAttribute("maxRetry", maxRetryCount);
	            return true;
	        }
	        return false;
	    }
	}