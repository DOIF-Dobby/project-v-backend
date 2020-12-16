package org.doif.projectv.common.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.common.user.constant.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import static org.springframework.util.StringUtils.*;

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
        private String svnId;
        private String svnPassword;

        @QueryProjection
        public Result(String id, String name, UserStatus status, String svnId, String svnPassword) {
            this.id = id;
            this.name = name;
            this.status = status;
            this.svnId = svnId;
            this.svnPassword = svnPassword;
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
        private String svnId;
        private String svnPassword;

        public String getSvnId() {
            return isEmpty(svnId) ? "" : svnId;
        }

        public String getSvnPassword() {
            return isEmpty(svnPassword) ? "" : svnPassword;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private String name;
        private UserStatus status;
        private String svnId;
        private String svnPassword;

        public String getSvnId() {
            return isEmpty(svnId) ? "" : svnId;
        }

        public String getSvnPassword() {
            return isEmpty(svnPassword) ? "" : svnPassword;
        }
    }
}
