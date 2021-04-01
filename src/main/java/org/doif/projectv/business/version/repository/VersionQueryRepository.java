package org.doif.projectv.business.version.repository;

import org.doif.projectv.business.version.dto.VersionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VersionQueryRepository {
    Page<VersionDto.Result> searchByCondition(Long id, VersionDto.Search search, Pageable pageable);

    Page<VersionDto.Result> searchVersionsNotMappingIssue(Long issueId, Pageable pageable);

    Page<VersionDto.Result> searchVersionsNotMappingPatchLog(Long patchLogId, Pageable pageable);
}
