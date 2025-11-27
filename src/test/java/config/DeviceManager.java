package config;

import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public class DeviceManager {
    private static final ConcurrentHashMap<Long, DeviceInfo> threadDeviceMap = new ConcurrentHashMap<>();
    private static final AtomicInteger deviceIndex = new AtomicInteger(0);
    private static final List<DeviceInfo> availableDevices = DeviceConfig.loadDevices();


    public static DeviceInfo getDeviceForThread() {
        long threadId = Thread.currentThread().getId();
        return threadDeviceMap.computeIfAbsent(threadId, id -> {
            int index = deviceIndex.getAndIncrement() % availableDevices.size();
            DeviceInfo device = availableDevices.get(index);
            log.info("Thread " + threadId + " assigned to device: " + device.getName());
            return device;
        });
    }


    public static void releaseDeviceForThread() {
        long threadId = Thread.currentThread().getId();
        DeviceInfo device = threadDeviceMap.remove(threadId);
        if (device != null) {
            log.info("Thread " + threadId + " released device: " + device.getName());
        }
    }
}

