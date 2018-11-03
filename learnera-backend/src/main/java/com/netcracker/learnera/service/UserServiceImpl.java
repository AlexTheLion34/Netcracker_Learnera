package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;
import com.netcracker.learnera.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    protected UserRepository userRepository;

    @Override
    public User createUser(User user) throws EntityAlreadyExistsException {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new EntityAlreadyExistsException("User already exists!");
        if (user.getInfo() != null) {
            user.getInfo().setUser(user);
        }
        return userRepository.save(user);
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) throws EntityNotFoundException {
        if (!userRepository.existsByEmail(user.getEmail()))
            throw new EntityNotFoundException("User not found!");
        if (user.getInfo() != null) {
            user.getInfo().setUser(user);
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException("User not found!");
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) throws EntityNotFoundException {
        return userRepository.findByEmail(email);
    }
}
