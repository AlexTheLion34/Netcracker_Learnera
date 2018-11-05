package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.template.lesson.QuestionAttempt;
import com.netcracker.learnera.repository.QuestionAttemptRepository;
import com.netcracker.learnera.service.QuestionAttemptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionAttemptServiceImpl extends CrudServiceImpl<QuestionAttempt, Long> implements QuestionAttemptService {

    private QuestionAttemptRepository questionAttemptRepository;

    @Override
    public Iterable<QuestionAttempt> findAllByQuestionId(Long id) {
        return questionAttemptRepository.findAllByQuestionId(id);
    }

    public QuestionAttemptServiceImpl(QuestionAttemptRepository questionAttemptRepository) {
        super(questionAttemptRepository);
        this.questionAttemptRepository = questionAttemptRepository;
    }

}