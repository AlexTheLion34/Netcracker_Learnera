package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.template.Template;
import com.netcracker.learnera.repository.TemplateRepository;
import com.netcracker.learnera.service.TemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TemplateServiceImpl extends CrudServiceImpl<Template, Long> implements TemplateService {

    private TemplateRepository templateRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository) {
        super(templateRepository);
        this.templateRepository = templateRepository;
    }

    @Override
    public Iterable<Template> findAllByTeacherId(Long id) {
        return templateRepository.findAllByTeacherId(id);
    }

}
