package org.doif.projectv.common.role.repository;

import org.doif.projectv.common.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
