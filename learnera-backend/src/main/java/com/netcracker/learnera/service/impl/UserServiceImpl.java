package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.entity.enums.UserRole;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;
import com.netcracker.learnera.repository.UserRepository;
import com.netcracker.learnera.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@Transactional
public class UserServiceImpl extends CrudServiceImpl<User, Long> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public User login(String email, String password) {
        if (!userRepository.existsByEmail(email))
            return null;

        User user = userRepository.findByEmail(email);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User register(User user) throws EntityAlreadyExistsException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.create(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!userRepository.existsByEmail(s))
            throw new UsernameNotFoundException("User not found!");

        User user = userRepository.findByEmail(s);
        org.springframework.security.core.userdetails.User.UserBuilder builder = withUsername(s);
        builder.accountExpired(false);
        builder.accountLocked(false);
        builder.credentialsExpired(false);
        builder.password(user.getPassword());
        builder.authorities(user.getRole() == UserRole.STUDENT ? "ROLE_STUDENT" : "ROLE_TEACHER");

        return builder.build();
    }
}
