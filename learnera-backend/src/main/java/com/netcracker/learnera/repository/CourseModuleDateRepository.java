package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.template.CourseModuleDate;
import com.netcracker.learnera.entity.template.CourseModuleDateId;
import org.springframework.data.repository.CrudRepository;

public interface CourseModuleDateRepository extends CrudRepository<CourseModuleDate, CourseModuleDateId> {
}
