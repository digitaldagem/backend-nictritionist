package com.nictritionist.backend.security.services;

import com.nictritionist.backend.storage.user.User;
import com.nictritionist.backend.storage.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        try {
            if (userRepository.findByUsername(username).isPresent()) {
                user = userRepository.findByUsername(username).get();
            }
        }
        catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return UserDetailsImpl.build(user);
    }

}
