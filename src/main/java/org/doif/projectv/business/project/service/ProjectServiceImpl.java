package org.doif.projectv.business.project.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.project.dto.ProjectDto;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.business.project.repository.ProjectRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ProjectDto.Result> select() {
        List<Project> result = projectRepository.findAll();
        return result.stream()
                .map(project -> {
                    ProjectDto.Result projectResult = new ProjectDto.Result();
                    projectResult.setProjectId(project.getId());
                    projectResult.setProjectName(project.getProjectName());
                    projectResult.setDescription(project.getDescription());
                    projectResult.setCreatedBy(project.getCreatedBy());
                    projectResult.setCreatedDate(project.getCreatedDate());
                    projectResult.setLastModifiedBy(project.getLastModifiedBy());
                    projectResult.setLastModifiedDate(project.getLastModifiedDate());
                    return projectResult;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponse insert(ProjectDto.Insert dto) {
        Project project = new Project(dto.getProjectName(), dto.getDescription());
        projectRepository.save(project);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, ProjectDto.Update dto) {
        Optional<Project> optional = projectRepository.findById(id);
        Project project = optional.orElseThrow(() -> new IllegalArgumentException("데이터가 존재하지 않음"));
        project.changeProject(dto.getProjectName(), dto.getDescription());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Project> optional = projectRepository.findById(id);
        Project project = optional.orElseThrow(() -> new IllegalArgumentException("데이터가 존재하지 않음"));
        projectRepository.delete(project);

        return ResponseUtil.ok();
    }
}
