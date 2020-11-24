package org.doif.projectv.business.module.service;

import org.doif.projectv.business.module.dto.ModuleDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface ModuleService {

    List<ModuleDto.Result> searchByProjectId(Long projectId);

    CommonResponse insert(ModuleDto.Insert dto);

    CommonResponse update(Long id, ModuleDto.Update dto);

    CommonResponse delete(Long id);
}
