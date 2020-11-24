package org.doif.projectv.common.user.repository;

import org.doif.projectv.common.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>, UserQueryRepository {
}
