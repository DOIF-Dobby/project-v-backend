package org.doif.projectv.common.role.dto;

import lombok.*;
import org.doif.projectv.common.status.EnableStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class RoleDto {

    @Getter
    @Setter
    @ToString
    public static class Result {
        private Long roleId;
        private String name;
        private String description;
        private EnableStatus status;
        private String statusName;

        public Result(Long roleId, String name, String description, EnableStatus status) {
            this.roleId = roleId;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = status.getMessage();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        List<Result> content;
    }

    @Getter
    @Setter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Insert {
        @NotEmpty
        private String name;
        private String description;
        @NotNull
        private EnableStatus status;
    }

    @Getter
    @Setter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        @NotEmpty
        private String name;
        private String description;
        @NotNull
        private EnableStatus status;
    }
}
