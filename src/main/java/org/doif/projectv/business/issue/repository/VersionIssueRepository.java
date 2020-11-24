package org.doif.projectv.business.issue.repository;

import org.doif.projectv.business.issue.entity.VersionIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionIssueRepository extends JpaRepository<VersionIssue, Long>, VersionIssueQueryRepository {

}
