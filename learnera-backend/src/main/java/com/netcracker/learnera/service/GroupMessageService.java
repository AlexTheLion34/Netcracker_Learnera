package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.GroupMessage;

public interface GroupMessageService extends CrudService<GroupMessage, Long> {

    Iterable<GroupMessage> findAllByDestinationGroupId(Long id);
    Iterable<GroupMessage> findAllBySenderId(Long id);

}
