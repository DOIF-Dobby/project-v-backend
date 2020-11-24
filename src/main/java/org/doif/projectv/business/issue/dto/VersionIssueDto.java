package org.doif.projectv.business.issue.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.springframework.data.domain.Page;

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
        private String issueName;
        private IssueStatus issueStatus;
        private String issueContents;
        private String issueYm;
        private VersionIssueProgress progress;
        private String assignee;
        private String remark;

        @QueryProjection
        public Result(Long versionIssueId, String moduleName, String issueName, IssueStatus issueStatus, String issueContents, String issueYm, VersionIssueProgress progress, String assignee, String remark) {
            this.versionIssueId = versionIssueId;
            this.moduleName = moduleName;
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
        private IssueStatus status;
        private IssueCategory category;
        private String issueYm;
        private VersionIssueProgress progress;
        private String assignee;

        private PatchTarget patchTarget;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseOverview {
        Page<VersionIssueOverviewDto> pageInfo;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseNonePatch {
        Page<Result> pageInfo;
    }
}
