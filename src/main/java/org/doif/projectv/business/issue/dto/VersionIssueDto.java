package org.doif.projectv.business.issue.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class VersionIssueDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Result {
        private Long versionIssueId;
        private String moduleName;
        private String versionName;
        private String issueName;
        private IssueStatus issueStatus;
        private String issueContents;
        private String issueYm;
        private VersionIssueProgress progress;
        private String assignee;
        private String remark;

        @QueryProjection
        public Result(Long versionIssueId, String moduleName, String versionName, String issueName, IssueStatus issueStatus, String issueContents, String issueYm, VersionIssueProgress progress, String assignee, String remark) {
            this.versionIssueId = versionIssueId;
            this.moduleName = moduleName;
            this.versionName = versionName;
            this.issueName = issueName;
            this.issueStatus = issueStatus;
            this.issueContents = issueContents;
            this.issueYm = issueYm;
            this.progress = progress;
            this.assignee = assignee;
            this.remark = remark;
        }
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private List<Result> content;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Search {
        private Long issueId;
        private Long projectId;
        private Long moduleId;
        private Long versionId;
        private IssueStatus status;
        private IssueCategory category;
        private String issueYm;
        private VersionIssueProgress progress;
        private String assignee;
        private PatchTarget patchTarget;
    }

    @Getter
    @Setter
    public static class Insert {
        private Long versionId;
        private Long issueId;
        private String issueYm;
        private VersionIssueProgress progress;
        private String assignee;
        private String remark;
    }

    @Getter
    @Setter
    public static class Update {
        private String issueYm;
        private VersionIssueProgress progress;
        private String assignee;
        private String remark;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ResultOverview {
        private Long versionIssueId;
        private String projectName;
        private String moduleName;
        private String versionName;
        private String issueName;
        private IssueStatus status;
        private String statusName;
        private IssueCategory category;
        private String categoryName;
        private VersionIssueProgress progress;
        private String progressName;
        private String issueYm;
        private String assignee;
        private LocalDate startDate;
        private LocalDate endDate;
        private Double manDay;
        private LocalDate patchDateDev;
        private LocalDate patchDateProd;

        @QueryProjection
        public ResultOverview(Long versionIssueId, String projectName, String moduleName, String versionName, String issueName, IssueStatus status, IssueCategory category, VersionIssueProgress progress, String issueYm, String assignee, LocalDate startDate, LocalDate endDate, Double manDay, LocalDate patchDateDev, LocalDate patchDateProd) {
            this.versionIssueId = versionIssueId;
            this.projectName = projectName;
            this.moduleName = moduleName;
            this.versionName = versionName;
            this.issueName = issueName;
            this.status = status;
            this.statusName = status.getMessage();
            this.category = category;
            this.categoryName = category.getMessage();
            this.progress = progress;
            this.progressName = progress.getMessage();
            this.issueYm = issueYm;
            this.assignee = assignee;
            this.startDate = startDate;
            this.endDate = endDate;
            this.manDay = manDay;
            this.patchDateDev = patchDateDev;
            this.patchDateProd = patchDateProd;
        }
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseOverview {
        private Page<ResultOverview> pageInfo;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseNonePatch {
        private Page<Result> pageInfo;
    }
}
