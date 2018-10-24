package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.User;
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

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        User userFromDb = userService.findByEmail(user.getEmail());
        if (userFromDb != null)
            return new ResponseEntity<>("User exists", HttpStatus.BAD_REQUEST);
        userService.saveUser(user);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }
}
