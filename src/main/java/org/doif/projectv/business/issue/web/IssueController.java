package org.doif.projectv.business.issue.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.issue.dto.IssueDto;
import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.issue.service.IssueService;
import org.doif.projectv.business.issue.service.VersionIssueService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;
    private final VersionIssueService versionIssueService;

    @GetMapping
    public ResponseEntity<IssueDto.Response> searchByProjectId(IssueDto.Search search, Pageable pageable) {
        Page<IssueDto.Result> content = issueService.searchByCondition(search, pageable);
        IssueDto.Response response = new IssueDto.Response(content);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody IssueDto.Insert dto) {
        CommonResponse response = issueService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody IssueDto.Update dto) {
        CommonResponse response = issueService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = issueService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/version-issues")
    public ResponseEntity<VersionIssueDto.Response> searchByIssueId(@PathVariable Long id) {
        List<VersionIssueDto.Result> content = versionIssueService.searchByIssueId(id);
        VersionIssueDto.Response response = new VersionIssueDto.Response(content);
        return ResponseEntity.ok(response);
    }
}
