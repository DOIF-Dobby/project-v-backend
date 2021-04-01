package org.doif.projectv.business.issue.service;

import org.doif.projectv.business.issue.dto.IssueDto;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IssueService {
    Page<IssueDto.Result> searchByCondition(IssueDto.Search search, Pageable pageable);

    CommonResponse insert(IssueDto.Insert dto);

    CommonResponse update(Long id, IssueDto.Update dto);

    CommonResponse delete(Long id);

    Page<IssueDto.Result> searchIssuesNotMappingVersion(Long versionId, Pageable pageable);
}
