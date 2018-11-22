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

    @GetMapping("/user/{userId}/week/{weekId}/latest")
    public ResponseEntity<Iterable<QuestionAttempt>> findLatestUserWeekAttempts(
            @PathVariable("userId") Long userId, @PathVariable("weekId") Long weekId
    ) {
        return new ResponseEntity<>(questionAttemptService.findFirstByUserIdWeekId(userId, weekId), HttpStatus.OK);
    }

}