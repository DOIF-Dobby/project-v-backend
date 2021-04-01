package org.doif.projectv.business.version.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.issue.service.VersionIssueService;
import org.doif.projectv.business.version.dto.VersionDto;
import org.doif.projectv.business.version.service.VersionService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/versions")
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;
    private final VersionIssueService versionIssueService;

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

    @PostMapping("/release")
    public ResponseEntity<CommonResponse> release(@RequestBody VersionDto.Release dto) {
        CommonResponse response = versionService.release(dto.getVersionId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/version-issues")
    public ResponseEntity<VersionIssueDto.Response> searchByVersionId(@PathVariable Long id) {
        List<VersionIssueDto.Result> content = versionIssueService.searchByVersionId(id);
        VersionIssueDto.Response response = new VersionIssueDto.Response(content);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/not-mapping-issue")
    public ResponseEntity<VersionDto.Response> searchVersionsNotMappingIssue(@RequestParam("issueId") Long issueId, Pageable pageable) {
        Page<VersionDto.Result> content = versionService.searchVersionsNotMappingIssue(issueId, pageable);
        VersionDto.Response response = new VersionDto.Response(content);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/not-mapping-patch-log")
    public ResponseEntity<VersionDto.Response> searchVersionsNotMappingPatchLog(@RequestParam("patchLogId") Long patchLogId, Pageable pageable) {
        Page<VersionDto.Result> content = versionService.searchVersionsNotMappingPatchLog(patchLogId, pageable);
        VersionDto.Response response = new VersionDto.Response(content);
        return ResponseEntity.ok(response);
    }
}
