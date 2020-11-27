package org.doif.projectv.common.resource.service.page;

import org.doif.projectv.common.resource.dto.PageDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface PageService {
    List<PageDto.Result> select();

    CommonResponse insert(PageDto.Insert dto);

    CommonResponse update(Long id, PageDto.Update dto);

    CommonResponse delete(Long id);
}
