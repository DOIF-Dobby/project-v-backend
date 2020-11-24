package org.doif.projectv.common.user.service;

import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.user.dto.UserDto;

public interface UserService {

    UserDto findById(String id);

    CommonResponse insertUser(UserDto userDto);
}
