package org.example.dto;

import lombok.Data;
import org.example.model.Device;
import org.example.util.Role;

import java.util.List;

@Data
public class UserDTO {
    private String username;
    private Role role;
    private List<Device> associatedDevices;

}
