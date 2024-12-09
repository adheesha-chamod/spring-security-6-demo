package com.demo.springSecurityDemo.security;

import com.demo.springSecurityDemo.entity.User;
import com.demo.springSecurityDemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            log.warn("[loadUserByUsername] User '{}' not found", username);
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(user.get());
    }
}
