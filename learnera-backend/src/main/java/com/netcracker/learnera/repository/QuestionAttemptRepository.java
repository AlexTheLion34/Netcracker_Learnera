package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.template.lesson.Question;
import com.netcracker.learnera.entity.template.lesson.QuestionAttempt;
import org.springframework.data.repository.CrudRepository;

public interface QuestionAttemptRepository extends CrudRepository<QuestionAttempt, Long> {

    Iterable<QuestionAttempt> findAllByQuestionId(Long id);

}
