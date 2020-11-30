package org.doif.projectv.common.resource.service.button;

import org.doif.projectv.common.resource.dto.ButtonDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface ButtonService {
    List<ButtonDto.Result> selectByPage(Long pageId);

    CommonResponse insert(ButtonDto.Insert dto);

    CommonResponse update(Long id, ButtonDto.Update dto);

    CommonResponse delete(Long id);
}
