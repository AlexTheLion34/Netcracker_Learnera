package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.GroupMessage;
import com.netcracker.learnera.service.GroupMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/group-messages")
public class GroupMessageController extends BasicController<GroupMessage, Long> {

    private GroupMessageService groupMessageService;

    public GroupMessageController(GroupMessageService groupMessageService) {
        super(groupMessageService);
        this.groupMessageService = groupMessageService;
    }

    @GetMapping("/destination/{id}")
    public ResponseEntity<Iterable<GroupMessage>> findAllByDestinationLessonId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(groupMessageService.findAllByDestinationGroupId(id), HttpStatus.OK);
    }

}