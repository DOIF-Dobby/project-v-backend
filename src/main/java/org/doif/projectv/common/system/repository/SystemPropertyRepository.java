package org.doif.projectv.common.system.repository;

import org.doif.projectv.common.system.entity.SystemProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemPropertyRepository extends JpaRepository<SystemProperty, Long>, SystemPropertyQueryRepository {
}
