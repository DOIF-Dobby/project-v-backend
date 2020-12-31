package org.doif.projectv.common.resource.repository;

import org.doif.projectv.common.resource.dto.AuthCheckDto;

import java.util.List;

public interface ResourceQueryRepository {

    List<AuthCheckDto.ResourceAuthorityCheck> searchAuthorityResource(String userId);

    List<AuthCheckDto.ResourcePageCheck> searchPageResource(String userId);
}
