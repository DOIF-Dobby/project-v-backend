package org.doif.projectv.common.resource.repository.label;

import org.doif.projectv.common.resource.entity.Label;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {

    List<Label> findAllByPage(Page page);

    List<Label> findAllByPageAndStatus(Page page, EnableStatus status);
}
