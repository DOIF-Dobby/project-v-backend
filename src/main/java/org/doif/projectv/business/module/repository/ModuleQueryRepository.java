package org.doif.projectv.business.module.repository;

import org.doif.projectv.business.module.dto.ModuleDto;

import java.util.List;

public interface ModuleQueryRepository {
    List<ModuleDto.Result> searchByProjectId(Long projectId);
}
