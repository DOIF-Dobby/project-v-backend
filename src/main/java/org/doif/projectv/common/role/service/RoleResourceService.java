package org.doif.projectv.common.role.service;

import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.role.dto.RoleResourceDto;

import java.util.List;

public interface RoleResourceService {
    List<RoleResourceDto.ResultPage> selectPage();

    List<RoleResourceDto.ResultButton> selectButtonByPageId(Long pageId);

    List<RoleResourceDto.ResultTab> selectTabByPageId(Long pageId);

    CommonResponse allocate(RoleResourceDto.Allocate dto);
}
