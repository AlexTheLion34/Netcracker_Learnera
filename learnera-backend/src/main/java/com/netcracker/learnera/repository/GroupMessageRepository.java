package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.GroupMessage;
import org.springframework.data.repository.CrudRepository;

public interface GroupMessageRepository extends CrudRepository<GroupMessage, Long> {

    Iterable<GroupMessage> findAllByDestinationGroupId(Long id);
    Iterable<GroupMessage> findAllBySenderId(Long id);

}
