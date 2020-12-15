package org.doif.projectv.common.resource.service.label;

import org.doif.projectv.common.resource.dto.LabelDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface LabelService {
    List<LabelDto.Result> selectByPage(LabelDto.Search search);

    CommonResponse insert(LabelDto.Insert dto);

    CommonResponse update(Long id, LabelDto.Update dto);

    CommonResponse delete(Long id);
}
