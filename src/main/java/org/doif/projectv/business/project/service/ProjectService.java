package org.doif.projectv.business.project.service;

import org.doif.projectv.business.project.dto.ProjectDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectDto.Result> select();

    CommonResponse insert(ProjectDto.Insert dto);

    CommonResponse update(Long id, ProjectDto.Update dto);

    CommonResponse delete(Long id);

}
