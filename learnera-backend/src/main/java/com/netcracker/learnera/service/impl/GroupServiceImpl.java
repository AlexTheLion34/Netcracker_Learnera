package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.Course;
import com.netcracker.learnera.entity.Group;
import com.netcracker.learnera.entity.User;
import com.netcracker.learnera.repository.GroupRepository;
import com.netcracker.learnera.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class GroupServiceImpl extends CrudServiceImpl<Group, Long> implements GroupService {

    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        super(groupRepository);
        this.groupRepository = groupRepository;
    }

    @Override
    protected void forwardReferences(Group item) {
        if (item.getStudents() != null) {
            for (User student : item.getStudents()) {
                student.setStudyGroups(new ArrayList<>());
                student.getStudyGroups().add(item);
            }
        }

        if (item.getCourses() != null) {
            for (Course course : item.getCourses()) {
                course.setGroups(new ArrayList<>());
                course.getGroups().add(item);
            }
        }
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
