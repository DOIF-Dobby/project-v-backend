package org.doif.projectv.business.issue.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.common.jpa.dto.BaseDto;
import org.springframework.data.domain.Page;

@Getter
@Setter
@ToString
public class IssueDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Result extends BaseDto {
        private Long issueId;
        private String issueName;
        private String contents;
        private IssueStatus status;
        private IssueCategory category;

        @QueryProjection
        public Result(Long issueId, String issueName, String contents, IssueStatus status, IssueCategory category) {
            this.issueId = issueId;
            this.issueName = issueName;
            this.contents = contents;
            this.status = status;
            this.category = category;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Search {
        private String contents;
        private IssueStatus status;
        private IssueCategory category;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Page<IssueDto.Result> pageInfo;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Insert {
        private String issueName;
        private String contents;
        private IssueStatus status;
        private IssueCategory category;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private String issueName;
        private String contents;
        private IssueStatus status;
        private IssueCategory category;
    }
}
