package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.Course;
import com.netcracker.learnera.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController extends BasicController<Course, Long> {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        super(courseService);
        this.courseService = courseService;
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<Iterable<Course>> findAllByTeacherId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courseService.findAllByTeacherId(id), HttpStatus.OK);
    }

}
