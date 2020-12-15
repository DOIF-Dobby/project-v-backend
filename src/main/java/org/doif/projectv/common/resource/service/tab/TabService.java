package org.doif.projectv.common.resource.service.tab;

import org.doif.projectv.common.resource.dto.TabDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface TabService {
    List<TabDto.Result> selectByPage(TabDto.Search search);

    CommonResponse insert(TabDto.Insert dto);

    CommonResponse update(Long id, TabDto.Update dto);

    CommonResponse delete(Long id);
}
