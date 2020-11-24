package org.doif.projectv.business.task.service;

import org.doif.projectv.business.task.dto.TaskDto;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<TaskDto.Result> searchByCondition(TaskDto.Search search, Pageable pageable);

    CommonResponse insert(TaskDto.Insert dto);

    CommonResponse update(Long id, TaskDto.Update dto);

    CommonResponse delete(Long id);
}
