package com.fintrackusermanagement.usermanagement.service;
import com.fintrackusermanagement.usermanagement.model.User;
import com.fintrackusermanagement.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserRegisterService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;
        public User registerUser(User user) {
            // Encode the password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }
