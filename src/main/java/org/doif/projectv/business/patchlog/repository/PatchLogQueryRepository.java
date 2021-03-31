package org.doif.projectv.business.patchlog.repository;

import org.doif.projectv.business.patchlog.dto.PatchLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatchLogQueryRepository {

    Page<PatchLogDto.Result> searchByCondition(Long clientId, PatchLogDto.Search search, Pageable pageable);
}
