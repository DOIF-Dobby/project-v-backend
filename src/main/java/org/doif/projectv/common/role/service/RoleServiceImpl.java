package org.doif.projectv.common.role.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.role.dto.RoleDto;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.repository.RoleRepository;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public List<RoleDto.Result> select() {
        return roleRepository.findAll()
                .stream()
                .map(role -> new RoleDto.Result(role.getId(), role.getName(), role.getDescription(), role.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponse insert(RoleDto.Insert dto) {
        Role role = new Role(dto.getName(), dto.getDescription(), dto.getStatus());
        roleRepository.save(role);
        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, RoleDto.Update dto) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        Role role = optionalRole.orElseThrow(() -> new IllegalArgumentException("Role를 찾을 수 없음"));
        role.changeRole(dto.getName(), dto.getDescription(), dto.getStatus());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        Role role = optionalRole.orElseThrow(() -> new IllegalArgumentException("Role를 찾을 수 없음"));
        roleRepository.delete(role);

        return ResponseUtil.ok();
    }
}
