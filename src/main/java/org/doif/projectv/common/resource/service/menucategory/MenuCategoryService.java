package org.doif.projectv.common.resource.service.menucategory;

import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface MenuCategoryService {
    List<MenuCategoryDto.Result> select();

    CommonResponse insert(MenuCategoryDto.Insert dto);

    CommonResponse update(Long id, MenuCategoryDto.Update dto);

    CommonResponse delete(Long id);
}
