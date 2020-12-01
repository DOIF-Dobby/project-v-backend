package org.doif.projectv.common.resource.repository.label;

import org.doif.projectv.common.resource.entity.Label;
import org.doif.projectv.common.resource.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {

    List<Label> findAllByPage(Page page);
}
