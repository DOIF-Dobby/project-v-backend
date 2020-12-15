package org.doif.projectv.common.user.service;

import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.user.dto.UserRoleDto;

import java.util.List;

public interface UserRoleService {
    List<UserRoleDto.ResultRole> selectRole(UserRoleDto.Search search);

    CommonResponse allocate(UserRoleDto.Allocate dto);
}
