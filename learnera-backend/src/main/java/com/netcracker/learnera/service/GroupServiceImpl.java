package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.Group;
import com.netcracker.learnera.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Override
    public Iterable<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
