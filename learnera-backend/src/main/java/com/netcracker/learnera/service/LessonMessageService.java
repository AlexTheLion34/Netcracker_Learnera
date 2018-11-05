package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.template.lesson.LessonMessage;

public interface LessonMessageService extends CrudService<LessonMessage, Long> {

    Iterable<LessonMessage> findAllByDestinationLessonId(Long id);

}
