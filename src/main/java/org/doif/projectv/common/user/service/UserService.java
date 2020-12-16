package org.doif.projectv.common.user.service;

import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserDto.Result> selectByCondition(UserDto.Search search, Pageable pageable);

    CommonResponse insert(UserDto.Insert dto);

    CommonResponse update(String id, UserDto.Update dto);

    CommonResponse delete(String id);
}
