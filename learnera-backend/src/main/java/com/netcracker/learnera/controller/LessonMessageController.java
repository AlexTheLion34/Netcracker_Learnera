package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.template.lesson.LessonMessage;
import com.netcracker.learnera.service.LessonMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lesson-messages")
public class LessonMessageController extends BasicController<LessonMessage, Long> {

    private LessonMessageService lessonMessageService;

    public LessonMessageController(LessonMessageService lessonMessageService) {
        super(lessonMessageService);
        this.lessonMessageService = lessonMessageService;
    }

    @GetMapping("/destination/{id}")
    public ResponseEntity<Iterable<LessonMessage>> findAllByDestinationLessonId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(lessonMessageService.findAllByDestinationLessonId(id), HttpStatus.OK);
    }

}
