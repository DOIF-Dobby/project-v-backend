package org.doif.projectv.business.version.repository;

import org.doif.projectv.business.version.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, Long>, VersionQueryRepository {
}
