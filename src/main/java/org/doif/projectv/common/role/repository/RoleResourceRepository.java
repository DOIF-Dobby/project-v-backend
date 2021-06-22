package org.doif.projectv.common.role.repository;

import org.doif.projectv.common.role.dto.RoleResourceDto;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.entity.RoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Long>, RoleResourceQueryRepository {

    @Query("select rr " +
            "from Menu m " +
            "join RoleResource rr on m.id = rr.resource.id " +
            "where rr.role.id = :roleId")
    List<RoleResource> selectMenuRoleResources(@Param("roleId") Long roleId);

    @Query("select rr " +
            "from MenuCategory mc " +
            "join RoleResource rr on mc.id = rr.resource.id " +
            "where rr.role.id = :roleId")
    List<RoleResource> selectMenuCategoryRoleResources(@Param("roleId") Long roleId);

    @Modifying(clearAutomatically = true)
    @Query("delete from RoleResource rr " +
            "where rr.role.id = :roleId " +
            "and not exists (select 1 from Menu m where m.id = rr.resource.id) " +
            "and not exists (select 1 from MenuCategory mc where mc.id = rr.resource.id)")
    void deleteByRoleId(@Param("roleId") Long roleId);

    @Modifying(clearAutomatically = true)
    @Query("delete from RoleResource rr " +
            "where  " +
            "exists (select 1 from Menu m where m.id = rr.resource.id and rr.role.id = :roleId) " +
            "or exists (select 1 from MenuCategory mc where mc.id = rr.resource.id and rr.role.id = :roleId)")
    void deleteMenuByRoleId(@Param("roleId") Long id);
}
