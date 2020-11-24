package org.doif.projectv.common.user.repository;

import org.doif.projectv.common.user.entity.User;

import java.util.Optional;

public interface UserQueryRepository {

    Optional<User> searchUserFetchRole(String userId);
}
