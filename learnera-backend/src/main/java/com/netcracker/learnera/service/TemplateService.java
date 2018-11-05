package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.Group;
import com.netcracker.learnera.entity.template.Template;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;

public interface TemplateService extends CrudService<Template, Long> {

    Iterable<Template> findAllByTeacherId(Long id);
    
}
