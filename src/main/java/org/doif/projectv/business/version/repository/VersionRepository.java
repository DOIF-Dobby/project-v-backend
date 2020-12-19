package org.doif.projectv.business.version.repository;

import org.doif.projectv.business.version.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VersionRepository extends JpaRepository<Version, Long>, VersionQueryRepository {

    @Query("select v from Version v join fetch v.module where v.id = :id")
    Optional<Version> findFetchModuleById(@Param("id") Long id);
}
