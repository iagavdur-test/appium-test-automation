package tests;

import config.AppiumConfig;
import config.DeviceInfo;
import config.DeviceManager;
import io.appium.java_client.AppiumDriver;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import utils.DriverStorage;

@Log4j2
public class BaseTest {
    protected AppiumDriver driver;
    protected AppiumConfig appiumConfig;
    protected DeviceInfo deviceInfo;

    @BeforeEach
    void setUp() {
        DriverStorage.initDriver();
        driver = DriverStorage.getDriver();
        appiumConfig = new AppiumConfig();
        driver = DriverStorage.getDriver();
        deviceInfo = DeviceManager.getDeviceForThread();
        log.info("Running test on device: " + deviceInfo.getName());
    }

    @AfterEach
    void tearDown() {
        DriverStorage.quitDriver();
    }
}
