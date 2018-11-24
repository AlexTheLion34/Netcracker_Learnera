package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends CrudService<User, Long>, UserDetailsService {

    User login(String email, String password);
    User register(User user) throws EntityAlreadyExistsException;
    User findByEmail(String email) throws EntityNotFoundException;
    Iterable<User> findAllByStudyGroupId(Long id);
    Iterable<User> findAllStudents();
    Iterable<User> findAllByCourseId(Long courseId);

    // For auth

}
