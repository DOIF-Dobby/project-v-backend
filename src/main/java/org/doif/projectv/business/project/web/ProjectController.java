package org.doif.projectv.business.project.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.module.dto.ModuleDto;
import org.doif.projectv.business.module.service.ModuleService;
import org.doif.projectv.business.project.dto.ProjectDto;
import org.doif.projectv.business.project.service.ProjectService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ModuleService moduleService;

    @GetMapping
    public ResponseEntity<ProjectDto.Response> select() {
        List<ProjectDto.Result> result = projectService.select();
        ProjectDto.Response response = ProjectDto.Response
                .builder()
                .content(result)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody ProjectDto.Insert dto) {
        CommonResponse response = projectService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody ProjectDto.Update dto) {
        CommonResponse response = projectService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = projectService.delete(id);
        return ResponseEntity.ok(response);
    }

    /**
     * project 하위의 모듈들 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}/modules")
    public ResponseEntity<ModuleDto.Response> searchByProjectId(@PathVariable Long id) {
        List<ModuleDto.Result> content = moduleService.searchByProjectId(id);
        ModuleDto.Response response = new ModuleDto.Response(content);
        return ResponseEntity.ok(response);
    }

}
