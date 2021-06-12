package com.nictritionist.backend.bff.controllers.registration_n_login;

import com.nictritionist.backend.bff.dtos.requests.*;
import com.nictritionist.backend.bff.dtos.responses.MessageDTO;
import com.nictritionist.backend.storage.user.*;
import com.nictritionist.backend.storage.user.role.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/register")
public class RegistrationController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignupDTO userSignUpDTO) {
        if (userRepository.findByUsername(userSignUpDTO.getFirstName()+"."+userSignUpDTO.getLastName()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageDTO("Error: Username is already taken!"));
        }
        if (userRepository.findByEmail(userSignUpDTO.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageDTO("Error: Email is already in use!"));
        }
        User user = new User(userSignUpDTO.getFirstName()+"."+userSignUpDTO.getLastName(),
                userSignUpDTO.getEmail(),
                passwordEncoder.encode(userSignUpDTO.getPassword()));
        List<Role> roles = new ArrayList<>();
        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
            Role userRole = new Role(ERole.ROLE_USER);
            roleRepository.save(userRole);
        }
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: User role not found."));
        roles.add(userRole);
        user.setRoles(roles);
        user = userRepository.save(user);
        return ResponseEntity.ok(user.getUserDTO());
    }

    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminSignupDTO adminSignUpDTO) {
        if (userRepository.findByUsername(
                adminSignUpDTO.getFirstName()+"."+ adminSignUpDTO.getLastName()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageDTO("Error: Username is already registered with another account!"));
        }
        if (userRepository.findByEmail(adminSignUpDTO.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageDTO("Error: Email is already registered with another account!"));
        }
        User user = new User(adminSignUpDTO.getFirstName() + "." + adminSignUpDTO.getLastName(),
                adminSignUpDTO.getEmail(),
                passwordEncoder.encode(adminSignUpDTO.getPassword()));
        List<Role> roles = new ArrayList<>();
        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            Role userRole = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(userRole);
        }
        Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Admin Role not found."));
        roles.add(userRole);
        user.setRoles(roles);
        return ResponseEntity.ok(userRepository.save(user).getUserDTO());
    }
}
