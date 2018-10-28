package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.User;
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
    public User saveUser(User user) {
        if (user != null && user.getInfo() != null) {
            user.getInfo().setUser(user);
        }
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
