package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;

public interface UserService extends CrudService<User, Long> {

    User findByEmail(String email) throws EntityNotFoundException;
    Iterable<User> findAllByStudyGroupId(Long id);

}
