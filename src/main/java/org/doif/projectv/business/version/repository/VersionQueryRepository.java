package org.doif.projectv.business.version.repository;

import org.doif.projectv.business.version.dto.VersionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VersionQueryRepository {
    Page<VersionDto.Result> searchByCondition(VersionDto.Search search, Pageable pageable);
}
