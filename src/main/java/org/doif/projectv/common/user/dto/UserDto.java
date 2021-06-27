package org.doif.projectv.common.user.dto;

import lombok.*;
import org.doif.projectv.common.user.constant.UserStatus;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
        private String profilePicture;

        public Result(String id, String name, UserStatus status, String profilePicture) {
            this.id = id;
            this.name = name;
            this.status = status;
            this.statusName = status.getMessage();
            this.profilePicture= profilePicture;
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
        @NotEmpty
        private String id;
        @NotEmpty
        private String name;
        @NotEmpty
        private String password;
        @NotNull
        private UserStatus status;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        @NotEmpty
        private String name;
        @NotNull
        private UserStatus status;
    }
}
