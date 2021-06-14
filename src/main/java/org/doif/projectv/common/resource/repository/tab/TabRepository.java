package org.doif.projectv.common.resource.repository.tab;

import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.entity.Tab;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TabRepository extends JpaRepository<Tab, Long> {

    List<Tab> findAllByPage(Page page);

    @Query(
            "select t " +
            "from Tab t " +
            "join RoleResource rr on t.id = rr.resource.id " +
            "where t.page = :page " +
            "and t.status = :status"
    )
    List<Tab> findAllByPageAndStatus(@Param("page") Page page, @Param("status") EnableStatus status);
}
