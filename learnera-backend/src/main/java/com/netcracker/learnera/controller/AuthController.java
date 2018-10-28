package com.netcracker.learnera.controller;


import java.util.Base64;
import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<User> login(@RequestHeader String authorization)
    {
        String decoded = new String(Base64.getDecoder().decode(authorization.split(" ")[1]));
        String[] info = decoded.split(":");
        User user = userService.findByEmail(info[0]);
        if (user != null && user.getPassword().equals(info[1]))
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody User user) {
        User userFromDb = userService.findByEmail(user.getEmail());
        if (userFromDb != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
