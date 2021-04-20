package org.doif.projectv.common.resource.repository;

import org.doif.projectv.common.resource.dto.AuthCheckDto;
import org.doif.projectv.common.resource.entity.Menu;
import org.doif.projectv.common.resource.entity.MenuCategory;

import java.util.List;

public interface ResourceQueryRepository {

    List<AuthCheckDto.ResourceAuthorityCheck> searchAuthorityResource(String userId);

    List<AuthCheckDto.ResourcePageCheck> searchPageResource(String userId);

    List<MenuCategory> findAllMenuCategoryByValidResource(String userId);

    List<Menu> findAllMenuByValidResource(String userId);
}
