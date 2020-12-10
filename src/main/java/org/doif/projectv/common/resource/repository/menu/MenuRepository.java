package org.doif.projectv.common.resource.repository.menu;

import org.doif.projectv.common.resource.dto.MenuDto;
import org.doif.projectv.common.resource.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long>, MenuQueryRepository {

}
