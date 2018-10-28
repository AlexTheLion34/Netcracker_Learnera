package com.netcracker.learnera.repository;

import com.netcracker.learnera.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
}
