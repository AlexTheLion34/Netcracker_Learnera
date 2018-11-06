package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.Course;
import com.netcracker.learnera.entity.template.CourseWeekDate;
import com.netcracker.learnera.entity.template.CourseWeekDateId;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.repository.CourseRepository;
import com.netcracker.learnera.repository.CourseWeekDateRepository;
import com.netcracker.learnera.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl extends CrudServiceImpl<Course, Long> implements CourseService {

    private CourseRepository courseRepository;

    @Autowired
    private CourseWeekDateRepository courseWeekDateRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        super(courseRepository);
        this.courseRepository = courseRepository;
    }

    @Override
    public Course create(Course course) throws EntityAlreadyExistsException {
        course = courseRepository.save(course);

        if (course.getWeekDates() != null) {
            for (CourseWeekDate wd : course.getWeekDates()) {
                wd.setCourse(course);
                wd.setId(new CourseWeekDateId(course.getId(), wd.getId().getWeekId()));
                courseWeekDateRepository.save(wd);
            }
        }
        return course;
    }

    @Override
    public Iterable<Course> findAllByTeacherId(Long teacherId) {
        return courseRepository.findAllByTeacherId(teacherId);
    }
}
