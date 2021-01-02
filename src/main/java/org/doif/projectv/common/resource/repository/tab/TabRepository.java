package org.doif.projectv.common.resource.repository.tab;

import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.entity.Tab;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TabRepository extends JpaRepository<Tab, Long> {

    List<Tab> findAllByPage(Page page);

    List<Tab> findAllByPageAndStatus(Page page, EnableStatus status);
}
