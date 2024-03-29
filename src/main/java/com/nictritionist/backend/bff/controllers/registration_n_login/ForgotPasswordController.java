package com.nictritionist.backend.bff.controllers.registration_n_login;

import com.nictritionist.backend.bff.dtos.requests.ResetPasswordDTO;
import com.nictritionist.backend.storage.user.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/forgot_password")
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ForgotPasswordController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        if (userRepository.findByUsername(resetPasswordDTO.getUsername()).isPresent()) {
            User user = userRepository.findByUsername(resetPasswordDTO.getUsername()).get();

            if (userRepository.findByEmail(resetPasswordDTO.getEmail()).isPresent()) {
                user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
                userRepository.save(user);
                return ResponseEntity
                    .ok("Your password has been changed!");
            }
            return ResponseEntity.badRequest().body("Email cannot be found!");
        }
        return ResponseEntity.badRequest().body("Username cannot be found!");
    }

}
