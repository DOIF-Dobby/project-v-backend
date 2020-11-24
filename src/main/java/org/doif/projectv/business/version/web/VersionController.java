package org.doif.projectv.business.version.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.version.dto.VersionDto;
import org.doif.projectv.business.version.service.VersionService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/version")
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @GetMapping
    public ResponseEntity<VersionDto.Response> searchByCondition(@RequestBody VersionDto.Search search, Pageable pageable) {
        Page<VersionDto.Result> result = versionService.searchByCondition(search, pageable);
        VersionDto.Response response = new VersionDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody VersionDto.Insert dto) {
        CommonResponse response = versionService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody VersionDto.Update dto) {
        CommonResponse response = versionService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = versionService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/release/{id}")
    public ResponseEntity<CommonResponse> tag(@PathVariable Long id) {
        CommonResponse response = versionService.release(id);
        return ResponseEntity.ok(response);
    }
}
