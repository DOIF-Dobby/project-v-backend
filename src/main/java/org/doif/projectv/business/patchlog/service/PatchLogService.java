package org.doif.projectv.business.patchlog.service;

import org.doif.projectv.business.patchlog.dto.PatchLogDto;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatchLogService {
    Page<PatchLogDto.Result> searchByCondition(PatchLogDto.Search search, Pageable pageable);

    CommonResponse insert(PatchLogDto.Insert dto);

    CommonResponse update(Long id, PatchLogDto.Update dto);

    CommonResponse delete(Long id);
}
