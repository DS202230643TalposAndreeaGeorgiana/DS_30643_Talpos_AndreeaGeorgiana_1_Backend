package org.example.service;

import org.example.model.Device;

import java.util.List;

public interface DeviceService {
    List<Device> getAllDevices();
    Device getDeviceById(Long id);
    Device updateDevice(Device device, Long id);
    void createDevice(Device device);
    void deleteDeviceById(Long id);
}
