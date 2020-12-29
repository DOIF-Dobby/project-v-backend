package org.doif.projectv.business.task.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.task.constant.TaskType;
import org.doif.projectv.common.jpa.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", length = 10, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_issue_id", nullable = false)
    private VersionIssue versionIssue;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Lob
    @Column(name = "contents", nullable = false)
    private String contents;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private TaskType type;

    @Column(name = "man_day", precision = 4, scale = 1, nullable = false)
    private Double manDay;

    @Column(name = "worker", length = 50, nullable = false)
    private String worker;

    @Column(name = "remark")
    private String remark;

    @Builder
    public Task(VersionIssue versionIssue, LocalDate startDate, LocalDate endDate, String contents, TaskType type, Double manDay, String worker, String remark) {
        this.versionIssue = versionIssue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
        this.type = type;
        this.manDay = manDay;
        this.worker = worker;
        this.remark = remark;

        versionIssue.getTasks().add(this);
    }

    public void changeTask(LocalDate startDate, LocalDate endDate, String contents, TaskType type, Double manDay, String worker, String remark) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
        this.type = type;
        this.manDay = manDay;
        this.worker = worker;
        this.remark = remark;
    }

}
