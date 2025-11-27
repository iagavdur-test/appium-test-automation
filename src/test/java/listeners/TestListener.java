package listeners;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.DriverStorage;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;

@Log4j2
public class TestListener implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final String START_TIME = "start time";

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        getStore(context).put(START_TIME, System.currentTimeMillis());
        Method testMethod = context.getRequiredTestMethod();
        String testName = testMethod.getName();
        log.info("Starting test: {}", testName);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        String testName = testMethod.getName();
        long startTime = getStore(context).remove(START_TIME, long.class);
        long duration = System.currentTimeMillis() - startTime;
        log.info("Finished test: {} in {} ms", testName, duration);
        if (context.getExecutionException().isPresent()) {
            Throwable exception = context.getExecutionException().get();
            log.error("Test {} failed with exception: {}", testName, exception.getMessage());
            AppiumDriver driver = DriverStorage.getDriver();
            if (driver != null) {
                try {
                    byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
                    Allure.addAttachment("Скриншот при падении теста: " + testName, "image/png", new ByteArrayInputStream(screenshot), "png");
                } catch (Exception e) {
                    log.error("Failed to take a screenshot: {}", e.getMessage());
                }
            }
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }
}
