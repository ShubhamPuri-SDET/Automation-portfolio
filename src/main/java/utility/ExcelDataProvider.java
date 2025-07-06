package utility;


	import org.testng.annotations.DataProvider;
	import java.lang.reflect.Method;
	import java.util.Map;

	public class ExcelDataProvider {

	    /**
	     * Universal reusable data provider for test classes using Excel.
	     * It dynamically loads data based on the test class name and method name.
	     *
	     * @param method TestNG injects the test method
	     * @return 2D Object array of test data
	     */
	    @DataProvider(name = "excelData")
	    public Object[][] getExcelData(Method method) {
	        Class<?> testClass = method.getDeclaringClass();
	        String className = testClass.getSimpleName();  // e.g., LoginTest
	        String methodName = method.getName();          // e.g., verifyLogin

	        // Excel file path
	        String excelPath = System.getProperty("user.dir")
	                + "/src/test/resources/InputTestData/" + className + ".xlsx";

	        ExcelUtil excelUtil;
	        try {
	            excelUtil = new ExcelUtil(excelPath, methodName);
	        } catch (Exception e) {
	            throw new RuntimeException("❌ Failed to load Excel file: " + excelPath + "\n" + e.getMessage());
	        }

	        Object[][] data = excelUtil.getDataAs2DArray();
	        if (data.length == 0) {
	            throw new RuntimeException("❌ No data found in sheet '" + methodName + "' of file: " + className + ".xlsx");
	        }

	        return data;
	    }
	}
