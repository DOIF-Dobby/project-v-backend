package org.doif.projectv.common.resource.repository.button;

import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ButtonRepository extends JpaRepository<Button, Long> {

    List<Button> findAllByPage(Page page);

    List<Button> findAllByPageAndStatus(Page page, EnableStatus status);
}
