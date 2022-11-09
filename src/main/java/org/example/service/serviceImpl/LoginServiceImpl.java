package org.example.service.serviceImpl;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.String.format;

@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User customer;
        if(userRepository.findByUsername(username).isPresent()) {
            customer = userRepository.findByUsername(username).get();
        } else {
            throw new UsernameNotFoundException(format("User with username - %s, not found", username));
        }
        return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(), new ArrayList<>(Collections.singleton(customer.getRole())));
    }
}
