package org.doif.projectv.common.role.repository;

import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.entity.RoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from RoleResource rr where rr.role.id = :roleId")
    void deleteByRoleId(@Param("roleId") Long roleId);
}
