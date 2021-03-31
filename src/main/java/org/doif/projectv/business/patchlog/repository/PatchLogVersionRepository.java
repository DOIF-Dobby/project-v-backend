package org.doif.projectv.business.patchlog.repository;

import org.doif.projectv.business.patchlog.entity.PatchLogVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatchLogVersionRepository extends JpaRepository<PatchLogVersion, Long>, PatchLogVersionQueryRepository {
}
