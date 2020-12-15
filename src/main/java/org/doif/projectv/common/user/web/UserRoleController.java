package org.doif.projectv.common.user.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.user.dto.UserRoleDto;
import org.doif.projectv.common.user.service.UserRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-role")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @GetMapping
    public ResponseEntity<UserRoleDto.Response> selectRole(@RequestBody UserRoleDto.Search search) {
        List<UserRoleDto.ResultRole> result = userRoleService.selectRole(search);
        UserRoleDto.Response response = new UserRoleDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> allocate(@RequestBody UserRoleDto.Allocate dto) {
        CommonResponse response = userRoleService.allocate(dto);
        return ResponseEntity.ok(response);
    }
}
