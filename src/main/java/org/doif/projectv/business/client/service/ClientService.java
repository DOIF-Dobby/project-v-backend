package org.doif.projectv.business.client.service;

import org.doif.projectv.business.client.dto.ClientDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface ClientService {
    List<ClientDto.Result> select();

    CommonResponse insert(ClientDto.Insert dto);

    CommonResponse update(Long id, ClientDto.Update dto);

    CommonResponse delete(Long id);
}
