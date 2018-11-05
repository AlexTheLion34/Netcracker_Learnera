package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.template.Lesson;

public interface LessonService extends CrudService<Lesson, Long> {
    Iterable<Lesson> findAllByWeekId(Long id);
}
