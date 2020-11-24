package org.doif.projectv.business.issue.service;

import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.issue.dto.VersionIssueOverviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VersionIssueService {
    List<VersionIssueDto.Result> searchByIssueId(VersionIssueDto.Search search);

    Page<VersionIssueOverviewDto> searchOverview(VersionIssueDto.Search search, Pageable pageable);

    Page<VersionIssueDto.Result> searchNonePatchModuleIssue(VersionIssueDto.Search search, Pageable pageable);
}
