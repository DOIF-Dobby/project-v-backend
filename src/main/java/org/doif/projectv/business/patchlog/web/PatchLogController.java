package org.doif.projectv.business.patchlog.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.patchlog.dto.PatchLogDto;
import org.doif.projectv.business.patchlog.dto.PatchLogVersionDto;
import org.doif.projectv.business.patchlog.service.PatchLogService;
import org.doif.projectv.business.patchlog.service.PatchLogVersionService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patch-logs")
@RequiredArgsConstructor
public class PatchLogController {

    private final PatchLogService patchLogService;
    private final PatchLogVersionService patchLogVersionService;

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

    @GetMapping("/{id}/patch-log-versions")
    public ResponseEntity<PatchLogVersionDto.Response> searchPatchLogVersionsByPatchLogId(@PathVariable Long id) {
        List<PatchLogVersionDto.Result> results = patchLogVersionService.searchPatchLogVersionsByPatchLogId(id);
        PatchLogVersionDto.Response response = new PatchLogVersionDto.Response(results);
        return ResponseEntity.ok(response);
    }
}
