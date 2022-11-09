package org.example.service;

import org.example.dto.DeviceDTO;
import org.example.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO updateUser(UserDTO user, String username);
    UserDTO createUser(UserDTO user);
    Long deleteUserById(Long id);
    String getUserRole(String username);
    List<DeviceDTO> getUserAssociatedDevices(String username);
    DeviceDTO associateDevice(String username, Long deviceId);
    Long removeDevice(String username, Long deviceId);
}
