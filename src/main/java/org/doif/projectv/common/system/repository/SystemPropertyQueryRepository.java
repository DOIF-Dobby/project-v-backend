package org.doif.projectv.common.system.repository;

import org.doif.projectv.common.system.dto.SystemPropertyDto;
import org.doif.projectv.common.system.entity.SystemProperty;

import java.util.List;

public interface SystemPropertyQueryRepository {

    List<SystemProperty> searchByCondition(SystemPropertyDto.Search search);
}
