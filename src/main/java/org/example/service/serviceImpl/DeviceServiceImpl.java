package org.example.service.serviceImpl;

import org.example.model.Device;
import org.example.repository.DeviceRepository;
import org.example.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device getDeviceById(Long id) {
        if (deviceRepository.findById(id).isPresent()) {
            return (Device) deviceRepository.findById(id).get();
        }
        return null;
    }

    public Device updateDevice(Device device, Long id) {
        if (deviceRepository.findById(id).isPresent()) {
            Device deviceToUpdate = deviceRepository.findById(id).get();

            if (device.getDescription().isEmpty() && !device.getDescription().isEmpty()
                    && !"".equals(device.getDescription()) && !"".equals(device.getAddress())) {
                deviceToUpdate.setAddress(device.getAddress());
                deviceToUpdate.setDescription(device.getDescription());
            }

            deviceRepository.save(deviceToUpdate);
        }
        return null;
    }

    public void createDevice(Device device) {
        deviceRepository.save(device);
    }

    public void deleteDeviceById(Long id) {
        deviceRepository.deleteById(id);
    }
}
