package org.doif.projectv.common.system.service;

import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.system.dto.SystemPropertyDto;

import java.util.List;

public interface SystemPropertyService {
    List<SystemPropertyDto.Result> searchByCondition(SystemPropertyDto.Search search);

    CommonResponse insert(SystemPropertyDto.Insert dto);

    CommonResponse update(Long id, SystemPropertyDto.Update dto);

    CommonResponse delete(Long id);
}
