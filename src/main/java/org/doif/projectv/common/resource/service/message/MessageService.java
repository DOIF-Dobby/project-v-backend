package org.doif.projectv.common.resource.service.message;

import org.doif.projectv.common.resource.dto.MessageDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface MessageService {
    List<MessageDto.Result> select();

    CommonResponse insert(MessageDto.Insert dto);

    CommonResponse update(Long id, MessageDto.Update dto);

    CommonResponse delete(Long id);
}
