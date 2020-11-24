package org.doif.projectv.common.resource.repository;

import org.doif.projectv.common.resource.dto.AuthorityResourceDto;

import java.util.List;

public interface ResourceQueryRepository {

    List<AuthorityResourceDto> searchAuthorityResource(String userId);
}
