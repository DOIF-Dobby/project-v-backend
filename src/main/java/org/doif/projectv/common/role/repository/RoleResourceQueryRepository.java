package org.doif.projectv.common.role.repository;

import org.doif.projectv.common.role.dto.RoleResourceDto;

import java.util.List;

public interface RoleResourceQueryRepository {

    List<RoleResourceDto.ResultPage> selectPage(RoleResourceDto.SearchPage search);

    List<RoleResourceDto.ResultButton> selectButton(RoleResourceDto.Search search);

    List<RoleResourceDto.ResultTab> selectTab(RoleResourceDto.Search search);

}
