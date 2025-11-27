package config;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import utils.ConfigurationHelper;

import java.net.URL;

public class AppiumConfig {
    public static final String APPIUM_SERVER_URL = ConfigurationHelper.getConfigProperties().getProperty("appiumServerURL");
    public static final String APP_PACKAGE = ConfigurationHelper.getConfigProperties().getProperty("appPackage");
    public static final String APP_ACTIVITY = ConfigurationHelper.getConfigProperties().getProperty("appActivity");

    public AppiumDriver initializeAppiumDriver(DeviceInfo device) {
        try {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName(device.getPlatformName());
            options.setPlatformVersion(device.getPlatformVersion());
            options.setDeviceName(device.getDeviceName());
            options.setUdid(device.getUdid());
            options.setAutomationName("UiAutomator2");
            options.setAppPackage(APP_PACKAGE);
            options.setAppActivity(APP_ACTIVITY);
            URL appiumServerURL = new URL(APPIUM_SERVER_URL);
            AppiumDriver driver = new AndroidDriver(appiumServerURL, options);
            return driver;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Appium driver for device: " +
                    (device != null ? device.getName() : "unknown"), e);
        }
    }
}
