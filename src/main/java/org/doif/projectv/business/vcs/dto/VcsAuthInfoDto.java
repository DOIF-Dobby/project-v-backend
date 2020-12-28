package org.doif.projectv.business.vcs.dto;

import lombok.*;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public class VcsAuthInfoDto {
    @Getter
    @Setter
    @ToString
    public static class Result {
        private Long vcsAuthInfoId;
        private String userId;
        private VcsType vcsType;
        private String vcsTypeName;
        private String vcsAuthId;
        private String vcsAuthPassword;
        private EnableStatus status;
        private String statusName;

        public Result(Long vcsAuthInfoId, String userId, VcsType vcsType, String vcsAuthId, String vcsAuthPassword, EnableStatus status) {
            this.vcsAuthInfoId = vcsAuthInfoId;
            this.userId = userId;
            this.vcsType = vcsType;
            this.vcsTypeName = vcsType.getMessage();
            this.vcsAuthId = vcsAuthId;
            this.vcsAuthPassword = vcsAuthPassword;
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
    public static class Search {
        private String userId;
    }

    @Getter
    @Setter
    public static class Insert {
        private String userId;
        private VcsType vcsType;
        private String vcsAuthId;
        private String vcsAuthPassword;
        private EnableStatus status;
    }

    @Getter
    @Setter
    public static class Update {
        private String vcsAuthId;
        private String vcsAuthPassword;
        private EnableStatus status;
    }
}
