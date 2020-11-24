package org.doif.projectv.business.task.repository;

import org.doif.projectv.business.task.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskQueryRepository {

    List<TaskDto.Result> searchByModuleIssueId(Long moduleIssueId);

    Page<TaskDto.Result> searchByCondition(TaskDto.Search search, Pageable pageable);

}
