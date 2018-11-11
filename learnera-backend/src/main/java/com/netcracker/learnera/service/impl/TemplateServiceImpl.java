package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.template.Lesson;
import com.netcracker.learnera.entity.template.Template;
import com.netcracker.learnera.entity.template.Week;
import com.netcracker.learnera.entity.template.lesson.Assignment;
import com.netcracker.learnera.entity.template.lesson.MultipleChoiceQuestion;
import com.netcracker.learnera.entity.template.lesson.MultipleChoiceVariant;
import com.netcracker.learnera.entity.template.lesson.Question;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;
import com.netcracker.learnera.repository.*;
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
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private MultipleChoiceVariantRepository multipleChoiceVariantRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository) {
        super(templateRepository);
        this.templateRepository = templateRepository;
    }

    @Override
    protected void forwardReferences(Template template) {
        if (template.getWeeks() != null) {
            for (Week week : template.getWeeks()) {
                week.setTemplate(template);

                for (Lesson lesson : week.getLessons()) {
                    lesson.setWeek(week);

                    if (lesson instanceof Assignment) {
                        Assignment assignment = (Assignment) lesson;
                        for (Question question : assignment.getQuestions()) {
                            question.setAssignment(assignment);

                            if (question instanceof MultipleChoiceQuestion) {
                                MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;

                                for (MultipleChoiceVariant variant : multipleChoiceQuestion.getVariants()) {
                                    variant.setQuestion(multipleChoiceQuestion);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Iterable<Template> findAllByTeacherId(Long id) {
        return templateRepository.findAllByTeacherId(id);
    }

}
