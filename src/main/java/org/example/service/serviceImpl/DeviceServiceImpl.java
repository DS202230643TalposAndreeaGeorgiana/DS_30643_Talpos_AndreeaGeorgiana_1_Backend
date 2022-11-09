package org.example.service.serviceImpl;

import org.example.controller.handlers.exceptions.ResourceNotFoundException;
import org.example.dto.DeviceDTO;
import org.example.model.Device;
import org.example.repository.DeviceRepository;
import org.example.service.DeviceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DeviceRepository deviceRepository;


    public List<DeviceDTO> getAllDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(x -> modelMapper.map(x, DeviceDTO.class))
                .collect(Collectors.toList());
    }

    public List<DeviceDTO> getDevicesByDescription(String description) {
        List<DeviceDTO> devices = this.getAvailableDevices();
        return devices.stream()
                .filter(x -> x.getDescription().equals(description))
                .collect(Collectors.toList());
    }

    public List<DeviceDTO> getAvailableDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .filter(x -> x.getUser()==null)
                .map(x -> modelMapper.map(x, DeviceDTO.class))
                .collect(Collectors.toList());
    }

    public DeviceDTO getDeviceById(Long id) {
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isEmpty()) {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with ID " +id);
        }
        return modelMapper.map(device, DeviceDTO.class);
    }

    public DeviceDTO updateDevice(DeviceDTO device, Long id) {
        Optional<Device> deviceToUpdate = deviceRepository.findById(id);

        if (deviceToUpdate.isEmpty()) {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with ID " +id);
        }
        deviceToUpdate.get().setDescription(device.getDescription());
        deviceToUpdate.get().setAddress(device.getAddress());
        deviceToUpdate.get().setMaximumHourlyConsumption(device.getMaximumHourlyConsumption());
        return modelMapper.map(deviceRepository.save(deviceToUpdate.get()), DeviceDTO.class);
    }

    public DeviceDTO createDevice(DeviceDTO device) {
        Device deviceToCreate = modelMapper.map(device, Device.class);
        return modelMapper.map(deviceRepository.save(deviceToCreate), DeviceDTO.class);
    }

    public Long deleteDeviceById(Long id) {
        Optional<Device> deviceToDelete = deviceRepository.findById(id);
        if (deviceToDelete.isEmpty()) {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with ID " +id);
        }
        deviceRepository.deleteById(id);
        return id;
    }
}
