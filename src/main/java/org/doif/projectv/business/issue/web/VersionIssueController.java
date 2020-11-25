package org.doif.projectv.business.issue.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.issue.service.VersionIssueService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/version-issue")
@RequiredArgsConstructor
public class VersionIssueController {

    private final VersionIssueService versionIssueService;

    @GetMapping("/issue/{issueId}")
    public ResponseEntity<VersionIssueDto.Response> searchByIssueId(@PathVariable Long issueId) {
        List<VersionIssueDto.Result> content = versionIssueService.searchByIssueId(issueId);
        VersionIssueDto.Response response = new VersionIssueDto.Response(content);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/version/{versionId}")
    public ResponseEntity<VersionIssueDto.Response> searchByVersionId(@PathVariable Long versionId) {
        List<VersionIssueDto.Result> content = versionIssueService.searchByVersionId(versionId);
        VersionIssueDto.Response response = new VersionIssueDto.Response(content);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody VersionIssueDto.Insert dto) {
        CommonResponse response = versionIssueService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody VersionIssueDto.Update dto) {
        CommonResponse response = versionIssueService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = versionIssueService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/overview")
    public ResponseEntity<VersionIssueDto.ResponseOverview> searchOverview(@RequestBody VersionIssueDto.Search search, Pageable pageable) {
        Page<VersionIssueDto.ResultOverview> pageInfo = versionIssueService.searchOverview(search, pageable);
        VersionIssueDto.ResponseOverview response = new VersionIssueDto.ResponseOverview(pageInfo);
        return ResponseEntity.ok(response);
    }

}
