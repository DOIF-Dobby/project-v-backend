package org.doif.projectv.business.task.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.task.dto.TaskDto;
import org.doif.projectv.business.task.service.TaskService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<TaskDto.Response> searchByCondition(@RequestBody TaskDto.Search search, Pageable pageable) {
        Page<TaskDto.Result> result = taskService.searchByCondition(search, pageable);
        TaskDto.Response response = new TaskDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody TaskDto.Insert dto) {
        CommonResponse response = taskService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody TaskDto.Update dto) {
        CommonResponse response = taskService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = taskService.delete(id);
        return ResponseEntity.ok(response);
    }
}
