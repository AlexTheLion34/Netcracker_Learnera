package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.GroupMessage;
import com.netcracker.learnera.repository.GroupMessageRepository;
import com.netcracker.learnera.service.GroupMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupMessageServiceImpl extends CrudServiceImpl<GroupMessage, Long> implements GroupMessageService {

    private GroupMessageRepository groupMessageRepository;

    public GroupMessageServiceImpl(GroupMessageRepository groupMessageRepository) {
        super(groupMessageRepository);
        this.groupMessageRepository = groupMessageRepository;
    }

    @Override
    public Iterable<GroupMessage> findAllByDestinationGroupId(Long id) {
        return groupMessageRepository.findAllByDestinationGroupId(id);
    }

    @Override
    public Iterable<GroupMessage> findAllBySenderId(Long id) {
        return groupMessageRepository.findAllBySenderId(id);
    }
}
