package org.doif.projectv.business.issue.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.issue.dto.VersionIssueOverviewDto;
import org.doif.projectv.business.issue.service.VersionIssueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/module-issue")
@RequiredArgsConstructor
public class VersionIssueController {

    private final VersionIssueService versionIssueService;

    @GetMapping
    public ResponseEntity<VersionIssueDto.Response> searchByIssueId(@RequestBody VersionIssueDto.Search search) {
        List<VersionIssueDto.Result> content = versionIssueService.searchByIssueId(search);
        VersionIssueDto.Response response = new VersionIssueDto.Response(content);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/overview")
    public ResponseEntity<VersionIssueDto.ResponseOverview> searchOverview(@RequestBody VersionIssueDto.Search search, Pageable pageable) {
        Page<VersionIssueOverviewDto> pageInfo = versionIssueService.searchOverview(search, pageable);
        VersionIssueDto.ResponseOverview response = new VersionIssueDto.ResponseOverview(pageInfo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/none-patch")
    public ResponseEntity<VersionIssueDto.ResponseNonePatch> searchNonePatchModuleIssue(@RequestBody VersionIssueDto.Search search, Pageable pageable) {
        Page<VersionIssueDto.Result> content = versionIssueService.searchNonePatchModuleIssue(search, pageable);
        VersionIssueDto.ResponseNonePatch response = new VersionIssueDto.ResponseNonePatch(content);
        return ResponseEntity.ok(response);
    }
}
