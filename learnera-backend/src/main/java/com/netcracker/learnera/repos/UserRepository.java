package com.netcracker.learnera.repos;

import com.netcracker.learnera.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
