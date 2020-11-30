package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.http.HttpMethod;

import java.util.List;

public class TabDto {

    @Getter
    @Setter
    @ToString
    public static class Result extends ResourceAuthorityDto.Result {
        private String tabGroup;
        private Integer sort;

        public Result(Long resourceId, String name, String description, EnableStatus status, String url, HttpMethod httpMethod, String tabGroup, Integer sort) {
            this.resourceId = resourceId;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = status.getMessage();
            this.url = url;
            this.httpMethod = httpMethod;
            this.tabGroup = tabGroup;
            this.sort = sort;
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        List<Result> content;
    }

    @Getter
    @Setter
    @ToString
    public static class Insert extends ResourceAuthorityDto.Insert {
        private Long pageId;
        private String tabGroup;
        private Integer sort;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceAuthorityDto.Update {
        private String tabGroup;
        private Integer sort;
    }
}
