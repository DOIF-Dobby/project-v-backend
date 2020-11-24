package org.doif.projectv.business.patchlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public class PatchLogDto {

    @Getter
    @Setter
    @ToString
    public static class Result {
        private Long patchLogId;
        private String moduleName;
        private String versionName;
        private PatchTarget target;
        private PatchStatus status;
        private LocalDate patchScheduleDate;
        private LocalDate patchDate;
        private String worker;
        private String remark;

        @QueryProjection
        public Result(Long patchLogId, String moduleName,  String versionName, PatchTarget target, PatchStatus status, LocalDate patchScheduleDate, LocalDate patchDate, String worker, String remark) {
            this.patchLogId = patchLogId;
            this.moduleName = moduleName;
            this.versionName = versionName;
            this.target = target;
            this.status = status;
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
        private Long moduleId;
        private Long versionId;
        private PatchTarget target;
        private PatchStatus status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate patchScheduleDateGoe;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate patchScheduleDateLt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate patchDateGoe;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate patchDateLt;
        private String worker;
    }

    @Getter
    @Setter
    public static class Insert {
        private Long versionId;
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
