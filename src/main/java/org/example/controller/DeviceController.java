package org.example.controller;

import org.example.model.Device;
import org.example.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public Long createDevice(@RequestBody Device device) {
        deviceService.createDevice(device);
        return device.getId();
    }
    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/{deviceId}")
    public Device getDeviceById(@PathVariable("deviceId") Long deviceId) {
        return deviceService.getDeviceById(deviceId);
    }

    @PutMapping("/{deviceId}")
    public Device updateDevice(@RequestBody Device device, @PathVariable("deviceId") Long deviceId) {
        return deviceService.updateDevice(device, deviceId);
    }

    @DeleteMapping("/{deviceId}")
    public void deleteDevice(@PathVariable("deviceId") Long deviceId) {
        deviceService.deleteDeviceById(deviceId);
    }
}
