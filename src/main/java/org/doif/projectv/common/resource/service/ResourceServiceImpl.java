package org.doif.projectv.common.resource.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.ResourceAuthorityDto;
import org.doif.projectv.common.resource.repository.ResourceRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    @Cacheable(value = "authorityResourceCache", key = "#userId")
    @Override
    public List<ResourceAuthorityDto.Result> searchAuthorityResource(String userId) {
        return resourceRepository.searchAuthorityResource(userId);
    }
}
