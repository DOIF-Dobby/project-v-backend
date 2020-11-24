package org.doif.projectv.common.user.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.user.constant.LanguageType;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.user.constant.UserStatus;
import org.doif.projectv.common.user.dto.UserDto;
import org.doif.projectv.common.user.entity.User;
import org.doif.projectv.common.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto findById(String id) {

        return null;
    }

    @Override
    public CommonResponse insertUser(UserDto userDto) {
//        userRepository.save(user);
        return ResponseUtil.ok();
    }
}
