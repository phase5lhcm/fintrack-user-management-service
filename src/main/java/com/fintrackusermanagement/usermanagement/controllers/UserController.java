package com.fintrackusermanagement.usermanagement.controllers;

import com.fintrackusermanagement.usermanagement.model.User;
import com.fintrackusermanagement.usermanagement.repository.UserRepository;
import com.fintrackusermanagement.usermanagement.service.UserManagementService;
import com.fintrackusermanagement.usermanagement.service.UserRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRegisterService userRegisterService;

    @PostMapping("/api/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user, @RequestParam(defaultValue = "false") boolean isAdmin){
        try{
           User savedUser = userRegisterService.registerUser(user, isAdmin);
           return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully" + savedUser.getUsername());
        } catch (Exception error){
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                  "Error registering user: " + error.getMessage()
          );

        }
    }
}
