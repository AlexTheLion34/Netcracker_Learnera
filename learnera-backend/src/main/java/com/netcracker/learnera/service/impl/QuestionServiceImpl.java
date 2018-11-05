package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.template.lesson.Question;
import com.netcracker.learnera.repository.QuestionRepository;
import com.netcracker.learnera.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionServiceImpl extends CrudServiceImpl<Question, Long> implements QuestionService {

    private QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        super(questionRepository);
        this.questionRepository = questionRepository;
    }

}