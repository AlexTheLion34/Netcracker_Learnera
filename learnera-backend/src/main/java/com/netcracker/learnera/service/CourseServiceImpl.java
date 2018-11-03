package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.Course;
import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;
import com.netcracker.learnera.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Iterable<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course updateCourse(Course course) throws EntityNotFoundException {
        if (!courseRepository.existsById(course.getId()))
            throw new EntityNotFoundException("Course not found!");
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) throws EntityNotFoundException {

    }
}
