package config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DeviceConfig {
    private static final String DEVICES_CONFIG_FILE = "/devices.json";
    private static volatile List<DeviceInfo> devices;

    private static List<DeviceInfo> readDevicesFromJson() {
        List<DeviceInfo> loadedDevices = new ArrayList<>();
        try (InputStream inputStream = DeviceConfig.class.getResourceAsStream(DEVICES_CONFIG_FILE)) {
            if (inputStream == null) {
                throw new RuntimeException("Devices configuration file not found: " + DEVICES_CONFIG_FILE);
            }
            JsonObject jsonObject = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
            Gson gson = new Gson();
            if (jsonObject.has("devices") && jsonObject.get("devices").isJsonArray()) {
                var devicesArray = jsonObject.getAsJsonArray("devices");
                for (var deviceElement : devicesArray) {
                    DeviceInfo device = gson.fromJson(deviceElement, DeviceInfo.class);
                    loadedDevices.add(device);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load devices configuration", e);
        }
        return loadedDevices;
    }

    public static List<DeviceInfo> loadDevices() {
        if (devices == null) {
            synchronized (DeviceConfig.class) {
                if (devices == null) {
                    devices = readDevicesFromJson();
                    if (devices.isEmpty()) {
                        throw new RuntimeException("No devices found in configuration file");
                    }
                    return devices;
                }
            }
        }
        return devices;
    }
}

