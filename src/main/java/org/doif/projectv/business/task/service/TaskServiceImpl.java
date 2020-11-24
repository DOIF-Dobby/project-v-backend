package org.doif.projectv.business.task.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.issue.repository.VersionIssueRepository;
import org.doif.projectv.business.task.dto.TaskDto;
import org.doif.projectv.business.task.entity.Task;
import org.doif.projectv.business.task.repository.TaskRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final VersionIssueRepository moduleIssueRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<TaskDto.Result> searchByCondition(TaskDto.Search search, Pageable pageable) {
        return taskRepository.searchByCondition(search, pageable);
    }

    @Override
    public CommonResponse insert(TaskDto.Insert dto) {
        Optional<VersionIssue> optional = moduleIssueRepository.findById(dto.getVersionIssueId());
        VersionIssue versionIssue = optional.orElseThrow(() -> new IllegalArgumentException("Version-Issue를 찾을 수 없음"));
        Task task = new Task(versionIssue, dto.getStartDate(), dto.getEndDate(), dto.getContents(), dto.getType(), dto.getManDay(), dto.getWorker(), dto.getRemark());
        taskRepository.save(task);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, TaskDto.Update dto) {
        Optional<Task> optional = taskRepository.findById(id);
        Task task = optional.orElseThrow(() -> new IllegalArgumentException("Task를 찾을 수 없음"));
        task.changeTask(dto.getStartDate(), dto.getEndDate(), dto.getContents(), dto.getType(), dto.getManDay(), dto.getWorker(), dto.getRemark());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Task> optional = taskRepository.findById(id);
        Task task = optional.orElseThrow(() -> new IllegalArgumentException("Task를 찾을 수 없음"));
        taskRepository.delete(task);

        return ResponseUtil.ok();
    }
}
