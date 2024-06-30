package com.fintrackusermanagement.usermanagement.controllers;

import com.fintrackusermanagement.usermanagement.model.User;
import com.fintrackusermanagement.usermanagement.service.UserRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRegisterService userRegisterService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user, @RequestParam(defaultValue = "false") boolean isAdmin) {
        logger.info("User management register API called");
        try {
           String hashedPwd =  passwordEncoder.encode(user.getPassword());
           user.setPassword(hashedPwd);
            User savedUser = userRegisterService.registerUser(user, isAdmin);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully: " + savedUser.getUsername());
        } catch (Exception error) {
            logger.error("Error registering user: ", error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Error registering user: " + error.getMessage()
            );
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        logger.info("User management login API called");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                logger.info("User logged in successfully: " + user.getUsername());
                return ResponseEntity.ok("User logged in successfully: " + user.getUsername());
            } else {
                logger.warn("Authentication failed for user: " + user.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
            }
        } catch (AuthenticationException e) {
            logger.error("Error logging in user: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error logging in user: " + e.getMessage());
        }
    }
}
