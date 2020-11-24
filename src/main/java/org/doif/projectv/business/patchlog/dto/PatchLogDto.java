package org.doif.projectv.business.patchlog.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;

import java.time.LocalDate;

public class PatchLogDto {

    @Getter
    @Setter
    @ToString
    public static class Result {
        private Long patchLogId;
        private String moduleName;
        private PatchTarget target;
        private PatchStatus status;
        private LocalDate patchScheduleDate;
        private LocalDate patchDate;
        private String worker;
        private String version;
        private String remark;

        @QueryProjection
        public Result(Long patchLogId, String moduleName, PatchTarget target, PatchStatus status, LocalDate patchScheduleDate, LocalDate patchDate, String worker, String version, String remark) {
            this.patchLogId = patchLogId;
            this.moduleName = moduleName;
            this.target = target;
            this.status = status;
            this.patchScheduleDate = patchScheduleDate;
            this.patchDate = patchDate;
            this.worker = worker;
            this.version = version;
            this.remark = remark;
        }
    }
}
