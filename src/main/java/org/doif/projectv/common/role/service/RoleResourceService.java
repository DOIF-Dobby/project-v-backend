package org.doif.projectv.common.role.service;

import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.role.dto.RoleResourceDto;

import java.util.List;

public interface RoleResourceService {
    List<RoleResourceDto.ResultPage> selectPage(RoleResourceDto.SearchPage search);

    List<RoleResourceDto.ResultMenu> selectMenu(RoleResourceDto.SearchMenu search);

    CommonResponse allocate(RoleResourceDto.Allocate dto);

    CommonResponse allocateMenu(RoleResourceDto.AllocateMenu dto);
}
