package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.template.lesson.LessonMessage;
import com.netcracker.learnera.repository.LessonMessageRepository;
import com.netcracker.learnera.service.LessonMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LessonMessageServiceImpl extends CrudServiceImpl<LessonMessage, Long> implements LessonMessageService {

    private LessonMessageRepository lessonMessageRepository;

    @Override
    public Iterable<LessonMessage> findAllByDestinationLessonId(Long id) {
        return lessonMessageRepository.findAllByDestinationLessonId(id);
    }

    public LessonMessageServiceImpl(LessonMessageRepository lessonMessageRepository) {
        super(lessonMessageRepository);
        this.lessonMessageRepository = lessonMessageRepository;
    }

}
