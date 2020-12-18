package org.doif.projectv.business.vcs.service;

import org.doif.projectv.business.vcs.dto.VcsAuthInfoDto;
import org.doif.projectv.common.response.CommonResponse;

import java.util.List;

public interface VcsAuthInfoService {
    List<VcsAuthInfoDto.Result> searchByCondition(VcsAuthInfoDto.Search search);

    CommonResponse insert(VcsAuthInfoDto.Insert dto);

    CommonResponse update(Long id, VcsAuthInfoDto.Update dto);

    CommonResponse delete(Long id);
}
