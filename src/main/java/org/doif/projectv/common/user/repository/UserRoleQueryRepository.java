package org.doif.projectv.common.user.repository;

import org.doif.projectv.common.user.dto.UserRoleDto;

import java.util.List;

public interface UserRoleQueryRepository {

    List<UserRoleDto.ResultRole> selectRole(UserRoleDto.Search search);
}
