/**
 * Allows a user to register as an admin or a regular user without admin provileges
 * TODO - should I create a separate route for admins?
 * */
package com.fintrackusermanagement.usermanagement.service;
import com.fintrackusermanagement.usermanagement.model.Role;
import com.fintrackusermanagement.usermanagement.model.User;
import com.fintrackusermanagement.usermanagement.repository.RoleRepository;
import com.fintrackusermanagement.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserRegisterService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

       @Autowired
         private RoleRepository roleRepository;
        public User registerUser(User user, boolean isAdmin) {
            // Encode the password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // allow a user to register as an admin by sending appropriate details during registration process
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByRoleName("ROLE_USER");
            roles.add(userRole);

            if (isAdmin) {
                Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
                roles.add(adminRole);
            }

            user.setRoles(roles);
            return userRepository.save(user);
        }
    }
