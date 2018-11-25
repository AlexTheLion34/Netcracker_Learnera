package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.Course;
import com.netcracker.learnera.entity.template.CourseModuleDate;
import com.netcracker.learnera.entity.template.CourseModuleDateId;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.repository.CourseRepository;
import com.netcracker.learnera.repository.CourseModuleDateRepository;
import com.netcracker.learnera.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl extends CrudServiceImpl<Course, Long> implements CourseService {

    private CourseRepository courseRepository;

    @Autowired
    private CourseModuleDateRepository courseModuleDateRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        super(courseRepository);
        this.courseRepository = courseRepository;
    }

    @Override
    public Course create(Course course) throws EntityAlreadyExistsException {
        if (course.getModuleDates() != null) {
            List<CourseModuleDate> temp = course.getModuleDates();
            course.setModuleDates(null);

            course = courseRepository.save(course);
            for (CourseModuleDate wd : temp) {
                wd.setCourse(course);
                wd.setId(new CourseModuleDateId(course.getId(), wd.getId().getModuleId()));
            }
            course.setModuleDates(temp);
        }
        course = courseRepository.save(course);
        return course;
    }

    @Override
    public Iterable<Course> findAllByTeacherId(Long teacherId) {
        return courseRepository.findAllByTeacherId(teacherId);
    }
}
