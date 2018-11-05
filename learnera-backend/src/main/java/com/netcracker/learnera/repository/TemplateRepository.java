package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.template.Template;
import org.springframework.data.repository.CrudRepository;

public interface TemplateRepository extends CrudRepository<Template, Long> {
    Iterable<Template> findAllByTeacherId(Long teacherId);
}
