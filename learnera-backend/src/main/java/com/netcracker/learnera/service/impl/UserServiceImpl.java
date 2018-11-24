package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;
import com.netcracker.learnera.repository.UserRepository;
import com.netcracker.learnera.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends CrudServiceImpl<User, Long> implements UserService {

    private UserRepository userRepository;

    @Override
    public Iterable<User> findAllStudents() {
        return userRepository.findAllByRole();
    }

    @Override
    public Iterable<User> findAllByCourseId(Long courseId) {
        return userRepository.findAllByCourseId(courseId);
    }

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<User> findAllByStudyGroupId(Long id) {
        return userRepository.findAllByStudyGroupId(id);
    }

    @Override
    public User create(User user) throws EntityAlreadyExistsException {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new EntityAlreadyExistsException("User already exists!");
        if (user.getInfo() != null) {
            user.getInfo().setUser(user);
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user) throws EntityNotFoundException {
        if (!userRepository.existsByEmail(user.getEmail()))
            throw new EntityNotFoundException("User not found!");
        if (user.getInfo() != null) {
            user.getInfo().setUser(user);
        }
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
