package com.nictritionist.backend.services;

import com.nictritionist.backend.storage.user.*;
import com.nictritionist.backend.storage.user.role.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

import org.slf4j.*;

@Component
public class LoadDataService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LoadDataService.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoadDataService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if(userRepository.findByUsername("user.dagem").isEmpty()) {
            userRepository.save(
                    new User(
                            "user.dagem",
                            "digitaldagem@gmail.com",
                            passwordEncoder.encode("password"),
                            new ArrayList<>(
                                    Collections.singletonList(
                                            roleRepository.save(
                                                    new Role(ERole.ROLE_USER)
                                            )
                                    )
                            )
                    )
            );
        }

        if(userRepository.findByUsername("admin.dagem").isEmpty()) {
            userRepository.save(
                    new User(
                            "admin.dagem",
                            "dagemh@gmail.com",
                            passwordEncoder.encode("password"),
                            new ArrayList<>(
                                    Collections.singletonList(
                                            roleRepository.save(
                                                    new Role(ERole.ROLE_ADMIN)
                                            )
                                    )
                            )
                    )
            );
        }

        userRepository.findAll().forEach(illness -> logger.info("{}", illness.getUsername()));
    }
}
