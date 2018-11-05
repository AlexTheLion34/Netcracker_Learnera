package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.Course;
import com.netcracker.learnera.repository.CourseRepository;
import com.netcracker.learnera.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl extends CrudServiceImpl<Course, Long> implements CourseService {

    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        super(courseRepository);
        this.courseRepository = courseRepository;
    }

    @Override
    public Iterable<Course> findAllByTeacherId(Long teacherId) {
        return courseRepository.findAllByTeacherId(teacherId);
    }
}
