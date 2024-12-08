package com.demo.springSecurityDemo.service;

import com.demo.springSecurityDemo.dto.AddUserDTO;
import com.demo.springSecurityDemo.dto.UserDTO;
import com.demo.springSecurityDemo.entity.User;
import com.demo.springSecurityDemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public User addUser(AddUserDTO addUserDTO) {
        User newUser = User.builder()
                .username(addUserDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(addUserDTO.getPassword()))
                .build();

        return userRepository.save(newUser);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDTO convertToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
