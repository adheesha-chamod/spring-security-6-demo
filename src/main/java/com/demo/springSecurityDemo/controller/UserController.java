package com.demo.springSecurityDemo.controller;

import com.demo.springSecurityDemo.dto.AddUserDTO;
import com.demo.springSecurityDemo.dto.LoginUserDTO;
import com.demo.springSecurityDemo.dto.UserDTO;
import com.demo.springSecurityDemo.entity.User;
import com.demo.springSecurityDemo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody AddUserDTO addUserDTO) {
        User newUser = userService.addUser(addUserDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.convertToUserDTO(newUser));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        Optional<User> user = userService.getUserByUsername(loginUserDTO.getUsername());

        if (user.isEmpty()) {
            log.warn("[loginUser] User '{}' not found", loginUserDTO.getUsername());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.convertToUserDTO(user.get()));
        }
    }
}
