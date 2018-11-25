package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.template.lesson.Question;
import com.netcracker.learnera.entity.template.lesson.QuestionAttempt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QuestionAttemptRepository extends CrudRepository<QuestionAttempt, Long> {

    Iterable<QuestionAttempt> findAllByQuestionId(Long id);
    @Query("select qa from Assignment a join a.questions q join q.attempts qa " +
            "where a.module.id = :moduleId and qa.student.id = :userId")
    Iterable<QuestionAttempt> findFirstByUserIdModuleId(@Param("userId") Long userId, @Param("moduleId") Long moduleId);

    Iterable<QuestionAttempt> findAllByCourseId(Long courseId);
}
