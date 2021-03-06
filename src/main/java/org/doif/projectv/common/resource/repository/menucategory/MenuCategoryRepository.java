package org.doif.projectv.common.resource.repository.menucategory;

import org.doif.projectv.common.resource.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

}

