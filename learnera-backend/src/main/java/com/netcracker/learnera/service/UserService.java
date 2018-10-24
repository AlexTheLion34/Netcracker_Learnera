package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.User;

public interface UserService {

    User saveUser(User users);

    User findByEmail(String email);

}
