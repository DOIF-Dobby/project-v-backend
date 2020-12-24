package org.doif.projectv.business.vcs.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.module.repository.ModuleRepository;
import org.doif.projectv.business.vcs.dto.VcsDto;
import org.doif.projectv.business.vcs.operator.VcsOperator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VcsServiceImpl implements VcsService {

    private final Map<String, VcsOperator> vcsOperatorMap;
    private final ModuleRepository moduleRepository;

    @Override
    public List<VcsDto.Log> searchLogByCondition(VcsDto.SearchLog search) {
        Optional<Module> optionalModule = moduleRepository.findById(search.getModuleId());
        Module module = optionalModule.orElseThrow(() -> new IllegalArgumentException("모듈을 찾을 수 없음"));

        VcsOperator vcsOperator = vcsOperatorMap.get(module.getVcsType().getOperatorBeanName());
        return vcsOperator.getLogs(module.getVcsRepository(), search.getStartDate(), search.getEndDate());
    }
}
