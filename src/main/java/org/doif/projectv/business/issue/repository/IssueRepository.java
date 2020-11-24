package org.doif.projectv.business.issue.repository;

import org.doif.projectv.business.issue.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long>, IssueQueryRepository {
}
