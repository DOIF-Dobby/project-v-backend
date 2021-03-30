package org.doif.projectv.common.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.common.status.EnableStatus;

import java.util.List;

public class UserRoleDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ResultRole {
        private Long roleId;
        private String name;
        private String description;
        private EnableStatus status;
        private String statusName;
        private boolean checked;

        @QueryProjection
        public ResultRole(Long roleId, String name, String description, EnableStatus status, boolean checked) {
            this.roleId = roleId;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = status.getMessage();
            this.checked = checked;
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        List<ResultRole> content;
    }

    @Getter
    @Setter
    @ToString
    public static class Allocate {
        private String userId;
        private List<Long> roleIds;
    }
}
