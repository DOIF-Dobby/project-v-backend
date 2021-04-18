package org.doif.projectv.common.user.dto;

import lombok.*;
import org.doif.projectv.common.user.constant.UserStatus;
import org.springframework.data.domain.Page;

public class UserDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Result {
        private String id;
        private String name;
        private UserStatus status;
        private String statusName;

        public Result(String id, String name, UserStatus status) {
            this.id = id;
            this.name = name;
            this.status = status;
            this.statusName = status.getMessage();
        }
    }

    @Getter
    @Setter
    public static class Auth {
        private String id;
        private String password;
    }

    @Getter
    @Setter
    public static class Search {
        private String id;
        private String name;
        private UserStatus status;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Page<Result> pageInfo;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Insert {
        private String id;
        private String name;
        private String password;
        private UserStatus status;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private String name;
        private UserStatus status;
    }
}
