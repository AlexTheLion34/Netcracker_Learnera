package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);

    @Query("select u from User u where u.role = 'STUDENT'")
    Iterable<User> findAllByRole();

    @Query("select u from Group g join g.students u where g.id = :id")
    Iterable<User> findAllByStudyGroupId(@Param("id") Long id);
}
