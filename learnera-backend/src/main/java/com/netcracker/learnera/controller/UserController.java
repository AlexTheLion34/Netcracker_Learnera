package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController extends BasicController<User, Long> {

    private UserService userService;

    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @GetMapping("/studyGroup/{id}")
    public ResponseEntity<Iterable<User>> findAllByStudyGroupId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findAllByStudyGroupId(id), HttpStatus.OK);
    }

}
