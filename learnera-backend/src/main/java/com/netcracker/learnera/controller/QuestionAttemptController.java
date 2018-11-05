package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.template.lesson.QuestionAttempt;
import com.netcracker.learnera.service.QuestionAttemptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question-attempts")
public class QuestionAttemptController extends BasicController<QuestionAttempt, Long> {

    private QuestionAttemptService questionAttemptService;

    public QuestionAttemptController(QuestionAttemptService questionAttemptService) {
        super(questionAttemptService);
        this.questionAttemptService = questionAttemptService;
    }

    @GetMapping("/questionAttempt/{id}")
    public ResponseEntity<Iterable<QuestionAttempt>> findAllByQuestionId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(questionAttemptService.findAllByQuestionId(id), HttpStatus.OK);
    }

}