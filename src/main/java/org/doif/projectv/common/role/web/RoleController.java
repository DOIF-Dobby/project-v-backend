package org.doif.projectv.common.role.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.role.dto.RoleDto;
import org.doif.projectv.common.role.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<RoleDto.Response> select() {
        List<RoleDto.Result> result = roleService.select();
        RoleDto.Response response = new RoleDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody RoleDto.Insert dto) {
        CommonResponse response = roleService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody RoleDto.Update dto) {
        CommonResponse response = roleService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = roleService.delete(id);
        return ResponseEntity.ok(response);
    }
}
