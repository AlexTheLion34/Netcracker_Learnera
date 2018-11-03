package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.Course;
import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;

public interface CourseService {

    Course createCourse(Course course) throws EntityAlreadyExistsException;
    Iterable<Course> getAllCourses();
    Course updateCourse(Course course) throws EntityNotFoundException;
    void deleteCourse(Long id) throws EntityNotFoundException;

}
