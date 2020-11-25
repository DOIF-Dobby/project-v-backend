package org.doif.projectv.business.issue.service;

import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VersionIssueService {
    List<VersionIssueDto.Result> searchByIssueId(Long issueId);

    List<VersionIssueDto.Result> searchByVersionId(Long versionId);

    CommonResponse insert(VersionIssueDto.Insert dto);

    CommonResponse update(Long id, VersionIssueDto.Update dto);

    CommonResponse delete(Long id);

    Page<VersionIssueDto.ResultOverview> searchOverview(VersionIssueDto.Search search, Pageable pageable);

}
