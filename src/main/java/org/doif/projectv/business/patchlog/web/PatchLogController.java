package org.doif.projectv.business.patchlog.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.patchlog.dto.PatchLogDto;
import org.doif.projectv.business.patchlog.service.PatchLogService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patch-log")
@RequiredArgsConstructor
public class PatchLogController {

    private final PatchLogService patchLogService;

    @GetMapping
    public ResponseEntity<PatchLogDto.Response> searchByCondition(@RequestBody PatchLogDto.Search search, Pageable pageable) {
        Page<PatchLogDto.Result> result = patchLogService.searchByCondition(search, pageable);
        PatchLogDto.Response response = new PatchLogDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody PatchLogDto.Insert dto) {
        CommonResponse response = patchLogService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody PatchLogDto.Update dto) {
        CommonResponse response = patchLogService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = patchLogService.delete(id);
        return ResponseEntity.ok(response);
    }
}
