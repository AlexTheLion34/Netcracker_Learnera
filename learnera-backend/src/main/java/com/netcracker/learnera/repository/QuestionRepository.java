package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.template.lesson.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
