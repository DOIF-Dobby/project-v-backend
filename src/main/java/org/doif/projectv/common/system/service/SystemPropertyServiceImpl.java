package org.doif.projectv.common.system.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.system.dto.SystemPropertyDto;
import org.doif.projectv.common.system.entity.SystemProperty;
import org.doif.projectv.common.system.repository.SystemPropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SystemPropertyServiceImpl implements SystemPropertyService {

    private final SystemPropertyRepository systemPropertyRepository;

    @Override
    public List<SystemPropertyDto.Result> searchByCondition(SystemPropertyDto.Search search) {
        return systemPropertyRepository.searchByCondition(search)
                .stream()
                .map(systemProperty -> new SystemPropertyDto.Result(
                        systemProperty.getId(),
                        systemProperty.getPropertyGroup(),
                        systemProperty.getProperty(),
                        systemProperty.getValue(),
                        systemProperty.getDescription(),
                        systemProperty.getUpdatable()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponse insert(SystemPropertyDto.Insert dto) {
        SystemProperty systemProperty = new SystemProperty(dto.getPropertyGroup(), dto.getProperty(), dto.getValue(), dto.getDescription(), dto.isUpdatable());
        systemPropertyRepository.save(systemProperty);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, SystemPropertyDto.Update dto) {
        Optional<SystemProperty> optionalSystemProperty = systemPropertyRepository.findById(id);
        SystemProperty systemProperty = optionalSystemProperty.orElseThrow(() -> new IllegalArgumentException("SystemProperty를 찾을 수 없음"));

        if(!systemProperty.getUpdatable()){
            throw new IllegalArgumentException("수정이 불가능한 SystemProperty");
        }

        systemProperty.changeSystemProperty(dto.getValue(), dto.getDescription(), dto.isUpdatable());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<SystemProperty> optionalSystemProperty = systemPropertyRepository.findById(id);
        SystemProperty systemProperty = optionalSystemProperty.orElseThrow(() -> new IllegalArgumentException("SystemProperty를 찾을 수 없음"));
        systemPropertyRepository.delete(systemProperty);

        return ResponseUtil.ok();
    }
}
