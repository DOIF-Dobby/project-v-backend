package org.doif.projectv.business.patchlog.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.patchlog.repository.PatchLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatchLogServiceImpl implements PatchLogService {

    private final PatchLogRepository patchLogRepository;
}
