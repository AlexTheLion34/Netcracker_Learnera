package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.template.Lesson;
import org.springframework.data.repository.CrudRepository;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
    Iterable<Lesson> findAllByModuleId(Long id);
}
