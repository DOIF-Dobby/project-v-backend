package org.doif.projectv.business.module.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.module.dto.ModuleDto;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.module.repository.ModuleRepository;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.business.project.repository.ProjectRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ModuleDto.Result> searchByProjectId(Long projectId) {
        return moduleRepository.searchByProjectId(projectId);
    }

    @Override
    public CommonResponse insert(ModuleDto.Insert dto) {
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectId());
        Project project = optionalProject.orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없음"));
        Module module = new Module(dto.getModuleName(), project, dto.getDescription());
        moduleRepository.save(module);
        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, ModuleDto.Update dto) {
        Optional<Module> optionalModule = moduleRepository.findById(id);
        Module module = optionalModule.orElseThrow(() -> new IllegalArgumentException("모듈을 찾을 수 없음"));
        module.changeModule(dto.getModuleName(), dto.getDescription());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Module> optionalModule = moduleRepository.findById(id);
        Module module = optionalModule.orElseThrow(() -> new IllegalArgumentException("모듈을 찾을 수 없음"));
        moduleRepository.delete(module);
        return ResponseUtil.ok();
    }
}
