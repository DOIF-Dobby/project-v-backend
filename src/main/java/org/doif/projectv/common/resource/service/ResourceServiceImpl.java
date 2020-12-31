package org.doif.projectv.common.resource.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.AuthCheckDto;
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
    public List<AuthCheckDto.ResourceAuthorityCheck> searchAuthorityResource(String userId) {
        return resourceRepository.searchAuthorityResource(userId);
    }

    @Cacheable(value = "pageResourceCache", key = "#userId")
    @Override
    public List<AuthCheckDto.ResourcePageCheck> searchPageResource(String userId) {
        return resourceRepository.searchPageResource(userId);
    }
}
