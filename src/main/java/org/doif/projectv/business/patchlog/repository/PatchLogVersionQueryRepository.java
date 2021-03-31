package org.doif.projectv.business.patchlog.repository;

import org.doif.projectv.business.patchlog.dto.PatchLogVersionDto;

import java.util.List;

public interface PatchLogVersionQueryRepository {

    List<PatchLogVersionDto.Result> searchPatchLogVersionsByPatchLogId(Long patchLogId);
}
