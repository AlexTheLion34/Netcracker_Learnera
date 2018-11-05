package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.template.lesson.LessonMessage;
import org.springframework.data.repository.CrudRepository;

public interface LessonMessageRepository extends CrudRepository<LessonMessage, Long> {

    Iterable<LessonMessage> findAllByDestinationLessonId(Long id);

}
