package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.template.Lesson;
import com.netcracker.learnera.entity.template.Template;
import com.netcracker.learnera.entity.template.Week;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.repository.LessonRepository;
import com.netcracker.learnera.repository.TemplateRepository;
import com.netcracker.learnera.repository.WeekRepository;
import com.netcracker.learnera.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TemplateServiceImpl extends CrudServiceImpl<Template, Long> implements TemplateService {

    private TemplateRepository templateRepository;

    @Autowired
    private WeekRepository weekRepository;
    @Autowired
    private LessonRepository lessonRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository) {
        super(templateRepository);
        this.templateRepository = templateRepository;
    }

    @Override
    public Template create(Template template) throws EntityAlreadyExistsException {
        template = templateRepository.save(template);

        if (template.getWeeks() != null) {
            for (Week week : template.getWeeks()) {
                week.setTemplate(template);
                week = weekRepository.save(week);

                for (Lesson lesson : week.getLessons()) {
                    lesson.setWeek(week);
                    lessonRepository.save(lesson);
                }
            }
        }

        return template;
    }

    @Override
    public Iterable<Template> findAllByTeacherId(Long id) {
        return templateRepository.findAllByTeacherId(id);
    }

}
