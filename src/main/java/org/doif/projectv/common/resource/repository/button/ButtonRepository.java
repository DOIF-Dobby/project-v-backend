package org.doif.projectv.common.resource.repository.button;

import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ButtonRepository extends JpaRepository<Button, Long> {

    List<Button> findAllByPage(Page page);

    @Query(
            "select distinct b " +
            "from Button b " +
            "join RoleResource rr on b.id = rr.resource.id " +
            "where b.page = :page " +
            "and b.status = :status"
    )
    List<Button> findAllByPageAndStatus(@Param("page") Page page, @Param("status") EnableStatus status);
}
