package org.doif.projectv.business.issue.repository;

import org.doif.projectv.business.issue.dto.IssueDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssueQueryRepository {

    Page<IssueDto.Result> searchByCondition(IssueDto.Search searchDto, Pageable pageable);
}
