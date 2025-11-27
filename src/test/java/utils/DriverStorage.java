package utils;

import config.AppiumConfig;
import config.DeviceManager;
import io.appium.java_client.AppiumDriver;


public class DriverStorage {
    private static final ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<>();

    public static void initDriver() {
        driverThreadLocal.set(null);
        driverThreadLocal.set(createAppiumDriver());
    }

    public static AppiumDriver getDriver() {
        if (driverThreadLocal.get() != null) {
            return driverThreadLocal.get();
        } else {
            throw new RuntimeException("Appium driver has not been created");
        }
    }

    public static void quitDriver() {
        try {
            getDriver().quit();
        } finally {
            DeviceManager.releaseDeviceForThread();
        }
    }

    private static AppiumDriver createAppiumDriver() {
        return new AppiumConfig().initializeAppiumDriver(DeviceManager.getDeviceForThread());
    }
}


