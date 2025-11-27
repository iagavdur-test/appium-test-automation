package config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceInfo {
    private String name;
    private String platformName;
    private String platformVersion;
    private String deviceName;
    private String udid;
}

