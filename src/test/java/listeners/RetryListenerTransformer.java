package listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;


//Automatically apply retry to all tests//

//Retry logic - if want to use need to assign listener in TestNG

public class RetryListenerTransformer implements IAnnotationTransformer {

    @Override
    public void transform(
        ITestAnnotation annotation,
        Class testClass,
        Constructor testConstructor,
        Method testMethod
    ) {
        if (annotation.getRetryAnalyzerClass() == null) {
            annotation.setRetryAnalyzer(AdvancedRetryAnalyzer.class);
        }
    }
}
