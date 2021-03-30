package org.doif.projectv.business.module.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.module.dto.ModuleDto;
import org.doif.projectv.business.module.service.ModuleService;
import org.doif.projectv.business.version.dto.VersionDto;
import org.doif.projectv.business.version.service.VersionService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;
    private final VersionService versionService;

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

    @GetMapping("/{id}/versions")
    public ResponseEntity<VersionDto.Response> searchByCondition(@PathVariable Long id, VersionDto.Search search, Pageable pageable) {
        Page<VersionDto.Result> result = versionService.searchByCondition(id, search, pageable);
        VersionDto.Response response = new VersionDto.Response(result);
        return ResponseEntity.ok(response);
    }
}
