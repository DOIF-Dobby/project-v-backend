package org.doif.projectv.business.module.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.module.dto.ModuleDto;
import org.doif.projectv.business.module.service.ModuleService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/module")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping
    public ResponseEntity<ModuleDto.Response> searchByProjectId(@RequestBody ModuleDto.Search search) {
        List<ModuleDto.Result> content = moduleService.searchByProjectId(search.getProjectId());
        ModuleDto.Response response = new ModuleDto.Response(content);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody ModuleDto.Insert dto) {
        CommonResponse response = moduleService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody ModuleDto.Update dto) {
        CommonResponse response = moduleService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = moduleService.delete(id);
        return ResponseEntity.ok(response);
    }
}
