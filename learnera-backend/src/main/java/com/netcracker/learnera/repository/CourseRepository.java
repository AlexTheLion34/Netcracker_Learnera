package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends CrudRepository<Course, Long> {
    @Query(value = "SELECT c FROM Course c JOIN c.template t WHERE t.teacher.id = :teacherId")
    Iterable<Course> findAllByTeacherId(@Param("teacherId") Long teacherId);
}
