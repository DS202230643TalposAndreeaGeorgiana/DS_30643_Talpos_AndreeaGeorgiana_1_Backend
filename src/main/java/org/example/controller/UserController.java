package org.example.controller;

import org.example.dto.DeviceDTO;
import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/data/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }


    @PutMapping("/{username}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user, @PathVariable("username") String username) {
        return new ResponseEntity<>(userService.updateUser(user, username), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.deleteUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/role/{username}")
    public ResponseEntity<String> getUserRole(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUserRole(username), HttpStatus.OK);
    }

    @GetMapping("/devices/{username}")
    public ResponseEntity<List<DeviceDTO>> getUserAssociatedDevices(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUserAssociatedDevices(username), HttpStatus.OK);
    }

    @PostMapping("/devices/{username}/{id}")
    public ResponseEntity<DeviceDTO> associateDevice(@PathVariable("username") String username, @PathVariable Long id) {
        return new ResponseEntity<>(userService.associateDevice(username, id), HttpStatus.OK);
    }

    @PostMapping("/devices/remove/{username}/{id}")
    public ResponseEntity<Long> removeDevice(@PathVariable("username") String username, @PathVariable Long id) {
        return new ResponseEntity<>(userService.removeDevice(username, id), HttpStatus.OK);
    }
}
