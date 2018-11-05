package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.Group;
import com.netcracker.learnera.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
public class GroupController extends BasicController<Group, Long> {

    private GroupService groupService;

    public GroupController(GroupService groupService) {
        super(groupService);
        this.groupService = groupService;
    }

    @GetMapping("/curator/{curatorId}")
    public ResponseEntity<Iterable<Group>> getGroupsByCuratorId(@PathVariable("curatorId") Long curatorId) {
        return new ResponseEntity<>(groupService.findAllByCuratorId(curatorId), HttpStatus.OK);
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<Iterable<Group>> findAllByCourseId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(groupService.findAllByCourseId(id), HttpStatus.OK);
    }

}
