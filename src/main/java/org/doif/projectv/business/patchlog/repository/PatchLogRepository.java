package org.doif.projectv.business.patchlog.repository;

import org.doif.projectv.business.patchlog.entity.PatchLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatchLogRepository extends JpaRepository<PatchLog, Long>, PatchLogQueryRepository {
}
