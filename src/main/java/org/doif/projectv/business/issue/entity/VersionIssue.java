package org.doif.projectv.business.issue.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.task.entity.Task;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.common.jpa.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VersionIssue extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_issue_id", length = 10, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", nullable = false)
    private Version version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Column(name = "issue_ym", length = 6, nullable = false)
    private String issueYm;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress", length = 20, nullable = false)
    private VersionIssueProgress progress;

    @Column(name = "assignee", length = 50)
    private String assignee;

    @Column(name = "remark")
    private String remark;

    @OneToMany(mappedBy = "versionIssue")
    private List<Task> tasks = new ArrayList<>();

    public VersionIssue(Version version, Issue issue, String issueYm, VersionIssueProgress progress, String assignee, String remark) {
        this.version = version;
        this.issue = issue;
        this.issueYm = issueYm;
        this.progress = progress;
        this.assignee = assignee;
        this.remark = remark;

        version.getVersionIssues().add(this);
    }

    public void changeVersionIssue(String issueYm, VersionIssueProgress progress, String assignee, String remark) {
        this.issueYm = issueYm;
        this.progress = progress;
        this.assignee = assignee;
        this.remark = remark;
    }
}
