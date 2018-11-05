package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.Group;
import com.netcracker.learnera.repository.GroupRepository;
import com.netcracker.learnera.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupServiceImpl extends CrudServiceImpl<Group, Long> implements GroupService {

    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        super(groupRepository);
        this.groupRepository = groupRepository;
    }


    @Override
    public Iterable<Group> findAllByCuratorId(Long id) {
        return groupRepository.findAllByCuratorId(id);
    }

    @Override
    public Iterable<Group> findAllByCourseId(Long id) {
        return groupRepository.findAllByCourseId(id);
    }

}
