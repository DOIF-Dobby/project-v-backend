package org.doif.projectv.common.resource.repository;

import org.doif.projectv.common.resource.dto.ResourceAuthorityDto;

import java.util.List;

public interface ResourceQueryRepository {

    List<ResourceAuthorityDto.Result> searchAuthorityResource(String userId);
}
