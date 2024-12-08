package com.demo.springSecurityDemo.service;

import com.demo.springSecurityDemo.dto.AddUserDTO;
import com.demo.springSecurityDemo.dto.UserDTO;
import com.demo.springSecurityDemo.entity.User;
import com.demo.springSecurityDemo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User addUser(AddUserDTO addUserDTO) {
        User newUser = User.builder()
                .username(addUserDTO.getUsername())
                .password(addUserDTO.getPassword())
                .build();

        return userRepository.save(newUser);
    }

    public UserDTO convertToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}