package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends CrudRepository<Group, Long> {
    Iterable<Group> findAllByCuratorId(Long id);
    @Query("select g from Course c join c.groups g where c.id = :id")
    Iterable<Group> findAllByCourseId(@Param("id") Long id);
}
