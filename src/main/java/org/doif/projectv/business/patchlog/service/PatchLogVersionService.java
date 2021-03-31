package org.doif.projectv.business.patchlog.service;

import org.doif.projectv.business.patchlog.dto.PatchLogVersionDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface PatchLogVersionService {
    List<PatchLogVersionDto.Result> searchPatchLogVersionsByPatchLogId(Long patchLogId);

    CommonResponse insert(PatchLogVersionDto.Insert dto);
}
