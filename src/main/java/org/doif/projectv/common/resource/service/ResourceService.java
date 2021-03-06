package org.doif.projectv.common.resource.service;

import org.doif.projectv.common.resource.dto.AuthCheckDto;
import org.doif.projectv.common.resource.dto.MenuDto;
import org.doif.projectv.common.resource.dto.PageDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface ResourceService {

    List<AuthCheckDto.ResourceAuthorityCheck> searchAuthorityResource(String userId);

    List<AuthCheckDto.ResourcePageCheck> searchPageResource(String userId);

    PageDto.Child searchPageChildResource(String url, String menuPath);

    List<MenuDto.Result> selectSideMenu(String userId);

    CommonResponse refreshCacheAll();
}
