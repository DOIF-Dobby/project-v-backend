package org.doif.projectv.business.patchlog.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.patchlog.dto.PatchLogVersionDto;
import org.doif.projectv.business.patchlog.entity.PatchLog;
import org.doif.projectv.business.patchlog.entity.PatchLogVersion;
import org.doif.projectv.business.patchlog.repository.PatchLogRepository;
import org.doif.projectv.business.patchlog.repository.PatchLogVersionRepository;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.business.version.repository.VersionRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatchLogVersionServiceImpl implements PatchLogVersionService {

    private final PatchLogVersionRepository patchLogVersionRepository;
    private final PatchLogRepository patchLogRepository;
    private final VersionRepository versionRepository;

    @Override
    public List<PatchLogVersionDto.Result> searchPatchLogVersionsByPatchLogId(Long patchLogId) {
        return patchLogVersionRepository.searchPatchLogVersionsByPatchLogId(patchLogId);
    }

    @Override
    public CommonResponse insert(PatchLogVersionDto.Insert dto) {
        Optional<PatchLog> optionalPatchLog = patchLogRepository.findById(dto.getPatchLogId());
        PatchLog patchLog = optionalPatchLog.orElseThrow(() -> new IllegalArgumentException("패치 로그를 찾을 수 없음"));
        Optional<Version> optionalVersion = versionRepository.findById(dto.getVersionId());
        Version version = optionalVersion.orElseThrow(() -> new IllegalArgumentException("버전을 찾을 수 없음"));

        PatchLogVersion patchLogVersion = new PatchLogVersion(patchLog, version);
        patchLogVersionRepository.save(patchLogVersion);

        return ResponseUtil.ok();
    }
}
