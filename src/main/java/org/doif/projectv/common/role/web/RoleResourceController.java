package org.doif.projectv.common.role.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.role.dto.RoleResourceDto;
import org.doif.projectv.common.role.service.RoleResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-resource")
@RequiredArgsConstructor
public class RoleResourceController {

    private final RoleResourceService roleResourceService;

    @GetMapping("/page")
    public ResponseEntity<RoleResourceDto.Response<RoleResourceDto.ResultPage>> selectPage() {
        List<RoleResourceDto.ResultPage> result = roleResourceService.selectPage();
        RoleResourceDto.Response<RoleResourceDto.ResultPage> response = new RoleResourceDto.Response<>(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/button/{pageId}")
    public ResponseEntity<RoleResourceDto.Response<RoleResourceDto.ResultButton>> selectButtonByPageId(@PathVariable Long pageId) {
        List<RoleResourceDto.ResultButton> result = roleResourceService.selectButtonByPageId(pageId);
        RoleResourceDto.Response<RoleResourceDto.ResultButton> response = new RoleResourceDto.Response<>(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tab/{pageId}")
    public ResponseEntity<RoleResourceDto.Response<RoleResourceDto.ResultTab>> selectTabByPageId(@PathVariable Long pageId) {
        List<RoleResourceDto.ResultTab> result = roleResourceService.selectTabByPageId(pageId);
        RoleResourceDto.Response<RoleResourceDto.ResultTab> response = new RoleResourceDto.Response<>(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> allocate(@RequestBody RoleResourceDto.Allocate dto) {
        CommonResponse response = roleResourceService.allocate(dto);
        return ResponseEntity.ok(response);
    }

}
