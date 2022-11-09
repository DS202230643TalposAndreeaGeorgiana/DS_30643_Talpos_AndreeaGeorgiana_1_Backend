package org.example.service.serviceImpl;

import org.example.controller.handlers.exceptions.ResourceNotFoundException;
import org.example.dto.DeviceDTO;
import org.example.dto.UserDTO;
import org.example.model.Device;
import org.example.model.User;
import org.example.repository.DeviceRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<UserDTO> getAllUsers() {
        List<User> customers = userRepository.findAll();
        return customers.stream()
                .map(x -> modelMapper.map(x, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with ID " + id);
        }
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with username " + username);
        }
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO updateUser(final UserDTO user, final String username) {
        Optional<User> userToUpdate = userRepository.findByUsername(username);

        if(userToUpdate.isEmpty()) {
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with username " + username);
        }
        if(!user.getUsername().isEmpty() && !"".equals(user.getUsername())) {
            userToUpdate.get().setUsername(user.getUsername());
        }

        if(!user.getPassword().isEmpty() && !"".equals(user.getPassword())) {
            userToUpdate.get().setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }

        return modelMapper.map(userRepository.save(userToUpdate.get()), UserDTO.class);
    }

    public UserDTO createUser(UserDTO user) {
        User customerToCreate = modelMapper.map(user, User.class);
        customerToCreate.setPassword(new BCryptPasswordEncoder().encode(customerToCreate.getPassword()));
        return modelMapper.map(userRepository.save(customerToCreate), UserDTO.class);
    }

    public Long deleteUserById(Long id) {
        Optional<User> userToDelete = userRepository.findById(id);

        if(userToDelete.isEmpty()) {
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with ID " + id);
        }
        for(Device d : userToDelete.get().getAssociatedDevices()) {
            d.setUser(null);
        }
        userToDelete.get().getAssociatedDevices().clear();
        userRepository.deleteById(id);
        return id;
    }

    public String getUserRole(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        System.out.println(user.get().getRole());
        return user.map(value -> value.getRole().name())
                .orElse(null);
    }

    public List<DeviceDTO> getUserAssociatedDevices(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with username " + username);
        }
        List<Device> userDevices = user.get().getAssociatedDevices();
        return userDevices
                .stream()
                .map(device -> modelMapper
                                .map(device, DeviceDTO.class))
                        .collect(Collectors.toList());
    }

    public DeviceDTO associateDevice(String username,Long deviceId) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Device> device = deviceRepository.findById(deviceId);
        if(user.isEmpty()) {
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with username " + username);
        }
        if(device.isEmpty()) {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id " + deviceId);
        }
        user.get().addDevice(device.get());
        userRepository.save(user.get());
        return modelMapper.map(device, DeviceDTO.class);
    }

    public Long removeDevice(String username, Long deviceId) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Device> device = deviceRepository.findById(deviceId);
        if(user.isEmpty()) {
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with username " + username);
        }
        if(device.isEmpty()) {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id " + deviceId);
        }
        user.get().removeDevice(device.get());
        userRepository.save(user.get());
        return device.get().getDeviceId();
    }

}
