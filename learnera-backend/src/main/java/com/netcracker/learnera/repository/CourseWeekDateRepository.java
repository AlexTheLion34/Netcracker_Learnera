package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.template.CourseWeekDate;
import com.netcracker.learnera.entity.template.CourseWeekDateId;
import org.springframework.data.repository.CrudRepository;

public interface CourseWeekDateRepository extends CrudRepository<CourseWeekDate, CourseWeekDateId> {
}
