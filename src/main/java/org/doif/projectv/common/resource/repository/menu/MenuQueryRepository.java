package org.doif.projectv.common.resource.repository.menu;

import org.doif.projectv.common.resource.dto.MenuDto;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuQueryRepository {

    List<MenuDto.Result> findAllRecursiveCategoryAndMenu();
}
