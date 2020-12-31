package org.doif.projectv.common.resource.service;

import org.doif.projectv.common.resource.dto.AuthCheckDto;

import java.util.List;

public interface ResourceService {

    List<AuthCheckDto.ResourceAuthorityCheck> searchAuthorityResource(String userId);

    List<AuthCheckDto.ResourcePageCheck> searchPageResource(String userId);
}
