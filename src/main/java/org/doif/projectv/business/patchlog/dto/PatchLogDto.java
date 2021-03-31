package org.doif.projectv.business.patchlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class PatchLogDto {

    @Getter
    @Setter
    @ToString
    public static class Result {
        private Long patchLogId;
        private String clientName;
        private PatchTarget target;
        private String targetName;
        private PatchStatus status;
        private String statusName;
        private LocalDate patchScheduleDate;
        private LocalDate patchDate;
        private String worker;
        private String remark;

        @QueryProjection
        public Result(Long patchLogId, String clientName, PatchTarget target, PatchStatus status, LocalDate patchScheduleDate, LocalDate patchDate, String worker, String remark) {
            this.patchLogId = patchLogId;
            this.clientName = clientName;
            this.target = target;
            this.targetName = target.getMessage();
            this.status = status;
            this.statusName = status.getMessage();
            this.patchScheduleDate = patchScheduleDate;
            this.patchDate = patchDate;
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
        Page<PatchLogDto.Result> pageInfo;
    }

    @Getter
    @Setter
    public static class Search {
        private PatchTarget target;
        private PatchStatus status;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate patchScheduleDateGoe;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate patchScheduleDateLt;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate patchDateGoe;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate patchDateLt;
        private String worker;
    }

    @Getter
    @Setter
    public static class Insert {
        private Long clientId;
        private PatchTarget target;
        private PatchStatus status;
        private LocalDate patchScheduleDate;
        private String worker;
        private String remark;
    }

    @Getter
    @Setter
    public static class Update {
        private PatchTarget target;
        private PatchStatus status;
        private LocalDate patchScheduleDate;
        private LocalDate patchDate;
        private String worker;
        private String remark;
    }
}
