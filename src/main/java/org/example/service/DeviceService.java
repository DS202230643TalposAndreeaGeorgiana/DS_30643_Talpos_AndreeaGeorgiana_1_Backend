package org.example.service;

import org.example.dto.DeviceDTO;

import java.util.List;

public interface DeviceService {
    List<DeviceDTO> getAllDevices();
    List<DeviceDTO> getAvailableDevices();
    DeviceDTO getDeviceById(Long id);
    DeviceDTO updateDevice(DeviceDTO device, Long id);
    DeviceDTO createDevice(DeviceDTO device);
    Long deleteDeviceById(Long id);
    List<DeviceDTO> getDevicesByDescription(String description);

}
