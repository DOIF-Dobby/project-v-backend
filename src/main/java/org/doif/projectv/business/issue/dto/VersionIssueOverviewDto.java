package org.doif.projectv.business.issue.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class VersionIssueOverviewDto {

    private String projectName;
    private String moduleName;
    private String issueName;
    private IssueStatus status;
    private IssueCategory category;
    private VersionIssueProgress progress;
    private String issueYm;
    private String assignee;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double manDay;
    private LocalDate patchDateDev;
    private LocalDate patchDateProd;

    @QueryProjection
    public VersionIssueOverviewDto(String projectName, String moduleName, String issueName, IssueStatus status, IssueCategory category, VersionIssueProgress progress, String issueYm, String assignee, LocalDate startDate, LocalDate endDate, Double manDay, LocalDate patchDateDev, LocalDate patchDateProd) {
        this.projectName = projectName;
        this.moduleName = moduleName;
        this.issueName = issueName;
        this.status = status;
        this.category = category;
        this.progress = progress;
        this.issueYm = issueYm;
        this.assignee = assignee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.manDay = manDay;
        this.patchDateDev = patchDateDev;
        this.patchDateProd = patchDateProd;
    }
}
