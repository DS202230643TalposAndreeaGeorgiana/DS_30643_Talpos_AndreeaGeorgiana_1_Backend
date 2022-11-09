package org.example.dto;

import lombok.Data;
import org.example.model.Device;
import org.example.util.Role;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private List<Device> associatedDevices;

}
