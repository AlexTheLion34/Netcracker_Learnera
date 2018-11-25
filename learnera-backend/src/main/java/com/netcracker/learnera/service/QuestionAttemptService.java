package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.template.lesson.QuestionAttempt;

public interface QuestionAttemptService extends CrudService<QuestionAttempt, Long> {

    Iterable<QuestionAttempt> findAllByQuestionId(Long id);
    Iterable<QuestionAttempt> scoreAllAttempts(Iterable<QuestionAttempt> attempts);
    Iterable<QuestionAttempt> findFirstByUserIdModuleId(Long userId, Long moduleId);
    Iterable<QuestionAttempt> findAllCourseAttempts(Long courseId);
}
