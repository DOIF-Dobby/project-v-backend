package org.doif.projectv.business.client.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.client.dto.ClientDto;
import org.doif.projectv.business.client.entity.Client;
import org.doif.projectv.business.client.repository.ClientRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ClientDto.Result> select() {
        return clientRepository.findAll().stream()
                .map(client -> new ClientDto.Result(
                        client.getId(),
                        client.getName(),
                        client.getDescription(),
                        client.getTel(),
                        client.getBizRegNo(),
                        client.getZipCode(),
                        client.getBasicAddr(),
                        client.getDetailAddr()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponse insert(ClientDto.Insert dto) {
        Client client = new Client(dto.getName(), dto.getDescription(), dto.getTel(), dto.getBizRegNo(), dto.getZipCode(), dto.getBasicAddr(), dto.getDetailAddr());
        clientRepository.save(client);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, ClientDto.Update dto) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        Client client = optionalClient.orElseThrow(() -> new IllegalArgumentException("고객사를 찾을 수 없음"));
        client.changeClient(dto.getName(), dto.getDescription(), dto.getTel(), dto.getBizRegNo(), dto.getZipCode(), dto.getBasicAddr(), dto.getDetailAddr());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        Client client = optionalClient.orElseThrow(() -> new IllegalArgumentException("고객사를 찾을 수 없음"));
        clientRepository.delete(client);

        return ResponseUtil.ok();
    }
}
