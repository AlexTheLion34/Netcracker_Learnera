package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.repository.UserRepository;
import com.netcracker.learnera.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws EntityAlreadyExistsException {
        userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
