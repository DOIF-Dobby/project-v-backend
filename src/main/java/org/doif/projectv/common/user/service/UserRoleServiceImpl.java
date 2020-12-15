package org.doif.projectv.common.user.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.repository.RoleRepository;
import org.doif.projectv.common.user.dto.UserRoleDto;
import org.doif.projectv.common.user.entity.User;
import org.doif.projectv.common.user.entity.UserRole;
import org.doif.projectv.common.user.repository.UserRepository;
import org.doif.projectv.common.user.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<UserRoleDto.ResultRole> selectRole(UserRoleDto.Search search) {
        return userRoleRepository.selectRole(search);
    }

    @Transactional
    @Override
    public CommonResponse allocate(UserRoleDto.Allocate dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없음"));

        userRoleRepository.deleteByUserId(user.getId());

        dto.getRoleIds()
                .forEach(roleId -> {
                    Optional<Role> optionalRole = roleRepository.findById(roleId);
                    Role role = optionalRole.orElseThrow(() -> new IllegalArgumentException("Role을 찾을 수 없음"));
                    UserRole userRole = new UserRole(user, role);
                    userRoleRepository.save(userRole);
                });


        return ResponseUtil.ok();
    }
}
