package org.doif.projectv.business.vcs.repository;

import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.business.vcs.entity.VcsAuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VcsAuthInfoRepository extends JpaRepository<VcsAuthInfo, Long> {

    List<VcsAuthInfo> findByUserId(String userId);

    Optional<VcsAuthInfo> findByUserIdAndVcsType(String userId, VcsType vcsType);

}

