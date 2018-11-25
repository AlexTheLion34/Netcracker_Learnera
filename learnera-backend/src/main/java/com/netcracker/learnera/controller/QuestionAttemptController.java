package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.template.lesson.QuestionAttempt;
import com.netcracker.learnera.service.QuestionAttemptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question-attempts")
public class QuestionAttemptController extends BasicController<QuestionAttempt, Long> {

    private QuestionAttemptService questionAttemptService;

    public QuestionAttemptController(QuestionAttemptService questionAttemptService) {
        super(questionAttemptService);
        this.questionAttemptService = questionAttemptService;
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<Iterable<QuestionAttempt>> findAllByQuestionId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(questionAttemptService.findAllByQuestionId(id), HttpStatus.OK);
    }

    @PostMapping("/batch")
    public ResponseEntity<Iterable<QuestionAttempt>> scoreAllAttempts(@RequestBody Iterable<QuestionAttempt> attempts) {
        return new ResponseEntity<>(questionAttemptService.scoreAllAttempts(attempts), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/module/{moduleId}/latest")
    public ResponseEntity<Iterable<QuestionAttempt>> findLatestUserModuleAttempts(
            @PathVariable("userId") Long userId, @PathVariable("moduleId") Long moduleId
    ) {
        return new ResponseEntity<>(questionAttemptService.findFirstByUserIdModuleId(userId, moduleId), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Iterable<QuestionAttempt>> findAllCourseAttempts(
            @PathVariable("courseId") Long courseId
    ) {
        return new ResponseEntity<>(questionAttemptService.findAllCourseAttempts(courseId), HttpStatus.OK);
    }
}