package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.Group;

public interface GroupService extends CrudService<Group, Long> {

    Iterable<Group> findAllByCuratorId(Long id);
    Iterable<Group> findAllByCourseId(Long id);
    
}
