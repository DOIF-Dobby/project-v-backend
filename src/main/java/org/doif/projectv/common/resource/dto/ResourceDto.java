package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.doif.projectv.common.status.EnableStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ResourceDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Result {
        protected Long resourceId;
        protected String name;
        protected String description;
        protected EnableStatus status;
        protected String statusName;
        protected String code;

        public Result(Long resourceId, String name, String description, EnableStatus status, String code) {
            this.resourceId = resourceId;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = status.getMessage();
            this.code = code;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Insert {
        @NotEmpty
        protected String name;
        protected String description;
        @NotNull
        protected EnableStatus status;
        protected String code;
    }

    @Getter
    @Setter
    @ToString
    public static class Update {
        protected String name;
        protected String description;
        protected EnableStatus status;
    }

}
