package org.doif.projectv.business.vcs.repository;

import org.doif.projectv.business.vcs.entity.VcsAuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VcsAuthInfoRepository extends JpaRepository<VcsAuthInfo, Long> {

    List<VcsAuthInfo> findByUserId(String userId);
}
