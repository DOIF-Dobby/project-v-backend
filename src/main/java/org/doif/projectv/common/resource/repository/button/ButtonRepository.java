package org.doif.projectv.common.resource.repository.button;

import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ButtonRepository extends JpaRepository<Button, Long> {

    List<Button> findByPage(Page page);
}
