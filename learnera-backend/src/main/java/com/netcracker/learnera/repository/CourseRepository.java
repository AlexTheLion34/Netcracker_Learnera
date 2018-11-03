package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
