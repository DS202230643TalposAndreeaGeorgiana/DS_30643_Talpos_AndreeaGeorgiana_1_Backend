package org.example.controller;

import org.example.model.Device;
import org.example.service.serviceImpl.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceServiceImpl deviceServiceImpl;

    @PostMapping
    public Long createDevice(@RequestBody Device device) {
        deviceServiceImpl.createDevice(device);
        return device.getDeviceId();
    }
    @GetMapping
    public List<Device> getAllDevices() {
        return deviceServiceImpl.getAllDevices();
    }

    @GetMapping("/{deviceId}")
    public Device getDeviceById(@PathVariable("deviceId") Long deviceId) {
        return deviceServiceImpl.getDeviceById(deviceId);
    }

    @PutMapping("/{deviceId}")
    public Device updateDevice(@RequestBody Device device, @PathVariable("deviceId") Long deviceId) {
        return deviceServiceImpl.updateDevice(device, deviceId);
    }

    @DeleteMapping("/{deviceId}")
    public void deleteDevice(@PathVariable("deviceId") Long deviceId) {
        deviceServiceImpl.deleteDeviceById(deviceId);
    }
}
