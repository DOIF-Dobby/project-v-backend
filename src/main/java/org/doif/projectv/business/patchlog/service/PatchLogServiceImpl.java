package org.doif.projectv.business.patchlog.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.client.entity.Client;
import org.doif.projectv.business.client.repository.ClientRepository;
import org.doif.projectv.business.patchlog.dto.PatchLogDto;
import org.doif.projectv.business.patchlog.entity.PatchLog;
import org.doif.projectv.business.patchlog.repository.PatchLogRepository;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.business.version.repository.VersionRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatchLogServiceImpl implements PatchLogService {

    private final PatchLogRepository patchLogRepository;
    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<PatchLogDto.Result> searchByCondition(Long clientId, PatchLogDto.Search search, Pageable pageable) {
        return patchLogRepository.searchByCondition(clientId, search, pageable);
    }

    @Override
    public CommonResponse insert(PatchLogDto.Insert dto) {
        Optional<Client> optionalClient = clientRepository.findById(dto.getClientId());
        Client client = optionalClient.orElseThrow(() -> new IllegalArgumentException("고객을 찾을 수 없음"));

        PatchLog patchLog = new PatchLog(client, dto.getTarget(), dto.getStatus(), dto.getPatchScheduleDate(), dto.getWorker(), dto.getRemark());
        patchLogRepository.save(patchLog);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, PatchLogDto.Update dto) {
        Optional<PatchLog> optionalPatchLog = patchLogRepository.findById(id);
        PatchLog patchLog = optionalPatchLog.orElseThrow(() -> new IllegalArgumentException("패치로그를 찾을 수 없음"));

        patchLog.changePatchLog(dto.getTarget(), dto.getStatus(), dto.getPatchScheduleDate(), dto.getPatchDate(), dto.getWorker(), dto.getRemark());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<PatchLog> optionalPatchLog = patchLogRepository.findById(id);
        PatchLog patchLog = optionalPatchLog.orElseThrow(() -> new IllegalArgumentException("패치로그를 찾을 수 없음"));

        patchLogRepository.delete(patchLog);

        return ResponseUtil.ok();
    }
}
