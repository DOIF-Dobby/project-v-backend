package org.doif.projectv.common.resource.service.menu;

import org.doif.projectv.common.resource.dto.MenuDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface MenuService {
    List<MenuDto.Result> select();

    CommonResponse insert(MenuDto.Insert dto);

    CommonResponse update(Long id, MenuDto.Update dto);

    CommonResponse delete(Long id);
}
