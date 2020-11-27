package org.doif.projectv.common.resource.service;

import org.doif.projectv.common.resource.dto.ResourceAuthorityDto;

import java.util.List;

public interface ResourceService {

    List<ResourceAuthorityDto.Result> searchAuthorityResource(String userId);
}
