package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.Course;

public interface CourseService extends CrudService<Course, Long> {

    Iterable<Course> findAllByTeacherId(Long teacherId);

}
