package org.example.controller;

import org.example.model.User;
import org.example.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping()
    public Long createUser(@RequestBody User user) {
        userServiceImpl.createUser(user);
        return user.getUserId();
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") Long userId) {
        return userServiceImpl.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable("userId") Long userId) {
       return userServiceImpl.updateUser(user, userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userServiceImpl.deleteUserById(userId);
    }
}
