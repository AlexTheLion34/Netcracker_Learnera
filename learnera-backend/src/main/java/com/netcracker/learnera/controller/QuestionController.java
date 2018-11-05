package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.template.lesson.Question;
import com.netcracker.learnera.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuestionController extends BasicController<Question, Long> {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        super(questionService);
        this.questionService = questionService;
    }

}