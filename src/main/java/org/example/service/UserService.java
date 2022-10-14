package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User updateUser(User user, Long id) {
        User userToUpdate = userRepository.findById(id).get();

        if(!user.getUsername().isEmpty() && !"".equals(user.getUsername())) {
            userToUpdate.setUsername(user.getUsername());
        }

        if(!user.getPassword().isEmpty() && !"".equals(user.getPassword())) {
            userToUpdate.setPassword(user.getPassword());
        }

        return userRepository.save(userToUpdate);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
