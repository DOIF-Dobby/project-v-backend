package org.doif.projectv.business.version.service;

import org.doif.projectv.business.version.dto.VersionDto;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VersionService {

    Page<VersionDto.Result> searchByCondition(Long id, VersionDto.Search search, Pageable pageable);

    CommonResponse insert(VersionDto.Insert dto);

    CommonResponse update(Long id, VersionDto.Update dto);

    CommonResponse delete(Long id);

    CommonResponse release(Long id);
}
