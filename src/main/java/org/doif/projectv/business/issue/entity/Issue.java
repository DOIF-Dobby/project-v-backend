package org.doif.projectv.business.issue.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.common.jpa.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Issue extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id", length = 10, nullable = false)
    private Long id;

    @Column(name = "issue_name", nullable = false)
    private String issueName;

    @Lob
    @Column(name = "contents", nullable = false)
    private String contents;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private IssueStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 20, nullable = false)
    private IssueCategory category;

    public Issue(String issueName, String contents, IssueStatus status, IssueCategory category) {
        this.issueName = issueName;
        this.contents = contents;
        this.status = status;
        this.category = category;
    }

    public void changeIssue(String issueName, String contents, IssueStatus status, IssueCategory category) {
        this.issueName = issueName;
        this.contents = contents;
        this.status = status;
        this.category = category;
    }
}
