package org.doif.projectv.common.role.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.role.dto.RoleResourceDto.*;
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
    public ResponseEntity<Response<ResultPage>> selectPage(@RequestBody SearchPage search) {
        List<ResultPage> result = roleResourceService.selectPage(search);
        Response<ResultPage> response = new Response<>(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/button")
    public ResponseEntity<Response<ResultButton>> selectButton(@RequestBody Search search) {
        List<ResultButton> result = roleResourceService.selectButton(search);
        Response<ResultButton> response = new Response<>(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tab")
    public ResponseEntity<Response<ResultTab>> selectTab(@RequestBody Search search) {
        List<ResultTab> result = roleResourceService.selectTab(search);
        Response<ResultTab> response = new Response<>(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> allocate(@RequestBody Allocate dto) {
        CommonResponse response = roleResourceService.allocate(dto);
        return ResponseEntity.ok(response);
    }

}
