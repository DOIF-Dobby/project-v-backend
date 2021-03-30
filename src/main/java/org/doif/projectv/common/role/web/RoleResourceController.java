package org.doif.projectv.common.role.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.role.dto.RoleResourceDto.*;
import org.doif.projectv.common.role.service.RoleResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-resources")
@RequiredArgsConstructor
public class RoleResourceController {

    private final RoleResourceService roleResourceService;

    @GetMapping("/pages")
    public ResponseEntity<Response<ResultPage>> selectPage(SearchPage search) {
        List<ResultPage> result = roleResourceService.selectPage(search);
        Response<ResultPage> response = new Response<>(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buttons")
    public ResponseEntity<Response<ResultButton>> selectButton(Search search) {
        List<ResultButton> result = roleResourceService.selectButton(search);
        Response<ResultButton> response = new Response<>(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tabs")
    public ResponseEntity<Response<ResultTab>> selectTab(Search search) {
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
