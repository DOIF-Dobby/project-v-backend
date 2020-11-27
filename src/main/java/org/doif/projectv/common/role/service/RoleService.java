package org.doif.projectv.common.role.service;

import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.role.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto.Result> select();

    CommonResponse insert(RoleDto.Insert dto);

    CommonResponse update(Long id, RoleDto.Update dto);

    CommonResponse delete(Long id);
}
