package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.template.lesson.Question;
import com.netcracker.learnera.entity.template.lesson.QuestionAttempt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QuestionAttemptRepository extends CrudRepository<QuestionAttempt, Long> {

    Iterable<QuestionAttempt> findAllByQuestionId(Long id);
    @Query("select qa from Assignment a join a.questions q join q.attempts qa " +
            "where a.week.id = :weekId and qa.student.id = :userId")
    Iterable<QuestionAttempt> findFirstByUserIdWeekId(@Param("userId") Long userId, @Param("weekId") Long weekId);

    Iterable<QuestionAttempt> findAllByCourseId(Long courseId);
}
