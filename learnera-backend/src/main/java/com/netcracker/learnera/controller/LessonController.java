package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.template.Lesson;
import com.netcracker.learnera.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lessons")
public class LessonController extends BasicController<Lesson, Long> {

    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
        super(lessonService);
        this.lessonService = lessonService;
    }

    @GetMapping("/module/{id}")
    public ResponseEntity<Iterable<Lesson>> findAllByModuleId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(lessonService.findAllByModuleId(id), HttpStatus.OK);
    }

}