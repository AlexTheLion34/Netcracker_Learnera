package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.template.Lesson;
import com.netcracker.learnera.repository.LessonRepository;
import com.netcracker.learnera.service.LessonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LessonServiceImpl extends CrudServiceImpl<Lesson, Long> implements LessonService {

    private LessonRepository lessonRepository;

    @Override
    public Iterable<Lesson> findAllByWeekId(Long id) {
        return lessonRepository.findAllByWeekId(id);
    }

    public LessonServiceImpl(LessonRepository lessonRepository) {
        super(lessonRepository);
        this.lessonRepository = lessonRepository;
    }

}