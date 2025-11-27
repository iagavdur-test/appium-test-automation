package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigurationHelper {
    private static volatile Properties configProperties;

    private ConfigurationHelper() {
    }

    private static Properties loadPropertiesFile(String filePath) {
        Properties prop = new Properties();
        try (InputStream resourceAsStream = ConfigurationHelper.class.getClassLoader()
                .getResourceAsStream(filePath)) {
            prop.load(resourceAsStream);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load properties file : " + filePath);
        }
        return prop;
    }

    public static Properties getConfigProperties() {
        if (configProperties == null) {
            synchronized (ConfigurationHelper.class) {
                if (configProperties == null) {
                    configProperties = loadPropertiesFile("config.properties");
                }
            }
        }
        return configProperties;
    }
}
