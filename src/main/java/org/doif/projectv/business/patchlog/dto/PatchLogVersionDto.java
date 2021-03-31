package org.doif.projectv.business.patchlog.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

public class PatchLogVersionDto {

    @Getter
    @Setter
    @ToString
    public static class Result {
        private Long patchLogVersionId;
        private Long patchLogId;
        private Long versionId;
        private String versionName;
        private String versionDescription;
        private String moduleName;

        @QueryProjection
        public Result(Long patchLogVersionId, Long patchLogId, Long versionId, String versionName, String versionDescription, String moduleName) {
            this.patchLogVersionId = patchLogVersionId;
            this.patchLogId = patchLogId;
            this.versionId = versionId;
            this.versionName = versionName;
            this.versionDescription = versionDescription;
            this.moduleName = moduleName;
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {
        List<Result> content;
    }

    @Getter
    @Setter
    public static class Insert {
        private Long patchLogId;
        private Long versionId;
    }

}
