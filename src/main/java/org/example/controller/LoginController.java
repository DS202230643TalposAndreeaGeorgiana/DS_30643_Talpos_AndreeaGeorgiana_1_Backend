package org.example.controller;

import org.example.dto.UserDTO;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.example.util.JwtUtil;
import org.example.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> generateToken(@RequestBody UserDTO user) throws Exception {
        Optional<User> currentUser = userRepository.findByUsername(user.getUsername());
        System.out.println(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (currentUser.isPresent() && new BCryptPasswordEncoder().matches(user.getPassword(), currentUser.get().getPassword())) {
            return new ResponseEntity<>(jwtUtil.generateToken(user.getUsername()), HttpStatus.ACCEPTED);
        } else if (currentUser.isEmpty() && "admin".equals(user.getUsername())) {
            userService.createUser(user);
        }
        throw new Exception("invalid username/password");
    }
}
