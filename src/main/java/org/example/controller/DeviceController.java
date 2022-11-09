package org.example.controller;

import org.example.dto.DeviceDTO;
import org.example.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/devices")
@CrossOrigin(origins = "http://localhost:4200")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO device) {
        return new ResponseEntity<>(deviceService.createDevice(device), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        return new ResponseEntity<>(deviceService.getAllDevices(), HttpStatus.OK);
    }
    @GetMapping("/available/{description}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByDescription(@PathVariable("description") String description) {
        return new ResponseEntity<>(deviceService.getDevicesByDescription(description), HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<DeviceDTO>> getAvailableDevices() {
        return new ResponseEntity<>(deviceService.getAvailableDevices(), HttpStatus.OK);
    }
    
    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable("deviceId") Long deviceId) {
        return new ResponseEntity<>(deviceService.getDeviceById(deviceId), HttpStatus.OK);
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<DeviceDTO> updateDevice(@RequestBody DeviceDTO device, @PathVariable("deviceId") Long id) {
        return new ResponseEntity<>(deviceService.updateDevice(device, id), HttpStatus.OK);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Long> deleteDevice(@PathVariable("deviceId") Long deviceId) {
        return new ResponseEntity<>(deviceService.deleteDeviceById(deviceId), HttpStatus.OK);
    }
}
