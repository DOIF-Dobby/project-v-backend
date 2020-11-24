package org.doif.projectv.common.resource.service;

import org.doif.projectv.common.resource.dto.AuthorityResourceDto;

import java.util.List;

public interface ResourceService {

    List<AuthorityResourceDto> searchAuthorityResource(String userId);
}
