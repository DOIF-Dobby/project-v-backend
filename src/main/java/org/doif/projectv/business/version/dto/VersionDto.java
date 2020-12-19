package org.doif.projectv.business.version.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.version.constant.VersionStatus;
import org.springframework.data.domain.Page;

public class VersionDto {

    @Getter
    @Setter
    @ToString
    public static class Result {
        private Long versionId;
        private String versionName;
        private String description;
        private Long moduleId;
        private VersionStatus versionStatus;
        private String versionStatusName;
        private String revision;
        private String tag;

        @QueryProjection
        public Result(Long versionId, String versionName, String description, Long moduleId, VersionStatus versionStatus, String revision, String tag) {
            this.versionId = versionId;
            this.versionName = versionName;
            this.description = description;
            this.moduleId = moduleId;
            this.versionStatus = versionStatus;
            this.versionStatusName = versionStatus.getMessage();
            this.revision = revision;
            this.tag = tag;
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        Page<VersionDto.Result> pageInfo;
    }

    @Getter
    @Setter
    public static class Search {
        private Long moduleId;
        private String versionName;
        private String description;
        private VersionStatus versionStatus;
    }

    @Getter
    @Setter
    public static class Insert {
        private String versionName;
        private String description;
        private Long moduleId;
    }

    @Getter
    @Setter
    public static class Update {
        private String description;
    }
}
