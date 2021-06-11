package org.doif.projectv.common.user.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.user.dto.UserDto;
import org.doif.projectv.common.user.dto.UserRoleDto;
import org.doif.projectv.common.user.service.UserRoleService;
import org.doif.projectv.common.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRoleService userRoleService;

    @GetMapping
    public ResponseEntity<UserDto.Response> searchByProjectId(UserDto.Search search, Pageable pageable) {
        Page<UserDto.Result> content = userService.selectByCondition(search, pageable);
        UserDto.Response response = new UserDto.Response(content);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody @Valid UserDto.Insert dto) {
        CommonResponse response = userService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable String id, @RequestBody @Valid UserDto.Update dto) {
        CommonResponse response = userService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable String id) {
        CommonResponse response = userService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/user-roles")
    public ResponseEntity<UserRoleDto.Response> selectRole(@PathVariable String id) {
        List<UserRoleDto.ResultRole> result = userRoleService.selectRole(id);
        UserRoleDto.Response response = new UserRoleDto.Response(result);
        return ResponseEntity.ok(response);
    }

}
