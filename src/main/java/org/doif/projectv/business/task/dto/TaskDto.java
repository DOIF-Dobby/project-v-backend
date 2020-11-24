package org.doif.projectv.business.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.business.task.constant.TaskType;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public class TaskDto {

    @Getter
    @Setter
    @ToString
    public static class Result {
        private Long taskId;
        private String issueName;
        private String versionName;
        private TaskType type;
        private String typeName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate startDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate endDate;
        private Double manDay;
        private String contents;
        private String worker;
        private String remark;

        @QueryProjection
        public Result(Long taskId, String issueName, String versionName, TaskType type, LocalDate startDate, LocalDate endDate, Double manDay, String contents, String worker, String remark) {
            this.taskId = taskId;
            this.issueName = issueName;
            this.versionName = versionName;
            this.type = type;
            this.typeName = type.getMessage();
            this.startDate = startDate;
            this.endDate = endDate;
            this.manDay = manDay;
            this.contents = contents;
            this.worker = worker;
            this.remark = remark;
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        Page<TaskDto.Result> pageInfo;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Search {
        private Long moduleId;
        private Long versionId;
        private Long versionIssueId;
        private LocalDate startDate;
        private LocalDate endDate;
        private TaskType type;
        private String worker;
    }

    @Getter
    @Setter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Insert {
        private Long versionIssueId;
        private TaskType type;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate startDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate endDate;
        private Double manDay;
        private String contents;
        private String worker;
        private String remark;
    }

    @Getter
    @Setter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private TaskType type;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate startDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate endDate;
        private Double manDay;
        private String contents;
        private String worker;
        private String remark;
    }
}
