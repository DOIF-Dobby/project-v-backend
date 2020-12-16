package org.doif.projectv.common.user.repository;

import org.doif.projectv.common.user.dto.UserDto;
import org.doif.projectv.common.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserQueryRepository {

    Optional<User> searchUserFetchRole(String userId);

    Page<User> selectByCondition(UserDto.Search search, Pageable pageable);
}
