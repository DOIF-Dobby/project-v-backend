package org.doif.projectv.business.vcs.service;

import org.doif.projectv.business.vcs.dto.VcsDto;

import java.util.List;

public interface VcsService {
    List<VcsDto.Log> searchLogByCondition(VcsDto.SearchLog search);
}
