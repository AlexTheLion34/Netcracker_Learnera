package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;

public interface UserService {

    User createUser(User user) throws EntityAlreadyExistsException;
    Iterable<User> getAllUsers();
    User updateUser(User user) throws EntityNotFoundException;
    void deleteUser(Long id) throws EntityNotFoundException;

    User findByEmail(String email) throws EntityNotFoundException;

}
