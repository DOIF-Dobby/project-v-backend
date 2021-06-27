package org.doif.projectv.common.resource.repository.menu;

import org.doif.projectv.common.resource.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByUrl(String url);

    @Query(
            "select distinct m " +
            "from Menu m " +
            "join RoleResource rr on rr.resource.id = m.id " +
            "join UserRole ur on ur.role.id = rr.role.id " +
            "where ur.user.id = :userId " +
            "and m.name like concat('%',:search,'%') "
    )
    List<Menu> findAccessibleMenuByUserId(@Param("userId") String userId, @Param("search") String search);
}
